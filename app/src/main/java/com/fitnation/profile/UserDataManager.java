package com.fitnation.profile;

import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.model.UserDemographic;

/**
 * Created by Jeremy on 2/26/2017.
 */

public class UserDataManager extends DataManager {


    public void SaveProfileData(final UserDemographic userDemographic){

        //save data to local data store
        saveData(userDemographic, new DataResult() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

        //save data to webservice
    }

}
