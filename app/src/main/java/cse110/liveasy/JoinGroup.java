package cse110.liveasy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

public class JoinGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        }

    public void joinGroup(View view1) {

        EditText editText = (EditText) findViewById(R.id.editText6);
        final String groupNumber = editText.getText().toString();
        //compare groupNumber to numbers in database
        //if it exists, link to this group page, go to that group number
        //if it doesnt exist

        Intent goToHomeScreen = new Intent(this, HomeScreen.class);
        startActivity(goToHomeScreen);

    }

    public void cancelJoinGroup(View view){
        Intent goBacktoMain = new Intent(this, MainActivity.class);
        startActivity(goBacktoMain);
    }
    }


