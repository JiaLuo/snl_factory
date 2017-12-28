package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/7/19.
 */

public class OfflineCustomInfoEntity{
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private OfflineCustomInfoResult result;

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

    public OfflineCustomInfoResult getResult() {
        return result;
    }

    public void setResult(OfflineCustomInfoResult result) {
        this.result = result;
    }

    public class OfflineCustomInfoResult{
        @SerializedName("id")
        private String id;
        @SerializedName("uname")
        private String uName;
        @SerializedName("umobile")
        private String uMobile;
        @SerializedName("last_time")
        private String lastTime;
        @SerializedName("orders")
        private List<String> orders;
        @SerializedName("platform_card")
        private OfflineCustomInfoPlatformCard platformCard;
        @SerializedName("merchant_card")
        private OfflineCustomInfoMerchantCard merchantCard;

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

        public String getuMobile() {
            return uMobile;
        }

        public void setuMobile(String uMobile) {
            this.uMobile = uMobile;
        }

        public String getLastTime() {
            return lastTime;
        }

        public void setLastTime(String lastTime) {
            this.lastTime = lastTime;
        }

        public List<String> getOrders() {
            return orders;
        }

        public void setOrders(List<String> orders) {
            this.orders = orders;
        }

        public OfflineCustomInfoPlatformCard getPlatformCard() {
            return platformCard;
        }

        public void setPlatformCard(OfflineCustomInfoPlatformCard platformCard) {
            this.platformCard = platformCard;
        }

        public OfflineCustomInfoMerchantCard getMerchantCard() {
            return merchantCard;
        }

        public void setMerchantCard(OfflineCustomInfoMerchantCard merchantCard) {
            this.merchantCard = merchantCard;
        }
        //        @SerializedName("card_number")
//        private String cardNumber;
//        @SerializedName("card_name")
//        private String cardName;
//        @SerializedName("balance")
//        private String balance;
//        @SerializedName("join_time")
//        private String joinTime;

        public class OfflineCustomInfoMerchantCard{
            @SerializedName("cname")
            private String cName;
            @SerializedName("cbalance")
            private String cBalance;

            public String getcName() {
                return cName;
            }

            public void setcName(String cName) {
                this.cName = cName;
            }

            public String getcBalance() {
                return cBalance;
            }

            public void setcBalance(String cBalance) {
                this.cBalance = cBalance;
            }
        }


        public class OfflineCustomInfoPlatformCard{
            @SerializedName("cname")
            private String cName;
            @SerializedName("cbalance")
            private String cBalance;

            public String getcName() {
                return cName;
            }

            public void setcName(String cName) {
                this.cName = cName;
            }

            public String getcBalance() {
                return cBalance;
            }

            public void setcBalance(String cBalance) {
                this.cBalance = cBalance;
            }
            //            @SerializedName("id")
//            private String id;
//            @SerializedName("uid")
//            private String uid;
//            @SerializedName("mid")
//            private String mid;
//            @SerializedName("card_number")
//            private String cardNumber;
//            @SerializedName("card_manager")
//            private String cardManager;
//            @SerializedName("mobile_number")
//            private String mobileNumber;
//            @SerializedName("card_type")
//            private String cardType;
//            @SerializedName("card_sum")
//            private String cardSum;
//            @SerializedName("card_state")
//            private String cardState;
//            @SerializedName("card_discount")
//            private String cardDiscount;
//            @SerializedName("bought_time")
//            private String boughtTime;
        }
    }
}
