package com.placement.portal.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        
        // Build the frontend URL with error message
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/login")
                .queryParam("error", "invalid_credentials")
                .build().toUriString();

        // Redirect to frontend with error parameter
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

