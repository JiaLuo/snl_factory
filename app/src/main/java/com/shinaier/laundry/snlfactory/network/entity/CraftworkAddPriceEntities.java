package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/1.
 */

public class CraftworkAddPriceEntities {
    @SerializedName("amount")
    private String amount;
    @SerializedName("freight")
    private String freight;
    @SerializedName("fuwu")
    private String fuwu;
    @SerializedName("list")
    private List<AddPriceItem> priceItemList;
    @SerializedName("total")
    private String total;
    @SerializedName("total_num")
    private String totalNum;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getFuwu() {
        return fuwu;
    }

    public void setFuwu(String fuwu) {
        this.fuwu = fuwu;
    }

    public List<AddPriceItem> getPriceItemList() {
        return priceItemList;
    }

    public void setPriceItemList(List<AddPriceItem> priceItemList) {
        this.priceItemList = priceItemList;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public class AddPriceItem{
        @SerializedName("color")
        private String color;
        @SerializedName("create_time")
        private String createTime;
        @SerializedName("hedging")
        private double hedging;
        @SerializedName("id")
        private String id;
        @SerializedName("item_note")
        private String itemNote;
        @SerializedName("item_title")
        private String itemTitle;
        @SerializedName("itemid")
        private String itemId;
        @SerializedName("name")
        private String name;
        @SerializedName("number")
        private String number;
        @SerializedName("orderid")
        private String orderId;
        @SerializedName("price")
        private String price;
        @SerializedName("special")
        private String special;
        @SerializedName("special_comment")
        private String specialComment;
        @SerializedName("type")
        private String type;
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public double getHedging() {
            return hedging;
        }

        public void setHedging(double hedging) {
            this.hedging = hedging;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getItemNote() {
            return itemNote;
        }

        public void setItemNote(String itemNote) {
            this.itemNote = itemNote;
        }

        public String getItemTitle() {
            return itemTitle;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSpecial() {
            return special;
        }

        public void setSpecial(String special) {
            this.special = special;
        }

        public String getSpecialComment() {
            return specialComment;
        }

        public void setSpecialComment(String specialComment) {
            this.specialComment = specialComment;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
