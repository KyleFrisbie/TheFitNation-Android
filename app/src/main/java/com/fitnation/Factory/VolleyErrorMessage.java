package com.fitnation.Factory;

import android.app.AlertDialog;
import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.fitnation.networking.RefreshAccessToken;

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
    public String GetErrorMessage(Context context){
        return GenerateErrorMessage(volleyError.networkResponse, context);
    }

    private String GenerateErrorMessage(NetworkResponse response, Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        String message = String.valueOf(response.statusCode);

        switch (response.statusCode) {
            case 100:
                message = "100: Continue";
                break;
            case 101:
                message = "101: Switching Protocols";
                break;
            case 102:
                message = "102: Processing";
                break;
            case 200:
                message = "200: Success";
                break;
            case 201:
                message = "201: Created";
                break;
            case 202:
                message = "202: Accepted";
                break;
            case 203:
                message = "203: Non-Authoritative Information Error";
                break;
            case 204:
                message = "204: No Content Error";
                break;
            case 205:
                message = "205: Reset Content Error";
                break;
            case 206:
                message = "206: Partial Content Error";
                break;
            case 207:
                message = "207: Multi-Status Error";
                break;
            case 208:
                message = "208: Already Reported Error";
                break;
            case 226:
                message = "226: IM used Error";
                break;
            case 300:
                message = "300 Multiple choices Error";
                break;
            case 301:
                message = "301: Moved Permanently Error";
                break;
            case 302:
                message = "302: Found Error";
                break;
            case 303:
                message = "303: See Other Error";
                break;
            case 304:
                message = "304: Modified Error";
                break;
            case 305:
                message = "305: Use Proxy Error";
                break;
            case 306:
                message = "306: Switch Proxy Error";
                break;
            case 307:
                message = "307: Temporary Redirect Error";
                break;
            case 308:
                message = "308: Permanent Redirect Error";
                break;
            case 400:
                message = "400: Bad Request Error";
                break;
            case 401:
                RefreshAccessToken refreshAccessToken = new RefreshAccessToken();
                if (refreshAccessToken.refresh(context)) {
                    message = "Authorization has been refreshed";
                } else {
                    message = "401: Unauthorized Error";
                }
                break;
            case 402:
                message = "402: Payment Required";
                break;
            case 403:
                message = "403: Forbidden Error";
                break;
            case 404:
                message = "404: Not Found Error";
                break;
            case 405:
                message = "405: Method Not Allowed Error";
                break;
            case 406:
                message = "406: Not Acceptable Error";
                break;
            case 407:
                message = "407: Proxy Authentication Required";
                break;
            case 408:
                message = "408: Request Time-Out";
                break;
            case 409:
                message = "409: Conflict Error";
                break;
            case 410:
                message = "410: Gone Error";
                break;
            case 411:
                message = "411: Length Required";
                break;
            case 412:
                message = "412: Precondition Failed";
                break;
            case 413:
                message = "413: Payload To Large";
                break;
            case 414:
                message = "414: URI Too Long";
                break;
            case 415:
                message = "415: Unsupported Media Type";
                break;
            case 416:
                message = "416: Range Not Satisfiable";
                break;
            case 417:
                message = "417: Expectation Failed";
                break;
            case 418:
                message = "418: I'm a Teapot";
                break;
            case 421:
                message = "421: Misdirect Request";
                break;
            case 422:
                message = "422: Unprocessable Entity";
                break;
            case 423:
                message = "423: Locked";
                break;
            case 424:
                message = "424: Failed Dependency";
                break;
            case 426:
                message = "426: Upgrade Required";
                break;
            case 428:
                message = "428: Precondition Required";
                break;
            case 429:
                message = "429: Too Many Requests";
                break;
            case 431:
                message = "431: Request Header Fields Too Large";
                break;
            case 451:
                message = "451: Unavailable for Legal Reasons";
                break;
            case 498:
                message = "498: Invalid Token";
                break;
            case 499:
                message = "499: Token Required";
                break;
            case 500:
                message = "500: Internal Server Error";
                break;
            case 501:
                message = "501: Not Implimented";
                break;
            case 502:
                message = "502: Bad Gateway";
                break;
            case 503:
                message = "503: Service Unavailable";
                break;
            case 504:
                message = "504: Gateway Time-out";
                break;
            case 505:
                message = "505: HTTP Version Not Supported";
                break;
            case 506:
                message = "506: Variant Also Negotiates";
                break;
            case 507:
                message = "507: Insufficient Storage ";
                break;
            case 508:
                message = "508: Loop Detected";
                break;
            case 510:
                message = "510: Not Extended";
                break;
            case 511:
                message = "511: Network Authentication Required";
                break;
        }
        return message;
    }
}
