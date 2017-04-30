package com.fitnation.model;

import android.support.annotation.NonNull;

import com.fitnation.utils.DateFormatter;
import com.fitnation.utils.PrimaryKeyFactory;
import com.google.gson.annotations.Expose;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.UserWorkoutInstanceRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * A workout that has/will been/be performed by a User
 */
@Parcel(implementations = {UserWorkoutInstanceRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { UserWorkoutInstance.class })
public class UserWorkoutInstance extends RealmObject implements Cloneable, Comparable, WorkoutView {
    @PrimaryKey
    @Expose(serialize = false)
    private Long androidId;
    private Long id;
    private String createdOn;
    private String lastUpdated;
    private boolean wasCompleted;
    private Long userWorkoutTemplateId;
    private Long workoutInstanceId;
    private String workoutInstanceName;
    private String notes;
    private RealmList<UserExerciseInstance> userExerciseInstances;

    public UserWorkoutInstance() {
        //required default constructor
    }

    public UserWorkoutInstance(WorkoutInstance workoutInstance, long androidId) {
        createdOn = DateFormatter.getFormattedDate(new Date());
        lastUpdated = DateFormatter.getFormattedDate(new Date());
        workoutInstanceId = workoutInstance.getId();
        workoutInstanceName = workoutInstance.getName();
        notes = workoutInstance.getNotes();
        userExerciseInstances = new RealmList<>();
        for (ExerciseInstance exerciseInstance :
                workoutInstance.getExerciseInstances()) {
            UserExerciseInstance userExerciseInstance = new UserExerciseInstance(exerciseInstance, this);
            userExerciseInstances.add(userExerciseInstance);
        }
    }

    public static long getNextAndroidKey() {
        return PrimaryKeyFactory.getInstance().nextKey(UserWorkoutInstance.class);
    }

    public static List<WorkoutView> convertWorkoutsToWorkoutViews(List<UserWorkoutInstance> userWorkoutInstances) {
        List<WorkoutView> workoutViews = null;

        if (userWorkoutInstances != null) {
            workoutViews = new ArrayList<>();
            for (UserWorkoutInstance userWorkoutInstance : userWorkoutInstances) {
                workoutViews.add(userWorkoutInstance);
            }
        }

        return workoutViews;
    }

    public void setUserWorkoutTemplateId(Long userWorkoutTemplateId) {
        this.userWorkoutTemplateId = userWorkoutTemplateId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getWorkoutTemplateId() {
        return null;
    }

    public Long getAndroidId() {
        return androidId;
    }

    public void setAndroidId(Long androidId) {
        this.androidId = androidId;
    }

    public boolean wasCompleted() {
        return wasCompleted;
    }

    public void initAndroidId() {
        androidId = PrimaryKeyFactory.getInstance().nextKey(UserWorkoutInstance.class);
    }

    public String getCreatedOn() {
        return DateFormatter.getUIDate(createdOn);

    }

    @ParcelPropertyConverter(UserExerciseInstanceParcelConverter.class)
    public void setExerciseInstanceSets(RealmList<UserExerciseInstance> userExerciseInstances) {
        this.userExerciseInstances = userExerciseInstances;
    }

    public RealmList<UserExerciseInstance> getUserExerciseInstances() {
        return userExerciseInstances;
    }

    public String getWorkoutInstanceName() {
        return workoutInstanceName;
    }

    public void setWorkoutInstanceName(String workoutInstanceName) {
        this.workoutInstanceName = workoutInstanceName;
    }

    public void setWasCompleted(boolean wasCompleted) {
        this.wasCompleted = wasCompleted;
    }

    @Override
    public String getName() {
        return workoutInstanceName;
    }

    @Override
    public String getLastUpdated() {
        return null;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<ExerciseView> getExerciseViews() {
        List<ExerciseView> exerciseViews = new ArrayList<>(userExerciseInstances.size());
        for (UserExerciseInstance exercise : userExerciseInstances) {
            exerciseViews.add(exercise);
        }

        return exerciseViews;
    }

    @Override
    public String toString() {
        return "UserWorkoutInstance{" +
            "id=" + id +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserWorkoutInstance that = (UserWorkoutInstance) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return workoutInstanceId != null ? workoutInstanceId.equals(that.workoutInstanceId) : that.workoutInstanceId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (workoutInstanceId != null ? workoutInstanceId.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
