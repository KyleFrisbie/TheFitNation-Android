package com.fitnation.workout.parent;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fitnation.R;
import com.fitnation.workout.exercise.list.ExercisesListFragment;
import com.fitnation.workout.callbacks.ExerciseSelectedCallback;
import com.fitnation.workout.callbacks.OnEditExercisePressedCallback;
import com.fitnation.model.ExerciseInstance;

import java.util.List;

/**
 * Manages the different tabs Fragments
 */
public class ExerciseSectionsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private ExerciseSelectedCallback mExerciseSelectedCallback;
    private OnEditExercisePressedCallback mOnEditExercisePressedCallback;
    private List<ExerciseInstance> mExerciseListTab1;
    private List<ExerciseInstance> mExerciseListTab2;
    private List<ExerciseInstance> mExerciseListTab3;
    private ExercisesListFragment mFragmentTab1;
    private ExercisesListFragment mFragmentTab2;
    private ExercisesListFragment mFragmentTab3;

    /**
     * Constructor
     * @param fm - fragment manager to be used
     * @param context - context
     * @param exerciseSelectedCallback- callback to be notified when an Exercise is selected
     * @param onEditExercisePressedCallback - callabck to be notified when an Exercise's edit button is pressed
     */
    public ExerciseSectionsPagerAdapter(FragmentManager fm, Context context, ExerciseSelectedCallback exerciseSelectedCallback, OnEditExercisePressedCallback onEditExercisePressedCallback) {
        super(fm);
        mContext = context;
        mExerciseSelectedCallback = exerciseSelectedCallback;
        mOnEditExercisePressedCallback = onEditExercisePressedCallback;
    }

    public void setExerciseData(List<ExerciseInstance> exerciseListTab1, List<ExerciseInstance> exerciseListTab2, List<ExerciseInstance> exerciseListTab3) {
        mExerciseListTab1 = exerciseListTab1;
        mExerciseListTab2 = exerciseListTab2;
        mExerciseListTab3 = exerciseListTab3;
    }

    public void refresh(List<ExerciseInstance> exerciseListTab1, List<ExerciseInstance> exerciseListTab2, List<ExerciseInstance> exerciseListTab3) {
        mFragmentTab1.displayExercises(ExerciseInstance.convertExercisesToExerciseViews(exerciseListTab1));
        mFragmentTab2.displayExercises(ExerciseInstance.convertExercisesToExerciseViews(exerciseListTab2));
        mFragmentTab3.displayExercises(ExerciseInstance.convertExercisesToExerciseViews(exerciseListTab3));
    }

    @Override
    public int getItemPosition(Object object) {
        int position = super.getItemPosition(object);

        return position;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                mFragmentTab1 = ExercisesListFragment.newInstance(ExerciseInstance.convertExercisesToExerciseViews(mExerciseListTab1), true, mExerciseSelectedCallback, mOnEditExercisePressedCallback);
                return mFragmentTab1;
            case 1:
                mFragmentTab2 = ExercisesListFragment.newInstance(ExerciseInstance.convertExercisesToExerciseViews(mExerciseListTab2), true, mExerciseSelectedCallback, mOnEditExercisePressedCallback);
                return mFragmentTab2;
            case 2:
                mFragmentTab3 = ExercisesListFragment.newInstance(ExerciseInstance.convertExercisesToExerciseViews(mExerciseListTab3), true, mExerciseSelectedCallback, mOnEditExercisePressedCallback);
                return mFragmentTab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        final int PAGE_COUNT = 3;

        return PAGE_COUNT;
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
