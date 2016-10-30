package cse110.liveasy;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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

        ArrayList<String> temp = extras.getStringArrayList("pending");
        System.out.println("list..: " + extras.getStringArrayList("pending").get(0));
        listItems = extras.getStringArrayList("pending");

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);

        ListView pendingList = (ListView) findViewById(R.id.pending_list);


        if(adapter == null){
            System.out.println("pending list is null");
        }
        else{
            System.out.println("notnul");
            pendingList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

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
