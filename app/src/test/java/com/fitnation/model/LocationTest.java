package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class LocationTest {
    private Location mLocation;
    private Location mLocationClone;

    @Before
    public void testSetUp() throws Exception {
        mLocation = new Location();
        mLocationClone = new Location();
        mLocation.setId(10L);
        mLocationClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mLocation.equals(mLocationClone));
    }

    @Test
    public void testHashCode() throws Exception {
        Location location = new Location();

        location.setId(20L);

        assertEquals(mLocation.hashCode(), mLocationClone.hashCode());
        assertNotEquals(mLocation.hashCode(), location.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mLocation.toString());
    }

}