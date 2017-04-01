package com.fitnation.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 4/1/2017.
 */
public class EnvironmentManagerTest {
    @Test
    public void getCurrentEnvironment() throws Exception {
        final String DEV_ENDPOINT = "https://the-fit-nation-dev.herokuapp.com/";
        final String DEV_API_ENDPOINT = "https://the-fit-nation-dev.herokuapp.com/api/";
        EnvironmentManager environmentManager = EnvironmentManager.getInstance();

        assertNotNull(environmentManager);

        Environment current = environmentManager.getCurrentEnvironment();
        assertTrue(current.getBaseUrl().equals(DEV_ENDPOINT));
        assertTrue(current.getApiUrl().equals(DEV_API_ENDPOINT));
    }

    @Test
    public void setCurrentEnvironment() throws Exception {
        final String DEV_ENDPOINT = "http://localhost:5580/";
        final String DEV_API_ENDPOINT = "http://localhost:5580/api/";
        EnvironmentManager environmentManager = EnvironmentManager.getInstance();
        environmentManager.setEnvironment(new Environment(DEV_ENDPOINT));

        Environment current = environmentManager.getCurrentEnvironment();
        assertTrue(current.getBaseUrl().equals(DEV_ENDPOINT));
        assertTrue(current.getApiUrl().equals(DEV_API_ENDPOINT));
    }


}