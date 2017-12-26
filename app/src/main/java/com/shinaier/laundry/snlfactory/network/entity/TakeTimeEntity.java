package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/11/3.
 */

public class TakeTimeEntity {
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private List<String> times;

    private boolean isSelect;

    public boolean isSelect() {
            return isSelect;
        }

    public void setSelect(boolean select) {
            isSelect = select;
        }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }
}
