package com.ysxsoft.grainandoil.modle;

public class MyMsgBean {


    /**
     * code : 0
     * msg : 获取成功！
     * data : {"id":86,"nickname":"简简单单","avatar":"755","sex":0,"mobile":"15138651620","role":3,"prices":"90.66","price":"91.10","money":"18.17","idcare":"1","imgurl":"http://abc.zzshopping.cn/uploads//20190506/c32f8af2f05e2efa821dd1866b80c498.jpg","type":2,"zds":2,"time":0,"zong":0.01,"dengji":5,"dengjis":"至尊级"}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 86
         * nickname : 简简单单
         * avatar : 755
         * sex : 0
         * mobile : 15138651620
         * role : 3
         * prices : 90.66
         * price : 91.10
         * money : 18.17
         * idcare : 1
         * imgurl : http://abc.zzshopping.cn/uploads//20190506/c32f8af2f05e2efa821dd1866b80c498.jpg
         * type : 2
         * zds : 2
         * time : 0
         * zong : 0.01
         * dengji : 5
         * dengjis : 至尊级
         */

        private String id;
        private String nickname;
        private String avatar;
        private String sex;
        private String mobile;
        private String role;
        private String prices;
        private String price;
        private String money;
        private String idcare;
        private String imgurl;
        private String type;
        private String zds;
        private String time;
        private String zong;
        private String dengji;
        private String dengjis;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getPrices() {
            return prices;
        }

        public void setPrices(String prices) {
            this.prices = prices;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getIdcare() {
            return idcare;
        }

        public void setIdcare(String idcare) {
            this.idcare = idcare;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getZds() {
            return zds;
        }

        public void setZds(String zds) {
            this.zds = zds;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getZong() {
            return zong;
        }

        public void setZong(String zong) {
            this.zong = zong;
        }

        public String getDengji() {
            return dengji;
        }

        public void setDengji(String dengji) {
            this.dengji = dengji;
        }

        public String getDengjis() {
            return dengjis;
        }

        public void setDengjis(String dengjis) {
            this.dengjis = dengjis;
        }
    }
}
