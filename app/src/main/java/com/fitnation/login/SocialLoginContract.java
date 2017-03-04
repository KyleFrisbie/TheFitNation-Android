package com.fitnation.login;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * Created by Ryan on 1/31/2017.
 */

public interface SocialLoginContract {
    interface View extends BaseView<Presenter> {
        void showProgress();
        void showAuthError(String errorMessage);
    }

    interface Presenter extends BasePresenter {
        void onFacebookLoginPressed();
        void onGoogleLoginPressed();
        void onGitHubLoginPressed();
        void onTwitterLoginPressed();
    }
}
