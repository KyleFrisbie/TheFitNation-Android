package com.fitnation.networking;

import com.fitnation.model.Exercise;
import com.fitnation.model.Muscle;
import com.fitnation.model.enums.ExerciseFamily;
import com.fitnation.utils.FileUtils;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

import static org.junit.Assert.*;

/**
 *
 */
public class ExerciseParsingTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void convertJsonStringToList() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("exercises.json");
        String json = FileUtils.readTextFile(in);

        List<Exercise> exercises = JsonParser.convertJsonStringToList(json, Exercise[].class);

        assertEquals(3, exercises.size());
    }

    @Test
    public void convertJsonStringToPojo() throws Exception {
        Exercise EXPECTED = getExercise();

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("exercise.json");
        String json = FileUtils.readTextFile(in);

        Exercise squat = JsonParser.convertJsonStringToPojo(json, Exercise.class);

        assertNotNull(squat);
        assertEquals(EXPECTED, squat);

    }

    @Test
    public void convertPojoToJsonString() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("exercise-post.json");
        String json = FileUtils.readTextFile(in);
        json = json.replace("\r", "");
        String actual = JsonParser.convertPojoToJsonString(getExercise());

        assertTrue(actual.equalsIgnoreCase(json));
    }

    private Exercise getExercise() {
        List<Muscle> muscles = new ArrayList<>();
        Muscle chest = new Muscle(1103, "Sternal Head", "Chest");
        muscles.add(chest);

        return new Exercise(1651l, "Squat", "http://some.image.io", "Squats are a bitch", "Beginner", muscles, ExerciseFamily.FREE_WEIGHTS);
    }
}