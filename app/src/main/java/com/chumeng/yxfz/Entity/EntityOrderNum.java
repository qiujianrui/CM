package com.chumeng.yxfz.Entity;

/**
 * Created by qiu on 2018/8/28.
 */

public class EntityOrderNum {

    /**
     * code : 1
     * message : 获取成功
     * data : {"u_num":"1926","o_num":"877"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * u_num : 1926
         * o_num : 877
         */

        private String u_num;
        private String o_num;

        public String getU_num() {
            return u_num;
        }

        public void setU_num(String u_num) {
            this.u_num = u_num;
        }

        public String getO_num() {
            return o_num;
        }

        public void setO_num(String o_num) {
            this.o_num = o_num;
        }
    }
}
