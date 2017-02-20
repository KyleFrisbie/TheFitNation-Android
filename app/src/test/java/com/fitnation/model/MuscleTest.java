package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class MuscleTest {
    private Muscle mMuscle;
    private Muscle mMuscleClone;

    @Before
    public void testSetUp() throws Exception {
        mMuscle = new Muscle();
        mMuscleClone = new Muscle();
        mMuscle.setId(10L);
        mMuscleClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mMuscle.equals(mMuscleClone));
        assertTrue(mMuscle.equals(mMuscle));
        assertFalse(mMuscle.equals(null));
        assertFalse(new Muscle().equals(mMuscle));
        assertFalse(mMuscle.equals(new String()));
    }

    @Test
    public void testHashCode() throws Exception {
        Muscle muscle = new Muscle();

        muscle.setId(20L);

        assertEquals(mMuscle.hashCode(), mMuscleClone.hashCode());
        assertNotEquals(mMuscle.hashCode(), muscle.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mMuscle.toString());
    }

}