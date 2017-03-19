package com.fitnation.login;

import android.widget.Switch;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

public class VolleyErrorMessageGenerator {
    VolleyError volleyError;

    public VolleyErrorMessageGenerator(VolleyError volleyError){
        this.volleyError = volleyError;
    }

    public String GetErrorMessage(){
        String message = GenerateErrorMessage(volleyError.networkResponse);
        return message;
    }

    private String GenerateErrorMessage(NetworkResponse response) {
        String message = String.valueOf(response.statusCode);
        switch (response.statusCode) {
            case 200:
                message = "200: Success";
                        break;
            case 201:
                message = "201: Created";
                break;
            case 401:
                message = "401: Unauthorized Error";
                break;
            case 403:
                message = "403: Forbidden Error";
                break;
            case 404:
                message = "404: Not Found Error";
                break;
            case 500:
                message = "500: Internal Server Error";
                break;
        }
        return message;
    }
}
