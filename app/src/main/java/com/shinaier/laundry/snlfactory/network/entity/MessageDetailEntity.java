package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2018/1/16.
 */

public class MessageDetailEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private MessageDetailResult result;

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

    public MessageDetailResult getResult() {
        return result;
    }

    public void setResult(MessageDetailResult result) {
        this.result = result;
    }

    public class MessageDetailResult{
        @SerializedName("title")
        private String title;
        @SerializedName("content")
        private String content;
        @SerializedName("ordersn")
        private String ordersn;
        @SerializedName("clean_number")
        private String cleanNumber;
        @SerializedName("name")
        private String name;
        @SerializedName("take_time")
        private String takeTime;
        @SerializedName("time")
        private String time;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getCleanNumber() {
            return cleanNumber;
        }

        public void setCleanNumber(String cleanNumber) {
            this.cleanNumber = cleanNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTakeTime() {
            return takeTime;
        }

        public void setTakeTime(String takeTime) {
            this.takeTime = takeTime;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
