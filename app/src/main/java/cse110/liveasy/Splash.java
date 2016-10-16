package cse110.liveasy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    /** Duration of Splash **/
    private final int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent openWelcome = new Intent(Splash.this, LoginActivity.class);
                Splash.this.startActivity(openWelcome);
                Splash.this.finish();
            }
        }, SPLASH_DURATION);
    }
}
