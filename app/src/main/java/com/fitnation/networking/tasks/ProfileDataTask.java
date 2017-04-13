package com.fitnation.networking.tasks;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.base.BaseActivity;
import com.fitnation.profile.ProfileData;
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.model.UserWeight;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.JsonParser;
import com.fitnation.networking.UserLogins;
import com.fitnation.profile.ProfilePresenter;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeremy on 4/9/2017.
 *
 * This class just handles the 3 different requests for a profile page
 * User Demographic, User, and User Weight Data.
 */

public final class ProfileDataTask{

    BaseActivity mActivity;

    public ProfileDataTask(BaseActivity activity){
        mActivity = activity;
    }

    /*
    Save all of the profile data to web
     */
    public void saveProfileData(ProfilePresenter profile){
        UserDemographic userdemo = profile.userdemo;
        UserWeight userWeight = profile.userWeight;
        User user = profile.user;

        if(userdemo==null|| userWeight==null||user==null) {
            Log.d("PROFILE DATA", "Some profile data is null, not saving");
            return;
        }

        PutUserDemographicTask.putUserDemographicData(userdemo, profile.getBaseActivity());

    }

    public void getProfileData(final ProfilePresenter presenter){
        RequestQueue queue = Volley.newRequestQueue(mActivity);
        Environment env = EnvironmentManager.getInstance().getCurrentEnvironment();
        String userDemoId = "3154";
        String url = env.getApiUrl()+"user-demographics/" + userDemoId;
        final ProfileData profileData = new ProfileData();
        //final String authToken = AuthToken.getInstance().getAccessToken();
        //TODO CHANGE THIS TO PREVIOUS COMMENTED OUT LINE
        final String authToken = AuthToken.getHardToken();

        //USERDEMOGRAPHIC
        JsonObjectRequest jsonRequestUserDemo =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("GET", response.toString());
                        UserDemographic userdemo = JsonParser.convertJsonStringToPojo(response.toString(), UserDemographic.class);
                        UserLogins.getInstance().setUserDemographicId(String.valueOf(userdemo.getId()));
                        presenter.setDemographic(userdemo);
                        presenter.mProfile.addUserDemographicInfo(userdemo);
                        GetUserTask.getUser(userdemo.getUserLogin(), presenter);
                        GetUserWeightTask.getUserWeight(presenter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GET", error.toString());
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        params.put("Accept", "application/json");
                        params.put("Authorization", "Bearer "+authToken);
                        return params;
                    }
                };

        queue.add(jsonRequestUserDemo);
    }

}
