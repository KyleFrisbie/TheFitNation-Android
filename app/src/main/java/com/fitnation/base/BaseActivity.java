package com.fitnation.base;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * BaseActivity to be used by all Activities in the project
 */
public class BaseActivity extends AppCompatActivity {
    ProgressDialog mProgress;

    /**
     * Shows a progress dialog
     * @param message - message to be displayed to the user
     */
    public void showProgress(@Nullable  String message) {
        if(message == null) {
            message = "Loading...";
        }
        mProgress = new ProgressDialog(this);
        mProgress.setMessage(message);
        mProgress.show();
    }

    public void stopProgress() {
        if(mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopProgress();
    }
}
