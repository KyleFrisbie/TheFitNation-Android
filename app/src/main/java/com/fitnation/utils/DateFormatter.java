package com.fitnation.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ryan on 4/16/2017.
 */

public class DateFormatter {
    private DateFormatter(){}

    public static String getFormattedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");

        return dateFormat.format(date);
    }


}
