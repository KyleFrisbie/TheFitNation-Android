package com.fitnation.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A UserWeight.
 */
public class UserWeight implements Serializable {
    private Long id;
    private Date weight_date;
    private Float weight;
    private UserDemographic userDemographic; //TODO circular ref

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
            ", weight_date='" + weight_date + "'" +
            ", weight='" + weight + "'" +
            '}';
    }

    public void setUserDemographic(UserDemographic userDemographic) {
        this.userDemographic = userDemographic;
    }
}
