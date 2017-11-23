package com.shinaier.laundry.snlfactory.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/8/19.
 */

public class PrintRechargeEntity implements Parcelable {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private PrintRechargeDatas datas;

    protected PrintRechargeEntity(Parcel in) {
        retcode = in.readInt();
        status = in.readString();
    }

    public static final Creator<PrintRechargeEntity> CREATOR = new Creator<PrintRechargeEntity>() {
        @Override
        public PrintRechargeEntity createFromParcel(Parcel in) {
            return new PrintRechargeEntity(in);
        }

        @Override
        public PrintRechargeEntity[] newArray(int size) {
            return new PrintRechargeEntity[size];
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

    public PrintRechargeDatas getDatas() {
        return datas;
    }

    public void setDatas(PrintRechargeDatas datas) {
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

    public static class PrintRechargeDatas implements Parcelable {
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("recharge_amount")
        private String rechargeAmount;
        @SerializedName("give")
        private String give;
        @SerializedName("ucode")
        private String ucode;
        @SerializedName("balance")
        private String balance;
        @SerializedName("address")
        private String address;
        @SerializedName("phone")
        private String phone;
        @SerializedName("mid")
        private String mid;
        @SerializedName("pay_type") //支付方式
        private String payType;
        @SerializedName("clerkname")
        private String clerkName;
        @SerializedName("order_number")
        private String orderNumber;
        @SerializedName("qrcode")
        private String qrCode;
        @SerializedName("qrcodeUrl")
        private String qrcodeUrl;

        public String getQrcodeUrl() {
            return qrcodeUrl;
        }

        public void setQrcodeUrl(String qrcodeUrl) {
            this.qrcodeUrl = qrcodeUrl;
        }

        public String getClerkName() {
            return clerkName;
        }

        public void setClerkName(String clerkName) {
            this.clerkName = clerkName;
        }

        protected PrintRechargeDatas(Parcel in) {
            mobile = in.readString();
            rechargeAmount = in.readString();
            give = in.readString();
            ucode = in.readString();
            balance = in.readString();
            address = in.readString();
            phone = in.readString();
            mid = in.readString();
            payType = in.readString();
            orderNumber = in.readString();
            qrCode = in.readString();
        }

        public static final Creator<PrintRechargeDatas> CREATOR = new Creator<PrintRechargeDatas>() {
            @Override
            public PrintRechargeDatas createFromParcel(Parcel in) {
                return new PrintRechargeDatas(in);
            }

            @Override
            public PrintRechargeDatas[] newArray(int size) {
                return new PrintRechargeDatas[size];
            }
        };

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRechargeAmount() {
            return rechargeAmount;
        }

        public void setRechargeAmount(String rechargeAmount) {
            this.rechargeAmount = rechargeAmount;
        }

        public String getGive() {
            return give;
        }

        public void setGive(String give) {
            this.give = give;
        }

        public String getUcode() {
            return ucode;
        }

        public void setUcode(String ucode) {
            this.ucode = ucode;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
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

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mobile);
            dest.writeString(rechargeAmount);
            dest.writeString(give);
            dest.writeString(ucode);
            dest.writeString(balance);
            dest.writeString(address);
            dest.writeString(phone);
            dest.writeString(mid);
            dest.writeString(payType);
            dest.writeString(orderNumber);
            dest.writeString(qrCode);
        }
    }
}
