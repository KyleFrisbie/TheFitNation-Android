package com.fitnation.profile;

import android.os.SystemClock;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.fitnation.R;
import com.fitnation.base.InstrumentationTest;
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.model.UserWeight;
import com.fitnation.navigation.NavigationActivity;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.UserLogins;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;
import com.fitnation.utils.FileUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Jeremy on 4/16/2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileScreenTest extends InstrumentationTest {
    final static String SUCCESS_AUTH_TOKEN = "65d2a110-212b-403c-8d7e-0be4102442db";
    final static String TOKEN_FOR_IMPERIAL = "65d2a110-212b-403c-8d7e-0be4102442dd";
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
    final String TAG = getClass().getSimpleName().toString();
    private final int DELAY_TIME = 500;



    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule =
            new ActivityTestRule<NavigationActivity>(NavigationActivity.class);

    @AfterClass
    public static void AfterClass() {
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
    }

    @Before
    public void setUp() {
        super.unlockScreen(mActivityRule.getActivity());
        //delete realm entries between tests.
        final RealmResults tempUD = testRealm.where(UserDemographic.class).findAll();
        final RealmResults tempU = testRealm.where(User.class).findAll();
        final RealmResults tempUW = testRealm.where(UserWeight.class).findAll();

        testRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                tempUD.deleteAllFromRealm();
                tempU.deleteAllFromRealm();
                tempUW.deleteAllFromRealm();
            }
        });

        final MockWebServer mockWebServer = new MockWebServer();

        final String USERDEMOGRAPHIC_RESPONSE = FileUtils.readTextFile(getClass()
                .getClassLoader().getResourceAsStream("user-demographic-metric.json"));

        final String IMPERIAL_USERDEMO_RESPONSE = FileUtils.readTextFile(getClass()
                .getClassLoader().getResourceAsStream("user-demographic-imperial.json"));

        final String USER_RESPONSE = FileUtils.readTextFile(getClass()
                .getClassLoader().getResourceAsStream("user.json"));

        final String WEIGHT_RESPONSE = FileUtils.readTextFile(getClass()
                .getClassLoader().getResourceAsStream("user-weights.json"));

        final String INVALID_TOKEN = FileUtils.readTextFile(getClass()
                .getClassLoader().getResourceAsStream("500-internal-server-error.json"));

        final String SKILL_LEVELS_RESPONSE = FileUtils.readTextFile(getClass()
                .getClassLoader().getResourceAsStream("skill-levels.json"));

        final String USER_DEMO_PUT_RESPONSE = FileUtils.readTextFile(getClass()
                .getClassLoader().getResourceAsStream("user-demographic-metric.json"));

        final String WEIGHT_POST_RESPONSE = FileUtils.readTextFile(getClass()
                .getClassLoader().getResourceAsStream("user-weight-post.json"));


        final String EMPTY_WEIGHT_RESPONSE = FileUtils.readTextFile(getClass()
                .getClassLoader().getResourceAsStream("user-weights-empty.json"));

        final MockResponse imperial_user_demo_response = new MockResponse().setResponseCode(200).setBody(IMPERIAL_USERDEMO_RESPONSE);
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
                        && (request.getHeaders().toString().contains(SUCCESS_AUTH_TOKEN) ||
                        request.getPath().toLowerCase().contains(TOKEN_FOR_IMPERIAL))) {
                    return good_weights_response;
                } else if (request.getPath().toLowerCase().contains(USER_WEIGHT_PATH)
                        && request.getHeaders().toString().contains(FAILURE_AUTH_TOKEN)) {
                    return empty_weight_response;
                } else if (request.getPath().toLowerCase().contains(USER_DEMO_BY_LOGGED_IN_PATH)
                        && request.getHeaders().toString().contains(TOKEN_FOR_IMPERIAL)) {
                    return imperial_user_demo_response;
                } else if (request.getPath().toLowerCase().contains(USER_DEMO_BY_LOGGED_IN_PATH)
                        && request.getHeaders().toString().contains(SUCCESS_AUTH_TOKEN)) {
                    return good_user_demo_response;
                } else if (request.getPath().toLowerCase().contains(USER_DEMO_BY_LOGGED_IN_PATH)
                        && request.getHeaders().toString().contains(FAILURE_AUTH_TOKEN)) {
                    return good_user_demo_response;
                } else if (request.getPath().toLowerCase().contains(USER_PATH)
                        && (request.getHeaders().toString().contains(SUCCESS_AUTH_TOKEN) ||
                        request.getPath().toLowerCase().contains(TOKEN_FOR_IMPERIAL))) {
                    return good_user_response;
                } else if (request.getPath().toLowerCase().contains(SKILL_LEVELS_PATH)) {
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
        try {
            mockWebServer.start();
        } catch (IOException ex){
            Log.d(TAG, ex.toString());
        }
        Environment environment = new Environment(mockWebServer.url("").toString());
        EnvironmentManager.getInstance().setEnvironment(environment);
        SystemClock.sleep(500);
        UserLogins.setUserDemographicId("3154");
        UserLogins.setUserLogin("admin");
        UserLogins.setUserId("2802");
    }

    @After
    public void tearDown() {
        super.tearDown(mActivityRule.getActivity());
    }

    @Test
    public void dateFragmentTest(){
        AuthToken.getInstance().setAccessToken(SUCCESS_AUTH_TOKEN);
        onNavMyProfilePressed();
        profilePageIsDisplayed();
        SystemClock.sleep(DELAY_TIME);
        onView(allOf(withId(R.id.birthdayEditText),
                withParent(withId(R.id.fragment_profile))))
                .perform(scrollTo(), click());

        SystemClock.sleep(DELAY_TIME);

        onView(withId(android.R.id.button1)).check(matches(isDisplayed())).perform(click());

        SystemClock.sleep(DELAY_TIME);

        onView(allOf(withId(R.id.ageText),
                withParent(withId(R.id.fragment_profile))))
                .perform(scrollTo(), click());

        SystemClock.sleep(DELAY_TIME);

        onView(withId(android.R.id.button2)).check(matches(isDisplayed())).perform(click());
        SystemClock.sleep(DELAY_TIME);
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
        SystemClock.sleep(DELAY_TIME);
        onView(withText(R.string.switchMeasureToImperial)).perform(scrollTo(), click());
        SystemClock.sleep(DELAY_TIME);
        onView(withText(R.string.switchMeasureToMetric)).perform(scrollTo(), click());
        onView(withId(R.id.weightEditText)).perform(replaceText("123"));
        onView(withId(R.id.heightEditText)).perform(replaceText("456"));
        SystemClock.sleep(DELAY_TIME);
        onView(withId(R.id.switchMeasurement)).perform(scrollTo(), click());
        SystemClock.sleep(DELAY_TIME);
        onView(allOf(withId(R.id.saveButton),
                withText("Save"),
                withParent(withId(R.id.fragment_profile)))).
                perform(scrollTo(), click());
        SystemClock.sleep(DELAY_TIME);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Start Workout")).perform(click());
        onNavMyProfilePressed();
        profilePageIsDisplayed();
        pressBack();
    }



    @Test
    public void imperialMeasureTest(){
        AuthToken.getInstance().setAccessToken(TOKEN_FOR_IMPERIAL);
        onNavMyProfilePressed();
        profilePageIsDisplayed();
        try {
            onView(withText(R.string.switchMeasureToMetric)).perform(click());
        } catch (Exception e) {
            onView(withText(R.string.switchMeasureToImperial)).perform(click());
        }

        SystemClock.sleep(DELAY_TIME);
        onView(withId(R.id.weightEditText)).perform(replaceText("123"));
        onView(withId(R.id.heightEditText)).perform(replaceText("456"));
        onView(withId(R.id.switchMeasurement)).perform(click());
        SystemClock.sleep(DELAY_TIME);
        onView(allOf(withId(R.id.saveButton),
                withText("Save"),
                withParent(withId(R.id.fragment_profile)))).
                perform(scrollTo(), click());
        SystemClock.sleep(DELAY_TIME);
        pressBack();
    }

    @Test
    public void emptyRealmDataTest(){
        AuthToken.getInstance().setAccessToken(FAILURE_AUTH_TOKEN);
        onNavMyProfilePressed();
        profilePageIsDisplayed();
        onView(allOf(withId(R.id.saveButton),
                withText("Save"),
                withParent(withId(R.id.fragment_profile)))).
                perform(scrollTo(), click());
        pressBack();
        SystemClock.sleep(DELAY_TIME);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Start Workout")).perform(click());
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
