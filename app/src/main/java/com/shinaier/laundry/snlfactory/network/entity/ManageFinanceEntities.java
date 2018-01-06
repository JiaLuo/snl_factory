package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/10.
 */

public class ManageFinanceEntities {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private ManageFinanceResult result;

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

    public ManageFinanceResult getResult() {
        return result;
    }

    public void setResult(ManageFinanceResult result) {
        this.result = result;
    }

    public class ManageFinanceResult{
        @SerializedName("balance")
        private String balance;
        @SerializedName("account")
        private String account;
        @SerializedName("bank")
        private String bank;
        @SerializedName("record")
        private List<ManageFinanceRecord> records;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public List<ManageFinanceRecord> getRecords() {
            return records;
        }

        public void setRecords(List<ManageFinanceRecord> records) {
            this.records = records;
        }

        public class ManageFinanceRecord{
            @SerializedName("type")
            private String type;
            @SerializedName("amount")
            private String amount;
            @SerializedName("real_amount")
            private String realAmount;
            @SerializedName("platform_gain")
            private String platformGain;
            @SerializedName("trade_time")
            private String tradeTime;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getRealAmount() {
                return realAmount;
            }

            public void setRealAmount(String realAmount) {
                this.realAmount = realAmount;
            }

            public String getPlatformGain() {
                return platformGain;
            }

            public void setPlatformGain(String platformGain) {
                this.platformGain = platformGain;
            }

            public String getTradeTime() {
                return tradeTime;
            }

            public void setTradeTime(String tradeTime) {
                this.tradeTime = tradeTime;
            }
        }
    }
}
