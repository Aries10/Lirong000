package com.bawei.xiangmutwo.bean;

/**
 * Created by dell-pc on 2017/10/21.
 */

public class AddAddressBean {

    /**
     * code : 200
     * datas : {"address_id":20}
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

    public static class DatasBean {
        /**
         * address_id : 20
         */

        private int address_id;

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }
    }
}
