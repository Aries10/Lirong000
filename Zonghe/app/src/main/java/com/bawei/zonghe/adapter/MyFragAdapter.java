package com.bawei.zonghe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dell-pc on 2017/10/25.
 */

public class MyFragAdapter extends FragmentPagerAdapter {

    private String[] mTitle;
    private List<Fragment> list;

    public MyFragAdapter(FragmentManager fm,String[] mTitle,List<Fragment> list) {
        super(fm);
        this.mTitle = mTitle;
        this.list = list;
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
