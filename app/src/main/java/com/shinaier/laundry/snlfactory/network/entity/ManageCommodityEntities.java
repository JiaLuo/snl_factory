package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/14.
 */

public class ManageCommodityEntities {
    /**
     * {
     "code": 0,
     "msg": "SUCCESS",c
     "result": [
     {
     "id": “7",                               分类ID
     "cate_name": “特殊衣物",    分类名称
     "items": [
     {
     "id": “7”,                        商家项目ID
     "item_name": “西装",     项目名称
     "item_price": “20.00”     项目价格
     }
     ]
     },
     {
     "id": "4",
     "cate_name": "日用、家居类",
     "items": []
     },
     {
     "id": "3",
     "cate_name": "小件类",
     "items": []
     },
     {
     "id": “2",
     "cate_name": "下装类",
     "items": []
     },
     {
     "id": "1",
     "cate_name": "上装类",
     "items": [
     {
     "id": "1",
     "item_name": "衬衣",
     "item_price": "15.00"
     }
     ]
     }
     ]
     }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<ManageCommodityResult> result;

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

    public List<ManageCommodityResult> getResult() {
        return result;
    }

    public void setResult(List<ManageCommodityResult> result) {
        this.result = result;
    }

    public class ManageCommodityResult{
        @SerializedName("id")
        private String id;
        @SerializedName("cate_name")
        private String cateName;
        @SerializedName("items")
        private List<ManageCommodityItems> itemses;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public List<ManageCommodityItems> getItemses() {
            return itemses;
        }

        public void setItemses(List<ManageCommodityItems> itemses) {
            this.itemses = itemses;
        }

        public class ManageCommodityItems{
            @SerializedName("id")
            private String id;
            @SerializedName("item_name")
            private String itemName;
            @SerializedName("item_price")
            private String itemPrice;

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
        }
    }
}
