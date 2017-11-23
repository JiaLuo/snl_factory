package com.shinaier.laundry.snlfactory.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/2.
 */

public class CheckClothesEntities implements Parcelable {
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
    @SerializedName("img")
    private List<String> imgs;
    @SerializedName("img_count")
    private String imgCount;
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
    private int type;

    protected CheckClothesEntities(Parcel in) {
        color = in.readString();
        createTime = in.readString();
        gName = in.readString();
        hedging = in.readString();
        id = in.readString();
        imgs = in.createStringArrayList();
        imgCount = in.readString();
        itemNote = in.readString();
        itemTitle = in.readString();
        itemId = in.readString();
        number = in.readString();
        orderId = in.readString();
        price = in.readString();
        special = in.readString();
        specialComment = in.readString();
        type = in.readInt();
    }

    public static final Creator<CheckClothesEntities> CREATOR = new Creator<CheckClothesEntities>() {
        @Override
        public CheckClothesEntities createFromParcel(Parcel in) {
            return new CheckClothesEntities(in);
        }

        @Override
        public CheckClothesEntities[] newArray(int size) {
            return new CheckClothesEntities[size];
        }
    };

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

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(color);
        parcel.writeString(createTime);
        parcel.writeString(gName);
        parcel.writeString(hedging);
        parcel.writeString(id);
        parcel.writeStringList(imgs);
        parcel.writeString(imgCount);
        parcel.writeString(itemNote);
        parcel.writeString(itemTitle);
        parcel.writeString(itemId);
        parcel.writeString(number);
        parcel.writeString(orderId);
        parcel.writeString(price);
        parcel.writeString(special);
        parcel.writeString(specialComment);
        parcel.writeInt(type);
    }
}
