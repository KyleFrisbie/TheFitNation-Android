package com.fitnation.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
@RunWith(JUnit4.class)
public class ExerciseTest {
    private Exercise mExercise;
    private Exercise mExerciseClone;

    @Before
    public void testSetUp() throws Exception {
        mExercise = new Exercise();
        mExerciseClone = new Exercise();
        mExercise.setId(10L);
        mExerciseClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mExercise.equals(mExerciseClone));
    }

    @Test
    public void testHashCode() throws Exception {
        Exercise exercise = new Exercise();
        exercise.setName("Gladiator workout");

        assertEquals(mExercise.hashCode(), mExerciseClone.hashCode());
        assertNotEquals(mExercise.hashCode(), exercise.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mExercise.toString());
    }

}