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


public class Home1 extends Fragment {

    public Home1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home1, container, false);

        if( ((NavDrawerActivity)getActivity()).user.group ) {
            Button button = (Button) view.findViewById(R.id.button3);
            button.setVisibility(View.GONE);
        }

        // Inflate the layout for this fragment
        return view;
    }



}
