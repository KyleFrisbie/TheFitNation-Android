package com.fitnation.login;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fitnation.R;
import com.fitnation.utils.InstrumentationTest;

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

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginScreenTest extends InstrumentationTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule(LoginActivity.class);

    @Before
    public void setUp() {
        super.unlockScreen(mActivityRule.getActivity());
    }

    @Test
    public void loginScreenIsDisplayed() {
        onView(ViewMatchers.withText(R.string.login_screen)).check(matches(isDisplayed()));
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testMainActivityLaunchedUponLogin() {
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
    }
}
