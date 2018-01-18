package com.shinaier.laundry.snlfactory.manage.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/10/30.
 */

public class AddEmployeeJurisdictionEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<AddEmployeeJurisdictionResult> results;

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

    public List<AddEmployeeJurisdictionResult> getResults() {
        return results;
    }

    public void setResults(List<AddEmployeeJurisdictionResult> results) {
        this.results = results;
    }

    public class AddEmployeeJurisdictionResult{
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
}
