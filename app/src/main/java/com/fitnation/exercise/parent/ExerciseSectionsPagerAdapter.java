package com.fitnation.exercise.parent;

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
 * Manages the different tabs Fragments
 */
public class ExerciseSectionsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private ExerciseSelectedCallback mExerciseSelectedCallback;
    private OnEditExercisePressed mOnEditExercisePressed;
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
     * @param onEditExercisePressed - callabck to be notified when an Exercise's edit button is pressed
     */
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

    public void refresh(List<ExerciseInstance> exerciseListTab1, List<ExerciseInstance> exerciseListTab2, List<ExerciseInstance> exerciseListTab3) {
        mFragmentTab1.displayExercises(exerciseListTab1);
        mFragmentTab2.displayExercises(exerciseListTab2);
        mFragmentTab3.displayExercises(exerciseListTab3);
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
                mFragmentTab1 = ExercisesListFragment.newInstance(mExerciseListTab1, mExerciseSelectedCallback, mOnEditExercisePressed);
                return mFragmentTab1;
            case 1:
                mFragmentTab2 = ExercisesListFragment.newInstance(mExerciseListTab2, mExerciseSelectedCallback, mOnEditExercisePressed);
                return mFragmentTab2;
            case 2:
                mFragmentTab3 = ExercisesListFragment.newInstance(mExerciseListTab3, mExerciseSelectedCallback, mOnEditExercisePressed);
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
