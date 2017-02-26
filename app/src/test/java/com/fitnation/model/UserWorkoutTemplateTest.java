package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class UserWorkoutTemplateTest {
    private UserWorkoutTemplate mUserWorkoutTemplate;
    private UserWorkoutTemplate mUserWorkoutTemplateClone;

    @Before
    public void testSetUp() throws Exception {
        mUserWorkoutTemplate = new UserWorkoutTemplate();
        mUserWorkoutTemplateClone = new UserWorkoutTemplate();
        mUserWorkoutTemplate.setId(10L);
        mUserWorkoutTemplateClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mUserWorkoutTemplate.equals(mUserWorkoutTemplateClone));
        assertTrue(mUserWorkoutTemplate.equals(mUserWorkoutTemplate));
        assertFalse(mUserWorkoutTemplate.equals(null));
        assertFalse(new UserWorkoutTemplate().equals(mUserWorkoutTemplate));
        assertFalse(mUserWorkoutTemplate.equals(new String()));
    }

    @Test
    public void testHashCode() throws Exception {
        UserWorkoutTemplate userWorkoutTemplate = new UserWorkoutTemplate();

        userWorkoutTemplate.setId(20L);

        assertEquals(mUserWorkoutTemplate.hashCode(), mUserWorkoutTemplateClone.hashCode());
        assertNotEquals(mUserWorkoutTemplate.hashCode(), userWorkoutTemplate.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mUserWorkoutTemplate.toString());
    }

}