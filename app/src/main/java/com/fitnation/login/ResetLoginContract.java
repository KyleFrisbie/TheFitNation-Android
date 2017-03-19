package com.fitnation.login;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * Created by Ryan on 1/31/2017.
 */

public interface ResetLoginContract {
    interface View extends BaseView<Presenter> {
        void showProgress(String message);
        void showAuthError(String errorMessage);
    }

    interface Presenter extends BasePresenter {
        void onResetPasswordButtonPressed(String email);
    }
}
