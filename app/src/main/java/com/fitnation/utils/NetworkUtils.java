package com.fitnation.utils;

import java.util.Map;

/**
 * Holds all the utilities for network communications
 */
public class NetworkUtils {

    private NetworkUtils(){
    }//private because util class

    /**
     * Converts a hashmap of values to url encoded form for requests.
     * @param params Hashmap to be converted
     * @return String containing the converted hashmap
     */
    private String convertToUrlEncodedPostBody(Map<String, String> params){
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
}
