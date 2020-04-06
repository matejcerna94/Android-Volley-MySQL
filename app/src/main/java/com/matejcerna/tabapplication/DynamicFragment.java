package com.matejcerna.tabapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DynamicFragment extends Fragment {
    View view;

    public static DynamicFragment newInstance(String category_name) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putString("category_name", category_name);
        fragment.setArguments(args);
        return fragment;
    }

    String val;
    TextView c;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        val = getArguments().getString("category_name", "");
        c = view.findViewById(R.id.c);
        c.setText("" + val);
        return view;
    }
}
