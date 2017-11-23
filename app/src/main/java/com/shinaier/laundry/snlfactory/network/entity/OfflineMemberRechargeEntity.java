package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/4.
 */

public class OfflineMemberRechargeEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OfflineMemberRechargeData data;

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

    public OfflineMemberRechargeData getData() {
        return data;
    }

    public void setData(OfflineMemberRechargeData data) {
        this.data = data;
    }

    public class OfflineMemberRechargeData{
        @SerializedName("member")
        private OfflineMember member;
        @SerializedName("merchantCardsRule")
        private List<MerchantCardsRule> merchantCardsRules;

        public OfflineMember getMember() {
            return member;
        }

        public void setMember(OfflineMember member) {
            this.member = member;
        }

        public List<MerchantCardsRule> getMerchantCardsRules() {
            return merchantCardsRules;
        }

        public void setMerchantCardsRules(List<MerchantCardsRule> merchantCardsRules) {
            this.merchantCardsRules = merchantCardsRules;
        }

        public class OfflineMember{
            @SerializedName("ucode")
            private String ucode;
            @SerializedName("mobile_number")
            private String mobileNumber;
            @SerializedName("username")
            private String userName;
            @SerializedName("card_name")
            private String cardName;
            @SerializedName("balance")
            private String balance;

            public String getUcode() {
                return ucode;
            }

            public void setUcode(String ucode) {
                this.ucode = ucode;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getCardName() {
                return cardName;
            }

            public void setCardName(String cardName) {
                this.cardName = cardName;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }
        }

        public class MerchantCardsRule{
            @SerializedName("card_name")
            private String cardName;
            @SerializedName("discount")
            private int discount;
            @SerializedName("price")
            private int price;

            public String getCardName() {
                return cardName;
            }

            public void setCardName(String cardName) {
                this.cardName = cardName;
            }

            public int getDiscount() {
                return discount;
            }

            public void setDiscount(int discount) {
                this.discount = discount;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }
    }
}
