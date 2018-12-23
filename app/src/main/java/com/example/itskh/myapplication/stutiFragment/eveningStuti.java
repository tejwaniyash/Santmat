package com.example.itskh.myapplication.stutiFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itskh.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class eveningStuti extends Fragment {


    public eveningStuti() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_evening_stuti, container, false);
        return view;
    }

}
