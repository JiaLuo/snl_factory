package com.shinaier.laundry.snlfactory.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/19.
 */

public class OrderPrintEntity implements Parcelable {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OrderPrintDatas datas;

    protected OrderPrintEntity(Parcel in) {
        retcode = in.readInt();
        status = in.readString();
    }

    public static final Creator<OrderPrintEntity> CREATOR = new Creator<OrderPrintEntity>() {
        @Override
        public OrderPrintEntity createFromParcel(Parcel in) {
            return new OrderPrintEntity(in);
        }

        @Override
        public OrderPrintEntity[] newArray(int size) {
            return new OrderPrintEntity[size];
        }
    };

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

    public OrderPrintDatas getDatas() {
        return datas;
    }

    public void setDatas(OrderPrintDatas datas) {
        this.datas = datas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(retcode);
        dest.writeString(status);
    }

    public static class OrderPrintDatas implements Parcelable{
        @SerializedName("orderInfo")
        private OrderPrintInfo info;
        @SerializedName("items")
        private List<OrderPrintItems> items;

        protected OrderPrintDatas(Parcel in) {
        }

        public static final Creator<OrderPrintDatas> CREATOR = new Creator<OrderPrintDatas>() {
            @Override
            public OrderPrintDatas createFromParcel(Parcel in) {
                return new OrderPrintDatas(in);
            }

            @Override
            public OrderPrintDatas[] newArray(int size) {
                return new OrderPrintDatas[size];
            }
        };

        public OrderPrintInfo getInfo() {
            return info;
        }

        public void setInfo(OrderPrintInfo info) {
            this.info = info;
        }

        public List<OrderPrintItems> getItems() {
            return items;
        }

        public void setItems(List<OrderPrintItems> items) {
            this.items = items;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public static class OrderPrintItems implements Parcelable{
            @SerializedName("item_note")
            private String itemNote;
            @SerializedName("color")
            private String color;
            @SerializedName("price")
            private String price;
            @SerializedName("put_number")
            private String putNumber;
            @SerializedName("name")
            private String name;
            @SerializedName("color_text")
            private String colorText;
            @SerializedName("note_text")
            private String noteText;

            protected OrderPrintItems(Parcel in) {
                itemNote = in.readString();
                color = in.readString();
                price = in.readString();
                putNumber = in.readString();
                name = in.readString();
                colorText = in.readString();
                noteText = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(itemNote);
                dest.writeString(color);
                dest.writeString(price);
                dest.writeString(putNumber);
                dest.writeString(name);
                dest.writeString(colorText);
                dest.writeString(noteText);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<OrderPrintItems> CREATOR = new Creator<OrderPrintItems>() {
                @Override
                public OrderPrintItems createFromParcel(Parcel in) {
                    return new OrderPrintItems(in);
                }

                @Override
                public OrderPrintItems[] newArray(int size) {
                    return new OrderPrintItems[size];
                }
            };

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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public String getColorText() {
                return colorText;
            }

            public void setColorText(String colorText) {
                this.colorText = colorText;
            }

            public String getNoteText() {
                return noteText;
            }

            public void setNoteText(String noteText) {
                this.noteText = noteText;
            }
        }

        public static class OrderPrintInfo implements Parcelable{
            @SerializedName("ordersn")
            private String ordersn;
            @SerializedName("mobile")
            private String mobile;
            @SerializedName("piece_num")
            private String pieceNum;
            @SerializedName("hedging")
            private String hedging;
            @SerializedName("pay_amount")
            private String payAmount;
            @SerializedName("reduce_price")
            private String reducePrice;
            @SerializedName("pay_channel")
            private String payChannel;
            @SerializedName("userid")
            private String userid;
            @SerializedName("address")
            private String address;
            @SerializedName("phone")
            private String phone;
            @SerializedName("mid")
            private String mid;
            @SerializedName("clerkname")
            private String clerkName;
            @SerializedName("pay_state")
            private String payState;
            @SerializedName("amount")
            private String amount;
            @SerializedName("card_number")
            private String cardNumber;
            @SerializedName("card_balance")
            private String cardBalance;
            @SerializedName("qrcode")
            private String qrcode;
            @SerializedName("mname")
            private String mName;

            public String getmName() {
                return mName;
            }

            public void setmName(String mName) {
                this.mName = mName;
            }

            protected OrderPrintInfo(Parcel in) {
                ordersn = in.readString();
                mobile = in.readString();
                pieceNum = in.readString();
                hedging = in.readString();
                payAmount = in.readString();
                reducePrice = in.readString();
                payChannel = in.readString();
                userid = in.readString();
                address = in.readString();
                phone = in.readString();
                mid = in.readString();
                clerkName = in.readString();
                payState = in.readString();
                amount = in.readString();
                cardNumber = in.readString();
                cardBalance = in.readString();
                qrcode = in.readString();
            }

            public static final Creator<OrderPrintInfo> CREATOR = new Creator<OrderPrintInfo>() {
                @Override
                public OrderPrintInfo createFromParcel(Parcel in) {
                    return new OrderPrintInfo(in);
                }

                @Override
                public OrderPrintInfo[] newArray(int size) {
                    return new OrderPrintInfo[size];
                }
            };

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

            public String getPieceNum() {
                return pieceNum;
            }

            public void setPieceNum(String pieceNum) {
                this.pieceNum = pieceNum;
            }

            public String getHedging() {
                return hedging;
            }

            public void setHedging(String hedging) {
                this.hedging = hedging;
            }

            public String getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(String payAmount) {
                this.payAmount = payAmount;
            }

            public String getReducePrice() {
                return reducePrice;
            }

            public void setReducePrice(String reducePrice) {
                this.reducePrice = reducePrice;
            }

            public String getPayChannel() {
                return payChannel;
            }

            public void setPayChannel(String payChannel) {
                this.payChannel = payChannel;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
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

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getClerkName() {
                return clerkName;
            }

            public void setClerkName(String clerkName) {
                this.clerkName = clerkName;
            }

            public String getPayState() {
                return payState;
            }

            public void setPayState(String payState) {
                this.payState = payState;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCardNumber() {
                return cardNumber;
            }

            public void setCardNumber(String cardNumber) {
                this.cardNumber = cardNumber;
            }

            public String getCardBalance() {
                return cardBalance;
            }

            public void setCardBalance(String cardBalance) {
                this.cardBalance = cardBalance;
            }

            public String getQrcode() {
                return qrcode;
            }

            public void setQrcode(String qrcode) {
                this.qrcode = qrcode;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(ordersn);
                dest.writeString(mobile);
                dest.writeString(pieceNum);
                dest.writeString(hedging);
                dest.writeString(payAmount);
                dest.writeString(reducePrice);
                dest.writeString(payChannel);
                dest.writeString(userid);
                dest.writeString(address);
                dest.writeString(phone);
                dest.writeString(mid);
                dest.writeString(clerkName);
                dest.writeString(payState);
                dest.writeString(amount);
                dest.writeString(cardNumber);
                dest.writeString(cardBalance);
                dest.writeString(qrcode);
            }
        }
    }
}
