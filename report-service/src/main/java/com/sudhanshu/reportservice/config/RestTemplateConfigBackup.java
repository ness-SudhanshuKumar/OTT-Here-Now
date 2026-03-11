package com.sudhanshu.reportservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;



public class RestTemplateConfigBackup {

    @Value("${resttemplate.connection.timeout:5000}")
    private int connectionTimeout;
    @Value("${resttemplate.read.timeout:10000}")
    private int readTimeout;
    
    @Value("${external.service.auth.token:default-token}")
    private String authToken;
    
    @Value("${external.service.api.key:default-api-key}")
    private String apiKey;
    
//
//	@Bean
//	@LoadBalanced
    public RestTemplate restTemplate() {
		
		// 1. Configure Request Factory with Timeouts
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectionTimeout);
        factory.setReadTimeout(readTimeout);
        
        // Use buffering to allow reading response body multiple times
        BufferingClientHttpRequestFactory bufferingFactory = 
            new BufferingClientHttpRequestFactory(factory);
        
        RestTemplate restTemplate = new RestTemplate(bufferingFactory);
        
        // 2. Add Interceptors directly as lambda/anonymous classes
        restTemplate.setInterceptors(Arrays.asList(
            
            // REQUEST INTERCEPTOR - Adds headers, params & auth
            (request, body, execution) -> {

                HttpHeaders headers = request.getHeaders();
                
                // Add Authorization Header
                if (authToken != null && !authToken.isEmpty() && 
                    !headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    headers.setBearerAuth(authToken);
                }
                
                // Add Custom Headers
                if (apiKey != null && !apiKey.isEmpty()) {
                    headers.add("X-API-Key", apiKey);
                }
                headers.add("X-Request-ID", UUID.randomUUID().toString());
                headers.add("X-Correlation-ID", "CORR-" + UUID.randomUUID().toString().substring(0, 8));
                headers.add("X-Client-Name", "my-service");
                headers.add("X-Client-Version", "1.0.0");
                headers.add("X-Timestamp", String.valueOf(System.currentTimeMillis()));
                
                // Set default headers if not present
                if (!headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                }
                if (!headers.containsKey(HttpHeaders.ACCEPT)) {
                    headers.add(HttpHeaders.ACCEPT, "application/json");
                }
                
                // Log outgoing request
                System.out.println("\n========== OUTGOING REQUEST ==========");
                System.out.println("URI: " + request.getURI());
                //System.out.println("Method: " + request.getMethodValue());
                System.out.println("Headers: " + maskSensitiveHeaders(headers));
                System.out.println("======================================\n");
                
                // Execute request (headers are already modified in place)
                return execution.execute(request, body);
            
            },
            
            // RESPONSE INTERCEPTOR - Enhances and logs response
            (request, body, execution) -> {
                long startTime = System.currentTimeMillis();
                
                // Execute request
                ClientHttpResponse response = execution.execute(request, body);
                
                long duration = System.currentTimeMillis() - startTime;
                
                // Cache response body
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getBody(), StandardCharsets.UTF_8)
                );
                String responseBody = reader.lines().collect(Collectors.joining("\n"));
                byte[] cachedBody = responseBody.getBytes(StandardCharsets.UTF_8);
                
                // Log response details
                System.out.println("\n========== RESPONSE RECEIVED ==========");
                System.out.println("Request URI: " + request.getURI());
                System.out.println("Status: " + response.getStatusCode() + " " + response.getStatusText());
                System.out.println("Duration: " + duration + "ms");
                System.out.println("Response Headers: " + response.getHeaders().keySet());
                
                String displayBody = responseBody.length() > 500 
                    ? responseBody.substring(0, 500) + "... (truncated)" 
                    : responseBody;
                System.out.println("Response Body: " + displayBody);
                System.out.println("Body Size: " + cachedBody.length + " bytes");
                
                // Warnings
                if (response.getStatusCode().is4xxClientError() || 
                    response.getStatusCode().is5xxServerError()) {
                    System.err.println("⚠ ERROR Response: " + response.getStatusCode());
                }
                if (duration > 3000) {
                    System.out.println("⚠ SLOW Response: " + duration + "ms");
                }
                
                System.out.println("=====================================\n");
                
                // Return wrapped response with cached body
                return new CachedBodyClientHttpResponse(response, cachedBody);
            }
        ));
        
        return restTemplate;
    }
    
    // Helper method to mask sensitive headers
    private String maskSensitiveHeaders(HttpHeaders headers) {
        StringBuilder sb = new StringBuilder("{");
        headers.forEach((key, values) -> {
            if (key.equalsIgnoreCase("Authorization") || key.equalsIgnoreCase("X-API-Key")) {
                sb.append(key).append("=***MASKED***, ");
            } else {
                sb.append(key).append("=").append(values).append(", ");
            }
        });
        sb.append("}");
        return sb.toString();
    }
    
    // Inner class for Cached Response
    private static class CachedBodyClientHttpResponse implements ClientHttpResponse {
        private final ClientHttpResponse response;
        private final byte[] cachedBody;
        
        public CachedBodyClientHttpResponse(ClientHttpResponse response, byte[] cachedBody) {
            this.response = response;
            this.cachedBody = cachedBody;
        }
        
        @Override
        public HttpStatus getStatusCode() throws IOException {
            return (HttpStatus) response.getStatusCode();
        }
        
        @Override
        public String getStatusText() throws IOException {
            return response.getStatusText();
        }
        
        @Override
        public void close() {
            response.close();
        }
        
        @Override
        public java.io.InputStream getBody() throws IOException {
            return new ByteArrayInputStream(cachedBody);
        }
        
        @Override
        public HttpHeaders getHeaders() {
            return response.getHeaders();
        }
    }
}