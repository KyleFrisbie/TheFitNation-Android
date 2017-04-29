package com.fitnation.utils;

import java.util.Calendar;

/**
 * Created by Jeremy on 4/9/2017.
 */

public final class UnitConversion {

    static final double INCH_PER_CM = 0.393701;
    static final double CM_PER_INCH = 2.54;
    static final double LBS_PER_KG = 2.20462;
    static final double KG_PER_LB = 0.453592;
    static final long MILLISECONDS_IN_YEAR = 31556952000L;

    private UnitConversion(){}

    public static double lbsToKgs(double lbs){ return lbs * KG_PER_LB; }
    public static double kgsToLbs(double kgs){ return kgs * LBS_PER_KG;}
    public static double cmToInch(double cm) { return cm * INCH_PER_CM;}
    public static double inchToCM(double in) { return in * CM_PER_INCH;}

    public static int getAgeInYears(Calendar cal){
        Calendar now = Calendar.getInstance();
        long mili = now.getTimeInMillis() - cal.getTimeInMillis();
        if (mili > 0) {

            mili /= MILLISECONDS_IN_YEAR; //TO YEARS
            int yearsOld = (int) (mili);

            return yearsOld;
        }
        return 0;
    }
}
