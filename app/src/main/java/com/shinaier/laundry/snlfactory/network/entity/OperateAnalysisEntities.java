package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/9/12.
 */

public class OperateAnalysisEntities {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private OperateAnalysisResult result;

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

    public OperateAnalysisResult getResult() {
        return result;
    }

    public void setResult(OperateAnalysisResult result) {
        this.result = result;
    }

    public class OperateAnalysisResult{
        @SerializedName("time")
        private String time;
        @SerializedName("now_info")
        private OperateAnalysisNowInfo nowInfo;
        @SerializedName("last_info")
        private OperateAnalysisLastInfo lastInfo;
        @SerializedName("increase")
        private OperateAnalysisIncrease increase;
        @SerializedName("now_business_total")
        private String nowBusinessTotal;
        @SerializedName("before_business_total")
        private String beforeBusinessTotal;
        @SerializedName("now_month_data")
        private List<OperateAnalysisNowMonth> nowMonths;
        @SerializedName("last_month_data")
        private List<OperateAnalysisLastMonth> lastMonthData;
        @SerializedName("monarr")
        private List<String> monarr;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public OperateAnalysisNowInfo getNowInfo() {
            return nowInfo;
        }

        public void setNowInfo(OperateAnalysisNowInfo nowInfo) {
            this.nowInfo = nowInfo;
        }

        public OperateAnalysisLastInfo getLastInfo() {
            return lastInfo;
        }

        public void setLastInfo(OperateAnalysisLastInfo lastInfo) {
            this.lastInfo = lastInfo;
        }

        public OperateAnalysisIncrease getIncrease() {
            return increase;
        }

        public void setIncrease(OperateAnalysisIncrease increase) {
            this.increase = increase;
        }

        public String getNowBusinessTotal() {
            return nowBusinessTotal;
        }

        public void setNowBusinessTotal(String nowBusinessTotal) {
            this.nowBusinessTotal = nowBusinessTotal;
        }

        public String getBeforeBusinessTotal() {
            return beforeBusinessTotal;
        }

        public void setBeforeBusinessTotal(String beforeBusinessTotal) {
            this.beforeBusinessTotal = beforeBusinessTotal;
        }

        public List<OperateAnalysisNowMonth> getNowMonths() {
            return nowMonths;
        }

        public void setNowMonths(List<OperateAnalysisNowMonth> nowMonths) {
            this.nowMonths = nowMonths;
        }

        public List<OperateAnalysisLastMonth> getLastMonthData() {
            return lastMonthData;
        }

        public void setLastMonthData(List<OperateAnalysisLastMonth> lastMonthData) {
            this.lastMonthData = lastMonthData;
        }

        public List<String> getMonarr() {
            return monarr;
        }

        public void setMonarr(List<String> monarr) {
            this.monarr = monarr;
        }

        public class OperateAnalysisLastMonth{
            @SerializedName("day")
            private String day;
            @SerializedName("total")
            private String total;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }
        }

        public class OperateAnalysisNowMonth{
            @SerializedName("day")
            private String day;
            @SerializedName("total")
            private String total;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }
        }


        public class OperateAnalysisIncrease{
            @SerializedName("business_total")
            private String businessTotal;
            @SerializedName("item_total")
            private String itemTotal;
            @SerializedName("cancel_item_total")
            private String cancelItemTotal;

            public String getBusinessTotal() {
                return businessTotal;
            }

            public void setBusinessTotal(String businessTotal) {
                this.businessTotal = businessTotal;
            }

            public String getItemTotal() {
                return itemTotal;
            }

            public void setItemTotal(String itemTotal) {
                this.itemTotal = itemTotal;
            }

            public String getCancelItemTotal() {
                return cancelItemTotal;
            }

            public void setCancelItemTotal(String cancelItemTotal) {
                this.cancelItemTotal = cancelItemTotal;
            }
        }

        public class OperateAnalysisLastInfo{
            @SerializedName("business_total")
            private double businessTotal;
            @SerializedName("item_total")
            private double itemTotal;
            @SerializedName("cancel_item_total")
            private double cancelItemTotal;

            public double getBusinessTotal() {
                return businessTotal;
            }

            public void setBusinessTotal(double businessTotal) {
                this.businessTotal = businessTotal;
            }

            public double getItemTotal() {
                return itemTotal;
            }

            public void setItemTotal(double itemTotal) {
                this.itemTotal = itemTotal;
            }

            public double getCancelItemTotal() {
                return cancelItemTotal;
            }

            public void setCancelItemTotal(double cancelItemTotal) {
                this.cancelItemTotal = cancelItemTotal;
            }
        }

        public class OperateAnalysisNowInfo{
            @SerializedName("business_total")
            private double businessTotal;
            @SerializedName("item_total")
            private double itemTotal;
            @SerializedName("cancel_item_total")
            private double cancelItemTotal;

            public double getBusinessTotal() {
                return businessTotal;
            }

            public void setBusinessTotal(double businessTotal) {
                this.businessTotal = businessTotal;
            }

            public double getItemTotal() {
                return itemTotal;
            }

            public void setItemTotal(double itemTotal) {
                this.itemTotal = itemTotal;
            }

            public double getCancelItemTotal() {
                return cancelItemTotal;
            }

            public void setCancelItemTotal(double cancelItemTotal) {
                this.cancelItemTotal = cancelItemTotal;
            }
        }
    }
}
