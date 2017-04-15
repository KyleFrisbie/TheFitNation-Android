package com.fitnation.networking.tasks;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.model.UserWeight;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.JsonParser;
import com.fitnation.profile.ProfilePresenter;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by J on 4/9/2017.
 */

public class PostUserWeightTask extends NetworkTask{

    static List<UserWeight> weightList;
    static UserWeight weight;
    static RequestQueue queue;
    static Environment env;
    static String url;


    PostUserWeightTask(String authToken, RequestQueue queue){
        super(authToken, queue);
    }


}