package com.fitnation.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ryan on 4/16/2017.
 */

public class DateFormatter {
    private static final String TAG = DateFormatter.class.getSimpleName();
    private DateFormatter(){}

    public static String getFormattedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");

        return dateFormat.format(date);
    }

    public static String getUIDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("MM-dd-yyyy");
        Date date = parseDate(dateString);

        return dateFormat.format(date);
    }

    private static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            Log.e(TAG, e.toString());
        }

        return date;
    }


}
