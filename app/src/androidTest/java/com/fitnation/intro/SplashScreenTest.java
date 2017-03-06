package com.fitnation.intro;

import android.os.SystemClock;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fitnation.R;
import com.fitnation.base.InstrumentationTest;
import com.fitnation.intro.SplashScreenActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SplashScreenTest extends InstrumentationTest {

    @Rule
    public ActivityTestRule<SplashScreenActivity> mActivityRule = new ActivityTestRule(SplashScreenActivity.class);

    @Before
    public void setUp() {
        super.unlockScreen(mActivityRule.getActivity());
    }

    @After
    public void tearDown() {
        super.tearDown(mActivityRule.getActivity());
    }

    @Test
    public void SplashScreenAppears() {
        onView(withId(R.id.content_splash_screen)).check(matches(isDisplayed()));
    }

    @Test
    public void TestLoginScreenLaunchedAfterDelay() {
        final int LOGIN_LAUNCH_DELAY = 5000;
        SystemClock.sleep(LOGIN_LAUNCH_DELAY);
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_login)).check(matches(isDisplayed()));
    }
}