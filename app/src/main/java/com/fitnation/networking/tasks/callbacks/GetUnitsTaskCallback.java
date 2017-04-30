package com.fitnation.networking.tasks.callbacks;

import com.fitnation.model.Unit;

import java.util.List;

/**
 * Created by Ryan Newsom on 4/9/17. *
 */
public interface GetUnitsTaskCallback {
    void onSuccess(List<Unit> skillLevels);
    void onFailure(String error);
}
