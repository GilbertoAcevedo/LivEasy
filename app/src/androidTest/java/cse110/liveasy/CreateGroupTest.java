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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 Given I am on the “Create a Group Page”
 When I type in a group name AND click “Create Group”
 Then a pop window will be displayed, which includes a shareable group key
 AND the option to copy it to my clipboard AND an okay button to dismiss the pop up
 **/

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateGroupTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void createGroupTest() {
        ViewInteraction appCompatEditText = onView(
                withId(R.id.input_username));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.input_username));
        appCompatEditText2.perform(scrollTo(), replaceText("creategrouptest"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                withId(R.id.input_password));
        appCompatEditText3.perform(scrollTo(), replaceText("password"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Login")));
        appCompatButton.perform(scrollTo(), click());

        ActivityTestRule<NavDrawerActivity> mActivityTestRule2 = new ActivityTestRule<>(NavDrawerActivity.class);

        ViewInteraction button = onView(
                allOf(withId(R.id.button_creategroup),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_creategroup), withText("Create Group"),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ActivityTestRule<CreateGroup> mActivityTestRule3 = new ActivityTestRule<>(CreateGroup.class);

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editText5),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Test Group"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button8), withText("Create"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(android.R.id.button3),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(android.R.id.button1),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ActivityTestRule<NavDrawerActivity> mActivityTestRule4 = new ActivityTestRule<>(NavDrawerActivity.class);

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Leave Group"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button1), withText("Yes"),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Log Out"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

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
