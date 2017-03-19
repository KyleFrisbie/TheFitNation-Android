package com.fitnation.login;

import android.os.SystemClock;
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
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginScreenTest extends InstrumentationTest {

    @Rule
    public ActivityTestRule<LoginBaseActivity> mActivityRule = new ActivityTestRule<>(LoginBaseActivity.class);

    @Before
    public void setUp() {
        super.unlockScreen(mActivityRule.getActivity());
    }

    @After
    public void tearDown() {
        super.tearDown(mActivityRule.getActivity());
    }

    @Test
    public void testLoginFlow() {
        loginScreenIsDisplayed();
        onView(withId(R.id.email_editText)).perform(typeText("testemail@android.com"));
        onView(withId(R.id.password_editText)).perform(typeText("Pa55w0rd"));
        pressBack();
        onView(withId(R.id.login_button)).perform(click());
        SystemClock.sleep(500);
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("500: Internal Server Error")))
                .check(matches(isDisplayed()));
    }


    //test needs to be reworked to sign up user and then delete same user after completion of test
    @Test
    public void testSignUpFlow(){
        loginScreenIsDisplayed();
        onView(withId(R.id.signUp_button)).perform(click());
        registerScreenIsDisplayed();
        onView(withId(R.id.registerEmail_editText)).perform(typeText("testregisteremail@android.com"));
        onView(withId(R.id.registerPassword_editText)).perform(typeText("Pa55w0rd"));
        onView(withId(R.id.userName_editText)).perform(typeText("androidtest"));
        pressBack();
        onView(withId(R.id.register_button)).perform(click());
        SystemClock.sleep(500);
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("400")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testForgotLoginFlow(){
        loginScreenIsDisplayed();
        onView(withId(R.id.forgot_login_button)).perform(click());
        forgotLoginScreenIsDisplayed();
        onView(withId(R.id.resetPassword_editText)).perform(typeText("testemail@android.com"));
        pressBack();
        onView(withId(R.id.resetPassword_button)).perform(click());
        SystemClock.sleep(500);
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("e-mail was sent")))
                .check(matches(isDisplayed()));
        pressBack();
    }


    @Test
    public void testGoogleSignInFlow(){
        loginScreenIsDisplayed();
        onView(withId(R.id.google_login_button)).perform(click());
    }

    @Test
    public void testFacebookSignInFlow(){
        loginScreenIsDisplayed();
        onView(withId(R.id.facebook_login_button)).perform(click());
    }

    public void navigationScreenIsDisplayed(){
        SystemClock.sleep(500);
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
    }

    public void loginScreenIsDisplayed() {
        SystemClock.sleep(500);
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

    public void registerScreenIsDisplayed(){
        SystemClock.sleep(500);
        onView(withId(R.id.register_button)).check(matches(isDisplayed()));
        onView(withId(R.id.registerEmail_editText)).check(matches(isDisplayed()));
        onView(withId(R.id.registerPassword_editText)).check(matches(isDisplayed()));
        onView(withId(R.id.userName_editText)).check(matches(isDisplayed()));
    }

    public void forgotLoginScreenIsDisplayed(){
        SystemClock.sleep(500);
        onView(withId(R.id.resetPassword_button)).check(matches(isDisplayed()));
        onView(withId(R.id.resetPassword_editText)).check(matches(isDisplayed()));
    }
}
