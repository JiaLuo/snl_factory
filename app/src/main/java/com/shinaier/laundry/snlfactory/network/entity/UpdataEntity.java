package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/5/26.
 */

public class UpdataEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("states")
    private String states;
    @SerializedName("data")
    private UpdataDatas datas;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public UpdataDatas getDatas() {
        return datas;
    }

    public void setDatas(UpdataDatas datas) {
        this.datas = datas;
    }

    public class UpdataDatas{
        @SerializedName("url")
        private String url;
        @SerializedName("version_content")
        private String versionContent;
        @SerializedName("version_num")
        private String versionNum;
        @SerializedName("version_size")
        private String versionSize;

        public String getVersionContent() {
            return versionContent;
        }

        public void setVersionContent(String versionContent) {
            this.versionContent = versionContent;
        }

        public String getVersionNum() {
            return versionNum;
        }

        public void setVersionNum(String versionNum) {
            this.versionNum = versionNum;
        }

        public String getVersionSize() {
            return versionSize;
        }

        public void setVersionSize(String versionSize) {
            this.versionSize = versionSize;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
