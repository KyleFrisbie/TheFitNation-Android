package com.fitnation.profile;

import com.fitnation.model.User;
import com.fitnation.model.UserWeight;
import com.fitnation.model.UserDemographic;
import com.fitnation.networking.VolleyQueueSingleton;
import com.fitnation.networking.tasks.GetUserDemographicTask;

import java.util.List;

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View mView;

    public ProfilePresenter (ProfileContract.View view) {

        mView = view;
    }



    @Override
    public void onViewReady() {}

    @Override
    public void start() {}

    @Override
    public void stop() {}

    UserDemographic userdemo;
    User user;
    UserWeight weight;

    @Override
    public void saveProfileData(ProfileFragment profile) {
        //LOCAL DATE STORE
        ProfileDataManager userDataManager = new ProfileDataManager();
        userdemo = profile.userdemo;
        user = profile.user;
        weight = profile.userWeight;
        userDataManager.SaveUserDemographicData(userdemo);
        userDataManager.SaveUserData(user);
        userDataManager.SaveWeightData(weight);

        saveUserWeightData(weight);

        GetUserDemographicTask.putUserDemographicData(userdemo, mView.getBaseActivity());

    }

    @Override
    public void getProfileData(ProfileFragment fragment) {

    }



    public void saveUserData(User user){
        ProfileDataManager pdm = new ProfileDataManager();
        pdm.SaveUserData(user);
    }

}
