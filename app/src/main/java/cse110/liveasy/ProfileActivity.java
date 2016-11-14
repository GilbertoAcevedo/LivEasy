package cse110.liveasy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    Bundle extras;
    String originalPhoneNumber;
    String changedPhoneNumber;



    // Spinner flags
    boolean smokingSpinnerFlag = false;

    boolean canEdit = false;
    boolean changeFlag = false;

    Map<String, Object> userMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //final View theView = findViewById(R.id.profile_activity);

        extras = getIntent().getExtras();
        String memberName = (String) extras.get("memberName");
        String userName = (String) extras.get("username");

        Button saveButton = (Button) findViewById(R.id.save_button);
        if(userName.equals(memberName)) {
            canEdit = true;
        }
        else
        {
            saveButton.setVisibility(View.GONE);
        }

        // Initialize Spinners
        final Spinner smokingspinner = (Spinner) findViewById(R.id.smoking_spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smokingspinner.setAdapter(adapter);



        getSupportActionBar().setTitle(memberName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final FirebaseDatabase ref = FirebaseDatabase.getInstance();
        final DatabaseReference uRef = ref.getReference().child("users").child(memberName);

        ValueEventListener userListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Map<String, Object> currentUserMap = (HashMap<String, Object>) dataSnapshot.getValue();
                userMap = currentUserMap;

                //TODO IMAGES

                TextView phone = (TextView) findViewById(R.id.main_profile_number);
                String formattedNumber = PhoneNumberUtils.formatNumber((String) currentUserMap.get("phone_number"), "US");
                originalPhoneNumber = formattedNumber;
                phone.setText(formattedNumber);


                TextView email = (TextView) findViewById(R.id.main_profile_email);
                email.setText((String) currentUserMap.get("email"));

                TextView aboutMe = (TextView) findViewById(R.id.about_me1);
                aboutMe.setText((String) currentUserMap.get("about_me"));

                TextView allergies = (TextView) findViewById(R.id.allergies1);
                allergies.setText((String) currentUserMap.get("allergies"));

                TextView drinking = (TextView) findViewById(R.id.drinking1);
                if ((boolean)currentUserMap.get("drinking")) {
                    drinking.setText("I am OK with it");
                }
                else {
                    drinking.setText("I am NOT ok with it");
                }

                //TextView smoking = (TextView) findViewById(R.id.smoking1);
                if ((boolean)currentUserMap.get("smoking")) {
                    smokingspinner.setSelection(adapter.getPosition("I am OK with it"));
                    smokingSpinnerFlag = true;
                    //smoking.setText("I am OK with it");

                }
                else {
                    smokingspinner.setSelection(adapter.getPosition("I am NOT ok with it"));
                    smokingSpinnerFlag = false;
                    //smoking.setText("I am not OK with it");
                }

                TextView pets = (TextView) findViewById(R.id.pets1);
                if ((boolean)currentUserMap.get("pets")) {
                    pets.setText("I am OK with it");
                }
                else {
                    pets.setText("I am NOT ok with it");
                }

                TextView guests = (TextView) findViewById(R.id.guests1);
                if ((boolean)currentUserMap.get("guests")) {
                    guests.setText("I am OK with it");
                }
                else {
                    guests.setText("I am NOT ok with it");
                }

                TextView petpeeves = (TextView) findViewById(R.id.pet_peeves);
                petpeeves.setText((String) currentUserMap.get("pet_peeves"));

                TextView e_contact_name = (TextView) findViewById(R.id.e_contact_name);
                e_contact_name.setText("Emergency Contact: " + ((String)currentUserMap.get("e_contact_name")));

                TextView e_contact_phone_numbers = (TextView) findViewById(R.id.e_contact_phone);
                String formattedNumber1 = PhoneNumberUtils.formatNumber((String) currentUserMap.get("e_contact_phone_number"), "US");
                e_contact_phone_numbers.setText(formattedNumber1);

                TextView e_contact_relationship = (TextView) findViewById(R.id.e_contact_relationship);
                e_contact_relationship.setText((String) currentUserMap.get("e_contact_relationship"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        uRef.addListenerForSingleValueEvent(userListener);
        uRef.removeEventListener(userListener);

        if(canEdit) {

            // Setup listener for phone text view
            final TextView phoneNumber = (TextView) findViewById(R.id.main_profile_number);
            final TextView phoneNumberType = (TextView) findViewById(R.id.phone_type);
            final EditText editphoneNumber = (EditText) findViewById(R.id.phone_edit_text);

            originalPhoneNumber = phoneNumber.getText().toString();

            phoneNumber.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    phoneNumber.setVisibility(View.GONE);
                    phoneNumberType.setVisibility(View.GONE);
                    editphoneNumber.setVisibility(View.VISIBLE);
                    editphoneNumber.setText("");


                    //onbackpressed, go to prev number that existed

                    editphoneNumber.setOnKeyListener(new View.OnKeyListener() {

                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            changedPhoneNumber = editphoneNumber.getText().toString();


                            // If the event is a key-down event on the "enter" button
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                    (keyCode == KeyEvent.KEYCODE_ENTER)) {

                                if ((changedPhoneNumber.isEmpty()) || (changedPhoneNumber.length() != 10)) {

                                    //check if digits


                                    phoneNumber.setText(originalPhoneNumber);

                                    phoneNumber.setVisibility(View.VISIBLE);
                                    phoneNumberType.setVisibility(View.VISIBLE);
                                    editphoneNumber.setVisibility(View.GONE);
                                } else {
                                    changedPhoneNumber = PhoneNumberUtils.formatNumber(changedPhoneNumber, "US");
                                    phoneNumber.setText(changedPhoneNumber);
                                    changeFlag = true;
                                    phoneNumber.setVisibility(View.VISIBLE);
                                    phoneNumberType.setVisibility(View.VISIBLE);
                                    editphoneNumber.setVisibility(View.GONE);
                                    originalPhoneNumber = changedPhoneNumber;
                                }
                                return false;

                            }
                            return false;

                        }
                    });
                    return false;
                }
            });


            // Set Spinner Listeners
            smokingspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    if (item.equals("I am OK with it")) {
                        if (smokingSpinnerFlag == false) {
                            smokingSpinnerFlag = true;
                            changeFlag = true;
                        }
                    } else {
                        if (smokingSpinnerFlag == true) {
                            smokingSpinnerFlag = false;
                            changeFlag = true;
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(changeFlag == true && canEdit) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Would you like to save changes made?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    uploadData();
                                    changeFlag = false;
                                    Intent goBack = new Intent(ProfileActivity.this, NavDrawerActivity.class);
                                    goBack.putExtra("username", (String)extras.get("username"));
                                    startActivity(goBack);
                                    finish();
                                    //NavUtils.navigateUpTo(this, goBack);


                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent goBack = new Intent(ProfileActivity.this, NavDrawerActivity.class);
                                    goBack.putExtra("username", (String)extras.get("username"));
                                    startActivity(goBack);
                                    finish();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                    Intent goBack = new Intent(this, NavDrawerActivity.class);
                    goBack.putExtra("username", (String)extras.get("username"));
                    startActivity(goBack);
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void uploadData()
    {
        //uploading...
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("users").child(extras.getString("username"));
        userMap.put("phone_number", originalPhoneNumber);
        userMap.put("smoking", smokingSpinnerFlag);
        ref.updateChildren(userMap);
    }

    public void onSaveChangesPressed(View v) {
        System.out.println("In method onSaveChangesPressed.");
        if(changeFlag == true && canEdit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like to save changes made?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            uploadData();
                            changeFlag = false;
                            //finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //finish();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
            Toast toast = Toast.makeText(ProfileActivity.this, "No changes have been made.",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.NO_GRAVITY, 0, 0);
            toast.show();
            //finish();
        }

    }

    @Override
    public void onBackPressed(){
        if(changeFlag == true && canEdit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like to save changes made?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            uploadData();
                            changeFlag = false;
                            Intent goBack = new Intent(ProfileActivity.this, NavDrawerActivity.class);
                            goBack.putExtra("username", (String) extras.get("username"));
                            startActivity(goBack);
                            finish();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent goBack = new Intent(ProfileActivity.this, NavDrawerActivity.class);
                            goBack.putExtra("username", (String) extras.get("username"));
                            startActivity(goBack);
                            finish();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        }
        else
        {
            Intent goBack = new Intent(this, NavDrawerActivity.class);
            goBack.putExtra("username", (String) extras.get("username"));
            startActivity(goBack);
            finish();
        }
    }
}
