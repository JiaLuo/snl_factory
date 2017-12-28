package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 新增会员 的商户卡实体
 * Created by 张家洛 on 2017/12/27.
 */

public class MerchantCardListEntities {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<MerchantCardListResult> result;

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

    public List<MerchantCardListResult> getResult() {
        return result;
    }

    public void setResult(List<MerchantCardListResult> result) {
        this.result = result;
    }

    public class MerchantCardListResult{
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
}
