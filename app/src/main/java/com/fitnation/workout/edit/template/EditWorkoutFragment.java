package com.fitnation.workout.edit.template;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.base.Navigationable;
import com.fitnation.model.WorkoutInstance;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditWorkoutFragment extends BaseFragment implements EditWorkoutContract.View{
    private static final String WORKOUT_INSTANCE = "WORKOUT_INSTANCE";
    private WorkoutInstance mWorkoutInstance;
    private EditWorkoutContract.Presenter mPresenter;

    public EditWorkoutFragment() {
        // Required empty public constructor
    }

    public static EditWorkoutFragment newInstance(WorkoutInstance workoutInstance) {
        Bundle args = new Bundle();
        args.putParcelable(WORKOUT_INSTANCE, Parcels.wrap(workoutInstance));
        EditWorkoutFragment editWorkoutFragment = new EditWorkoutFragment();
        editWorkoutFragment.setArguments(args);

        return editWorkoutFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mWorkoutInstance = Parcels.unwrap(args.getParcelable(WORKOUT_INSTANCE));

        mPresenter.setWorkout(mWorkoutInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_workout, container, false);
        ButterKnife.bind(this, v);
        Navigationable navigationable =  (Navigationable) getBaseActivity();
        navigationable.updateToolbar(true, getString(R.string.edit_workout_title) + " " + mWorkoutInstance.getName());

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

    @OnClick(R.id.edit_workout_instance_action_button)
    public void onActionRequested() {
        mPresenter.onSavePressed();
    }

    @Override
    public void showSuccess(final AlertDialog alertDialog) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
    }

    @Override
    public void showFailure(final AlertDialog alertDialog) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
    }

    @Override
    public void showProgress() {
        getBaseActivity().showProgress("Saving...");
    }

    @Override
    public void hideProgress() {
        getBaseActivity().stopProgress();
    }
}
