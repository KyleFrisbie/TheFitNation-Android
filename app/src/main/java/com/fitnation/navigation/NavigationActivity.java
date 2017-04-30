package com.fitnation.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.Navigationable;
import com.fitnation.profile.ProfileFragment;
import com.fitnation.workout.parent.ExercisesParentFragment;
import com.fitnation.model.enums.ExerciseAction;

import java.util.List;
import com.fitnation.login.LoginBaseActivity;
import com.fitnation.workoutInstance.parent.WorkoutInstanceParentFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Handles Navigation for the flyout drawer & its container
 */
public class NavigationActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, Navigationable {
    private static final String TAG = NavigationActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    public NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_activity_navigation);
        ButterKnife.bind(this);
        setUpActionBar();
        mNavigationView.setNavigationItemSelectedListener(this);
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
        updateToolbar(false, getString(R.string.fit_nation));
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);

        } else {
            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

            if (backStackCount >= 1) {
                NavigationState navigationState = Navigator.popNavigationState();
                updateBasedOffNavigationState(navigationState);
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
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

        if (id == R.id.nav_start_workout) {

        } else if (id == R.id.nav_my_workouts) {
            if(!item.isChecked()){
                Navigator.navigateToWorkouts(this, R.id.content_main_container);
            }
        } else if (id == R.id.nav_trends) {

        } else if (id == R.id.nav_workout_regimens) {

        } else if (id == R.id.nav_my_profile) {
            if(!item.isChecked()) {
                Navigator.navigateToProfileScreen(this, R.id.content_main_container);
            }
        } else if (id == R.id.nav_logout){
            Intent loginIntent = new Intent(this, LoginBaseActivity.class);
            startActivity(loginIntent);
            finish();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateBasedOffNavigationState(NavigationState navigationState) {
        if(navigationState != null) {
            updateToolbarView(navigationState.isBackArrowShown(), navigationState.getTitle());
        }
    }

    //---------------------------------Navigationable---------------------------------------------//

    @Override
    public void updateToolbar(boolean show, String title) {
        Navigator.addNavigationState(new NavigationState(show, title));

        updateToolbarView(show, title);
    }

    @Override
    public void updateMenuItemSelected(int id) {
        mNavigationView.setCheckedItem(id);
    }

    private void updateToolbarView(boolean show, String title) {
        if (title != null) {
            getSupportActionBar().setTitle(title);
        }
        if(show) {
            mToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mToggle.setDrawerIndicatorEnabled(true);
            mToggle.setToolbarNavigationClickListener(null);
        }
    }
}
