package com.shinaier.laundry.snlfactory.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/18.
 */

public class StoreInfoEntity implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("mname")
    private String mName;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;
    @SerializedName("round")
    private String round;
    @SerializedName("fuwu_amount")
    private String fuwuAmount;
    @SerializedName("fuwu_num")
    private String fuwuNum;
    @SerializedName("fuwu_total")
    private String fuwuTotal;
    @SerializedName("qrcode")
    private String qrCode;
    @SerializedName("cards")
    private List<StoreINfoCards> cards;

    protected StoreInfoEntity(Parcel in) {
        id = in.readString();
        mName = in.readString();
        address = in.readString();
        phone = in.readString();
        round = in.readString();
        fuwuAmount = in.readString();
        fuwuNum = in.readString();
        fuwuTotal = in.readString();
        qrCode = in.readString();
        cards = in.createTypedArrayList(StoreINfoCards.CREATOR);
    }

    public static final Creator<StoreInfoEntity> CREATOR = new Creator<StoreInfoEntity>() {
        @Override
        public StoreInfoEntity createFromParcel(Parcel in) {
            return new StoreInfoEntity(in);
        }

        @Override
        public StoreInfoEntity[] newArray(int size) {
            return new StoreInfoEntity[size];
        }
    };

    public List<StoreINfoCards> getCards() {
        return cards;
    }

    public void setCards(List<StoreINfoCards> cards) {
        this.cards = cards;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(mName);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeString(round);
        dest.writeString(fuwuAmount);
        dest.writeString(fuwuNum);
        dest.writeString(fuwuTotal);
        dest.writeString(qrCode);
        dest.writeTypedList(cards);
    }

    public static class StoreINfoCards implements Parcelable {
        @SerializedName("id")
        private String id;
        @SerializedName("card_name")
        private String cardName;
        @SerializedName("discount")
        private String discount;
        @SerializedName("price")
        private String price;

        protected StoreINfoCards(Parcel in) {
            id = in.readString();
            cardName = in.readString();
            discount = in.readString();
            price = in.readString();
        }

        public static final Creator<StoreINfoCards> CREATOR = new Creator<StoreINfoCards>() {
            @Override
            public StoreINfoCards createFromParcel(Parcel in) {
                return new StoreINfoCards(in);
            }

            @Override
            public StoreINfoCards[] newArray(int size) {
                return new StoreINfoCards[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(cardName);
            dest.writeString(discount);
            dest.writeString(price);
        }
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getFuwuAmount() {
        return fuwuAmount;
    }

    public void setFuwuAmount(String fuwuAmount) {
        this.fuwuAmount = fuwuAmount;
    }

    public String getFuwuNum() {
        return fuwuNum;
    }

    public void setFuwuNum(String fuwuNum) {
        this.fuwuNum = fuwuNum;
    }

    public String getFuwuTotal() {
        return fuwuTotal;
    }

    public void setFuwuTotal(String fuwuTotal) {
        this.fuwuTotal = fuwuTotal;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
