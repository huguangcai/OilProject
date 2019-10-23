package com.ysxsoft.grainandoil.modle;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/4/27 0027
 **/
public class ProductListResponse {

    /**
     * code : 0
     * msg : 操作成功!！
     * data : [{"gid":252,"goods_name":"福临门花生油","price":"80","imgurl":"http://abc.zzshopping.cn/uploads/20190422/90c0384a20677b790a6e4eb8a8dfae82.png","group":0,"category_name":"白金级"},{"gid":251,"goods_name":"双立人（ZWILLING）德国双立人刀具厨具套装菜刀剪刀刀架鸿运当头8件套","price":"800","imgurl":"http://abc.zzshopping.cn/uploads/20190422/538cf5087fdd853d616dfcb30dc41c9e.jpg","group":0,"category_name":"白金级"},{"gid":250,"goods_name":"金龙鱼花生油","price":"80","imgurl":"http://abc.zzshopping.cn/uploads/20190422/590abc82ef9dfe6bacddb59f99c657b5.png","group":0,"category_name":"白金级"},{"gid":249,"goods_name":"金龙鱼花生油","price":"80","imgurl":"http://abc.zzshopping.cn/uploads/20190422/590abc82ef9dfe6bacddb59f99c657b5.png","group":0,"category_name":"白金级"}]
     * last_page : 1
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

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * gid : 252
         * goods_name : 福临门花生油
         * price : 80
         * imgurl : http://abc.zzshopping.cn/uploads/20190422/90c0384a20677b790a6e4eb8a8dfae82.png
         * group : 0
         * category_name : 白金级
         */

        private int gid;
        private String goods_name;
        private String price;
        private String imgurl;
        private String group;
        private String category_name;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }
}
