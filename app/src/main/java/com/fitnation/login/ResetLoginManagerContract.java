package com.fitnation.login;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * Interface between view and presenter
 */
public interface ResetLoginManagerContract {
    interface View extends BaseView<Manager> {
        void successfulResponse();
        void errorResponse();
    }

    interface Manager extends BasePresenter {
        void resetPasswordRequest(String email);
    }
}
