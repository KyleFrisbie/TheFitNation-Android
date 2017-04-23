package com.fitnation.workout.edit;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.base.Navigationable;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 */
public class EditWorkoutFragment extends BaseFragment implements EditWorkoutContract.View {
    private static final String USER_WORKOUT_INSTANCE = "USER_WORKOUT_INSTANCE";
    private static final String USER_WORKOUT_TEMPLATE = "USER_WORKOUT_TEMPLATE";
    private UserWorkoutInstance mUserWorkoutInstance;
    private UserWorkoutTemplate mUserWorkoutTemplate;
    private EditWorkoutContract.Presenter mPresenter;

    public EditWorkoutFragment() {
        // Required empty public constructor
    }

    public static EditWorkoutFragment newInstance(UserWorkoutInstance userWorkoutInstance, UserWorkoutTemplate userWorkoutTemplate) {
        Bundle args = new Bundle();
        args.putParcelable(USER_WORKOUT_INSTANCE, Parcels.wrap(userWorkoutInstance));
        args.putParcelable(USER_WORKOUT_TEMPLATE, Parcels.wrap(userWorkoutTemplate));
        EditWorkoutFragment editWorkoutFragment = new EditWorkoutFragment();
        editWorkoutFragment.setArguments(args);

        return editWorkoutFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mUserWorkoutInstance = Parcels.unwrap(args.getParcelable(USER_WORKOUT_INSTANCE));
        mUserWorkoutTemplate = Parcels.unwrap(args.getParcelable(USER_WORKOUT_TEMPLATE));

        mPresenter.setUserWorkout(mUserWorkoutInstance, mUserWorkoutTemplate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.workout_fragment_edit_workout, container, false);
        ButterKnife.bind(this, v);
        Navigationable navigationable =  (Navigationable) getBaseActivity();
        navigationable.updateToolbar(true, getString(R.string.edit_workout_title) + " " + mUserWorkoutInstance.getWorkoutInstanceName() + " " + mUserWorkoutInstance.getCreatedOn());

        mPresenter.onViewReady();

        return v;
    }

    @Override
    public void setPresenter(EditWorkoutContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void updateData(UserWorkoutInstance userWorkoutInstance) {
        mUserWorkoutInstance = userWorkoutInstance;

    }

    @OnClick(R.id.edit_workout_action_button)
    public void onActionRequested() {
        mPresenter.onSavePressed();
    }

}
