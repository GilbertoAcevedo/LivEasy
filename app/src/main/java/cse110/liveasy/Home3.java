package cse110.liveasy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Home3 extends Fragment {

    public Home3() {
        // Required empty public constructor
    }

    /*
     * Sets view for if four people are in group
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home3, container, false);
    }

}
