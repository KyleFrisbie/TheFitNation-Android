package com.fitnation.model;

import java.util.Date;
import java.util.Objects;

/**
 * A WorkoutTemplate.
 */
public class WorkoutTemplate extends BaseModel {
    private Long id;
    private String name;
    private Date createdOn;
    private Boolean isPrivate;
    private UserDemographic userDemographic;

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
            ", createdOn='" + createdOn + "'" +
            ", isPrivate='" + isPrivate + "'" +
            '}';
    }
}
