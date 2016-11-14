package cse110.liveasy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.support.annotation.RequiresApi;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    String username = "";
    final User user = new User();
    final Group group = new Group();
    boolean currentPending = false;
    int pendingSize;
    int memberCount;
    int count = 0;

    ValueEventListener groupListener;
    ValueEventListener userListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nav_drawer);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");

        System.out.println("Username in NAV Drawer .... "+username);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /***************************/

        DatabaseReference uRef = ref.child("users").child(username);

        ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Boolean user_has_group = (Boolean) dataSnapshot.child("group").getValue();
                String user_group_id = (String) dataSnapshot.child("groupID").getValue();
                String user_email = (String) dataSnapshot.child("email").getValue();
                String user_phone_number = (String) dataSnapshot.child("phone_number").getValue();
                String user_full_name = (String) dataSnapshot.child("full_name").getValue();
                Boolean user_isPending = (Boolean) dataSnapshot.child("isPending").getValue();
                System.out.println("From database user has group "+user_has_group.booleanValue());
                user.groupID = user_group_id;
                user.email = user_email;
                user.phone_number = user_phone_number;
                user.full_name= user_full_name;
                user.isPending = user_isPending;
                currentPending = user_isPending; // listen for change
                user.group = user_has_group.booleanValue();

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View hView =  navigationView.getHeaderView(0);
                TextView nav_email = (TextView)hView.findViewById(R.id.textView);
                nav_email.setText(user.email);
                TextView nav_user = (TextView)hView.findViewById(R.id.textView3);
                nav_user.setText(user.full_name);
                //get image
                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                StorageReference profileRef = mStorageRef.child(username);

                final long ONE_MEGABYTE = 4 * 1024 * 1024;
                profileRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Data for "images/island.jpg" is returns, use this as needed
                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        View hView2 =  navigationView.getHeaderView(0);
                        ImageView nav_pic = (ImageView)hView2.findViewById(R.id.imageView);
                        Matrix mtx = new Matrix();
                        mtx.postRotate(90);
                        // Rotating Bitmap
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mtx, true);

                        nav_pic.setImageBitmap(rotatedBMP);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
                //nav_pic.setImageResource(R.drawable.woodie);

                //updateGroup();
                updateUser();

                if (user_has_group) {

                    //This is the second listener for getting the number of members in the group
                    DatabaseReference gRef = ref.child("groups").child(user_group_id);
                    ValueEventListener listener2 = new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if( user.group ) {
                                Map<String, Object> group_content = (Map<String, Object>) dataSnapshot.child("members").getValue();
                                ArrayList<String> pending = (ArrayList<String>) dataSnapshot.child("pending").getValue();
                                String gname = (String) dataSnapshot.child("name").getValue();
                                Long gnum = (Long) dataSnapshot.child("num_users").getValue();

                                // Update each user's info
                                group.members = group_content;
                                group.pending = pending;
                                group.name = gname;

                                if( gnum != null ) {
                                    group.num_users = gnum.intValue();
                                }

                                if (user.group) {
                                    getSupportActionBar().setTitle(group.name);
                                    notificationUp();
                                }
                            }

                            Fragment fragment = null;
                            Long num_users = (Long) dataSnapshot.child("num_users").getValue();
                            System.out.println("Number of Users: " + num_users);
                            memberCount = num_users.intValue();
                            switch(num_users.intValue()) {
                                case 1:
                                    fragment = new Home1();
                                    break;
                                case 2:
                                    fragment = new Home2();
                                    break;
                                case 3:
                                    fragment = new Home3();
                                    break;
                                case 4:
                                    fragment = new Home4();
                                    break;
                                case 5:
                                    fragment = new Home5();
                                    break;
                                default:
                                    fragment = new Home5();
                                    break;
                            }

                            if(fragment != null){
                                //if(!isFinishing()) {
                                System.out.println("infinite loop");
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, fragment);
                                ft.commitAllowingStateLoss();
                                //}
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
                    ft.commitAllowingStateLoss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        uRef.addListenerForSingleValueEvent(listener);
        uRef.removeEventListener(listener);


        /***************************/

        getSupportActionBar().setTitle(username);

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
            removeAllListeners();
            startActivity(goToCreateGroup);
            finish();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_send) {
            Intent goToShareCode = new Intent(this, ShareGroupCode.class);
            goToShareCode.putExtra("username", username);
            goToShareCode.putExtra("group_id", user.groupID);
            removeAllListeners();
            startActivity(goToShareCode);
            finish();


        } else if (id == R.id.manage_requests){
            Intent goToRequests = new Intent(this, ManageRequests.class);
            goToRequests.putStringArrayListExtra("pending", group.pending);
            goToRequests.putExtra("username", getIntent().getExtras().getString("username"));
            goToRequests.putExtra("groupKey", user.groupID);
            removeAllListeners();
            startActivity(goToRequests);
            finish();
        }
        else if ( id == R.id.leave_group ){
            View v = findViewById(R.id.content_nav_drawer);
            AlertDialog.Builder displayConfirmation  = new AlertDialog.Builder(v.getContext());
            displayConfirmation.setMessage("Are you sure you want to leave this group?" +
                    "\nIf you are the last member, the group will be deleted");
            displayConfirmation.setTitle("Leave group");
            displayConfirmation.setPositiveButton("Yes", null);
            displayConfirmation.setNegativeButton("No", null);
            displayConfirmation.setCancelable(false);

            displayConfirmation.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if( user.group ) {
                                removeUserFromGroup(username);
                                Toast toast = Toast.makeText(NavDrawerActivity.this, "You have been removed" +
                                                " from the group.",
                                        Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.NO_GRAVITY, 0, 0);
                                toast.show();
                            }
                            else {
                                Toast toast = Toast.makeText(NavDrawerActivity.this, "You do not have a group.",
                                        Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.NO_GRAVITY, 0, 0);
                                toast.show();
                            }
                        }
                    });
            displayConfirmation.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });
            displayConfirmation.create().show();
        }
        else if (id == R.id.logout){

            SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();

            FirebaseAuth.getInstance().signOut();

            Intent goToLogin = new Intent(this, LoginActivity.class);
            removeAllListeners();
            startActivity(goToLogin);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void goToCreateGroup(View view){
        Intent goToCreateGroup = new Intent(this, CreateGroup.class);

        Bundle extras = this.getIntent().getExtras();

        if (extras != null) {
            String value = extras.getString("username");
            goToCreateGroup.putExtra("username", value);
        }

        removeAllListeners();

        startActivity(goToCreateGroup);
        finish();
    }

    public void goToJoinGroup(View view){
        System.out.println("****************************");
        Bundle extras = this.getIntent().getExtras();
        Intent goToJoinGroup = new Intent(this, JoinGroup.class);
        goToJoinGroup.putExtra("username", (String)extras.getString("username"));
        removeAllListeners();
        startActivity(goToJoinGroup);
        finish();

    }

    public void updateGroup(){
        System.out.println("USER HAS GROUP "+user.group);

        if(user.group) {
            DatabaseReference gRef = ref.child("groups").child(user.groupID);
            groupListener = new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if( user.group ) {
                        Map<String, Object> group_content = (Map<String, Object>) dataSnapshot.child("members").getValue();
                        ArrayList<String> pending = (ArrayList<String>) dataSnapshot.child("pending").getValue();
                        String gname = (String) dataSnapshot.child("name").getValue();
                        Long gnum = (Long) dataSnapshot.child("num_users").getValue();

                        // Update each user's info
                        group.members = group_content;
                        group.pending = pending;
                        group.name = gname;

                        if( gnum != null ) {
                            if ( group.num_users != gnum.intValue() ) {

                                System.out.println("Changing intents when gnum = "+gnum.intValue()+" group.num_users = "+group.num_users);
                                restartActivity();
                            }
                        }

                        if (user.group) {
                            getSupportActionBar().setTitle(group.name);
                            notificationUp();
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            gRef.addValueEventListener(groupListener);

        }
    }


    /***********************************************************/
//    public void clickGoToProfile(View view) {
//        Bundle extras = getIntent().getExtras();
//        Intent goToProfile = new Intent(this, ProfileActivity.class);
//        goToProfile.putExtra("username", (String)extras.get("username"));
//        startActivity(goToProfile);
//    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void toProfilePopup(View view, Profile memberContent, final String memberName) {
        //get info for which photo was clicked on

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();

        View dialog_view = inflater.inflate(R.layout.activity_popup_profile, null);
        TextView users_name = (TextView)dialog_view.findViewById(R.id.username);
        users_name.setText(memberName);
//TODO IMAGES
        /*CircleImageView selfie = (CircleImageView)dialog_view.findViewById(R.id.profile_image);
        selfie.setImageResource();*/

        //View dialog_view2 = inflater.inflate(R.layout.activity_content_popup_profile, null);

        TextView email = (TextView)dialog_view.findViewById(R.id.email);
        email.setText(memberContent.email);

        TextView phoneNum = (TextView)dialog_view.findViewById(R.id.phone_number);
        String formattedNumber = PhoneNumberUtils.formatNumber(memberContent.phoneNum, "US");
        phoneNum.setText(formattedNumber);


        builder.setPositiveButton(R.string.go_back, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.setNegativeButton(R.string.go_profile, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent goProfile = new Intent(NavDrawerActivity.this, ProfileActivity.class);
                goProfile.putExtra("username", username);
                goProfile.putExtra("memberName", memberName);
                removeAllListeners();
                startActivity(goProfile);
                finish();
            }
        });

        //builder.setView(dialog_view2);
        builder.setView(dialog_view);

        builder.create().show();

    }

    public void toGroupProfilePopup(View view) {
        final AlertDialog.Builder group_builder = new AlertDialog.Builder(this);
        LayoutInflater group_inflater = (this).getLayoutInflater();
        View group_dialog_view = group_inflater.inflate(R.layout.activity_popup_group_profile, null);

        TextView groups_name = (TextView) group_dialog_view.findViewById(R.id.textView2);
        groups_name.setText(group.name);

        group_builder.setPositiveButton(R.string.go_back, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        group_builder.setNegativeButton(R.string.go_profile, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent goProfile = new Intent(NavDrawerActivity.this, GroupProfileActivity.class);
                goProfile.putExtra("username", username);
                goProfile.putExtra("groupName", group.name);
                removeAllListeners();
                startActivity(goProfile);
                finish();
            }
        });

        group_builder.setView(group_dialog_view);
        group_builder.create().show();
    }


    public void notificationUp() {
        System.out.println("in notifications up has group: " + user.groupID);
        if (user.group) {
            DatabaseReference pRef = ref.child("groups").child(user.groupID).child("pending");

            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if( user.group ) {
                        System.out.println(username + " " + (++count));
                        ArrayList<String> list = (ArrayList<String>) dataSnapshot.getValue();
                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        Menu menu = navigationView.getMenu();
                        MenuItem requestItem = menu.findItem(R.id.manage_requests);
                        if (list.size() > 1) {
                            requestItem.setTitle("Manage Requests (" + (list.size() - 1) + ")");

                        } else {
                            requestItem.setTitle("Manage Requests");
                        }
                        if (list.size() != pendingSize) {

                            if (list.size() > 1) {
                                System.out.println("before list size: " + list.size() + "\nbefore pendingSize: " + pendingSize);
                                Toast toast = Toast.makeText(NavDrawerActivity.this, "You have " + (list.size() - 1) + " pending request(s)",
                                        Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.NO_GRAVITY, 0, 0);
                                toast.show();
                            }
                            pendingSize = list.size();
                            System.out.println("after list size: " + list.size() + "\nafter pendingSize: " + pendingSize);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            pRef.addListenerForSingleValueEvent(listener);
            pRef.removeEventListener(listener);
        }
    }



    public void removeUserFromGroup(final String userName){


        final DatabaseReference gRef = ref.child("groups").child(user.groupID);
        gRef.removeEventListener(groupListener);
        user.group = false;
        user.groupID = "";

        groupListener = null;

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DatabaseReference uRef = ref.child("users").child(username);
                Map<String,Object> userContents = new HashMap<String,Object>();
                userContents.put("group", new Boolean(false));
                userContents.put("groupID", "");
                uRef.updateChildren(userContents);


                Map<String, Object> group = (HashMap<String,Object>)dataSnapshot.getValue();

                Map<String,Object> members = (HashMap<String,Object>)dataSnapshot.child("members").getValue();
                members.remove(userName);

                int currentMembers = ((Long)dataSnapshot.child("num_users").getValue()).intValue();
                if(currentMembers > 1) {
                    group.put("members", members);
                    group.put("num_users", currentMembers - 1);
                    gRef.updateChildren(group);
                }else{
                    gRef.removeValue();
                }

                restartActivity();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        gRef.addListenerForSingleValueEvent(listener);
        gRef.removeEventListener(listener);

    }

    public void restartActivity() {
        Intent restartActivity = new Intent(NavDrawerActivity.this, NavDrawerActivity.class);
        restartActivity.putExtra("username", username);
        startActivity(restartActivity);
        removeAllListeners();
        finish();

    }



    public void updateUser(){
        DatabaseReference uRef = ref.child("users").child(username);

         userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Boolean user_has_group = (Boolean) dataSnapshot.child("group").getValue();
                String user_group_id = (String) dataSnapshot.child("groupID").getValue();
                String user_email = (String) dataSnapshot.child("email").getValue();
                String user_phone_number = (String) dataSnapshot.child("phone_number").getValue();
                String user_full_name = (String) dataSnapshot.child("full_name").getValue();
                Boolean user_isPending = (Boolean) dataSnapshot.child("isPending").getValue();
                System.out.println("From database user has group "+ user_has_group.booleanValue());
                user.groupID = user_group_id;
                user.group = user_has_group.booleanValue();
                user.email = user_email;
                user.phone_number = user_phone_number;
                user.full_name= user_full_name;
                user.isPending = user_isPending;

                if( user.group && groupListener == null) {
                    updateGroup();
                }

                if( currentPending != user.isPending ) {
                    restartActivity();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        uRef.addValueEventListener(userListener);
    }

    public void removeAllListeners() {

        DatabaseReference uRef = ref.child("users").child(username);
        uRef.removeEventListener(userListener);
        if( user.group ) {
            DatabaseReference gRef = ref.child("groups").child(user.groupID);
            gRef.removeEventListener(groupListener);
        }

    }

    public String[] getMembers() {

        String[] mems = new String[group.num_users];
        int i = 0;
        for (Map.Entry<String, Object> entry : group.members.entrySet()) {

            mems[i] = entry.getKey();
            i++;
        }

        return mems;
    }
}
