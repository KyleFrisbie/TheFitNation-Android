package com.fitnation.utils;

/**
 * An environment containing the base url for all requests
 */
public class Environment {
    private String baseUrl;

    public Environment(String url) {
        baseUrl = url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiUrl() {
        return baseUrl += "api/";
    }
}
