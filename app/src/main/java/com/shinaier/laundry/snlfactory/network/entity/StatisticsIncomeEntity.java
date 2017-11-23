package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/1.
 */

public class StatisticsIncomeEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private StatisticsIncomeDatas datas;

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

    public StatisticsIncomeDatas getDatas() {
        return datas;
    }

    public void setDatas(StatisticsIncomeDatas datas) {
        this.datas = datas;
    }

    public class StatisticsIncomeDatas{
        @SerializedName("total_amount")
        private String totalAmount;
        @SerializedName("record")
        private List<StatisticsIncomeRecord> records;
        @SerializedName("record_count")
        private int recordCount;
        @SerializedName("page_count")
        private int pageCount;

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public List<StatisticsIncomeRecord> getRecords() {
            return records;
        }

        public void setRecords(List<StatisticsIncomeRecord> records) {
            this.records = records;
        }

        public int getRecordCount() {
            return recordCount;
        }

        public void setRecordCount(int recordCount) {
            this.recordCount = recordCount;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public class StatisticsIncomeRecord{
            @SerializedName("id")
            private String id;
            @SerializedName("mid")
            private String mid;
            @SerializedName("cash")
            private String cash;
            @SerializedName("platform_card")
            private String platformCard;
            @SerializedName("merchant_card")
            private String merchantCard;
            @SerializedName("wechat")
            private String wechat;
            @SerializedName("alipay")
            private String alipay;
            @SerializedName("total")
            private String total;
            @SerializedName("now_date")
            private String nowDate;

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

            public String getCash() {
                return cash;
            }

            public void setCash(String cash) {
                this.cash = cash;
            }

            public String getPlatformCard() {
                return platformCard;
            }

            public void setPlatformCard(String platformCard) {
                this.platformCard = platformCard;
            }

            public String getMerchantCard() {
                return merchantCard;
            }

            public void setMerchantCard(String merchantCard) {
                this.merchantCard = merchantCard;
            }

            public String getWechat() {
                return wechat;
            }

            public void setWechat(String wechat) {
                this.wechat = wechat;
            }

            public String getAlipay() {
                return alipay;
            }

            public void setAlipay(String alipay) {
                this.alipay = alipay;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getNowDate() {
                return nowDate;
            }

            public void setNowDate(String nowDate) {
                this.nowDate = nowDate;
            }
        }
    }

}
