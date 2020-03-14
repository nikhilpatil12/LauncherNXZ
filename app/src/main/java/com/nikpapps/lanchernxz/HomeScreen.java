package com.nikpapps.lanchernxz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class HomeScreen extends Fragment implements View.OnClickListener{

    public HomeScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_screen, container, false);
        ImageView Icon = v.findViewById(R.id.icon);
        Icon.setImageDrawable(MainActivity.getActivityIcon(this.getContext(), "com.android.chrome", "com.google.android.apps.chrome.Main"));
        Icon.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.icon:
                Intent launchIntent = MainActivity.baseContext.getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                startActivity(launchIntent);
                break;
        }


    }


}
