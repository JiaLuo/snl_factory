package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;
import com.shinaier.laundry.snlfactory.ordermanage.entities.GoodsBean;

import java.util.List;

/**
 * Created by 张家洛 on 2018/1/2.
 */

public class StoreDetailEntity {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<StoreDetailResult> result;

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

    public List<StoreDetailResult> getResult() {
        return result;
    }

    public void setResult(List<StoreDetailResult> result) {
        this.result = result;
    }

    public class StoreDetailResult{
        @SerializedName("cate_name")
        private String cate_name;
        @SerializedName("cate_en")
        private String cateEn;
        @SerializedName("items")
        private List<GoodsBean> items;

        private int typeId;

        public String getCateEn() {
            return cateEn;
        }

        public void setCateEn(String cateEn) {
            this.cateEn = cateEn;
        }

        public List<GoodsBean> getItems() {
            return items;
        }

        public void setItems(List<GoodsBean> items) {
            this.items = items;
        }


        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

    }
}
