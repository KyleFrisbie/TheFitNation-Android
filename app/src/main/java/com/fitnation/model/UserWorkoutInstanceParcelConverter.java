package com.fitnation.model;


import android.os.Parcel;

import org.parceler.Parcels;


/**
 * Created by Ryan on 4/2/2017.
 */
public class UserWorkoutInstanceParcelConverter extends RealmListParcelConverter<UserWorkoutInstance> {
    @Override
    public void itemToParcel(UserWorkoutInstance input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);

    }

    @Override
    public UserWorkoutInstance itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(UserWorkoutInstance.class.getClassLoader()));

    }
}
