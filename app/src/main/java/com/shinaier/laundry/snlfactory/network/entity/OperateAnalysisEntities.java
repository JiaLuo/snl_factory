package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/9/12.
 */

public class OperateAnalysisEntities {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OperateAnalysisDatas datas;

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

    public OperateAnalysisDatas getDatas() {
        return datas;
    }

    public void setDatas(OperateAnalysisDatas datas) {
        this.datas = datas;
    }

    public class OperateAnalysisDatas{
        @SerializedName("date")
        private List<String> dates;
        @SerializedName("sum")
        private String sum;
        @SerializedName("psum")
        private String psum;
        @SerializedName("now")
        private Nows nows;
        @SerializedName("previous")
        private Previous previouses;
        @SerializedName("day")
        private List<String> days;
        @SerializedName("now_sum")
        private List<String> nowSums;
        @SerializedName("previous_sum")
        private List<String> previousSums;
        @SerializedName("proportion")
        private Proportion proportions;

        public List<String> getPreviousSums() {
            return previousSums;
        }

        public void setPreviousSums(List<String> previousSums) {
            this.previousSums = previousSums;
        }

        public List<String> getDates() {
            return dates;
        }

        public void setDates(List<String> dates) {
            this.dates = dates;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getPsum() {
            return psum;
        }

        public void setPsum(String psum) {
            this.psum = psum;
        }

        public Nows getNows() {
            return nows;
        }

        public void setNows(Nows nows) {
            this.nows = nows;
        }

        public Previous getPreviouses() {
            return previouses;
        }

        public void setPreviouses(Previous previouses) {
            this.previouses = previouses;
        }

        public List<String> getDays() {
            return days;
        }

        public void setDays(List<String> days) {
            this.days = days;
        }

        public List<String> getNowSums() {
            return nowSums;
        }

        public void setNowSums(List<String> nowSums) {
            this.nowSums = nowSums;
        }

        public Proportion getProportions() {
            return proportions;
        }

        public void setProportions(Proportion proportions) {
            this.proportions = proportions;
        }

        public class Proportion{
            @SerializedName("sum")
            private double sum;
            @SerializedName("all")
            private double all;
            @SerializedName("cancel")
            private double cancel;

            public double getSum() {
                return sum;
            }

            public void setSum(double sum) {
                this.sum = sum;
            }

            public double getAll() {
                return all;
            }

            public void setAll(double all) {
                this.all = all;
            }

            public double getCancel() {
                return cancel;
            }

            public void setCancel(double cancel) {
                this.cancel = cancel;
            }
        }

        public class Previous{
            @SerializedName("sum")
            private double sum;
            @SerializedName("all")
            private double all;
            @SerializedName("cancel")
            private double cancel;

            public double getSum() {
                return sum;
            }

            public void setSum(double sum) {
                this.sum = sum;
            }

            public double getAll() {
                return all;
            }

            public void setAll(double all) {
                this.all = all;
            }

            public double getCancel() {
                return cancel;
            }

            public void setCancel(double cancel) {
                this.cancel = cancel;
            }
        }

        public class Nows{
            @SerializedName("sum")
            private double sum;
            @SerializedName("all")
            private double all;
            @SerializedName("cancel")
            private double cancel;

            public double getSum() {
                return sum;
            }

            public void setSum(double sum) {
                this.sum = sum;
            }

            public double getAll() {
                return all;
            }

            public void setAll(double all) {
                this.all = all;
            }

            public double getCancel() {
                return cancel;
            }

            public void setCancel(double cancel) {
                this.cancel = cancel;
            }
        }
    }
}
