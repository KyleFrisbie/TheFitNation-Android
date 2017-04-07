package com.fitnation.profile;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.networking.JsonParser;
import com.fitnation.base.FitNationApplication;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.*;

import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by Jeremy on 2/26/2017.
 */

public class ProfileDataManager extends DataManager {


    public void SaveUserDemographicData(final UserDemographic userDemographic){

        //save data to local data store
        saveData(userDemographic, new DataResult() {
            @Override
            public void onSuccess() {
                Log.i("REALM SAVE", "User Demographic Saved Successfully to Realm.");
            }

            @Override
            public void onError() {
                Log.d("REALM SAVE", "Error saving User Demographic to Realm");
            }
        });
    }

    public static UserDemographic getLocalUserDemographic(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<UserDemographic> userDemoResults = realm.where(UserDemographic.class).findAll();
        if (!userDemoResults.isEmpty()) return userDemoResults.last();
        Log.i("PROFILE", "Realm Result empty for UserDemographic");
        return new UserDemographic();
    }

    public void SaveUserData(final User user){

        //save data to local data store
        saveData(user, new DataResult() {
            @Override
            public void onSuccess() {
                Log.i("REALM SAVE", "User Saved Successfully to Realm.");
            }

            @Override
            public void onError() {
                Log.d("REALM SAVE", "Error saving User to Realm");
            }
        });
    }

    public static User getLocalUser(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> userResults =
                realm.where(User.class).findAll();

        if (!userResults.isEmpty()) return userResults.last();
        Log.i("PROFILE", "Realm Result empty for User");
        return new User();
    }
    
}

