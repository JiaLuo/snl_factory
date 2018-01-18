package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/16.
 */

public class EmployeeEntity {
    /**
     * {
     "code": 0,
     "msg": "SUCCESS",
     "result": [
     {
     "id": "235",   员工id
     "aname": "我就是我",  员工名称
     "account": "13681030211"    登录账号（手机号）
     }
     ]
     }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<EmployeeResult> results;

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

    public List<EmployeeResult> getResults() {
        return results;
    }

    public void setResults(List<EmployeeResult> results) {
        this.results = results;
    }

    public class EmployeeResult{
        @SerializedName("id")
        private String id;
        @SerializedName("aname")
        private String aName;
        @SerializedName("account")
        private String account;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
