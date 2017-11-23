package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/13.
 */

public class ManageFinanceDetailEntities {

    @SerializedName("count")
    private int count;
    @SerializedName("data")
    private List<FinanceListEntities> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<FinanceListEntities> getData() {
        return data;
    }

    public void setData(List<FinanceListEntities> data) {
        this.data = data;
    }

    public class FinanceListEntities{
        @SerializedName("sum")
        private String sum;
        @SerializedName("total")
        private String total;
        @SerializedName("reward")
        private String reward;
        @SerializedName("mch_get") //平台服务费
        private String mchGet;
        @SerializedName("time")
        private String time;
        @SerializedName("state")   //state: 1->收入; 2->返现; 3->平台打款;

        private int state;

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getMchGet() {
            return mchGet;
        }

        public void setMchGet(String mchGet) {
            this.mchGet = mchGet;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }


}
