package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class UserWeightTest {
    private UserWeight mUserWeight;
    private UserWeight mUserWeightClone;

    @Before
    public void testSetUp() throws Exception {
        mUserWeight = new UserWeight();
        mUserWeightClone = new UserWeight();
        mUserWeight.setId(10L);
        mUserWeightClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mUserWeight.equals(mUserWeightClone));
    }

    @Test
    public void testHashCode() throws Exception {
        UserWeight userExercise = new UserWeight();

        userExercise.setId(20L);

        assertEquals(mUserWeight.hashCode(), mUserWeightClone.hashCode());
        assertNotEquals(mUserWeight.hashCode(), userExercise.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mUserWeight.toString());
    }

}