package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/12/15.
 */

public class CashCouponCenterEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<CashCouponCenterResult> result;

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

    public List<CashCouponCenterResult> getResult() {
        return result;
    }

    public void setResult(List<CashCouponCenterResult> result) {
        this.result = result;
    }

    public class CashCouponCenterResult{
        @SerializedName("date")
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<CashCouponCenterRecords> getRecordses() {
            return recordses;
        }

        public void setRecordses(List<CashCouponCenterRecords> recordses) {
            this.recordses = recordses;
        }

        @SerializedName("records")

        private List<CashCouponCenterRecords> recordses;
        public class CashCouponCenterRecords{
            @SerializedName("id")
            private String id;
            @SerializedName("type")
            private String type;
            @SerializedName("make_count")
            private String makeCount;
            @SerializedName("used_count")
            private String usedCount;
            @SerializedName("make_time")
            private String makeTime;
            @SerializedName("make_value")
            private String makeValue;

            public String getMakeValue() {
                return makeValue;
            }

            public void setMakeValue(String makeValue) {
                this.makeValue = makeValue;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getMakeCount() {
                return makeCount;
            }

            public void setMakeCount(String makeCount) {
                this.makeCount = makeCount;
            }

            public String getUsedCount() {
                return usedCount;
            }

            public void setUsedCount(String usedCount) {
                this.usedCount = usedCount;
            }

            public String getMakeTime() {
                return makeTime;
            }

            public void setMakeTime(String makeTime) {
                this.makeTime = makeTime;
            }
        }
    }
}
