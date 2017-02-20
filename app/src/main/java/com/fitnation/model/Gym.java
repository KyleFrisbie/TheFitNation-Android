package com.fitnation.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Gym.
 */
public class Gym extends BaseModel {
    private Long id;
    private String name;
    private Location location;

    public void setId(Long id) {
        this.id = id;
    }

    public void setLocation(Location location) {
        this.location = location;
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
