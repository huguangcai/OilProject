package com.ysxsoft.grainandoil.modle;

import java.util.List;

public class AgencyListBean {

    /**
     * code : 0
     * msg : 成功！！
     * data : [{"earnings":"100","addtime":"2019-04-24 09:45:43"},{"earnings":"100","addtime":"2019-04-24 09:45:25"},{"earnings":"100","addtime":"2019-04-24 09:44:29"},{"earnings":"100","addtime":"2019-04-24 09:43:43"},{"earnings":"100","addtime":"2019-04-24 09:42:47"},{"earnings":"100","addtime":"2019-04-24 09:42:01"},{"earnings":"100","addtime":"2019-04-24 09:41:54"},{"earnings":"1500","addtime":"2019-04-23 17:43:54"},{"moneys":"700"}]
     * last_page : 1
     */

    private String code;
    private String msg;
    private int last_page;
    private List<DataBean> data;
    private String moneys;

    public String getMoneys() {
        return moneys;
    }

    public void setMoneys(String moneys) {
        this.moneys = moneys;
    }
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
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * earnings : 100
         * addtime : 2019-04-24 09:45:43
         * moneys : 700
         */

        private String earnings;
        private String addtime;

        public String getEarnings() {
            return earnings;
        }

        public void setEarnings(String earnings) {
            this.earnings = earnings;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
