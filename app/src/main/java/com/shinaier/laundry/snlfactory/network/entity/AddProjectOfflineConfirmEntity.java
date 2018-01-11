package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2018/1/8.
 */

public class AddProjectOfflineConfirmEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("oid")
    private String oid;

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

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
