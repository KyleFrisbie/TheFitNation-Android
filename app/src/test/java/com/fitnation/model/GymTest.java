package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class GymTest {
    private Gym mGym;
    private Gym mGymClone;

    @Before
    public void testSetUp() throws Exception {
        mGym = new Gym();
        mGymClone = new Gym();
        mGym.setId(10L);
        mGymClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mGym.equals(mGymClone));
    }

    @Test
    public void testHashCode() throws Exception {
        Gym gym = new Gym();
        gym.setId(20L);

        assertEquals(mGym.hashCode(), mGymClone.hashCode());
        assertNotEquals(mGym.hashCode(), gym.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mGym.toString());
    }

}