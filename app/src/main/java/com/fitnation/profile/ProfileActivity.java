package com.fitnation.profile;

import android.os.Bundle;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;

/**
 * Created by Jeremy on 2/21/2017.
 */

public class ProfileActivity extends BaseActivity{
    private static final int VIEW_CONTAINER = R.id.activity_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        launchProfileFragment();
    }

    private void launchProfileFragment() {
        ProfileFragment profileFragment = ProfileFragment.newInstance();
        profileFragment.setPresenter(new ProfilePresenter(profileFragment));
        getSupportFragmentManager().beginTransaction()
                .replace(VIEW_CONTAINER, profileFragment).commit();
    }
}
