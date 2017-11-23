package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/8/2.
 */

public class OfflineMemberNumEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OfflineMemberNumDatas datas;

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

    public OfflineMemberNumDatas getDatas() {
        return datas;
    }

    public void setDatas(OfflineMemberNumDatas datas) {
        this.datas = datas;
    }

    public class OfflineMemberNumDatas{
        @SerializedName("ucode")
        private String ucode;

        public String getUcode() {
            return ucode;
        }

        public void setUcode(String ucode) {
            this.ucode = ucode;
        }
    }
}
