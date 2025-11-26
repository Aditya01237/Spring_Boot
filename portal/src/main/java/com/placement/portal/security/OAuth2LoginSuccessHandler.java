package com.placement.portal.security;

import com.placement.portal.model.Student;
import com.placement.portal.repository.StudentRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // Fetch the student from DB to get Role and Details
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Generate JWT Token
        String token = jwtUtils.generateToken(student.getEmail(), student.getRole());

        // Build the Frontend URL with the Token as a parameter
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/login")
                .queryParam("token", token)
                .queryParam("error", "")
                .build().toUriString();

        // Redirect user to Frontend with the Token
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}