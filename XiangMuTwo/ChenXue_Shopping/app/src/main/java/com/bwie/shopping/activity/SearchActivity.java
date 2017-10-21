package com.bwie.shopping.activity;

import android.content.Intent;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.shopping.R;
import com.bwie.shopping.adapter.MyAdapter_History;
import com.bwie.shopping.api.API;
import com.bwie.shopping.bean.SearchBean;
import com.bwie.shopping.fragment.Fragment_Message;
import com.bwie.shopping.utils.GetServerData;
import com.bwie.shopping.utils.MyUtils;
import com.bwie.shopping.utils.SearchHistoryUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    private ImageView search_back, camera_search;
    private TextView search_from;
    private EditText edit_search;
    private Button bu_search;
    private ListView lv;
    //最多显示几条历史记录
    private static final int HISTORY_MAX = 5;
    private List<String> mResults;
    private TextView mFooterView;
    private RelativeLayout history;
    private MyAdapter_History adapter;
    private Map<String, String> hisAll;
    private Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //获取控件
        initView();
        //得到传过来的值
        final Intent intent = getIntent();
        String search_content = intent.getStringExtra("search_content");
        //赋值给输入框
        edit_search.setHint(search_content);
        //返回按钮点击监听
        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, Fragment_Message.class);
                intent.putExtra("content", SearchHistoryUtils.getFirst(SearchActivity.this));
                startActivity(intent);
            }
        });
        //判断edit_search是否为空
        if (TextUtils.isEmpty(edit_search.getText().toString())){
            //展示历史记录
            showSearchHistory();
        }
        //EditText监听
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {//索索框内容为空  显示历史记录
                    showSearchHistory();
                }
            }
        });
        //搜索按钮点击监听
        bu_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edit_search.getText().toString())) {
                    saveSearchHistory(edit_search.getHint().toString());
                } else {
                    saveSearchHistory(edit_search.getText().toString());
                }
                //点击搜索 跳转搜索结果
                intent1 = new Intent(SearchActivity.this, SearchResultActivity.class);
                //根据输入的内容传值
                if (edit_search.getText().toString().contains("劳力士")) {
                    getServerData();
                } else {
                    String json = "暂无此商品";
                    intent1.putExtra("json", json);
                    intent1.putExtra("edit",edit_search.getText().toString());
                    startActivity(intent1);
                }
            }
        });
        //mFooterView点击监听
        mFooterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除历史记录
                clearsearchHistory();
            }
        });
    }

    //获取控件的方法
    private void initView() {
        search_back = (ImageView) findViewById(R.id.search_back);
        search_from = (TextView) findViewById(R.id.search_from);
        camera_search = (ImageView) findViewById(R.id.camera_search);
        edit_search = (EditText) findViewById(R.id.edit_search);
        bu_search = (Button) findViewById(R.id.bu_search);
        lv = (ListView) findViewById(R.id.lv_history);
        mFooterView = (TextView) findViewById(R.id.clear_history);
        history = (RelativeLayout) findViewById(R.id.history);
    }

    //保存搜索记录的方法
    private void saveSearchHistory(String keyWords) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //保存之前要先查询sp中是否有该value的记录，有则删除.这样保证搜索历史记录不会有重复条目
        Map<String, String> historys = (Map<String, String>) SearchHistoryUtils.getAll(SearchActivity.this);
        for (Map.Entry<String, String> entry : historys.entrySet()) {
            if (keyWords.equals(entry.getValue())) {
                SearchHistoryUtils.remove(SearchActivity.this, entry.getKey());
            }
        }
        SearchHistoryUtils.put(SearchActivity.this, "" + mFormat.format(System.currentTimeMillis()), keyWords);
    }

    //显示历史记录
    private void showSearchHistory() {
        hisAll = (Map<String, String>) SearchHistoryUtils.getAll(SearchActivity.this);
        //将key排序升序
        Object[] keys = hisAll.keySet().toArray();
        Arrays.sort(keys);
        int keyLeng = keys.length;
        //这里计算 如果历史记录条数是大于 可以显示的最大条数，则用最大条数做循环条件，防止历史记录条数-最大条数为负值，数组越界
        int hisLeng = keyLeng > HISTORY_MAX ? HISTORY_MAX : keyLeng;
        mResults = new ArrayList<>();
        for (int i = 1; i <= hisLeng; i++) {
            mResults.add(hisAll.get(keys[keyLeng - i]));
        }
        lv.setAdapter(new MyAdapter_History(mResults, SearchActivity.this));
        //历史记录点击监听
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveSearchHistory(mResults.get(position));
                //点击搜索 跳转搜索结果
                intent1 = new Intent(SearchActivity.this, SearchResultActivity.class);
                //根据输入的内容传值
                if (mResults.get(position).contains("劳力士")) {
                    Log.e("+++++++++++","劳力士");
                    getServerData();
                } else {
                    String json = "暂无此商品";
                    intent1.putExtra("json", json);
                    intent1.putExtra("edit",mResults.get(position));
                    startActivity(intent1);
                }
            }
        });
        //如果size不为0 显示footerview
        mFooterView.setVisibility(0 != mResults.size() ? View.VISIBLE : View.GONE);
        history.setVisibility(0 != mResults.size() ? View.VISIBLE : View.GONE);
    }

    //清楚历史记录
    private void clearsearchHistory() {
        SearchHistoryUtils.clear(this);
        mResults.clear();
        lv.setAdapter(new MyAdapter_History(mResults, SearchActivity.this));
        //如果size不为0 显示footerview
        mFooterView.setVisibility(0 != mResults.size() ? View.VISIBLE : View.GONE);
        history.setVisibility(0 != mResults.size() ? View.VISIBLE : View.GONE);
    }

    //获取搜索结果的方法
    public void getServerData() {
        GetServerData.doGet(API.SEARCH_PATH, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        intent1.putExtra("json", json);
                        intent1.putExtra("edit",edit_search.getText().toString());
                        startActivity(intent1);
                    }
                });
            }
        });
    }
}
