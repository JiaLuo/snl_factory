package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/8/1.
 */

public class OfflineHomeEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OfflineHomeDatas datas;

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

    public OfflineHomeDatas getDatas() {
        return datas;
    }

    public void setDatas(OfflineHomeDatas datas) {
        this.datas = datas;
    }

    public class OfflineHomeDatas{
        @SerializedName("nickname")
        private String nickname;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("circle_logo")
        private String circleLogo;
        @SerializedName("mname")
        private String mName;

        public String getCircleLogo() {
            return circleLogo;
        }

        public void setCircleLogo(String circleLogo) {
            this.circleLogo = circleLogo;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
