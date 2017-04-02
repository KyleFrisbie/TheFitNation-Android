package com.fitnation.login;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * Interface between view and presenter
 */
public interface RegisterManagerContract {
    interface View extends BaseView<Manager> {
        void successfulResponse();
        void errorResponse();
    }

    interface Manager extends BasePresenter {
        void requestRegistration(String email, String password, String userName, String language);
    }
}
