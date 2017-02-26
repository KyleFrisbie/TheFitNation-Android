package com.fitnation.login;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * Created by Ryan on 1/31/2017.
 */

public interface ForgotLoginContract {
    interface View extends BaseView<Presenter> {
        void showProgress();
        void showAuthError();
    }

    interface Presenter extends BasePresenter {
        void onResetPasswordButtonPressed(String email);
    }
}
