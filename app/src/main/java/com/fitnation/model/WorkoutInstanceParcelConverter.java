package com.fitnation.model;


import android.os.Parcel;

import org.parceler.Parcels;


/**
 * Created by Ryan on 4/2/2017.
 */
public class WorkoutInstanceParcelConverter extends RealmListParcelConverter<WorkoutInstance> {
    @Override
    public void itemToParcel(WorkoutInstance input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);

    }

    @Override
    public WorkoutInstance itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(WorkoutInstance.class.getClassLoader()));

    }
}
