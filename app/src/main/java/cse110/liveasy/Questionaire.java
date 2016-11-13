package cse110.liveasy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Questionaire extends AppCompatActivity {

    Boolean contactIsOpen = false;
    Boolean aboutMeIsOpen = false;
    Boolean preferenceIsOpen = false;
    Boolean petPeevesIsOpen = false;
    Boolean allergiesIsOpen = false;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);

        extras = getIntent().getExtras();

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

    /**
     *  Checks that all fields have input and stores them in firebase
     */
    public void uploadData(View view){
        //if statement checks to see that all fields are populated
        if(validate(view)){
            TextView currentText;
            String currentString;
            CheckBox currentCheckBox;
            Map<String,Object> preferences = new HashMap<String,Object>();


            /** upload to database **/

            //emergency contact name
            currentText = (TextView)findViewById(R.id.input_emergency_name);
            currentString = currentText.getText().toString();
            preferences.put("e_contact_name", currentString);

            //emergency contact relationship
            currentText = (TextView)findViewById(R.id.input_emergency_relationship);
            currentString = currentText.getText().toString();
            preferences.put("e_contact_relationship", currentString);

            //emergency contact phone number
            currentText = (TextView)findViewById(R.id.input_emergency_phone);
            currentString = currentText.getText().toString();
            preferences.put("e_contact_phone_number", currentString);

            //about me
            currentText = (TextView)findViewById(R.id.input_about_me);
            currentString = currentText.getText().toString();
            preferences.put("about_me", currentString);

            //smoking
            currentCheckBox = (CheckBox)findViewById(R.id.smoking_checkbox);
            if(currentCheckBox.isChecked()){
                preferences.put("smoking", new Boolean(true));
            }
            else{
                preferences.put("smoking", new Boolean(false));
            }

            //drinking
            currentCheckBox = (CheckBox)findViewById(R.id.drinking_checkbox);
            if(currentCheckBox.isChecked()){
                preferences.put("drinking", new Boolean(true));
            }
            else{
                preferences.put("drinking", new Boolean(false));
            }

            //guests
            currentCheckBox = (CheckBox)findViewById(R.id.guests_checkbox);
            if(currentCheckBox.isChecked()){
                preferences.put("guests", new Boolean(true));
            }
            else{
                preferences.put("guests", new Boolean(false));
            }

            //pets
            currentCheckBox = (CheckBox)findViewById(R.id.pets_checkbox);
            if(currentCheckBox.isChecked()){
                preferences.put("pets", new Boolean(true));
            }
            else{
                preferences.put("pets", new Boolean(false));
            }

            //pet peeves
            currentText = (TextView)findViewById(R.id.input_pet_peeve);
            currentString = currentText.getText().toString();
            preferences.put("pet_peeves", currentString);

            //allergies
            currentText = (TextView)findViewById(R.id.input_allergies);
            currentString = currentText.getText().toString();
            preferences.put("allergies", currentString);

            //uploading...
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference().child("users").child(extras.getString("username"));
            ref.updateChildren(preferences);

            /** Go to homepage **/
            Intent goToHomePage = new Intent(this, NavDrawerActivity.class);
            goToHomePage.putExtra("username", extras.getString("username"));
            startActivity(goToHomePage);
            finish();
        }
        else{
            //do nothing
        }

    }

    /**
     * Validates the inputs in the questionare
     * @return true if everything is inputed correctly
     */
    private Boolean validate(View view){
        TextView currentView;
        String currentText;
        //check emergency contact name
        currentView = (TextView)findViewById(R.id.input_emergency_name);
        currentText = currentView.getText().toString();
        if(currentText.equals("")){
            createToast("Emergency contact name cannot be empty");
            return false;
        }


        //input_emergency_relationship
        currentView = (TextView)findViewById(R.id.input_emergency_relationship);
        currentText = currentView.getText().toString();
        if(currentText.equals("")){
            createToast("Emergency contact relationship cannot be empty");
            return false;
        }

        //input_emergency_phone
        currentView = (TextView)findViewById(R.id.input_emergency_phone);
        currentText = currentView.getText().toString();
        if(currentText.equals("")){
            createToast("Emergency contact phone number cannot be missing");
            return false;
        }else if(currentText.length() > 10) {
            createToast("Format phone number as digits only");
            return false;
        }else if(currentText.length() < 10){
            createToast("Phone number incomplete");
            return false;
        }

        //input_about_me
        currentView = (TextView)findViewById(R.id.input_about_me);
        currentText = currentView.getText().toString();
        if(currentText.equals("")){
            createToast("About me cannot be empty");
            return false;
        }

        //input_pet_peeve
        currentView = (TextView)findViewById(R.id.input_pet_peeve);
        currentText = currentView.getText().toString();
        if(currentText.equals("")){
            createToast("Pet peeves cannot be empty");
            return false;
        }

        //input_allergies
        currentView = (TextView)findViewById(R.id.input_allergies);
        currentText = currentView.getText().toString();
        if(currentText.equals("")){
            createToast("Allergies cannot be empty");
            return false;
        }


        return true;
    }

    /**
     * Will create a toast with specified string
     * @param message string to be displayed in the toast
     */
    private void createToast(String message){
        Toast toast = Toast.makeText(this, message,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
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