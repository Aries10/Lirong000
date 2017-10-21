package com.bawei.xiangmutwo.bean;

import java.io.Serializable;

/**
 * Created by dell-pc on 2017/10/12.
 */

public class LoginBean implements Serializable{
    /**
     * code : 200
     * datas : {"username":"andro","userid":"8","key":"c0e92b92c2e782221a78b8f457389440"}
     */

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean implements Serializable {
        /**
         * username : andro
         * userid : 8
         * key : c0e92b92c2e782221a78b8f457389440
         */

        private String username;
        private String userid;
        private String key;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
