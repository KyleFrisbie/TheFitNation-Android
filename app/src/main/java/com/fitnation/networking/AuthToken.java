package com.fitnation.networking;


public class AuthToken {
    private String accessToken = null;
    private String refreshToken = null;

    private static AuthToken ourInstance;

    public synchronized static AuthToken getInstance() {
        if(ourInstance == null){
            ourInstance = new AuthToken();
        }
        return ourInstance;
    }

    public synchronized void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public synchronized void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public synchronized String getRefreshToken() {
        return refreshToken;
    }

    public synchronized String getAccessToken() {
        return accessToken;
    }

    private AuthToken() {
    }
}
