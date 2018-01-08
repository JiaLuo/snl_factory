package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/3.
 */

public class OfflineMemberConsumeListEntity {
    /**
     * {
     "code": 0,
     "msg": "SUCCESS",
     "result": {
     "sum": “225.00",           消费总额
     "count": “1",                   累计单数
     "record": [                         记录列表
     {
     "ordersn": “171214104439844389",        订单号
     "umobile": “18745729547",                    手机号
     "pay_amount": “225.00",                    支付金额
     "pay_time": “2017.12.21"                  支付时间
     }
     ],
     "page_count": 1                                          总页数
     }
     }
     }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private OfflineMemberConsumeResult result;

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

    public OfflineMemberConsumeResult getResult() {
        return result;
    }

    public void setResult(OfflineMemberConsumeResult result) {
        this.result = result;
    }

    public class OfflineMemberConsumeResult{
        @SerializedName("count")
        private String count;
        @SerializedName("sum")
        private String sum;
        @SerializedName("record")
        private List<OfflineMemberConsumeRecord> consumeRecord;
        @SerializedName("page_count")
        private int pageCount;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
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
            @SerializedName("ordersn")
            private String ordersn;
            @SerializedName("umobile")
            private String uMobile;
            @SerializedName("pay_amount")
            private String payAmount;
            @SerializedName("pay_time")
            private String payTime;

            public String getOrdersn() {
                return ordersn;
            }

            public void setOrdersn(String ordersn) {
                this.ordersn = ordersn;
            }

            public String getuMobile() {
                return uMobile;
            }

            public void setuMobile(String uMobile) {
                this.uMobile = uMobile;
            }

            public String getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(String payAmount) {
                this.payAmount = payAmount;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }
        }
    }
}
