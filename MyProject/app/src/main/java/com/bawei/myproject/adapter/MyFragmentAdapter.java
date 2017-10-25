package com.bawei.myproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bawei.myproject.fragment.FragmentMiddle;

/**
 * Created by dell-pc on 2017/9/13.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private String[] mTitles;

    public MyFragmentAdapter(FragmentManager fm,String[] titles,String[] mTitles) {
        super(fm);
        this.titles = titles;
        this.mTitles = mTitles;
    }


    @Override
    public Fragment getItem(int position) {
        return FragmentMiddle.newInstance(mTitles[position]);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
