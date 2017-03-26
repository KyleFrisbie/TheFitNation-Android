package com.fitnation.profile;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeremy on 3/18/2017.
 */

public class UserDemoStringRequest extends StringRequest {
    public UserDemoStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public UserDemoStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        params.put("Accept", "application/json");
        params.put("Authorization", "Bearer 2cd2ab74-cecd-4a8c-a61a-2b7e83a5313a");
        return params;
    }


}
