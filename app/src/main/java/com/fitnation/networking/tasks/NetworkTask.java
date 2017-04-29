package com.fitnation.networking.tasks;

import com.android.volley.RequestQueue;

/**
 * Created by Ryan on 4/8/2017.
 */

public class NetworkTask {
    protected static String mAuthToken;
    protected RequestQueue mRequestQueue;

    public NetworkTask(String authToken, RequestQueue requestQueue) {
        mAuthToken = authToken;
        mRequestQueue = requestQueue;
    }
}
