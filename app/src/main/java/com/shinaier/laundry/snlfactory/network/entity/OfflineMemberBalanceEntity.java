package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/7/28.
 */

public class OfflineMemberBalanceEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private OfflineMemberBalanceResult result;

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

    public OfflineMemberBalanceResult getResult() {
        return result;
    }

    public void setResult(OfflineMemberBalanceResult result) {
        this.result = result;
    }

    public class OfflineMemberBalanceResult{
        @SerializedName("count")
        private String count;
        @SerializedName("sum")
        private String sum;
        @SerializedName("record")
        private List<OfflineMemberBalanceRecord> memberLists;
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

        public List<OfflineMemberBalanceRecord> getMemberLists() {
            return memberLists;
        }

        public void setMemberLists(List<OfflineMemberBalanceRecord> memberLists) {
            this.memberLists = memberLists;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public class OfflineMemberBalanceRecord{
            @SerializedName("umobile")
            private String uMobile;
            @SerializedName("uname")
            private String uName;
            @SerializedName("cname")
            private String cName;
            @SerializedName("cbalance")
            private String cBalance;
            @SerializedName("ctime")
            private String cTime;

            public String getuMobile() {
                return uMobile;
            }

            public void setuMobile(String uMobile) {
                this.uMobile = uMobile;
            }

            public String getuName() {
                return uName;
            }

            public void setuName(String uName) {
                this.uName = uName;
            }

            public String getcName() {
                return cName;
            }

            public void setcName(String cName) {
                this.cName = cName;
            }

            public String getcBalance() {
                return cBalance;
            }

            public void setcBalance(String cBalance) {
                this.cBalance = cBalance;
            }

            public String getcTime() {
                return cTime;
            }

            public void setcTime(String cTime) {
                this.cTime = cTime;
            }
        }
    }
}
