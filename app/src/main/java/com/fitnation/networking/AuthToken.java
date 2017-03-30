package com.fitnation.networking;


class AuthToken {
    private String accessToken;
    private String refreshToken;

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

    private AuthToken() {
    }
}
