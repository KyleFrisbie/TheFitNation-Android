package com.fitnation.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2/19/2017.
 */
public class WorkoutTemplateTest {
    private WorkoutTemplate mWorkoutTemplate;
    private WorkoutTemplate mWorkoutTemplateClone;

    @Before
    public void testSetUp() throws Exception {
        mWorkoutTemplate = new WorkoutTemplate();
        mWorkoutTemplateClone = new WorkoutTemplate();
        mWorkoutTemplate.setId(10L);
        mWorkoutTemplateClone.setId(10L);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(mWorkoutTemplate.equals(mWorkoutTemplateClone));
    }

    @Test
    public void testHashCode() throws Exception {
        WorkoutTemplate workoutLog = new WorkoutTemplate();

        workoutLog.setId(20L);

        assertEquals(mWorkoutTemplate.hashCode(), mWorkoutTemplateClone.hashCode());
        assertNotEquals(mWorkoutTemplate.hashCode(), workoutLog.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mWorkoutTemplate.toString());
    }


}