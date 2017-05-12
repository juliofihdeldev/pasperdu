package com.pasperdu.app.wapjwennli.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pasperdu.app.wapjwennli.R;

public class AddDoc extends Fragment {
    public AddDoc() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String searchKey = bundle.getString("searchKey");
            // Toast.makeText(getContext(), "" + searchKey, Toast.LENGTH_SHORT).show();
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_add_retrouve, container, false);
    }
}
