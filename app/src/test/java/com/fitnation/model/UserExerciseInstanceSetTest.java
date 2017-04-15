package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class UserExerciseInstanceSetTest {
    private UserExerciseInstanceSet mUserExerciseInstanceSet;
    private UserExerciseInstanceSet mUserExerciseInstanceSetClone;

    @Before
    public void testSetUp() throws Exception {
        mUserExerciseInstanceSet = new UserExerciseInstanceSet();
        mUserExerciseInstanceSetClone = new UserExerciseInstanceSet();
        mUserExerciseInstanceSet.setId(10L);
        mUserExerciseInstanceSetClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mUserExerciseInstanceSet.equals(mUserExerciseInstanceSetClone));
        assertTrue(mUserExerciseInstanceSet.equals(mUserExerciseInstanceSet));
        assertFalse(mUserExerciseInstanceSet.equals(null));
        assertFalse(new UserExerciseInstanceSet().equals(mUserExerciseInstanceSet));
        assertFalse(mUserExerciseInstanceSet.equals(new String()));
    }

    @Test
    public void testHashCode() throws Exception {
        UserExerciseInstanceSet userExerciseInstanceSet = new UserExerciseInstanceSet();

        userExerciseInstanceSet.setId(20L);

        assertEquals(mUserExerciseInstanceSet.hashCode(), mUserExerciseInstanceSetClone.hashCode());
        assertNotEquals(mUserExerciseInstanceSet.hashCode(), userExerciseInstanceSet.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mUserExerciseInstanceSet.toString());
    }

}