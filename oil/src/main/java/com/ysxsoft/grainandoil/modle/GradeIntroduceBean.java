package com.ysxsoft.grainandoil.modle;

import java.util.List;

public class GradeIntroduceBean {

    /**
     * code : 0
     * msg : 获取成功！！
     * data : [{"cid":18,"category_name":"白金级","grade":1,"texts":"充值1000既享每3月提升一次额度","counts":"<p>充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度<\/p>","del":0},{"cid":19,"category_name":"钻石级","grade":2,"texts":"充值3000既享每3月提升一次额度","counts":"<p>充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度<\/p>","del":0},{"cid":20,"category_name":"星耀级","grade":3,"texts":"充值5000既享每2月提升一次额度","counts":"<p>充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度<\/p>","del":0},{"cid":21,"category_name":"荣耀级","grade":4,"texts":"充值1万既享每2月提升一次额度","counts":"<p>充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度<\/p><p><br/><\/p>","del":0},{"cid":22,"category_name":"至尊级","grade":5,"texts":"充值10万既享每1月提升一次额度","counts":"<p>充值10万既享每1月提升一次额度充值10万既享每1月提升一次额度充值10万既享每1月提升一次额度充值10万既享每1月提升一次额度充值10万既享每1月提升一次额度充值10万既享每1月提升一次额度充值10万既享每1月提升一次额度<\/p>","del":0}]
     */

    private String code;
    private String msg;
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

    public static class DataBean {
        /**
         * cid : 18
         * category_name : 白金级
         * grade : 1
         * texts : 充值1000既享每3月提升一次额度
         * counts : <p>充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度充值1万既享每2月提升一次额度</p>
         * del : 0
         */

        private String cid;
        private String category_name;
        private int grade;
        private String texts;
        private String counts;
        private int del;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getTexts() {
            return texts;
        }

        public void setTexts(String texts) {
            this.texts = texts;
        }

        public String getCounts() {
            return counts;
        }

        public void setCounts(String counts) {
            this.counts = counts;
        }

        public int getDel() {
            return del;
        }

        public void setDel(int del) {
            this.del = del;
        }
    }
}
