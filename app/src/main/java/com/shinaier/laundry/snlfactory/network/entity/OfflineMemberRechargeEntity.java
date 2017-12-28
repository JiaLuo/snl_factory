package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/4.
 */

public class OfflineMemberRechargeEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private OfflineMemberRechargeResult result;

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

    public OfflineMemberRechargeResult getResult() {
        return result;
    }

    public void setResult(OfflineMemberRechargeResult result) {
        this.result = result;
    }

    public class OfflineMemberRechargeResult{
        @SerializedName("id")
        private String id;
        @SerializedName("uname")
        private String uName;
        @SerializedName("umobile")
        private String uMobile;
        @SerializedName("cname")
        private String cName;
        @SerializedName("cbalance")
        private String cBalance;
        @SerializedName("cards")
        private List<OfflineMemberRechargeCards> cardses;

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

        public List<OfflineMemberRechargeCards> getCardses() {
            return cardses;
        }

        public void setCardses(List<OfflineMemberRechargeCards> cardses) {
            this.cardses = cardses;
        }

        public class OfflineMemberRechargeCards{
            @SerializedName("id")
            private String id;
            @SerializedName("card_name")
            private String cardName;
            @SerializedName("discount")
            private double discount;
            @SerializedName("price")
            private double price;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCardName() {
                return cardName;
            }

            public void setCardName(String cardName) {
                this.cardName = cardName;
            }

            public double getDiscount() {
                return discount;
            }

            public void setDiscount(double discount) {
                this.discount = discount;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }
    }
}
