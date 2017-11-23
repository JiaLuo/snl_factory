package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/22.
 */

public class OrderInquiryEntities {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("count")
    private int count;
    @SerializedName("data")
    private List<Data> dataList;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public class Data{
        @SerializedName("id")
        private String id;
        @SerializedName("ordersn")
        private String ordersn;
        @SerializedName("state")
        private int state;
        @SerializedName("time")
        private String time;
        @SerializedName("create_time")
        private String createTime;
        @SerializedName("adresinfo")
        private String adresinfo;
        @SerializedName("freight") //上门服务费
        private String freight;
        @SerializedName("coupon_price") //优惠金额
        private String couponPrice;
        @SerializedName("update_time")
        private String updateTime;
        @SerializedName("name")
        private String name;
        @SerializedName("phone")
        private String phone;
        @SerializedName("adr")
        private String adr;
        @SerializedName("item")
        private List<Item> itemList;
        @SerializedName("special") //特殊工艺加价
        private String special;
        @SerializedName("hedging") //保值金额
        private String hedging;
        @SerializedName("amount") //总价
        private String amount;
        @SerializedName("sum") //总个数
        private String sum;
        @SerializedName("pay_state")
        private int payState; // 订单支付状态
        @SerializedName("pay_amount")
        private String payAmount;// 实际支付的金额

        public int getPayState() {
            return payState;
        }

        public void setPayState(int payState) {
            this.payState = payState;
        }

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public boolean isOpen = false;

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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAdresinfo() {
            return adresinfo;
        }

        public void setAdresinfo(String adresinfo) {
            this.adresinfo = adresinfo;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(String couponPrice) {
            this.couponPrice = couponPrice;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAdr() {
            return adr;
        }

        public void setAdr(String adr) {
            this.adr = adr;
        }

        public List<Item> getItemList() {
            return itemList;
        }

        public void setItemList(List<Item> itemList) {
            this.itemList = itemList;
        }

        public String getSpecial() {
            return special;
        }

        public void setSpecial(String special) {
            this.special = special;
        }

        public String getHedging() {
            return hedging;
        }

        public void setHedging(String hedging) {
            this.hedging = hedging;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public class Item{
            @SerializedName("price")
            private String price;
            @SerializedName("number")
            private String number;
            @SerializedName("g_name")
            private String gName;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getgName() {
                return gName;
            }

            public void setgName(String gName) {
                this.gName = gName;
            }
        }
    }
}
