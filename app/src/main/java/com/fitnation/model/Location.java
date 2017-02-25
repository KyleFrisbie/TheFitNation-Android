package com.fitnation.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Represents a Location somewhere in the World
 */
public class Location extends RealmObject {
    @PrimaryKey
    private Long androidId;
    private Long id;
    private Double longitude;
    private Double latitude;
    private String address;

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (id != null ? !id.equals(location.id) : location.id != null) return false;
        if (longitude != null ? !longitude.equals(location.longitude) : location.longitude != null)
            return false;
        if (latitude != null ? !latitude.equals(location.latitude) : location.latitude != null)
            return false;
        return address != null ? address.equals(location.address) : location.address == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
