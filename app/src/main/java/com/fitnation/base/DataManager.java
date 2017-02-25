package com.fitnation.base;

import android.util.Log;

import java.util.Collection;

import io.realm.Realm;

import io.realm.RealmObject;

/**
 * Created by Ryan on 2/25/2017.
 */

public class DataManager {
    private static final String TAG = "DataManager";

    /**
     * Constructor
     */
    public DataManager() {
    }

    /**
     * Saves/Updates data to the local data store
     */
    public <T extends RealmObject> void saveData(final T data, final DataResult resultCallback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Realm realm = Realm.getDefaultInstance();

                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(data);
                    realm.commitTransaction();
                    realm.close();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    resultCallback.onError();
                }
                resultCallback.onSuccess();
            }
        }).start();

    }

    /**
     * Saves/Updates collection to the local data store
     */
    public <T extends RealmObject> void saveData(final Collection<T> data, final DataResult resultCallback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Realm realm = Realm.getDefaultInstance();

                    realm.beginTransaction();

                    for (T individualData :data) {
                        if(individualData.isValid()) {
                            realm.copyToRealmOrUpdate(data);
                        } else {
                            Log.wtf(TAG, "Realm save failed. Reason: In-valid object");
                        }
                    }

                    realm.commitTransaction();
                    realm.close();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    resultCallback.onError();
                }
                resultCallback.onSuccess();
            }
        }).start();

    }
}
