package com.bawei.myproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.bawei.myproject.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell-pc on 2017/9/20.
 */

public class Pindao {

    private List<ChannelBean> list;
    private MainActivity mainActivity;
    private String jsonStr;
    private final SharedPreferences preferences;

    public Pindao(List<ChannelBean> list, MainActivity mainActivity,String jsonStr) {
        this.list = list;
        this.mainActivity = mainActivity;
        this.jsonStr = jsonStr;
        preferences = mainActivity.getSharedPreferences("pindao", Context.MODE_PRIVATE);
    }

    public void getPindaoData(){
        //频道管理
        if (list == null) {//判断集合中是否已有数据，没有则创建
            list = new ArrayList<>();
            //第一个是显示的条目，第二个参数是否显示
            list.add(new ChannelBean("热点", true));
            list.add(new ChannelBean("军事", true));
            list.add(new ChannelBean("八卦", true));
            list.add(new ChannelBean("游戏", true));
            list.add(new ChannelBean("宠物", true));
            list.add(new ChannelBean("汽车", false));
            list.add(new ChannelBean("热卖", false));
            list.add(new ChannelBean("外卖", false));
            list.add(new ChannelBean("太阳花", false));
            list.add(new ChannelBean("九三", false));
            list.add(new ChannelBean("八嘎", false));
            list.add(new ChannelBean("色昂", false));

            ChannelActivity.startChannelActivity(mainActivity, list);

        } else if (jsonStr != null) {//当判断保存的字符串不为空的时候，直接加载已经有了的字符串
            ChannelActivity.startChannelActivity(mainActivity, jsonStr);
        }

    }

//    private void LoadData() {

//        list = new ArrayList<>();
//        initDat1a();
//        //        创建Fragmnet对象
//        String str = preferences.getString("pindao", null);
//        if (str == null) {
//            for (int i = 0; i < list.size(); i++) {
//                FragmentMiddle.newInstance(list.get(i).getName());
//            }
//        } else {
//            //            当我们点击添加数据时通过解析保存的值来创建Fragmnet对象
//            List<ChannelBean> listAll = new Gson().fromJson(str, new TypeToken<List<ChannelBean>>() {
//            }.getType());
//            for (int i = 0; i < listAll.size(); i++) {
//                if (listAll.get(i).isSelect()) {
//                    FragmentMiddle.newInstance(list.get(i).getName());
//                }
//            }
//        }

        //通过判断来个tablayout加载标题（点击按钮和未点击按钮）

//        if(str==null){
//            for(int i=0;i<list.size();i++){
//                tabLayout.getTabAt(i).setText(tabList.get(i).getName());
//            }
//        }else {
//            List<ChannelBean> listAll= new Gson().fromJson(str, new TypeToken<List<ChannelBean>>() {}.getType());
//            for(int i = 0;i<listAll.size();i++){
//                if (listAll.get(i).isSelect()==true)
//                    tabLayout.getTabAt(i).setText(listAll.get(i).getName());
//            }
//        }
//    }
//    }

}
