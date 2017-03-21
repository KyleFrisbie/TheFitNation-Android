package com.fitnation.networking;

import com.fitnation.BuildConfig;

/**
 * Created by Ryan on 3/21/2017.
 */

public class EnvironmentManager {
    private final static String PROD_BASE_URL = "https://the-fit-nation.com/api/";
    private final static String DEBUG_BASE_URL = "http://the-fit-nation-dev.herokuapp.com/api/";

    public static String getRequestUrl(String resourceRoute) {
        if(BuildConfig.DEBUG) {
            return DEBUG_BASE_URL + resourceRoute;
        } else {
            return PROD_BASE_URL + resourceRoute;
        }
    }
}
