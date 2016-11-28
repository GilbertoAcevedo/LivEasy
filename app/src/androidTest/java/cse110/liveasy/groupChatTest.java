package cse110.liveasy;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
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
public class GroupChatTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void GroupChatTest() {
        ViewInteraction appCompatEditText = onView(
                withId(R.id.input_username));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.input_username));
        appCompatEditText2.perform(scrollTo(), replaceText("duncan"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                withId(R.id.input_password));
        appCompatEditText3.perform(scrollTo(), replaceText("123123qwe"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Login")));
        appCompatButton.perform(scrollTo(), click());

        ActivityTestRule<NavDrawerActivity> NavDrawer = new ActivityTestRule<>(NavDrawerActivity.class);

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ActivityTestRule<GroupChat> groupChat123 = new ActivityTestRule<>(GroupChat.class);



        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Group Chat"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.msg_input),
                        withParent(allOf(withId(R.id.activity_group_chat),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.msg_input),
                        withParent(allOf(withId(R.id.activity_group_chat),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("tgoudz for the win"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.send_msg_btn), withText("Send"),
                        withParent(allOf(withId(R.id.activity_group_chat),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        pressBack();



        ViewInteraction appCompatImageButton5 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Log Out"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                withId(R.id.input_username));
        appCompatEditText6.perform(scrollTo(), click());

        ViewInteraction appCompatEditText7 = onView(
                withId(R.id.input_username));
        appCompatEditText7.perform(scrollTo(), replaceText("kobe"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                withId(R.id.input_password));
        appCompatEditText8.perform(scrollTo(), replaceText("123123qwe"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_login), withText("Login")));
        appCompatButton3.perform(scrollTo(), click());




        ViewInteraction appCompatImageButton6 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ActivityTestRule<NavDrawerActivity> NavDrawer1 = new ActivityTestRule<>(NavDrawerActivity.class);

        ViewInteraction appCompatImageButton30 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton30.perform(click());

        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Group Chat"), isDisplayed()));
        appCompatCheckedTextView3.perform(click());

        ActivityTestRule<GroupChat> groupChat = new ActivityTestRule<>(GroupChat.class);


        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.msg_input),
                        withParent(allOf(withId(R.id.activity_group_chat),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText9.perform(click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.msg_input),
                        withParent(allOf(withId(R.id.activity_group_chat),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText10.perform(replaceText("yah hes great"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.send_msg_btn), withText("Send"),
                        withParent(allOf(withId(R.id.activity_group_chat),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton4.perform(click());


        ViewInteraction appCompatImageButton7 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        ActivityTestRule<NavDrawerActivity> navDrawer2 = new ActivityTestRule<>(NavDrawerActivity.class);



        ViewInteraction appCompatImageButton8 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        ViewInteraction appCompatCheckedTextView4 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Log Out"), isDisplayed()));
        appCompatCheckedTextView4.perform(click());

        ActivityTestRule<LoginActivity> loginActivity2 = new ActivityTestRule<>(LoginActivity.class);


        ViewInteraction appCompatEditText11 = onView(
                withId(R.id.input_username));
        appCompatEditText11.perform(scrollTo(), click());

        ViewInteraction appCompatEditText12 = onView(
                withId(R.id.input_username));
        appCompatEditText12.perform(scrollTo(), replaceText("duncan"), closeSoftKeyboard());


        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.input_password)));
        appCompatEditText15.perform(scrollTo(), replaceText("123123qwe"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btn_login), withText("Login")));
        appCompatButton5.perform(scrollTo(), click());

        ActivityTestRule<NavDrawerActivity> navDrawer3 = new ActivityTestRule<>(NavDrawerActivity.class);


        ViewInteraction appCompatImageButton9 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton9.perform(click());

        ViewInteraction appCompatImageButton10 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton10.perform(click());


        ViewInteraction appCompatCheckedTextView5 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Group Chat"), isDisplayed()));
        appCompatCheckedTextView5.perform(click());

        ActivityTestRule<GroupChat> groupChat2 = new ActivityTestRule<>(GroupChat.class);


        ViewInteraction appCompatImageButton13 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton13.perform(click());

        ActivityTestRule<NavDrawerActivity> navDrawer4 = new ActivityTestRule<>(NavDrawerActivity.class);


        ViewInteraction appCompatImageButton14 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton14.perform(click());

        ViewInteraction appCompatCheckedTextView6 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Log Out"), isDisplayed()));
        appCompatCheckedTextView6.perform(click());

    }

}
