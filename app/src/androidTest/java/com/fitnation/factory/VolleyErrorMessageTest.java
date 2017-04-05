package com.fitnation.factory;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AlertDialog;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.fitnation.Factory.VolleyErrorMessage;
import com.fitnation.base.InstrumentationTest;
import com.fitnation.login.LoginBaseActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests if the factory is displaying everything correctly
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class VolleyErrorMessageTest extends InstrumentationTest {
    private VolleyErrorMessage mVolleyErrorMessage;
    private AlertDialog.Builder builder = null;

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

    private void setUp(int responseCode){
        NetworkResponse mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        VolleyError mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);
        //builder = mVolleyErrorMessage.getErrorMessage(mActivityRule.getActivity());
    }

    private Runnable createRunnableAlert(){
        return new Runnable() {
            @Override
            public void run() {
                builder = mVolleyErrorMessage.getErrorMessage(mActivityRule.getActivity());
                builder.show();
            }
        };
    }

    @Test
    public void testMessage100(){
        int responseCode = 100;
        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage101(){
        int responseCode = 101;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage102(){
        int responseCode = 102;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage200(){
        int responseCode = 200;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage201(){
        int responseCode = 201;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage202(){
        int responseCode = 202;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage203(){
        int responseCode = 203;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage204(){
        int responseCode = 204;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage205(){
        int responseCode = 205;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage206(){
        int responseCode = 206;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage207(){
        int responseCode = 207;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage208(){
        int responseCode = 208;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage226(){
        int responseCode = 226;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage300(){
        int responseCode = 300;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage301(){
        int responseCode = 301;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage302(){
        int responseCode = 302;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage303(){
        int responseCode = 303;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage304(){
        int responseCode = 304;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage305(){
        int responseCode = 305;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage306(){
        int responseCode = 306;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage307(){
        int responseCode = 307;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage308(){
        int responseCode = 308;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage400(){
        int responseCode = 400;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage402(){
        int responseCode = 402;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage403(){
        int responseCode = 403;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage404(){
        int responseCode = 404;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage405(){
        int responseCode = 405;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage406(){
        int responseCode = 406;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage407(){
        int responseCode = 407;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage408(){
        int responseCode = 408;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage409(){
        int responseCode = 409;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage410(){
        int responseCode = 410;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage411(){
        int responseCode = 411;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }
    @Test
    public void testMessage412(){
        int responseCode = 412;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }
    @Test
    public void testMessage413(){
        int responseCode = 413;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage414(){
        int responseCode = 414;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage415(){
        int responseCode = 415;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage416(){
        int responseCode = 416;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage417(){
        int responseCode = 417;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage418(){
        int responseCode = 418;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage421(){
        int responseCode = 421;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage422(){
        int responseCode = 422;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage423(){
        int responseCode = 423;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage424(){
        int responseCode = 424;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage426(){
        int responseCode = 426;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage428(){
        int responseCode = 428;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage429(){
        int responseCode = 429;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage431(){
        int responseCode = 431;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage451(){
        int responseCode = 451;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage498(){
        int responseCode = 498;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage499(){
        int responseCode = 499;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage500(){
        int responseCode = 500;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage501(){
        int responseCode = 501;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage502(){
        int responseCode = 502;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage503(){
        int responseCode = 503;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage504(){
        int responseCode = 504;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage505(){
        int responseCode = 505;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage506(){
        int responseCode = 506;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage507(){
        int responseCode = 507;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage508(){
        int responseCode = 508;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage510(){
        int responseCode = 510;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }

    @Test
    public void testMessage511(){
        int responseCode = 511;

        setUp(responseCode);

        mActivityRule.getActivity().runOnUiThread(createRunnableAlert());
        onView((withText("Error"))).check(matches(isDisplayed()));
        onView((withText("OK"))).perform(click());
    }
}
