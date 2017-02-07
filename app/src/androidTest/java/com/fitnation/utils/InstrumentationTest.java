package com.fitnation.utils;

import android.app.Activity;
import android.view.WindowManager;

import org.junit.Before;

/**
 * Created by Ryan on 2/7/2017.
 */

public class InstrumentationTest {

    public void unlockScreen(final Activity activity) {
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        activity.runOnUiThread(wakeUpDevice);
    }
}
