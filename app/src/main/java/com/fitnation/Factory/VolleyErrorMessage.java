package com.fitnation.Factory;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.android.volley.VolleyError;
import com.fitnation.R;
import com.fitnation.networking.tasks.RefreshAuthTokenTask;
import com.fitnation.networking.tasks.TaskCallback;

/**
 * A factory for generating alert dialog messages for HTTP response codes from the server.
 */
public class VolleyErrorMessage implements TaskCallback.Factory{
    private FactoryCallback.FactoryReturn mFactoryReturn;
    private Context context;

    /**
     * constructor for testing purposes only do not use unless you need to inject alert
     * dialogs into ui during tests
     * @param context The activity that is running the volley request
     */
    public VolleyErrorMessage(Context context){
        this.context = context;
    }

    /**
     * Constructor to be used with callback interface
     * @param context The activity that is running the volley request
     * @param factoryReturn  The callback interface to be used
     */
    public VolleyErrorMessage(Context context, FactoryCallback.FactoryReturn factoryReturn){
        this.context = context;
        this.mFactoryReturn = factoryReturn;
    }

    /**
     * Tests code against all http status codes builds an alert dialog for the status
     * @param volleyError The error received from volley requests
     * @return special return for testing purposes should not be used instead callback should be used
     */
    public AlertDialog.Builder getErrorMessage(VolleyError volleyError) {
        AlertDialog.Builder builder = generateAlertDialog(context, "System Error", "Something went wrong please try again", false);

        try {
            switch (volleyError.networkResponse.statusCode) {
                case 100:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_100),
                            context.getString(R.string.volley_message_100), true);
                      break;
                case 101:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_101),
                            context.getString(R.string.volley_message_101), true);
                    break;
                case 102:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_102),
                            context.getString(R.string.volley_message_102), true);
                     break;
                case 200:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_200),
                            context.getString(R.string.volley_message_200), true);
                      break;
                case 201:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_201),
                            context.getString(R.string.volley_message_201), true);
                      break;
                case 202:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_202),
                            context.getString(R.string.volley_message_202), true);
                      break;
                case 203:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_203),
                            context.getString(R.string.volley_message_203), true);
                    break;
                case 204:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_204),
                            context.getString(R.string.volley_message_204), true);
                    break;
                case 205:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_205),
                            context.getString(R.string.volley_message_205), true);
                    break;
                case 206:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_206),
                            context.getString(R.string.volley_message_206), true);
                    break;
                case 207:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_207),
                            context.getString(R.string.volley_message_207), true);
                    break;
                case 208:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_208),
                            context.getString(R.string.volley_message_208), true);
                    break;
                case 226:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_226),
                            context.getString(R.string.volley_message_226), true);
                    break;
                case 300:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_300),
                            context.getString(R.string.volley_message_300), true);
                    break;
                case 301:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_301),
                            context.getString(R.string.volley_message_301), true);
                    break;
                case 302:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_302),
                            context.getString(R.string.volley_message_302), true);
                    break;
                case 303:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_303),
                            context.getString(R.string.volley_message_303), true);
                    break;
                case 304:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_304),
                            context.getString(R.string.volley_message_304), true);
                    break;
                case 305:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_305),
                            context.getString(R.string.volley_message_305), true);
                    break;
                case 306:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_306),
                            context.getString(R.string.volley_message_306), true);
                    break;
                case 307:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_307),
                            context.getString(R.string.volley_message_307), true);
                    break;
                case 308:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_308),
                            context.getString(R.string.volley_message_308), true);
                    break;
                case 400:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_400),
                            context.getString(R.string.volley_message_400), true);
                    break;
                case 401:
                    RefreshAuthTokenTask refreshAccessToken = new RefreshAuthTokenTask(this);
                    refreshAccessToken.refresh(context);
                    builder = null;
                case 402:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_402),
                            context.getString(R.string.volley_message_402), true);
                    break;
                case 403:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_403),
                            context.getString(R.string.volley_message_403), true);
                    break;
                case 404:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_404),
                            context.getString(R.string.volley_message_404), true);
                    break;
                case 405:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_405),
                            context.getString(R.string.volley_message_405), true);
                    break;
                case 406:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_406),
                            context.getString(R.string.volley_message_406), true);
                    break;
                case 407:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_407),
                            context.getString(R.string.volley_message_407), true);
                    break;
                case 408:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_408),
                            context.getString(R.string.volley_message_408), true);
                    break;
                case 409:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_409),
                            context.getString(R.string.volley_message_409), true);
                    break;
                case 410:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_410),
                            context.getString(R.string.volley_message_410), true);
                    break;
                case 411:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_411),
                            context.getString(R.string.volley_message_411), true);
                    break;
                case 412:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_412),
                            context.getString(R.string.volley_message_412), true);
                    break;
                case 413:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_413),
                            context.getString(R.string.volley_message_413), true);
                    break;
                case 414:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_414),
                            context.getString(R.string.volley_message_414), true);
                    break;
                case 415:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_415),
                            context.getString(R.string.volley_message_415), true);
                    break;
                case 416:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_416),
                            context.getString(R.string.volley_message_416), true);
                    break;
                case 417:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_417),
                            context.getString(R.string.volley_message_417), true);
                    break;
                case 418:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_418),
                            context.getString(R.string.volley_message_418), true);
                    break;
                case 421:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_421),
                            context.getString(R.string.volley_message_421), true);
                    break;
                case 422:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_422),
                            context.getString(R.string.volley_message_422), true);
                    break;
                case 423:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_423),
                            context.getString(R.string.volley_message_423), true);
                    break;
                case 424:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_424),
                            context.getString(R.string.volley_message_424), true);
                    break;
                case 426:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_426),
                            context.getString(R.string.volley_message_426), true);
                    break;
                case 428:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_428),
                            context.getString(R.string.volley_message_428), true);
                    break;
                case 429:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_429),
                            context.getString(R.string.volley_message_429), true);
                    break;
                case 431:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_431),
                            context.getString(R.string.volley_message_431), true);
                    break;
                case 451:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_451),
                            context.getString(R.string.volley_message_451), true);
                    break;
                case 498:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_498),
                            context.getString(R.string.volley_message_498), true);
                    break;
                case 499:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_499),
                            context.getString(R.string.volley_message_499), true);
                    break;
                case 500:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_500),
                            context.getString(R.string.volley_message_500), true);
                    break;
                case 501:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_501),
                            context.getString(R.string.volley_message_501), true);
                    break;
                case 502:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_502),
                            context.getString(R.string.volley_message_502), true);
                    break;
                case 503:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_503),
                            context.getString(R.string.volley_message_503), true);
                    break;
                case 504:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_504),
                            context.getString(R.string.volley_message_504), true);
                    break;
                case 505:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_505),
                            context.getString(R.string.volley_message_505), true);
                    break;
                case 506:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_506),
                            context.getString(R.string.volley_message_506), true);
                    break;
                case 507:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_507),
                            context.getString(R.string.volley_message_507), true);
                    break;
                case 508:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_508),
                            context.getString(R.string.volley_message_508), true);
                    break;
                case 510:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_510),
                            context.getString(R.string.volley_message_510), true);
                    break;
                case 511:
                    builder = generateAlertDialog(context,
                            context.getString(R.string.volley_title_511),
                            context.getString(R.string.volley_message_511), true);
                    break;
            }
        } catch (NullPointerException nullPointer) {
            if(volleyError.getMessage() != null){
                if (volleyError.getMessage().equalsIgnoreCase("java.io.IOException: No authentication challenges found")) {
                    RefreshAuthTokenTask refreshAccessToken = new RefreshAuthTokenTask(this);
                    refreshAccessToken.refresh(context);
                    builder = null;
                }
            }else {
                AlertDialog.Builder noResponseBuilder = generateAlertDialog(context,
                        context.getString(R.string.volley_title_no_response),
                        context.getString(R.string.volley_message_no_response), false);
                mFactoryReturn.showErrorDialog(noResponseBuilder);
            }
        }
        if(mFactoryReturn != null){
            mFactoryReturn.showErrorDialog(builder);
        }
        //for testing purposes only do not use as you should be using callback
        return builder;
    }

    /**
     * Creates an alert dialog from params
     * @param context The activity that it will display on
     * @param title  The title for the error
     * @param message  The message body for the error
     * @param button  Add an ok button or not
     * @return Alert Dialog for the http status code
     */
    private AlertDialog.Builder generateAlertDialog(Context context, String title, String message, boolean button){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if(button) {
            alertDialog.setPositiveButton(context.getString(R.string.alert_dialog_ok_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        alertDialog.create();
        return alertDialog;
    }

    /*------------------------------------TaskCallback.Factory------------------------------------*/

    @Override
    public void didRequestWork(boolean requestWorked) {
        if (requestWorked) {
            AlertDialog.Builder builder = generateAlertDialog(context,
                    context.getString(R.string.volley_title_access_refreshed),
                    context.getString(R.string.volley_message_access_refreshed), false);
            mFactoryReturn.showSuccessDialog(builder);
        } else {
            AlertDialog.Builder builder = generateAlertDialog(context,
                    context.getString(R.string.volley_title_401),
                    context.getString(R.string.volley_message_401), true);
            mFactoryReturn.showErrorDialog(builder);
        }
    }
}
