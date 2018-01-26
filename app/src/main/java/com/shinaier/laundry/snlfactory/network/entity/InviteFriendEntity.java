package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/2/15.
 */

public class InviteFriendEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private InviteFriendResult result;

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

    public InviteFriendResult getResult() {
        return result;
    }

    public void setResult(InviteFriendResult result) {
        this.result = result;
    }

    public class InviteFriendResult{
        @SerializedName("mcode")
        private String mCode;
        @SerializedName("url")
        private String url;

        public String getmCode() {
            return mCode;
        }

        public void setmCode(String mCode) {
            this.mCode = mCode;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
