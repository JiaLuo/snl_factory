package com.shinaier.laundry.snlfactory.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/1.
 */

public class CraftworkAddPriceEntities {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private CraftworkAddPriceResult result;

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

    public CraftworkAddPriceResult getResult() {
        return result;
    }

    public void setResult(CraftworkAddPriceResult result) {
        this.result = result;
    }

    public static class CraftworkAddPriceResult implements Parcelable {
        @SerializedName("amount")
        private String amount;
        @SerializedName("freight_price")
        private String freightPrice;
        @SerializedName("craft_price")
        private String craftPrice;
        @SerializedName("keep_price")
        private String keepPrice;
        @SerializedName("is_online")
        private String isOnline;
        @SerializedName("total_amount")
        private String totalAmount;
        @SerializedName("item_count")
        private String itemCount;
        @SerializedName("items")
        private List<CraftworkAddPriceItems> itemses;

        protected CraftworkAddPriceResult(Parcel in) {
            amount = in.readString();
            freightPrice = in.readString();
            craftPrice = in.readString();
            keepPrice = in.readString();
            isOnline = in.readString();
            totalAmount = in.readString();
            itemCount = in.readString();
            itemses = in.createTypedArrayList(CraftworkAddPriceItems.CREATOR);
        }

        public static final Creator<CraftworkAddPriceResult> CREATOR = new Creator<CraftworkAddPriceResult>() {
            @Override
            public CraftworkAddPriceResult createFromParcel(Parcel in) {
                return new CraftworkAddPriceResult(in);
            }

            @Override
            public CraftworkAddPriceResult[] newArray(int size) {
                return new CraftworkAddPriceResult[size];
            }
        };

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getFreightPrice() {
            return freightPrice;
        }

        public void setFreightPrice(String freightPrice) {
            this.freightPrice = freightPrice;
        }

        public String getCraftPrice() {
            return craftPrice;
        }

        public void setCraftPrice(String craftPrice) {
            this.craftPrice = craftPrice;
        }

        public String getKeepPrice() {
            return keepPrice;
        }

        public void setKeepPrice(String keepPrice) {
            this.keepPrice = keepPrice;
        }

        public String getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(String isOnline) {
            this.isOnline = isOnline;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getItemCount() {
            return itemCount;
        }

        public void setItemCount(String itemCount) {
            this.itemCount = itemCount;
        }

        public List<CraftworkAddPriceItems> getItemses() {
            return itemses;
        }

        public void setItemses(List<CraftworkAddPriceItems> itemses) {
            this.itemses = itemses;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(amount);
            dest.writeString(freightPrice);
            dest.writeString(craftPrice);
            dest.writeString(keepPrice);
            dest.writeString(isOnline);
            dest.writeString(totalAmount);
            dest.writeString(itemCount);
            dest.writeTypedList(itemses);
        }

        public static class CraftworkAddPriceItems implements Parcelable {
            @SerializedName("id")
            private String id;
            @SerializedName("item_name")
            private String itemName;
            @SerializedName("item_price")
            private String itemPrice;
            @SerializedName("keep_price")
            private double keepPrice;
            @SerializedName("craft_price")
            private String craftPrice;
            @SerializedName("item_real_price")
            private String itemRealPrice;
            @SerializedName("has_discount")
            private String hasDiscount;
            @SerializedName("item_discount")
            private String itemDiscount;
            @SerializedName("color")
            private String color;
            @SerializedName("problem")
            private String problem;
            @SerializedName("forecast")
            private String forecast;
            @SerializedName("take_time")
            private String takeTime;
            @SerializedName("craft_des")
            private String craftDes;
            @SerializedName("clean_sn")
            private String cleanSn;
            @SerializedName("put_sn")
            private String putSn;
            @SerializedName("image")
            private String image;

            protected CraftworkAddPriceItems(Parcel in) {
                id = in.readString();
                itemName = in.readString();
                itemPrice = in.readString();
                keepPrice = in.readDouble();
                craftPrice = in.readString();
                itemRealPrice = in.readString();
                hasDiscount = in.readString();
                itemDiscount = in.readString();
                color = in.readString();
                problem = in.readString();
                forecast = in.readString();
                takeTime = in.readString();
                craftDes = in.readString();
                cleanSn = in.readString();
                putSn = in.readString();
                image = in.readString();
            }

            public static final Creator<CraftworkAddPriceItems> CREATOR = new Creator<CraftworkAddPriceItems>() {
                @Override
                public CraftworkAddPriceItems createFromParcel(Parcel in) {
                    return new CraftworkAddPriceItems(in);
                }

                @Override
                public CraftworkAddPriceItems[] newArray(int size) {
                    return new CraftworkAddPriceItems[size];
                }
            };

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

            public double getKeepPrice() {
                return keepPrice;
            }

            public void setKeepPrice(double keepPrice) {
                this.keepPrice = keepPrice;
            }

            public String getCraftPrice() {
                return craftPrice;
            }

            public void setCraftPrice(String craftPrice) {
                this.craftPrice = craftPrice;
            }

            public String getItemRealPrice() {
                return itemRealPrice;
            }

            public void setItemRealPrice(String itemRealPrice) {
                this.itemRealPrice = itemRealPrice;
            }

            public String getHasDiscount() {
                return hasDiscount;
            }

            public void setHasDiscount(String hasDiscount) {
                this.hasDiscount = hasDiscount;
            }

            public String getItemDiscount() {
                return itemDiscount;
            }

            public void setItemDiscount(String itemDiscount) {
                this.itemDiscount = itemDiscount;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getProblem() {
                return problem;
            }

            public void setProblem(String problem) {
                this.problem = problem;
            }

            public String getForecast() {
                return forecast;
            }

            public void setForecast(String forecast) {
                this.forecast = forecast;
            }

            public String getTakeTime() {
                return takeTime;
            }

            public void setTakeTime(String takeTime) {
                this.takeTime = takeTime;
            }

            public String getCraftDes() {
                return craftDes;
            }

            public void setCraftDes(String craftDes) {
                this.craftDes = craftDes;
            }

            public String getCleanSn() {
                return cleanSn;
            }

            public void setCleanSn(String cleanSn) {
                this.cleanSn = cleanSn;
            }

            public String getPutSn() {
                return putSn;
            }

            public void setPutSn(String putSn) {
                this.putSn = putSn;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(itemName);
                dest.writeString(itemPrice);
                dest.writeDouble(keepPrice);
                dest.writeString(craftPrice);
                dest.writeString(itemRealPrice);
                dest.writeString(hasDiscount);
                dest.writeString(itemDiscount);
                dest.writeString(color);
                dest.writeString(problem);
                dest.writeString(forecast);
                dest.writeString(takeTime);
                dest.writeString(craftDes);
                dest.writeString(cleanSn);
                dest.writeString(putSn);
                dest.writeString(image);
            }
        }
    }
}
