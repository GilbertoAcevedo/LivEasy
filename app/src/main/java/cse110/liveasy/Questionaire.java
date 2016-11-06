package cse110.liveasy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Questionaire extends AppCompatActivity {

    Boolean contactIsOpen = false;
    Boolean aboutMeIsOpen = false;
    Boolean preferenceIsOpen = false;
    Boolean petPeevesIsOpen = false;
    Boolean allergiesIsOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);

        TextView eContactText = (TextView)findViewById(R.id.eContactText);
        eContactText.setText("+ Emergency Contact Info");

        TextView aboutMeText = (TextView)findViewById(R.id.about_me_text);
        aboutMeText.setText("+ About Me");

        TextView preferencesText = (TextView)findViewById(R.id.preferences_text);
        preferencesText.setText("+ Preferences");

        TextView petPeevesText = (TextView)findViewById(R.id.pet_peeves_text);
        petPeevesText.setText("+ Pet Peeves");

        TextView allergiesText = (TextView)findViewById(R.id.allergies_text);
        allergiesText.setText("+ Allergies");




        LinearLayout contactLayout = (LinearLayout)findViewById(R.id.contact_layout);
        contactLayout.setVisibility(LinearLayout.GONE);

        LinearLayout aboutMeLayout = (LinearLayout)findViewById(R.id.about_me_layout);
        aboutMeLayout.setVisibility(LinearLayout.GONE);

        RelativeLayout checkboxes = (RelativeLayout)findViewById(R.id.questionnaire_checkboxes);
        checkboxes.setVisibility(RelativeLayout.GONE);

        LinearLayout petPeevesLayout = (LinearLayout)findViewById(R.id.pet_peeve_layout);
        petPeevesLayout.setVisibility(LinearLayout.GONE);

        LinearLayout allergiesLayout = (LinearLayout)findViewById(R.id.allergies_layout);
        allergiesLayout.setVisibility(LinearLayout.GONE);

    }

    public void toggleContact(View view){
        closeAll("contact");
        LinearLayout contactLayout = (LinearLayout)findViewById(R.id.contact_layout);
        if(contactIsOpen){
            TextView eContactText = (TextView)findViewById(R.id.eContactText);
            eContactText.setText("+ Emergency Contact Info");
            contactLayout.setVisibility(LinearLayout.GONE);
            contactIsOpen = false;
        }
        else{
            TextView eContactText = (TextView)findViewById(R.id.eContactText);
            eContactText.setText("- Emergency Contact Info");

            contactLayout.setVisibility(LinearLayout.VISIBLE);
            contactIsOpen = true;
        }
    }

    public void toggleAboutMe(View view){
        closeAll("about_me");
        LinearLayout aboutMeLayout = (LinearLayout)findViewById(R.id.about_me_layout);
        if(aboutMeIsOpen){
            TextView aboutMeText = (TextView)findViewById(R.id.about_me_text);
            aboutMeText.setText("+ About Me");
            aboutMeLayout.setVisibility(LinearLayout.GONE);
            aboutMeIsOpen = false;
        }
        else{
            TextView aboutMeText = (TextView)findViewById(R.id.about_me_text);
            aboutMeText.setText("- About Me");

            aboutMeLayout.setVisibility(LinearLayout.VISIBLE);
            aboutMeIsOpen = true;
        }
    }

    public void togglePreferences(View view){
        closeAll("preferences");
        RelativeLayout preferencesLayout = (RelativeLayout) findViewById(R.id.questionnaire_checkboxes);
        if(preferenceIsOpen){
            TextView preferencesText = (TextView)findViewById(R.id.preferences_text);
            preferencesText.setText("+ Preferences");
            preferencesLayout.setVisibility(LinearLayout.GONE);
            preferenceIsOpen = false;
        }
        else{
            TextView preferencesText = (TextView)findViewById(R.id.preferences_text);
            preferencesText.setText("- Preferences");

            preferencesLayout.setVisibility(LinearLayout.VISIBLE);
            preferenceIsOpen = true;
        }
    }

    public void togglePetPeeves(View view){
        closeAll("pet_peeves");
        LinearLayout petPeevesLayout = (LinearLayout) findViewById(R.id.pet_peeve_layout);
        if(petPeevesIsOpen){
            TextView petPeevesText = (TextView)findViewById(R.id.pet_peeves_text);
            petPeevesText.setText("+ Pet Peeves");
            petPeevesLayout.setVisibility(LinearLayout.GONE);
            petPeevesIsOpen = false;
        }
        else{
            TextView petPeevesText = (TextView)findViewById(R.id.pet_peeves_text);
            petPeevesText.setText("- Pet Peeves");

            petPeevesLayout.setVisibility(LinearLayout.VISIBLE);
            petPeevesIsOpen = true;
        }
    }

    public void toggleAllergies(View view){
        closeAll("allergies");
        LinearLayout allergiesLayout = (LinearLayout) findViewById(R.id.allergies_layout);
        if(allergiesIsOpen){
            TextView allergiesText = (TextView)findViewById(R.id.allergies_text);
            allergiesText.setText("+ Allergies");
            allergiesLayout.setVisibility(LinearLayout.GONE);
            allergiesIsOpen = false;
        }
        else{
            TextView allergiesText = (TextView)findViewById(R.id.allergies_text);
            allergiesText.setText("- Allergies");

            allergiesLayout.setVisibility(LinearLayout.VISIBLE);
            allergiesIsOpen = true;
        }
    }

    public void closeAll(String exception){
        if(!exception.equals("contact")) {
            LinearLayout contactLayout = (LinearLayout) findViewById(R.id.contact_layout);
            TextView eContactText = (TextView) findViewById(R.id.eContactText);
            eContactText.setText("+ Emergency Contact Info");
            contactLayout.setVisibility(LinearLayout.GONE);
            contactIsOpen = false;
        }

        if(!exception.equals("about_me")) {
            LinearLayout aboutMeLayout = (LinearLayout) findViewById(R.id.about_me_layout);
            TextView aboutMeText = (TextView) findViewById(R.id.about_me_text);
            aboutMeText.setText("+ About Me");
            aboutMeLayout.setVisibility(LinearLayout.GONE);
            aboutMeIsOpen = false;
        }

        if(!exception.equals("preferences")) {
            RelativeLayout preferencesLayout = (RelativeLayout) findViewById(R.id.questionnaire_checkboxes);
            TextView preferencesText = (TextView) findViewById(R.id.preferences_text);
            preferencesText.setText("+ Preferences");
            preferencesLayout.setVisibility(LinearLayout.GONE);
            preferenceIsOpen = false;
        }
        if(!exception.equals("pet_peeves")) {

            LinearLayout petPeevesLayout = (LinearLayout) findViewById(R.id.pet_peeve_layout);
            TextView petPeevesText = (TextView) findViewById(R.id.pet_peeves_text);
            petPeevesText.setText("+ Pet Peeves");
            petPeevesLayout.setVisibility(LinearLayout.GONE);
            petPeevesIsOpen = false;
        }

        if(!exception.equals("allergies")) {
            LinearLayout allergiesLayout = (LinearLayout) findViewById(R.id.allergies_layout);
            TextView allergiesText = (TextView) findViewById(R.id.allergies_text);
            allergiesText.setText("+ Allergies");
            allergiesLayout.setVisibility(LinearLayout.GONE);
            allergiesIsOpen = false;
        }

    }
}

//                contactIsOpen = true;
//                aboutMeIsOpen = true;
//                preferenceIsOpen = true;
//                petPeevesIsOpen = true;
//                allergiesIsOpen = true;
//
//                toggleContact(view);
//                toggleAboutMe(view);
//                togglePreferences(view);
//                togglePetPeeves(view);
//                toggleAllergies(view);
