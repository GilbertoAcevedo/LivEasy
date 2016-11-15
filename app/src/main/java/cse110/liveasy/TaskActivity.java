package cse110.liveasy;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        System.out.println(members.toString());


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
            System.out.println(members.toString());

            Map.Entry pair = (Map.Entry)it.next();
            String user = (String)pair.getKey();
            innerLayout = createUserLayout(user);
            layout.addView(innerLayout);
            it.remove();
        }



    }

    public LinearLayout createUserLayout(String username){
        System.out.println(members.toString());

        setUserTasksArray(username);
        LinearLayout returnLayout = new LinearLayout(this);
        returnLayout.setOrientation(LinearLayout.VERTICAL);
        returnLayout.getShowDividers();

        TextView userTitle = new TextView(this);
        userTitle.setText(username);
        userTitle.setTextSize(30);
        returnLayout.addView(userTitle);

        for(String task: userTasks){
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
        DatabaseReference gRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID).child("tasks").child(username);
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
        System.out.println("thissss shoulllddh print");
        System.out.println(members.toString());
        final HashMap<String,Object> tempMembers = (HashMap)members;
        System.out.println(tempMembers.toString());

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(tempMembers.toString());

                if(dataSnapshot.child("tasks").getValue() == null){
                    addTasksFieldToDatabase();
                }
                if(dataSnapshot.child("tasks").child(tempUsername).getValue() == null){
                    System.out.println("wat");
                    addUserToTasksField(tempUsername);
                }
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
