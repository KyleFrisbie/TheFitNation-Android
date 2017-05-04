package com.fitnation.model.parsel;

import com.fitnation.model.UserExerciseInstanceSet;

import org.parceler.Parcels;

/**
 * Created by Ryan on 4/29/2017.
 */

public class UserExerciseInstanceSetParcelConverter extends RealmListParcelConverter<UserExerciseInstanceSet> {
    @Override
    public void itemToParcel(UserExerciseInstanceSet input, android.os.Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);

    }

    @Override
    public UserExerciseInstanceSet itemFromParcel(android.os.Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(UserExerciseInstanceSet.class.getClassLoader()));

    }
}