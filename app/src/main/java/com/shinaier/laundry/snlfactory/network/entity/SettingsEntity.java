package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/2/18.
 */

public class SettingsEntity {
    /**
     * {
     "code": 0,
     "msg": "SUCCESS",
     "result": {
     "mname": "测试店铺",
     "mlogo": "http://snltest.oss-cn-beijing.aliyuncs.com/clean/2017/12/02/5a22885b704b78.72938771.jpg",
     "bank_card": “0232****0323”,
     "mobile_number": "18745729547",
     "manager": “王海华",              区域经理
     "service": “010-58207998”    客服电话
     }
     }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private SettingsResult result;

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

    public SettingsResult getResult() {
        return result;
    }

    public void setResult(SettingsResult result) {
        this.result = result;
    }

    public class SettingsResult{

        @SerializedName("mname")
        private String mName;
        @SerializedName("mlogo")
        private String mLogo;
        @SerializedName("bank_card")
        private String bankCard;
        @SerializedName("mobile_number")
        private String mobileNumber;
        @SerializedName("manager")
        private String manager;
        @SerializedName("service")
        private String service;

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getmLogo() {
            return mLogo;
        }

        public void setmLogo(String mLogo) {
            this.mLogo = mLogo;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getManager() {
            return manager;
        }

        public void setManager(String manager) {
            this.manager = manager;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }
    }
}
