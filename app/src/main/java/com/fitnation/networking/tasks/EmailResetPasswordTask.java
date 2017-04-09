package com.fitnation.networking.tasks;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factory.FactoryContract;
import com.fitnation.Factory.VolleyErrorMessage;
import com.fitnation.base.BaseActivity;
import com.fitnation.utils.EnvironmentManager;

import java.util.HashMap;
import java.util.Map;

/**
 * handles the email reset password request
 */

public class EmailResetPasswordTask implements FactoryContract.FactoryReturn{
    private TaskContract.Presenter mPresenter;
    private BaseActivity mActivity;

    public EmailResetPasswordTask(BaseActivity mActivity, TaskContract.Presenter presenter) {
        this.mPresenter = presenter;
        this.mActivity = mActivity;
    }

    public void resetPasswordRequest(final String email){
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        String endpoint = "api/account/reset_password/init";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getBaseUrl() + endpoint;

        ProgressDialog progressDialog = new ProgressDialog(mActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait...");
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
                try{
                    int code = error.networkResponse.statusCode;
                    errorResponseMessage(error);
                    mPresenter.stopProgress();
                }catch(NullPointerException nullPointer){
                    mPresenter.stopProgress();
                    noResponseError();
                }
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

        requestQueue.add(resetPasswordWithEmailRequest);
        requestQueue.start();
    }

    private void successfulResponse(String response) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
        alertDialog.setTitle("Success");
        alertDialog.setMessage(response);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.create();
        mPresenter.showSuccess(alertDialog);
    }

    private void errorResponseMessage(VolleyError error){
        if(error.networkResponse.statusCode != 401) {
            VolleyErrorMessage volleyErrorMessage = new VolleyErrorMessage(error);
            mPresenter.showAuthError(volleyErrorMessage.getErrorMessage(mActivity));
        }else{
            VolleyErrorMessage volleyErrorMessage = new VolleyErrorMessage(error, this);
            volleyErrorMessage.getErrorMessage(mActivity);
        }
    }

    private void noResponseError(){
        AlertDialog.Builder noResponseDialog = new AlertDialog.Builder(mActivity);
        noResponseDialog.setTitle("No Response");
        noResponseDialog.setMessage("Attempted to connect to the server but did not receive a response. Please try again");
        noResponseDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noResponseDialog.create();
        mPresenter.showAuthError(noResponseDialog);
    }

    @Override
    public void showSuccessDialog(AlertDialog.Builder alertDialog) {
        mPresenter.showSuccess(alertDialog);
    }

    @Override
    public void showErrorDialog(AlertDialog.Builder alertDialog) {
        mPresenter.showAuthError(alertDialog);
    }
}
