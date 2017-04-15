package com.fitnation.workout.parent;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.fitnation.R;
import com.fitnation.workout.callbacks.SaveDialogCallback;

/**
 * Prompts a user to save a workout
 */
public class SaveWorkoutDialogFragment extends DialogFragment {
    private EditText mEditText;
    private SaveDialogCallback mCallback;

    public static SaveWorkoutDialogFragment newInstance(SaveDialogCallback callback) {
        SaveWorkoutDialogFragment dialogFragment = new SaveWorkoutDialogFragment();
        dialogFragment.setCallback(callback);
        return dialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.workout_fragment_save_workout_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(R.string.save_workout_title);
        alertDialogBuilder.setPositiveButton("SAVE",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCallback.onSaveRequested(mEditText.getText().toString());
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.workout_fragment_save_workout_dialog, null);
        alertDialogBuilder.setView(v);
        mEditText = (EditText) v.findViewById(R.id.exercise_name_to_save);

        return alertDialogBuilder.create();
    }


    public void setCallback(SaveDialogCallback callback) {
        mCallback = callback;
    }
}
