package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/7.
 */

public class OrderDetailEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private OrderDetailResult detailResult;

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

    public OrderDetailResult getDetailResult() {
        return detailResult;
    }

    public void setDetailResult(OrderDetailResult detailResult) {
        this.detailResult = detailResult;
    }

    public class OrderDetailResult{
        @SerializedName("id")
        private String id;
        @SerializedName("ordersn")
        private String orderSn;
        @SerializedName("is_online")
        private String isOnline; // 1 是线上订单 2 是线下订单
        @SerializedName("amount")
        private String amount;
        @SerializedName("pay_amount")
        private String payAmount;
        @SerializedName("freight_price")
        private String freightPrice;
        @SerializedName("reduce_price")
        private String reducePrice;
        @SerializedName("freight_free")
        private String freightFree;
        @SerializedName("pay_state") // 0 是未支付 1 已支付
        private String payState;
        @SerializedName("otime")
        private String oTime;
        @SerializedName("ostatus")
        private String oStatus;
        @SerializedName("item_count")
        private String itemCount;
        @SerializedName("uname")
        private String uName;
        @SerializedName("umobile")
        private String uMobile;
        @SerializedName("uaddress")
        private String uAddress;
        @SerializedName("uremark")
        private String uRemark;
        @SerializedName("time")
        private String time;
        @SerializedName("is_company") // 线下订单 特有的字段     是否为企业会员:1-是;0-否
        private String isCompany;
        @SerializedName("total_amount")
        private String totalAmount;

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getIsCompany() {
            return isCompany;
        }

        public void setIsCompany(String isCompany) {
            this.isCompany = isCompany;
        }

        @SerializedName("items")
        private List<OrderDetailItems> orderDetailItemses;

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

        public String getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(String isOnline) {
            this.isOnline = isOnline;
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

        public String getFreightPrice() {
            return freightPrice;
        }

        public void setFreightPrice(String freightPrice) {
            this.freightPrice = freightPrice;
        }

        public String getReducePrice() {
            return reducePrice;
        }

        public void setReducePrice(String reducePrice) {
            this.reducePrice = reducePrice;
        }

        public String getFreightFree() {
            return freightFree;
        }

        public void setFreightFree(String freightFree) {
            this.freightFree = freightFree;
        }

        public String getPayState() {
            return payState;
        }

        public void setPayState(String payState) {
            this.payState = payState;
        }

        public String getoTime() {
            return oTime;
        }

        public void setoTime(String oTime) {
            this.oTime = oTime;
        }

        public String getoStatus() {
            return oStatus;
        }

        public void setoStatus(String oStatus) {
            this.oStatus = oStatus;
        }

        public String getItemCount() {
            return itemCount;
        }

        public void setItemCount(String itemCount) {
            this.itemCount = itemCount;
        }

        public String getuName() {
            return uName;
        }

        public void setuName(String uName) {
            this.uName = uName;
        }

        public String getuMobile() {
            return uMobile;
        }

        public void setuMobile(String uMobile) {
            this.uMobile = uMobile;
        }

        public String getuAddress() {
            return uAddress;
        }

        public void setuAddress(String uAddress) {
            this.uAddress = uAddress;
        }

        public String getuRemark() {
            return uRemark;
        }

        public void setuRemark(String uRemark) {
            this.uRemark = uRemark;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<OrderDetailItems> getOrderDetailItemses() {
            return orderDetailItemses;
        }

        public void setOrderDetailItemses(List<OrderDetailItems> orderDetailItemses) {
            this.orderDetailItemses = orderDetailItemses;
        }

        public class OrderDetailItems{
            @SerializedName("id")
            private String id;
            @SerializedName("item_name")
            private String itemName;
            @SerializedName("item_price")
            private String itemPrice;
            @SerializedName("keep_price")
            private double keepPrice;
            @SerializedName("craft_price")
            private String craftPrice;
            @SerializedName("item_real_price")
            private String itemRealPrice;
            @SerializedName("has_discount")
            private String hasDiscount;
            @SerializedName("item_discount")
            private String itemDiscount;
            @SerializedName("color")
            private String color;
            @SerializedName("problem")
            private String problem;
            @SerializedName("forecast")
            private String forecast;
            @SerializedName("take_time")
            private String takeTime;
            @SerializedName("craft_des")
            private String craftDes;
            @SerializedName("clean_sn")
            private String cleanSn;
            @SerializedName("put_sn")
            private String putSn;
            @SerializedName("image")
            private String image;
            @SerializedName("item_images")
            private List<String> itemImages;

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

            public String getItemPrice() {
                return itemPrice;
            }

            public void setItemPrice(String itemPrice) {
                this.itemPrice = itemPrice;
            }

            public double getKeepPrice() {
                return keepPrice;
            }

            public void setKeepPrice(double keepPrice) {
                this.keepPrice = keepPrice;
            }

            public String getCraftPrice() {
                return craftPrice;
            }

            public void setCraftPrice(String craftPrice) {
                this.craftPrice = craftPrice;
            }

            public String getItemRealPrice() {
                return itemRealPrice;
            }

            public void setItemRealPrice(String itemRealPrice) {
                this.itemRealPrice = itemRealPrice;
            }

            public String getHasDiscount() {
                return hasDiscount;
            }

            public void setHasDiscount(String hasDiscount) {
                this.hasDiscount = hasDiscount;
            }

            public String getItemDiscount() {
                return itemDiscount;
            }

            public void setItemDiscount(String itemDiscount) {
                this.itemDiscount = itemDiscount;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getProblem() {
                return problem;
            }

            public void setProblem(String problem) {
                this.problem = problem;
            }

            public String getForecast() {
                return forecast;
            }

            public void setForecast(String forecast) {
                this.forecast = forecast;
            }

            public String getTakeTime() {
                return takeTime;
            }

            public void setTakeTime(String takeTime) {
                this.takeTime = takeTime;
            }

            public String getCraftDes() {
                return craftDes;
            }

            public void setCraftDes(String craftDes) {
                this.craftDes = craftDes;
            }

            public String getCleanSn() {
                return cleanSn;
            }

            public void setCleanSn(String cleanSn) {
                this.cleanSn = cleanSn;
            }

            public String getPutSn() {
                return putSn;
            }

            public void setPutSn(String putSn) {
                this.putSn = putSn;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public List<String> getItemImages() {
                return itemImages;
            }

            public void setItemImages(List<String> itemImages) {
                this.itemImages = itemImages;
            }
        }
    }
}
