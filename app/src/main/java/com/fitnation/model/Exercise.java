package com.fitnation.model;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.realm.RealmCollection;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A single Exercise
 */
//test build
public class Exercise extends RealmObject {
    @PrimaryKey
    private Long androidId;
    private Long id;
    private RealmList<Muscle> muscles;
    private String name;


    public Exercise() {
        //default constructor
    }

    public void setMuscles(RealmList<Muscle> muscles) {
        this.muscles = muscles;
    }

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
        Exercise exercise = (Exercise) o;
        if (exercise.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exercise.id);
    }

    @Override
    public String toString() {
        return "Exercise{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (muscles != null ? muscles.hashCode() : 0);
        return result;
    }
}
