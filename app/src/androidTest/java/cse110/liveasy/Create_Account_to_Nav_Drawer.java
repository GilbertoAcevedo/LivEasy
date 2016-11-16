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
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Create_Account_to_Nav_Drawer {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void create_Account_to_Nav_Drawer() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.link_signup), withText("No account yet? Create one.")));
        appCompatTextView.perform(scrollTo(), click());

        ViewInteraction appCompatEditText = onView(
                withId(R.id.input_name));
        appCompatEditText.perform(scrollTo(), replaceText("John Doe"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.input_username));
        appCompatEditText2.perform(scrollTo(), replaceText("johndoe"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_name), withText("John Doe")));
        appCompatEditText3.perform(scrollTo(), longClick());

        ViewInteraction appCompatEditText4 = onView(
                withId(R.id.input_email));
        appCompatEditText4.perform(scrollTo(), replaceText("johndoe@example.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                withId(R.id.input_mobile));
        appCompatEditText5.perform(scrollTo(), replaceText("8580000000"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText6 = onView(
                withId(R.id.input_password));
        appCompatEditText6.perform(scrollTo(), replaceText("password"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                withId(R.id.input_reEnterPassword));
        appCompatEditText7.perform(scrollTo(), replaceText("password"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_signup), withText("Create Account")));
        appCompatButton.perform(scrollTo(), click());

        ActivityTestRule<Questionaire> mActivityTestRule = new ActivityTestRule<>(Questionaire.class);

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.eContactText), withText("+ Emergency Contact Info")));
        appCompatTextView2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.input_emergency_name),
                        withParent(withId(R.id.contact_layout))));
        appCompatEditText8.perform(scrollTo(), replaceText("John Doe"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.input_emergency_relationship),
                        withParent(withId(R.id.contact_layout))));
        appCompatEditText9.perform(scrollTo(), replaceText("Friend"), closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.input_emergency_phone),
                        withParent(withId(R.id.contact_layout))));
        appCompatEditText10.perform(scrollTo(), replaceText("8580000000"), closeSoftKeyboard());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.eContactText), withText("- Emergency Contact Info")));
        appCompatTextView3.perform(scrollTo(), click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.about_me_text), withText("+ About Me")));
        appCompatTextView4.perform(scrollTo(), click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.input_about_me),
                        withParent(withId(R.id.about_me_layout))));
        appCompatEditText11.perform(scrollTo(), replaceText("I like hanging out"), closeSoftKeyboard());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.preferences_text), withText("+ Preferences")));
        appCompatTextView5.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.smoking_checkbox), withText("Smoking"),
                        withParent(withId(R.id.questionnaire_checkboxes))));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.guests_checkbox), withText("Guests"),
                        withParent(withId(R.id.questionnaire_checkboxes))));
        appCompatCheckBox2.perform(scrollTo(), click());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(R.id.pet_peeves_text), withText("+ Pet Peeves")));
        appCompatTextView6.perform(scrollTo(), click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.input_pet_peeve),
                        withParent(withId(R.id.pet_peeve_layout))));
        appCompatEditText12.perform(scrollTo(), replaceText("I hate everyone except me"), closeSoftKeyboard());

        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(R.id.allergies_text), withText("+ Allergies")));
        appCompatTextView7.perform(scrollTo(), click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.input_allergies),
                        withParent(withId(R.id.allergies_layout))));
        appCompatEditText13.perform(scrollTo(), replaceText("Sea salt"), closeSoftKeyboard());

        ViewInteraction appCompatTextView8 = onView(
                allOf(withId(R.id.allergies_text), withText("- Allergies")));
        appCompatTextView8.perform(scrollTo(), click());

        ViewInteraction appCompatButton3 = onView(
                withText("Save"));
        appCompatButton3.perform(scrollTo(), click());

//        ActivityTestRule<NavDrawerActivity> mActivityTestRule = new ActivityTestRule<>(NavDrawerActivity.class);
//
//        ViewInteraction button = onView(
//                allOf(withId(R.id.button_creategroup),
//                        childAtPosition(
//                                allOf(withId(R.id.activity_main),
//                                        childAtPosition(
//                                                withId(R.id.fragment_home1),
//                                                0)),
//                                1),
//                        isDisplayed()));
//        button.check(matches(isDisplayed()));
//
//        ViewInteraction button2 = onView(
//                allOf(withId(R.id.JoinGroup),
//                        childAtPosition(
//                                allOf(withId(R.id.activity_main),
//                                        childAtPosition(
//                                                withId(R.id.fragment_home1),
//                                                0)),
//                                2),
//                        isDisplayed()));
//        button2.check(matches(isDisplayed()));
//
//        ViewInteraction imageView2 = onView(
//                allOf(withId(R.id.main_profile_image1),
//                        childAtPosition(
//                                allOf(withId(R.id.activity_main),
//                                        childAtPosition(
//                                                withId(R.id.fragment_home1),
//                                                0)),
//                                0),
//                        isDisplayed()));
//        imageView2.check(matches(isDisplayed()));

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