package com.bawei.xiangmutwo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.okhttplibrary.utils.GsonObjectCallback;
import com.bawei.okhttplibrary.utils.OkhttpUtils;
import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.activity.AddressActivity;
import com.bawei.xiangmutwo.activity.LoginRegActivity;
import com.bawei.xiangmutwo.api.Api;
import com.bawei.xiangmutwo.bean.LoginBean;
import com.bawei.xiangmutwo.bean.UnregBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by dell-pc on 2017/10/4.
 */

public class FragmentMine extends Fragment implements View.OnClickListener {

//    private SharedPreferences preferences;
    private View view;
    private TextView tv_exit;
    private TextView tv_name;
    private TextView tv_address;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragmentmine, container, false);

        tv_exit = (TextView) view.findViewById(R.id.tv_exit);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_address = (TextView) view.findViewById(R.id.tv_address);

        boolean isLogin = Api.sharedPreferences.getBoolean("isLogin", false);

        EventBus.getDefault().register(this);

        //如果为false 跳转至登录页面
        if(isLogin == false){
            Intent intent = new Intent(getActivity(), LoginRegActivity.class);
            intent.putExtra("isLogin",isLogin);
            startActivity(intent);
        }

        tv_exit.setOnClickListener(this);
        tv_address.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_exit:
                if(tv_exit.getText().toString().equals("退出账号")){
                    String key = Api.sharedPreferences.getString("key", "");
                    String username = Api.sharedPreferences.getString("username", "");
                    Map<String,String> map = new HashMap<>();
                    map.put("key",key);
                    map.put("username",username);
                    map.put("client",Api.Client);
                    OkhttpUtils.doPost(map, Api.UNREG, new GsonObjectCallback<UnregBean>() {
                        @Override
                        public void onUi(UnregBean unregBean) {
                            //点击退出登录 重新设置保存的数据
                            tv_exit.setText("登录");
                            tv_name.setText("未登录");
                            Api.editor.putString("key","").commit();
                            Api.editor.putString("username","").commit();
                            Api.editor.putBoolean("isLogin",false).commit();
                        }

                        @Override
                        public void onFailed(Call call, IOException e) {

                        }
                    });
                }else if(tv_exit.getText().toString().equals("登录")){
                    //点击登录重新创建数据
                    boolean isLogin = Api.sharedPreferences.getBoolean("isLogin", false);
                    Intent intent = new Intent(getActivity(), LoginRegActivity.class);
                    intent.putExtra("isLogin",isLogin);
                    startActivity(intent);
                }
                break;
            case R.id.tv_address:
                startActivity(new Intent(getActivity(), AddressActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEvent(LoginBean loginBean){
        Api.sharedPreferences.edit().putBoolean("isLogin", true).commit();
        tv_name.setText("用户"+loginBean.getDatas().getUsername()+"已登录");
        tv_exit.setText("退出账号");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
