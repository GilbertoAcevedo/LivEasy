package cse110.liveasy;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] usr;
    private final Integer[] imageId;
    public CustomList(Activity context, String[] usr, Integer[] imageId) {
        super(context, R.layout.fragment_home5more, usr);
        this.context = context;
        this.usr = usr;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_content, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.usr);


        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(usr[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}