package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/7/25.
 */

public class TakeClothesEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private TakeClothesResult result;

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

    public TakeClothesResult getResult() {
        return result;
    }

    public void setResult(TakeClothesResult result) {
        this.result = result;
    }

    public class TakeClothesResult{
        @SerializedName("count_total")
        private int countTotal;
        @SerializedName("userinfo")
        private TakeClothesUserInfo userInfo;
        @SerializedName("list")
        private List<TakeClothesList> lists;

        public int getCountTotal() {
            return countTotal;
        }

        public void setCountTotal(int countTotal) {
            this.countTotal = countTotal;
        }

        public TakeClothesUserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(TakeClothesUserInfo userInfo) {
            this.userInfo = userInfo;
        }

        public List<TakeClothesList> getLists() {
            return lists;
        }

        public void setLists(List<TakeClothesList> lists) {
            this.lists = lists;
        }

        public class TakeClothesList{
            @SerializedName("id")
            private String id;
            @SerializedName("ordersn")
            private String ordersn;
            @SerializedName("pay_amount")
            private String payAmount;
            @SerializedName("pay_state")
            private String payState;
            @SerializedName("otime")
            private String oTime;
            @SerializedName("items")
            private List<TakeClothesItems> itemses;
            @SerializedName("count")
            private int count;

            public boolean isOpen = false;

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

            public String getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(String payAmount) {
                this.payAmount = payAmount;
            }

            public String getPayState() {
                return payState;
            }

            public void setPayState(String payState) {
                this.payState = payState;
            }

            public String getoTime() {
                return oTime;
            }

            public void setoTime(String oTime) {
                this.oTime = oTime;
            }

            public List<TakeClothesItems> getItemses() {
                return itemses;
            }

            public void setItemses(List<TakeClothesItems> itemses) {
                this.itemses = itemses;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public class TakeClothesItems{
                @SerializedName("id")
                private String id;
                @SerializedName("item_name")
                private String itemName;
                @SerializedName("status")
                private String status;
                @SerializedName("put_sn")
                private String putSn;

                public boolean isSelect = false;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getItemName() {
                    return itemName;
                }

                public void setItemName(String itemName) {
                    this.itemName = itemName;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getPutSn() {
                    return putSn;
                }

                public void setPutSn(String putSn) {
                    this.putSn = putSn;
                }
            }
        }

        public class TakeClothesUserInfo{
            @SerializedName("uname")
            private String uName;
            @SerializedName("umobile")
            private String uMobile;

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
        }
    }
}
