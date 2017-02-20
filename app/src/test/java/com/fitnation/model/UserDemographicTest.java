package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class UserDemographicTest {
    private UserDemographic mUserDemographic;
    private UserDemographic mUserDemographicClone;

    @Before
    public void testSetUp() throws Exception {
        mUserDemographic = new UserDemographic();
        mUserDemographicClone = new UserDemographic();
        mUserDemographic.setId(10L);
        mUserDemographicClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mUserDemographic.equals(mUserDemographicClone));
    }

    @Test
    public void testHashCode() throws Exception {
        UserDemographic userDemographic = new UserDemographic();

        userDemographic.setId(20L);

        assertEquals(mUserDemographic.hashCode(), mUserDemographicClone.hashCode());
        assertNotEquals(mUserDemographic.hashCode(), userDemographic.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mUserDemographic.toString());
    }

}