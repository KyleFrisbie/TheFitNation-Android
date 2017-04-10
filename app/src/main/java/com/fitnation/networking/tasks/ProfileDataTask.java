package com.fitnation.networking.tasks;

import android.util.Log;

import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.model.UserWeight;
import com.fitnation.profile.ProfileFragment;

/**
 * Created by Jeremy on 4/9/2017.
 *
 * This class just handles the 3 different requests for a profile page
 * User Demographic, User, and User Weight Data.
 */

public final class ProfileDataTask {

    private ProfileDataTask(){}

    /*
    Save all of the profile data to web
     */
    public static void saveProfileData(ProfileFragment fragment){
        UserDemographic userdemo = fragment.userdemo;
        UserWeight userWeight = fragment.userWeight;
        User user = fragment.user;

        if(userdemo==null|| userWeight==null||user==null) {
            Log.d("PROFILE DATA", "Some profile data is null, not saving");
            return;
        }




    }

}
