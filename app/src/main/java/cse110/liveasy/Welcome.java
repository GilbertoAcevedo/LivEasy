package cse110.liveasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Welcome extends AppCompatActivity {

    /*
     * Sets up layout for welcome activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }

    /*
     * Sets up intent to go to Sign in
     */
    public void goToSignIn(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /*
     * Sets up intent to go to Sign up
     */
    public void goToSignUp( View view ) {

        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);


    }

}
