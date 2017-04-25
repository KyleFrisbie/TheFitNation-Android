package com.fitnation.workoutInstance.instance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.model.WorkoutInstance;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Erik on 4/24/2017.
 */

public class WorkoutInstanceAdapter extends RecyclerView.Adapter<WorkoutInstanceAdapter.ViewHolder> {
    private List<WorkoutInstance> mWorkouts;

    public WorkoutInstanceAdapter(List<WorkoutInstance> mWorkouts) {
        this.mWorkouts = mWorkouts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_instance_in_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WorkoutInstance workoutInstance = mWorkouts.get(position);
        holder.workoutName.setText(workoutInstance.getName());
        //TODO check to see if i want updated or created date
        holder.modifiedDate.setText(workoutInstance.getLastUpdated());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.workout_name_textView) TextView workoutName;
        @BindView(R.id.modified_date_textView) TextView modifiedDate;
        @BindView(R.id.delete_button) Button deleteWorkout;
        @BindView(R.id.launch_button) Button launchWorkout;
        @BindView(R.id.details_button) Button detailsOfWorkout;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
