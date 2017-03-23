package com.fitnation.base;

import io.realm.RealmResults;

/**
 * Created by Ryan on 3/23/2017.
 */

public interface QueryResult {
    void onQueryCollectionSuccess(RealmResults<?> realmResults );
    void onQueryError();
}
