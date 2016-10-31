package cse110.liveasy;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ManageRequests extends AppCompatActivity {
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_requests);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        extras = getIntent().getExtras();
        for (String key : extras.keySet())
        {
            System.out.println(key + " = \"" + extras.get(key) + "\"");
        }

        listItems = extras.getStringArrayList("pending");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(this);
        title.setText("Pending Requests");
        title.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        title.setTextSize(18);
        title.setPadding(0,15,0,100);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);

        layout.addView(title);

        for( int index = 1; index < listItems.size(); index++ ) {

            LinearLayout inner_layout = new LinearLayout(this);

            TextView user = new TextView(this);
            user.setText(listItems.get(index));
            user.setTextSize(15);

            user.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);

            LinearLayout.LayoutParams button_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f);

            LinearLayout.LayoutParams user_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            user.setLayoutParams(user_param);

            Button accept = new Button(this);
            accept.setText("Accept");
            accept.setLayoutParams(button_param);

            Button reject =  new Button(this);
            reject.setText("Reject");
            reject.setLayoutParams(button_param);

            inner_layout.addView(user, LinearLayout.LayoutParams.WRAP_CONTENT);
            inner_layout.addView(accept);
            inner_layout.addView(reject);

            layout.addView(inner_layout);

        }



        setContentView(layout);


//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1,
//                listItems);
//
//        ListView pendingList = (ListView) findViewById(R.id.pending_list);
//
//
//        pendingList.setAdapter(adapter);
//        adapter.notifyDataSetChanged();


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goBack = new Intent(this, NavDrawerActivity.class);
                goBack.putExtra("username", (String)extras.get("username"));
                NavUtils.navigateUpTo(this, goBack);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
