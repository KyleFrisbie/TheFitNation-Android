package com.fitnation.base;

import android.app.Application;

import com.fitnation.utils.PrimaryKeyFactory;

import io.realm.Realm;

/**
 * Created by Ryan on 2/25/2017.
 */
public class FitNationApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        PrimaryKeyFactory.getInstance().initialize(Realm.getDefaultInstance());
    }
}
