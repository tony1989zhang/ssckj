package com.loft_9086.tx.v2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.loft_9086.tx.v2.module.NewsFragment;
import com.loft_9086.tx.v2.module.ShiShiCaiFragment;
import com.loft_9086.tx.v2.module.SscOpenFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT =4;
    private String[] titles;

    public ViewPagerAdapter(FragmentManager fm, String[] titles2) {
        super(fm);
        titles=titles2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // Open FragmentTab1.java
            case 0:
                return  LiveFragment.newInstance(position);
//                return LiveFragment.newInstance(position);
            case 1:
                return SscOpenFragment.newInstance(position);
            case 2:
                return  ShiShiCaiFragment.newInstance(position);
            case 3:
                return NewsFragment.newInstance(position);
            case 4:
                return SampleFragment.newInstance(position);

        }
        return null;
    }

    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}