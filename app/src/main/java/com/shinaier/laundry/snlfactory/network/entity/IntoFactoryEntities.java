package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2018/1/18.
 */

public class IntoFactoryEntities {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<IntoFactoryResult> results;

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

    public List<IntoFactoryResult> getResults() {
        return results;
    }

    public void setResults(List<IntoFactoryResult> results) {
        this.results = results;
    }

    public class IntoFactoryResult{
        @SerializedName("date")
        private String date;
        @SerializedName("list")
        private List<IntoFactoryList> lists;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<IntoFactoryList> getLists() {
            return lists;
        }

        public void setLists(List<IntoFactoryList> lists) {
            this.lists = lists;
        }

        public class IntoFactoryList{
            @SerializedName("id")
            private String id;
            @SerializedName("item_name")
            private String itemName;
            @SerializedName("clean_sn")
            private String cleanSn;
            @SerializedName("assist")
            private String assist;
            @SerializedName("time")
            private String time;

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
