package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/14.
 */

public class AddCommodityEntities {
    @SerializedName("count")
    private int count;
    @SerializedName("item")
    private List<Item> item;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public class Item{
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("goods_count")
        private String goodsCount;
        @SerializedName("goods")
        private List<Goods> goods;

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

        public String getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(String goodsCount) {
            this.goodsCount = goodsCount;
        }

        public List<Goods> getGoods() {
            return goods;
        }

        public void setGoods(List<Goods> goods) {
            this.goods = goods;
        }

        public class Goods {
            @SerializedName("id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("fid")
            private String fid;


            public boolean isChanged = false;

//            public boolean isChanged() {
//                return isChanged;
//            }
//
//            public void setChanged(boolean changed) {
//                isChanged = changed;
//            }

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

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }
        }
    }
}
