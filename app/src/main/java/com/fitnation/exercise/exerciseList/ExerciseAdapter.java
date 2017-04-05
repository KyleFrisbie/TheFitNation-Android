package com.fitnation.exercise.exerciseList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.exercise.callbacks.ExerciseSelectedCallback;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.ExerciseInstanceSet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for displaying some ExerciseInstance's
 */
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private List<ExerciseInstance> mExercises;
    private ExerciseSelectedCallback mSelectedExercisesCallback;
    private OnEditExercisePressed mEditPressedCallback;

    /**
     * Constructor
     * @param exercises - exercises to be shown
     * @param callback - notified when an exercise is selected/unselected
     */
    public ExerciseAdapter(List<ExerciseInstance> exercises, ExerciseSelectedCallback callback, OnEditExercisePressed onEditExercisePressed) {
        mExercises = exercises;
        mSelectedExercisesCallback = callback;
        mEditPressedCallback = onEditExercisePressed;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_exercise_in_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ExerciseInstance exerciseInstance = mExercises.get(position);
        final Exercise exercise = exerciseInstance.getExercise();
        List<ExerciseInstanceSet> sets = exerciseInstance.getExerciseInstanceSets();
        String setText = "";
        String repText = "";
        int cutOff = 3;
        if(cutOff >= sets.size()) {
            cutOff = sets.size();
            showEllipses(false, holder);
        } else {
            showEllipses(true, holder);
        }

        for (int i = 0; i < cutOff; i++) {
            ExerciseInstanceSet set = sets.get(i);

            if(i == 0) {
                setText = String.valueOf(set.getOrderNumber());
                repText = String.valueOf(set.getRepQuantityAsInt());
            } else {
                setText = setText + "\n" + String.valueOf(set.getOrderNumber());
                repText = repText + "\n" + String.valueOf(set.getRepQuantityAsInt());
            }
        }

        holder.addExerciseBox.setChecked(exerciseInstance.isSelected());
        holder.exerciseName.setText(exercise.getName());
        holder.setOne.setText(setText);
        holder.setOneReps.setText(repText);
        holder.editExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditPressedCallback.onEditPressed(exerciseInstance);
            }
        });
        holder.addExerciseBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                exerciseInstance.setSelected(checked);
                mSelectedExercisesCallback.onExerciseSelected(exerciseInstance, checked);
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
        @BindView(R.id.edit_exercise_surface_view) View editExerciseButton;
        @BindView(R.id.sets_continued) View setsContinued;
        @BindView(R.id.reps_continued) View repsContinued;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void showEllipses(boolean show, ViewHolder holder) {
        if(show) {
            holder.setsContinued.setVisibility(View.VISIBLE);
            holder.repsContinued.setVisibility(View.VISIBLE);
        } else {
            holder.setsContinued.setVisibility(View.INVISIBLE);
            holder.repsContinued.setVisibility(View.INVISIBLE);
        }
    }
}
