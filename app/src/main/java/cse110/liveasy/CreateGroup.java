package cse110.liveasy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

public class CreateGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
    }

    public String generateRandomNumber() {
        Random randVal = new Random();
        int number = 100000 + randVal.nextInt(900000);
        String myString = String.valueOf(number);
        return myString;
    }


    public void createGroup(View view){
        EditText editText = (EditText) findViewById(R.id.editText5);
        String groupName = editText.getText().toString();
        if(!groupName.matches("") && !groupName.matches("Group Name")) {
            String groupKey = generateRandomNumber();
            //CHECK TO SEE THAT KEY DOES NOT EXIST

            AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
            builder.setTitle("\"" + groupName + "\"" + " successfully created.");
            builder.setMessage("Your group's key is: " + groupKey +
                    "\nPlease pass on this key to your roommates so that they can join.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent goBack = new Intent(CreateGroup.this, MainActivity.class);
                    startActivity(goBack);                }
            });
            builder.create().show();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
            builder.setMessage("Please type in a group name");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Do nothing
                }
            });
            builder.create().show();
        }
    }

    public void cancelCreateGroup(View view){
        Intent goBack = new Intent(this, MainActivity.class);
        startActivity(goBack);
    }
}
