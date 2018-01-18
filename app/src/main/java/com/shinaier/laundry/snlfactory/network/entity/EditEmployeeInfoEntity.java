package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/11/2.
 */

public class EditEmployeeInfoEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private EditEmployeeInfoResult result;

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

    public EditEmployeeInfoResult getResult() {
        return result;
    }

    public void setResult(EditEmployeeInfoResult result) {
        this.result = result;
    }

    public class EditEmployeeInfoResult{
        @SerializedName("data")
        private EditEmployeeData data;
        @SerializedName("might")
        private List<EditEmployeeSelectAuthority> selectAuthorities;

        public EditEmployeeData getData() {
            return data;
        }

        public void setData(EditEmployeeData data) {
            this.data = data;
        }

        public List<EditEmployeeSelectAuthority> getSelectAuthorities() {
            return selectAuthorities;
        }

        public void setSelectAuthorities(List<EditEmployeeSelectAuthority> selectAuthorities) {
            this.selectAuthorities = selectAuthorities;
        }

        public class EditEmployeeSelectAuthority{
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

        public class EditEmployeeData{
            @SerializedName("mid")
            private String mId;
            @SerializedName("aname")
            private String aName;
            @SerializedName("account")
            private String account;
            @SerializedName("auth")
            private String auth;

            public String getmId() {
                return mId;
            }

            public void setmId(String mId) {
                this.mId = mId;
            }

            public String getaName() {
                return aName;
            }

            public void setaName(String aName) {
                this.aName = aName;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getAuth() {
                return auth;
            }

            public void setAuth(String auth) {
                this.auth = auth;
            }
        }
    }
}
