package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/7/28.
 */

public class OfflineMemberDetailEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OfflineMemberDetailDatas detailDatas;

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

    public OfflineMemberDetailDatas getDetailDatas() {
        return detailDatas;
    }

    public void setDetailDatas(OfflineMemberDetailDatas detailDatas) {
        this.detailDatas = detailDatas;
    }

    public class OfflineMemberDetailDatas{
        @SerializedName("user")
        private OfflineMemberDetailUser user;
        @SerializedName("record")
        private List<OfflineMemberDetailRecord> record;

        public OfflineMemberDetailUser getUser() {
            return user;
        }

        public void setUser(OfflineMemberDetailUser user) {
            this.user = user;
        }

        public List<OfflineMemberDetailRecord> getRecord() {
            return record;
        }

        public void setRecord(List<OfflineMemberDetailRecord> record) {
            this.record = record;
        }

        public class OfflineMemberDetailUser{
            @SerializedName("id")
            private String id;
            @SerializedName("username")
            private String userName;
            @SerializedName("sex")
            private String sex;
            @SerializedName("ucode")
            private String ucode;
            @SerializedName("card_name")
            private String cardName;
            @SerializedName("mobile_number")
            private String mobileNumber;
            @SerializedName("balance")
            private String balance;
            @SerializedName("birthday")
            private String birthday;
            @SerializedName("register_time")
            private String registerTime;
            @SerializedName("address")
            private String address;
            @SerializedName("remark")
            private String remark;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getUcode() {
                return ucode;
            }

            public void setUcode(String ucode) {
                this.ucode = ucode;
            }

            public String getCardName() {
                return cardName;
            }

            public void setCardName(String cardName) {
                this.cardName = cardName;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getRegisterTime() {
                return registerTime;
            }

            public void setRegisterTime(String registerTime) {
                this.registerTime = registerTime;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }

        public class OfflineMemberDetailRecord{
            @SerializedName("id")
            private String id;
            @SerializedName("mid")
            private String mid;
            @SerializedName("uid")
            private String uid;
            @SerializedName("type") //0 是消费 1 是充值
            private String type;
            @SerializedName("amount")
            private String amount;
            @SerializedName("give")
            private String give;
            @SerializedName("update_time")
            private String updateTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getGive() {
                return give;
            }

            public void setGive(String give) {
                this.give = give;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
