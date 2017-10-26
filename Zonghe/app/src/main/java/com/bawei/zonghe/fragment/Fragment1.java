package com.bawei.zonghe.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.zonghe.R;
import com.bawei.zonghe.adapter.MyFragAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell-pc on 2017/10/25.
 */

public class Fragment1 extends Fragment {

    private TabLayout tabLayout;
    private String[] mTitle = new String[]{"最新日报", "专栏", "热门", "主题日报"};
    private ViewPager viewPager;
    private List<Fragment> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment01, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        for (String str : mTitle) {
            tabLayout.addTab(tabLayout.newTab().setText(str));
        }

        list = new ArrayList<>();
        list.add(new FragmentNew());
        list.add(new FragmentHot());
        list.add(new FragmentOther());
        list.add(new FragmentTheme());

        viewPager.setAdapter(new MyFragAdapter(getActivity().getSupportFragmentManager(),mTitle,list));
        tabLayout.setupWithViewPager(viewPager);

    }
}
