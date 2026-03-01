package com.placement.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    // Define the root location for storing uploads
    private final Path rootLocation = Paths.get("uploads/cvs");

    public FileStorageService() {
        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    public String store(MultipartFile file, String studentRoll) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }

            // Create a unique filename: RollNumber_Timestamp_OriginalName
            // Example: RN_MT_CSE_1_1638220000_my_cv.pdf
            String filename = studentRoll + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path destinationFile = this.rootLocation.resolve(Paths.get(filename))
                    .normalize().toAbsolutePath();

            // Security check to prevent writing outside the directory
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory.");
            }

            // Copy the file to the target location (replacing existing if same name)
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            // Return the relative path to store in the database
            return "uploads/cvs/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }
}