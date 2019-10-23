package com.ysxsoft.grainandoil.modle;

public class HomeDialogBean {

    /**
     * code : 0
     * msg : 成功！
     * data : {"regulation":"<p>欢迎来到乐速家<\/p>"}
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
         * regulation : <p>欢迎来到乐速家</p>
         */

        private String regulation;

        public String getRegulation() {
            return regulation;
        }

        public void setRegulation(String regulation) {
            this.regulation = regulation;
        }
    }
}
