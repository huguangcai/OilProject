package com.ysxsoft.grainandoil.modle;

public class AgreementBean {

    /**
     * code : 0
     * msg : 获取成功！
     * data : {"text":"<p>会员协议<\/p><p>会员协议<\/p><p>会员协议<\/p><p>会员协议<\/p><p>会员协议<\/p><p style=\"white-space: normal;\">会员协议<\/p><p style=\"white-space: normal;\">会员协议<\/p><p style=\"white-space: normal;\">会员协议<\/p><p style=\"white-space: normal;\">会员协议<\/p><p style=\"white-space: normal;\">会员协议<\/p><p style=\"white-space: normal;\">会员协议<\/p><p style=\"white-space: normal;\">会员协议<\/p><p style=\"white-space: normal;\">会员协议<\/p><p style=\"white-space: normal;\">会员协议<\/p><p style=\"white-space: normal;\">会员协议<\/p><p><br/><\/p>"}
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
         * text : <p>会员协议</p><p>会员协议</p><p>会员协议</p><p>会员协议</p><p>会员协议</p><p style="white-space: normal;">会员协议</p><p style="white-space: normal;">会员协议</p><p style="white-space: normal;">会员协议</p><p style="white-space: normal;">会员协议</p><p style="white-space: normal;">会员协议</p><p style="white-space: normal;">会员协议</p><p style="white-space: normal;">会员协议</p><p style="white-space: normal;">会员协议</p><p style="white-space: normal;">会员协议</p><p style="white-space: normal;">会员协议</p><p><br/></p>
         */

        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
