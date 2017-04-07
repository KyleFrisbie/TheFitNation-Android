package com.fitnation.utils;

import com.fitnation.networking.AuthToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

// TODO: either fix implementation or figure out how to use utils
/**
 * Holds all the utilities for network communications
 */
public class NetworkUtils {
    private static NetworkUtils mInstance;

    private NetworkUtils(){
    }//private because util class

    public synchronized static NetworkUtils getInstance() {
        if(mInstance == null) {
            mInstance = new NetworkUtils();
        }

        return mInstance;
    }

    /**
     * Converts a hashmap of values to url encoded form for requests.
     * @param params Hashmap to be converted
     * @return String containing the converted hashmap
     */
    public String convertToUrlEncodedPostBody(Map<String, String> params){
        StringBuilder sbPost = new StringBuilder();
        if(params != null) {
            int count = 0;
            for (String key : params.keySet()) {
                if (params.get(key) != null) {
                    if(count != 0) {
                        sbPost.append("&");
                    }
                    sbPost.append(key);
                    sbPost.append("=");
                    sbPost.append(params.get(key));
                    count++;
                }
            }
        }
        return sbPost.toString();
    }

    /**
     * Stores the json response token from the server to a local singleton for universal access
     * @param response The Json object from the server
     */
    public void storeTokens(JSONObject response){
        String accessToken;
        String refreshToken;

        try {

            accessToken = response.getString("access_token");
            refreshToken = response.getString("refresh_token");

            AuthToken.getInstance().setAccessToken(accessToken);
            AuthToken.getInstance().setRefreshToken(refreshToken);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
