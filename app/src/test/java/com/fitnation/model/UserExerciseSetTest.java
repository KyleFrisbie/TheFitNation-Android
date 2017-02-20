package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class UserExerciseSetTest {
    private UserExerciseSet mUserExerciseSet;
    private UserExerciseSet mUserExerciseSetClone;

    @Before
    public void testSetUp() throws Exception {
        mUserExerciseSet = new UserExerciseSet();
        mUserExerciseSetClone = new UserExerciseSet();
        mUserExerciseSet.setId(10L);
        mUserExerciseSetClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mUserExerciseSet.equals(mUserExerciseSetClone));
        assertTrue(mUserExerciseSet.equals(mUserExerciseSet));
        assertFalse(mUserExerciseSet.equals(null));
        assertFalse(new UserExerciseSet().equals(mUserExerciseSet));
        assertFalse(mUserExerciseSet.equals(new String()));
    }

    @Test
    public void testHashCode() throws Exception {
        UserExerciseSet userExerciseSet = new UserExerciseSet();

        userExerciseSet.setId(20L);

        assertEquals(mUserExerciseSet.hashCode(), mUserExerciseSetClone.hashCode());
        assertNotEquals(mUserExerciseSet.hashCode(), userExerciseSet.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mUserExerciseSet.toString());
    }

}