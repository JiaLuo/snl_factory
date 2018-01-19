package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2018/1/11.
 */

public class WashingEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<WashingResult> results;

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

    public List<WashingResult> getResults() {
        return results;
    }

    public void setResults(List<WashingResult> results) {
        this.results = results;
    }

    public class WashingResult{
        @SerializedName("id")
        private String id;
        @SerializedName("item_name")
        private String itemName;
        @SerializedName("clean_sn")
        private String cleanSn;
        @SerializedName("assist") // 判断有没有清洗 0 就可入厂或清洗
        private String assist;


        public boolean isSelect = false;

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

        public String getAssist() {
            return assist;
        }

        public void setAssist(String assist) {
            this.assist = assist;
        }

    }
}
