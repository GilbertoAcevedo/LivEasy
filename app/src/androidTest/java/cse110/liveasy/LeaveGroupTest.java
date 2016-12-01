package cse110.liveasy;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/* TEST SCENARIO:

Given I have an account,
AND I am logged in,
AND I have the nav drawer open,
When I click on the leave group option,
Then I will be removed from the group and be sent to the home page with the options
of joining or creating a group.

 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LeaveGroupTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void leaveGroupTest() {

        //Thread.sleep(4000);

        ViewInteraction appCompatEditText = onView(
                withId(R.id.input_username));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.input_username));
        appCompatEditText2.perform(scrollTo(), replaceText("gmgonzal9"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_username), withText("gmgonzal9")));
        appCompatEditText3.perform(pressImeActionButton());

        ViewInteraction appCompatEditText4 = onView(
                withId(R.id.input_password));
        appCompatEditText4.perform(scrollTo(), replaceText("password"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.input_password), withText("password")));
        appCompatEditText5.perform(pressImeActionButton());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Login")));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Leave Group"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Yes"),
                        withParent(allOf(withId(R.id.buttonPanel),
                                withParent(withId(R.id.parentPanel)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.button_creategroup),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(R.id.fragment_home1),
                                                0)),
                                1),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.JoinGroup),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(R.id.fragment_home1),
                                                0)),
                                2),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.activity_main),
                        childAtPosition(
                                allOf(withId(R.id.fragment_home1),
                                        childAtPosition(
                                                withId(R.id.content_frame),
                                                0)),
                                0),
                        isDisplayed()));
        relativeLayout.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
