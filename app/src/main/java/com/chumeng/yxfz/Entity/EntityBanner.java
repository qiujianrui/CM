package com.chumeng.yxfz.Entity;

import java.util.List;

/**
 * Created by qiu on 2018/8/28.
 */

public class EntityBanner {
    /**
     * code : 1
     * message : 获取成功
     * data : [{"img":"/Public/images/banner.jpg","href":"http://chumeng.vip/Home/Index/ad"},{"img":"/Public/images/banner.jpg","href":"http://chumeng.vip/Home/Index/ad"}]
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

    public static class DataBean {
        /**
         * img : /Public/images/banner.jpg
         * href : http://chumeng.vip/Home/Index/ad
         */

        private String img;
        private String href;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }
}
