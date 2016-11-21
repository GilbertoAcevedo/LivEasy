package cse110.liveasy;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomList extends ArrayAdapter<String>{

    private final String[] URLs;
    private final String[] numbers;
    private final String[] emails;
    private final Activity context;
    private final String[] usr;
    //private final Integer[] imageId;
    /*public CustomList(Activity context, String[] usr, Integer[] imageId) {
        super(context, R.layout.fragment_home5more, usr);
        this.context = context;
        this.usr = usr;
        this.imageId = imageId;

    }*/

    public CustomList(Activity context, String[] usr, String[] URLs, String[] numbers, String[] emails) {
        super(context, R.layout.fragment_home5more, usr);
        this.context = context;
        this.usr = usr;
        this.URLs = URLs;
        this.emails = emails;
        this.numbers = numbers;

    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_content, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.usr);
        // final Profile newProfile = new Profile()

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        txtTitle.setText(usr[position]);

        //imageView.setImageResource(imageId[position]);

        Picasso.with(context)
                .load(URLs[position])
                .resize(200,200)
                .centerCrop()
                .placeholder(R.drawable.blank)
                .into(imageView);


        return rowView;
    }
}