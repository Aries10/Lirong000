package com.bawei.xiangmutwo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.activity.SearchActivity;

/**
 * Created by dell-pc on 2017/10/4.
 */

public class FragmentIndex extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ImageView iv_erweima;
    private EditText et_sea;
    private ImageView iv_camera;
    private RecyclerView rv_index;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentindex, container, false);
        initView(view);
        return view;
    }

    //初始化组件
    private void initView(View view) {
        iv_erweima = (ImageView) view.findViewById(R.id.iv_erweima);
        et_sea = (EditText) view.findViewById(R.id.et_sea);
        iv_camera = (ImageView) view.findViewById(R.id.iv_camera);
        rv_index = (RecyclerView) view.findViewById(R.id.rv_index);

        //设置点击监听
        iv_erweima.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        et_sea.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_sea:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.iv_erweima:
                break;
            case R.id.iv_camera:
                break;
        }
    }
}
