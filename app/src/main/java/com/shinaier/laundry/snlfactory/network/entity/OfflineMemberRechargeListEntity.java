package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/3.
 */

public class OfflineMemberRechargeListEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private OfflineMemberRechargeResult result;

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

    public OfflineMemberRechargeResult getResult() {
        return result;
    }

    public void setResult(OfflineMemberRechargeResult result) {
        this.result = result;
    }

    public class OfflineMemberRechargeResult{
        @SerializedName("record")
        private List<OfflineMemberRechargeRecord> rechargeRecords;
        @SerializedName("recharge")
        private String recharge;
        @SerializedName("give")
        private String give;
        @SerializedName("page_count")
        private int pageCount;

        public List<OfflineMemberRechargeRecord> getRechargeRecords() {
            return rechargeRecords;
        }

        public void setRechargeRecords(List<OfflineMemberRechargeRecord> rechargeRecords) {
            this.rechargeRecords = rechargeRecords;
        }

        public String getRecharge() {
            return recharge;
        }

        public void setRecharge(String recharge) {
            this.recharge = recharge;
        }

        public String getGive() {
            return give;
        }

        public void setGive(String give) {
            this.give = give;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public class OfflineMemberRechargeRecord{

            @SerializedName("umobile")
            private String uMobile;
            @SerializedName("amount")
            private String amount;
            @SerializedName("give")
            private String give;
            @SerializedName("log_time")
            private String logTime;

            public String getuMobile() {
                return uMobile;
            }

            public void setuMobile(String uMobile) {
                this.uMobile = uMobile;
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

            public String getLogTime() {
                return logTime;
            }

            public void setLogTime(String logTime) {
                this.logTime = logTime;
            }
        }

    }
}
