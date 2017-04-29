package com.fitnation.workout.parent;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fitnation.base.DataManager;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.tasks.callbacks.ExerciseInstanceRequestCallback;
import com.fitnation.networking.tasks.callbacks.ExercisesRequestCallback;
import com.fitnation.networking.tasks.GetExerciseInstancesFromExercisesTask;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.enums.SkillLevel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the Exercise/ExerciseInstance Data
 */
public class ExercisesManager extends DataManager {
    private static final String TAG = ExercisesManager.class.getSimpleName();
    private static String mAuthToken;
    private RequestQueue mRequestQueue;
    private List<ExerciseInstance> mSelectedExercises;
    private List<ExerciseInstance> mExerciseInstances;
    private List<ExerciseInstance> mExerciseInstancesTab1;
    private List<ExerciseInstance> mExerciseInstancesTab2;
    private List<ExerciseInstance> mExerciseInstancesTab3;



    public ExercisesManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mSelectedExercises = new ArrayList<>();
        mAuthToken = AuthToken.getInstance().getAccessToken();
    }

    public List<ExerciseInstance> getExercisesTab1() {
        return mExerciseInstancesTab1;
    }

    public List<ExerciseInstance> getExercisesTab2() {
        return mExerciseInstancesTab2;
    }

    public List<ExerciseInstance> getExercisesTab3() {
        return mExerciseInstancesTab3;
    }

    /**
     * Gets each set of exercises for each individual tab. These objects have no references to one another,
     * as a deep clone has been done before passing them in the callback.
     * @param callback - Callback to be invoked upon success/failure
     */
    public void getExercises(final ExercisesRequestCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(mExerciseInstances == null) {
                    GetExerciseInstancesFromExercisesTask getExerciseInstancesTask = new GetExerciseInstancesFromExercisesTask(mAuthToken, mRequestQueue);
                    getExerciseInstancesTask.getExerciseInstancesFromExercises(new ExerciseInstanceRequestCallback() {
                        @Override
                        public void onSuccess(List<ExerciseInstance> exerciseInstances) {
                            mExerciseInstances = exerciseInstances;
                            mExerciseInstancesTab1 = new ArrayList<ExerciseInstance>(exerciseInstances.size());
                            mExerciseInstancesTab2 = new ArrayList<ExerciseInstance>(exerciseInstances.size());
                            mExerciseInstancesTab3 = new ArrayList<ExerciseInstance>(exerciseInstances.size());

                            for (ExerciseInstance instance : exerciseInstances) {
                                ExerciseInstance copy1;
                                ExerciseInstance copy2;
                                ExerciseInstance copy3;

                                copy1 = (ExerciseInstance) instance.clone();
                                copy2 = (ExerciseInstance) instance.clone();
                                copy3 = (ExerciseInstance) instance.clone();

                                mExerciseInstancesTab1.add(copy1);
                                mExerciseInstancesTab2.add(copy2);
                                mExerciseInstancesTab3.add(copy3);
                            }

                            mExerciseInstancesTab1 = filterExerciseBySkillLevel(mExerciseInstancesTab1, SkillLevel.BEGINNER);
                            mExerciseInstancesTab2 = filterExerciseBySkillLevel(mExerciseInstancesTab2, SkillLevel.INTERMEDIATE);
                            mExerciseInstancesTab3 = filterExerciseBySkillLevel(mExerciseInstancesTab3, SkillLevel.ADVANCED);

                            Collections.sort(mExerciseInstances);
                            Collections.sort(mExerciseInstancesTab1);
                            Collections.sort(mExerciseInstancesTab2);
                            Collections.sort(mExerciseInstancesTab3);

                            callback.onExercisesRetrieved(mExerciseInstancesTab1, mExerciseInstancesTab2, mExerciseInstancesTab3);
                        }

                        @Override
                        public void onFailure(String error) {
                            callback.onError();
                        }
                    });
                } else {
                    callback.onExercisesRetrieved(mExerciseInstancesTab1, mExerciseInstancesTab2, mExerciseInstancesTab3);
                }
            }
        }).start();
    }

    public void exerciseInstanceSelected(ExerciseInstance exerciseInstance, boolean isSelected) {
        if(isSelected) {
            Log.i(TAG, "Exercise was selected: " + exerciseInstance.getExercise().getName());
            mSelectedExercises.add(exerciseInstance);
        } else {
            Log.i(TAG, "Attempting to remove Exercise");
            if(mSelectedExercises.remove(exerciseInstance)){
                Log.i(TAG, "Exercise was removed: " + exerciseInstance.getExercise().getName());
            }
        }
    }

    public boolean atLeastOneExerciseSelected() {
        return mSelectedExercises.size() >= 1;
    }



    public void updateExerciseList(ExerciseInstance original, ExerciseInstance updated, int tab) {
        mExerciseInstances.remove(original);
        mExerciseInstances.add(updated);
        Collections.sort(mExerciseInstances);

        switch(tab) {
            case 0:
                mExerciseInstancesTab1.remove(original);
                mExerciseInstancesTab1.add(updated);
                Collections.sort(mExerciseInstancesTab1);
                break;
            case 1:
                mExerciseInstancesTab2.remove(original);
                mExerciseInstancesTab2.add(updated);
                Collections.sort(mExerciseInstancesTab2);
                break;
            case 2:
                mExerciseInstancesTab3.remove(original);
                mExerciseInstancesTab3.add(updated);
                Collections.sort(mExerciseInstancesTab3);
                break;
        }

    }

    public static List<ExerciseInstance> filterExerciseBySkillLevel(List<ExerciseInstance> exerciseInstances, String filter) {
        List<ExerciseInstance> filteredList = new ArrayList<>();

        for (ExerciseInstance exerciseInstance: exerciseInstances) {
            if(exerciseInstance.getExercise().getSkillLevelLevel().equalsIgnoreCase(filter)) {
                filteredList.add(exerciseInstance);
            }
        }

        return filteredList;
    }

    public List<ExerciseInstance> getSelectedExercises() {
        return mSelectedExercises;
    }
}