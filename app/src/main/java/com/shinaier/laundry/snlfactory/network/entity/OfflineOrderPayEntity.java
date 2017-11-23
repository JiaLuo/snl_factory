package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/8/2.
 */

public class OfflineOrderPayEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OfflineOrderPayDatas datas;

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

    public OfflineOrderPayDatas getDatas() {
        return datas;
    }

    public void setDatas(OfflineOrderPayDatas datas) {
        this.datas = datas;
    }

    public class OfflineOrderPayDatas{
        @SerializedName("order")
        private OfflineOrderPayOrder offlineOrderPayOrder;
        @SerializedName("platformCard")
        private OfflineOrderPayPlatformCard offlineOrderPayPlatformCard;
        @SerializedName("merchantCard")
        private OfflineOrderPayMerchantCard offlineOrderPayMerchantCard;

        public OfflineOrderPayOrder getOfflineOrderPayOrder() {
            return offlineOrderPayOrder;
        }

        public void setOfflineOrderPayOrder(OfflineOrderPayOrder offlineOrderPayOrder) {
            this.offlineOrderPayOrder = offlineOrderPayOrder;
        }

        public OfflineOrderPayPlatformCard getOfflineOrderPayPlatformCard() {
            return offlineOrderPayPlatformCard;
        }

        public void setOfflineOrderPayPlatformCard(OfflineOrderPayPlatformCard offlineOrderPayPlatformCard) {
            this.offlineOrderPayPlatformCard = offlineOrderPayPlatformCard;
        }

        public OfflineOrderPayMerchantCard getOfflineOrderPayMerchantCard() {
            return offlineOrderPayMerchantCard;
        }

        public void setOfflineOrderPayMerchantCard(OfflineOrderPayMerchantCard offlineOrderPayMerchantCard) {
            this.offlineOrderPayMerchantCard = offlineOrderPayMerchantCard;
        }

        public class OfflineOrderPayOrder{
            @SerializedName("id")
            private String id;
            @SerializedName("userid")
            private String userid;
            @SerializedName("amount")
            private String amount;
            @SerializedName("pay_state")
            private String payState;
            @SerializedName("freight")
            private String freight;
            @SerializedName("special")
            private String special;
            @SerializedName("reduce_price")
            private String reducePrice;
            @SerializedName("total_amount")
            private double totalAmount; //应收
            @SerializedName("mobile_number")
            private String mobileNumber;
            @SerializedName("invite_code")
            private String inviteCode;

            public String getInviteCode() {
                return inviteCode;
            }

            public void setInviteCode(String inviteCode) {
                this.inviteCode = inviteCode;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getPayState() {
                return payState;
            }

            public void setPayState(String payState) {
                this.payState = payState;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getSpecial() {
                return special;
            }

            public void setSpecial(String special) {
                this.special = special;
            }

            public String getReducePrice() {
                return reducePrice;
            }

            public void setReducePrice(String reducePrice) {
                this.reducePrice = reducePrice;
            }

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }
        }

        public class OfflineOrderPayPlatformCard{
            @SerializedName("id")
            private String id;
            @SerializedName("card_sum")
            private double cardSum;
            @SerializedName("card_number")
            private String cardNumber;
            @SerializedName("card_discount")
            private String cardDiscount;
            @SerializedName("card_exist")
            private int cardExist; //1 存在  0 不存在
            @SerializedName("mobile_number")
            private String mobileNumber;

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public double getCardSum() {
                return cardSum;
            }

            public void setCardSum(double cardSum) {
                this.cardSum = cardSum;
            }

            public String getCardNumber() {
                return cardNumber;
            }

            public void setCardNumber(String cardNumber) {
                this.cardNumber = cardNumber;
            }

            public String getCardDiscount() {
                return cardDiscount;
            }

            public void setCardDiscount(String cardDiscount) {
                this.cardDiscount = cardDiscount;
            }

            public int getCardExist() {
                return cardExist;
            }

            public void setCardExist(int cardExist) {
                this.cardExist = cardExist;
            }
        }

        public class OfflineOrderPayMerchantCard{
            @SerializedName("card_number")
            private String cardNumber;
            @SerializedName("balance")
            private String balance;
            @SerializedName("discount")
            private double discount;
            @SerializedName("card_exist")
            private int cardExist;
            @SerializedName("mobile_number")
            private String mobileNumber;

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getCardNumber() {
                return cardNumber;
            }

            public void setCardNumber(String cardNumber) {
                this.cardNumber = cardNumber;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public double getDiscount() {
                return discount;
            }

            public void setDiscount(double discount) {
                this.discount = discount;
            }

            public int getCardExist() {
                return cardExist;
            }

            public void setCardExist(int cardExist) {
                this.cardExist = cardExist;
            }
        }
    }
}
