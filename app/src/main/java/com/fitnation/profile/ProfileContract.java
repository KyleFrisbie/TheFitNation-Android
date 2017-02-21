package com.fitnation.profile;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.login.LoginContract;


public interface ProfileContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void onAgePressed();
        void onPhotoPressed();
        void onWeightPressed();
        void onHeightPressed();
        void onUserNamePressed();
        void onLifterTypePressed();

    }
}

