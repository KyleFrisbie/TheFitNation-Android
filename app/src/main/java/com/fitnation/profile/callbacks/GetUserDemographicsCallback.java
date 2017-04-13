package com.fitnation.profile.callbacks;

import com.fitnation.model.UserDemographic;
import com.fitnation.model.UserWeight;

import java.util.List;

/**
 * Created by Jeremy on 4/12/2017.
 */

public interface GetUserDemographicsCallback {
    void onSuccess(UserDemographic userDemographic);
    void onFailure(String error);
}
