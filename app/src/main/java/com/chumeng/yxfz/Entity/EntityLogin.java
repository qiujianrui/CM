package com.chumeng.yxfz.Entity;

/**
 * Created by qiu on 2018/8/27.
 */

public class EntityLogin {

    /**
     * code : 1
     * message : 登录成功
     * data : {"id":"27","phone":"17875824787","password":"d21bd10925cd12b320b8d7ae86c47555","username":"QJR","last_ip":"219.134.114.60","last_time":"2018-08-27 16:47:29","open_id":"","headurl":"","nickname":"","country":"","province":"","city":"","status":"1","idcard":"","is_stop":"0","level":"0","exp":"0","add_time":""}
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
         * id : 27
         * phone : 17875824787
         * password : d21bd10925cd12b320b8d7ae86c47555
         * username : QJR
         * last_ip : 219.134.114.60
         * last_time : 2018-08-27 16:47:29
         * open_id :
         * headurl :
         * nickname :
         * country :
         * province :
         * city :
         * status : 1
         * idcard :
         * is_stop : 0
         * level : 0
         * exp : 0
         * add_time :
         */

        private String id;
        private String phone;
        private String password;
        private String username;
        private String last_ip;
        private String last_time;
        private String open_id;
        private String headurl;
        private String nickname;
        private String country;
        private String province;
        private String city;
        private String status;
        private String idcard;
        private String is_stop;
        private String level;
        private String exp;
        private String add_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getLast_ip() {
            return last_ip;
        }

        public void setLast_ip(String last_ip) {
            this.last_ip = last_ip;
        }

        public String getLast_time() {
            return last_time;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }

        public String getOpen_id() {
            return open_id;
        }

        public void setOpen_id(String open_id) {
            this.open_id = open_id;
        }

        public String getHeadurl() {
            return headurl;
        }

        public void setHeadurl(String headurl) {
            this.headurl = headurl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getIs_stop() {
            return is_stop;
        }

        public void setIs_stop(String is_stop) {
            this.is_stop = is_stop;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
