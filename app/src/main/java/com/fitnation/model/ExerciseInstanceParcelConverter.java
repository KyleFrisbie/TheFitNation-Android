package com.fitnation.model;

import android.os.Parcel;

import org.parceler.Parcels;

/**
 * Created by Ryan Newsom on 4/30/17. *
 */

public class ExerciseInstanceParcelConverter extends RealmListParcelConverter<ExerciseInstance>{
    @Override
    public void itemToParcel(ExerciseInstance input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public ExerciseInstance itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(ExerciseInstance.class.getClassLoader()));
    }
}
