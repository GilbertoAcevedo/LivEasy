package cse110.liveasy;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //final View theView = findViewById(R.id.profile_activity);

        extras = getIntent().getExtras();
        String memberName = (String) extras.get("memberName");

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

                //TODO IMAGES

                TextView phone = (TextView) findViewById(R.id.main_profile_number);
                String formattedNumber = PhoneNumberUtils.formatNumber((String) currentUserMap.get("phone_number"), "US");
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
                    drinking.setText("I am not OK with it");
                }

                TextView smoking = (TextView) findViewById(R.id.smoking1);
                if ((boolean)currentUserMap.get("smoking")) {
                    smoking.setText("I am OK with it");
                }
                else {
                    smoking.setText("I am not OK with it");
                }

                TextView pets = (TextView) findViewById(R.id.pets1);
                if ((boolean)currentUserMap.get("pets")) {
                    pets.setText("I am OK with it");
                }
                else {
                    pets.setText("I am not OK with it");
                }

                TextView guests = (TextView) findViewById(R.id.guests1);
                if ((boolean)currentUserMap.get("guests")) {
                    guests.setText("I am OK with it");
                }
                else {
                    guests.setText("I am not OK with it");
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



        setContentView(R.layout.activity_profile);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goBack = new Intent(this, NavDrawerActivity.class);
                goBack.putExtra("username", (String)extras.get("username"));
                startActivity(goBack);
                finish();
                //NavUtils.navigateUpTo(this, goBack);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
