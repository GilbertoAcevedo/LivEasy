package cse110.liveasy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateGroup extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

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

    private String generateKey() {

        DatabaseReference groupsRef = ref.child("groups");

        return groupsRef.push().getKey();
    }


    public void createGroup(View view1) {

        EditText editText = (EditText) findViewById(R.id.editText5);
        final String groupName = editText.getText().toString();

        Bundle extras = getIntent().getExtras();
        final String username = extras.getString("username");
        DatabaseReference uRef = ref.child("users").child(username).child("group");
        final View view = view1;

        ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Boolean user_has_group = (Boolean) dataSnapshot.getValue();
                System.err.print("\n User contents " + user_has_group);

                if (user_has_group) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
                    builder.setTitle("\"" + groupName + "\"" + " cannot be created.");
                    builder.setMessage("You have already created a group. Cannot create more than one.");

                    builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent goBack = new Intent(CreateGroup.this, MainActivity.class);
                            goBack.putExtra("username", username);
                            startActivity(goBack);                }
                    });
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Intent goBack = new Intent(CreateGroup.this, NavDrawerActivity.class);
                            goBack.putExtra("username", username);
                            startActivity(goBack);
                        }
                    });

                    builder.create().show();

                } else {
                    if (!groupName.matches("") && !groupName.matches("Group Name")) {
                        String groupKey = generateKey();
                        //CHECK TO SEE THAT KEY DOES NOT EXIST
                        DatabaseReference groupsRef = ref.child("groups");
                        Map<String, Object> group_info = new HashMap<String, Object>();
                        Map<String, Object> members = new HashMap<String, Object>();

                        members.put("user1", new String(username));
                        group_info.put("/" + groupKey + "/", new Group(groupName, members, 1));
                        groupsRef.updateChildren(group_info);

                        // Set user's group boolean to true
                        DatabaseReference usersRef = ref.child("users").child(username);
                        Map<String, Object> group_bool = new HashMap<String, Object>();
                        group_bool.put("/group/", new Boolean(true));
                        usersRef.updateChildren(group_bool);


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
                                startActivity(goBack);
                            }
                        });

                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Intent goBack = new Intent(CreateGroup.this, MainActivity.class);
                                goBack.putExtra("username", username);
                                startActivity(goBack);
                            }
                        });

                        builder.create().show();
                    } else {
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        uRef.addListenerForSingleValueEvent(listener);
        uRef.removeEventListener(listener);

    }




//        if(!groupName.matches("") && !groupName.matches("Group Name")) {
//            String groupKey = generateKey();
//            //CHECK TO SEE THAT KEY DOES NOT EXIST
//            DatabaseReference groupsRef = ref.child("groups");
//            Map<String, Object> group_info = new HashMap<String, Object>();
//            Map<String, Object> members = new HashMap<String, Object>();
//
//            members.put("user1", new String(username));
//            group_info.put("/"+groupKey+"/", new Group(groupName, members, 1));
//            groupsRef.updateChildren(group_info);
//
//            // Set user's group boolean to true
//            DatabaseReference usersRef = ref.child("users").child(username);
//            Map<String, Object> group_bool = new HashMap<String, Object>();
//            group_bool.put("/group/", new Boolean(true));
//            usersRef.updateChildren(group_bool);
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
//            builder.setTitle("\"" + groupName + "\"" + " successfully created.");
//            builder.setMessage("Your group's key is: " + groupKey +
//                    "\nPlease pass on this key to your roommates so that they can join.");
//            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    Intent goBack = new Intent(CreateGroup.this, MainPage2.class);
//                    goBack.putExtra("username", username);
//
//                    startActivity(goBack);                }
//            });
//            builder.create().show();
//        }
//        else{
//            AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
//            builder.setMessage("Please type in a group name");
//            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    // Do nothing
//                }
//            });
//            builder.create().show();
//        }
//    }

    public void cancelCreateGroup(View view){
        Intent goBack = new Intent(this, MainActivity.class);
        startActivity(goBack);
    }
}
