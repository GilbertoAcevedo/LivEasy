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

public class MainPage2 extends ListActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page2);

        String[] activities = {"Duke added Gerardo", "Gerardo added Sergio"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_expandable_list_item_1, activities);
        getListView().setAdapter(adapter);
    }

    public void toProfilePopup(View view) {
        /*Intent intent = new Intent(this, MainPage3.class);
        startActivity(intent);*/

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainPage2.this);
        LayoutInflater inflater = (MainPage2.this).getLayoutInflater();
        View dialog_view = inflater.inflate(R.layout.activity_popup_profile, null);

        builder.setPositiveButton(R.string.go_back, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent goBack = new Intent(MainPage2.this, MainPage2.class);
                startActivity(goBack);
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
//        builder.setView(findViewById(R.id.activity_splash));
//        builder.create().show();

    }


}
