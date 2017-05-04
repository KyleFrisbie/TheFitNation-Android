package com.fitnation.base;

/**
 * Callbacks for saving data to the local data store
 */
public interface DataResult {
    //Called when there is an error in saving the data
    public void onError();
    //Called when data is saved successfully
    public void onSuccess();
}
