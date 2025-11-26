package com.placement.portal.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle "User Not Found" or "Job Not Found" errors
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        response.put("status", "400");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle Database constraints (Duplicate Email, Duplicate Code, etc.)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseException(DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        String msg = ex.getMostSpecificCause().getMessage();

        if (msg.contains("Duplicate entry")) {
            response.put("error", "Duplicate entry detected. This record already exists.");
            // You can parse 'msg' further to say "Email already exists" if you want
        } else {
            response.put("error", "Database error occurred.");
        }
        response.put("details", msg);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409 Conflict
    }

    // Catch-all for other errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Something went wrong.");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}