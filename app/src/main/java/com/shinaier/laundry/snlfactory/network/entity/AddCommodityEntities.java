package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/14.
 */

public class AddCommodityEntities {
    /**
     * {
     "code": 0,
     "msg": "SUCCESS",
     "result": [
     {
     "id": “1",    项目ID
     "item_name": “衬衣",    项目名称
     "item_price": “15.00",    项目价格
     "item_cycle": “3”           项目洗护周期
     },
     {
     "id": "2",
     "item_name": "大衣",
     "item_price": "15.00",
     "item_cycle": "3"
     }
     ]
     }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<AddCommodityResult> result;

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

    public List<AddCommodityResult> getResult() {
        return result;
    }

    public void setResult(List<AddCommodityResult> result) {
        this.result = result;
    }

    public class AddCommodityResult{
        @SerializedName("id")
        private String id;
        @SerializedName("item_name")
        private String itemName;
        @SerializedName("item_price")
        private String itemPrice;
        @SerializedName("item_cycle")
        private String itemCycle;

        public boolean isChanged = false;

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

        public String getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(String itemPrice) {
            this.itemPrice = itemPrice;
        }

        public String getItemCycle() {
            return itemCycle;
        }

        public void setItemCycle(String itemCycle) {
            this.itemCycle = itemCycle;
        }
    }
}
