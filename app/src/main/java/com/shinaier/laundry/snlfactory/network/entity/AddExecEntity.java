package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2018/1/11.
 */

public class AddExecEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private AddExecResult result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AddExecResult getResult() {
        return result;
    }

    public void setResult(AddExecResult result) {
        this.result = result;
    }

    public class AddExecResult{
        @SerializedName("id")
        private String id;
        @SerializedName("item_name")
        private String itemName;
        @SerializedName("clean_sn")
        private String cleanSn;
        @SerializedName("status")
        private String status;
        @SerializedName("assist")
        private String assist;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getCleanSn() {
            return cleanSn;
        }

        public void setCleanSn(String cleanSn) {
            this.cleanSn = cleanSn;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAssist() {
            return assist;
        }

        public void setAssist(String assist) {
            this.assist = assist;
        }
    }
}
