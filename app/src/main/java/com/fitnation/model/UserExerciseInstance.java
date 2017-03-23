package com.fitnation.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Ryan on 3/22/2017.
 */
public class UserExerciseInstance extends RealmObject{
    private Long id;
    private String notes;
    private UserWorkoutInstance userWorkoutInstance;
    private ExerciseInstance exerciseInstance;
    private RealmList<UserExerciseInstanceSet> userExerciseInstanceSets;

    /**
     * Constructor
     * @param notes - the notes for the exercise
     * @param userWorkoutInstance - The workout instance this exercise belongs to
     * @param exerciseInstance - The exercise instance of whom this a child
     * @param userExerciseInstanceSets - Exercise instance sets
     */
    public UserExerciseInstance(String notes, UserWorkoutInstance userWorkoutInstance, ExerciseInstance exerciseInstance, RealmList<UserExerciseInstanceSet> userExerciseInstanceSets) {
        this.notes = notes;
        this.userWorkoutInstance = userWorkoutInstance;
        this.exerciseInstance = exerciseInstance;
        this.userExerciseInstanceSets = userExerciseInstanceSets;
    }

    public UserExerciseInstance(ExerciseInstance exerciseInstance, String userNotes, UserWorkoutInstance userWorkoutInstance) {
        this.notes = userNotes;
        this.userWorkoutInstance = userWorkoutInstance;
        this.exerciseInstance = exerciseInstance;
        userExerciseInstanceSets = new RealmList<>();

        for (ExerciseInstanceSet set: exerciseInstance.getExerciseInstanceSets()) {
            userExerciseInstanceSets.add(new UserExerciseInstanceSet(set, this));
        }
    }

    public UserExerciseInstance() {
        //Required default constructor
    }

    public ExerciseInstance getExerciseInstance() {
        return exerciseInstance;
    }
}
