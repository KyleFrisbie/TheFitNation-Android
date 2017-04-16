package com.fitnation.workout.exercise;

import android.support.constraint.ConstraintLayout;
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
import com.fitnation.model.ExerciseSetView;
import com.fitnation.workout.callbacks.OnSetSelectedCallback;
import com.fitnation.model.ExerciseInstanceSet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Adapter for ExerciseInstanceSets
 */
public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {
    private static final String TAG = SetAdapter.class.getSimpleName();
    public List<ExerciseSetView> mSets;
    private OnSetSelectedCallback mOnSetSelectedCallback;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.exercise_value) EditText mExerciseValueView;
        @BindView(R.id.reps_value) EditText mRepsValueView;
        @BindView(R.id.rest_value) EditText mRestValueView;
        @BindView(R.id.set_order) TextView mSetOrderView;
        @BindView(R.id.root_set_in_list) ConstraintLayout mRootView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * Constructor
     * @param exerciseInstanceSets - the sets to be displayed
     */
    public SetAdapter(List<ExerciseSetView> exerciseInstanceSets, OnSetSelectedCallback callback) {
        mSets = exerciseInstanceSets;
        mOnSetSelectedCallback = callback;
    }

    public List<ExerciseSetView> getExerciseInstanceSets() {
        return mSets;
    }

    @Override
    public SetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_set_in_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SetAdapter.ViewHolder holder, int position) {
        final ExerciseSetView set = mSets.get(position);

        holder.mSetOrderView.setText("Set " + String.valueOf(set.getOrderNumber()));
        holder.mExerciseValueView.setText(String.valueOf(set.getEffortQuantity()));
        holder.mRepsValueView.setText(String.valueOf(set.getRepQuantityAsInt()));
        holder.mRestValueView.setText(String.valueOf(set.getRestTime()));

        holder.mRootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnSetSelectedCallback.onSetSelected(set);

                return true;
            }
        });

        holder.mRepsValueView.addTextChangedListener(new TextWatcher() {
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
                    set.setReqQuantity(newAmount);
                } catch (NumberFormatException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });

        holder.mExerciseValueView.addTextChangedListener(new TextWatcher() {
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

        holder.mRestValueView.addTextChangedListener(new TextWatcher() {
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
