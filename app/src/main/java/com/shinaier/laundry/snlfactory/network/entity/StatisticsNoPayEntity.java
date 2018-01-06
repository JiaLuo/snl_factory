package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/1.
 */

public class StatisticsNoPayEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<StatisticsResult> result;

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

    public List<StatisticsResult> getResult() {
        return result;
    }

    public void setResult(List<StatisticsResult> result) {
        this.result = result;
    }

    public class StatisticsResult{
        @SerializedName("date")
        private String date;
        @SerializedName("list")
        private List<StatisticsInnerOrders> innerOrders;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<StatisticsInnerOrders> getInnerOrders() {
            return innerOrders;
        }

        public void setInnerOrders(List<StatisticsInnerOrders> innerOrders) {
            this.innerOrders = innerOrders;
        }

        public class StatisticsInnerOrders{
            @SerializedName("id")
            private String id;
            @SerializedName("uname")
            private String uName;
            @SerializedName("pay_amount")
            private String amount;
            @SerializedName("ordersn")
            private String ordersn;
            @SerializedName("otime")
            private String oTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getuName() {
                return uName;
            }

            public void setuName(String uName) {
                this.uName = uName;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getOrdersn() {
                return ordersn;
            }

            public void setOrdersn(String ordersn) {
                this.ordersn = ordersn;
            }

            public String getoTime() {
                return oTime;
            }

            public void setoTime(String oTime) {
                this.oTime = oTime;
            }
        }
    }
}
