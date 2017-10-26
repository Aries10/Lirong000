package com.bawei.zonghe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.zonghe.R;
import com.bawei.zonghe.activity.DetailsActivity;
import com.bawei.zonghe.adapter.MyThemeAdapter;
import com.bawei.zonghe.api.Api;
import com.bawei.zonghe.bean.ThemeBean;
import com.bwei.okhttp3ps.utils.GsonObjectCallback;
import com.bwei.okhttp3ps.utils.OkHttp3Utils;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by dell-pc on 2017/10/25.
 */

public class FragmentTheme extends Fragment {
    private RecyclerView rv_theme;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenttheme, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        rv_theme = (RecyclerView) view.findViewById(R.id.rv_theme);
        rv_theme.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        OkHttp3Utils.doGet(Api.ThemeURL, new GsonObjectCallback<ThemeBean>() {
            @Override
            public void onUi(final ThemeBean themeBean) {
                //将数据传入适配器
                MyThemeAdapter myThemeAdapter = new MyThemeAdapter(getActivity(), themeBean.getOthers());
                rv_theme.setAdapter(myThemeAdapter);
                myThemeAdapter.setOnRecyclerListener(new MyThemeAdapter.OnRecyclerListener() {
                    @Override
                    public void onRecycle(int position) {
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra("id",themeBean.getOthers().get(position).getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }
}
