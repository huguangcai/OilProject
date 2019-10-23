package com.ysxsoft.grainandoil.modle;

import java.util.List;

public class ReMoenyBean {

    /**
     * code : 0
     * msg : 成功！！
     * data : [{"id":3,"uid":86,"year":"2020","list":[{"id":4,"pid":3,"uid":86,"price":2000,"month":"6","values":"微信还款"}]},{"id":1,"uid":86,"year":"2019","list":[{"id":1,"pid":1,"uid":86,"price":2000,"":"4","values":"支付宝还款"},{"id":3,"pid":1,"uid":86,"price":2000,"month":"5","values":"微信还款"}]}]
     * last_page : 1
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
         * id : 3
         * uid : 86
         * year : 2020
         * list : [{"id":4,"pid":3,"uid":86,"price":2000,"month":"6","values":"微信还款"}]
         */

        private String id;
        private String uid;
        private String year;
        private List<ListBean> list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 4
             * pid : 3
             * uid : 86
             * price : 2000
             * month : 6
             * values : 微信还款
             */

            private String id;
            private String pid;
            private String uid;
            private String price;
            private String month;

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            private String addtime;
            private String values;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getValues() {
                return values;
            }

            public void setValues(String values) {
                this.values = values;
            }
        }
    }
}
