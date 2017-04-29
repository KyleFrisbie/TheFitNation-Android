package com.fitnation.profile.callbacks;

import com.fitnation.model.UserWeight;

import java.util.List;

/**
 * Created by Jeremy on 4/12/2017.
 */

public interface GetUserWeightCallback {
    void onSuccess(List<UserWeight> userWeightList);
    void onFailure(String error);
}
