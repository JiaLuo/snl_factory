package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/2/9.
 */

public class StoreEntity {

    /**
     *
     {
     {
     "code": 0,
     "msg": "SUCCESS",
     "result": {
     "mname": “诗奈尔上海总部",    店铺名
     "mlogo": "http://xiyi.wzj.dev.shuxier.com/uploads/2016-11-10/58241f642ceb3.png",    店铺logo
     "mstatus": “10",    店铺状态：10-营业中；11-休息
     "maddress": “万达广场",    店铺地址
     "mlevel": “5.0",    店铺星级
     "amount": “0",    今日营业额
     "order_count": “0”    今日订单量
     }
     }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private StoreResult result;
    @SerializedName("message_count")
    private String messageCount;

    public String getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(String messageCount) {
        this.messageCount = messageCount;
    }

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

    public StoreResult getResult() {
        return result;
    }

    public void setResult(StoreResult result) {
        this.result = result;
    }

    public class StoreResult{
        @SerializedName("mname")
        private String mname;
        @SerializedName("mlogo")
        private String mlogo;
        @SerializedName("mstatus") //10 正常营业 11 休息
        private String mstatus;
        @SerializedName("maddress")
        private String maddress;
        @SerializedName("mlevel")
        private String mlevel;
        @SerializedName("amount")
        private double amount;
        @SerializedName("order_count")
        private String orderCount;

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public String getMlogo() {
            return mlogo;
        }

        public void setMlogo(String mlogo) {
            this.mlogo = mlogo;
        }

        public String getMstatus() {
            return mstatus;
        }

        public void setMstatus(String mstatus) {
            this.mstatus = mstatus;
        }

        public String getMaddress() {
            return maddress;
        }

        public void setMaddress(String maddress) {
            this.maddress = maddress;
        }

        public String getMlevel() {
            return mlevel;
        }

        public void setMlevel(String mlevel) {
            this.mlevel = mlevel;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(String orderCount) {
            this.orderCount = orderCount;
        }
    }
}
