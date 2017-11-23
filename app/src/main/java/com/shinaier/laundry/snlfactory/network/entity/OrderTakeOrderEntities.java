package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/25.
 */

public class OrderTakeOrderEntities {
    @SerializedName("count")
    private int count;
    @SerializedName("data")
    private List<TakeOrderData> takeOrderDataList;
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("dataCount")
    private String dataCount;

    public String getDataCount() {
        return dataCount;
    }

    public void setDataCount(String dataCount) {
        this.dataCount = dataCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<TakeOrderData> getTakeOrderDataList() {
        return takeOrderDataList;
    }

    public void setTakeOrderDataList(List<TakeOrderData> takeOrderDataList) {
        this.takeOrderDataList = takeOrderDataList;
    }

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

    public class TakeOrderData{
        @SerializedName("adr")
        private String adr;
        @SerializedName("create_time")
        private String createTime;
        @SerializedName("id")
        private String id;
        @SerializedName("item_state")
        private int itemState;
        @SerializedName("name")
        private String name;
        @SerializedName("ordersn")
        private String ordersn;
        @SerializedName("phone")
        private String phone;
        @SerializedName("state")
        private String state;
        @SerializedName("time")
        private String time;
        @SerializedName("up_time")
        private String upTime;
        @SerializedName("update_time") // 最近操作的时间
        private String updateTime;

        public String getAdr() {
            return adr;
        }

        public void setAdr(String adr) {
            this.adr = adr;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getItemState() {
            return itemState;
        }

        public void setItemState(int itemState) {
            this.itemState = itemState;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUpTime() {
            return upTime;
        }

        public void setUpTime(String upTime) {
            this.upTime = upTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
