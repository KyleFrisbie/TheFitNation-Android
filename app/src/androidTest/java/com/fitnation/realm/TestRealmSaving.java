package com.fitnation.realm;

import android.os.Handler;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.base.InstrumentationTest;
import com.fitnation.model.UserDemographic;
import com.fitnation.navigation.NavigationActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * This is to demonstrate the usage of Realm
 */
@RunWith(AndroidJUnit4.class)
public class TestRealmSaving extends InstrumentationTest {
    private static final Long USER_ID = 55L;
    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule = new ActivityTestRule(NavigationActivity.class);

    @Before
    public void setUp() {
        super.unlockScreen(mActivityRule.getActivity());
    }

    @After
    public void tearDown() {
        super.tearDown(mActivityRule.getActivity());
    }

    @Test
    public void testDataIsPersisted() throws Exception {
        final Object syncObject = new Object();
        final DataManager dataManager = new TestDataManager();
        final UserDemographic userDemographic = new UserDemographic();
        userDemographic.setId(USER_ID);
        dataManager.saveData(userDemographic, new DataResult() {
            @Override
            public void onError() {
                Assert.assertTrue(false);
                synchronized (syncObject) {
                    syncObject.notify();
                }
            }

            @Override
            public void onSuccess() {
                Assert.assertTrue(true);
                synchronized (syncObject) {
                    syncObject.notify();
                }
            }
        });

        synchronized (syncObject){
            syncObject.wait();
        }

        Realm realm = Realm.getDefaultInstance();
        // Build the query looking at all users:
        RealmQuery<UserDemographic> query = realm.where(UserDemographic.class);

        // Add query conditions:
        query.equalTo("androidId", USER_ID);


        // Execute the query:
        RealmResults<UserDemographic> result1 = query.findAll();

        Assert.assertTrue(result1.size() == 1);
    }
}
