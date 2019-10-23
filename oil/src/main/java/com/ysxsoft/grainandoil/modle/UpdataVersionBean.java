package com.ysxsoft.grainandoil.modle;

public class UpdataVersionBean {


    /**
     * code : 0
     * msg : 操作成功!！
     * data : {"id":1,"new_version":"1.0.2","apk_file_url":"https://fir.im/cqs9","update_log":"<p>修改bug<br/><\/p>","target_size":null,"new_md5":"","constraint":null,"addtime":1559529216,"del":0}
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
         * id : 1
         * new_version : 1.0.2
         * apk_file_url : https://fir.im/cqs9
         * update_log : <p>修改bug<br/></p>
         * target_size : null
         * new_md5 :
         * constraint : null
         * addtime : 1559529216
         * del : 0
         */

        private int id;
        private String new_version;
        private String apk_file_url;
        private String update_log;
        private Object target_size;
        private String new_md5;
        private Object constraint;
        private int addtime;
        private int del;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNew_version() {
            return new_version;
        }

        public void setNew_version(String new_version) {
            this.new_version = new_version;
        }

        public String getApk_file_url() {
            return apk_file_url;
        }

        public void setApk_file_url(String apk_file_url) {
            this.apk_file_url = apk_file_url;
        }

        public String getUpdate_log() {
            return update_log;
        }

        public void setUpdate_log(String update_log) {
            this.update_log = update_log;
        }

        public Object getTarget_size() {
            return target_size;
        }

        public void setTarget_size(Object target_size) {
            this.target_size = target_size;
        }

        public String getNew_md5() {
            return new_md5;
        }

        public void setNew_md5(String new_md5) {
            this.new_md5 = new_md5;
        }

        public Object getConstraint() {
            return constraint;
        }

        public void setConstraint(Object constraint) {
            this.constraint = constraint;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public int getDel() {
            return del;
        }

        public void setDel(int del) {
            this.del = del;
        }
    }
}
