package com.fitnation.networking.tasks.loginTasks;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factory.FactoryCallback;
import com.fitnation.Factory.VolleyErrorMessage;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.utils.EnvironmentManager;

import java.util.HashMap;
import java.util.Map;

/**
 * handles the email reset password request
 */

public class EmailResetPasswordTask implements FactoryCallback.FactoryReturn{
    private TaskCallback.Presenter mPresenter;
    private BaseActivity mActivity;

    /**
     * Constructor
     * @param activity The base calling activity
     * @param presenter The interface to be used
     */
    public EmailResetPasswordTask(BaseActivity activity, TaskCallback.Presenter presenter) {
        this.mPresenter = presenter;
        this.mActivity = activity;
    }

    /**
     * Request to the server to reset a users password by an email address
     * @param email The email associated with the account
     */
    public void resetPasswordRequest(final String email){
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        String endpoint = "api/account/reset_password/init";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getBaseUrl() + endpoint;

        ProgressDialog progressDialog = new ProgressDialog(mActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(mActivity.getString(R.string.progress_message));
        progressDialog.setIndeterminate(true);
        mPresenter.showProgress(progressDialog);

        StringRequest resetPasswordWithEmailRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                mPresenter.stopProgress();
                successfulResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPresenter.stopProgress();
                errorResponseMessage(error);
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return email.getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "text/plain");
                return params;
            }
        };

        resetPasswordWithEmailRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));
        requestQueue.add(resetPasswordWithEmailRequest);
    }

    /**
     * Shows a success alert dialog indicating to user that an email has been sent
     * @param response The response from the server
     */
    private void successfulResponse(String response) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
        alertDialog.setTitle(R.string.email_success_title);
        alertDialog.setMessage(response);
        alertDialog.setPositiveButton(R.string.alert_dialog_ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.create();
        mPresenter.showSuccess(alertDialog);
    }

    /**
     * Error response to be generated from volleyErrorFactory
     * @param error Volleys error object
     */
    private void errorResponseMessage(VolleyError error){
        VolleyErrorMessage volleyErrorMessage = new VolleyErrorMessage(mActivity, this);
        volleyErrorMessage.getErrorMessage(error);
    }

    /*--------------------------------------FactoryCallback--------------------------------------*/

    @Override
    public void showSuccessDialog(AlertDialog.Builder alertDialog) {
            mPresenter.showSuccess(alertDialog);
    }

    @Override
    public void showErrorDialog(AlertDialog.Builder alertDialog) {
        mPresenter.showAuthError(alertDialog);
    }
}
