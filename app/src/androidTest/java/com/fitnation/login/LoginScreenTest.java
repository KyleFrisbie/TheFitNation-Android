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

// TODO: create mock objects for volley responses from server
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginScreenTest extends InstrumentationTest {
    private final int DELAY_TIME = 500;

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
    public void testLoginFlowFor400Error() {
        loginScreenIsDisplayed();
        onView(withId(R.id.email_editText)).perform(typeText("badusername@email.com"));
        onView(withId(R.id.password_editText)).perform(typeText("Pa55w0rd"));
        pressBack();
        onView(withId(R.id.login_button)).perform(click());
        SystemClock.sleep(DELAY_TIME);
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testLoginFlowForSuccess(){
        loginScreenIsDisplayed();
        onView(withId(R.id.email_editText)).perform(typeText("androidtest"));
        onView(withId(R.id.password_editText)).perform(typeText("Pa55w0rd"));
        pressBack();
        onView(withId(R.id.login_button)).perform(click());
        SystemClock.sleep(DELAY_TIME);
        navigationScreenIsDisplayed();
    }

    @Test
    public void testSignUpFlowFor400Error(){
        loginScreenIsDisplayed();
        onView(withId(R.id.signUp_button)).perform(click());
        registerScreenIsDisplayed();
        onView(withId(R.id.registerEmail_editText)).perform(typeText("testemail@android.com"));
        onView(withId(R.id.registerPassword_editText)).perform(typeText("Pa55w0rd"));
        onView(withId(R.id.userName_editText)).perform(typeText("android"));
        pressBack();
        onView(withId(R.id.register_button)).perform(click());
        SystemClock.sleep(DELAY_TIME);
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testForgotLoginFlowFor400Error(){
        loginScreenIsDisplayed();
        onView(withId(R.id.forgot_login_button)).perform(click());
        forgotLoginScreenIsDisplayed();
        onView(withId(R.id.resetPassword_editText)).perform(typeText("bademail@badrequest.com"));
        pressBack();
        onView(withId(R.id.resetPassword_button)).perform(click());
        SystemClock.sleep(DELAY_TIME);
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testForgotLoginFlowForSuccess(){
        loginScreenIsDisplayed();
        onView(withId(R.id.forgot_login_button)).perform(click());
        forgotLoginScreenIsDisplayed();
        onView(withId(R.id.resetPassword_editText)).perform(typeText("testemail@android.com"));
        pressBack();
        onView(withId(R.id.resetPassword_button)).perform(click());
        SystemClock.sleep(DELAY_TIME);
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("e-mail was sent")))
                .check(matches(isDisplayed()));
    }

    public void navigationScreenIsDisplayed(){
        SystemClock.sleep(DELAY_TIME);
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
    }

    public void loginScreenIsDisplayed() {
        SystemClock.sleep(DELAY_TIME);
        onView(withId(R.id.activity_login)).check(matches(isDisplayed()));
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.signUp_button)).check(matches(isDisplayed()));
        onView(withId(R.id.email_editText)).check(matches(isDisplayed()));
        onView(withId(R.id.password_editText)).check(matches(isDisplayed()));
        onView(withId(R.id.signUp_textView)).check(matches(isDisplayed()));
        onView(withId(R.id.logo_imageView)).check(matches(isDisplayed()));
    }

    public void registerScreenIsDisplayed(){
        SystemClock.sleep(DELAY_TIME);
        onView(withId(R.id.register_button)).check(matches(isDisplayed()));
        onView(withId(R.id.registerEmail_editText)).check(matches(isDisplayed()));
        onView(withId(R.id.registerPassword_editText)).check(matches(isDisplayed()));
        onView(withId(R.id.userName_editText)).check(matches(isDisplayed()));
    }

    public void forgotLoginScreenIsDisplayed(){
        SystemClock.sleep(DELAY_TIME);
        onView(withId(R.id.resetPassword_button)).check(matches(isDisplayed()));
        onView(withId(R.id.resetPassword_editText)).check(matches(isDisplayed()));
    }
}
