package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/2/18.
 */

public class SettingsEntity {
    @SerializedName("card_number")
    private String cardNumber;
    @SerializedName("circle_logo")
    private String circleLogo;
    @SerializedName("manager")
    private String manager;
    @SerializedName("mname")
    private String mname;
    @SerializedName("phone")
    private String phone;
    @SerializedName("service")
    private String service;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCircleLogo() {
        return circleLogo;
    }

    public void setCircleLogo(String circleLogo) {
        this.circleLogo = circleLogo;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
