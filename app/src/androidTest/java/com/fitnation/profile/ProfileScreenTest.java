package com.fitnation.profile;

import org.junit.After;
import org.junit.AfterClass;

import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import com.fitnation.R;
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.model.UserWeight;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.UserLogins;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;

import android.os.SystemClock;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockContext;
import android.util.Log;

import com.fitnation.base.InstrumentationTest;
import com.fitnation.navigation.NavigationActivity;

import java.io.File;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Jeremy on 4/16/2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileScreenTest extends InstrumentationTest {
    private final int DELAY_TIME = 500;
    final static String SUCCESS_AUTH_TOKEN = "65d2a110-212b-403c-8d7e-0be4102442db";
    final static String FAILURE_AUTH_TOKEN = "e7f01c5f-4efe-4749-8c7a-d4d9b98670d5";
    final static String USER_DEMO_BY_LOGGED_IN_PATH = "users/user-demographic";
    final static String USER_DEMO_PUT_PATH = "user-demographics";
    final static String USER_WEIGHT_PATH = "user-weights";
    final static String USER_PATH = "users/admin";
    final static String SKILL_LEVELS_PATH = "skill-levels";
    static Realm testRealm;
    static RealmResults<UserDemographic> udQuery;
    static RealmResults<User> uQuery;
    static RealmResults<UserWeight> uwQuery;


    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule =
            new ActivityTestRule<NavigationActivity>(NavigationActivity.class);


    @Before
    public void setUp() {
        super.unlockScreen(mActivityRule.getActivity());

        testRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                testRealm.where(UserDemographic.class).findAll().deleteAllFromRealm();
                testRealm.where(User.class).findAll().deleteAllFromRealm();
                testRealm.where(UserWeight.class).findAll().deleteAllFromRealm();
            }
        });
    }

    @After
    public void tearDown() {
        super.tearDown(mActivityRule.getActivity());
    }

    @AfterClass
    public static void AfterClass(){
        testRealm.beginTransaction();
        testRealm.insert(udQuery);
        testRealm.insert(uQuery);
        testRealm.insert(uwQuery);
        testRealm.commitTransaction();
        testRealm.close();
    }

    @BeforeClass
    public static void mockServer() throws IOException {
        //get all the data out of realm and set it aside while testing.
        testRealm = Realm.getDefaultInstance();
        udQuery = testRealm.where(UserDemographic.class).findAll();
        uQuery = testRealm.where(User.class).findAll();
        uwQuery = testRealm.where(UserWeight.class).findAll();

        testRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                udQuery.deleteAllFromRealm();
                uQuery.deleteAllFromRealm();
                uwQuery.deleteAllFromRealm();
            }
        });


        final MockWebServer mockWebServer = new MockWebServer();

        final String USERDEMOGRAPHIC_RESPONSE = "{\n" +
                "  \"id\": 3154,\n" +
                "  \"createdOn\": \"2017-04-09\",\n" +
                "  \"lastLogin\": \"2017-04-09\",\n" +
                "  \"gender\": \"Other\",\n" +
                "  \"dateOfBirth\": \"2017-04-08\",\n" +
                "  \"height\": 123,\n" +
                "  \"unitOfMeasure\": \"Metric\",\n" +
                "  \"userId\": 2802,\n" +
                "  \"userLogin\": \"admin\",\n" +
                "  \"gyms\": [],\n" +
                "  \"skillLevelId\": 1251,\n" +
                "  \"skillLevelLevel\": \"Beginner\"\n" +
                "}";

        final String USER_RESPONSE = "{\n" +
                "  \"id\": 2802,\n" +
                "  \"login\": \"admin\",\n" +
                "  \"firstName\": \"admin\",\n" +
                "  \"lastName\": \"admin\",\n" +
                "  \"email\": \"admin@admin.com\",\n" +
                "  \"imageUrl\": null,\n" +
                "  \"activated\": true,\n" +
                "  \"langKey\": \"en\",\n" +
                "  \"createdBy\": \"kyle\",\n" +
                "  \"createdDate\": \"2017-03-13T01:59:24.183Z\",\n" +
                "  \"lastModifiedBy\": \"admin\",\n" +
                "  \"lastModifiedDate\": \"2017-03-19T19:04:27.062Z\",\n" +
                "  \"authorities\": [\n" +
                "    \"ROLE_USER\",\n" +
                "    \"ROLE_ADMIN\"\n" +
                "  ]\n" +
                "}";

        final String WEIGHT_RESPONSE = "[\n" +
                "{\n" +
                "    \"id\": 11663,\n" +
                "    \"weightDate\": \"2017-04-15\",\n" +
                "    \"weight\": 456,\n" +
                "    \"userDemographicId\": 3154\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 11664,\n" +
                "    \"weightDate\": \"2017-04-15\",\n" +
                "    \"weight\": 567,\n" +
                "    \"userDemographicId\": 3154\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 11665,\n" +
                "    \"weightDate\": \"2017-04-15\",\n" +
                "    \"weight\": 678,\n" +
                "    \"userDemographicId\": 3154\n" +
                "  }\n" +
                "]";

        final String INVALID_TOKEN = "{\n" +
                "  \"error\": \"invalid_token\",\n" +
                "  \"error_description\": \"Invalid access token: \"" + FAILURE_AUTH_TOKEN + "\"\n" +
                "}";

        final String SKILL_LEVELS_RESPONSE = "[\n" +
                "  {\n" +
                "    \"id\": 1251,\n" +
                "    \"level\": \"Beginner\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1252,\n" +
                "    \"level\": \"Intermediate\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1253,\n" +
                "    \"level\": \"Advanced\"\n" +
                "  }\n" +
                "]";

        final String USER_DEMO_PUT_RESPONSE = "{\"id\":3154,\"" +
                "createdOn\":\"2017-04-17\",\"" +
                "lastLogin\":\"2017-04-18\",\"" +
                "gender\":\"Other\",\"" +
                "dateOfBirth\":\"2017-04-17\",\"" +
                "height\":80,\"" +
                "unitOfMeasure\":\"" +
                "Imperial\",\"" +
                "userId\":2802,\"" +
                "userLogin\":\"admin\",\"" +
                "gyms\":[],\"" +
                "skillLevelId\":1251,\"" +
                "skillLevelLevel\":\"Beginner\"" +
                "}";

        final String WEIGHT_POST_RESPONSE = "{\"id\":15101,\"" +
                "weightDate\":\"2017-04-17\",\"" +
                "weight\":150,\"" +
                "userDemographicId\":3154}";


        final String EMPTY_WEIGHT_RESPONSE = "[]";

        final MockResponse good_user_demo_response = new MockResponse().setResponseCode(200).setBody(USERDEMOGRAPHIC_RESPONSE);
        final MockResponse good_weights_response = new MockResponse().setResponseCode(200).setBody(WEIGHT_RESPONSE);
        final MockResponse empty_weight_response = new MockResponse().setResponseCode(200).setBody(EMPTY_WEIGHT_RESPONSE);
        final MockResponse good_user_response = new MockResponse().setResponseCode(200).setBody(USER_RESPONSE);
        final MockResponse skill_levels_response = new MockResponse().setResponseCode(200).setBody(SKILL_LEVELS_RESPONSE);
        final MockResponse user_demo_put_response = new MockResponse().setResponseCode(200).setBody(USER_DEMO_PUT_RESPONSE);
        final MockResponse user_weight_post_response = new MockResponse().setResponseCode(200).setBody(WEIGHT_POST_RESPONSE);


        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                if (request.toString().contains(USER_WEIGHT_PATH)
                        && request.getHeaders().toString().contains(SUCCESS_AUTH_TOKEN)) {
                    return good_weights_response;
                } else if (request.getPath().toLowerCase().contains(USER_WEIGHT_PATH)
                        && request.getHeaders().toString().contains(FAILURE_AUTH_TOKEN)) {
                    return empty_weight_response;
                } else if (request.getPath().toLowerCase().contains(USER_DEMO_BY_LOGGED_IN_PATH)
                        && request.getHeaders().toString().contains(SUCCESS_AUTH_TOKEN)) {
                    return good_user_demo_response;
                } else if (request.getPath().toLowerCase().contains(USER_DEMO_BY_LOGGED_IN_PATH)
                        && request.getHeaders().toString().contains(FAILURE_AUTH_TOKEN)) {
                    return good_user_demo_response;
                } else if (request.getPath().toLowerCase().contains(USER_PATH)
                        && request.getHeaders().toString().contains(SUCCESS_AUTH_TOKEN)) {
                    return good_user_response;
                } else if (request.getPath().toLowerCase().contains(SKILL_LEVELS_PATH)
                        && request.getHeaders().toString().contains(SUCCESS_AUTH_TOKEN)) {
                    return skill_levels_response;
                } else if (request.getPath().toLowerCase().contains(USER_DEMO_PUT_PATH)
                        && request.getHeaders().toString().contains(SUCCESS_AUTH_TOKEN)) {
                    return user_demo_put_response;
                } else if (request.getPath().toLowerCase().contains(USER_WEIGHT_PATH)
                        && request.getHeaders().toString().contains(SUCCESS_AUTH_TOKEN)
                        && request.toString().contains("PUT")) {
                    return user_weight_post_response;
                }
                    else return new MockResponse().setResponseCode(404);
            }
        };

        mockWebServer.setDispatcher(dispatcher);
        mockWebServer.start();
        Environment environment = new Environment(mockWebServer.url("").toString());
        EnvironmentManager.getInstance().setEnvironment(environment);
        SystemClock.sleep(500);
        UserLogins.setUserDemographicId("3154");
        UserLogins.setUserLogin("admin");
        UserLogins.setUserId("2802");
    }

    @Test
    public void navigationActivityDisplayed() {
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.content_main_container)).check(matches(isDisplayed()));
    }

    @Test
    public void badAuthToken() {
        SystemClock.sleep(DELAY_TIME);
        AuthToken.getInstance().setAccessToken(FAILURE_AUTH_TOKEN);
        onNavMyProfilePressed();
        profilePageIsDisplayed();
        SystemClock.sleep(DELAY_TIME);

    }

    @Test
    public void startupFromScratch() {
        AuthToken.getInstance().setAccessToken(SUCCESS_AUTH_TOKEN);
        onNavMyProfilePressed();
        profilePageIsDisplayed();
        onView(withText(R.string.switchMeasureToMetric));
        onView(withId(R.id.switchMeasurement)).perform(click());
        SystemClock.sleep(DELAY_TIME);
        onView(withText(R.string.switchMeasureToImperial));
        SystemClock.sleep(DELAY_TIME);
        onView(allOf(withId(R.id.saveButton),
                withText("Save"),
                withParent(withId(R.id.fragment_profile)))).
                perform(scrollTo(), click());
        SystemClock.sleep(DELAY_TIME);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("My Workouts")).perform(click());
        onNavMyProfilePressed();
        profilePageIsDisplayed();

    }

    public void onNavMyProfilePressed(){
        SystemClock.sleep(DELAY_TIME);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        onView(withText("My Profile")).perform(click());

    }

    public void profilePageIsDisplayed(){
        SystemClock.sleep(DELAY_TIME);
        onView(withId(R.id.nameText));
        onView(withId(R.id.nameView));
        onView(withId(R.id.emailView));
        onView(withId(R.id.emailText));
        onView(withId(R.id.genderEditText));
        onView(withId(R.id.genderView));
        onView(withId(R.id.birthdayView));
        onView(withId(R.id.birthdayEditText));
        onView(withId(R.id.ageText));
        onView(withId(R.id.weightView));
        onView(withId(R.id.weightEditText));
        onView(withId(R.id.switchMeasurement));
        onView(withId(R.id.heightView));
        onView(withId(R.id.heightEditText));
        onView(withId(R.id.lifterTypeView));
        onView(withId(R.id.lifterTypeSpinner));
        onView(withId(R.id.saveButton));
    }


}
