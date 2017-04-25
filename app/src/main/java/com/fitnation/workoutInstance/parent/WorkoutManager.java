package com.fitnation.workoutInstance.parent;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.model.enums.SkillLevel;
import com.fitnation.networking.AuthToken;
import com.fitnation.utils.PrimaryKeyFactory;
import com.fitnation.workout.callbacks.ExerciseInstanceRequestCallback;
import com.fitnation.workout.callbacks.SaveWorkoutCallback;
import com.fitnation.workout.callbacks.WorkoutInstancePostCallback;
import com.fitnation.workout.callbacks.WorkoutTemplatePostCallback;
import com.fitnation.workout.parent.tasks.GetExerciseInstancesFromExercisesTask;
import com.fitnation.workout.parent.tasks.PostWorkoutInstanceTask;
import com.fitnation.workout.parent.tasks.PostWorkoutTemplateTask;
import com.fitnation.workoutInstance.callbacks.WorkoutsRequestCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Manages the Exercise/ExerciseInstance Data
 */
public class WorkoutManager extends DataManager {
    private static final String TAG = WorkoutManager.class.getSimpleName();
    private static String mAuthToken;
    private RequestQueue mRequestQueue;
    private List<WorkoutInstance> mSelectedWorkouts;
    private List<WorkoutInstance> mWorkoutInstances;


    public WorkoutManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mSelectedWorkouts = new ArrayList<>();
        mAuthToken = AuthToken.getInstance().getAccessToken();
    }

    public List<WorkoutInstance> getmWorkoutInstances() {
        return mWorkoutInstances;
    }

    /**
     * Gets each set of exercises for each individual tab. These objects have no references to one another,
     * as a deep clone has been done before passing them in the callback.
     * @param callback - Callback to be invoked upon success/failure
     */
    public void getWorkouts(final WorkoutsRequestCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(mWorkoutInstances == null) {
                    GetWorkoutInstancesFromExercisesTask getWorkoutInstancesTask = new GetWorkoutInstancesFromWorkoutTask(mAuthToken, mRequestQueue);
                    getWorkoutInstancesTask.getWorkoutInstancesFromWorkouts(new WorkoutInstanceRequestCallback() {
                        @Override
                        public void onSuccess(List<WorkoutInstance> workoutInstances) {
                            mWorkoutInstances = workoutInstances;
                            mWorkoutInstances = new ArrayList<ExerciseInstance>(workoutInstances.size());

                            WorkoutInstance copy;

                            copy = (WorkoutInstance) instance.clone;

                            mWorkoutInstances.add(copy);

                            Collections.sort(mWorkoutInstances);

                            callback.onWorkoutsRetrieved(mWorkoutInstances);
                        }

                        @Override
                        public void onFailure(String error) {
                            callback.onError();
                        }
                    });
                } else {
                    callback.onWorkoutsRetrieved(mWorkoutInstances);
                }
            }
        }).start();
    }

    public void workoutInstanceSelected(WorkoutInstance workoutInstance, boolean isSelected) {
        if(isSelected) {
            Log.i(TAG, "Exercise was selected: " + workoutInstance.getWorkout().getName());
            mSelectedWorkouts.add(workoutInstance);
        } else {
            Log.i(TAG, "Attempting to remove Exercise");
            if(mSelectedWorkouts.remove(workoutInstance)){
                Log.i(TAG, "Exercise was removed: " + workoutInstance.getWorkout().getName());
            }
        }
    }

    public boolean atLeastOneExerciseSelected() {
        return mSelectedWorkouts.size() >= 1;
    }


    private WorkoutInstance buildWorkoutInstance(WorkoutTemplate updatedTemplate, String name) {
        WorkoutInstance workoutInstance = new WorkoutInstance(name, 0f, 1, updatedTemplate, "");
        RealmList<ExerciseInstance> selectedExercises = new RealmList<>();

        for (WorkoutInstance workoutInstance1 : mSelectedWorkouts) {
            selectedExercises.add(workoutInstance1);
        }

        workoutInstance.setExercises(selectedExercises);
        workoutInstance.setAndroidId(PrimaryKeyFactory.getInstance().nextKey(WorkoutInstance.class));
        return workoutInstance;
    }

    private void addSkillLevelToWorkout(WorkoutTemplate workoutTemplate) {
        int beginnerCount = 0;
        int intermediateCount = 0;
        int advancedCount= 0;
        long beginnerSkillLevelId = 0;
        long intermediateSkillLevelId = 0;
        long advancedSkillLevelId = 0;

        for (ExerciseInstance exerciseInstance : mSelectedWorkouts) {
            Exercise exercise = exerciseInstance.getExercise();
            String skillLevel = exercise.getSkillLevelLevel();

            switch(skillLevel) {
                case SkillLevel.BEGINNER:
                    beginnerCount++;
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

        if (beginnerCount > intermediateCount && beginnerCount > advancedCount) {
            workoutTemplate.setSkillLevelLevel(SkillLevel.BEGINNER);
            workoutTemplate.setSkillLevelId(beginnerSkillLevelId);
        } else if ( intermediateCount > beginnerCount && intermediateCount > advancedCount ) {
            workoutTemplate.setSkillLevelLevel(SkillLevel.INTERMEDIATE);
            workoutTemplate.setSkillLevelId(intermediateSkillLevelId);
        } else if ( advancedCount > beginnerCount && advancedCount > intermediateCount ) {
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
        mWorkoutInstances.remove(original);
        mWorkoutInstances.add(updated);
        Collections.sort(mWorkoutInstances);

        switch(tab) {
            case 0:
                mExerciseInstances.remove(original);
                mExerciseInstances.add(updated);
                Collections.sort(mExerciseInstances);
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
}
