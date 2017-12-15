package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/12/14.
 */

public class EditCommodityEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private EditCommodityResult result;

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

    public EditCommodityResult getResult() {
        return result;
    }

    public void setResult(EditCommodityResult result) {
        this.result = result;
    }

    public class EditCommodityResult{
        @SerializedName("id")
        private String id;
        @SerializedName("item_name")
        private String itemName;
        @SerializedName("item_price")
        private String itemPrice;
        @SerializedName("item_cycle")
        private String itemCycle;
        @SerializedName("item_discount")
        private String itemDiscount;
        @SerializedName("has_discount")
        private String hasDiscount;
        @SerializedName("item_forecast")
        private String itemForecast;

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

        public String getItemCycle() {
            return itemCycle;
        }

        public void setItemCycle(String itemCycle) {
            this.itemCycle = itemCycle;
        }

        public String getItemDiscount() {
            return itemDiscount;
        }

        public void setItemDiscount(String itemDiscount) {
            this.itemDiscount = itemDiscount;
        }

        public String getHasDiscount() {
            return hasDiscount;
        }

        public void setHasDiscount(String hasDiscount) {
            this.hasDiscount = hasDiscount;
        }

        public String getItemForecast() {
            return itemForecast;
        }

        public void setItemForecast(String itemForecast) {
            this.itemForecast = itemForecast;
        }
    }
}
