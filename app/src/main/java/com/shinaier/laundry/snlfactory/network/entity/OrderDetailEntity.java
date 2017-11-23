package com.shinaier.laundry.snlfactory.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/7.
 */

public class OrderDetailEntity {
    @SerializedName("adr")
    private String adr;
    @SerializedName("adresinfo")
    private String adresinfo;
    @SerializedName("amount")
    private String amount;
    @SerializedName("content")
    private String content;
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
    private List<OrderDetailItem> orderDetailItemList;
    @SerializedName("item_sum")
    private String itemSum;
    @SerializedName("name")
    private String name;
    @SerializedName("ordersn")
    private String ordersn;
    @SerializedName("pay_amount")
    private String payAmount;
    @SerializedName("phone")
    private String phone;
    @SerializedName("pic_total")
    private String picTotal;
    @SerializedName("special")
    private String special;
    @SerializedName("state")
    private int state;
    @SerializedName("sum")
    private String sum;
    @SerializedName("time")
    private String time;

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getAdresinfo() {
        return adresinfo;
    }

    public void setAdresinfo(String adresinfo) {
        this.adresinfo = adresinfo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<OrderDetailItem> getOrderDetailItemList() {
        return orderDetailItemList;
    }

    public void setOrderDetailItemList(List<OrderDetailItem> orderDetailItemList) {
        this.orderDetailItemList = orderDetailItemList;
    }

    public String getItemSum() {
        return itemSum;
    }

    public void setItemSum(String itemSum) {
        this.itemSum = itemSum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicTotal() {
        return picTotal;
    }

    public void setPicTotal(String picTotal) {
        this.picTotal = picTotal;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public static class OrderDetailItem{
        @SerializedName("color")
        private String color;
        @SerializedName("g_name")
        private String gName;
        @SerializedName("hedging")
        private double hedging;
        @SerializedName("id")
        private String id;
        @SerializedName("img")
        private List<Img> imgs;
        @SerializedName("img_count")
        private String imgCount;
        @SerializedName("item_note")
        private String itemNote;
        @SerializedName("number")
        private String number;
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

        public List<Img> getImgs() {
            return imgs;
        }

        public void setImgs(List<Img> imgs) {
            this.imgs = imgs;
        }

        public String getImgCount() {
            return imgCount;
        }

        public void setImgCount(String imgCount) {
            this.imgCount = imgCount;
        }

        public String getItemNote() {
            return itemNote;
        }

        public void setItemNote(String itemNote) {
            this.itemNote = itemNote;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static class Img implements Parcelable {
            @SerializedName("img")
            private String img;

            protected Img(Parcel in) {
                img = in.readString();
            }

            public static final Creator<Img> CREATOR = new Creator<Img>() {
                @Override
                public Img createFromParcel(Parcel in) {
                    return new Img(in);
                }

                @Override
                public Img[] newArray(int size) {
                    return new Img[size];
                }
            };

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(img);
            }
        }
    }
}
