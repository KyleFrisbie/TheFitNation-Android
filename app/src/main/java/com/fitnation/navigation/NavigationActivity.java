package com.fitnation.navigation;


import android.os.Build;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.toolbox.Volley;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.profile.ProfileDataManager;
import com.fitnation.profile.ProfileFragment;

import java.util.List;
import com.fitnation.login.LoginBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Handles Navigation for the flyout drawer & its container
 */
public class NavigationActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar) public Toolbar mToolbar;
    @BindView(R.id.drawer_layout) public DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private static final String TAG = NavigationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_activity_navigation);
        ButterKnife.bind(this);
        setUpActionBar();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ProfileDataManager profileDataManager =
                new ProfileDataManager(Volley.newRequestQueue(this));
        profileDataManager.getUserLogins();
    }

    private void setUpActionBar() {
        setSupportActionBar(mToolbar);
        mToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                mToolbar,
                R.string.navigation_drawer_open,  /* "open drawer" description */
                R.string.navigation_drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        /*if (item.isChecked()){
            Log.i(TAG, "Attempted to select an already selected Nav Drawer item");
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }*/

        if (id == R.id.nav_start_workout) {

        } else if (id == R.id.nav_my_workouts) {

        } else if (id == R.id.nav_trends) {

        } else if (id == R.id.nav_workout_regimens) {

        } else if (id == R.id.nav_my_profile) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main_container, ProfileFragment.newInstance())
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_logout){
            Intent loginIntent = new Intent(this, LoginBaseActivity.class);
            startActivity(loginIntent);
            finish();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}