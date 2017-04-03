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
import com.fitnation.exercise.callbacks.ExercisesRequestCallback;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.PrimaryKeyFactory;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.model.enums.SkillLevel;
import com.fitnation.networking.EnvironmentManager;
import com.fitnation.networking.JsonParser;

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
                    // Instantiate the RequestQueue.
                    String resourceRoute = "exercises";

                    String url = EnvironmentManager.getInstance().getRequestUrl(resourceRoute);
                    StringRequest getExercisesRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    List<Exercise> exercises = JsonParser.convertJsonStringToList(response, Exercise[].class);
                                    List<ExerciseInstance> exerciseInstances = convertExercisesToInstances(exercises);
                                    mExerciseInstances = exerciseInstances;
                                    mExerciseInstancesTab1 = new ArrayList<ExerciseInstance>(exercises.size());
                                    mExerciseInstancesTab2 = new ArrayList<ExerciseInstance>(exercises.size());
                                    mExerciseInstancesTab3 = new ArrayList<ExerciseInstance>(exercises.size());

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
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(TAG, error.toString());
                                    callback.onError();
                                }
                            }
                    ) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            String authToken = "57092971-0599-4271-82d7-603435791f03";
                            Map<String, String> mHeaders = new ArrayMap();

                            mHeaders.put("Authorization", "Bearer" + " " + authToken);
                            mHeaders.put("Content-Type", "application/json");

                            return mHeaders;
                        }
                    };

                    getExercisesRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

                    mRequestQueue.add(getExercisesRequest);
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

    /**
     * Creates a workout out of the currently selected exercises and saves it under the given name
     * @param name - The name of the workout
     */
    public void createWorkoutAndSave(final String name, final WorkoutTemplate workoutTemplate) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Realm realm = Realm.getDefaultInstance();

                WorkoutTemplate trueWorkoutTemplate = getWorkoutTemplate(workoutTemplate, realm);
                WorkoutInstance workoutInstanceTemplate = new WorkoutInstance(name, 0f, 1, trueWorkoutTemplate, "");
                RealmList<ExerciseInstance> selectedExercises = new RealmList<>();

                for (ExerciseInstance exerciseInstance : mSelectedExercises) {
                    selectedExercises.add(exerciseInstance);
                }

                workoutInstanceTemplate.setExercises(selectedExercises);
                workoutInstanceTemplate.setAndroidId(PrimaryKeyFactory.getInstance().nextKey(WorkoutInstance.class));
                saveData(trueWorkoutTemplate, new DataResult() {
                    @Override
                    public void onError() {
                        Log.e(TAG, "WorkoutTemplate was not succesfully saved");
                        realm.close();
                    }

                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "WorkoutTemplate was succesfully saved");
                    }
                });
            }
        }).start();




    }

    private WorkoutTemplate getWorkoutTemplate(WorkoutTemplate workoutTemplate, Realm realm) {
        Log.i(TAG, "Determining workout template");

        if(workoutTemplate == null) {
            Log.i(TAG, "Workout template was not given, so going to see if one exists");
            Long androidKey = PrimaryKeyFactory.getInstance().nextKey(WorkoutTemplate.class);

            if (androidKey == 1) {
                workoutTemplate = new WorkoutTemplate();
                workoutTemplate.setAndroidId(PrimaryKeyFactory.getInstance().nextKey(WorkoutTemplate.class));
                Log.i(TAG, "Looks like none exist, going to create a new workout template");
            } else {
                Log.i(TAG, "We have at least one workout template in the DB");
                RealmResults<WorkoutTemplate> query = realm.where(WorkoutTemplate.class).findAll();
                if (query.size() == 0) {
                    Log.i(TAG, "No workout template's found in query, making a new one");
                    workoutTemplate = new WorkoutTemplate();
                    workoutTemplate.setAndroidId(PrimaryKeyFactory.getInstance().nextKey(WorkoutTemplate.class));
                } else {
                    Log.i(TAG, "Found the workout template");
                    workoutTemplate = query.first();
                }
            }

        }

        return workoutTemplate;
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

    private List<ExerciseInstance> convertExercisesToInstances(List<Exercise> exercises) {
        List<ExerciseInstance> exerciseInstances = new ArrayList<>(exercises.size());
        for (Exercise exercise: exercises) {
            ExerciseInstance instance = new ExerciseInstance(exercise);
            exerciseInstances.add(instance);
        }

        return exerciseInstances;
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
