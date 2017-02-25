package com.fitnation.model;

import io.realm.annotations.PrimaryKey;

/**
 * Created by Ryan on 2/25/2017.
 */
public class GenderString {
    @PrimaryKey
    private Long androidId;
    public String description;
}
