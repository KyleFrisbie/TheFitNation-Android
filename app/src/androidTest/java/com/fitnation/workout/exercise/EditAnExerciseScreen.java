package com.fitnation.workout.exercise;

import android.support.annotation.Nullable;
import android.support.test.rule.ActivityTestRule;

import com.fitnation.R;
import com.fitnation.base.InstrumentationTest;
import com.fitnation.model.ExerciseView;
import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.navigation.NavigationActivity;
import com.fitnation.networking.JsonParser;
import com.fitnation.utils.FileUtils;
import com.fitnation.navigation.Navigator;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.InputStream;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Ryan Newsom on 4/9/17. *
 */

public class EditAnExerciseScreen extends InstrumentationTest {
    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule = new ActivityTestRule(NavigationActivity.class);

    @Before
    public void setUp() {
        super.unlockScreen(mActivityRule.getActivity());
        InputStream exercisesInputStream = this.getClass().getClassLoader().getResourceAsStream("exercise.json");
        final String exerciseJson = FileUtils.readTextFile(exercisesInputStream);
        Exercise exercise = JsonParser.convertJsonStringToPojo(exerciseJson, Exercise.class);
        ExerciseInstance exerciseInstance = new ExerciseInstance(exercise);

        Navigator.navigateToEditExercise(mActivityRule.getActivity(), exerciseInstance, new OnExerciseUpdatedCallback() {
            @Override
            public void exerciseUpdated(@Nullable ExerciseView updatedExerciseInstance) {

            }
        }, R.id.content_main_container);
    }

    @After
    public void tearDown() {
        super.tearDown(mActivityRule.getActivity());
    }

    @Test
    public void testEditExerciseScreenElementsVisible() throws Exception {
        onView(withId(R.id.reset_button)).check(matches(isDisplayed()));
        onView(withId(R.id.save_button)).check(matches(isDisplayed()));
        onView(withId(R.id.add_set_button)).check(matches(isDisplayed()));
        onView(withId(R.id.reset_button)).check(matches(isDisplayed()));
        onView(withText("Squat")).check(matches(isDisplayed()));
    }
}
