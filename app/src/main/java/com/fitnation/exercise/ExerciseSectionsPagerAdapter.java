package com.fitnation.exercise;

/**
 * Created by Ryan Newsom on 3/12/17. *
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fitnation.R;
import com.fitnation.exercise.exerciseList.ExercisesListFragment;
import com.fitnation.exercise.callbacks.ExerciseSelectedCallback;
import com.fitnation.exercise.exerciseList.OnEditExercisePressed;
import com.fitnation.model.ExerciseInstance;

import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ExerciseSectionsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private ExerciseSelectedCallback mExerciseSelectedCallback;
    private OnEditExercisePressed mOnEditExercisePressed;
    private List<ExerciseInstance> mExerciseListTab1;
    private List<ExerciseInstance> mExerciseListTab2;
    private List<ExerciseInstance> mExerciseListTab3;

    public ExerciseSectionsPagerAdapter(FragmentManager fm, Context context, ExerciseSelectedCallback exerciseSelectedCallback, OnEditExercisePressed onEditExercisePressed) {
        super(fm);
        mContext = context;
        mExerciseSelectedCallback = exerciseSelectedCallback;
        mOnEditExercisePressed = onEditExercisePressed;
    }

    public void setExerciseData(List<ExerciseInstance> exerciseListTab1, List<ExerciseInstance> exerciseListTab2, List<ExerciseInstance> exerciseListTab3) {
        mExerciseListTab1 = exerciseListTab1;
        mExerciseListTab2 = exerciseListTab2;
        mExerciseListTab3 = exerciseListTab3;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return ExercisesListFragment.newInstance(mExerciseListTab1, mExerciseSelectedCallback, mOnEditExercisePressed);
            case 1:
                return ExercisesListFragment.newInstance(mExerciseListTab2, mExerciseSelectedCallback, mOnEditExercisePressed);
            case 2:
                return ExercisesListFragment.newInstance(mExerciseListTab3, mExerciseSelectedCallback, mOnEditExercisePressed);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.beginner);
            case 1:
                return mContext.getString(R.string.intermediate);
            case 2:
                return mContext.getString(R.string.advanced);
        }

        return null;
    }
}
