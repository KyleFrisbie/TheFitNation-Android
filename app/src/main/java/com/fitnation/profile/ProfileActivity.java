package com.fitnation.profile;

import android.os.Bundle;
import android.util.Log;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Jeremy on 2/21/2017.
 */

public class ProfileActivity extends BaseActivity{
    private static final int VIEW_CONTAINER = R.id.activity_profile;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        launchProfileFragment();
    }


    private void launchProfileFragment() {
        profileFragment = ProfileFragment.newInstance();

        profileFragment.setPresenter(new ProfilePresenter(profileFragment));
        getSupportFragmentManager().beginTransaction()
                .replace(VIEW_CONTAINER, profileFragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            profileFragment.onSaveClicked();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
