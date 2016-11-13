package cse110.liveasy;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShareGroupCode extends AppCompatActivity {
    Bundle extras;

    /*
     * sets the layout for the SharGroupCode activity
     * displays group id  or doesnt find it
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_group_code);

        extras = getIntent().getExtras();
        String groupKey = extras.getString("group_id");

        TextView groupID = (TextView)findViewById(R.id.group_id);
        if(!groupKey.equals("")) {
            groupID.setText(extras.getString("group_id"));
            groupID.setTextSize(28);
        }
        else{
            groupID.setText("No group code to share.");
            groupID.setTextSize(28);
            LinearLayout buttonLayout = (LinearLayout)findViewById(R.id.button_layout);
            buttonLayout.setVisibility(LinearLayout.GONE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    /*
     * Goes back to previous page on back selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goBack = new Intent(this, NavDrawerActivity.class);
                goBack.putExtra("username", extras.getString("username"));
                startActivity(goBack);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Gave ability to copy code to clipboard
     * Used so that users can share with group members
     */
    public void copyToClipboard(View view){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied", extras.getString("group_id"));
        clipboard.setPrimaryClip(clip);

        Toast toast = Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 400);
        toast.show();
    }

    @Override
    public void onBackPressed(){

    }
}
