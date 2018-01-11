package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/22.
 */

public class BuyPlateformCardEntity  {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<BuyPlateformCardResult> results;

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

    public List<BuyPlateformCardResult> getResults() {
        return results;
    }

    public void setResults(List<BuyPlateformCardResult> results) {
        this.results = results;
    }

    public class BuyPlateformCardResult{
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("discount")
        private String discount;
        @SerializedName("price")
        private String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
