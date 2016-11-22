package cse110.liveasy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class GroupProfileActivity extends AppCompatActivity {

    Bundle extras;
    String changedGroupAddress;
    String originalGroupAddress;
    boolean changeFlag;
    Map<String, Object> groupMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_profile);

        extras = getIntent().getExtras();
        String groupName = (String) extras.get("groupName");
        String groupID = (String) extras.get("groupID");

        changeFlag = false;

        extras = getIntent().getExtras();

        getSupportActionBar().setTitle((String)extras.get("groupName"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final FirebaseDatabase ref = FirebaseDatabase.getInstance();
        final DatabaseReference gRef = ref.getReference().child("groups").child(groupID);

        ValueEventListener groupListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Map<String, Object> currentgroupMap = (HashMap<String, Object>) dataSnapshot.getValue();
                groupMap = currentgroupMap;
                changeFlag = false;

                TextView address = (TextView) findViewById(R.id.groupAddress);
                //address.setText((String) currentgroupMap.get("address"));
                originalGroupAddress = address.getText().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        gRef.addListenerForSingleValueEvent(groupListener);
        gRef.removeEventListener(groupListener);


        final TextView groupAddress = (TextView) findViewById(R.id.groupAddress);
        final EditText editGroupAddress  = (EditText) findViewById(R.id.edit_groupAddress);

        groupAddress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                groupAddress.setVisibility(View.GONE);
                editGroupAddress.setVisibility(View.VISIBLE);
                editGroupAddress.setText("");


                //onbackpressed, go to prev number that existed

                editGroupAddress.setOnKeyListener(new View.OnKeyListener() {

                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        changedGroupAddress = editGroupAddress.getText().toString();


                        // If the event is a key-down event on the "enter" button
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {

                            if(changedGroupAddress.equals("")){
                                groupAddress.setText(originalGroupAddress);
                                groupAddress.setVisibility(View.VISIBLE);
                                editGroupAddress.setVisibility(View.GONE);
                            }
                            else{
                                groupAddress.setText(changedGroupAddress);
                                groupAddress.setVisibility(View.VISIBLE);
                                editGroupAddress.setVisibility(View.GONE);
                                changeFlag = true;
                                originalGroupAddress = changedGroupAddress;
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
                Intent goBack = new Intent(this, NavDrawerActivity.class);
                goBack.putExtra("username", (String)extras.get("username"));
                startActivity(goBack);
                finish();
                //NavUtils.navigateUpTo(this, goBack);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed(){
        if(changeFlag == true ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like to save changes made?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            uploadData();
                            changeFlag = false;
                            Intent goBack = new Intent(GroupProfileActivity.this, NavDrawerActivity.class);
                            goBack.putExtra("username", (String) extras.get("username"));
                            startActivity(goBack);
                            finish();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent goBack = new Intent(GroupProfileActivity.this, NavDrawerActivity.class);
                            goBack.putExtra("username", (String) extras.get("username"));
                            startActivity(goBack);
                            finish();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        }
        else
        {
            Intent goBack = new Intent(this, NavDrawerActivity.class);
            goBack.putExtra("username", (String) extras.get("username"));
            startActivity(goBack);
            finish();
        }
    }

    public void uploadData()
    {
        //uploading...
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference allref = database.getReference().child("groups").child(extras.getString("groupID"));

        groupMap.put("address", originalGroupAddress);


        allref.updateChildren(groupMap);



    }
}
