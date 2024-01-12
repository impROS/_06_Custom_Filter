package com.example._06_custom_filter_02;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.stream.Collectors;

public class CustomFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        StringBuilder requestDetails = new StringBuilder();

        requestDetails.append("Endpoint: ").append(request.getRequestURI()).append(", ");

        requestDetails.append("HTTP Method: ").append(request.getMethod()).append(", ");

        // Request Parametreleri
        requestDetails.append("Request Parameters: ");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            requestDetails.append(paramName).append("=").append(request.getParameter(paramName)).append(", ");
        }

        // Request Body
        String requestBody = getRequestContent(request);
        requestDetails.append("Request Body: ").append(requestBody);

        System.out.println("Request details: " + requestDetails.toString());


        System.out.println("Response sent for /hello at: " + System.currentTimeMillis());

        filterChain.doFilter(request, response);
    }

    private String getRequestContent(HttpServletRequest request) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            return bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

}


