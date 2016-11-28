package cse110.liveasy;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RequestTest {

    @Rule
    public ActivityTestRule<LoginActivity> login1 = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void requestTest() {

        ViewInteraction appCompatEditText3 = onView(
                withId(R.id.input_username));
        appCompatEditText3.perform(scrollTo(), replaceText("curry"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                withId(R.id.input_password));
        appCompatEditText4.perform(scrollTo(), replaceText("123123qwe"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Login")));
        appCompatButton.perform(scrollTo(), click());


        ActivityTestRule<NavDrawerActivity> nav1 = new ActivityTestRule<>(NavDrawerActivity.class);


        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.JoinGroup), withText("Join Group"),
                        withParent(allOf(withId(R.id.activity_main),
                                withParent(withId(R.id.fragment_home1)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ActivityTestRule<JoinGroup> join1 = new ActivityTestRule<>(JoinGroup.class);



        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.editText6),
                        withParent(allOf(withId(R.id.activity_create_group),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("-KXcvWrWZRaaFGFODeDy"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.button9), withText("Join"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.button9), withText("Join"), isDisplayed()));
        appCompatButton5.perform(click());



        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        withParent(allOf(withId(R.id.buttonPanel),
                                withParent(withId(R.id.parentPanel)))),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ActivityTestRule<NavDrawerActivity> nav3 = new ActivityTestRule<>(NavDrawerActivity.class);


        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Log Out"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ActivityTestRule<LoginActivity> login2 = new ActivityTestRule<>(LoginActivity.class);


        ViewInteraction appCompatEditText7 = onView(
                withId(R.id.input_username));
        appCompatEditText7.perform(scrollTo(), click());

        ViewInteraction appCompatEditText8 = onView(
                withId(R.id.input_username));
        appCompatEditText8.perform(scrollTo(), replaceText("duncan"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                withId(R.id.input_password));
        appCompatEditText9.perform(scrollTo(), replaceText("123123qwe"), closeSoftKeyboard());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.btn_login), withText("Login")));
        appCompatButton7.perform(scrollTo(), click());

        ActivityTestRule<NavDrawerActivity> nav2 = new ActivityTestRule<>(NavDrawerActivity.class);


        ViewInteraction appCompatImageButton15 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton15.perform(click());

        ViewInteraction appCompatImageButton16 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton16.perform(click());


        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Manage Requests (1)"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());


        ActivityTestRule<ManageRequests> manageRequests = new ActivityTestRule<>(ManageRequests.class);




        ViewInteraction button = onView(
                allOf(withText("Accept"), isDisplayed()));
        button.perform(click());


        ViewInteraction appCompatImageButton8 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        ViewInteraction appCompatImageButton22 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton22.perform(click());

        ActivityTestRule<NavDrawerActivity> navDrawer6 = new ActivityTestRule<>(NavDrawerActivity.class);


        ViewInteraction appCompatCheckedTextView6 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Log Out"), isDisplayed()));
        appCompatCheckedTextView6.perform(click());



        ViewInteraction appCompatEditText12 = onView(
                withId(R.id.input_username));
        appCompatEditText12.perform(scrollTo(), replaceText("curry"), closeSoftKeyboard());

        ViewInteraction appCompatEditText16 = onView(
                withId(R.id.input_password));
        appCompatEditText16.perform(scrollTo(), replaceText("123123qwe"), closeSoftKeyboard());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.btn_login), withText("Login")));
        appCompatButton15.perform(scrollTo(), click());



        ViewInteraction appCompatImageButton69 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton69.perform(click());


        ActivityTestRule<NavDrawerActivity> navDrawer8 = new ActivityTestRule<>(NavDrawerActivity.class);

        ViewInteraction appCompatImageButton70 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton70.perform(click());



        ViewInteraction appCompatCheckedTextView19 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Leave Group"), isDisplayed()));
        appCompatCheckedTextView19.perform(click());

        ViewInteraction appCompatButton24 = onView(
                allOf(withId(android.R.id.button1), withText("Yes"),
                        withParent(allOf(withId(R.id.buttonPanel),
                                withParent(withId(R.id.parentPanel)))),
                        isDisplayed()));
        appCompatButton24.perform(click());


    }

}
