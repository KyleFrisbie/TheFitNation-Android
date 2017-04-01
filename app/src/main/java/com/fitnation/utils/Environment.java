package com.fitnation.utils;

/**
 * Created by Ryan on 4/1/2017.
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
