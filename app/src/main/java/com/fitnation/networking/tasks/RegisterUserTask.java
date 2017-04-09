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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the register request
 */

public class RegisterUserTask implements FactoryContract.FactoryReturn{
    private BaseActivity mActivity;
    private TaskContract.Presenter mPresenter;

    public RegisterUserTask(BaseActivity activity, TaskContract.Presenter presenter) {
        this.mActivity = activity;
        this.mPresenter = presenter;
    }

    public void requestRegistration(final String email, final String password, final String userName,
                                    final String language){
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        String endpoint = "api/register";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getBaseUrl() + endpoint;

        ProgressDialog progressDialog = new ProgressDialog(mActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);
        mPresenter.showProgress(progressDialog);

        StringRequest jsonObjectPost = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                mPresenter.stopProgress();
                handleJsonResponse();

            }
        },  new Response.ErrorListener() {
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
        }
        ){
            @Override
            public byte[] getBody() {
                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("langKey", language);
                map.put("login", userName);
                map.put("password", password);

                JSONObject jsonObject = new JSONObject(map);
                return jsonObject.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }

        };

        requestQueue.add(jsonObjectPost);
        requestQueue.start();
    }

    private void errorResponseMessage(VolleyError error) {
        if(error.networkResponse.statusCode != 401) {
            VolleyErrorMessage volleyErrorMessage = new VolleyErrorMessage(error);
            mPresenter.showAuthError(volleyErrorMessage.getErrorMessage(mActivity));
        }else{
            VolleyErrorMessage volleyErrorMessage = new VolleyErrorMessage(error);
            volleyErrorMessage.getErrorMessage(mActivity);
        }
    }

    /**
     * returns the successful registration alert dialog which informs user to activate email
     */
    private void handleJsonResponse(){
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(mActivity);
        alertDialog.setTitle("Success!");
        alertDialog.setMessage("Welcome to the Fit Nation! To complete the regestration process please confirm your email");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.create();
        mPresenter.showSuccess(alertDialog);
    }

    private void noResponseError(){
        AlertDialog.Builder noResponseDialog = new AlertDialog.Builder(mActivity);
        noResponseDialog.setTitle("No Response");
        noResponseDialog.setMessage("Attempted to connect to the server but did not recieve a response. Please try again");
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
