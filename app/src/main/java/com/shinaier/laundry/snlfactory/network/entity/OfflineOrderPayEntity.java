package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/2.
 */

public class OfflineOrderPayEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private OfflineOrderPayResult result;

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

    public OfflineOrderPayResult getResult() {
        return result;
    }

    public void setResult(OfflineOrderPayResult result) {
        this.result = result;
    }

    public class OfflineOrderPayResult{
        @SerializedName("pay_amount")
        private double payAmount;
        @SerializedName("keep_price")
        private double keepPrice;
        @SerializedName("freight_price")
        private String freightPrice;
        @SerializedName("craft_price")
        private String craftPrice;
        @SerializedName("reduce_price")
        private double reducePrice;
        @SerializedName("amount")
        private String amount;
        @SerializedName("uid")
        private String uId;
        @SerializedName("total_amount")
        private double totalAmount;
        @SerializedName("platform")
        private OfflineOrderPayPlatform offlineOrderPayPlatform;
        @SerializedName("has_platform")
        private String hasPlatform;
        @SerializedName("merchant")
        private OfflineOrderPayMerchant offlineOrderPayMerchant;
        @SerializedName("has_merchant")
        private String hasMerchant;
        @SerializedName("umobile")
        private String uMobile;
        @SerializedName("items")
        private List<OfflineOrderPayItems> itemses;
        @SerializedName("master_phone")
        private String masterPhone;

        public String getMasterPhone() {
            return masterPhone;
        }

        public void setMasterPhone(String masterPhone) {
            this.masterPhone = masterPhone;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public double getKeepPrice() {
            return keepPrice;
        }

        public void setKeepPrice(double keepPrice) {
            this.keepPrice = keepPrice;
        }

        public String getFreightPrice() {
            return freightPrice;
        }

        public void setFreightPrice(String freightPrice) {
            this.freightPrice = freightPrice;
        }

        public String getCraftPrice() {
            return craftPrice;
        }

        public void setCraftPrice(String craftPrice) {
            this.craftPrice = craftPrice;
        }

        public double getReducePrice() {
            return reducePrice;
        }

        public void setReducePrice(double reducePrice) {
            this.reducePrice = reducePrice;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getuId() {
            return uId;
        }

        public void setuId(String uId) {
            this.uId = uId;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public OfflineOrderPayPlatform getOfflineOrderPayPlatform() {
            return offlineOrderPayPlatform;
        }

        public void setOfflineOrderPayPlatform(OfflineOrderPayPlatform offlineOrderPayPlatform) {
            this.offlineOrderPayPlatform = offlineOrderPayPlatform;
        }

        public String getHasPlatform() {
            return hasPlatform;
        }

        public void setHasPlatform(String hasPlatform) {
            this.hasPlatform = hasPlatform;
        }

        public OfflineOrderPayMerchant getOfflineOrderPayMerchant() {
            return offlineOrderPayMerchant;
        }

        public void setOfflineOrderPayMerchant(OfflineOrderPayMerchant offlineOrderPayMerchant) {
            this.offlineOrderPayMerchant = offlineOrderPayMerchant;
        }

        public String getHasMerchant() {
            return hasMerchant;
        }

        public void setHasMerchant(String hasMerchant) {
            this.hasMerchant = hasMerchant;
        }

        public String getuMobile() {
            return uMobile;
        }

        public void setuMobile(String uMobile) {
            this.uMobile = uMobile;
        }

        public List<OfflineOrderPayItems> getItemses() {
            return itemses;
        }

        public void setItemses(List<OfflineOrderPayItems> itemses) {
            this.itemses = itemses;
        }

        public class OfflineOrderPayItems{
            @SerializedName("id")
            private String id;
            @SerializedName("item_price")
            private double itemPrice;
            @SerializedName("item_real_price")
            private double itemRealPrice;
            @SerializedName("has_discount")
            private String hasDiscount;
            @SerializedName("item_discount")
            private double itemDiscount;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public double getItemPrice() {
                return itemPrice;
            }

            public void setItemPrice(double itemPrice) {
                this.itemPrice = itemPrice;
            }

            public double getItemRealPrice() {
                return itemRealPrice;
            }

            public void setItemRealPrice(double itemRealPrice) {
                this.itemRealPrice = itemRealPrice;
            }

            public String getHasDiscount() {
                return hasDiscount;
            }

            public void setHasDiscount(String hasDiscount) {
                this.hasDiscount = hasDiscount;
            }

            public double getItemDiscount() {
                return itemDiscount;
            }

            public void setItemDiscount(double itemDiscount) {
                this.itemDiscount = itemDiscount;
            }
        }

        public class OfflineOrderPayPlatform{
            @SerializedName("cdiscount")
            private double cDiscount;
            @SerializedName("cbalance")
            private double cBalance;
            @SerializedName("cname")
            private String cName;

            public double getcDiscount() {
                return cDiscount;
            }

            public void setcDiscount(double cDiscount) {
                this.cDiscount = cDiscount;
            }

            public double getcBalance() {
                return cBalance;
            }

            public void setcBalance(double cBalance) {
                this.cBalance = cBalance;
            }

            public String getcName() {
                return cName;
            }

            public void setcName(String cName) {
                this.cName = cName;
            }
        }

        public class OfflineOrderPayMerchant{
            @SerializedName("cdiscount")
            private double cDiscount;
            @SerializedName("cbalance")
            private double cBalance;
            @SerializedName("cname")
            private String cName;

            public double getcDiscount() {
                return cDiscount;
            }

            public void setcDiscount(double cDiscount) {
                this.cDiscount = cDiscount;
            }

            public double getcBalance() {
                return cBalance;
            }

            public void setcBalance(double cBalance) {
                this.cBalance = cBalance;
            }

            public String getcName() {
                return cName;
            }

            public void setcName(String cName) {
                this.cName = cName;
            }
        }
    }
}
