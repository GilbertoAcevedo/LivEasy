package cse110.liveasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    String username = "";
    final User user = new User();
    final Group group = new Group();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displayScreen();
        System.out.println("In Oncreate user has group "+user.group);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            //do nothing
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent goToCreateGroup = new Intent(this, CreateGroup.class);

            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                String value = extras.getString("username");
                goToCreateGroup.putExtra("username", value);
            }

            startActivity(goToCreateGroup);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.manage_requests){
            Intent goToRequests = new Intent(this, ManageRequests.class);
            goToRequests.putStringArrayListExtra("pending", group.pending);
            goToRequests.putExtra("username", getIntent().getExtras().getString("username"));
            startActivity(goToRequests);
        }
        else if (id == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            Intent goToLogin = new Intent(this, LoginActivity.class);
            startActivity(goToLogin);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayScreen(){

        DatabaseReference uRef = ref.child("users").child(username);


        ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Boolean user_has_group = (Boolean) dataSnapshot.child("group").getValue();
                String user_group_id = (String) dataSnapshot.child("groupID").getValue();
                String user_email = (String) dataSnapshot.child("email").getValue();
                String user_phone_number = (String) dataSnapshot.child("phone_number").getValue();
                String user_full_name = (String) dataSnapshot.child("full_name").getValue();
                System.out.println("From database user has group "+user_has_group.booleanValue());
                user.groupID = user_group_id;
                user.group = user_has_group.booleanValue();
                user.email = user_email;
                user.phone_number = user_phone_number;
                user.full_name= user_full_name;

                updateGroup();

                System.err.print("\n User contents " + user_has_group);

                if (user_has_group) {
                    //This is the second listener for getting the number of members in the group
                    DatabaseReference gRef = ref.child("groups").child(user_group_id);
                    ValueEventListener listener2 = new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Fragment fragment = null;
                            Long num_users = (Long) dataSnapshot.child("num_users").getValue();
                            System.out.println("Number of Users: " + num_users);
                            switch(num_users.intValue()) {
                                case 1:
                                    fragment = new Home1();
                                    break;
                            }

                            if(fragment != null){
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, fragment);
                                ft.commit();
                            }

                            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    };
                    gRef.addListenerForSingleValueEvent(listener2);
                    gRef.removeEventListener(listener2);


                } else{
                    Fragment fragment = new Home1();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        uRef.addListenerForSingleValueEvent(listener);
        uRef.removeEventListener(listener);



    }

    public void goToCreateGroup(View view){
        Intent goToCreateGroup = new Intent(this, CreateGroup.class);

        Bundle extras = this.getIntent().getExtras();

        if (extras != null) {
            String value = extras.getString("username");
            goToCreateGroup.putExtra("username", value);
        }

        startActivity(goToCreateGroup);
    }

    public void updateGroup(){
        System.out.println("USER HAS GROUP "+user.group);
        if(user.group) {
            DatabaseReference gRef = ref.child("groups").child(user.groupID);
            ValueEventListener listener = new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> group_content =  (Map<String, Object>)dataSnapshot.child("members").getValue();
                    ArrayList<String> pending =  (ArrayList<String>) dataSnapshot.child("pending").getValue();

                    // Update each user's info
                    group.members = group_content;
                    group.pending = pending;

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            gRef.addValueEventListener(listener);

        }
    }

}
