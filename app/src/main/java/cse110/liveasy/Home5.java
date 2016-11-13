package cse110.liveasy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cse110.liveasy.R;

import com.google.firebase.database.DatabaseReference;


public class Home5 extends Fragment {

    ListView list;
    // String[] user = {"Duke", "Gil", "Sergio", "Kelvin", "Harambae", "Zoboomafo"};
    //Integer[] imageID = {R.drawable.woodie, R.drawable.woodie, R.drawable.woodie, R.drawable.woodie, R.drawable.woodie,R.drawable.woodie};

    public Home5() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home5more, container, false);
        String[] user = new String[((NavDrawerActivity)getActivity()).group.num_users];
        String[] allMembers = ((NavDrawerActivity)getActivity()).getMembers();
        user[0] = ((NavDrawerActivity)getActivity()).username;
        int j = 1;
        for (int i = 0; i < ((NavDrawerActivity)getActivity()).group.num_users; i++) {
            if (!allMembers[i].equals(((NavDrawerActivity)getActivity()).username)) {
                user[j] = allMembers[i];
                j++;
            }
        }



        Integer[] imageID = {R.drawable.woodie, R.drawable.woodie, R.drawable.woodie, R.drawable.woodie,R.drawable.woodie};

        CustomList adapter = new CustomList(getActivity(), user, imageID);
        list=(ListView) view.findViewById(R.id.list);
        list.setAdapter(adapter);


        // Inflate the layout for this fragment
        return view;
    }

}