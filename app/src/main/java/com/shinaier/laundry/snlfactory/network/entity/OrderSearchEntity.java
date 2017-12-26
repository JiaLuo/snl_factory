package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/12/25.
 */

public class OrderSearchEntity {
    /**
     * {
     "code": 0,
     "msg": "SUCCESS",
     "result": [
     {
     "id": “9",       订单ID
     "ostatus": “102",        订单状态
     "ordersn": “171216174547932730",    订单编号
     "pay_amount": “0.00",                         用户支付总额
     "amount": “0.00",                                衣物总额
     "freight_price": “0.00",                         运费
     "craft_price": “0.00",                           工艺加价
     "keep_price": “0.00",                          保值金额
     "reduce_price": “0.00”,                        优惠金额
     "otime": "2017-12-16 17:45:47”,           订单时间
     "uname": “杨云龙",
     "umobile": "18745729547",
     "uaddress": "北京市朝阳区万达广场三号楼1902室",
     "items": [                                                 项目列表
     {
     "item_name": "衬衣",
     "item_price": "15.00"
     },
     {
     "item_name": "衬衣",
     "item_price": "15.00"
     },
     {
     "item_name": "大衣",
     "item_price": "15.00"
     }
     ],
     "item_count": 3                                    项目数量
     }
     ]
     }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<OrderSearchResult> results;
    @SerializedName("count")
    private int count;
    @SerializedName("page_count")
    private int pageCount;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

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

    public List<OrderSearchResult> getResults() {
        return results;
    }

    public void setResults(List<OrderSearchResult> results) {
        this.results = results;
    }

    public class OrderSearchResult{
        @SerializedName("id")
        private String id;
        @SerializedName("ostatus")
        private String oStatus;
        @SerializedName("ordersn")
        private String ordersn;
        @SerializedName("pay_amount")
        private String payAmount;
        @SerializedName("amount")
        private String amount;
        @SerializedName("freight_price")
        private String freightPrice;
        @SerializedName("craft_price")
        private String craftPrice;
        @SerializedName("keep_price")
        private String keepPrice;
        @SerializedName("reduce_price")
        private String reducePrice;
        @SerializedName("otime")
        private String oTime;
        @SerializedName("uname")
        private String uName;
        @SerializedName("umobile")
        private String uMobile;
        @SerializedName("uaddress")
        private String uAddress;
        @SerializedName("items")
        private List<OrderSearchItems> itemses;
        @SerializedName("item_count")
        private String itemCount;

        public boolean isOpen = false;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getoStatus() {
            return oStatus;
        }

        public void setoStatus(String oStatus) {
            this.oStatus = oStatus;
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getFreightPrice() {
            return freightPrice;
        }

        public void setFreightPrice(String freightPrice) {
            this.freightPrice = freightPrice;
        }

        public String getCraftPrice() {
            return craftPrice;
        }

        public void setCraftPrice(String craftPrice) {
            this.craftPrice = craftPrice;
        }

        public String getKeepPrice() {
            return keepPrice;
        }

        public void setKeepPrice(String keepPrice) {
            this.keepPrice = keepPrice;
        }

        public String getReducePrice() {
            return reducePrice;
        }

        public void setReducePrice(String reducePrice) {
            this.reducePrice = reducePrice;
        }

        public String getoTime() {
            return oTime;
        }

        public void setoTime(String oTime) {
            this.oTime = oTime;
        }

        public String getuName() {
            return uName;
        }

        public void setuName(String uName) {
            this.uName = uName;
        }

        public String getuMobile() {
            return uMobile;
        }

        public void setuMobile(String uMobile) {
            this.uMobile = uMobile;
        }

        public String getuAddress() {
            return uAddress;
        }

        public void setuAddress(String uAddress) {
            this.uAddress = uAddress;
        }

        public List<OrderSearchItems> getItemses() {
            return itemses;
        }

        public void setItemses(List<OrderSearchItems> itemses) {
            this.itemses = itemses;
        }

        public String getItemCount() {
            return itemCount;
        }

        public void setItemCount(String itemCount) {
            this.itemCount = itemCount;
        }

        public class OrderSearchItems{
            @SerializedName("item_name")
            private String itemName;
            @SerializedName("item_price")
            private String itemPrice;

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getItemPrice() {
                return itemPrice;
            }

            public void setItemPrice(String itemPrice) {
                this.itemPrice = itemPrice;
            }
        }
    }
}
