package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/1.
 */

public class StatisticsIncomeEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private StatisticsIncomeResult result;

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

    public StatisticsIncomeResult getResult() {
        return result;
    }

    public void setResult(StatisticsIncomeResult result) {
        this.result = result;
    }

    public class StatisticsIncomeResult{
        @SerializedName("sum")
        private String sum;
        @SerializedName("page_count")
        private int pageCount;
        @SerializedName("record")
        private List<StatisticsIncomeRecord> records;

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<StatisticsIncomeRecord> getRecords() {
            return records;
        }

        public void setRecords(List<StatisticsIncomeRecord> records) {
            this.records = records;
        }

        public class StatisticsIncomeRecord{
            @SerializedName("cash")
            private String cash;
            @SerializedName("platform")
            private String platform;
            @SerializedName("merchant")
            private String merchant;
            @SerializedName("wechat")
            private String wechat;
            @SerializedName("alipay")
            private String alipay;
            @SerializedName("real_total")
            private String realTotal;
            @SerializedName("total")
            private String total;
            @SerializedName("eftime")
            private String eftime;

            public String getCash() {
                return cash;
            }

            public void setCash(String cash) {
                this.cash = cash;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getMerchant() {
                return merchant;
            }

            public void setMerchant(String merchant) {
                this.merchant = merchant;
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

            public String getRealTotal() {
                return realTotal;
            }

            public void setRealTotal(String realTotal) {
                this.realTotal = realTotal;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getEftime() {
                return eftime;
            }

            public void setEftime(String eftime) {
                this.eftime = eftime;
            }
        }
    }
}
