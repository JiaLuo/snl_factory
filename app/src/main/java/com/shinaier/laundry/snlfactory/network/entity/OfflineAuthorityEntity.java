package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/11/1.
 */

public class OfflineAuthorityEntity {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private OfflineAuthorityDatas datas;

    public OfflineAuthorityDatas getDatas() {
        return datas;
    }

    public void setDatas(OfflineAuthorityDatas datas) {
        this.datas = datas;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public class OfflineAuthorityDatas{
        @SerializedName("no_read_number")
        private int noReadNumber;
        @SerializedName("merchant")
        private OfflineAuthorityMerchant merchant;
        @SerializedName("data")
        private List<OfflineAuthorities> authorities;

        public int getNoReadNumber() {
            return noReadNumber;
        }

        public void setNoReadNumber(int noReadNumber) {
            this.noReadNumber = noReadNumber;
        }

        public List<OfflineAuthorities> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(List<OfflineAuthorities> authorities) {
            this.authorities = authorities;
        }

        public OfflineAuthorityMerchant getMerchant() {
            return merchant;
        }

        public void setMerchant(OfflineAuthorityMerchant merchant) {
            this.merchant = merchant;
        }



        public class OfflineAuthorities{
            @SerializedName("value")
            private String value;
            @SerializedName("name")
            private String name;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public class OfflineAuthorityMerchant{
            @SerializedName("mname")
            private String mName;
            @SerializedName("logo")
            private String logo;

            public String getmName() {
                return mName;
            }

            public void setmName(String mName) {
                this.mName = mName;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }

    }
}
