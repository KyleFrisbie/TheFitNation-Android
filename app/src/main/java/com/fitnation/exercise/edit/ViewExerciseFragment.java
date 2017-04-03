package com.fitnation.exercise.edit;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.exercise.callbacks.OnSetSelectedCallback;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.navigation.NavigationActivity;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Allows the user to Edit an Exercise Instance
 */
public class ViewExerciseFragment extends BaseFragment implements ViewExerciseContract.View {
    private static final String TAG = ViewExerciseFragment.class.getSimpleName();
    private static final String EXERCISE_KEY = "EXERCISE_KEY";
    private ExerciseInstance mExerciseInstance;
    private ViewExerciseContract.Presenter mPresenter;

    @BindView(R.id.exercise_name_edit)
    public TextView mExerciseNameView;
    @BindView(R.id.level_value)
    public TextView mLevelView;
    @BindView(R.id.notes)
    public TextView mNotesView;
    @BindView(R.id.sets_recycler_view)
    public RecyclerView mRecyclerView;
    private SetAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public ViewExerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance
     * @param exerciseInstance - the exercise that is being edited
     * @return - A new instance for the passed in exercise
     */
    public static ViewExerciseFragment newInstance(ExerciseInstance exerciseInstance) {
        ViewExerciseFragment viewExerciseFragment = new ViewExerciseFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXERCISE_KEY, Parcels.wrap(exerciseInstance));
        viewExerciseFragment.setArguments(args);

        return viewExerciseFragment;
    }

    @Override
    public void setPresenter(ViewExerciseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        if(args != null) {
            mExerciseInstance = Parcels.unwrap(args.getParcelable(EXERCISE_KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.exercise_fragment_view_exercise, container, false);
        ButterKnife.bind(this, v);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mPresenter.onViewReady();

        return v;
    }

    @Override
    public void bindExerciseInstanceToView(ExerciseInstance exerciseInstance, OnSetSelectedCallback callback) {
        mExerciseNameView.setText(exerciseInstance.getExercise().getName());
        mLevelView.setText(exerciseInstance.getExercise().getSkillLevelLevel());
        mNotesView.setText(exerciseInstance.getNotes());
        mAdapter = new SetAdapter(exerciseInstance.getExerciseInstanceSets(), callback);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        NavigationActivity navigationActivity = (NavigationActivity) getBaseActivity();
        navigationActivity.displayBackArrow(true, "Edit");
    }

    @OnClick(R.id.add_set_button)
    public void onAddSetClicked() {
        mPresenter.onAddSetClicked();
    }

    @OnClick(R.id.save_button)
    public void onSaveClicked() {
        mExerciseInstance.setExerciseInstanceSets(mAdapter.getExerciseInstanceSets());
        mPresenter.onSaveClicked(mExerciseInstance);
    }

    @OnClick(R.id.reset_button)
    public void onResetClicked() {
        mPresenter.onResetClicked();
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void onStop() {
        if(mExerciseInstance != null) {
            mExerciseInstance.setExerciseInstanceSets(mAdapter.getExerciseInstanceSets());
            mPresenter.onExit(mExerciseInstance);
        }
        super.onStop();
    }
}
