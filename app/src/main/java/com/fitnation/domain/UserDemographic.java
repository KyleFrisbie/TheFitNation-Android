package com.fitnation.domain;

import com.fitnation.domain.enumeration.Gender;
import com.fitnation.domain.enumeration.SkillLevel;
import com.fitnation.domain.enumeration.UnitOfMeasure;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserDemographic.
 */
public class UserDemographic implements Serializable {

    private Long id;
    private String first_name;
    private String last_name;

    private Gender gender;
    private Date dob;
    private Integer height;
    private SkillLevel skill_level;
    private UnitOfMeasure unit_of_measure;
    private Boolean is_active;
    private Set<Gym> gyms = new HashSet<>();
    private Set<UserWeight> userWeights = new HashSet<>();
    private WorkoutLog workoutLog;
    private Set<WorkoutTemplate> workoutTemplates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDemographic addGym(Gym gym) {
        gyms.add(gym);
        gym.getUserDemographics().add(this);
        return this;
    }

    public UserDemographic removeGym(Gym gym) {
        gyms.remove(gym);
        gym.getUserDemographics().remove(this);
        return this;
    }

    public UserDemographic addUserWeight(UserWeight userWeight) {
        userWeights.add(userWeight);
//        userWeight.setUserDemographic(this);
        return this;
    }

    public UserDemographic removeUserWeight(UserWeight userWeight) {
        userWeights.remove(userWeight);
//        userWeight.setUserDemographic(null);
        return this;
    }

    public UserDemographic addWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        workoutTemplates.add(workoutTemplate);
//        workoutTemplate.setUserDemographic(this);
        return this;
    }

    public UserDemographic removeWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        workoutTemplates.remove(workoutTemplate);
//        workoutTemplate.setUserDemographic(null);
        return this;
    }

    public void setWorkoutTemplates(Set<WorkoutTemplate> workoutTemplates) {
        this.workoutTemplates = workoutTemplates;
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
            ", first_name='" + first_name + "'" +
            ", last_name='" + last_name + "'" +
            ", gender='" + gender + "'" +
            ", dob='" + dob + "'" +
            ", height='" + height + "'" +
            ", skill_level='" + skill_level + "'" +
            ", unit_of_measure='" + unit_of_measure + "'" +
            ", is_active='" + is_active + "'" +
            '}';
    }
}
