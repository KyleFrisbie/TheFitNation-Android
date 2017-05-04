package com.fitnation.workout.create.parent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.base.Navigationable;
import com.fitnation.model.ExerciseView;
import com.fitnation.workout.create.list.ExercisesListFragment;
import com.fitnation.workout.callbacks.ExerciseSelectedCallback;
import com.fitnation.workout.callbacks.OnEditExercisePressedCallback;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.enums.ExerciseAction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ryan Newsom on 3/12/17.
 */
public class ExercisesParentFragment extends BaseFragment implements ExercisesParentContract.View, ExerciseSelectedCallback, OnEditExercisePressedCallback {
    private static final String TAG = ExercisesParentFragment.class.getSimpleName();
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
        mSectionsPagerAdapter = new ExerciseSectionsPagerAdapter(getChildFragmentManager(), getContext(), this, this);
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter.notifyDataSetChanged();

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mActionButton.setText(mAction.name());

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated()");
    }

    @Override
    public void setPresenter(ExercisesParentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void showProgress() {
        BaseActivity baseActivity = getBaseActivity();
        if(baseActivity != null) {
            baseActivity.showProgress(null);
        }
    }

    @Override
    public void stopProgress() {
        final BaseActivity baseActivity = getBaseActivity();
        if(baseActivity != null) {
            baseActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    baseActivity.stopProgress();
                }
            });
        }
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart()");
        super.onStart();
        mPresenter.onViewReady();
        ((Navigationable) getActivity()).updateToolbar(true, "Build A Workout");
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume()");
        super.onResume();
    }

    @OnClick(R.id.exercise_list_action)
    public void onActionButtonPressed() {
        mPresenter.onActionButtonClicked(mAction);
    }

    @Override
    public void displayExercises(List<ExerciseInstance> tabOneExercises, List<ExerciseInstance> tabTwoExercises, List<ExerciseInstance> tabThreeExercises) {
        //check skill level chosen by user, create list of ExerciseInstances based off that
        ExercisesListFragment beginnerFragment = (ExercisesListFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 0);
        ExercisesListFragment intermediatFragment = (ExercisesListFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 1);
        ExercisesListFragment advancedFragment = (ExercisesListFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 2);

        mSectionsPagerAdapter.setExerciseData(tabOneExercises, tabTwoExercises, tabThreeExercises);

        beginnerFragment.displayExercises(ExerciseInstance.convertExercisesToExerciseViews(tabOneExercises));
        intermediatFragment.displayExercises(ExerciseInstance.convertExercisesToExerciseViews(tabTwoExercises));
        advancedFragment.displayExercises(ExerciseInstance.convertExercisesToExerciseViews(tabThreeExercises));
    }

    @Override
    public void displayUpdatedExercises(List<ExerciseInstance> exerciseListTab1, List<ExerciseInstance> exerciseListTab2, List<ExerciseInstance> exerciseListTab3) {
        mSectionsPagerAdapter.notifyDataSetChanged();
        mSectionsPagerAdapter.refresh(exerciseListTab1, exerciseListTab2, exerciseListTab3);
    }

    @Override
    public void promptUserToSave(DialogFragment alertDialog) {
        alertDialog.show(getFragmentManager(), null);
    }

    @Override
    public int getSelectedTab() {
        int selected = -1;

        if(mViewPager != null) {
            selected = mViewPager.getCurrentItem();
        }

        return selected;
    }

    @Override
    public void showSuccess(final AlertDialog alertDialog) {
        getBaseActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
    }

    @Override
    public void showFailure(final AlertDialog alertDialog) {
        getBaseActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
    }

    @Override
    public void setSaveButtonEnabled(boolean shouldEnable) {
        mActionButton.setEnabled(shouldEnable);
    }

    @Override
    public void onExerciseSelected(ExerciseView exerciseView, boolean isSelected) {
        ExerciseInstance exerciseInstance = (ExerciseInstance) exerciseView;
        mPresenter.onExerciseSelected(exerciseInstance, isSelected);
    }

    @Override
    public void onEditPressed(ExerciseView exercise) {
        mPresenter.onEditPressed((ExerciseInstance) exercise);
    }
}
