package com.placement.portal.security;

import com.placement.portal.model.Student;
import com.placement.portal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. Load user details from Google (Standard OAuth2 flow)
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Extract the email from the Google account
        String email = oAuth2User.getAttribute("email");

        // 2. Check if this email exists in our Student Database
        Optional<Student> studentOptional = studentRepository.findByEmail(email);

        // 3. STRICT MODE: Block login if user is not pre-registered
        if (studentOptional.isEmpty()) {
            throw new OAuth2AuthenticationException(new OAuth2Error("access_denied"),
                    "You are not a registered student. Access Denied for email: " + email);
        }

        // 4. If found, return the user (Login Successful)
        // You could also update the user's name/photo from Google here if you wanted to sync data
        return oAuth2User;
    }
}