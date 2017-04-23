package com.fitnation.workout.edit;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Allows a user to save a User Workout Instance
 */
public class SaveUserWorkoutInstanceFragment extends BaseFragment implements SaveWorkoutContract.View {
    private static final String USER_WORKOUT_INSTANCE = "USER_WORKOUT_INSTANCE";
    private static final String USER_WORKOUT_TEMPLATE = "USER_WORKOUT_TEMPLATE";
    @BindView(R.id.save_user_workout_instance_button)
    public Button mActionButton;
    @BindView(R.id.notes_edittext)
    public EditText mNotesEditText;
    @BindView(R.id.completed_checkbox)
    public CheckBox mCompletedCheckBox;
    private SaveWorkoutContract.Presenter mPresenter;
    private UserWorkoutInstance mUserWorkoutInstance;
    private UserWorkoutTemplate mUserWorkoutTemplate;

    public SaveUserWorkoutInstanceFragment() {
        // Required empty public constructor
    }

    public static SaveUserWorkoutInstanceFragment newInstance(UserWorkoutInstance userWorkoutInstance, UserWorkoutTemplate userWorkoutTemplate) {
        Bundle args = new Bundle();
        args.putParcelable(USER_WORKOUT_INSTANCE, Parcels.wrap(userWorkoutInstance));
        args.putParcelable(USER_WORKOUT_TEMPLATE, Parcels.wrap(userWorkoutTemplate));

        SaveUserWorkoutInstanceFragment saveUserWorkoutInstanceFragment = new SaveUserWorkoutInstanceFragment();
        saveUserWorkoutInstanceFragment.setArguments(args);

        return saveUserWorkoutInstanceFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        if(args != null) {
            mUserWorkoutInstance = Parcels.unwrap(args.getParcelable(USER_WORKOUT_INSTANCE));
            mUserWorkoutTemplate = Parcels.unwrap(args.getParcelable(USER_WORKOUT_TEMPLATE));
        }

        mPresenter.setWorkoutData(mUserWorkoutInstance, mUserWorkoutTemplate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.workout_fragment_save_user_workout_instance, container, false);
        ButterKnife.bind(this, v);

        String notes = mUserWorkoutInstance.getNotes();
        if(notes != null) {
            mNotesEditText.setText(notes);
        }

        if(mUserWorkoutInstance.wasCompleted()) {
            mCompletedCheckBox.setChecked(true);
        }

        return v;
    }

    @OnCheckedChanged(R.id.completed_checkbox)
    public void onCheckedChanged(boolean checked) {
        mUserWorkoutInstance.setWasCompleted(checked);
    }

    @OnClick(R.id.save_user_workout_instance_button)
    public void onSaveRequested() {
        mPresenter.onSavePressed();
    }

    @OnTextChanged(R.id.notes_edittext)
    public void onTextChanged(CharSequence charSequence) {
        mUserWorkoutInstance.setNotes(charSequence.toString());
    }

    @Override
    public void setPresenter(SaveWorkoutContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void showProgress() {
        Activity activity = getActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getBaseActivity().showProgress(null);
            }
        });    }

    @Override
    public void stopProgress() {
        Activity activity = getActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getBaseActivity().stopProgress();
            }
        });
    }

    @Override
    public void showError(final AlertDialog alertDialog) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
    }
}
