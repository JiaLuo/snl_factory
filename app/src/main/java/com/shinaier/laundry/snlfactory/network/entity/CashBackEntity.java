package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/18.
 */

public class CashBackEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private CashBackResult result;

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

    public CashBackResult getResult() {
        return result;
    }

    public void setResult(CashBackResult result) {
        this.result = result;
    }

    public class CashBackResult{
        @SerializedName("share_total")
        private String shareTotal;
        @SerializedName("back_balance")
        private double backBalance;
        @SerializedName("list")
        private List<CashBackList> lists;

        public String getShareTotal() {
            return shareTotal;
        }

        public void setShareTotal(String shareTotal) {
            this.shareTotal = shareTotal;
        }

        public double getBackBalance() {
            return backBalance;
        }

        public void setBackBalance(double backBalance) {
            this.backBalance = backBalance;
        }

        public List<CashBackList> getLists() {
            return lists;
        }

        public void setLists(List<CashBackList> lists) {
            this.lists = lists;
        }

        public class CashBackList{
            @SerializedName("value")
            private String value;
            @SerializedName("update_time")
            private String updateTime;
            @SerializedName("uname")
            private String uName;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getuName() {
                return uName;
            }

            public void setuName(String uName) {
                this.uName = uName;
            }
        }
    }
}
