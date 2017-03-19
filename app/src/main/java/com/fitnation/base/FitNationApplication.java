package com.fitnation.base;

import android.app.Application;

import com.fitnation.model.PrimaryKeyFactory;

import io.realm.Realm;
import android.content.Context;

/**
 * Created by Ryan on 2/25/2017.
 */

public class FitNationApplication extends Application {
    public static android.content.Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Realm.init(getApplicationContext());
        PrimaryKeyFactory.getInstance().initialize(Realm.getDefaultInstance());
    }

}
