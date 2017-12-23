package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/25.
 */

public class OrderCleaningEntities {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<OrderCleaningResult> results;
    @SerializedName("count")
    private int count;
    @SerializedName("page_count")
    private int pageCount;

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

    public List<OrderCleaningResult> getResults() {
        return results;
    }

    public void setResults(List<OrderCleaningResult> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public class OrderCleaningResult{
        @SerializedName("id")
        private String id;
        @SerializedName("ordersn")
        private String ordersn;
        @SerializedName("craft_price")
        private String craftPrice;
        @SerializedName("reduce_price")
        private String reducePrice;
        @SerializedName("freight_price")
        private String freightPrice;
        @SerializedName("amount")
        private String amount;
        @SerializedName("pay_amount")
        private String payAmount;
        @SerializedName("keep_price")
        private String keepPrice;
        @SerializedName("otime")
        private String oTime;
        @SerializedName("uname")
        private String uName;
        @SerializedName("uaddress")
        private String uAddress;
        @SerializedName("umobile")
        private String uMobile;
        @SerializedName("items")
        private List<OrderCleaningItems> itemses;

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

        public String getCraftPrice() {
            return craftPrice;
        }

        public void setCraftPrice(String craftPrice) {
            this.craftPrice = craftPrice;
        }

        public String getReducePrice() {
            return reducePrice;
        }

        public void setReducePrice(String reducePrice) {
            this.reducePrice = reducePrice;
        }

        public String getFreightPrice() {
            return freightPrice;
        }

        public void setFreightPrice(String freightPrice) {
            this.freightPrice = freightPrice;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getKeepPrice() {
            return keepPrice;
        }

        public void setKeepPrice(String keepPrice) {
            this.keepPrice = keepPrice;
        }

        public String getoTime() {
            return oTime;
        }

        public void setoTime(String oTime) {
            this.oTime = oTime;
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

        public String getuMobile() {
            return uMobile;
        }

        public void setuMobile(String uMobile) {
            this.uMobile = uMobile;
        }

        public List<OrderCleaningItems> getItemses() {
            return itemses;
        }

        public void setItemses(List<OrderCleaningItems> itemses) {
            this.itemses = itemses;
        }

        public class OrderCleaningItems{
            @SerializedName("item_name")
            private String itemName;
            @SerializedName("item_price")
            private String itemPrice;
            @SerializedName("put_sn")
            private String putSn;

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getItemPrice() {
                return itemPrice;
            }

            public void setItemPrice(String itemPrice) {
                this.itemPrice = itemPrice;
            }

            public String getPutSn() {
                return putSn;
            }

            public void setPutSn(String putSn) {
                this.putSn = putSn;
            }
        }
    }
}
