package com.fitnation.model.enums;

import com.fitnation.R;

import java.util.HashMap;
import java.util.Map;

/**
 * The GenderString enumeration.
 */
public class Gender {
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static final String OTHER = "Other";

    public final Integer PROFILE_ID_MALE   = R.id.maleRadioButton;
    public final Integer PROFILE_ID_FEMALE = R.id.femaleRadioButton;
    public final Integer PROFILE_ID_OTHER  = R.id.otherRadioButton;

    private Map<Integer, String> map1;
    private Map<String, Integer> map2;

    public Gender(){
        map1 = new HashMap<Integer, String>();

        map1.put(PROFILE_ID_MALE, MALE);
        map1.put(PROFILE_ID_FEMALE, FEMALE);
        map1.put(PROFILE_ID_OTHER, OTHER);

        map2 = new HashMap<String, Integer>();

        map2.put(MALE, PROFILE_ID_MALE);
        map2.put(FEMALE, PROFILE_ID_FEMALE);
        map2.put(OTHER, PROFILE_ID_OTHER);
    }

    public String getGenderFromId(int id){
        return map1.get(id);
    }

    public Integer getIdFromGender(String str) { return map2.get(str);}
}


