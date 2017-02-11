package com.fitnation.intro;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fitnation.R;
import com.fitnation.base.InstrumentationTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SplashScreenTest extends InstrumentationTest{

    @Rule
    public ActivityTestRule<SplashScreenActivity> mActivityRule = new ActivityTestRule(SplashScreenActivity.class);

    @Before
    public void setUp() {
        super.unlockScreen(mActivityRule.getActivity());
    }

    @Test
    public void splashScreenIsDisplayed() {
        onView(ViewMatchers.withText(R.string.title_activity_splash_screen)).check(matches(isDisplayed()));
    }
}