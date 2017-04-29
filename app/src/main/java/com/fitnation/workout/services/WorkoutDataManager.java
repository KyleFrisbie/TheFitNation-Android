package com.fitnation.workout.services;

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
import com.fitnation.networking.tasks.PostWorkoutInstanceTask;
import com.fitnation.networking.tasks.PostWorkoutTemplateTask;
import com.fitnation.networking.tasks.callbacks.WorkoutInstancePostCallback;
import com.fitnation.networking.tasks.callbacks.WorkoutTemplatePostCallback;
import com.fitnation.utils.PrimaryKeyFactory;
import com.fitnation.workout.callbacks.SaveWorkoutCallback;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by Ryan on 4/24/2017.
 */
public class WorkoutDataManager extends DataManager {
    private static final String TAG = WorkoutDataManager.class.getSimpleName();
    private WorkoutInstance mWorkoutInstance;
    private RequestQueue mRequestQueue;

    public WorkoutDataManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Creates a workout out of the currently selected exercises and saves it under the given name
     */
    public void saveWorkout(final WorkoutTemplate workoutTemplate, final WorkoutInstance workoutInstance, final SaveWorkoutCallback saveWorkoutCallback) {
        final String authToken = AuthToken.getInstance().getAccessToken();
        List<ExerciseInstance> exercisesSelected = workoutInstance.getExerciseInstances();

        if(workoutInstance.getAndroidId() == null) {
            workoutInstance.setAndroidId(PrimaryKeyFactory.getInstance().nextKey(WorkoutInstance.class));
        }

        if(workoutTemplate.getAndroidId() == null) {
            workoutTemplate.setAndroidId(PrimaryKeyFactory.getInstance().nextKey(WorkoutTemplate.class));
        }

        if(exercisesSelected != null) {
            if(workoutTemplate.getSkillLevelLevel() == null) {
                addSkillLevelToWorkout(exercisesSelected, workoutTemplate);
            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                postWorkoutTemplateToWeb(authToken, workoutTemplate, new WorkoutTemplatePostCallback() {
                    @Override
                    public void onSuccess(final WorkoutTemplate updatedTemplate) {
                        updatedTemplate.setAndroidId(workoutTemplate.getAndroidId());
                        workoutInstance.setWorkoutTemplate(updatedTemplate);
                        postWorkoutInstanceToWeb(authToken , workoutInstance, new WorkoutInstancePostCallback() {
                            @Override
                            public void onSuccess(WorkoutInstance updatedWorkoutInstance) {
                                updatedWorkoutInstance.setAndroidId((workoutInstance.getAndroidId()));
                                mWorkoutInstance = updatedWorkoutInstance;
                                RealmList<ExerciseInstance> updatedExerciseInstances = updatedWorkoutInstance.getExerciseInstances();
                                RealmList<ExerciseInstance> exerciseInstances = workoutInstance.getExerciseInstances();

                                for (int i = 0; i < exerciseInstances.size(); i++) {
                                    ExerciseInstance updated = updatedExerciseInstances.get(i);
                                    ExerciseInstance old = exerciseInstances.get(i);

                                    updated.setExercise(old.getExercise());

                                }

                                updatedTemplate.addWorkoutInstance(updatedWorkoutInstance);
                                saveWorkoutToDatabase(updatedTemplate, saveWorkoutCallback);
                            }

                            @Override
                            public void onFailure(String error) {
                                saveWorkoutCallback.onFailure("Unable to save individual Workout Instances. ErrorCode: " + error);
                            }
                        });

                    }

                    @Override
                    public void onFailure(String error) {
                        saveWorkoutCallback.onFailure("Unable to save Workout Template Error: " + error);
                    }
                });
            }
        }).start();
    }

    private void addSkillLevelToWorkout(List<ExerciseInstance> selectedExercises, WorkoutTemplate workoutTemplate) {
        int beginnerCount = 0;
        int intermediateCount = 0;
        int advancedCount= 0;
        long beginnerSkillLevelId = 0;
        long intermediateSkillLevelId = 0;
        long advancedSkillLevelId = 0;

        for (ExerciseInstance exerciseInstance : selectedExercises) {
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

    private void saveWorkoutToDatabase(final WorkoutTemplate workoutTemplate, final SaveWorkoutCallback callback) {
        saveData(workoutTemplate, new DataResult() {
            @Override
            public void onError() {
                Log.e(TAG, "WorkoutTemplate was not succesfully saved");
                callback.onFailure("Unable to save to local data store.");
            }

            @Override
            public void onSuccess() {
                callback.onSuccess();
                mWorkoutInstance = workoutTemplate.getWorkoutInstances().get(0);
                Log.e(TAG, "WorkoutTemplate was succesfully saved");
            }
        });
    }

    private void postWorkoutTemplateToWeb(final String authToken, final WorkoutTemplate template, final WorkoutTemplatePostCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PostWorkoutTemplateTask postWorkoutTemplateTask = new PostWorkoutTemplateTask(authToken, mRequestQueue);
                postWorkoutTemplateTask.postWorkoutTemplate(template, callback);
            }
        }).start();
    }

    private void postWorkoutInstanceToWeb(final String authToken,final WorkoutInstance workoutInstance, final WorkoutInstancePostCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PostWorkoutInstanceTask postWorkoutInstanceTask = new PostWorkoutInstanceTask(authToken, mRequestQueue);
                postWorkoutInstanceTask.postWorkoutInstance(workoutInstance, callback);
            }
        }).start();
    }

    public WorkoutInstance getWorkoutInstance() {
        return mWorkoutInstance;
    }
}
