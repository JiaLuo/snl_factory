package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/11/6.
 */

public class CashConponEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("value")
    private double value;

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
