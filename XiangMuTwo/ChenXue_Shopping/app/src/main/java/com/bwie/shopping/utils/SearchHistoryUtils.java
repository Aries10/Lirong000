package com.bwie.shopping.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/1
 */

public class SearchHistoryUtils {
    private static SharedPreferences sharedPreferences;

    public static Map<String, String> getAll(Context context) {
        Map<String, String> map = new HashMap<>();
        //得到
        sharedPreferences = context.getSharedPreferences("config2", context.MODE_PRIVATE);
        map = (Map<String, String>) sharedPreferences.getAll();
        return map;
    }

    public static String getFirst(Context context){
        sharedPreferences = context.getSharedPreferences("config2", context.MODE_PRIVATE);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String string = sharedPreferences.getString(mFormat.format(System.currentTimeMillis()), "解忧杂货铺");
        return string;
    }

    public static void remove(Context context, String key) {
        //得到
        sharedPreferences = context.getSharedPreferences("config2", context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(key);
        edit.commit();
    }

    public static void put(Context context, String key, String value) {
        //得到
        sharedPreferences = context.getSharedPreferences("config2", context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key,value);
        edit.commit();
    }

    public static void clear(Context context){
        //得到
        sharedPreferences = context.getSharedPreferences("config2", context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.commit();
    }
}
