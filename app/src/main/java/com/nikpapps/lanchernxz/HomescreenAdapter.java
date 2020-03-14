package com.nikpapps.lanchernxz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class HomescreenAdapter extends FragmentStatePagerAdapter {
    final int NUM_PAGES =2;
    public HomescreenAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new HomeScreen();
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}