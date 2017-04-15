package com.fitnation.viewWorkouts.pastWorkouts;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.login.LoginContract;

/**
 * Created by Erik on 4/15/2017.
 */

public interface PastWorkoutsContract {
    interface View extends BaseView<LoginContract.Presenter> {
    }

    interface Presenter extends BasePresenter {

    }
}
