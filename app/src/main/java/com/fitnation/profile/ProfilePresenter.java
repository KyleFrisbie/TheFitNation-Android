package com.fitnation.profile;


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
    public void onAgePressed() {
        //open date picker
    }

    @Override
    public void onPhotoPressed() {
        //TODO open to gallery or camera
    }


    @Override
    public void onUserNamePressed() {
        //TODO allow changing mUsername?
        //Is mUsername final?
    }

    @Override
    public void onLifterTypePressed() {

    }

    public void onSave(UserDemographic userDemo){
        UserDataManager userDataManager = new UserDataManager();
        userDataManager.SaveProfileData(userDemo);
    }


}
