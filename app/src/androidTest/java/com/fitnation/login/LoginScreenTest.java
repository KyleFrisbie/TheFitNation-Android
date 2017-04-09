package com.fitnation.login;

import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fitnation.R;
import com.fitnation.base.InstrumentationTest;
import com.fitnation.networking.AuthToken;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.io.IOException;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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

    @BeforeClass
    public static void mockServerForSuccess() throws IOException {
        final MockWebServer mockWebServer = new MockWebServer();

        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                switch (request.getPath()) {

                    case "/oauth/token":

                        if (request.getBody().toString().contains("androidtest") ||
                                request.getBody().toString().contains("Pa55w0rd")) {
                            return new MockResponse().setResponseCode(200).setBody("{\n" +
                                    "  \"access_token\": \"2185a8f2-8c21-4b78-a271-5429a3138f49\",\n" +
                                    "  \"token_type\": \"bearer\",\n" +
                                    "  \"refresh_token\": \"93bcd68d-6b0e-49fd-a3a7-819866794bab\",\n" +
                                    "  \"expires_in\": 1799,\n" +
                                    "  \"scope\": \"read write\"\n" +
                                    "}");
                        } else if(request.getBody().toString().contains("93bcd68d-6b0e-49fd-a3a7-819866794bab")){
                            return new MockResponse().setResponseCode(200).setBody("{\n" +
                                    "  \"access_token\": \"2185a8f2-8c21-4b78-a271-5429a3138f49\",\n" +
                                    "  \"token_type\": \"bearer\",\n" +
                                    "  \"refresh_token\": \"93bcd68d-6b0e-49fd-a3a7-819866794bab\",\n" +
                                    "  \"expires_in\": 1799,\n" +
                                    "  \"scope\": \"read write\"\n" +
                                    "}");
                        }else {
                            return new MockResponse().setResponseCode(400).setBody("{\n" +
                                    "  \"error\": \"invalid_grant\",\n" +
                                    "  \"error_description\": \"Bad credentials\"\n" +
                                    "}");
                        }

                    case "/api/account/reset_password/init":

                        if (request.getBody().toString().contains("testemail@android.com")) {
                            return new MockResponse().setResponseCode(200).setBody("e-mail was sent");
                        } else if(request.getBody().toString().contains("401errortest")){
                            return new MockResponse().setResponseCode(401).setBody("{\n" +
                                    "  \"error\": \"invalid_token\",\n" +
                                    "  \"error_description\": \"Invalid access token: ac4cc37a-d3b2-428d-8660-069676a00976\"\n" +
                                    "}");
                        }else {
                            return new MockResponse().setResponseCode(400).setBody("e-mail address not registered");
                        }

                    case "/api/register":

                        if (request.getBody().toString().contains("testemail@android.com")) {
                            return new MockResponse().setResponseCode(201).setBody("");
                        } else {
                            return new MockResponse().setResponseCode(400).setBody("login already in use");
                        }

                    default:

                        return new MockResponse().setResponseCode(404);
                }
            }
        };
        mockWebServer.setDispatcher(dispatcher);
        mockWebServer.start();
        Environment environment = new Environment(mockWebServer.url("").toString());
        EnvironmentManager.getInstance().setEnvironment(environment);
        SystemClock.sleep(500);
    }

    @Test
    public void testLoginFlowFor400Error() {
        loginScreenIsDisplayed();
        onView(withId(R.id.email_editText)).perform(typeText("bad@email.com"));
        onView(withId(R.id.password_editText)).perform(typeText("fsaf"));
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
        onView(withId(R.id.registerEmail_editText)).perform(typeText("bad@email.com"));
        onView(withId(R.id.registerPassword_editText)).perform(typeText("Pa55w0rd"));
        onView(withId(R.id.userName_editText)).perform(typeText("bad"));
        pressBack();
        onView(withId(R.id.register_button)).perform(click());
        SystemClock.sleep(DELAY_TIME);
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testSignUpFlowForSuccess(){
        loginScreenIsDisplayed();
        onView(withId(R.id.signUp_button)).perform(click());
        registerScreenIsDisplayed();
        onView(withId(R.id.registerEmail_editText)).perform(typeText("testemail@android.com"));
        onView(withId(R.id.registerPassword_editText)).perform(typeText("Pa55w0rd"));
        onView(withId(R.id.userName_editText)).perform(typeText("androidtest"));
        pressBack();
        onView(withId(R.id.register_button)).perform(click());
        SystemClock.sleep(DELAY_TIME);
        onView((withText("Success!"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testForgotLoginFlowFor400Error(){
        loginScreenIsDisplayed();
        onView(withId(R.id.forgot_login_button)).perform(click());
        forgotLoginScreenIsDisplayed();
        onView(withId(R.id.resetPassword_editText)).perform(typeText("bad@email.com"));
        pressBack();
        onView(withId(R.id.resetPassword_button)).perform(click());
        SystemClock.sleep(DELAY_TIME);
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testForgotLoginFlowFor401Error(){
        AuthToken.getInstance().setRefreshToken("93bcd68d-6b0e-49fd-a3a7-819866794bab");
        loginScreenIsDisplayed();
        onView(withId(R.id.forgot_login_button)).perform(click());
        forgotLoginScreenIsDisplayed();
        onView(withId(R.id.resetPassword_editText)).perform(typeText("401errortest"));
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
        onView((withText("Success"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
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
