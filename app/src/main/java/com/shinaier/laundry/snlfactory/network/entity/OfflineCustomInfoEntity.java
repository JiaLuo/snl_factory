package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/7/19.
 */

public class OfflineCustomInfoEntity{
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OfflineCustomInfoDatas datas;

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

    public OfflineCustomInfoDatas getDatas() {
        return datas;
    }

    public void setDatas(OfflineCustomInfoDatas datas) {
        this.datas = datas;
    }

    public class OfflineCustomInfoDatas{
        @SerializedName("id")
        private String id;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("username")
        private String userName;
        @SerializedName("card_number")
        private String cardNumber;
        @SerializedName("card_name")
        private String cardName;
        @SerializedName("balance")
        private String balance;
        @SerializedName("join_time")
        private String joinTime;
        @SerializedName("orders")
        private List<String> orders;
        @SerializedName("platform_card")
        private OfflineCustomInfoPlatformCard platformCard;

        public OfflineCustomInfoPlatformCard getPlatformCard() {
            return platformCard;
        }

        public void setPlatformCard(OfflineCustomInfoPlatformCard platformCard) {
            this.platformCard = platformCard;
        }

        public class OfflineCustomInfoPlatformCard{
            @SerializedName("id")
            private String id;
            @SerializedName("uid")
            private String uid;
            @SerializedName("mid")
            private String mid;
            @SerializedName("card_number")
            private String cardNumber;
            @SerializedName("card_manager")
            private String cardManager;
            @SerializedName("mobile_number")
            private String mobileNumber;
            @SerializedName("card_type")
            private String cardType;
            @SerializedName("card_sum")
            private String cardSum;
            @SerializedName("card_state")
            private String cardState;
            @SerializedName("card_discount")
            private String cardDiscount;
            @SerializedName("bought_time")
            private String boughtTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getCardNumber() {
                return cardNumber;
            }

            public void setCardNumber(String cardNumber) {
                this.cardNumber = cardNumber;
            }

            public String getCardManager() {
                return cardManager;
            }

            public void setCardManager(String cardManager) {
                this.cardManager = cardManager;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getCardType() {
                return cardType;
            }

            public void setCardType(String cardType) {
                this.cardType = cardType;
            }

            public String getCardSum() {
                return cardSum;
            }

            public void setCardSum(String cardSum) {
                this.cardSum = cardSum;
            }

            public String getCardState() {
                return cardState;
            }

            public void setCardState(String cardState) {
                this.cardState = cardState;
            }

            public String getCardDiscount() {
                return cardDiscount;
            }

            public void setCardDiscount(String cardDiscount) {
                this.cardDiscount = cardDiscount;
            }

            public String getBoughtTime() {
                return boughtTime;
            }

            public void setBoughtTime(String boughtTime) {
                this.boughtTime = boughtTime;
            }
        }

        public String getCardName() {
            return cardName;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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

        public String getJoinTime() {
            return joinTime;
        }

        public void setJoinTime(String joinTime) {
            this.joinTime = joinTime;
        }

        public List<String> getOrders() {
            return orders;
        }

        public void setOrders(List<String> orders) {
            this.orders = orders;
        }
    }
}
