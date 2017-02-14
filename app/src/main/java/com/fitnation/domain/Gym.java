package com.fitnation.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Gym.
 */
public class Gym implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String location;
    private Set<UserDemographic> userDemographics = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Gym name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public Gym location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<UserDemographic> getUserDemographics() {
        return userDemographics;
    }

    public Gym userDemographics(Set<UserDemographic> userDemographics) {
        this.userDemographics = userDemographics;
        return this;
    }

    public Gym addUserDemographic(UserDemographic userDemographic) {
        userDemographics.add(userDemographic);
//        userDemographic.getGyms().add(this);
        return this;
    }

    public Gym removeUserDemographic(UserDemographic userDemographic) {
        userDemographics.remove(userDemographic);
//        userDemographic.getGyms().remove(this);
        return this;
    }

    public void setUserDemographics(Set<UserDemographic> userDemographics) {
        this.userDemographics = userDemographics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gym gym = (Gym) o;
        if (gym.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gym.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Gym{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", location='" + location + "'" +
            '}';
    }
}
