package com.bawei.xiangmutwo.bean;

import java.util.List;

/**
 * Created by dell-pc on 2017/10/17.
 */

public class TypeThirdBean {

    /**
     * code : 200
     * datas : {"class_list":[{"gc_id":"358","gc_name":"剃须刀"},{"gc_id":"359","gc_name":"剃/脱毛器"},{"gc_id":"360","gc_name":"口腔护理"},{"gc_id":"361","gc_name":"电吹风"},{"gc_id":"362","gc_name":"美容器"},{"gc_id":"363","gc_name":"美发器"},{"gc_id":"364","gc_name":"按摩椅"},{"gc_id":"365","gc_name":"按摩器"},{"gc_id":"366","gc_name":"足浴盆"},{"gc_id":"367","gc_name":"血压计"},{"gc_id":"368","gc_name":"健康秤/厨房秤"},{"gc_id":"369","gc_name":"血糖仪"},{"gc_id":"370","gc_name":"体温计"},{"gc_id":"371","gc_name":"计步器/脂肪检测仪"},{"gc_id":"372","gc_name":"其它健康电器"}]}
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
        private List<ClassListBean> class_list;

        public List<ClassListBean> getClass_list() {
            return class_list;
        }

        public void setClass_list(List<ClassListBean> class_list) {
            this.class_list = class_list;
        }

        public static class ClassListBean {
            /**
             * gc_id : 358
             * gc_name : 剃须刀
             */

            private String gc_id;
            private String gc_name;

            public String getGc_id() {
                return gc_id;
            }

            public void setGc_id(String gc_id) {
                this.gc_id = gc_id;
            }

            public String getGc_name() {
                return gc_name;
            }

            public void setGc_name(String gc_name) {
                this.gc_name = gc_name;
            }
        }
    }
}
