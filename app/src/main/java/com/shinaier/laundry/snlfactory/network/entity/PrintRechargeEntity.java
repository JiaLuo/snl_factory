package com.shinaier.laundry.snlfactory.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/8/19.
 */

public class PrintRechargeEntity {
    /**
     * {
     "code": 0,
     "msg": "SUCCESS",
     "result": {
     "trade_sn": “",                     交易单号
     "umobile": “18519234689",       用户手机号
     "amount": “100.00",                充值金额
     "give": “0.00",                         赠送金额
     "maddress": "短发短发短发 vv”,       店铺地址
     "phone_number": “18500000000",          店铺电话
     "mid": “108",                                   商家ID
     "employee": “店主",                       员工姓名
     "qrcode": "http:\/\/xiyi.wzj.dev.shuxier.com\/static\/qrcode.jpg"                  二维码地址
     }
     }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private PrintRechargeResult result;

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

    public PrintRechargeResult getResult() {
        return result;
    }

    public void setResult(PrintRechargeResult result) {
        this.result = result;
    }

    public static class PrintRechargeResult implements Parcelable{
        @SerializedName("trade_sn")
        private String tradeSn;
        @SerializedName("umobile")
        private String umobile;
        @SerializedName("amount")
        private String amount;
        @SerializedName("give")
        private String give;
        @SerializedName("maddress")
        private String maddress;
        @SerializedName("phone_number")
        private String phoneNumber;
        @SerializedName("mid")
        private String mid;
        @SerializedName("mname")
        private String mName;
        @SerializedName("employee")
        private String employee;
        @SerializedName("qrcode")
        private String qrcode;
        @SerializedName("gateway")
        private String gateway;
        @SerializedName("cbalance")
        private String cBalance;

        protected PrintRechargeResult(Parcel in) {
            tradeSn = in.readString();
            umobile = in.readString();
            amount = in.readString();
            give = in.readString();
            maddress = in.readString();
            phoneNumber = in.readString();
            mid = in.readString();
            mName = in.readString();
            employee = in.readString();
            qrcode = in.readString();
            gateway = in.readString();
            cBalance = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(tradeSn);
            dest.writeString(umobile);
            dest.writeString(amount);
            dest.writeString(give);
            dest.writeString(maddress);
            dest.writeString(phoneNumber);
            dest.writeString(mid);
            dest.writeString(mName);
            dest.writeString(employee);
            dest.writeString(qrcode);
            dest.writeString(gateway);
            dest.writeString(cBalance);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<PrintRechargeResult> CREATOR = new Creator<PrintRechargeResult>() {
            @Override
            public PrintRechargeResult createFromParcel(Parcel in) {
                return new PrintRechargeResult(in);
            }

            @Override
            public PrintRechargeResult[] newArray(int size) {
                return new PrintRechargeResult[size];
            }
        };

        public String getcBalance() {
            return cBalance;
        }

        public void setcBalance(String cBalance) {
            this.cBalance = cBalance;
        }

        public String getTradeSn() {
            return tradeSn;
        }

        public void setTradeSn(String tradeSn) {
            this.tradeSn = tradeSn;
        }

        public String getUmobile() {
            return umobile;
        }

        public void setUmobile(String umobile) {
            this.umobile = umobile;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getGive() {
            return give;
        }

        public void setGive(String give) {
            this.give = give;
        }

        public String getMaddress() {
            return maddress;
        }

        public void setMaddress(String maddress) {
            this.maddress = maddress;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getEmployee() {
            return employee;
        }

        public void setEmployee(String employee) {
            this.employee = employee;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }
    }
}
