package com.fitnation.profile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Date;
import java.util.Calendar;

/**
 * Created by Jeremy on 3/5/2017.
 * Taken from https://developer.android.com/guide/topics/ui/controls/pickers.html
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public Calendar calendar;
    public DatePickerDialog.OnDateSetListener frag;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), frag, year, month, day);
    }

    public void setFragment(DatePickerDialog.OnDateSetListener f){
        frag = f;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        calendar = Calendar.getInstance();
        calendar.set(year, month, day);
    }



}