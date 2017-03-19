package com.fitnation.exercise;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.fitnation.model.Exercise;

import java.util.List;

/**
 * Created by Ryan Newsom on 3/19/17. *
 */
public class ExerciseAdapter extends RecyclerView.Adapter {
    private List<Exercise> mExercises;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

//    public ExerciseAdapter(List<Exercise> exercises) {
//        mExercises = exercises;
//    }
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public TextView mTextView;
//        public ViewHolder(TextView v) {
//            super(v);
//            mTextView = v;
//        }
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                   int viewType) {
//        // create a new view
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.my_text_view, parent, false);
//        // set the view's size, margins, paddings and layout parameters
//        ViewHolder vh = new ViewHolder(v);
//        return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        holder.mTextView.setText(mDataset[position]);
//
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        if(mExercises != null) {
//            return mExercises.size();
//        } else {
//            return 0;
//        }
//    }
}
