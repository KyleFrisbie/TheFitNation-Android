package com.fitnation.model;

import com.google.gson.annotations.Expose;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A single Exercise
 */
public class Exercise extends RealmObject {
    @PrimaryKey
    @Expose(serialize = false)
    private Long androidId;
    private Long id;
    private String name;
    @Expose(serialize = false)
    private String imageUri;
    private String notes;
    private String skillLevelLevel;
    private Long skillLevelId;
    @Expose(serialize = false)
    private RealmList<Muscle> muscles;
    @Expose(serialize = false)
    private String exerciseFamilyName;

    public Exercise() {
        //default constructor
    }

    /**
     * Loaded Constructor
     *
     * @param id
     * @param name
     * @param imageUri
     * @param notes
     * @param skillLevel
     * @param muscles
     * @param exerciseFamilyName
     */
    public Exercise(Long id, String name, String imageUri, String notes, String skillLevel, List<Muscle> muscles, String exerciseFamilyName) {
        this.id = id;
        this.name = name;
        this.imageUri = imageUri;
        this.notes = notes;
        this.skillLevelLevel = skillLevel;
        this.muscles = new RealmList<Muscle>();
        for (Muscle muscle : muscles) {
            this.muscles.add(muscle);
        }
        this.exerciseFamilyName = exerciseFamilyName;
    }

    public String getSkillLevelLevel() {
        return skillLevelLevel;
    }

    public Long getSkillLevelId() {
        return skillLevelId;
    }

    public void setSkillLevelId(Long skillLevelId) {
        this.skillLevelId = skillLevelId;
    }

    public void setMuscles(RealmList<Muscle> muscles) {
        this.muscles = muscles;
    }

    public String getNotes() {
        return notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        if (androidId != null ? !androidId.equals(exercise.androidId) : exercise.androidId != null)
            return false;
        if (id != null ? !id.equals(exercise.id) : exercise.id != null) return false;
        if (muscles != null ? !muscles.equals(exercise.muscles) : exercise.muscles != null)
            return false;
        if (name != null ? !name.equals(exercise.name) : exercise.name != null) return false;
        if (imageUri != null ? !imageUri.equals(exercise.imageUri) : exercise.imageUri != null)
            return false;
        if (notes != null ? !notes.equals(exercise.notes) : exercise.notes != null) return false;
        if (skillLevelLevel != null ? !skillLevelLevel.equals(exercise.skillLevelLevel) : exercise.skillLevelLevel != null)
            return false;
        return exerciseFamilyName != null ? exerciseFamilyName.equals(exercise.exerciseFamilyName) : exercise.exerciseFamilyName == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (muscles != null ? muscles.hashCode() : 0);
        return result;
    }
}
