package com.fitnation.exercise;

/**
 * Created by Ryan Newsom on 3/12/17. *
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fitnation.R;
import com.fitnation.model.Exercise;
import com.fitnation.model.enums.ExerciseAction;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ExerciseSectionsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public ExerciseSectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return ExercisesListFragment.newInstance(null);
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
