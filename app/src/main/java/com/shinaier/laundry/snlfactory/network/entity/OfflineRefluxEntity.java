package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2018/1/15.
 */

public class OfflineRefluxEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<OfflineRefluxResult> refluxResults;

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

    public List<OfflineRefluxResult> getRefluxResults() {
        return refluxResults;
    }

    public void setRefluxResults(List<OfflineRefluxResult> refluxResults) {
        this.refluxResults = refluxResults;
    }

    public class OfflineRefluxResult{
        @SerializedName("id")
        private String id;
        @SerializedName("item_name")
        private String itemName;
        @SerializedName("is_back")
        private String isBack;
        @SerializedName("back_state")
        private String backState;
        @SerializedName("back_content")
        private String backContent;
        @SerializedName("clean_sn")
        private String cleanSn;
        @SerializedName("image")
        private String image;
        @SerializedName("back_name")
        private String backName;
        @SerializedName("back_img")
        private List<String> backImg;

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

        public String getIsBack() {
            return isBack;
        }

        public void setIsBack(String isBack) {
            this.isBack = isBack;
        }

        public String getBackState() {
            return backState;
        }

        public void setBackState(String backState) {
            this.backState = backState;
        }

        public String getBackContent() {
            return backContent;
        }

        public void setBackContent(String backContent) {
            this.backContent = backContent;
        }

        public String getCleanSn() {
            return cleanSn;
        }

        public void setCleanSn(String cleanSn) {
            this.cleanSn = cleanSn;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getBackName() {
            return backName;
        }

        public void setBackName(String backName) {
            this.backName = backName;
        }

        public List<String> getBackImg() {
            return backImg;
        }

        public void setBackImg(List<String> backImg) {
            this.backImg = backImg;
        }
    }
}
