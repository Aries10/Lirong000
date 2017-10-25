package com.bawei.xiangmutwo.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.okhttplibrary.utils.GsonObjectCallback;
import com.bawei.okhttplibrary.utils.OkhttpUtils;
import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.activity.SearchActivity;
import com.bawei.xiangmutwo.adapter.MyIndexAdapter;
import com.bawei.xiangmutwo.api.Api;
import com.bawei.xiangmutwo.bean.IndexBean;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by dell-pc on 2017/10/4.
 */

public class FragmentIndex extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ImageView iv_erweima;
    private EditText et_sea;
    private ImageView iv_camera;
    private RecyclerView rv_index;
    private int REQUEST_TAKE_PHOTO_PERMISSION;
    private int REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentindex, container, false);
        //获取摄像头权限
        getCameraPermission();
        //ZXingLibrary初始化
        ZXingLibrary.initDisplayOpinion(getActivity());
        initView(view);
        return view;
    }

    //初始化组件
    private void initView(View view) {
        iv_erweima = (ImageView) view.findViewById(R.id.iv_erweima);
        et_sea = (EditText) view.findViewById(R.id.et_sea);
        iv_camera = (ImageView) view.findViewById(R.id.iv_camera);
        rv_index = (RecyclerView) view.findViewById(R.id.rv_index);

        rv_index.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置点击监听
        iv_erweima.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        et_sea.setOnClickListener(this);

        //获取首页数据
        getIndexData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_sea:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.iv_erweima:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                getActivity().startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.iv_camera:
                break;
        }
    }

    private void getIndexData(){
        OkhttpUtils.doGet(Api.IndexURL, new GsonObjectCallback<IndexBean>() {
            @Override
            public void onUi(IndexBean indexBean) {
                IndexBean.DataBean data = indexBean.getData();
                MyIndexAdapter myIndexAdapter = new MyIndexAdapter(getActivity(),data);
                rv_index.setAdapter(myIndexAdapter);
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
//                    et_sea.setText(result);//解析结果显示在TextView
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //Android6.0权限
    public void getCameraPermission()
    {
        if (Build.VERSION.SDK_INT>22){
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.CAMERA},2);
            }else {
                //说明已经获取到摄像头权限了 想干嘛干嘛
            }
        }else {
            //这个说明系统版本在6.0之下，不需要动态获取权限。
        }
    }

}
