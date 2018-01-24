package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2018/1/16.
 */

public class CooperativeStoreEntities {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<CooperativeStoreResults> resultses;

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

    public List<CooperativeStoreResults> getResultses() {
        return resultses;
    }

    public void setResultses(List<CooperativeStoreResults> resultses) {
        this.resultses = resultses;
    }

    public class CooperativeStoreResults{
        @SerializedName("crew")
        private String acceptId;
        @SerializedName("id")
        private String id;
        @SerializedName("mname")
        private String mName;
        @SerializedName("phone_number")
        private String phoneNumber;
        @SerializedName("mlogo")
        private String mLogo;
        @SerializedName("maddress")
        private String mAddress;

        public boolean isSelect = false;

        public String getmAddress() {
            return mAddress;
        }

        public void setmAddress(String mAddress) {
            this.mAddress = mAddress;
        }

        public String getAcceptId() {
            return acceptId;
        }

        public void setAcceptId(String acceptId) {
            this.acceptId = acceptId;
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

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getmLogo() {
            return mLogo;
        }

        public void setmLogo(String mLogo) {
            this.mLogo = mLogo;
        }
    }
}
