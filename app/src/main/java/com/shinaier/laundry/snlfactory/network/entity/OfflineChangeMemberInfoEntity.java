package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张家洛 on 2017/12/26.
 */

public class OfflineChangeMemberInfoEntity {
    /**
     * {
     "code": 0,
     "msg": "SUCCESS",
     "result": {
     "id": “6”,          用户ID
     "uname": “18745729547",       用户名称
     "umobile": “18745729547”,       用户手机号
     "sex": “0",                                用户性别:1-男，2-女，0-未填写
     "birthday": “",                           生日
     "is_company": “0",                   是否为企业会员:1-是；0-否
     "addr": “",                                  用户地址
     "remark": “”,                             用户备注
     "cdiscount": “10"                        会员卡折扣
     }
     }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private OfflineChangeMemberInfoResult result;

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

    public OfflineChangeMemberInfoResult getResult() {
        return result;
    }

    public void setResult(OfflineChangeMemberInfoResult result) {
        this.result = result;
    }

    public class OfflineChangeMemberInfoResult{
        @SerializedName("id")
        private String id;
        @SerializedName("uname")
        private String uName;
        @SerializedName("umobile")
        private String uMobile;
        @SerializedName("sex")
        private String sex;
        @SerializedName("birthday")
        private String birthday;
        @SerializedName("is_company")
        private String isCompany;
        @SerializedName("addr")
        private String addr;
        @SerializedName("remark")
        private String remark;
        @SerializedName("cdiscount")
        private String cdiscount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getuName() {
            return uName;
        }

        public void setuName(String uName) {
            this.uName = uName;
        }

        public String getuMobile() {
            return uMobile;
        }

        public void setuMobile(String uMobile) {
            this.uMobile = uMobile;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getIsCompany() {
            return isCompany;
        }

        public void setIsCompany(String isCompany) {
            this.isCompany = isCompany;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCdiscount() {
            return cdiscount;
        }

        public void setCdiscount(String cdiscount) {
            this.cdiscount = cdiscount;
        }
    }
}
