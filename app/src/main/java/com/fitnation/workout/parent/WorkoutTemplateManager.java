package com.fitnation.workout.parent;

import android.util.Log;

import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.utils.PrimaryKeyFactory;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Ryan Newsom on 4/23/17. *
 */

public class WorkoutTemplateManager {
    private static final String TAG = WorkoutTemplateManager.class.getSimpleName();
    private WorkoutTemplateManager(){}

    /**
     * Gets a single Workout Template
     * @return - a Workout Template
     */
    public static WorkoutTemplate getSingletonWorkoutTemplate() {
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
                workoutTemplate.setAndroidId(androidKey);
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

    /**
     * Gets a single User Workout Template from a WorkoutTemplate
     * @param workoutTemplate - workout template to be used for the parent
     * @return - A UserWorkoutTemplate
     */
    public static UserWorkoutTemplate getSingletonUserWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        Realm realm = null;
        UserWorkoutTemplate userWorkoutTemplate = null;
        Log.i(TAG, "Determining workout template");
        Log.i(TAG, "Workout template was not given, so going to see if one exists");
        Long androidKey = PrimaryKeyFactory.getInstance().nextKey(UserWorkoutTemplate.class);

        try {
            realm = Realm.getDefaultInstance();

            if (androidKey == 1) {
                userWorkoutTemplate = new UserWorkoutTemplate(workoutTemplate, androidKey);
                Log.i(TAG, "Looks like none exist, going to create a new workout template");
            } else {
                Log.i(TAG, "We have at least one workout template in the DB");
                RealmResults<UserWorkoutTemplate> query = realm.where(UserWorkoutTemplate.class).findAll();
                if (query.size() == 0) {
                    Log.i(TAG, "No workout template's found in query, making a new one");
                    userWorkoutTemplate = new UserWorkoutTemplate(workoutTemplate, androidKey);
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
}
