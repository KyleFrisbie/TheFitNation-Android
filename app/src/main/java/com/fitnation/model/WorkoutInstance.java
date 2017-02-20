package com.fitnation.model;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A workout that has been performed by the User
 */
public class WorkoutInstance extends BaseModel {
    private Long id;
    private String name;
    private Date createdOn;
    private Integer restBetweenInstances;
    private Integer orderNumber;
    private WorkoutTemplate workoutTemplate;
    private Set<UserWorkoutInstance> userWorkoutInstances = new HashSet<>();
    private Set<Exercise> exercises = new HashSet<>();

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutInstance workoutInstance = (WorkoutInstance) o;
        if (workoutInstance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workoutInstance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkoutInstance{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", createdOn='" + createdOn + "'" +
            ", restBetweenInstances='" + restBetweenInstances + "'" +
            ", orderNumber='" + orderNumber + "'" +
            '}';
    }
}
