package com.sudhanshu.reportservice.util;

import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;

public class RestTemplateHelper {
/**
     * Adds standard query parameters to any URL
     */
    @SuppressWarnings("deprecation")
	public static String addStandardQueryParams(String url) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("page", 0)
                .queryParam("size", 10)

                .queryParam("requestId", UUID.randomUUID().toString())
                .queryParam("timestamp", System.currentTimeMillis())
                .toUriString();
    }
    
    /**
     * Adds custom query parameters to any URL
     */
    @SuppressWarnings("deprecation")
	public static String addQueryParams(String url, String... keyValuePairs) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        
        for (int i = 0; i < keyValuePairs.length - 1; i += 2) {
            builder.queryParam(keyValuePairs[i], keyValuePairs[i + 1]);
        }
        
        return builder.toUriString();
    }
}
