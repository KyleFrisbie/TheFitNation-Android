package com.fitnation.exercise.edit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.model.ExerciseInstanceSet;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ryan on 4/1/2017.
 */

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {
    public List<ExerciseInstanceSet> mSets;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.exercise_value) EditText exerciseValueView;
        @BindView(R.id.reps_value) EditText repsValueView;
        @BindView(R.id.rest_value) EditText restValueView;
        @BindView(R.id.set_order) TextView setOrderView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public SetAdapter(List<ExerciseInstanceSet> exerciseInstanceSets) {
        mSets = exerciseInstanceSets;
    }

    public List<ExerciseInstanceSet> getExerciseInstanceSets() {
        return mSets;
    }

    @Override
    public SetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_in_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SetAdapter.ViewHolder holder, int position) {
        ExerciseInstanceSet set = mSets.get(position);

        holder.setOrderView.setText(String.valueOf(set.getOrderNumber()));
        holder.exerciseValueView.setText(String.valueOf(set.getEffortQuantity()));
        holder.repsValueView.setText(String.valueOf(set.getReqQuantityAsInt()));
        holder.restValueView.setText(String.valueOf(set.getRestTime()));
    }

    @Override
    public int getItemCount() {
        if(mSets != null) {
            return mSets.size();
        } else {
            return 0;
        }
    }
}
