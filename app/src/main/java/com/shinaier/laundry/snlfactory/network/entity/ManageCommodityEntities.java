package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/14.
 */

public class ManageCommodityEntities {
    @SerializedName("count")
    private int count;
    @SerializedName("item_type")
    private List<ItemType> itemType;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ItemType> getItemType() {
        return itemType;
    }

    public void setItemType(List<ItemType> itemType) {
        this.itemType = itemType;
    }

    public class ItemType{
        @SerializedName("count1")
        private int count1;
        @SerializedName("eid")
        private String eid;
        @SerializedName("er_name")
        private String erName;
        @SerializedName("item")
        private List<Item> item;

        public int getCount1() {
            return count1;
        }

        public void setCount1(int count1) {
            this.count1 = count1;
        }

        public String getEid() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getErName() {
            return erName;
        }

        public void setErName(String erName) {
            this.erName = erName;
        }

        public List<Item> getItem() {
            return item;
        }

        public void setItem(List<Item> item) {
            this.item = item;
        }

        public class Item {
            @SerializedName("cycle")
            private String cycle;
            @SerializedName("id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("price")
            private String price;
            @SerializedName("tid")
            private String tid;

            public String getCycle() {
                return cycle;
            }

            public void setCycle(String cycle) {
                this.cycle = cycle;
            }

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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }
        }
    }
}
