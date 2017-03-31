package com.fitnation.networking;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmToken {
    @PrimaryKey
    private Long androidId;
    private Long id;
    private String accessToken;
    private String refreshToken;


    public RealmToken(){
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
