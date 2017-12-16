package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/24.
 */

public class OrderDisposeEntities {
    /**
     * {
     "code": 0,
     "msg": "SUCCESS",
     "result": [
     {
     "id": “5",           订单ID
     "ordersn": “171213201325249678",    订单编号
     "time": "2018-09-20 12:00~13:00”,     用户预约时间
     "umobile": "18745729547",
     "uname": "杨云龙",
     "uaddress": "北京市朝阳区万达广场三号楼1902室",
     "otime": "2017.12.16 10:29”               下单时间
     }
     ]
     }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<OrderDisposeResult> results;

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

    public List<OrderDisposeResult> getResults() {
        return results;
    }

    public void setResults(List<OrderDisposeResult> results) {
        this.results = results;
    }

    public class OrderDisposeResult{
//        @SerializedName("adr")
//        private String adr;
//        @SerializedName("create_time")
//        private String createTime;
//        @SerializedName("id")
//        private String id;
//        @SerializedName("name")
//        private String name;
//        @SerializedName("ordersn")
//        private String ordersn;
//        @SerializedName("phone")
//        private String phone;
//        @SerializedName("state")
//        private int state;
//        @SerializedName("time")
//        private String time;

        @SerializedName("id")
        private String id;
        @SerializedName("ordersn")
        private String orderSn;
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

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
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
