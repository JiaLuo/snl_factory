package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/3.
 */

public class OfflineSendLaundryEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<OfflineSendLaundryDatas> datas;

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

    public List<OfflineSendLaundryDatas> getDatas() {
        return datas;
    }

    public void setDatas(List<OfflineSendLaundryDatas> datas) {
        this.datas = datas;
    }

    public class OfflineSendLaundryDatas{
        @SerializedName("id")
        private String id;
        @SerializedName("ordersn")
        private String ordersn;
        @SerializedName("number")
        private String number;
        @SerializedName("name")
        private String name;

        public boolean isSelect = false;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
