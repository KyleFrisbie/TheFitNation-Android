package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class WorkoutLogTest {
    private WorkoutLog mWorkoutLog;
    private WorkoutLog mWorkoutLogClone;

    @Before
    public void testSetUp() throws Exception {
        mWorkoutLog = new WorkoutLog();
        mWorkoutLogClone = new WorkoutLog();
        mWorkoutLog.setId(10L);
        mWorkoutLogClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mWorkoutLog.equals(mWorkoutLogClone));
    }

    @Test
    public void testHashCode() throws Exception {
        WorkoutLog workoutLog = new WorkoutLog();

        workoutLog.setId(20L);

        assertEquals(mWorkoutLog.hashCode(), mWorkoutLogClone.hashCode());
        assertNotEquals(mWorkoutLog.hashCode(), workoutLog.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mWorkoutLog.toString());
    }

}