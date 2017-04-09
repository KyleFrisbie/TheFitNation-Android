package com.fitnation.exercise;

import android.os.SystemClock;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fitnation.R;
import com.fitnation.base.InstrumentationTest;
import com.fitnation.navigation.NavigationActivity;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;
import com.fitnation.utils.FileUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Ryan Newsom on 4/9/17. *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateAWorkoutScreen extends InstrumentationTest {
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
    public void testCreateWorkoutScreenLaunchedOkay() throws Exception {
        final String exercisesUrl = "/api/exercises";
        final String unitsUrl = "/api/units";
        InputStream exercisesInputStream = this.getClass().getClassLoader().getResourceAsStream("exercises.json");
        InputStream unitsInputStream = this.getClass().getClassLoader().getResourceAsStream("units.json");
        final String exerciseJson = FileUtils.readTextFile(exercisesInputStream);
        final String unitsJson = FileUtils.readTextFile(unitsInputStream);
        MockWebServer server = new MockWebServer();
        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                if (request.getPath().equals(exercisesUrl)){
                    return new MockResponse().setBody(exerciseJson);
                }
                else if (request.getPath().equals(unitsUrl)){
                    return new MockResponse().setBody(unitsJson);
                }

                return new MockResponse().setResponseCode(404);
            }
        };
        server.setDispatcher(dispatcher);

        server.start();

        HttpUrl baseUrl = server.url("");
        String url = baseUrl.url().toString();
        EnvironmentManager.getInstance().setEnvironment(new Environment(baseUrl.url().toString()));
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        onView(withText(R.string.build_workout)).check(matches(isDisplayed()));
        onView(withText(R.string.build_workout)).perform(click());

        verifyCreateWorkoutScreenVisible();
        testTabsWork();
//        RecyclerViewActions.actionOnItemAtPosition(0, onView(withText("Edit")).perform(click()));
    }

    private void verifyCreateWorkoutScreenVisible() {
        onView(withId(R.id.tabs)).check(matches(isDisplayed()));
        onView(withText(R.string.beginner)).check(matches(isDisplayed()));
        onView(withText(R.string.intermediate)).check(matches(isDisplayed()));
        onView(withText(R.string.advanced)).check(matches(isDisplayed()));
    }

    private void testTabsWork() {
        onView(withText(R.string.intermediate)).perform(click());
        onView(withText(R.string.beginner)).perform(click());
        onView(withText(R.string.advanced)).perform(click());
    }

    //TODO test able to select exercises on multiple tabs
    //TODO test able to launch edit screen & edit an exercise. Changed are reflected on back pressed
    //TODO in EditExerciseScreenTest edit screen fully. Add set, remove set, edit all values

}
