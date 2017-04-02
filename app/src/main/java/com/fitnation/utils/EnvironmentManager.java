package com.fitnation.utils;

import com.fitnation.BuildConfig;

// TODO: get interface working for connecting to presenter
/**
 * Handles Environment switching
 */
public class EnvironmentManager {
    public static final String DEV_URL = "https://the-fit-nation-dev.herokuapp.com/";
    public static final String PROD_URL = "http://www.the-fit-nation.com/";
    private static EnvironmentManager mInstance;
    private Environment mEnvironment;

    private EnvironmentManager(){
        String url;

        if(BuildConfig.DEBUG) {
            url = DEV_URL;
        } else {
            url = PROD_URL;
        }

        mEnvironment = new Environment(url);
    }

    public synchronized static EnvironmentManager getInstance() {
        if(mInstance == null) {
            mInstance = new EnvironmentManager();
        }

        return mInstance;
    }

    public Environment getCurrentEnvironment() {
        return mEnvironment;
    }

    public void setEnvironment(Environment environment) {
        this.mEnvironment = environment;
    }
}
