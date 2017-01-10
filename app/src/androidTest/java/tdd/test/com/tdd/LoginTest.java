package tdd.test.com.tdd;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static junit.framework.Assert.assertTrue;

/**
 * Created by sujan on 1/10/2017.
 */

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    private ViewInteraction emailField, passwordField, loginButton;

    @Before
    public void setup(){
        emailField = onView(withId(R.id.email));
        passwordField = onView(withId(R.id.password));
        loginButton = onView(withId(R.id.email_sign_in_button));
    }

    @Test
    public void testValidLogin(){
        emailField.perform(clearText(), typeText("sujan@shrestha.com"));
        passwordField.perform(clearText(), typeText("password"));
        loginButton.perform(click());
        assertTrue(getActivityInstance() instanceof LoginActivity);

        emailField.perform(clearText(), typeText("foo@example.com"));
        passwordField.perform(clearText(), typeText("hello"));
        loginButton.perform(click());
        assertTrue(getActivityInstance() instanceof MainActivity);
        onView(withId(R.id.username)).check(matches(withText("foo@example.com")));

    }

    @Test
    public void testInvalidLogin(){
        loginButton.perform(click());
        assertTrue(getActivityInstance() instanceof LoginActivity);

        emailField.perform(typeText("email"));
        passwordField.perform(typeText("password"));
        loginButton.perform(click());
        assertTrue(getActivityInstance() instanceof LoginActivity);

        emailField.perform(clearText());
        passwordField.perform(clearText());
        loginButton.perform(click());
        assertTrue(getActivityInstance() instanceof LoginActivity);
    }


    private Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable( ) {
            public void run() {
                Activity currentActivity = null;
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()){
                    currentActivity = (Activity) resumedActivities.iterator().next();
                    activity[0] = currentActivity;
                }
            }
        });

        return activity[0];
    }
}
