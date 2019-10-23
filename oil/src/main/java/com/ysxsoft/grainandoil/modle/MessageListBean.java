package com.ysxsoft.grainandoil.modle;

import java.util.List;

public class MessageListBean {


    /**
     * code : 0
     * msg : 成功！
     * data : [{"sid":2,"goods_name":"阿迪达斯男装","goods_img":"http://bzdsh.sanzhima.cn/uploads/20190109/aa382aff12506ffd880b9a3f9200edb4.jpg","size":"M","colour":"红色","flag":2,"types":1,"addtime":"2019-01-22 14:03:02","news":1},{"sid":1,"flag":1,"text":"1478","addtime":"2019-01-21 11:40:08","news":1}]
     */

    private String code;
    private String msg;
    private int last_page;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public static class DataBean {
        /**
         * sid : 2
         * goods_name : 阿迪达斯男装
         * goods_img : http://bzdsh.sanzhima.cn/uploads/20190109/aa382aff12506ffd880b9a3f9200edb4.jpg
         * size : M
         * colour : 红色
         * flag : 2
         * types : 1
         * addtime : 2019-01-22 14:03:02
         * news : 1
         * text : 1478
         */

        private String sid;
        private String goods_name;
        private String goods_img;
        private String size;
        private String colour;
        private int flag;
        private String types;
        private String addtime;
        private int news;
        private String text;

        public String getTexts() {
            return texts;
        }

        public void setTexts(String texts) {
            this.texts = texts;
        }

        private String texts;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getNews() {
            return news;
        }

        public void setNews(int news) {
            this.news = news;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
