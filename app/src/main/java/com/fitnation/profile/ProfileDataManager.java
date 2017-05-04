package com.fitnation.profile;


import android.util.Log;


import com.android.volley.RequestQueue;
import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.model.UserWeight;
import com.fitnation.model.enums.SkillLevel;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.UserLogins;
import com.fitnation.networking.tasks.UserDemographicTask;
import com.fitnation.networking.tasks.UserWeightTask;
import com.fitnation.networking.tasks.callbacks.GetSkillLevelsCallback;
import com.fitnation.profile.callbacks.PutUserWeightCallback;
import com.fitnation.profile.callbacks.UserDemographicsCallback;
import com.fitnation.networking.tasks.GetSkillLevelsTask;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by Jeremy on 2/26/2017.
 */

public class ProfileDataManager extends DataManager {

    static String TAG = ProfileDataManager.class.getSimpleName();

    RequestQueue mRequestQueue;
    static List<SkillLevel> mSkillLevelList;
    String mAuthToken;

    public ProfileDataManager(RequestQueue queue){
        mRequestQueue = queue;
        mAuthToken = AuthToken.getInstance().getAccessToken();

        if (mSkillLevelList==null){
            getSkillLevelList();
        }
    }


    public static ProfileData getLocalProfileData(){
        ProfileData profileData = new ProfileData();

        profileData.addUserDemographicInfo(getLocalUserDemographic());
        profileData.addWeight(getLocalUserWeight());
        profileData.addUserInfo(getLocalUser());

        return profileData;
    }

    public void saveUserDemographicData(final UserDemographic userDemographic){
        if (userDemographic.getId()==null) {
            try{
                userDemographic.setId(Long.parseLong(UserLogins.getInstance().getUserDemographicId()));
                userDemographic.setUserId(Long.parseLong(UserLogins.getInstance().getUserId()));
                userDemographic.setUserLogin(UserLogins.getInstance().getUserLogin());
            } catch (Exception ex){
                Log.d(TAG, ex.toString());
            }
        }
        //save data to local data store
        saveData(userDemographic, new DataResult() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "User Demographic Saved Successfully to Realm.");
            }

            @Override
            public void onError() {
                Log.d(TAG, "Error saving User Demographic to Realm");
            }
        });
    }

    public static UserDemographic getLocalUserDemographic(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<UserDemographic> userDemoResults = realm.where(UserDemographic.class).findAll();

        realm.close();
        if (!userDemoResults.isEmpty()){
            return userDemoResults.last();
        }
        Log.i(TAG, "Realm Result empty for UserDemographic");
        return null;
    }

    public void saveUserData(final User user){

        //save data to local data store
        saveData(user, new DataResult() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "User Saved Successfully to Realm.");
            }

            @Override
            public void onError() {
                Log.d(TAG, "Error saving User to Realm");
            }
        });
    }

    public static User getLocalUser(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> userResults =
                realm.where(User.class).findAll();
        realm.close();
        if (!userResults.isEmpty()){
            return userResults.last();
        }
        Log.i(TAG, "Realm Result empty for User");
        return null;
    }

    public void saveWeightData(final UserWeight weight){

        //save data to local data store
        saveData(weight, new DataResult() {
            @Override
            public void onSuccess() { Log.i(TAG, "Weight Saved Successfully to Realm."); }

            @Override
            public void onError() {
                Log.d("REALM SAVE", "Error saving Weight to Realm");
            }
        });
    }

    public static UserWeight getLocalUserWeight(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<UserWeight> weightResults =
                realm.where(UserWeight.class)
                        .findAll();
        realm.close();
        if (!weightResults.isEmpty()){
            return weightResults.last();
        }
        Log.i(TAG, "Realm Result empty for UserWeight");
        return null;
    }

    public Long getSkillLevelId(String skillLevel){
        if (mSkillLevelList==null){
            Log.d(TAG, "The Skill Level Name-ID list is null.  Can't attain skill level");
            getSkillLevelList();
            return null;
        }
        skillLevel = skillLevel.toLowerCase();
        for(SkillLevel skillId:mSkillLevelList){
            if (skillId.getLevel().toLowerCase().equals(skillLevel))
                return skillId.getId();
        }

        return null;
    }

    public void getSkillLevelList(){
        final GetSkillLevelsTask skillLevelsTask = new GetSkillLevelsTask(
                mAuthToken, mRequestQueue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                skillLevelsTask.getSkillLevels(new GetSkillLevelsCallback() {
                    @Override
                    public void onSuccess(List<SkillLevel> skillLevels) {
                        mSkillLevelList = skillLevels;
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.d(TAG, error.toString() +
                        " Failed to get Skill Levels from web");
                    }
                });
            }
        }).start();
    }


    public void saveUserDemographicToWeb(final UserDemographic userdemo){
        final UserDemographicTask userDemographicTask =
                new UserDemographicTask(mAuthToken, mRequestQueue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                userDemographicTask.putUserDemographicData(userdemo,
                        new UserDemographicsCallback() {
                            @Override
                            public void onSuccess(UserDemographic userDemographic) {
                                Log.i(TAG, "Successfully saved UserDemographic to Web");
                            }

                            @Override
                            public void onFailure(String error) {
                                Log.d(TAG, error +
                                " Failed to Save UserDemographic to Web");
                            }
                        });
            }
        }).start();
    }


    public void saveUserWeightToWeb(final UserWeight userWeight){

        final UserWeightTask postUserWeightTask =
                new UserWeightTask(mAuthToken, mRequestQueue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                postUserWeightTask.putUserWeight(userWeight, new PutUserWeightCallback() {
                    @Override
                    public void onSuccess(UserWeight userWeightList) {
                        Log.i(TAG, "Successfully saved UserWeight to Web");
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.d(TAG, error.toString());
                    }
                });
            }
        }).start();
    }

    public void getUserLogins(){
        final UserDemographicTask getUserDemoTask =
                new UserDemographicTask(mAuthToken, mRequestQueue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getUserDemoTask.getUserDemographicById(new UserDemographicsCallback() {
                    @Override
                    public void onSuccess(UserDemographic userDemographic) {
                        Log.i(TAG, "Updated User Demographic Id, User Id, User Login");
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.d(TAG, error.toString());
                    }
                });
            }
        }).start();
    }

    private static int getUserdemographicId(){
        try {
            int udid = Integer.parseInt(UserLogins.getUserDemographicId());
            return udid;
        } catch (Exception ex){
            Log.d(TAG, ex.toString());
            return 0;
        }
    }

    private static int getUserId(){
        try {
            int uid = Integer.parseInt(UserLogins.getUserId());
            return uid;
        } catch (Exception ex){
            Log.d(TAG, ex.toString());
            return 0;
        }
    }
}

