package com.fitnation.exercise;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.model.ExerciseInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ryan Newsom on 3/12/17.
 */
public class CreateExerciseFragment extends BaseFragment implements CreateExerciseContract.View {
    private CreateExerciseContract.Presenter mPresenter;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ExerciseSectionsPagerAdapter mSectionsPagerAdapter;

    @BindView(R.id.container)
    public ViewPager mViewPager;

    public CreateExerciseFragment() {
        //required empty constructor
    }

    /**
     * Creates a new Fragment
     * @return New instance of a Create Exercise Fragment
     */
    public static CreateExerciseFragment newInstance(Context context) {
        CreateExerciseFragment fragment = new CreateExerciseFragment();
        fragment.setPresenter(new CreateExercisePresenter(context, fragment));

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_exercise_parent, container, false);
        ButterKnife.bind(this, v);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new ExerciseSectionsPagerAdapter(getFragmentManager(), getContext());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return v;
    }


    @Override
    public void setPresenter(CreateExerciseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public BaseActivity getBaseActivity() {
        return null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onViewReady();
    }

    @Override
    public void displayExercises(List<ExerciseInstance> exercises) {
        //check skill level chosen by user, create list of ExerciseInstances based off that
        ExercisesListFragment beginnerFragment = (ExercisesListFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 0);
        ExercisesListFragment intermediatFragment = (ExercisesListFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 1);
        ExercisesListFragment advancedFragment = (ExercisesListFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 2);

        //TODO filter by skill level
        List<ExerciseInstance> list1 = new ArrayList<ExerciseInstance>(exercises.size());
        List<ExerciseInstance> list2 = new ArrayList<ExerciseInstance>(exercises.size());
        List<ExerciseInstance> list3 = new ArrayList<ExerciseInstance>(exercises.size());

        for (ExerciseInstance instance : exercises) {
            ExerciseInstance copy1 = null;
            ExerciseInstance copy2 = null;
            ExerciseInstance copy3 = null;

            try {
                copy1 = (ExerciseInstance)instance.clone();
                copy2 = (ExerciseInstance)instance.clone();
                copy3 = (ExerciseInstance)instance.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            list1.add(copy1);
            list2.add(copy2);
            list3.add(copy3);
        }

        beginnerFragment.displayExercises(list1);
        intermediatFragment.displayExercises(list2);
        advancedFragment.displayExercises(list3);
    }
}
