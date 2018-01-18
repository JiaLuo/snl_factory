package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/11/1.
 */

public class OfflineAuthorityEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private OfflineAuthorityResult result;

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

    public OfflineAuthorityResult getResult() {
        return result;
    }

    public void setResult(OfflineAuthorityResult result) {
        this.result = result;
    }

    public class OfflineAuthorityResult{
        @SerializedName("message_count")
        private String messageCount;
        @SerializedName("mer_logo")
        private String merLogo;
        @SerializedName("mer_name")
        private String merName;
        @SerializedName("might")
        private List<OfflineAuthorityMight> mights;

        public String getMessageCount() {
            return messageCount;
        }

        public void setMessageCount(String messageCount) {
            this.messageCount = messageCount;
        }

        public String getMerLogo() {
            return merLogo;
        }

        public void setMerLogo(String merLogo) {
            this.merLogo = merLogo;
        }

        public String getMerName() {
            return merName;
        }

        public void setMerName(String merName) {
            this.merName = merName;
        }

        public List<OfflineAuthorityMight> getMights() {
            return mights;
        }

        public void setMights(List<OfflineAuthorityMight> mights) {
            this.mights = mights;
        }

        public class OfflineAuthorityMight{
            @SerializedName("module")
            private String module;
            @SerializedName("module_name")
            private String module_name;
            @SerializedName("state")
            private String state;

            public String getModule() {
                return module;
            }

            public void setModule(String module) {
                this.module = module;
            }

            public String getModule_name() {
                return module_name;
            }

            public void setModule_name(String module_name) {
                this.module_name = module_name;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    }
}
