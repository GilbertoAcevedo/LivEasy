package cse110.liveasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    /*
     * sets the layout for the main activity
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     * goes to profile when profile pick clicked (button)
     */
    public void clickGoToProfile(View v) {
        //Toast.makeText(this, "Show some text on the screen.", Toast.LENGTH_LONG).show();
        Intent goToProfile = new Intent(this, ProfileActivity.class);
        startActivity(goToProfile);
        finish();
    }

    /*
     * goes to creategroup page if this button is pressed
     */
    public void goToCreateGroup(View view){
        Intent goToCreateGroup = new Intent(this, CreateGroup.class);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String value = extras.getString("username");
            goToCreateGroup.putExtra("username", value);
        }

        startActivity(goToCreateGroup);
        finish();
    }


    /*
     * goes to joingroup activity if this is pressed
     */
    public void goToJoinGroup(View view){

        Intent goToJoinGroup = new Intent(this, JoinGroup.class);

        startActivity(goToJoinGroup);
        finish();
    }
}
