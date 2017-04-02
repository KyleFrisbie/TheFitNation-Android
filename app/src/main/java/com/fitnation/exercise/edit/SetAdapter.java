package com.fitnation.exercise.edit;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.model.ExerciseInstanceSet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for ExerciseInstanceSets
 */
public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {
    private static final String TAG = SetAdapter.class.getSimpleName();
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

    /**
     * Constructor
     * @param exerciseInstanceSets - the sets to be displayed
     */
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
        final ExerciseInstanceSet set = mSets.get(position);

        holder.setOrderView.setText(String.valueOf(set.getOrderNumber()));
        holder.exerciseValueView.setText(String.valueOf(set.getEffortQuantity()));
        holder.repsValueView.setText(String.valueOf(set.getRepQuantityAsInt()));
        holder.restValueView.setText(String.valueOf(set.getRestTime()));

        holder.repsValueView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    Float newAmount = Float.valueOf(editable.toString());
                    set.setRepQuantity(newAmount);
                } catch (NumberFormatException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });

        holder.exerciseValueView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    Float newAmount = Float.valueOf(editable.toString());
                    set.setEffortQuantity(newAmount);
                } catch (NumberFormatException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });

        holder.restValueView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    Float newAmount = Float.valueOf(editable.toString());
                    set.setRestTime(newAmount);
                } catch (NumberFormatException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
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
