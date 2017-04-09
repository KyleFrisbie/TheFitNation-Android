package com.fitnation.Factory;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.fitnation.networking.tasks.RefreshAuthTokenTask;

/**
 * A factory for generating alert dialog messages for HTTP response codes from the server.
 */
public class VolleyErrorMessage {
    private VolleyError volleyError;

    /**
     * Constructor: Sets the error message retrieved from the server
     * @param volleyError Error response information from server
     */
    public VolleyErrorMessage(VolleyError volleyError){
        this.volleyError = volleyError;
    }

    /**
     * Retrieves an error message based on the response code from the server.
     * @return String response message indication error number and type.
     */
    public AlertDialog.Builder getErrorMessage(Context context){
        return GenerateErrorMessage(volleyError.networkResponse, context);
    }

    private AlertDialog.Builder GenerateErrorMessage(NetworkResponse response, Context context) {
        String message = String.valueOf(response.statusCode);
        String title;
        AlertDialog.Builder builder;

        //initalize builder
        builder = generateAlertDialog(context, "No response", "There was response from the server", false);

        switch (response.statusCode) {
            case 100:
                message = "100: Continue";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 101:
                message = "101: Switching Protocols";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 102:
                message = "102: Processing";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 200:
                message = "200: Success";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 201:
                message = "201: Created";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 202:
                message = "202: Accepted";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 203:
                message = "203: Non-Authoritative Information";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 204:
                message = "204: No Content";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 205:
                message = "205: Reset Content";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 206:
                message = "206: Partial Content";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 207:
                message = "207: Multi-Status";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 208:
                message = "208: Already Reported";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 226:
                message = "226: IM used";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 300:
                message = "300 Multiple choices";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 301:
                message = "301: Moved Permanently";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 302:
                message = "302: Found";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 303:
                message = "303: See Other";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 304:
                message = "304: Modified";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 305:
                message = "305: Use Proxy";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 306:
                message = "306: Switch Proxy";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 307:
                message = "307: Temporary Redirect";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 308:
                message = "308: Permanent Redirect";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 400:
                message = "400: Bad Request";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 401:
                RefreshAuthTokenTask refreshAccessToken = new RefreshAuthTokenTask();
                if (refreshAccessToken.refresh(context)) {
                    message = "Authorization has been refreshed";
                    builder = generateAlertDialog(context, "Access Refreshed", message, false);
                } else {
                    if(message.isEmpty()){
                        message = "401: Unauthorized";
                        title = "Error";
                        builder = generateAlertDialog(context, title, message, true);
                    }
                }
                break;
            case 402:
                message = "402: Payment Required";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 403:
                message = "403: Forbidden";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 404:
                message = "404: Not Found";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 405:
                message = "405: Method Not Allowed";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 406:
                message = "406: Not Acceptable";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 407:
                message = "407: Proxy Authentication Required";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 408:
                message = "408: Request Time-Out";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 409:
                message = "409: Conflict";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 410:
                message = "410: Gone";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 411:
                message = "411: Length Required";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 412:
                message = "412: Precondition Failed";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 413:
                message = "413: Payload To Large";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 414:
                message = "414: URI Too Long";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 415:
                message = "415: Unsupported Media Type";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 416:
                message = "416: Range Not Satisfiable";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 417:
                message = "417: Expectation Failed";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 418:
                message = "418: I'm a Teapot";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 421:
                message = "421: Misdirect Request";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 422:
                message = "422: Unprocessable Entity";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 423:
                message = "423: Locked";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 424:
                message = "424: Failed Dependency";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 426:
                message = "426: Upgrade Required";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 428:
                message = "428: Precondition Required";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 429:
                message = "429: Too Many Requests";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 431:
                message = "431: Request Header Fields Too Large";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 451:
                message = "451: Unavailable for Legal Reasons";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 498:
                message = "498: Invalid Token";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 499:
                message = "499: Token Required";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 500:
                message = "500: Internal Server";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 501:
                message = "501: Not Implemented";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 502:
                message = "502: Bad Gateway";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 503:
                message = "503: Service Unavailable";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 504:
                message = "504: Gateway Time-out";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 505:
                message = "505: HTTP Version Not Supported";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 506:
                message = "506: Variant Also Negotiates";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 507:
                message = "507: Insufficient Storage ";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 508:
                message = "508: Loop Detected";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 510:
                message = "510: Not Extended";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
            case 511:
                message = "511: Network Authentication Required";
                title = "Error";
                builder = generateAlertDialog(context, title, message, true);
                break;
        }
        return builder;
    }

    private AlertDialog.Builder generateAlertDialog(Context context, String title, String message, boolean button){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if(button) {
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        alertDialog.create();
        return alertDialog;
    }
}
