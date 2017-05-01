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
import com.fitnation.workout.parent.WorkoutTemplateManager;
import com.fitnation.workout.services.UserWorkoutDataManager;
import com.fitnation.workout.services.WorkoutDataManager;
import com.fitnation.workoutInstance.callbacks.UserWorkoutInstanceRequestCallback;
import com.fitnation.workoutInstance.callbacks.UserWorkoutTemplateRequestCallback;
import com.fitnation.workoutInstance.callbacks.WorkoutInstanceRequestCallback;
import com.fitnation.workoutInstance.callbacks.WorkoutManagerWorkoutsCallback;
import com.fitnation.workoutInstance.callbacks.WorkoutTemplateRequestCallback;
import com.fitnation.workoutInstance.parent.tasks.UserWorkoutInstancesTasks;
import com.fitnation.workoutInstance.parent.tasks.UserWorkoutTemplateTasks;
import com.fitnation.workoutInstance.parent.tasks.WorkoutInstancesTasks;
import com.fitnation.workoutInstance.parent.tasks.WorkoutTemplateTasks;

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
    private Context mContext;
    private List<UserWorkoutInstance> mUserWorkoutInstances;
    private List<WorkoutInstance> mWorkoutInstances;


    public WorkoutManager(Context context) {
        mContext = context;
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
    public void getAllUserWorkoutInstances(final WorkoutManagerWorkoutsCallback.userInstance callback) {
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
                            callback.onUserError();
                        }
                    });
                } else {
                    callback.onUserWorkoutInstancesRetrieved(mUserWorkoutInstances);
                }
            }
        }).start();
    }

    public void getAllWorkoutInstances(final WorkoutManagerWorkoutsCallback.instance callback) {
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

    public void deleteWorkoutInstance(WorkoutInstance workoutInstance){
        //TODO build delete logic
        WorkoutInstancesTasks workoutInstancesTasks = new WorkoutInstancesTasks(mAuthToken, mRequestQueue);
        workoutInstancesTasks.deleteWorkoutInstance(workoutInstance.getId(), new WorkoutInstanceRequestCallback.delete() {
            @Override
            public void onDeleteSuccess() {
                //TODO update instances and delete in realm.
            }

            @Override
            public void onDeleteFailure(String error) {
                //TODO return error
            }
        });
    }

    public void deleteUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance){
        //TODO build delete logic
        UserWorkoutInstancesTasks userWorkoutInstancesTasks = new UserWorkoutInstancesTasks(mAuthToken, mRequestQueue);
        userWorkoutInstancesTasks.deleteUserWorkoutInstance(userWorkoutInstance.getId(), new UserWorkoutInstanceRequestCallback.delete() {
            @Override
            public void onDeleteSuccess() {
                //TODO update instances and delete realm
                Log.i(TAG, "UserWorkoutInstance Deleted");
            }

            @Override
            public void onDeleteFailure(String error) {
                //TODO return error
                Log.i(TAG, "WorkoutInstance Deleted");
            }
        });

    }

    public UserWorkoutTemplate getUserWorkoutTemplate() {
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
                    Log.i(TAG, "No workout template's found in query, getting a new one");
                    UserWorkoutTemplateTasks userWorkoutTemplateTasks = new UserWorkoutTemplateTasks(mAuthToken, mRequestQueue);
                    userWorkoutTemplateTasks.getAllUserWorkoutTemplates(new UserWorkoutTemplateRequestCallback.getAll() {
                        @Override
                        public void onGetAllSuccess(List<UserWorkoutTemplate> workoutInstances) {
                            UserWorkoutDataManager userWorkoutDataManager = new UserWorkoutDataManager(mContext);
                            if(!workoutInstances.isEmpty()) {
                                UserWorkoutTemplate userWorkoutTemplate1 = workoutInstances.get(0);
                                userWorkoutDataManager.saveUserWorkoutTemplate(userWorkoutTemplate1, new SaveWorkoutCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.i(TAG, "onUserWorkoutTemplateSaveSuccess()");
                                    }

                                    @Override
                                    public void onFailure(String error) {
                                        Log.i(TAG, "onUserWorkoutTemplateSaveFailure()");
                                    }
                                });
                            }else{
                                final WorkoutTemplate workoutTemplateSingleton = WorkoutTemplateManager.getSingletonWorkoutTemplate();
                                UserWorkoutTemplate userWorkoutTemplateSingleton = WorkoutTemplateManager.getSingletonUserWorkoutTemplate(workoutTemplateSingleton);
                                saveData(userWorkoutTemplateSingleton, new DataResult() {
                                    @Override
                                    public void onError() {
                                        Log.i(TAG, "onUserWorkoutTemplateSaveFailure()");
                                    }

                                    @Override
                                    public void onSuccess() {
                                        Log.i(TAG, "onUserWorkoutTemplateSaveSuccess()");

                                    }
                                });
                            }
                        }

                        @Override
                        public void onGetAllFailure(String error) {
                            Log.i(TAG, "Failed to get UserWorkoutTemplate form server");
                        }
                    });
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

    public WorkoutTemplate getWorkoutTemplate() {
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
                    Log.i(TAG, "No workout template's found in query, getting a new one");
                    WorkoutTemplateTasks workoutTemplateTasks = new WorkoutTemplateTasks(mAuthToken, mRequestQueue);
                    workoutTemplateTasks.getAllWorkoutTemplates(new WorkoutTemplateRequestCallback.getAll() {
                        @Override
                        public void onGetAllSuccess(List<WorkoutTemplate> workoutTemplates) {
                            WorkoutDataManager workoutDataManager = new WorkoutDataManager(mContext);
                            if(!workoutTemplates.isEmpty()) {
                                WorkoutTemplate workoutTemplate1 = workoutTemplates.get(0);
                                workoutTemplate1.setAndroidId(PrimaryKeyFactory.getInstance().nextKey(WorkoutTemplate.class));
                                workoutDataManager.saveWorkoutTemplate(workoutTemplate1, new SaveWorkoutCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.i(TAG, "onWorkoutTemplateSaveSuccess()");
                                    }

                                    @Override
                                    public void onFailure(String error) {
                                        Log.i(TAG, "onWorkoutTemplateSaveFailure()");
                                    }
                                });
                            }else{
                                WorkoutTemplate workoutTemplateSingleton = WorkoutTemplateManager.getSingletonWorkoutTemplate();
                                saveData(workoutTemplateSingleton, new DataResult() {
                                    @Override
                                    public void onError() {
                                        Log.i(TAG, "onWorkoutTemplateSaveFailure()");

                                    }

                                    @Override
                                    public void onSuccess() {
                                        Log.i(TAG, "onWorkoutTemplateSaveSuccess()");

                                    }
                                });
                            }
                        }

                        @Override
                        public void onGetAllFailure(String error) {
                            Log.i(TAG, "Failed to get workout template");
                        }
                    });
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
}
