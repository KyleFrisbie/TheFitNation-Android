package com.fitnation.navigation;

import android.os.SystemClock;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fitnation.R;
import com.fitnation.base.InstrumentationTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationScreenTest extends InstrumentationTest{

    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule = new ActivityTestRule(NavigationActivity.class);

    @Before
    public void setUp() {
        super.unlockScreen(mActivityRule.getActivity());
    }

    @After
    public void tearDown() {
        super.tearDown(mActivityRule.getActivity());
    }

    @Test
    public void navigationActivityDisplayed() {
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.content_navigation)).check(matches(isDisplayed()));
        onView(withText(R.string.main_container)).check(matches(isDisplayed()));
    }

    @Test
    public void onNavDrawerOpenedAllMenuItemsDisplayed() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        onNavStartWorkoutPressed();
        onNavMyWorkoutPressed();
        onNavTrendsPressed();
        onNavWorkoutRegiminsPressed();
        onNavMyProfilePressed();
        onNavLogoutPressed();
    }

    public void onNavStartWorkoutPressed(){
        onView(withId(R.id.nav_start_workout)).perform(click());
    }

    public void onNavMyWorkoutPressed(){
        onView(withId(R.id.nav_my_workouts)).perform(click());
    }

    public void onNavTrendsPressed(){
        onView(withId(R.id.nav_trends)).perform(click());
    }

    public void onNavWorkoutRegiminsPressed(){
        onView(withId(R.id.nav_workout_regimens)).perform(click());
    }

    public void onNavMyProfilePressed(){
        onView(withId(R.id.nav_my_profile)).perform(click());
    }

    public void onNavLogoutPressed(){
        onView(withId(R.id.nav_logout)).perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.activity_login)).check(matches(isDisplayed()));
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.signUp_button)).check(matches(isDisplayed()));
        onView(withId(R.id.facebook_login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.google_login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.email_editText)).check(matches(isDisplayed()));
        onView(withId(R.id.password_editText)).check(matches(isDisplayed()));
        onView(withId(R.id.signUp_textView)).check(matches(isDisplayed()));
        onView(withId(R.id.logo_imageView)).check(matches(isDisplayed()));
    }
}
