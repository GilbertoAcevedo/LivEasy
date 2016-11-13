package cse110.liveasy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Home1 extends Fragment {

    public Home1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home1, container, false);
        User userObject = ((NavDrawerActivity)getActivity()).user;
        final String mainUser = ((NavDrawerActivity)getActivity()).username;

        final Profile mainProfile = new Profile(userObject, mainUser);
        CircleImageView selfie = (CircleImageView) view.findViewById(R.id.main_profile_image1);
        selfie.setImageResource(R.drawable.woodie); //TODO

        selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((NavDrawerActivity) getActivity()).toProfilePopup(v, mainProfile, mainUser);
            }
        });

        if( ((NavDrawerActivity)getActivity()).user.group || ((NavDrawerActivity)getActivity()).user.isPending ) {
            Button createButton = (Button) view.findViewById(R.id.button_creategroup);
            createButton.setVisibility(View.GONE);
            Button joinButton = (Button) view.findViewById(R.id.JoinGroup);
            joinButton.setVisibility(View.GONE);

        }

        // Inflate the layout for this fragment
        return view;
    }



}
