package cse110.liveasy;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TaskActivity extends AppCompatActivity {

    public Bundle extras;
    public String groupID;
    public String user;
    public Map<String, Object> members;
    public ArrayList<String> userTasks = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        this.extras = getIntent().getExtras();
        this.groupID = extras.getString("group_id");
        this.members = (HashMap<String, Object>)extras.get("members");
        this.user = extras.getString("username");

        LinearLayout layout = (LinearLayout)findViewById(R.id.task_layout);

        TextView title = new TextView(this);
        title.setText("Tasks");
        title.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        title.setTextSize(18);
        title.setPadding(0,15,0,100);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);

        layout.addView(title);


        LinearLayout innerLayout;
        Map<String,Object> tempMembers = this.members;
        Iterator it = tempMembers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String user = (String)pair.getKey();
            innerLayout = createUserLayout(user);
            layout.addView(innerLayout);
            it.remove();
        }



    }

    public LinearLayout createUserLayout(String username){

        LinearLayout returnLayout = new LinearLayout(this);
        returnLayout.setOrientation(LinearLayout.VERTICAL);
        returnLayout.getShowDividers();

        LinearLayout userView = new LinearLayout(this);
        userView.setOrientation(LinearLayout.HORIZONTAL);

        TextView userTitle = new TextView(this);
        userTitle.setText(username);
        userTitle.setTextSize(30);
        userView.addView(userTitle);

        LinearLayout l = new LinearLayout(this);
        l.setPadding(40,100,40,10);
        final EditText input = new EditText(TaskActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setPadding(5,30,5,30);
        input.setLayoutParams(lp);
        l.addView(input);

        final String tempUsername = username;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Task for " + username);
        builder.setView(l);
        builder.setCancelable(false);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                addTask(tempUsername, input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){

                dialog.dismiss();
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog =  builder.create();
        Button addButton = new Button(this);
        addButton.setText("Add");
        addButton.setTextSize(10);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.show();
            }
        });
        userView.addView(addButton);
        returnLayout.addView(userView);

        ArrayList<String> temp = userTasks;
        System.out.println("hello: " + temp.toString());
        for(int i = 1; i < temp.size(); ++i){
            String task = temp.get(i);
            System.out.println("hello");
            LinearLayout taskLayout;
            if(username.equals(user)) {
                taskLayout = new LinearLayout(this);
                taskLayout.setOrientation(LinearLayout.HORIZONTAL);
                taskLayout.setPadding(10, 0, 0, 0);

                TextView taskText = new TextView(this);
                taskText.setText(task);
                taskText.setTextSize(15);
                taskText.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);

                LinearLayout.LayoutParams taskTextParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f);
                taskText.setLayoutParams(taskTextParam);

                taskLayout.addView(taskText);

                LinearLayout.LayoutParams buttonParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                Button accept = new Button(this);
                accept.setText("Done");
                accept.setLayoutParams(buttonParam);
                final String userNameForListener = username;
                final String taskForListener = task;
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        completeTask(userNameForListener, taskForListener);
                    }
                });
                taskLayout.addView(accept);
            }
            else{
                taskLayout = new LinearLayout(this);
                taskLayout.setOrientation(LinearLayout.HORIZONTAL);
                taskLayout.setPadding(10, 0, 0, 0);

                TextView taskText = new TextView(this);
                taskText.setText(task);
                taskText.setTextSize(15);
                taskText.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);

                LinearLayout.LayoutParams taskTextParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                taskText.setLayoutParams(taskTextParam);

                taskLayout.addView(taskText);
            }
            returnLayout.addView(taskLayout);

        }


        return returnLayout;
    }


    public void addTask(String username, String task){
        setUserTasksArray(username);
        DatabaseReference gRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID).child("tasks");
        ArrayList<String> tempArrayList = userTasks;
        tempArrayList.add(task);
        Map<String, Object> map = new HashMap<>();
        map.put(username, tempArrayList);
        gRef.updateChildren(map);
    }

    public void completeTask(String username, String task){
        setUserTasksArray(username);
        DatabaseReference gRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID).child("tasks").child(username);
        ArrayList<String> tempArrayList = userTasks;
        tempArrayList.remove(task);
        Map<String, Object> map = new HashMap<>();
        map.put(username, tempArrayList);
        gRef.updateChildren(map);
    }

    public void setUserTasksArray(String username){
        final DatabaseReference gRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID);
        final String tempUsername = username;
        final HashMap<String,Object> tempMembers = (HashMap)members;

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> tempArrayList = (ArrayList<String>)dataSnapshot.child("tasks").child(tempUsername).getValue();
                System.out.println("array list:" + tempArrayList.toString());
                userTasks = tempArrayList;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        gRef.addListenerForSingleValueEvent(listener);
        gRef.removeEventListener(listener);

        System.out.println("this gets called after");
    }

    public void addTasksFieldToDatabase(){
        DatabaseReference gRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID);
        Map<String, Object> tasks = new HashMap<String,Object>();
        ArrayList<String> temp = new ArrayList<>();
        temp.add("");
        tasks.put(user, temp);

        Map<String,Object> finalMap = new HashMap<>();
        finalMap.put("tasks", tasks);
        gRef.updateChildren(finalMap);
    }

    public void addUserToTasksField(String username){
        DatabaseReference gRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID).child("tasks");
        Map<String, Object> userTasks = new HashMap<>();
        userTasks.put(username, new ArrayList<String>());
        gRef.updateChildren(userTasks);
    }
}
