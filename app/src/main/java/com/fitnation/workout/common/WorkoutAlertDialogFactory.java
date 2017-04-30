package com.fitnation.workout.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.fitnation.R;

/**
 * Created by Ryan on 4/8/2017.
 */
public class WorkoutAlertDialogFactory {
    private WorkoutAlertDialogFactory(){};

    public static AlertDialog getBuildWorkoutSuccess(Context context, String action, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.dialog_success_title));
        builder.setMessage(context.getString(R.string.dialog_build_workout_success_message) + action);
        builder.setPositiveButton("OK", onClickListener);
        builder.setCancelable(false);

        return builder.create();
    }

    public static AlertDialog getBuildWorkoutError(String error, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.dialog_error_title));
        builder.setMessage(context.getString(R.string.dialog_build_workout_failure_message) + error);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        return builder.create();
    }

    public static AlertDialog getCreateWorkoutInstanceError(String error, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.dialog_error_title));
        builder.setMessage(context.getString(R.string.dialog_create_workout_instance_failure_message) + error);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        return builder.create();
    }
}
