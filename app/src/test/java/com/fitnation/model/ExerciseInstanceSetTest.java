package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class ExerciseInstanceSetTest {
    private ExerciseInstanceSet mExerciseInstanceSet;
    private ExerciseInstanceSet mExerciseInstanceSetClone;

    @Before
    public void testSetUp() throws Exception {
        mExerciseInstanceSet = new ExerciseInstanceSet();
        mExerciseInstanceSetClone = new ExerciseInstanceSet();
        mExerciseInstanceSet.setId(10L);
        mExerciseInstanceSetClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mExerciseInstanceSet.equals(mExerciseInstanceSetClone));
        assertTrue(mExerciseInstanceSet.equals(mExerciseInstanceSet));
        assertFalse(mExerciseInstanceSet.equals(null));
        assertFalse(new ExerciseInstanceSet().equals(mExerciseInstanceSet));
        assertFalse(mExerciseInstanceSet.equals(new String()));
    }

    @Test
    public void testHashCode() throws Exception {
        ExerciseInstanceSet ExerciseInstanceSet = new ExerciseInstanceSet();
        ExerciseInstanceSet.setId(20L);

        assertEquals(mExerciseInstanceSet.hashCode(), mExerciseInstanceSetClone.hashCode());
        assertNotEquals(mExerciseInstanceSet.hashCode(), ExerciseInstanceSet.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mExerciseInstanceSet.toString());
    }

}