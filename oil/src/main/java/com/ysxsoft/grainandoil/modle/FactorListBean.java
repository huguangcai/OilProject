package com.ysxsoft.grainandoil.modle;

import java.util.List;

/**
 * Create By 胡
 * on 2019/7/12 0012
 */
public class FactorListBean {

    /**
     * code : 0
     * msg : 成功
     * data : [{"id":501,"username":"17639022865","nickname":"","mobile":"17639022865"},{"id":481,"username":"18739173031","nickname":"","mobile":"18739173031"},{"id":314,"username":"18872681979","nickname":"","mobile":"18872681979"},{"id":177,"username":"17011292852","nickname":"夏天","mobile":"17011292852"},{"id":128,"username":"13014636549","nickname":"","mobile":"13014636549"},{"id":114,"username":"13253565026","nickname":"哈哈哈哈","mobile":"13253565026"},{"id":105,"username":"18539989392","nickname":"刘","mobile":"18539989392"}]
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
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 501
         * username : 17639022865
         * nickname :
         * mobile : 17639022865
         */

        private int id;
        private String username;
        private String nickname;
        private String mobile;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
