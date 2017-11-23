package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/25.
 */

public class OrderCleanEntities {
    @SerializedName("count")
    private int count;
    @SerializedName("data")
    private List<CleanData> datas;
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("dataCount")
    private String dataCount;

    public String getDataCount() {
        return dataCount;
    }

    public void setDataCount(String dataCount) {
        this.dataCount = dataCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CleanData> getDatas() {
        return datas;
    }

    public void setDatas(List<CleanData> datas) {
        this.datas = datas;
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

    public class CleanData{

        @SerializedName("ad_id")
        private String adId;
        @SerializedName("adr")
        private String adr;
        @SerializedName("amount")
        private String amount;
        @SerializedName("coupon_price")
        private String couponPrice;
        @SerializedName("create_time")
        private String createTime;
        @SerializedName("freight")
        private String freight;
        @SerializedName("hedging")
        private String hedging;
        @SerializedName("id")
        private String id;
        @SerializedName("item")
        private List<CleanItem> cleanItems;
        @SerializedName("name")
        private String name;
        @SerializedName("num")
        private String num;
        @SerializedName("ordersn")
        private String ordersn;
        @SerializedName("pay_state")
        private int payState;
        @SerializedName("phone")
        private String phone;
        @SerializedName("special")
        private String special;
        @SerializedName("sum")
        private String sum;
        @SerializedName("time")
        private String time;
        @SerializedName("up_time")
        private String upTime;
        @SerializedName("update_time")
        private String updateTime;

        public boolean isOpen = false;

        @SerializedName("pay_amount")
        private String payAmount;

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getAdId() {
            return adId;
        }

        public void setAdId(String adId) {
            this.adId = adId;
        }

        public String getAdr() {
            return adr;
        }

        public void setAdr(String adr) {
            this.adr = adr;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(String couponPrice) {
            this.couponPrice = couponPrice;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getHedging() {
            return hedging;
        }

        public void setHedging(String hedging) {
            this.hedging = hedging;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<CleanItem> getCleanItems() {
            return cleanItems;
        }

        public void setCleanItems(List<CleanItem> cleanItems) {
            this.cleanItems = cleanItems;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public int getPayState() {
            return payState;
        }

        public void setPayState(int payState) {
            this.payState = payState;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSpecial() {
            return special;
        }

        public void setSpecial(String special) {
            this.special = special;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUpTime() {
            return upTime;
        }

        public void setUpTime(String upTime) {
            this.upTime = upTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public class CleanItem{
            @SerializedName("color")
            private String color;

            @SerializedName("create_time")
            private String createTime;

            @SerializedName("g_name")
            private String gName;

            @SerializedName("hedging")
            private String hedging;

            @SerializedName("id")
            private String id;

            @SerializedName("item_note")
            private String itemNote;

            @SerializedName("item_title")
            private String itemTitle;

            @SerializedName("itemid")
            private String itemId;

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

            public String getgName() {
                return gName;
            }

            public void setgName(String gName) {
                this.gName = gName;
            }

            public String getHedging() {
                return hedging;
            }

            public void setHedging(String hedging) {
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
}
