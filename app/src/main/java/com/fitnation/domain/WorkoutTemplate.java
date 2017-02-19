package com.fitnation.domain;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A WorkoutTemplate.
 */
public class WorkoutTemplate implements Serializable {
    private Long id;
    private String name;
    private Date created_on;
    private Boolean is_private;
    private UserDemographic userDemographic;
    private Set<WorkoutInstance> workoutInstances = new HashSet<>();
    private Set<UserWorkoutTemplate> userWorkoutTemplates = new HashSet<>();


    public WorkoutTemplate addWorkoutInstance(WorkoutInstance workoutInstance) {
        workoutInstances.add(workoutInstance);
        workoutInstance.setWorkoutTemplate(this);
        return this;
    }

    public WorkoutTemplate removeWorkoutInstance(WorkoutInstance workoutInstance) {
        workoutInstances.remove(workoutInstance);
        workoutInstance.setWorkoutTemplate(null);
        return this;
    }


    public WorkoutTemplate addUserWorkoutTemplate(UserWorkoutTemplate userWorkoutTemplate) {
        userWorkoutTemplates.add(userWorkoutTemplate);
        userWorkoutTemplate.setWorkoutTemplate(this);
        return this;
    }

    public WorkoutTemplate removeUserWorkoutTemplate(UserWorkoutTemplate userWorkoutTemplate) {
        userWorkoutTemplates.remove(userWorkoutTemplate);
        userWorkoutTemplate.setWorkoutTemplate(null);
        return this;
    }

    public void setUserWorkoutTemplates(Set<UserWorkoutTemplate> userWorkoutTemplates) {
        this.userWorkoutTemplates = userWorkoutTemplates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutTemplate workoutTemplate = (WorkoutTemplate) o;
        if (workoutTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workoutTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkoutTemplate{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", created_on='" + created_on + "'" +
            ", is_private='" + is_private + "'" +
            '}';
    }

    public void setUserDemographic(UserDemographic userDemographic) {
        this.userDemographic = userDemographic;
    }
}
