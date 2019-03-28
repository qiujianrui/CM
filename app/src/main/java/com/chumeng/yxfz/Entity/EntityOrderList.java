package com.chumeng.yxfz.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiu on 2018/8/28.
 */

public class EntityOrderList {

    /**
     * code : 1
     * message : 获取成功
     * data : {"list_info":[{"id":"44","order_no":"CM201808126248","type":"陪练","title":"新手求带","content":"冲击王者","area":"苹果微信","start_star":"青铜I-0星","end_star":"钻石I-5星","price":"100.00","state":"2","username":"引起注意","user_id":"72386797","open_id":"","add_time":"2018-08-12 16:50:06","pay_time":"2018-08-12 16:52:02","pay_callback_time":"","pinch_id":"","pinch_name":"","take_time":"","finish_time":"","over_time":"","return_time":""}],"page_info":{"limit":"1","page":"1"},"where_info":{"state":2,"game_id":"2"}}
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
         * list_info : [{"id":"44","order_no":"CM201808126248","type":"陪练","title":"新手求带","content":"冲击王者","area":"苹果微信","start_star":"青铜I-0星","end_star":"钻石I-5星","price":"100.00","state":"2","username":"引起注意","user_id":"72386797","open_id":"","add_time":"2018-08-12 16:50:06","pay_time":"2018-08-12 16:52:02","pay_callback_time":"","pinch_id":"","pinch_name":"","take_time":"","finish_time":"","over_time":"","return_time":""}]
         * page_info : {"limit":"1","page":"1"}
         * where_info : {"state":2,"game_id":"2"}
         */

        private PageInfoBean page_info;
        private WhereInfoBean where_info;
        private List<ListInfoBean> list_info;

        public PageInfoBean getPage_info() {
            return page_info;
        }

        public void setPage_info(PageInfoBean page_info) {
            this.page_info = page_info;
        }

        public WhereInfoBean getWhere_info() {
            return where_info;
        }

        public void setWhere_info(WhereInfoBean where_info) {
            this.where_info = where_info;
        }

        public List<ListInfoBean> getList_info() {
            return list_info;
        }

        public void setList_info(List<ListInfoBean> list_info) {
            this.list_info = list_info;
        }

        public static class PageInfoBean {
            /**
             * limit : 1
             * page : 1
             */

            private String limit;
            private String page;

            public String getLimit() {
                return limit;
            }

            public void setLimit(String limit) {
                this.limit = limit;
            }

            public String getPage() {
                return page;
            }

            public void setPage(String page) {
                this.page = page;
            }
        }

        public static class WhereInfoBean {
            /**
             * state : 2
             * game_id : 2
             */

            private int state;
            private String game_id;

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getGame_id() {
                return game_id;
            }

            public void setGame_id(String game_id) {
                this.game_id = game_id;
            }
        }

        public static class ListInfoBean implements Serializable{
            /**
             * id : 44
             * order_no : CM201808126248
             * type : 陪练
             * title : 新手求带
             * content : 冲击王者
             * area : 苹果微信
             * start_star : 青铜I-0星
             * end_star : 钻石I-5星
             * price : 100.00
             * state : 2
             * username : 引起注意
             * user_id : 72386797
             * open_id :
             * add_time : 2018-08-12 16:50:06
             * pay_time : 2018-08-12 16:52:02
             * pay_callback_time :
             * pinch_id :
             * pinch_name :
             * take_time :
             * finish_time :
             * over_time :
             * return_time :
             */

            private String id;
            private String order_no;
            private String type;
            private String title;
            private String content;
            private String area;
            private String start_star;
            private String end_star;
            private String price;
            private String state;
            private String username;
            private String user_id;
            private String open_id;
            private String add_time;
            private String pay_time;
            private String pay_callback_time;
            private String pinch_id;
            private String pinch_name;
            private String take_time;
            private String finish_time;
            private String over_time;
            private String return_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getStart_star() {
                return start_star;
            }

            public void setStart_star(String start_star) {
                this.start_star = start_star;
            }

            public String getEnd_star() {
                return end_star;
            }

            public void setEnd_star(String end_star) {
                this.end_star = end_star;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getOpen_id() {
                return open_id;
            }

            public void setOpen_id(String open_id) {
                this.open_id = open_id;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getPay_time() {
                return pay_time;
            }

            public void setPay_time(String pay_time) {
                this.pay_time = pay_time;
            }

            public String getPay_callback_time() {
                return pay_callback_time;
            }

            public void setPay_callback_time(String pay_callback_time) {
                this.pay_callback_time = pay_callback_time;
            }

            public String getPinch_id() {
                return pinch_id;
            }

            public void setPinch_id(String pinch_id) {
                this.pinch_id = pinch_id;
            }

            public String getPinch_name() {
                return pinch_name;
            }

            public void setPinch_name(String pinch_name) {
                this.pinch_name = pinch_name;
            }

            public String getTake_time() {
                return take_time;
            }

            public void setTake_time(String take_time) {
                this.take_time = take_time;
            }

            public String getFinish_time() {
                return finish_time;
            }

            public void setFinish_time(String finish_time) {
                this.finish_time = finish_time;
            }

            public String getOver_time() {
                return over_time;
            }

            public void setOver_time(String over_time) {
                this.over_time = over_time;
            }

            public String getReturn_time() {
                return return_time;
            }

            public void setReturn_time(String return_time) {
                this.return_time = return_time;
            }
        }
    }
}
