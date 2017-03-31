package com.fitnation.networking;


public class AuthToken {
    private String accessToken = null;
    private String refreshToken = null;

    private static AuthToken ourInstance;

    public static AuthToken getInstance() {
        if(ourInstance == null){
            ourInstance = new AuthToken();
        }
        return ourInstance;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    private AuthToken() {
    }
}
