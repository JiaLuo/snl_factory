package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/1.
 */

public class StatisticsNoPayEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private StatisticsData data;

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

    public StatisticsData getData() {
        return data;
    }

    public void setData(StatisticsData data) {
        this.data = data;
    }

    public class StatisticsData{
        @SerializedName("page_count")
        private int pageCount;
        @SerializedName("orders")
        private List<StatisticsOrders> orders;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<StatisticsOrders> getOrders() {
            return orders;
        }

        public void setOrders(List<StatisticsOrders> orders) {
            this.orders = orders;
        }

        public class StatisticsOrders{
            @SerializedName("date")
            private String date;
            @SerializedName("orders")
            private List<StatisticsInnerOrders> innerOrders;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public List<StatisticsInnerOrders> getInnerOrders() {
                return innerOrders;
            }

            public void setInnerOrders(List<StatisticsInnerOrders> innerOrders) {
                this.innerOrders = innerOrders;
            }

            public class StatisticsInnerOrders{
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
