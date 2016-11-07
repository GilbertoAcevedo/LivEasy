package cse110.liveasy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static cse110.liveasy.R.id.tvNumber1;
import static cse110.liveasy.R.id.tvNumber3;

public class ProfileActivity extends AppCompatActivity {

    Bundle extras;
    String originalPhoneNumber;
    String changedPhoneNumber;
    String originalEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        extras = getIntent().getExtras();

        getSupportActionBar().setTitle((String)extras.get("username"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final TextView phoneNumber = (TextView) findViewById(tvNumber1);
        final EditText editphoneNumber = (EditText) findViewById(R.id.phone_edit_text);

        final TextView email  = (TextView) findViewById(tvNumber3);
        final EditText editEmail = (EditText) findViewById(R.id.email_edit);

        originalPhoneNumber = phoneNumber.getText().toString();
        originalEmail = email.getText().toString();




        phoneNumber.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                phoneNumber.setVisibility(View.GONE);
                editphoneNumber.setVisibility(View.VISIBLE);
                editphoneNumber.setText("");


                //onbackpressed, go to prev number that existed

                editphoneNumber.setOnKeyListener(new View.OnKeyListener() {

                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        changedPhoneNumber = editphoneNumber.getText().toString();

                        // If the event is a key-down event on the "enter" button
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {

                            if ((changedPhoneNumber.isEmpty()) || (changedPhoneNumber.length()!=10)){

                                //check if digits


                                phoneNumber.setText(originalPhoneNumber);

                                phoneNumber.setVisibility(View.VISIBLE);
                                editphoneNumber.setVisibility(View.GONE);


                            }
                            else{
                                phoneNumber.setText(changedPhoneNumber);

                                phoneNumber.setVisibility(View.VISIBLE);
                                editphoneNumber.setVisibility(View.GONE);

                            }
                            return false;

                        }
                        return false;

                        }
            });
                return false;
            }
    });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goBack = new Intent(this, MainPage2.class);
                goBack.putExtra("username", (String)extras.get("username"));
                NavUtils.navigateUpTo(this, goBack);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //check top left back button as well
    @Override
    public void onBackPressed() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like to save changes made?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //go to nav drawer
                        }
                    });

        AlertDialog alert = builder.create();
        alert.show();

    }




}
