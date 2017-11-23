package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/2/9.
 */

public class StoreEntity {
    @SerializedName("mname")
    private String mname;
    @SerializedName("circle_logo")
    private String circleLogo;
    @SerializedName("state") //1 正常营业 其他是休息
    private String state;
    @SerializedName("total") // 今日营业额
    private double total;
    @SerializedName("order_count") // 订单总数
    private String orderCount;
    @SerializedName("average") //评分
    private String average;
    @SerializedName("messageState") // 1 有消息 0 无消息
    private int messageState;

    public int getMessageState() {
        return messageState;
    }

    public void setMessageState(int messageState) {
        this.messageState = messageState;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getCircleLogo() {
        return circleLogo;
    }

    public void setCircleLogo(String circleLogo) {
        this.circleLogo = circleLogo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }
}
