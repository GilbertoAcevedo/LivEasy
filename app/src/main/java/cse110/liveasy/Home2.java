package cse110.liveasy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Home2 extends Fragment {

    public Home2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View theView = inflater.inflate(R.layout.fragment_home2, container, false);
        String[] membersList = ((NavDrawerActivity)getActivity()).getMembers();


        final String[] roommateList = new String[(membersList.length) - 1];

        final String mainUser = ((NavDrawerActivity)getActivity()).username;
        Map<String, Object> group = ((NavDrawerActivity)getActivity()).group.members;

        int j = 0;
        for (int i = 0; i < membersList.length; i++) {
            if (!membersList[i].equals(mainUser)) {
                roommateList[j] = membersList[i];
                j++;
            }
        }

        final Profile mainProfile = new Profile((Map<String, Object>) group.get(mainUser), mainUser);
        CircleImageView selfie = (CircleImageView) theView.findViewById(R.id.main_profile_image);
        selfie.setImageResource(R.drawable.woodie); //TODO

        selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((NavDrawerActivity) getActivity()).toProfilePopup(v, mainProfile, mainUser);
            }
        });

        for (int index = 0; index < roommateList.length; index++) {

            Map<String, Object> currentMember = (Map <String, Object>)((NavDrawerActivity)getActivity()).group.members.get(roommateList[index]);
            final Profile newProfile = new Profile(currentMember, roommateList[index]);
            final String membersName = roommateList[index];

            switch(index) {
                case 0:
                    CircleImageView memberSelfie = (CircleImageView) theView.findViewById(R.id.member_image_2_1);
                    memberSelfie.setImageResource(R.drawable.woodie1);

                    memberSelfie.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ((NavDrawerActivity) getActivity()).toProfilePopup(v, newProfile, membersName);
                        }
                    });

            }


        }

        return theView;
    }

}
