package com.fitnation.model.enums;

import com.fitnation.R;

import java.util.HashMap;
import java.util.Map;

/**
 * The SkillLevel enumeration.
 */
public class SkillLevel {
    public static final String BEGINNER = "Beginner";
    public static final String INTERMEDIATE = "Intermediate";
    public static final String ADVANCED = "Advanced";
    private Long id;
    private String level;

    public SkillLevel(){
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
