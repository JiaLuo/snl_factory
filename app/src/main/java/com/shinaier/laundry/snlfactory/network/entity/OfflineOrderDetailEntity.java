package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/1.
 */

public class OfflineOrderDetailEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OfflineOrderDetailDatas datas;

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

    public OfflineOrderDetailDatas getDatas() {
        return datas;
    }

    public void setDatas(OfflineOrderDetailDatas datas) {
        this.datas = datas;
    }

    public class OfflineOrderDetailDatas{
        @SerializedName("id")
        private String id;
        @SerializedName("ordersn")
        private String ordersn;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("amount")
        private String amount;
        @SerializedName("pay_state")
        private String payState;
        @SerializedName("create_time")
        private String createTime;
        @SerializedName("update_time")
        private String updateTime;
        @SerializedName("username")
        private String userName;
        @SerializedName("items")
        private List<OfflineOrderDetailItems> items;
        @SerializedName("items_count")
        private String itemsCount;
        @SerializedName("total_amount")
        private String totalAmount;

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getItemsCount() {
            return itemsCount;
        }

        public void setItemsCount(String itemsCount) {
            this.itemsCount = itemsCount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public List<OfflineOrderDetailItems> getItems() {
            return items;
        }

        public void setItems(List<OfflineOrderDetailItems> items) {
            this.items = items;
        }

        public class OfflineOrderDetailItems{
            @SerializedName("item_id")
            private String itemId;
            @SerializedName("price")
            private String price;
            @SerializedName("item_note")
            private String itemNote;
            @SerializedName("color")
            private String color;
            @SerializedName("g_name")
            private String gName;
            @SerializedName("url")
            private String url;
            @SerializedName("images")
            private List<String> imgs;
            @SerializedName("number")
            private String number;
            @SerializedName("special")
            private String special;
            @SerializedName("hedging")
            private double hedging;
            @SerializedName("special_comment")
            private String specialComment;


            public String getSpecialComment() {
                return specialComment;
            }

            public void setSpecialComment(String specialComment) {
                this.specialComment = specialComment;
            }

            public double getHedging() {
                return hedging;
            }

            public void setHedging(double hedging) {
                this.hedging = hedging;
            }

            public String getSpecial() {
                return special;
            }

            public void setSpecial(String special) {
                this.special = special;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getItemId() {
                return itemId;
            }

            public void setItemId(String itemId) {
                this.itemId = itemId;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getItemNote() {
                return itemNote;
            }

            public void setItemNote(String itemNote) {
                this.itemNote = itemNote;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getgName() {
                return gName;
            }

            public void setgName(String gName) {
                this.gName = gName;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public List<String> getImgs() {
                return imgs;
            }

            public void setImgs(List<String> imgs) {
                this.imgs = imgs;
            }
        }
    }

}
