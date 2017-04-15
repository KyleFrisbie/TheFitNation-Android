package com.fitnation.model;


import android.os.Parcel;

import org.parceler.Parcels;


/**
 * Created by Ryan on 4/2/2017.
 */
public class ExerciseInstanceSetParcelConverter extends RealmListParcelConverter<ExerciseInstanceSet> {
    @Override
    public void itemToParcel(ExerciseInstanceSet input, android.os.Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);

    }

    @Override
    public ExerciseInstanceSet itemFromParcel(android.os.Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(ExerciseInstanceSet.class.getClassLoader()));

    }
}
