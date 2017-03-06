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

    public final Integer PROFILE_ID_BEGIN = R.id.beginnerRadioButton;
    public final Integer PROFILE_ID_INTER = R.id.intermediateRadioButton;
    public final Integer PROFILE_ID_ADVNC = R.id.advancedRadioButton;

    private Map<Integer, String> map1;

    private Map<String, Integer> map2;

    public SkillLevel(){
        map1 = new HashMap<Integer, String>();

        map1.put(PROFILE_ID_BEGIN, BEGINNER);
        map1.put(PROFILE_ID_INTER, INTERMEDIATE);
        map1.put(PROFILE_ID_ADVNC, ADVANCED);

        map2 = new HashMap<String, Integer>();

        map2.put(BEGINNER, PROFILE_ID_BEGIN);
        map2.put(INTERMEDIATE, PROFILE_ID_INTER);
        map2.put(ADVANCED, PROFILE_ID_ADVNC);
    }

    public String getSkillLevelFromId(int id){
        return map1.get(id);
    }

    public Integer getIdFromSkillLevel(String str) { return map2.get(str); }
}
