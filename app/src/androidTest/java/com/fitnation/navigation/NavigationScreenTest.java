package com.fitnation.navigation;

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
    }

    @Test
    public void onNavItemCreateWorkoutSelected() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText(R.string.build_workout)).check(matches(isDisplayed()));
        onView(withText(R.string.build_workout)).perform(ViewActions.click());

        //should be launched
        onView(withId(R.id.tabs)).check(matches(isDisplayed()));
        onView(withText(R.string.beginner)).check(matches(isDisplayed()));
        onView(withText(R.string.intermediate)).check(matches(isDisplayed()));
        onView(withText(R.string.advanced)).check(matches(isDisplayed()));
    }
}
