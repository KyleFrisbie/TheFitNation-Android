package com.fitnation.profile;


import android.content.Context;
import android.util.Log;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.model.UserWeight;
import com.fitnation.model.enums.SkillLevel;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.UserLogins;
import com.fitnation.networking.tasks.PostUserWeightTask;
import com.fitnation.networking.tasks.UserDemographicTask;
import com.fitnation.networking.tasks.UserTask;
import com.fitnation.networking.tasks.UserWeightTask;
import com.fitnation.profile.callbacks.PutUserWeightCallback;
import com.fitnation.profile.callbacks.UserCallback;
import com.fitnation.profile.callbacks.UserDemographicsCallback;
import com.fitnation.workout.callbacks.GetSkillLevelsCallback;
import com.fitnation.workout.parent.GetSkillLevelsTask;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by Jeremy on 2/26/2017.
 */

public class ProfileDataManager extends DataManager {

    String TAG = ProfileDataManager.class.getSimpleName();

    RequestQueue mRequestQueue;
    static List<SkillLevel> mSkillLevelList;
    String mAuthToken;

    public ProfileDataManager(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
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

    public Long getSkillLevelId(String skillLevel){
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
                        Log.d("PROFILE DATA MANAGER", error.toString() +
                        " Failed to get Skill Levels from web");
                    }
                });
            }
        }).start();
    }

    public void saveUserToWeb(final User user){
        user.setId(Long.parseLong(UserLogins.getUserId()));

        user.setLogin(UserLogins.getUserLogin());
        final UserTask putUserTask = new UserTask(mAuthToken, mRequestQueue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                putUserTask.putUser(user, new UserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        Log.i(TAG, "Successfully save user to Web");
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.d(TAG, error.toString() +
                                " Failed to Save User to Web");
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
                                Log.d(TAG, error.toString() +
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
                postUserWeightTask.postUserWeight(userWeight, new PutUserWeightCallback() {
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
}

