package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class UserWorkoutTemplateTest {
    private UserWorkoutInstance mUserWorkoutInstance;
    private UserWorkoutInstance mUserWorkoutInstanceClone;

    @Before
    public void testSetUp() throws Exception {
        mUserWorkoutInstance = new UserWorkoutInstance();
        mUserWorkoutInstanceClone = new UserWorkoutInstance();
        mUserWorkoutInstance.setId(10L);
        mUserWorkoutInstanceClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mUserWorkoutInstance.equals(mUserWorkoutInstanceClone));
    }

    @Test
    public void testHashCode() throws Exception {
        UserWorkoutInstance userWorkoutInstance = new UserWorkoutInstance();

        userWorkoutInstance.setId(20L);

        assertEquals(mUserWorkoutInstance.hashCode(), mUserWorkoutInstanceClone.hashCode());
        assertNotEquals(mUserWorkoutInstance.hashCode(), userWorkoutInstance.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mUserWorkoutInstance.toString());
    }

}