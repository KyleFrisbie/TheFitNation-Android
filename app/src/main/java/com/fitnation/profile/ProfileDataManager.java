package com.fitnation.profile;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.model.ProfileInstance;
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.model.UserWeight;
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


    public static ProfileInstance getLocalProfileData(){
        ProfileInstance profileInstance = new ProfileInstance();

        profileInstance.addUserDemographicInfo(getLocalUserDemographic());
        profileInstance.addWeight(getLocalUserWeight());
        profileInstance.addUserInfo(getLocalUser());

        return profileInstance;
    }

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
        realm.close();
        if (!userDemoResults.isEmpty()) return userDemoResults.last();
        Log.i("PROFILE", "Realm Result empty for UserDemographic");
        return null;
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
        realm.close();
        if (!userResults.isEmpty()) return userResults.last();
        Log.i("PROFILE", "Realm Result empty for User");
        return null;
    }

    public void SaveWeightData(final UserWeight weight){

        //save data to local data store
        saveData(weight, new DataResult() {
            @Override
            public void onSuccess() {
                Log.i("REALM SAVE", "Weight Saved Successfully to Realm.");
            }

            @Override
            public void onError() {
                Log.d("REALM SAVE", "Error saving Weight to Realm");
            }
        });
    }

    public static UserWeight getLocalUserWeight(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<UserWeight> weightResults =
                realm.where(UserWeight.class).findAll();
        realm.close();
        if (!weightResults.isEmpty()) return weightResults.last();
        Log.i("PROFILE", "Realm Result empty for UserWeight");
        return null;
    }


}

