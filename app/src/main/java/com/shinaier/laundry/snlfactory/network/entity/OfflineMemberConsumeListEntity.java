package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/3.
 */

public class OfflineMemberConsumeListEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OfflineMemberConsumeDatas datas;

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

    public OfflineMemberConsumeDatas getDatas() {
        return datas;
    }

    public void setDatas(OfflineMemberConsumeDatas datas) {
        this.datas = datas;
    }

    public class OfflineMemberConsumeDatas{
        @SerializedName("order_count")
        private String orderCount;
        @SerializedName("order_total_amount")
        private String orderTotalAmount;
        @SerializedName("record")
        private List<OfflineMemberConsumeRecord> consumeRecord;
        @SerializedName("page_count")
        private int pageCount;

        public String getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(String orderCount) {
            this.orderCount = orderCount;
        }

        public String getOrderTotalAmount() {
            return orderTotalAmount;
        }

        public void setOrderTotalAmount(String orderTotalAmount) {
            this.orderTotalAmount = orderTotalAmount;
        }

        public List<OfflineMemberConsumeRecord> getConsumeRecord() {
            return consumeRecord;
        }

        public void setConsumeRecord(List<OfflineMemberConsumeRecord> consumeRecord) {
            this.consumeRecord = consumeRecord;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public class OfflineMemberConsumeRecord{
            @SerializedName("id")
            private String id;
            @SerializedName("ucode")
            private String ucode;
            @SerializedName("ordersn")
            private String ordersn;
            @SerializedName("pay_amount")
            private String payAmount;
            @SerializedName("create_time")
            private String createTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUcode() {
                return ucode;
            }

            public void setUcode(String ucode) {
                this.ucode = ucode;
            }

            public String getOrdersn() {
                return ordersn;
            }

            public void setOrdersn(String ordersn) {
                this.ordersn = ordersn;
            }

            public String getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(String payAmount) {
                this.payAmount = payAmount;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

    }
}
