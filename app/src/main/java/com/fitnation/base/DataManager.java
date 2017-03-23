package com.fitnation.base;

import android.content.Context;
import android.util.Log;

import java.util.Collection;

import io.realm.Realm;

import io.realm.RealmObject;

/**
 * All DataManagers control access to data for their presenter
 */
public abstract class DataManager {
    private static final String TAG = "DataManager";
    protected Realm mRealm;
    /**
     * Constructor
     */
    public DataManager(Context context) {
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * Saves/Updates data to the local data store
     * @param data - data to be saved
     * @param resultCallback - notified upon result of save
     * @param <T> - generic data type
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
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    resultCallback.onError();
                }
                resultCallback.onSuccess();
            }
        }).start();

    }

    /**
     * Saves/Updates a collection of data to the local data store
     * @param data  - collection to be saved
     * @param resultCallback - notified upon result of save
     * @param <T> - generic data type
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
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    resultCallback.onError();
                }
                resultCallback.onSuccess();
            }
        }).start();

    }
}
