package com.fitnation.model;

import io.realm.RealmObject;

/**
 * Created by Jeremy on 4/1/2017.
 */

public class User extends RealmObject {
    private String login;
    private String email;
    private String imageUrl;
    private boolean activated;

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

}
