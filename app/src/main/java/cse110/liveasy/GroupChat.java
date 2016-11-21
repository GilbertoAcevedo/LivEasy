package cse110.liveasy;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GroupChat extends AppCompatActivity {

    private Button sendMsgBtn;
    private EditText inputMsg;
    private TextView chatConversation;
    private ScrollView chatScrollView;

    private String username, groupID;
    private DatabaseReference chatRoomRef;
    private String tempKey;
    private String chatMsg, chatUsername;

    private ChildEventListener msgsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        // Keep keyboard from popping out automatically
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        sendMsgBtn = (Button) findViewById(R.id.send_msg_btn);
        inputMsg = (EditText) findViewById(R.id.msg_input);
        chatConversation = (TextView) findViewById(R.id.msg_textView);
        chatScrollView = (ScrollView) findViewById(R.id.chat_scroll);


        username = getIntent().getExtras().get("username").toString();
        groupID = getIntent().getExtras().get("group_id").toString();

        setTitle( " Chat Room ");

        chatScrollView.post(new Runnable() {

            @Override
            public void run() {
                chatScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });


        chatRoomRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID).child("chat_room");

        sendMsgBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                tempKey = chatRoomRef.push().getKey();

                DatabaseReference messagesRoot = chatRoomRef.child(tempKey);
                Map<String, Object> userMessageMap = new HashMap<String, Object>();
                userMessageMap.put("name", username);
                userMessageMap.put("msg", inputMsg.getText().toString());

                inputMsg.setText("");

                messagesRoot.updateChildren(userMessageMap);
            }
        });

        msgsListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                appendChatConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                appendChatConversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        chatRoomRef.addChildEventListener(msgsListener);

    }


    private void appendChatConversation(DataSnapshot dataSnapshot){
        Iterator iter = dataSnapshot.getChildren().iterator();

        while(iter.hasNext()){
            chatMsg = (String) ((DataSnapshot)iter.next()).getValue();
            chatUsername = (String) ((DataSnapshot)iter.next()).getValue();

            chatConversation.append(chatUsername + ": " + chatMsg + " \n");

        }
        chatScrollView.postDelayed(new Runnable() {

            @Override
            public void run() {
                chatScrollView.fullScroll(View.FOCUS_DOWN);
            }
        }, 100);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goBack = new Intent(this, NavDrawerActivity.class);
                goBack.putExtra("username", username);
                startActivity(goBack);
                chatRoomRef.removeEventListener(msgsListener);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent goBack = new Intent(this, NavDrawerActivity.class);
        goBack.putExtra("username", username);
        startActivity(goBack);
        finish();
    }


}

