package com.fitnation.networking;

import com.fitnation.model.Exercise;
import com.fitnation.model.Muscle;
import com.fitnation.utils.FileUtils;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import io.realm.RealmList;

import static org.junit.Assert.*;

/**
 *
 */
public class JsonParserTest {
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
        Muscle biceps = new Muscle();
        biceps.setId(3L);
        biceps.setName("biceps");
        Exercise EXPECTED = new Exercise();
        EXPECTED.setId(93345L);
        EXPECTED.setName("Bicep Curl");
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("exercise.json");
        String json = FileUtils.readTextFile(in);

        Exercise bicepCurl = JsonParser.convertJsonStringToPojo(json, Exercise.class);

        assertNotNull(bicepCurl);
        assertEquals(EXPECTED, bicepCurl);

    }

    @Test
    public void convertPojoToJsonString() throws Exception {
        RealmList<Muscle> musclesList = new RealmList<>();
        Muscle biceps = new Muscle();
        biceps.setId(3L);
        biceps.setName("biceps");
        musclesList.add(biceps);

        Exercise bicepCurl = new Exercise();
        bicepCurl.setId(93345L);
        bicepCurl.setName("Bicep Curl");
        bicepCurl.setMuscles(musclesList);

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("exercise.json");
        String json = FileUtils.readTextFile(in);

        String actual = JsonParser.convertPojoToJsonString(bicepCurl);

        assertEquals(json, actual);
    }
}