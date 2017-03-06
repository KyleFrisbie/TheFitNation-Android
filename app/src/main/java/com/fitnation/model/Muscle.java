package com.fitnation.model;

import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A Muscle.
 */
public class Muscle extends RealmObject {
    @PrimaryKey
    private Long androidId;
    private long id;
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Muscle muscle = (Muscle) o;

        return Objects.equals(id, muscle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Muscle{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
