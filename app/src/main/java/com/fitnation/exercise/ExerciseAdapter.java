package com.fitnation.exercise;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.ExerciseInstanceSet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ryan Newsom on 3/19/17. *
 */
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private List<ExerciseInstance> mExercises;

    /**
     * Constructor
     * @param exercises
     */
    public ExerciseAdapter(List<ExerciseInstance> exercises) {
        mExercises = exercises;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_in_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ExerciseInstance exerciseInstance = mExercises.get(position);
        Exercise exercise = exerciseInstance.getExercise();
        List<ExerciseInstanceSet> sets = exerciseInstance.getExerciseInstanceSets();
        String setText = "";
        String repText = "";
        int cutOff = 3;
        if(cutOff >= sets.size()) {
            cutOff = sets.size();
        }

        for (int i = 0; i < cutOff; i++) {
            ExerciseInstanceSet set = sets.get(i);

            if(i == 0) {
                setText = String.valueOf(set.getOrderNumber());
                repText = String.valueOf(set.getReqQuantity());
            } else {
                setText = setText + "\n" + String.valueOf(set.getOrderNumber());
                repText = repText + "\n" + String.valueOf(set.getReqQuantity());
            }
        }

        holder.addExerciseBox.setChecked(exerciseInstance.isSelected());
        holder.exerciseName.setText(exercise.getName());
        holder.setOne.setText(setText);
        holder.setOneReps.setText(repText);
        //TODO add click listener to edit ImageView
        holder.addExerciseBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                exerciseInstance.setSelected(checked);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return mExercises.get(position).getId();
    }

    @Override
    public int getItemCount() {
        if(mExercises != null) {
            return mExercises.size();
        } else {
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.add_exercise_box) CheckBox addExerciseBox;
        @BindView(R.id.exercise_name) TextView exerciseName;
        @BindView(R.id.set_one) TextView setOne;
        @BindView(R.id.set_one_reps) TextView setOneReps;
        @BindView(R.id.edit_exercise) ImageView editExercise;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
