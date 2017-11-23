package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/8/19.
 */

public class RechargeSuccessEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private RechargeSuccessDatas datas;

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

    public RechargeSuccessDatas getDatas() {
        return datas;
    }

    public void setDatas(RechargeSuccessDatas datas) {
        this.datas = datas;
    }

    public class RechargeSuccessDatas{
        @SerializedName("rechargeId")
        private String rechargeId;

        public String getRechargeId() {
            return rechargeId;
        }

        public void setRechargeId(String rechargeId) {
            this.rechargeId = rechargeId;
        }
    }
}
