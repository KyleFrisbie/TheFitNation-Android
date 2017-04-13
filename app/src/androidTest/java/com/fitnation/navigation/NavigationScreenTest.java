package com.fitnation.navigation;

import android.os.SystemClock;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fitnation.R;
import com.fitnation.base.InstrumentationTest;

import org.junit.After;
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
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationScreenTest extends InstrumentationTest{

    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule = new ActivityTestRule<NavigationActivity>(NavigationActivity.class);

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
        onView(withId(R.id.content_main_container)).check(matches(isDisplayed()));
    }

    @Test
    public void onNavDrawerOpenedAllMenuItemsDisplayed() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        onNavStartWorkoutPressed();
        onNavMyWorkoutPressed();
        onNavTrendsPressed();
        onNavWorkoutRegimensPressed();
        onNavMyProfilePressed();
        onNavLogoutPressed();
    }

    public void onNavStartWorkoutPressed(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        onView(withText("Start Workout")).perform(click());
    }

    public void onNavMyWorkoutPressed(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        onView(withText("My Workouts")).perform(click());
    }

    public void onNavTrendsPressed(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        onView(withText("Trends")).perform(click());
    }

    public void onNavWorkoutRegimensPressed(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        onView(withText("Workout Regimens")).perform(click());
    }

    public void onNavMyProfilePressed(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        onView(withText("My Profile")).perform(click());
    }

    public void onNavLogoutPressed(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        onView(withText("Logout")).perform(click());
        onView(withId(R.id.activity_login)).check(matches(isDisplayed()));
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.signUp_button)).check(matches(isDisplayed()));
        onView(withId(R.id.email_editText)).check(matches(isDisplayed()));
        onView(withId(R.id.password_editText)).check(matches(isDisplayed()));
        onView(withId(R.id.signUp_textView)).check(matches(isDisplayed()));
        onView(withId(R.id.logo_imageView)).check(matches(isDisplayed()));
    }

    @Test
    public void onNavItemCreateWorkoutSelected() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText(R.string.build_workout)).check(matches(isDisplayed()));
        onView(withText(R.string.build_workout)).perform(ViewActions.click());

        //TODO mock web service
//        SystemClock.sleep(1000);
//        onView(withId(R.id.tabs)).check(matches(isDisplayed()));
//        onView(withText(R.string.beginner)).check(matches(isDisplayed()));
//        onView(withText(R.string.intermediate)).check(matches(isDisplayed()));
//        onView(withText(R.string.advanced)).check(matches(isDisplayed()));
    }
}
