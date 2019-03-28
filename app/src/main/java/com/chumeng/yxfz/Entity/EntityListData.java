package com.chumeng.yxfz.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiu on 2018/8/30.
 */

public class EntityListData implements Serializable{

    /**
     * code : 1
     * message : 获取成功
     * data : [{"id":"1","name":"苹果微信"},{"id":"2","name":"苹果QQ"},{"id":"3","name":"安卓微信"},{"id":"4","name":"安卓QQ"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        public DataBean(String name) {
            this.name = name;
        }

        /**
         * id : 1
         * name : 苹果微信
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
