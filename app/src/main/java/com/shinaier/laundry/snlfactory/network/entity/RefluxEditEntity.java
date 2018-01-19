package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2018/1/15.
 */

public class RefluxEditEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private RefluxEditResult result;

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

    public RefluxEditResult getResult() {
        return result;
    }

    public void setResult(RefluxEditResult result) {
        this.result = result;
    }

    public class RefluxEditResult{
        @SerializedName("data")
        private RefluxEditData data;
        @SerializedName("module")
        private List<RefluxEditModule> refluxEditModules;


        public RefluxEditData getData() {
            return data;
        }

        public void setData(RefluxEditData data) {
            this.data = data;
        }

        public List<RefluxEditModule> getRefluxEditModules() {
            return refluxEditModules;
        }

        public void setRefluxEditModules(List<RefluxEditModule> refluxEditModules) {
            this.refluxEditModules = refluxEditModules;
        }

        public class RefluxEditModule{
            @SerializedName("module")
            private String module;
            @SerializedName("module_name")
            private String moduleName;

            public boolean isSelect = false;

            public String getModule() {
                return module;
            }

            public void setModule(String module) {
                this.module = module;
            }

            public String getModuleName() {
                return moduleName;
            }

            public void setModuleName(String moduleName) {
                this.moduleName = moduleName;
            }
        }

        public class RefluxEditData{
            @SerializedName("item_name")
            private String itemName;
            @SerializedName("image")
            private String image;
            @SerializedName("clean_sn")
            private String cleanSn;
            @SerializedName("back_img")
            private List<String> url;

            public List<String> getUrl() {
                return url;
            }

            public void setUrl(List<String> url) {
                this.url = url;
            }

            public String getCleanSn() {
                return cleanSn;
            }

            public void setCleanSn(String cleanSn) {
                this.cleanSn = cleanSn;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
