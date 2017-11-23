package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/22.
 */

public class BuyPlateformCardEntity  {
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<BuyPlateformCardDatas> datas;

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

    public List<BuyPlateformCardDatas> getDatas() {
        return datas;
    }

    public void setDatas(List<BuyPlateformCardDatas> datas) {
        this.datas = datas;
    }

    public class BuyPlateformCardDatas{
        @SerializedName("cardtype")
        private String cardtype;
        @SerializedName("cardsum")
        private String cardsum;
        @SerializedName("carddiscount")
        private String carddiscount;
        @SerializedName("state")
        private String state;
        @SerializedName("cardname")
        private String cardname;

        public String getCardtype() {
            return cardtype;
        }

        public void setCardtype(String cardtype) {
            this.cardtype = cardtype;
        }

        public String getCardsum() {
            return cardsum;
        }

        public void setCardsum(String cardsum) {
            this.cardsum = cardsum;
        }

        public String getCarddiscount() {
            return carddiscount;
        }

        public void setCarddiscount(String carddiscount) {
            this.carddiscount = carddiscount;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCardname() {
            return cardname;
        }

        public void setCardname(String cardname) {
            this.cardname = cardname;
        }
    }
}
