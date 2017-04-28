package com.fitnation.workoutInstance.parent;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.networking.AuthToken;
import com.fitnation.utils.PrimaryKeyFactory;
import com.fitnation.workout.callbacks.SaveWorkoutCallback;
import com.fitnation.workout.callbacks.WorkoutInstancePostCallback;
import com.fitnation.workout.callbacks.WorkoutTemplateRequestCallback;
import com.fitnation.workout.parent.tasks.PostWorkoutInstanceTask;
import com.fitnation.workout.parent.tasks.PostWorkoutTemplateTask;
import com.fitnation.workoutInstance.callbacks.UserWorkoutInstanceRequestCallback;
import com.fitnation.workoutInstance.callbacks.UserWorkoutInstancesRequestCallback;
import com.fitnation.workoutInstance.callbacks.WorkoutInstanceRequestCallback;
import com.fitnation.workoutInstance.callbacks.WorkoutInstancesRequestCallback;
import com.fitnation.workoutInstance.callbacks.WorkoutsRequestCallback;
import com.fitnation.workoutInstance.parent.tasks.UserWorkoutInstancesTasks;
import com.fitnation.workoutInstance.parent.tasks.WorkoutInstancesTasks;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Manages the Exercise/ExerciseInstance Data
 */
public class WorkoutManager extends DataManager {
    private static final String TAG = WorkoutManager.class.getSimpleName();
    private static String mAuthToken;
    private RequestQueue mRequestQueue;
    private List<UserWorkoutInstance> mUserWorkoutInstances;
    private List<WorkoutInstance> mWorkoutInstances;


    public WorkoutManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mAuthToken = AuthToken.getInstance().getAccessToken();
    }

    public List<WorkoutInstance> getmWorkoutInstances() {
        return mWorkoutInstances;
    }

    public List<UserWorkoutInstance> getmUserWorkoutInstances() {
        return mUserWorkoutInstances;
    }

    /**
     * Gets each set of exercises for each individual tab. These objects have no references to one another,
     * as a deep clone has been done before passing them in the callback.
     * @param callback - Callback to be invoked upon success/failure
     */
    public void getAllUserWorkoutInstances(final UserWorkoutInstancesRequestCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(mUserWorkoutInstances == null) {
                    UserWorkoutInstancesTasks getWorkoutInstancesTask = new UserWorkoutInstancesTasks(mAuthToken, mRequestQueue);
                    getWorkoutInstancesTask.getAllUserWorkoutInstances(new UserWorkoutInstanceRequestCallback.getAll() {
                        @Override
                        public void onGetAllSuccess(List<UserWorkoutInstance> userWorkoutInstances) {
                            callback.onUserWorkoutInstancesRetrieved(userWorkoutInstances);
                        }

                        @Override
                        public void onGetAllFailure(String error) {
                            callback.onError();
                        }
                    });
                } else {
                    callback.onUserWorkoutInstancesRetrieved(mUserWorkoutInstances);
                }
            }
        }).start();
    }

    public void getAllWorkoutInstances(final WorkoutInstancesRequestCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(mWorkoutInstances == null) {
                    WorkoutInstancesTasks getWorkoutInstancesTask = new WorkoutInstancesTasks(mAuthToken, mRequestQueue);
                    getWorkoutInstancesTask.getAllWorkoutInstances(new WorkoutInstanceRequestCallback.getAll() {
                        @Override
                        public void onGetAllSuccess(List<WorkoutInstance> workoutInstances) {
                            callback.onWorkoutInstancesRetrieved(workoutInstances);
                        }

                        @Override
                        public void onGetAllFailure(String error) {
                            callback.onError();
                        }
                    });
                } else {
                    callback.onWorkoutInstancesRetrieved(mWorkoutInstances);
                }
            }
        }).start();
    }

    private UserWorkoutTemplate getUserWorkoutTemplate() {
        Realm realm = null;
        UserWorkoutTemplate userWorkoutTemplate = null;
        Log.i(TAG, "Determining workout template");
        Log.i(TAG, "Workout template was not given, so going to see if one exists");
        Long androidKey = PrimaryKeyFactory.getInstance().nextKey(WorkoutTemplate.class);

        try {
            realm = Realm.getDefaultInstance();

            if (androidKey == 1) {
                userWorkoutTemplate = new UserWorkoutTemplate();
                userWorkoutTemplate.setAndroidId(PrimaryKeyFactory.getInstance().nextKey(UserWorkoutTemplate.class));
                Log.i(TAG, "Looks like none exist, going to create a new workout template");
            } else {
                Log.i(TAG, "We have at least one workout template in the DB");
                RealmResults<UserWorkoutTemplate> query = realm.where(UserWorkoutTemplate.class).findAll();
                if (query.size() == 0) {
                    Log.i(TAG, "No workout template's found in query, making a new one");
                    userWorkoutTemplate = new UserWorkoutTemplate();
                    userWorkoutTemplate.setAndroidId(PrimaryKeyFactory.getInstance().nextKey(UserWorkoutTemplate.class));
                } else {
                    Log.i(TAG, "Found the workout template");
                    userWorkoutTemplate = query.first();
                    userWorkoutTemplate = realm.copyFromRealm(userWorkoutTemplate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(realm != null) {
                realm.close();
            }
        }


        return userWorkoutTemplate;
    }

    private WorkoutTemplate getWorkoutTemplate() {
        Realm realm = null;
        WorkoutTemplate workoutTemplate = null;
        Log.i(TAG, "Determining workout template");
        Log.i(TAG, "Workout template was not given, so going to see if one exists");
        Long androidKey = PrimaryKeyFactory.getInstance().nextKey(WorkoutTemplate.class);

        try {
            realm = Realm.getDefaultInstance();

            if (androidKey == 1) {
                workoutTemplate = new WorkoutTemplate();
                workoutTemplate.setName("Individual Workout's");
                workoutTemplate.setAndroidId(PrimaryKeyFactory.getInstance().nextKey(WorkoutTemplate.class));
                Log.i(TAG, "Looks like none exist, going to create a new workout template");
            } else {
                Log.i(TAG, "We have at least one workout template in the DB");
                RealmResults<WorkoutTemplate> query = realm.where(WorkoutTemplate.class).findAll();
                if (query.size() == 0) {
                    Log.i(TAG, "No workout template's found in query, making a new one");
                    workoutTemplate = new WorkoutTemplate();
                    workoutTemplate.setName("Individual Workout's");
                    workoutTemplate.setAndroidId(PrimaryKeyFactory.getInstance().nextKey(WorkoutTemplate.class));
                } else {
                    Log.i(TAG, "Found the workout template");
                    workoutTemplate = query.first();
                    workoutTemplate = realm.copyFromRealm(workoutTemplate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(realm != null) {
                realm.close();
            }
        }


        return workoutTemplate;
    }

    private void saveWorkoutToDatabase(WorkoutTemplate workoutTemplate, final SaveWorkoutCallback callback) {
        saveData(workoutTemplate, new DataResult() {
            @Override
            public void onError() {
                Log.e(TAG, "WorkoutTemplate was not succesfully saved");
                callback.onFailure("Unable to save to local data store.");
            }

            @Override
            public void onSuccess() {
                callback.onSuccess();
                Log.e(TAG, "WorkoutTemplate was succesfully saved");
            }
        });
    }

    private void postWorkoutTemplateToWeb(final WorkoutTemplate template, final WorkoutTemplateRequestCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PostWorkoutTemplateTask postWorkoutTemplateTask = new PostWorkoutTemplateTask(mAuthToken, mRequestQueue);
                postWorkoutTemplateTask.postWorkoutTemplate(template, callback);
            }
        }).start();
    }

    private void postWorkoutInstanceToWeb(final WorkoutInstance workoutInstance, final WorkoutInstancePostCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PostWorkoutInstanceTask postWorkoutInstanceTask = new PostWorkoutInstanceTask(mAuthToken, mRequestQueue);
                postWorkoutInstanceTask.postWorkoutInstance(workoutInstance, callback);
            }
        }).start();
    }
}
