package com.fitnation.login;

import android.app.Activity;
import android.support.test.espresso.matcher.ViewMatchers;
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

import javax.annotation.PreDestroy;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
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

    @After
    public void tearDown() {
        super.tearDown(mActivityRule.getActivity());
    }


    @Test
    public void loginScreenIsDisplayed() {
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.register_button)).check(matches(isDisplayed()));
        onView(withId(R.id.facebook_login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.google_login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.password_textView)).check(matches(isDisplayed()));
        onView(withId(R.id.email_textView)).check(matches(isDisplayed()));
        onView(withId(R.id.email_editText)).check(matches(isDisplayed()));
        onView(withId(R.id.password_editText)).check(matches(isDisplayed()));
    }
    @Test
    public void TestRegisterButtonPressed(){
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.register_button)).check(matches(isDisplayed()));
        onView(withId(R.id.email_editText)).check(matches(isDisplayed()));
        onView(withId(R.id.password_editText)).check(matches(isDisplayed()));
    }

    @Test
    public void testOnGoogleLoginPressed(){
        onView(withId(R.id.google_login_button)).perform(click());
    }

    @Test
    public void testOnFacebookLoginPressed(){
        onView(withId(R.id.facebook_login_button)).perform(click());
    }
    //need to add in correct username and password for login to work
/*
    @Test
    public void testMainActivityLaunchedUponLogin() {
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
    }
*/
}
