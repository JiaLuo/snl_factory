package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/7/28.
 */

public class OfflineMemberBalanceEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OfflineMemberBalanceDatas datas;

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

    public OfflineMemberBalanceDatas getDatas() {
        return datas;
    }

    public void setDatas(OfflineMemberBalanceDatas datas) {
        this.datas = datas;
    }

    public class OfflineMemberBalanceDatas{
        @SerializedName("member_count")
        private String memberCount;
        @SerializedName("member_total_balance")
        private String memberTotalBalance;
        @SerializedName("member_list")
        private List<MemberList> memberLists;
        @SerializedName("page_count")
        private int pageCount;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public String getMemberCount() {
            return memberCount;
        }

        public void setMemberCount(String memberCount) {
            this.memberCount = memberCount;
        }

        public String getMemberTotalBalance() {
            return memberTotalBalance;
        }

        public void setMemberTotalBalance(String memberTotalBalance) {
            this.memberTotalBalance = memberTotalBalance;
        }

        public List<MemberList> getMemberLists() {
            return memberLists;
        }

        public void setMemberLists(List<MemberList> memberLists) {
            this.memberLists = memberLists;
        }

        public class MemberList{
            @SerializedName("ucode")
            private String ucode;
            @SerializedName("username")
            private String userName;
            @SerializedName("mobile")
            private String mobile;
            @SerializedName("balance")
            private String balance;
            @SerializedName("card_name")
            private String cardName;
            @SerializedName("register_time")
            private String registerTime;

            public String getUcode() {
                return ucode;
            }

            public void setUcode(String ucode) {
                this.ucode = ucode;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getCardName() {
                return cardName;
            }

            public void setCardName(String cardName) {
                this.cardName = cardName;
            }

            public String getRegisterTime() {
                return registerTime;
            }

            public void setRegisterTime(String registerTime) {
                this.registerTime = registerTime;
            }
        }
    }
}
