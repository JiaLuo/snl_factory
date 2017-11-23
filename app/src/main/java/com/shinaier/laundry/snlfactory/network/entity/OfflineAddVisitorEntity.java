package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/8/3.
 */

public class OfflineAddVisitorEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OfflineAddVisitorDatas datas;

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

    public OfflineAddVisitorDatas getDatas() {
        return datas;
    }

    public void setDatas(OfflineAddVisitorDatas datas) {
        this.datas = datas;
    }

    public class OfflineAddVisitorDatas{
        @SerializedName("user")
        private String user;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }
}
