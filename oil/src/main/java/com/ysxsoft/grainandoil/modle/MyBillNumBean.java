package com.ysxsoft.grainandoil.modle;

public class MyBillNumBean {

    /**
     * code : 0
     * msg : 成功！！
     * data : {"or":0,"os":0,"od":0,"of":0}
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
         * or : 0
         * os : 0
         * od : 0
         * of : 0
         */

        private String or;
        private String os;
        private String od;
        private String of;

        public String getOe() {
            return oe;
        }

        public void setOe(String oe) {
            this.oe = oe;
        }

        private String oe;

        public String getOr() {
            return or;
        }

        public void setOr(String or) {
            this.or = or;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOd() {
            return od;
        }

        public void setOd(String od) {
            this.od = od;
        }

        public String getOf() {
            return of;
        }

        public void setOf(String of) {
            this.of = of;
        }
    }
}
