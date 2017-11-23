package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/7/25.
 */

public class TakeClothesEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("page_count")
    private int pageCount;
    @SerializedName("data")
    private List<TakeClothesData> data;
    @SerializedName("count")
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

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

    public List<TakeClothesData> getData() {
        return data;
    }

    public void setData(List<TakeClothesData> data) {
        this.data = data;
    }

    public class TakeClothesData{
        @SerializedName("id")
        private String id;
        @SerializedName("ordersn")
        private String ordersn;
        @SerializedName("pay_state") // 0 是未支付 1 支付
        private String payState;
        @SerializedName("update_time")
        private String updateTime;
        @SerializedName("username")
        private String userName;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("items")
        private List<TakeClothesItems> items;
        @SerializedName("items_count")
        private String itemsCount;
        @SerializedName("special")
        private String special;
        @SerializedName("hedging")
        private String hedging;
        @SerializedName("total_amount")
        private String totalAmount;

        public boolean isOpen = false;

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

        public String getPayState() {
            return payState;
        }

        public void setPayState(String payState) {
            this.payState = payState;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public List<TakeClothesItems> getItems() {
            return items;
        }

        public void setItems(List<TakeClothesItems> items) {
            this.items = items;
        }

        public String getItemsCount() {
            return itemsCount;
        }

        public void setItemsCount(String itemsCount) {
            this.itemsCount = itemsCount;
        }

        public String getSpecial() {
            return special;
        }

        public void setSpecial(String special) {
            this.special = special;
        }

        public String getHedging() {
            return hedging;
        }

        public void setHedging(String hedging) {
            this.hedging = hedging;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public class TakeClothesItems{
            @SerializedName("item_id")
            private String itemId;
            @SerializedName("price")
            private String price;
            @SerializedName("status")
            private String status;
            @SerializedName("put_number")
            private String putNumber;
            @SerializedName("name")
            private String name;
            @SerializedName("number")
            private String number;

            public boolean isSelect = false;

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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getPutNumber() {
                return putNumber;
            }

            public void setPutNumber(String putNumber) {
                this.putNumber = putNumber;
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
        }
    }
}
