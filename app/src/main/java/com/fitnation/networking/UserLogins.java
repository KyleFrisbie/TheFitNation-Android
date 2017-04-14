package com.fitnation.networking;


/**
 * Created by Jeremy on 4/11/2017.
 *
 */

public class UserLogins {
    private static String userdemographicId = null;
    private static String userId = null;
    private static String userLogin = null;

    private static UserLogins udInstance;

    public synchronized static UserLogins getInstance(){
        if (udInstance == null) {
            udInstance = new UserLogins();
        }
        return udInstance;
    }

    public synchronized static String getUserId() {
        return userId;
    }

    public synchronized static void setUserId(String userId) {
        UserLogins.userId = userId;
    }

    public synchronized static String getUserLogin() {
        return userLogin;
    }

    public synchronized static void setUserLogin(String userLogin) {
        UserLogins.userLogin = userLogin;
    }

    public synchronized static void setUserDemographicId(String id){
        userdemographicId = id;
    }

    public synchronized static String getUserDemographicId(){
        return userdemographicId;
    }

    private UserLogins(){}
}

