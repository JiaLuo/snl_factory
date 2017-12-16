package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/25.
 */

public class OrderTakeOrderEntities {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<OrderTakeOrderResult> results;

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

    public List<OrderTakeOrderResult> getResults() {
        return results;
    }

    public void setResults(List<OrderTakeOrderResult> results) {
        this.results = results;
    }

    public class OrderTakeOrderResult{
//        @SerializedName("adr")
//        private String adr;
//        @SerializedName("create_time")
//        private String createTime;
//        @SerializedName("id")
//        private String id;
//        @SerializedName("item_state")
//        private int itemState;
//        @SerializedName("name")
//        private String name;
//        @SerializedName("ordersn")
//        private String ordersn;
//        @SerializedName("phone")
//        private String phone;
//        @SerializedName("state")
//        private String state;
//        @SerializedName("time")
//        private String time;
//        @SerializedName("up_time")
//        private String upTime;
//        @SerializedName("update_time") // 最近操作的时间
//        private String updateTime;

        @SerializedName("id")
        private String id;
        @SerializedName("ordersn")
        private String ordersn;
        @SerializedName("time")
        private String time;
        @SerializedName("umobile")
        private String uMobile;
        @SerializedName("uname")
        private String uName;
        @SerializedName("uaddress")
        private String uAddress;
        @SerializedName("otime")
        private String oTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getuMobile() {
            return uMobile;
        }

        public void setuMobile(String uMobile) {
            this.uMobile = uMobile;
        }

        public String getuName() {
            return uName;
        }

        public void setuName(String uName) {
            this.uName = uName;
        }

        public String getuAddress() {
            return uAddress;
        }

        public void setuAddress(String uAddress) {
            this.uAddress = uAddress;
        }

        public String getoTime() {
            return oTime;
        }

        public void setoTime(String oTime) {
            this.oTime = oTime;
        }
    }
}
