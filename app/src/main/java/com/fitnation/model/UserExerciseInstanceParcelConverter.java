package com.fitnation.model;

import org.parceler.Parcels;


/**
 * Created by Ryan on 4/2/2017.
 */
public class UserExerciseInstanceParcelConverter extends RealmListParcelConverter<UserExerciseInstance> {
    @Override
    public void itemToParcel(UserExerciseInstance input, android.os.Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);

    }

    @Override
    public UserExerciseInstance itemFromParcel(android.os.Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(ExerciseInstanceSet.class.getClassLoader()));

    }
}
