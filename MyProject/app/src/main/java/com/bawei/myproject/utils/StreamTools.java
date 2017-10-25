package com.bawei.myproject.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by dell-pc on 2017/9/15.
 */

public class StreamTools {

    public static String getStr(InputStream is){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = is.read(buffer)) != -1){
                stream.write(buffer,0,len);
            }
            return  stream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
