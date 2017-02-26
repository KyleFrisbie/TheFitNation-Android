package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class ExerciseSetTest {
    private ExerciseSet mExerciseSet;
    private ExerciseSet mExerciseSetClone;

    @Before
    public void testSetUp() throws Exception {
        mExerciseSet = new ExerciseSet();
        mExerciseSetClone = new ExerciseSet();
        mExerciseSet.setId(10L);
        mExerciseSetClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mExerciseSet.equals(mExerciseSetClone));
        assertTrue(mExerciseSet.equals(mExerciseSet));
        assertFalse(mExerciseSet.equals(null));
        assertFalse(new ExerciseSet().equals(mExerciseSet));
        assertFalse(mExerciseSet.equals(new String()));
    }

    @Test
    public void testHashCode() throws Exception {
        ExerciseSet exerciseSet = new ExerciseSet();
        exerciseSet.setId(20L);

        assertEquals(mExerciseSet.hashCode(), mExerciseSetClone.hashCode());
        assertNotEquals(mExerciseSet.hashCode(), exerciseSet.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mExerciseSet.toString());
    }

}