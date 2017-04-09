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
        mUserWeight.setUserDemographicId(10L);
        mUserWeightClone.setUserDemographicId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mUserWeight.equals(mUserWeightClone));
        assertTrue(mUserWeight.equals(mUserWeight));
        assertFalse(mUserWeight.equals(null));
        assertFalse(new UserWeight().equals(mUserWeight));
        assertFalse(mUserWeight.equals(new String()));
    }

    @Test
    public void testHashCode() throws Exception {
        UserWeight userExercise = new UserWeight();

        userExercise.setUserDemographicId(20L);

        assertEquals(mUserWeight.hashCode(), mUserWeightClone.hashCode());
        assertNotEquals(mUserWeight.hashCode(), userExercise.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mUserWeight.toString());
    }

}