package com.security.analyzer.v1.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

public class BearerTokenAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/problem+json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("{\"type\": \"https://example.com/access-denied\", \"title\": \"Forbidden\", \"status\": 403, \"detail\": \"" + accessDeniedException.getMessage() + "\", \"instance\": \"" + request.getRequestURI() + "\"}");
    }
}
