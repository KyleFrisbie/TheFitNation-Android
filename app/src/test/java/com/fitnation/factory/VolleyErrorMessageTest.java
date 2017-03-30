package com.fitnation.factory;

import android.content.Context;
import android.content.pm.InstrumentationInfo;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.fitnation.Factory.VolleyErrorMessage;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Erik on 3/30/2017.
 */

public class VolleyErrorMessageTest {
    private VolleyErrorMessage mVolleyErrorMessage;
    private VolleyError mVolleyError;
    private NetworkResponse mNetworkResponse;
    private Context context;


    @Test
    public void testMessage100(){
        int responseCode = 100;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage101(){
        int responseCode = 101;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage102(){
        int responseCode = 102;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage200(){
        int responseCode = 200;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage201(){
        int responseCode = 201;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage202(){
        int responseCode = 202;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage203(){
        int responseCode = 203;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage204(){
        int responseCode = 204;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage205(){
        int responseCode = 205;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage206(){
        int responseCode = 206;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage207(){
        int responseCode = 207;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage208(){
        int responseCode = 208;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage226(){
        int responseCode = 226;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage300(){
        int responseCode = 300;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage301(){
        int responseCode = 301;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage302(){
        int responseCode = 302;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage303(){
        int responseCode = 303;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage304(){
        int responseCode = 304;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage305(){
        int responseCode = 305;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage306(){
        int responseCode = 306;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage307(){
        int responseCode = 307;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage308(){
        int responseCode = 308;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage400(){
        int responseCode = 400;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage402(){
        int responseCode = 402;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage403(){
        int responseCode = 403;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage404(){
        int responseCode = 404;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage405(){
        int responseCode = 405;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage406(){
        int responseCode = 406;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage407(){
        int responseCode = 407;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage408(){
        int responseCode = 408;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage409(){
        int responseCode = 409;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage410(){
        int responseCode = 410;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage411(){
        int responseCode = 411;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }
    @Test
    public void testMessage412(){
        int responseCode = 412;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }
    @Test
    public void testMessage413(){
        int responseCode = 413;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage414(){
        int responseCode = 414;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage415(){
        int responseCode = 415;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage416(){
        int responseCode = 416;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage417(){
        int responseCode = 417;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage418(){
        int responseCode = 418;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage421(){
        int responseCode = 421;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage422(){
        int responseCode = 422;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage423(){
        int responseCode = 423;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage424(){
        int responseCode = 424;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage426(){
        int responseCode = 426;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage428(){
        int responseCode = 428;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage429(){
        int responseCode = 429;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage431(){
        int responseCode = 431;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage451(){
        int responseCode = 451;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage498(){
        int responseCode = 498;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage499(){
        int responseCode = 499;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage500(){
        int responseCode = 500;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage501(){
        int responseCode = 501;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage502(){
        int responseCode = 502;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage503(){
        int responseCode = 503;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage504(){
        int responseCode = 504;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage505(){
        int responseCode = 505;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage506(){
        int responseCode = 506;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage507(){
        int responseCode = 507;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage508(){
        int responseCode = 508;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage510(){
        int responseCode = 510;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }

    @Test
    public void testMessage511(){
        int responseCode = 511;
        String responseCodeString = Integer.toString(responseCode);

        mNetworkResponse = new NetworkResponse(responseCode, null, null, false);
        mVolleyError = new VolleyError(mNetworkResponse);
        mVolleyErrorMessage = new VolleyErrorMessage(mVolleyError);

        assertTrue(mVolleyErrorMessage.GetErrorMessage(context).contains(responseCodeString));
    }
}
