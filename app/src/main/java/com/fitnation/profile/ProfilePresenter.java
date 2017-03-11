package com.fitnation.profile;


import android.app.DialogFragment;

import com.fitnation.base.DataResult;
import com.fitnation.model.UserDemographic;

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View mView;
    private UserDemographic userInfo;


    public ProfilePresenter (ProfileContract.View view) {
        mView = view;
    }

    @Override
    public void onViewReady() {}

    @Override
    public void start() {}

    @Override
    public void stop() {}


    @Override
    public void onSaveClicked(UserDemographic userDemo) {
        UserDataManager userDataManager = new UserDataManager();
        userDataManager.SaveProfileData(userDemo);
    }



    public void onSave(UserDemographic userDemo){

    }


}
