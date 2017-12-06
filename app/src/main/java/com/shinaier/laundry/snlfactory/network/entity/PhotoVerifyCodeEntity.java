package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/12/2.
 */

public class PhotoVerifyCodeEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private PhotoVerifyCodeResult result;

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

    public PhotoVerifyCodeResult getResult() {
        return result;
    }

    public void setResult(PhotoVerifyCodeResult result) {
        this.result = result;
    }

    public class PhotoVerifyCodeResult{
        @SerializedName("captcha") //加密码
        private String captcha;
        @SerializedName("unique") //唯一码
        private String unique;
        @SerializedName("image")
        private String image;

        public String getCaptcha() {
            return captcha;
        }

        public void setCaptcha(String captcha) {
            this.captcha = captcha;
        }

        public String getUnique() {
            return unique;
        }

        public void setUnique(String unique) {
            this.unique = unique;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
