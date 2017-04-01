package com.fitnation.exercise.edit;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Allows the user to Edit an
 */
public class ViewExerciseFragment extends BaseFragment implements ViewExerciseContract.View {
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
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public ViewExerciseFragment() {
        // Required empty public constructor
    }

    public static ViewExerciseFragment newInstance(ExerciseInstance exerciseInstance) {
        ViewExerciseFragment viewExerciseFragment = new ViewExerciseFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXERCISE_KEY, exerciseInstance);
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
            mExerciseInstance = (ExerciseInstance) args.getSerializable(EXERCISE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.exercise_fragment_view_exercise, container, false);
        ButterKnife.bind(this, v);

        mExerciseNameView.setText(mExerciseInstance.getExercise().getName());
        mLevelView.setText(mExerciseInstance.getExercise().getSkillLevelLevel());
        mNotesView.setText(mExerciseInstance.getNotes());
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SetAdapter(mExerciseInstance.getExerciseInstanceSets());

        return v;
    }

    @OnClick(R.id.add_set)
    public void onAddSetClicked() {
        mPresenter.onAddSetClicked();
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
