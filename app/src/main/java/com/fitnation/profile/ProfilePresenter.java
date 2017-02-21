package com.fitnation.profile;



public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View mView;
    private ProfileModel mModel;

    public ProfilePresenter (ProfileContract.View view) {
        mView = view;
        //mModel = mView;
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
    public void onWeightPressed() {

    }

    @Override
    public void onHeightPressed() {

    }

    @Override
    public void onUserNamePressed() {
        //TODO allow changing mUsername?
        //Is mUsername final?
    }

    @Override
    public void onLifterTypePressed() {

    }


}
