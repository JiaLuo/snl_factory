package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2018/1/16.
 */

public class CooperativeStoreOperateEntities {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<CooperativeFactoryResult> results;

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

    public List<CooperativeFactoryResult> getResults() {
        return results;
    }

    public void setResults(List<CooperativeFactoryResult> results) {
        this.results = results;
    }

    public class CooperativeFactoryResult{
        @SerializedName("accept_id")
        private String acceptId;
        @SerializedName("mname")
        private String mName;

        public boolean isSelect = false;

        public String getAcceptId() {
            return acceptId;
        }

        public void setAcceptId(String acceptId) {
            this.acceptId = acceptId;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }
    }
}
