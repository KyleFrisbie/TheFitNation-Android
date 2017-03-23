package com.fitnation.exercise;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.exercise.exerciseList.ExercisesListFragment;
import com.fitnation.exercise.callbacks.ExerciseSelectedCallback;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.enums.ExerciseAction;
import com.fitnation.model.enums.SkillLevel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ryan Newsom on 3/12/17.
 */
public class ExercisesParentFragment extends BaseFragment implements ExercisesParentContract.View, ExerciseSelectedCallback {
    private ExercisesParentContract.Presenter mPresenter;
    private static final String EXERCISE_ACTION = "EXERCISE_ACTION";
    private ExerciseAction mAction;
    @BindView(R.id.exercise_list_action)
    public Button mActionButton;

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

    public ExercisesParentFragment() {
        //required empty constructor
    }

    /**
     * Creates a new Fragment
     * @return New instance of a Create Exercise Fragment
     */
    public static ExercisesParentFragment newInstance(Context context, ExerciseAction action) {
        ExercisesParentFragment fragment = new ExercisesParentFragment();
        fragment.setPresenter(new ExercisesParentPresenter(context, fragment));

        Bundle args = new Bundle();
        if(action != null) {
            args.putSerializable(EXERCISE_ACTION, action);
        }

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null) {
            mAction = (ExerciseAction) bundle.get(EXERCISE_ACTION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.workout_fragment_create_workout_parent, container, false);
        ButterKnife.bind(this, v);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new ExerciseSectionsPagerAdapter(getFragmentManager(), getContext(), this);
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mActionButton.setText(mAction.name());

        return v;
    }

    @Override
    public void setPresenter(ExercisesParentContract.Presenter presenter) {
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

    @OnClick(R.id.exercise_list_action)
    public void onActionButtonPressed() {
        mPresenter.onActionButtonClicked(mAction);
    }

    @Override
    public void displayExercises(List<ExerciseInstance> exercises) {
        //check skill level chosen by user, create list of ExerciseInstances based off that
        ExercisesListFragment beginnerFragment = (ExercisesListFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 0);
        ExercisesListFragment intermediatFragment = (ExercisesListFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 1);
        ExercisesListFragment advancedFragment = (ExercisesListFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 2);

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

        beginnerFragment.displayExercises(ExercisesManager.filterExerciseBySkillLevel(list1, SkillLevel.BEGINNER));
        intermediatFragment.displayExercises(ExercisesManager.filterExerciseBySkillLevel(list2, SkillLevel.INTERMEDIATE));
        advancedFragment.displayExercises(ExercisesManager.filterExerciseBySkillLevel(list3, SkillLevel.ADVANCED));
    }

    @Override
    public void promptUserToSave(DialogFragment alertDialog) {
        alertDialog.show(getFragmentManager(), null);
    }

    @Override
    public void onExerciseSelected(ExerciseInstance exerciseInstance, boolean isSelected) {
        mPresenter.onExerciseSelected(exerciseInstance, isSelected);
    }
}
