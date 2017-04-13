package com.fitnation.profile.callbacks;

import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;

/**
 * Created by Jeremy on 4/12/2017.
 */

public interface GetUserCallback {
    void onSuccess(User user);
    void onFailure(String error);
}
