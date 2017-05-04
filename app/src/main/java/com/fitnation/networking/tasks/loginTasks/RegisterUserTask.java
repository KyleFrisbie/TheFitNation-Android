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
import com.fitnation.utils.Factory.FactoryCallback;
import com.fitnation.utils.Factory.VolleyErrorMessage;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the register request
 */

public class RegisterUserTask implements FactoryCallback.FactoryReturn{
    private BaseActivity mActivity;
    private TaskCallback.Presenter mPresenter;

    /**
     * Constructor
     * @param activity The base calling activity
     * @param presenter The interface to be used
     */
    public RegisterUserTask(BaseActivity activity, TaskCallback.Presenter presenter) {
        this.mActivity = activity;
        this.mPresenter = presenter;
    }

    /**
     * Request to the server to register a new mUser account
     * @param email The email entered
     * @param password The password entered
     * @param userName The username entered
     * @param language The language which by default is english for now
     */
     // TODO: allow for different languages
    public void requestRegistration(final String email, final String password,
                                    final String userName, final String firstName,
                                    final String lastName, final String language){
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        String endpoint = "api/register";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getBaseUrl() + endpoint;

        ProgressDialog progressDialog = new ProgressDialog(mActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(mActivity.getString(R.string.progress_message));
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
                mPresenter.stopProgress();
                errorResponseMessage(error);
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
                map.put("firstName", firstName);
                map.put("lastName", lastName);

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

        jsonObjectPost.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));
        requestQueue.add(jsonObjectPost);
    }

    /**
     * Error response to be generated from volleyErrorFactory
     * @param error Volleys error object
     */
    private void errorResponseMessage(VolleyError error) {
        VolleyErrorMessage volleyErrorMessage = new VolleyErrorMessage(mActivity, this);
        volleyErrorMessage.getErrorMessage(error);
    }

    /**
     * returns the successful registration alert dialog which informs mUser to activate email
     */
    private void handleJsonResponse(){
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(mActivity);
        alertDialog.setTitle(R.string.register_success_title);
        alertDialog.setMessage(R.string.register_success_message);
        alertDialog.setPositiveButton(R.string.alert_dialog_ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.create();
        mPresenter.showSuccess(alertDialog);
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
