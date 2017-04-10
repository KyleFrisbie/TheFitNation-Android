package com.fitnation.utils;

/**
 * Created by Jeremy on 4/9/2017.
 */

public final class UnitConversion {

    static final double INCH_PER_CM = 0.393701;
    static final double CM_PER_INCH = 2.54;
    static final double LBS_PER_KG = 2.20462;
    static final double KG_PER_LB = 0.453592;

    private UnitConversion(){}

    public static double lbsToKgs(double lbs){ return lbs * KG_PER_LB; }
    public static double kgsToLbs(double kgs){ return kgs * LBS_PER_KG;}
    public static double cmToInch(double cm) { return cm * INCH_PER_CM;}
    public static double inchToCM(double in) { return in * CM_PER_INCH;}
}
