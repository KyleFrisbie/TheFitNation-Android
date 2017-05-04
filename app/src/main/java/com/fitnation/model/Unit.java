package com.fitnation.model;

import io.realm.RealmObject;

/**
 * Created by Ryan on 3/21/2017.
 */

public class Unit extends RealmObject {
    public static final String DEFAULT_REPS_UNIT = "Reps";
    public static final String DEFAULT_EFFORT_UNIT = "Pounds";
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
