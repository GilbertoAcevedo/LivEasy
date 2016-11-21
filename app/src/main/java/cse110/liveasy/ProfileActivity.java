package cse110.liveasy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    Bundle extras;
    String originalPhoneNumber;
    String changedPhoneNumber;
    String originalEmail;
    String changedEmail;
    String originalbio;
    String changedbio;
    String changedPetPeeves;
    String originalPetPeeves;
    String originalAllergies;
    String changedAllergies;
    String originaleName;
    String changedeName;
    String originalePhone;
    String changedePhone;



    // Spinner flags
    boolean smokingSpinnerFlag = false;
    boolean drinkingSpinnerFlag = false;
    boolean petsSpinnerFlag = false;
    boolean guestsSpinnerFlag = false;

    boolean canEdit = false;
    boolean changeFlag = false;

    Map<String, Object> userMap;

    private Button mTakePhoto;
    private ImageView mImageView;
    private static final String TAG = "upload";
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    String url = "";
    File photoFile = null;
    Bitmap bitmap = null;
    Boolean changedPic = false;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //final View theView = findViewById(R.id.profile_activity);

        extras = getIntent().getExtras();
        String memberName = (String) extras.get("memberName");
        String userName = (String) extras.get("username");
        changeFlag = false;



        // Initialize Spinners
        final Spinner smokingspinner = (Spinner) findViewById(R.id.smoking_spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smokingspinner.setAdapter(adapter);

        final Spinner drinkingspinner = (Spinner) findViewById(R.id.drinking_spinner);
        final ArrayAdapter<CharSequence> drinkingadapter = ArrayAdapter.createFromResource(this,
                R.array.Options, android.R.layout.simple_spinner_item);
        drinkingadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drinkingspinner.setAdapter(drinkingadapter);

        final Spinner petspinner = (Spinner) findViewById(R.id.pets_spinner);
        final ArrayAdapter<CharSequence> petsadapter = ArrayAdapter.createFromResource(this,
                R.array.Options, android.R.layout.simple_spinner_item);
        petsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petspinner.setAdapter(petsadapter);

        final Spinner guestspinner = (Spinner) findViewById(R.id.guest_spinner);
        final ArrayAdapter<CharSequence> guestadapter = ArrayAdapter.createFromResource(this,
                R.array.Options, android.R.layout.simple_spinner_item);
        guestadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        guestspinner.setAdapter(guestadapter);



        // Check if user can edit profile
        Button saveButton = (Button) findViewById(R.id.save_button);
        TextView smokingView = (TextView) findViewById(R.id.smoking1);
        TextView drinkingView = (TextView) findViewById(R.id.drinking1);
        TextView petsView = (TextView) findViewById(R.id.pets1);
        TextView guestsView = (TextView) findViewById(R.id.guests1);
        if(userName.equals(memberName)) {
            canEdit = true;
            smokingView.setVisibility(View.GONE);
            drinkingView.setVisibility(View.GONE);
            petsView.setVisibility(View.GONE);
            guestsView.setVisibility(View.GONE);
        }
        else
        {
            saveButton.setVisibility(View.GONE);
            smokingspinner.setVisibility(View.GONE);
            drinkingspinner.setVisibility(View.GONE);
            petspinner.setVisibility(View.GONE);
            guestspinner.setVisibility(View.GONE);
        }



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
                changeFlag = false;

                System.out.println((String)currentUserMap.get("photo_url"));

                CircleImageView selfie = (CircleImageView) findViewById(R.id.profile_image);
                Picasso.with(ProfileActivity.this)
                        .load((String)currentUserMap.get("photo_url"))
                        .resize(200,200)
                        .centerCrop()
                        .placeholder(R.drawable.blank)
                        .into(selfie);

                TextView phone = (TextView) findViewById(R.id.main_profile_number);
                String formattedNumber = PhoneNumberUtils.formatNumber((String) currentUserMap.get("phone_number"), "US");
                originalPhoneNumber = formattedNumber;
                phone.setText(formattedNumber);


                TextView email = (TextView) findViewById(R.id.main_profile_email);
                email.setText((String) currentUserMap.get("email"));
                originalEmail = (String) currentUserMap.get("email");

                TextView aboutMe = (TextView) findViewById(R.id.about_me1);
                aboutMe.setText((String) currentUserMap.get("about_me"));
                originalbio = aboutMe.getText().toString();

                TextView allergies = (TextView) findViewById(R.id.allergies1);
                allergies.setText((String) currentUserMap.get("allergies"));
                originalAllergies = allergies.getText().toString();

                TextView drinking = (TextView) findViewById(R.id.drinking1);
                if ((boolean)currentUserMap.get("drinking")) {
                    drinkingspinner.setSelection(drinkingadapter.getPosition("I am OK with it"));
                    drinkingSpinnerFlag = true;
                    drinking.setText("I am OK with it");
                }
                else {
                    drinkingspinner.setSelection(drinkingadapter.getPosition("I am NOT ok with it"));
                    drinkingSpinnerFlag = false;
                    drinking.setText("I am NOT ok with it");
                }

                TextView smoking = (TextView) findViewById(R.id.smoking1);
                if ((boolean)currentUserMap.get("smoking")) {
                    smokingspinner.setSelection(adapter.getPosition("I am OK with it"));
                    smokingSpinnerFlag = true;
                    smoking.setText("I am OK with it");
                }
                else {
                    smokingspinner.setSelection(adapter.getPosition("I am NOT ok with it"));
                    smokingSpinnerFlag = false;
                    smoking.setText("I am NOT ok with it");
                }

                TextView pets = (TextView) findViewById(R.id.pets1);
                if ((boolean)currentUserMap.get("pets")) {
                    petspinner.setSelection(petsadapter.getPosition("I am OK with it"));
                    petsSpinnerFlag = true;
                    pets.setText("I am OK with it");
                }
                else {
                    petspinner.setSelection(petsadapter.getPosition("I am NOT ok with it"));
                    petsSpinnerFlag = false;
                    pets.setText("I am NOT ok with it");
                }

                TextView guests = (TextView) findViewById(R.id.guests1);
                if ((boolean)currentUserMap.get("guests")) {
                    guestspinner.setSelection(guestadapter.getPosition("I am OK with it"));
                    guestsSpinnerFlag = true;
                    guests.setText("I am OK with it");
                }
                else {
                    guestspinner.setSelection(guestadapter.getPosition("I am NOT ok with it"));
                    guestsSpinnerFlag = false;
                    guests.setText("I am NOT ok with it");
                }

                TextView petpeeves = (TextView) findViewById(R.id.pet_peeves);
                petpeeves.setText((String) currentUserMap.get("pet_peeves"));
                originalPetPeeves = petpeeves.getText().toString();

                TextView e_contact_name = (TextView) findViewById(R.id.e_contact_name);
                e_contact_name.setText(((String)currentUserMap.get("e_contact_name")));
                originaleName = e_contact_name.getText().toString();

                TextView e_contact_phone_numbers = (TextView) findViewById(R.id.e_contact_phone);
                String formattedNumber1 = PhoneNumberUtils.formatNumber((String) currentUserMap.get("e_contact_phone_number"), "US");
                e_contact_phone_numbers.setText(formattedNumber1);
                originalePhone = e_contact_phone_numbers.getText().toString();

                //TextView e_contact_relationship = (TextView) findViewById(R.id.e_contact_relationship);
                //e_contact_relationship.setText((String) currentUserMap.get("e_contact_relationship"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        uRef.addListenerForSingleValueEvent(userListener);
        uRef.removeEventListener(userListener);

        if(canEdit) {

            //set listener for selfie image
            CircleImageView selfie = (CircleImageView) findViewById(R.id.profile_image);
            selfie.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    takePhoto();

                    return false;
                }
            });

            // Set listener for phone text view
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


            // Set email text view listener
            final TextView email  = (TextView) findViewById(R.id.main_profile_email);
            final TextView emailType = (TextView) findViewById(R.id.email_type);
            final EditText editEmail = (EditText) findViewById(R.id.email_edit);

            email.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    email.setVisibility(View.GONE);
                    emailType.setVisibility(View.GONE);
                    editEmail.setVisibility(View.VISIBLE);
                    editEmail.setText("");


                    //onbackpressed, go to prev number that existed

                    editEmail.setOnKeyListener(new View.OnKeyListener() {

                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            changedEmail = editEmail.getText().toString();

                            // If the event is a key-down event on the "enter" button
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                    (keyCode == KeyEvent.KEYCODE_ENTER)) {

                                if (changedEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(changedEmail).matches()){

                                    //check if digits


                                    email.setText(originalEmail);

                                    email.setVisibility(View.VISIBLE);
                                    emailType.setVisibility(View.VISIBLE);
                                    editEmail.setVisibility(View.GONE);

                                }
                                else{
                                    email.setText(changedEmail);
                                    email.setVisibility(View.VISIBLE);
                                    emailType.setVisibility(View.VISIBLE);
                                    editEmail.setVisibility(View.GONE);
                                    originalEmail = changedEmail;
                                    changeFlag = true;

                                }
                                return false;

                            }
                            return false;

                        }
                    });
                    return false;
                }
            });



            // Set About Me listener
            final TextView bio  = (TextView) findViewById(R.id.about_me1);
            final EditText editbio = (EditText) findViewById(R.id.edit_bio);

            bio.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    bio.setVisibility(View.GONE);
                    editbio.setVisibility(View.VISIBLE);
                    editbio.setText("");


                    //onbackpressed, go to prev number that existed

                    editbio.setOnKeyListener(new View.OnKeyListener() {

                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            changedbio = editbio.getText().toString();


                            // If the event is a key-down event on the "enter" button
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                    (keyCode == KeyEvent.KEYCODE_ENTER)) {

                                if(changedbio.equals("")) {
                                    bio.setText(originalbio);
                                    bio.setVisibility(View.VISIBLE);
                                    editbio.setVisibility(View.GONE);
                                }
                                else{
                                    bio.setText(changedbio);
                                    bio.setVisibility(View.VISIBLE);
                                    editbio.setVisibility(View.GONE);
                                    originalbio = changedbio;
                                    changeFlag = true;
                                }

                                return false;

                            }
                            return false;
                        }
                    });

                    return false;
                }
            });




            // Set allergies text view listener
            final TextView allergies = (TextView) findViewById(R.id.allergies1);
            final EditText edit_allergies  = (EditText) findViewById(R.id.edit_allergies);

            allergies.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    allergies.setVisibility(View.GONE);
                    edit_allergies.setVisibility(View.VISIBLE);
                    edit_allergies.setText("");


                    //onbackpressed, go to prev number that existed

                    edit_allergies.setOnKeyListener(new View.OnKeyListener() {

                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            changedAllergies = edit_allergies.getText().toString();


                            // If the event is a key-down event on the "enter" button
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                    (keyCode == KeyEvent.KEYCODE_ENTER)) {

                                if(changedAllergies.equals("")){
                                    allergies.setText(originalAllergies);
                                    allergies.setVisibility(View.VISIBLE);
                                    edit_allergies.setVisibility(View.GONE);
                                }
                                else{
                                    allergies.setText(changedAllergies);
                                    allergies.setVisibility(View.VISIBLE);
                                    edit_allergies.setVisibility(View.GONE);
                                    changeFlag = true;
                                    originalAllergies = changedAllergies;
                                }
                                return false;

                            }
                            return false;

                        }
                    });
                    return false;
                }
            });



            // Set Pet Peeves View Listener

            final TextView petPeeves  = (TextView) findViewById(R.id.pet_peeves);
            final EditText editPetPeeves = (EditText) findViewById(R.id.edit_petPeeves);

            petPeeves.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    petPeeves.setVisibility(View.GONE);
                    editPetPeeves.setVisibility(View.VISIBLE);
                    editPetPeeves.setText("");


                    //onbackpressed, go to prev number that existed

                    editPetPeeves.setOnKeyListener(new View.OnKeyListener() {

                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            changedPetPeeves = editPetPeeves.getText().toString();


                            // If the event is a key-down event on the "enter" button
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                    (keyCode == KeyEvent.KEYCODE_ENTER)) {

                                if(changedPetPeeves.equals("")){
                                    petPeeves.setText(originalPetPeeves);
                                    petPeeves.setVisibility(View.VISIBLE);
                                    editPetPeeves.setVisibility(View.GONE);
                                }
                                else{
                                    petPeeves.setText(changedPetPeeves);
                                    petPeeves.setVisibility(View.VISIBLE);
                                    editPetPeeves.setVisibility(View.GONE);
                                    originalPetPeeves = changedPetPeeves;
                                    changeFlag = true;
                                }
                                return false;

                            }
                            return false;

                        }
                    });
                    return false;
                }
            });

            // Set E Contact Name Listener

            final TextView eName  = (TextView) findViewById(R.id.e_contact_name);
            final EditText editeName = (EditText) findViewById(R.id.edit_contact_name);

            eName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    eName.setVisibility(View.GONE);
                    editeName.setVisibility(View.VISIBLE);
                    editeName.setText("");


                    //onbackpressed, go to prev number that existed

                    editeName.setOnKeyListener(new View.OnKeyListener() {

                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            changedeName = editeName.getText().toString();


                            // If the event is a key-down event on the "enter" button
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                    (keyCode == KeyEvent.KEYCODE_ENTER)) {

                                if(changedeName.equals("")){
                                    eName.setText(originaleName);
                                    eName.setVisibility(View.VISIBLE);
                                    editeName.setVisibility(View.GONE);
                                }
                                else{
                                    eName.setText(changedeName);
                                    eName.setVisibility(View.VISIBLE);
                                    editeName.setVisibility(View.GONE);
                                    originaleName = changedeName;
                                    changeFlag = true;
                                }
                                return false;

                            }
                            return false;

                        }
                    });
                    return false;
                }
            });

            // Set E Contact Phone Listener

            final TextView ePhone  = (TextView) findViewById(R.id.e_contact_phone);
            final EditText editePhone = (EditText) findViewById(R.id.edit_contact_phone);

            ePhone.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    ePhone.setVisibility(View.GONE);
                    editePhone.setVisibility(View.VISIBLE);
                    editePhone.setText("");


                    //onbackpressed, go to prev number that existed

                    editePhone.setOnKeyListener(new View.OnKeyListener() {

                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            changedePhone = editePhone.getText().toString();


                            // If the event is a key-down event on the "enter" button
                            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                    (keyCode == KeyEvent.KEYCODE_ENTER)) {

                                if ((changedePhone.isEmpty()) || (changedePhone.length() != 10)) {

                                    //check if digits


                                    ePhone.setText(originalPhoneNumber);

                                    ePhone.setVisibility(View.VISIBLE);
                                    editePhone.setVisibility(View.GONE);
                                } else {
                                    changedePhone = PhoneNumberUtils.formatNumber(changedePhone, "US");
                                    ePhone.setText(changedePhone);
                                    changeFlag = true;
                                    ePhone.setVisibility(View.VISIBLE);
                                    editePhone.setVisibility(View.GONE);
                                    originalePhone = changedePhone;
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

            drinkingspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    if (item.equals("I am OK with it")) {
                        if (drinkingSpinnerFlag == false) {
                            drinkingSpinnerFlag = true;
                            changeFlag = true;
                        }
                    } else {
                        if (drinkingSpinnerFlag == true) {
                            drinkingSpinnerFlag = false;
                            changeFlag = true;
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            petspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    if (item.equals("I am OK with it")) {
                        if (petsSpinnerFlag == false) {
                            petsSpinnerFlag = true;
                            changeFlag = true;
                        }
                    } else {
                        if (petsSpinnerFlag == true) {
                            petsSpinnerFlag = false;
                            changeFlag = true;
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            guestspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    if (item.equals("I am OK with it")) {
                        if (guestsSpinnerFlag == false) {
                            guestsSpinnerFlag = true;
                            changeFlag = true;
                        }
                    } else {
                        if (guestsSpinnerFlag == true) {
                            guestsSpinnerFlag = false;
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
                                    if(changedPic == true){
                                        progressDialog = new ProgressDialog(ProfileActivity.this,
                                                R.style.AppTheme_Dark_Dialog);
                                        progressDialog.setIndeterminate(true);
                                        progressDialog.setMessage("Uploading picture...");
                                        progressDialog.show();
                                        uploadPicAndJump();
                                    }
                                    else {
                                        changeFlag = false;
                                        Intent goBack = new Intent(ProfileActivity.this, NavDrawerActivity.class);
                                        goBack.putExtra("username", (String) extras.get("username"));
                                        startActivity(goBack);
                                        finish();
                                    }
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
        userMap.put("email", originalEmail);
        userMap.put("smoking", smokingSpinnerFlag);
        userMap.put("about_me", originalbio);
        userMap.put("allergies", originalAllergies);
        userMap.put("drinking", drinkingSpinnerFlag);
        userMap.put("pets", petsSpinnerFlag);
        userMap.put("guests", guestsSpinnerFlag);
        userMap.put("pet_peeves", originalPetPeeves);
        userMap.put("e_contact_name", originaleName);
        userMap.put("e_contact_phone_number", originalePhone);
        ref.updateChildren(userMap);

        // Change user auth email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail(originalEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }
                });

        if( (Boolean)getIntent().getExtras().get("group")) {
            DatabaseReference gref = database.getReference().child("groups").
                    child(getIntent().getExtras().getString("groupID")).
                    child("members").
                    child(getIntent().getExtras().getString("username"));

            gref.updateChildren(userMap);
        }


    }

    public void uploadPic(){
        //uploading...
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference().child("users").child(extras.getString("username"));

        // Save selfie
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = mStorageRef.child(extras.getString("username"));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 33, baos);
        byte[] fileData = baos.toByteArray();
        UploadTask uploadTask = profileRef.putBytes(fileData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.println("Upload unsuccessful");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                url = downloadUrl.toString();
                System.out.println("Profile: "+url);
                userMap.put("photo_url", url);
                ref.updateChildren(userMap);
                progressDialog.dismiss();
            }
        });
    }

    public void uploadPicAndJump(){
        //uploading...
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference().child("users").child(extras.getString("username"));

        // Save selfie
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = mStorageRef.child(extras.getString("username"));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 33, baos);
        byte[] fileData = baos.toByteArray();
        UploadTask uploadTask = profileRef.putBytes(fileData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.println("Upload unsuccessful");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                url = downloadUrl.toString();
                System.out.println("Profile: "+url);
                userMap.put("photo_url", url);
                ref.updateChildren(userMap);

                // Jump to Nav Drawer
                progressDialog.dismiss();
                Intent goBack = new Intent(ProfileActivity.this, NavDrawerActivity.class);
                goBack.putExtra("username", (String) extras.get("username"));
                changedPic = false;
                changeFlag = false;
                startActivity(goBack);
                finish();
            }
        });
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
                            if(changedPic == true){
                                progressDialog = new ProgressDialog(ProfileActivity.this,
                                        R.style.AppTheme_Dark_Dialog);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage("Uploading picture...");
                                progressDialog.show();
                                uploadPic();
                                changedPic = false;
                            }
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
    public void onBackPressed() {
        if (changeFlag == true && canEdit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like to save changes made?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            uploadData();
                            if(changedPic == true){
                                progressDialog = new ProgressDialog(ProfileActivity.this,
                                        R.style.AppTheme_Dark_Dialog);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage("Uploading picture...");
                                progressDialog.show();
                                uploadPicAndJump();
                            }
                            else{
                                changeFlag = false;
                                Intent goBack = new Intent(ProfileActivity.this, NavDrawerActivity.class);
                                goBack.putExtra("username", (String) extras.get("username"));
                                startActivity(goBack);
                                finish();
                            }
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
        } else {
            Intent goBack = new Intent(this, NavDrawerActivity.class);
            goBack.putExtra("username", (String) extras.get("username"));
            startActivity(goBack);
            finish();
        }
    }


    // Picture Taking functionality
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                System.out.println("photoFile was not null*****");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                System.out.println("photoFile was not null*****");
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                System.out.println("photoFile was not null*****");
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/picupload";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();

        File image = new File(storageDir + "/" + imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        Log.i(TAG, "photo path = " + mCurrentPhotoPath);


        return image;
    }

    private void takePhoto() {

        dispatchTakePictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("onActivityResult***");

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            System.out.println("Inside if onActivityResult****");
            bitmap = setPic();

        }
    }

    private Bitmap setPic() {

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        if(bitmap == null)
        {
            System.out.println("bitmap = null...");
        }

        System.out.println("Inside setPic****");

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(mCurrentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap bmRotated = rotateBitmap(bitmap, orientation);

        CircleImageView selfie = (CircleImageView) findViewById(R.id.profile_image);
        Picasso.with(ProfileActivity.this)
                .load(photoFile)
                .resize(200,200)
                .centerCrop()
                .placeholder(R.drawable.blank)
                .into(selfie);

        changeFlag = true;
        changedPic = true;

        return bmRotated;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

}
