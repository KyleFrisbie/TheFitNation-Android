package com.fitnation.model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * All models extend this guy so that if we ever switch how we save data, it is easy
 */
public class BaseModel extends RealmObject {
    private Integer androidId;
}
