package com.fitnation.Factory;

import android.support.v7.app.AlertDialog;

/**
 * Created by Erik on 4/9/2017.
 */

public interface FactoryCallback {
    interface FactoryReturn{
        void showSuccessDialog(AlertDialog.Builder alertDialog);
        void showErrorDialog(AlertDialog.Builder alertDialog);
    }
}
