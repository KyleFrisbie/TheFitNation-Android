package com.fitnation.model;

import android.util.Log;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jeremy on 4/1/2017.
 */

public class User extends RealmObject implements Cloneable{
    @PrimaryKey
    private Long androidId;
    private Long id;
    private String login;
    private String email;
    private String imageUrl;
    private boolean activated;
    private String firstName;
    private String lastName;
    private String langKey = "en";

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public Object clone() {
        User clone = null;

        try {
            clone = (User) super.clone();
        } catch (CloneNotSupportedException ex) {
            Log.d("USERDEMOGRAPHIC", ex.toString());
        }

        return clone;
    }
}
