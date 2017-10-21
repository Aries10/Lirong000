package com.bwie.shopping.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/9/8  19:12
 */

public class MyUtils {

    private static SharedPreferences sharedPreferences = null;
    //将UniCode转化


    public static String Unicode2GBK(String dataStr) {

        int index = 0;

        StringBuffer buffer = new StringBuffer();



        int li_len = dataStr.length();

        while (index < li_len) {

            if (index >= li_len - 1

                    || !"\\u".equals(dataStr.substring(index, index + 2))) {

                buffer.append(dataStr.charAt(index));



                index++;

                continue;

            }



            String charStr = "";

            charStr = dataStr.substring(index + 2, index + 6);



            char letter = (char) Integer.parseInt(charStr, 16);



            buffer.append(letter);

            index += 6;

        }

        return buffer.toString();

    }


    //SharedPreferences单例模式
    public static SharedPreferences getSharedPreferencesInstance(Context context){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
}
