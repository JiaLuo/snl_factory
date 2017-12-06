package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/18.
 */

public class StoreInfoEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private StoreInfoResult result;

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

    public StoreInfoResult getResult() {
        return result;
    }

    public void setResult(StoreInfoResult result) {
        this.result = result;
    }

    public class StoreInfoResult{
        @SerializedName("merchant")
        private StoreInfoMerchant merchant;
        @SerializedName("mcards")
        private List<StoreInfoCards> cards;

        public StoreInfoMerchant getMerchant() {
            return merchant;
        }

        public void setMerchant(StoreInfoMerchant merchant) {
            this.merchant = merchant;
        }

        public List<StoreInfoCards> getCards() {
            return cards;
        }

        public void setCards(List<StoreInfoCards> cards) {
            this.cards = cards;
        }

        public class StoreInfoCards{
            @SerializedName("id")
            private String id;
            @SerializedName("card_name")
            private String cardName;
            @SerializedName("discount")
            private String discount;
            @SerializedName("price")
            private String price;

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

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
        public class StoreInfoMerchant{
            @SerializedName("id")
            private String id;
            @SerializedName("mname")
            private String mName;
            @SerializedName("phone_number")
            private String phoneNumber;
            @SerializedName("mrange")
            private String mRange;
            @SerializedName("freight_price")
            private String freightPrice;
            @SerializedName("freight_free_num")
            private String freightFreeNum;
            @SerializedName("freight_free_amount")
            private String freightFreeAmount;
            @SerializedName("maddress")
            private String mAddress;
            @SerializedName("mdesc")
            private String mDesc;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getmName() {
                return mName;
            }

            public void setmName(String mName) {
                this.mName = mName;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getmRange() {
                return mRange;
            }

            public void setmRange(String mRange) {
                this.mRange = mRange;
            }

            public String getFreightPrice() {
                return freightPrice;
            }

            public void setFreightPrice(String freightPrice) {
                this.freightPrice = freightPrice;
            }

            public String getFreightFreeNum() {
                return freightFreeNum;
            }

            public void setFreightFreeNum(String freightFreeNum) {
                this.freightFreeNum = freightFreeNum;
            }

            public String getFreightFreeAmount() {
                return freightFreeAmount;
            }

            public void setFreightFreeAmount(String freightFreeAmount) {
                this.freightFreeAmount = freightFreeAmount;
            }

            public String getmAddress() {
                return mAddress;
            }

            public void setmAddress(String mAddress) {
                this.mAddress = mAddress;
            }

            public String getmDesc() {
                return mDesc;
            }

            public void setmDesc(String mDesc) {
                this.mDesc = mDesc;
            }
        }
    }
}
