package cse110.liveasy;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

public class MainPage2 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home1);

    }

    public void toProfilePopup(View view) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(MainPage2.this);
        LayoutInflater inflater = (MainPage2.this).getLayoutInflater();
        View dialog_view = inflater.inflate(R.layout.activity_popup_profile, null);

        builder.setPositiveButton(R.string.go_back, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.setNegativeButton(R.string.go_profile, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent goProfile = new Intent(MainPage2.this, ProfileActivity.class);
                startActivity(goProfile);
            }
        });

        builder.setView(dialog_view);
        builder.create().show();

    }

    public void toGroupProfilePopup(View view) {

        final AlertDialog.Builder group_builder = new AlertDialog.Builder(MainPage2.this);
        LayoutInflater group_inflater = (MainPage2.this).getLayoutInflater();
        View group_dialog_view = group_inflater.inflate(R.layout.activity_popup_group_profile, null);

        group_builder.setPositiveButton(R.string.go_back, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        group_builder.setNegativeButton(R.string.go_profile, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent goProfile = new Intent(MainPage2.this, GroupProfileActivity.class);
                startActivity(goProfile);
                finish();
            }
        });

        group_builder.setView(group_dialog_view);
        group_builder.create().show();


    }


}
