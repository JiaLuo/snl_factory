package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/3.
 */

public class OfflineMemberRechargeListEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OfflineMemberRechargeDatas datas;

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

    public OfflineMemberRechargeDatas getDatas() {
        return datas;
    }

    public void setDatas(OfflineMemberRechargeDatas datas) {
        this.datas = datas;
    }

    public class OfflineMemberRechargeDatas{
        @SerializedName("recharged_total")
        private String rechargedTotal;
        @SerializedName("given_total")
        private String givenTotal;
        @SerializedName("record")
        private List<OfflineMemberRechargeRecord> rechargeRecords;
        @SerializedName("page_count")
        private int pageCount;
        @SerializedName("recharged_count")
        private int rechargedCount;

        public int getRechargedCount() {
            return rechargedCount;
        }

        public void setRechargedCount(int rechargedCount) {
            this.rechargedCount = rechargedCount;
        }

        public String getRechargedTotal() {
            return rechargedTotal;
        }

        public void setRechargedTotal(String rechargedTotal) {
            this.rechargedTotal = rechargedTotal;
        }

        public String getGivenTotal() {
            return givenTotal;
        }

        public void setGivenTotal(String givenTotal) {
            this.givenTotal = givenTotal;
        }

        public List<OfflineMemberRechargeRecord> getRechargeRecords() {
            return rechargeRecords;
        }

        public void setRechargeRecords(List<OfflineMemberRechargeRecord> rechargeRecords) {
            this.rechargeRecords = rechargeRecords;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public class OfflineMemberRechargeRecord{

            @SerializedName("ucode")
            private String ucode;
            @SerializedName("recharge_amount")
            private String rechargeAmount;
            @SerializedName("give")
            private String give;
            @SerializedName("recharge_time")
            private String rechargeTime;

            public String getUcode() {
                return ucode;
            }

            public void setUcode(String ucode) {
                this.ucode = ucode;
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

            public String getRechargeTime() {
                return rechargeTime;
            }

            public void setRechargeTime(String rechargeTime) {
                this.rechargeTime = rechargeTime;
            }
        }

    }
}
