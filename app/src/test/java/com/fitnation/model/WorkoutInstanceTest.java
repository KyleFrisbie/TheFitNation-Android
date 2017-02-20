package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class WorkoutInstanceTest {
    private WorkoutInstance mWorkoutInstance;
    private WorkoutInstance mWorkoutInstanceClone;

    @Before
    public void testSetUp() throws Exception {
        mWorkoutInstance = new WorkoutInstance();
        mWorkoutInstanceClone = new WorkoutInstance();
        mWorkoutInstance.setId(10L);
        mWorkoutInstanceClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mWorkoutInstance.equals(mWorkoutInstanceClone));
    }

    @Test
    public void testHashCode() throws Exception {
        WorkoutInstance workoutInstance = new WorkoutInstance();

        workoutInstance.setId(20L);

        assertEquals(mWorkoutInstance.hashCode(), mWorkoutInstanceClone.hashCode());
        assertNotEquals(mWorkoutInstance.hashCode(), workoutInstance.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mWorkoutInstance.toString());
    }

}