package com.fitnation.model;

import java.util.Date;
import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A single User weight instance
 */
public class UserWeight extends RealmObject {
    @PrimaryKey
    private Long androidId;
    private Long id;
    private Date weightDate;
    private Float weight;

    public UserWeight() {
        weightDate = new Date();
    }

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
        UserWeight userWeight = (UserWeight) o;
        if (userWeight.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userWeight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserWeight{" +
            "id=" + id +
            ", weightDate='" + weightDate.toString() + "'" +
            ", weight='" + weight + "'" +
            '}';
    }
}
