package cse110.liveasy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

            Context context = view.getContext();
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView title = new TextView(view.getContext());
            title.setText("\n\"" + groupName + "\"");
            title.setTextSize(30);
            title.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);


            TextView message1 = new TextView(view.getContext());
            message1.setText("created, this is your group's id:");
            message1.setTextSize(20);
            message1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);


            TextView groupKeyTextView = new TextView(view.getContext());
            groupKeyTextView.setText(groupKey);
            groupKeyTextView.setTextSize(80);
            groupKeyTextView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);


            TextView message2 = new TextView(view.getContext());
            message2.setText("Please share this key with your rommates so that they may join.");
            message2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

            layout.addView(title);
            layout.addView(message1);
            layout.addView(groupKeyTextView);
            layout.addView(message2);

            AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
            builder.setView(layout);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent goBack = new Intent(CreateGroup.this, NavDrawerActivity.class);
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
