package com.fitnation.model;

import com.fitnation.model.enums.Gender;
import com.fitnation.model.enums.SkillLevel;
import com.fitnation.model.enums.UnitOfMeasure;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Information about a user and their data
 */
public class UserDemographic extends BaseModel {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date dob;
    private Integer height;
    private SkillLevel skillLevel;
    private UnitOfMeasure unitOfMeasure;
    private Boolean isActive;
    private Set<Gym> gyms = new HashSet<>();
    private Set<UserWeight> userWeights = new HashSet<>();
    private WorkoutLog workoutLog;
    private Set<WorkoutTemplate> workoutTemplates = new HashSet<>();

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
        UserDemographic userDemographic = (UserDemographic) o;
        if (userDemographic.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userDemographic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserDemographic{" +
            "id=" + id +
            ", first_name='" + firstName + "'" +
            ", last_name='" + lastName + "'" +
            ", gender='" + gender.toString() + "'" +
            ", dob='" + dob + "'" +
            ", height='" + height + "'" +
            ", skill_level='" + skillLevel.toString()+ "'" +
            ", unit_of_measure='" +  unitOfMeasure.toString() + "'" +
            ", is_active='" + isActive + "'" +
            '}';
    }
}
