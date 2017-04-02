package com.fitnation.profile;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.login.LoginContract;
import com.fitnation.model.UserDemographic;


public interface ProfileContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void saveData(UserDemographic userDemo);

        void getUserDemographic(ProfileFragment fragment);
    }
}

