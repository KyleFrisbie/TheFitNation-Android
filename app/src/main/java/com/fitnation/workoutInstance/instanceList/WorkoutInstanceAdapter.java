package com.fitnation.workoutInstance.instanceList;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.workoutInstance.callbacks.OnWorkoutDeletePressedCallback;
import com.fitnation.workoutInstance.callbacks.OnWorkoutDetailsPressedCallback;
import com.fitnation.workoutInstance.callbacks.OnWorkoutLaunchPressedCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for displaying workout instance's
 */

public class WorkoutInstanceAdapter extends RecyclerView.Adapter<WorkoutInstanceAdapter.ViewHolder> {
    private static final String TAG = WorkoutInstanceAdapter.class.getSimpleName();
    private List<WorkoutInstance> mWorkouts;
    private List<UserWorkoutInstance> mUserWorkouts;
    private OnWorkoutDeletePressedCallback mOnWorkoutDeletePressedCallback;
    private OnWorkoutLaunchPressedCallback mOnWorkoutLaunchPressedCallback;
    private OnWorkoutDetailsPressedCallback mOnWorkoutDetailsPressedCallback;

    public WorkoutInstanceAdapter(List<WorkoutInstance> mWorkouts, OnWorkoutDeletePressedCallback mOnWorkoutDeletePressedCallback, OnWorkoutLaunchPressedCallback mOnWorkoutLaunchPressedCallback, OnWorkoutDetailsPressedCallback mOnWorkoutDetailsPressedCallback) {
        this.mWorkouts = mWorkouts;
        this.mOnWorkoutDeletePressedCallback = mOnWorkoutDeletePressedCallback;
        this.mOnWorkoutLaunchPressedCallback = mOnWorkoutLaunchPressedCallback;
        this.mOnWorkoutDetailsPressedCallback = mOnWorkoutDetailsPressedCallback;
    }

    @Override
    public WorkoutInstanceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_instance_in_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WorkoutInstance workoutInstance = mWorkouts.get(position);

        holder.workoutName.setText(workoutInstance.getName());
        holder.modifiedDate.setText(workoutInstance.getLastUpdated());
        holder.deleteWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnWorkoutDeletePressedCallback.onDeletePressed(workoutInstance);
            }
        });
        holder.launchWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnWorkoutLaunchPressedCallback.onLaunchPressed(workoutInstance);
            }
        });
        holder.workoutDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnWorkoutDetailsPressedCallback.onDetailsPressed(workoutInstance);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return mWorkouts.get(position).getId();
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount()" + " array size: " + mWorkouts.size());
        if(mWorkouts != null) {
            return mWorkouts.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.workout_name_textView) TextView workoutName;
        @BindView(R.id.modified_date_textView) TextView modifiedDate;
        @BindView(R.id.delete_button) Button deleteWorkoutButton;
        @BindView(R.id.launch_button) Button launchWorkoutButton;
        @BindView(R.id.details_button) Button workoutDetailsButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
