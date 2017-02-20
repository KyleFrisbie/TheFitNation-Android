package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class UserExerciseTest {
    private UserExercise mUserExercise;
    private UserExercise mUserExerciseClone;

    @Before
    public void testSetUp() throws Exception {
        mUserExercise = new UserExercise();
        mUserExerciseClone = new UserExercise();
        mUserExercise.setId(10L);
        mUserExerciseClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mUserExercise.equals(mUserExerciseClone));
        assertTrue(mUserExercise.equals(mUserExercise));
        assertFalse(mUserExercise.equals(null));
        assertFalse(new UserExercise().equals(mUserExercise));
        assertFalse(mUserExercise.equals(new String()));
    }

    @Test
    public void testHashCode() throws Exception {
        UserExercise userExercise = new UserExercise();

        userExercise.setId(20L);

        assertEquals(mUserExercise.hashCode(), mUserExerciseClone.hashCode());
        assertNotEquals(mUserExercise.hashCode(), userExercise.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mUserExercise.toString());
    }
}