package com.fitnation.base;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.fitnation.utils.PrimaryKeyFactory;

import io.fabric.sdk.android.Fabric;
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
        Fabric.with(this, new Crashlytics());

    }
}
