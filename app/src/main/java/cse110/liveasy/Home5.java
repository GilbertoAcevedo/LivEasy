package cse110.liveasy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import cse110.liveasy.R;
import de.hdodenhof.circleimageview.CircleImageView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.Map;


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

        final View view = inflater.inflate(R.layout.fragment_home5more, container, false);
        final String[] user = new String[((NavDrawerActivity)getActivity()).group.num_users];
        String[] allMembers = ((NavDrawerActivity)getActivity()).getMembers();
        user[0] = ((NavDrawerActivity)getActivity()).username;
        int j = 1;
        for (int i = 0; i < ((NavDrawerActivity)getActivity()).group.num_users; i++) {
            if (!allMembers[i].equals(((NavDrawerActivity)getActivity()).username)) {
                user[j] = allMembers[i];
                j++;
            }
        }

        final String[] URLs = new String[((NavDrawerActivity)getActivity()).group.num_users];
        String[] emails = new String[((NavDrawerActivity)getActivity()).group.num_users];
        String[] numbers = new String[((NavDrawerActivity)getActivity()).group.num_users];

        for (int i = 0; i < ((NavDrawerActivity)getActivity()).group.num_users; i++) {
            Map<String, Object> member = (Map<String, Object>)((NavDrawerActivity)getActivity()).group.members.get(user[i]);
            URLs[i] = (String) member.get("photo_url");
            numbers[i] = (String) member.get("phone_number");
            emails[i] = (String) member.get("email");
        }


        //Integer[] imageID = {R.drawable.woodie, R.drawable.woodie, R.drawable.woodie, R.drawable.woodie,R.drawable.woodie};

        //CustomList adapter = new CustomList(getActivity(), user, imageID);
        CustomList adapter = new CustomList(getActivity(), user, URLs, numbers, emails);

        list=(ListView) view.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {

                Map<String, Object> currentMember = (Map <String, Object>)((NavDrawerActivity)getActivity()).group.members.get(user[position]);
                final Profile newProfile = new Profile(currentMember, user[position]);

                /*CircleImageView memberSelfie = (CircleImageView) v.findViewById(R.id.img);

                Picasso.with((NavDrawerActivity)getContext())
                        .load(URLs[position])
                        .rotate(90)
                        .resize(200,200)
                        .centerCrop()
                        .placeholder(R.drawable.blank)
                        .into(memberSelfie);

                memberSelfie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {*/

                        ((NavDrawerActivity) getActivity()).toProfilePopup(v, newProfile, user[position]);
                    //}
                //});

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}