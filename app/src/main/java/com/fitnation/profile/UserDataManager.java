package com.fitnation.profile;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.model.UserDemographic;
import com.fitnation.networking.JsonParser;
import com.fitnation.base.FitNationApplication;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.*;

import org.json.JSONObject;


/**
 * Created by Jeremy on 2/26/2017.
 */

public class UserDataManager extends DataManager {


    public void SaveProfileData(final UserDemographic userDemographic){

        //save data to local data store
        saveData(userDemographic, new DataResult() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });


    }


}
