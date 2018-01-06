package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/7/28.
 */

public class OfflineMemberDetailEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private OfflineMemberDetailResult result;

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

    public OfflineMemberDetailResult getResult() {
        return result;
    }

    public void setResult(OfflineMemberDetailResult result) {
        this.result = result;
    }

    public class OfflineMemberDetailResult{
        @SerializedName("uname")
        private String uName;
        @SerializedName("sex")
        private String sex;
        @SerializedName("cname")
        private String cName;
        @SerializedName("umobile")
        private String uMobile;
        @SerializedName("cbalance")
        private String cBalance;
        @SerializedName("birthday")
        private String birthday;
        @SerializedName("ctime")
        private String cTime;
        @SerializedName("addr")
        private String addr;
        @SerializedName("remark")
        private String remark;
        @SerializedName("is_company")
        private String isCompany;
        @SerializedName("cdiscount")
        private String cDiscount;
        @SerializedName("recode")
        private List<OfflineMemberDetailRecord> record;

        public String getcDiscount() {
            return cDiscount;
        }

        public void setcDiscount(String cDiscount) {
            this.cDiscount = cDiscount;
        }

        public String getuName() {
            return uName;
        }

        public void setuName(String uName) {
            this.uName = uName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getcName() {
            return cName;
        }

        public void setcName(String cName) {
            this.cName = cName;
        }

        public String getuMobile() {
            return uMobile;
        }

        public void setuMobile(String uMobile) {
            this.uMobile = uMobile;
        }

        public String getcBalance() {
            return cBalance;
        }

        public void setcBalance(String cBalance) {
            this.cBalance = cBalance;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getcTime() {
            return cTime;
        }

        public void setcTime(String cTime) {
            this.cTime = cTime;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getIsCompany() {
            return isCompany;
        }

        public void setIsCompany(String isCompany) {
            this.isCompany = isCompany;
        }

        public List<OfflineMemberDetailRecord> getRecord() {
            return record;
        }

        public void setRecord(List<OfflineMemberDetailRecord> record) {
            this.record = record;
        }


        public class OfflineMemberDetailRecord{
            @SerializedName("amount")
            private String amount;
            @SerializedName("give")
            private String give;
            @SerializedName("type") //0 是消费 1 是充值
            private String type;
            @SerializedName("log_time")
            private String logTime;

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLogTime() {
                return logTime;
            }

            public void setLogTime(String logTime) {
                this.logTime = logTime;
            }
        }
    }
}
