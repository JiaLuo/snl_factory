package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/1.
 */

public class StatisticsNoDoneEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private StatisticsNoDoneDatas datas;

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

    public StatisticsNoDoneDatas getDatas() {
        return datas;
    }

    public void setDatas(StatisticsNoDoneDatas datas) {
        this.datas = datas;
    }

    public class StatisticsNoDoneDatas{
        @SerializedName("page_count")
        private int pageCount;
        @SerializedName("orders")
        private List<StatisticsNoDoneOrders> orders;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<StatisticsNoDoneOrders> getOrders() {
            return orders;
        }

        public void setOrders(List<StatisticsNoDoneOrders> orders) {
            this.orders = orders;
        }

        public class StatisticsNoDoneOrders{
            @SerializedName("date")
            private String date;
            @SerializedName("orders")
            private List<StatisticsNoDoneInnerOrder> orders;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public List<StatisticsNoDoneInnerOrder> getOrders() {
                return orders;
            }

            public void setOrders(List<StatisticsNoDoneInnerOrder> orders) {
                this.orders = orders;
            }

            public class StatisticsNoDoneInnerOrder{
                @SerializedName("id")
                private String id;
                @SerializedName("ordersn")
                private String ordersn;
                @SerializedName("amount")
                private String amount;
                @SerializedName("create_time")
                private String createTime;

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

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
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
}
