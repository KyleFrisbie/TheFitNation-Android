package com.fitnation.workout.view;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fitnation.R;
import com.fitnation.base.InstrumentationTest;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.navigation.NavigationActivity;
import com.fitnation.navigation.Navigator;
import com.fitnation.networking.JsonParser;
import com.fitnation.utils.FileUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Ryan Newsom on 4/23/17. *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateUserWorkoutInstanceScreen extends InstrumentationTest {
    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule = new ActivityTestRule(NavigationActivity.class);

    @Before
    public void setUp() {
        unlockScreen(mActivityRule.getActivity());
        InputStream workoutInstanceInputStream = this.getClass().getClassLoader().getResourceAsStream("workout-instance.json");
        InputStream workoutTemplateInputStream = this.getClass().getClassLoader().getResourceAsStream("workout-template.json");
        String workoutInstanceJson = FileUtils.readTextFile(workoutInstanceInputStream);
        String workoutTemplateJson = FileUtils.readTextFile(workoutTemplateInputStream);
        WorkoutTemplate workoutTemplate = JsonParser.convertJsonStringToPojo(workoutTemplateJson, WorkoutTemplate.class);
        WorkoutInstance workoutInstance = JsonParser.convertJsonStringToPojo(workoutInstanceJson, WorkoutInstance.class);
        UserWorkoutTemplate userWorkoutTemplate = new UserWorkoutTemplate(workoutTemplate, UserWorkoutTemplate.getNextAndroidIdForClass());
        UserWorkoutInstance userWorkoutInstance = new UserWorkoutInstance(workoutInstance, UserWorkoutInstance.getNextAndroidKey());

        Navigator.navigateToEditUserWorkout(mActivityRule.getActivity(), userWorkoutInstance, userWorkoutTemplate, R.id.content_main_container);
    }

    @Test
    public void testUserWorkoutInstanceScreenLoadedOkay() {
        onView(withId(R.id.edit_user_workout_action_button)).check(matches(isDisplayed()));
    }

    //TODO test edit works
    @Test
    public void testUserExerciseCanBeEdited() {

    }
    //TODO test save functionality works
    @Test
    public void testWorkoutInstanceIsSaved() {

    }
}
