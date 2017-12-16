package com.shinaier.laundry.snlfactory.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/10/31.
 */

public class CashCouponEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<CashCouponResult> result;

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

    public List<CashCouponResult> getResult() {
        return result;
    }

    public void setResult(List<CashCouponResult> result) {
        this.result = result;
    }

    public static class CashCouponResult implements Parcelable {
        @SerializedName("id")
        private String id;
        @SerializedName("sn")
        private String sn;
        @SerializedName("end_time")
        private String endTime;

        protected CashCouponResult(Parcel in) {
            id = in.readString();
            sn = in.readString();
            endTime = in.readString();
        }

        public static final Creator<CashCouponResult> CREATOR = new Creator<CashCouponResult>() {
            @Override
            public CashCouponResult createFromParcel(Parcel in) {
                return new CashCouponResult(in);
            }

            @Override
            public CashCouponResult[] newArray(int size) {
                return new CashCouponResult[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(sn);
            dest.writeString(endTime);
        }
    }
}
