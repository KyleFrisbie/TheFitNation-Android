package com.fitnation.exercise;

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
import com.fitnation.model.UserDemographic;
import com.fitnation.model.UserExerciseInstance;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.networking.EnvironmentManager;
import com.fitnation.networking.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Ryan on 3/21/2017.
 */
public class ExercisesManager extends DataManager{
    private static final String TAG = ExercisesManager.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private List<ExerciseInstance> mSelectedExercises;


    public ExercisesManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mSelectedExercises = new ArrayList<>();
    }

    public void getExercises(final ExercisesRequestCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Instantiate the RequestQueue.
                String resourceRoute = "exercises";

                String url = EnvironmentManager.getInstance().getRequestUrl(resourceRoute);
                StringRequest getExercisesRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                List<Exercise> exercises = JsonParser.convertJsonStringToList(response, Exercise[].class);
                                List<ExerciseInstance> exerciseInstances = convertExercisesToInstances(exercises);
                                callback.onExercisesRetrieved(exerciseInstances);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, error.toString());
                                callback.onError();
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        String authToken = "4339b8dd-312d-4c54-b10a-02a107eb81a7";
                        Map<String, String> mHeaders = new ArrayMap();

                        mHeaders.put("Authorization", "Bearer" + " " + authToken);
                        mHeaders.put("Content-Type", "application/json");

                        return mHeaders;
                    }
                };

                getExercisesRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

                mRequestQueue.add(getExercisesRequest);
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
    public void createWorkoutAndSave(String name, WorkoutTemplate workoutTemplate) {
        WorkoutTemplate trueWorkoutTemplate = getWorkoutTemplate(workoutTemplate);
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
            }

            @Override
            public void onSuccess() {
                Log.e(TAG, "WorkoutTemplate was succesfully saved");
            }
        });

        //TODO Save the UserWorkoutTemplate to the web service
    }

    private WorkoutTemplate getWorkoutTemplate(WorkoutTemplate workoutTemplate) {
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
                Realm realm = Realm.getDefaultInstance();
                RealmResults<WorkoutTemplate> query = realm.where(WorkoutTemplate.class).findAll();
                realm.close();
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

    private RealmList<UserExerciseInstance> getUserExerciseInstancesFromTemplateInstances(List<ExerciseInstance> selectedExercises, UserWorkoutInstance workoutInstance) {
        RealmList<UserExerciseInstance> userExerciseInstances = new RealmList<>();

        for (ExerciseInstance selectedExercise : selectedExercises) {
            userExerciseInstances.add(new UserExerciseInstance(selectedExercise, selectedExercise.getNotes(), workoutInstance));
        }

        return userExerciseInstances;
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
