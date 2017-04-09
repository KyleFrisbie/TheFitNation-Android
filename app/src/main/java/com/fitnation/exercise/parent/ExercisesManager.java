package com.fitnation.exercise.parent;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.exercise.callbacks.ExerciseInstanceRequestCallback;
import com.fitnation.exercise.callbacks.ExercisesRequestCallback;
import com.fitnation.exercise.callbacks.SaveWorkoutCallback;
import com.fitnation.exercise.callbacks.WorkoutInstancePostCallback;
import com.fitnation.exercise.callbacks.WorkoutTemplatePostCallback;
import com.fitnation.exercise.parent.tasks.GetExerciseInstancesFromExercisesTask;
import com.fitnation.exercise.parent.tasks.PostWorkoutInstanceTask;
import com.fitnation.exercise.parent.tasks.PostWorkoutTemplateTask;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.PrimaryKeyFactory;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.model.enums.SkillLevel;
import com.fitnation.networking.JsonParser;
import com.fitnation.utils.EnvironmentManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Manages the Exercise/ExerciseInstance Data
 */
public class ExercisesManager extends DataManager {
    private static final String TAG = ExercisesManager.class.getSimpleName();
    private static final String mAuthToken = "de07a7fc-16de-4955-a659-c2c205c3a9ee";
    private RequestQueue mRequestQueue;
    private List<ExerciseInstance> mSelectedExercises;
    private List<ExerciseInstance> mExerciseInstances;
    private List<ExerciseInstance> mExerciseInstancesTab1;
    private List<ExerciseInstance> mExerciseInstancesTab2;
    private List<ExerciseInstance> mExerciseInstancesTab3;


    public ExercisesManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mSelectedExercises = new ArrayList<>();
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
                                ExerciseInstance copy1 = null;
                                ExerciseInstance copy2 = null;
                                ExerciseInstance copy3 = null;

                                copy1 = (ExerciseInstance) instance.clone();
                                copy2 = (ExerciseInstance) instance.clone();
                                copy3 = (ExerciseInstance) instance.clone();

                                mExerciseInstancesTab1.add(copy1);
                                mExerciseInstancesTab2.add(copy2);
                                mExerciseInstancesTab3.add(copy3);

                                mExerciseInstancesTab1 = filterExerciseBySkillLevel(mExerciseInstancesTab1, SkillLevel.BEGINNER);
                                mExerciseInstancesTab2 = filterExerciseBySkillLevel(mExerciseInstancesTab2, SkillLevel.INTERMEDIATE);
                                mExerciseInstancesTab3 = filterExerciseBySkillLevel(mExerciseInstancesTab3, SkillLevel.ADVANCED);
                            }
                            callback.onExercisesRetrieved(mExerciseInstancesTab1, mExerciseInstancesTab2, mExerciseInstancesTab3);
                        }

                        @Override
                        public void onFailure(String error) {
                            callback.onError();
                        }
                    });
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

    /**
     * Creates a workout out of the currently selected exercises and saves it under the given name
     * @param name - The name of the workout
     */
    public void createWorkoutAndSave(final String name, final SaveWorkoutCallback saveWorkoutCallback) {
        final WorkoutTemplate workoutTemplate = getWorkoutTemplate();
        addSkillLevelToWorkout(workoutTemplate);
        new Thread(new Runnable() {
            @Override
            public void run() {
                postWorkoutTemplateToWeb(workoutTemplate, new WorkoutTemplatePostCallback() {
                    @Override
                    public void onSuccess(final WorkoutTemplate updatedTemplate) {
                        WorkoutInstance workoutInstance = new WorkoutInstance(name, 0f, 1, updatedTemplate, "");
                        RealmList<ExerciseInstance> selectedExercises = new RealmList<>();

                        for (ExerciseInstance exerciseInstance : mSelectedExercises) {
                            selectedExercises.add(exerciseInstance);
                        }

                        workoutInstance.setExercises(selectedExercises);
                        workoutInstance.setAndroidId(PrimaryKeyFactory.getInstance().nextKey(WorkoutInstance.class));
                        postWorkoutInstanceToWeb(workoutInstance, new WorkoutInstancePostCallback() {
                            @Override
                            public void onSuccess(WorkoutInstance updatedWorkoutInstance) {
                                updatedTemplate.addWorkoutInstance(updatedWorkoutInstance);
                                saveWorkoutToDatabase(updatedTemplate, saveWorkoutCallback);
                            }

                            @Override
                            public void onFailure(String error) {
                                saveWorkoutCallback.onFailure("Unable to save individual Workout Instances. ErrorCode: " + error );
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        saveWorkoutCallback.onFailure("Unable to save Workout Template Error: " + error );
                    }
                });
            }
        }).start();
    }

    private void addSkillLevelToWorkout(WorkoutTemplate workoutTemplate) {
        int begginerCount = 0;
        int intermediateCount = 0;
        int advancedCount= 0;
        long beginnerSkillLevelId = 0;
        long intermediateSkillLevelId = 0;
        long advancedSkillLevelId = 0;

        for (ExerciseInstance exerciseInstance : mSelectedExercises) {
            Exercise exercise = exerciseInstance.getExercise();
            String skillLevel = exercise.getSkillLevelLevel();

            switch(skillLevel) {
                case SkillLevel.BEGINNER:
                    begginerCount++;
                    if(beginnerSkillLevelId == 0) {
                        beginnerSkillLevelId = exercise.getSkillLevelId();
                    }
                    break;
                case SkillLevel.INTERMEDIATE:
                    intermediateCount++;
                    if(intermediateSkillLevelId == 0) {
                        intermediateSkillLevelId = exercise.getSkillLevelId();
                    }
                    break;
                case SkillLevel.ADVANCED:
                    advancedCount++;
                    if(advancedSkillLevelId == 0) {
                        advancedSkillLevelId = exercise.getSkillLevelId();
                    }
                    break;
            }
        }

        if (begginerCount > intermediateCount && begginerCount > advancedCount) {
            workoutTemplate.setSkillLevelLevel(SkillLevel.BEGINNER);
            workoutTemplate.setSkillLevelId(beginnerSkillLevelId);
        } else if ( intermediateCount > begginerCount && intermediateCount > advancedCount ) {
            workoutTemplate.setSkillLevelLevel(SkillLevel.INTERMEDIATE);
            workoutTemplate.setSkillLevelId(intermediateSkillLevelId);
        } else if ( advancedCount > begginerCount && advancedCount > intermediateCount ) {
            workoutTemplate.setSkillLevelLevel(SkillLevel.ADVANCED);
            workoutTemplate.setSkillLevelId(advancedSkillLevelId);
        } else {
            if(intermediateSkillLevelId != 0) {
                workoutTemplate.setSkillLevelLevel(SkillLevel.INTERMEDIATE);
                workoutTemplate.setSkillLevelId(intermediateSkillLevelId);
            } else {
                workoutTemplate.setSkillLevelLevel(SkillLevel.ADVANCED);
                workoutTemplate.setSkillLevelId(advancedSkillLevelId);
            }
        }
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

    private WorkoutTemplate getWorkoutTemplate() {
        Realm realm = Realm.getDefaultInstance();
        WorkoutTemplate workoutTemplate = null;
        Log.i(TAG, "Determining workout template");
        Log.i(TAG, "Workout template was not given, so going to see if one exists");
        Long androidKey = PrimaryKeyFactory.getInstance().nextKey(WorkoutTemplate.class);

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
            }
        }


        return workoutTemplate;
    }

    private void postWorkoutTemplateToWeb(final WorkoutTemplate template, final WorkoutTemplatePostCallback callback) {
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

    public void updateExerciseList(ExerciseInstance original, ExerciseInstance updated, int tab) {
        mExerciseInstances.remove(original);
        mExerciseInstances.add(updated);

        switch(tab) {
            case 0:
                mExerciseInstancesTab1.remove(original);
                mExerciseInstancesTab1.add(updated);
                break;
            case 1:
                mExerciseInstancesTab2.remove(original);
                mExerciseInstancesTab2.add(updated);
                break;
            case 2:
                mExerciseInstancesTab3.remove(original);
                mExerciseInstancesTab3.add(updated);
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
}
