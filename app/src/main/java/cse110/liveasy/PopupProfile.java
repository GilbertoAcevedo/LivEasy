package cse110.liveasy;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Duke Lin on 10/30/2016.
 */

public class PopupProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_profile);
    }


    public void goToMainProfile(View v) {
        Intent toMainProfile = new Intent(this, ProfileActivity.class);
        startActivity(toMainProfile);
    }

}
