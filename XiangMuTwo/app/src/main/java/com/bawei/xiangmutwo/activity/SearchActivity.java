package com.bawei.xiangmutwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.bawei.okhttplibrary.utils.GsonObjectCallback;
import com.bawei.okhttplibrary.utils.OkhttpUtils;
import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.api.Api;
import com.bawei.xiangmutwo.bean.SearchBean;
import com.example.searchview_library.SearchALG;
import com.example.searchview_library.SearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class SearchActivity extends AppCompatActivity {
//if (str.equals("劳力士")) {
//        OkhttpUtils.doGet(Api.SEARCHURL, new GsonObjectCallback<SearchBean>() {
//            @Override
//            public void onUi(SearchBean searchBean) {
//                Intent intent = new Intent(SearchActivity.this, SearchShowActivity.class);
//                intent.putExtra("searchbean", searchBean);
//                intent.putExtra("s", str);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onFailed(Call call, IOException e) {
//
//            }
//        });
//    }

    private SearchView searchView;
    private SearchALG searchALG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = (SearchView) findViewById(R.id.searchView);
        initData();
        searchView.setOnSearchListener(new MyOnSearchListener());
    }

    private List<String> changedHintDatas;

    /**
     * 设置searview的监听
     */
    class MyOnSearchListener implements SearchView.OnSearchListener {

        /**
         * 搜索回调
         *
         * @param searchText 进行搜索的文本
         */
        @Override
        public void onSearch(final String searchText) {
            if (!TextUtils.isEmpty(searchText)) {
                Toast.makeText(SearchActivity.this, "完成搜索" + searchText, Toast.LENGTH_SHORT).show();
                if (searchText.equals("劳力士")) {
                    OkhttpUtils.doGet(Api.SEARCHURL, new GsonObjectCallback<SearchBean>() {
                        @Override
                        public void onUi(SearchBean searchBean) {
                            Intent intent = new Intent(SearchActivity.this, SearchShowActivity.class);
                            intent.putExtra("searchbean", searchBean);
                            intent.putExtra("s", searchText);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailed(Call call, IOException e) {

                        }
                    });
                }
            } else {
                Toast.makeText(SearchActivity.this, "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 刷新提示列表
         *
         * @param changedText 改变后的文本
         */
        @Override
        public void onRefreshHintList(String changedText) {
            if (changedHintDatas == null) {
                changedHintDatas = new ArrayList<>();
            } else {
                changedHintDatas.clear();
            }
            if (TextUtils.isEmpty(changedText)) {
                return;
            }
            for (int i = 0; i < hint_datas.size(); i++) {
                String hint_data = hint_datas.get(i);
                boolean isAdd = searchALG.isAddToHintList(hint_data, changedText);
                if (isAdd) {
                    changedHintDatas.add(hint_datas.get(i));
                }
            }

            /**
             * 根据搜索框文本的变化，动态的改变提示的listView
             */
            searchView.updateHintList(changedHintDatas);

        }
    }

    //热搜数据
    private List<String> hot_datas;
    //提示列表数据
    private List<String> hint_datas;

    private void initData() {
        hot_datas = new ArrayList<>();
        hint_datas = new ArrayList<>();

        searchALG = new SearchALG(this);

        for (int i = 0; i < 10; i++) {
            hot_datas.add("Android Hot " + i);
        }

        //设置热搜数据显示的列数
        searchView.setHotNumColumns(2);
        //设置热搜数据
        searchView.setHotSearchDatas(hot_datas);

        /**
         * 设置提示数据的集合
         */
        for (int i = 0; i < 10; i++) {
            hint_datas.add("ts" + "安卓学习" + "Android Hint " + i + " 你好");
        }

        /**
         * 设置自动保存搜索记录
         */
        searchView.keepSearchHistory(true);

        //设置提示列表的最大显示列数
        searchView.setMaxHintLines(8);
        //设置保存搜索记录的个数
        searchView.setMaxHistoryRecordCount(6);

    }

}
