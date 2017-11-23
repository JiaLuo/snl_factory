package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/1.
 */

public class AddItemShowEntities {
    @SerializedName("type")
    private List<Item> items;
    @SerializedName("type_name")
    private String typeName;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public class Item{
        @SerializedName("cycle") //洗护周期
        private String cycle;
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("price")
        private String price;
        @SerializedName("state")
        private String state;

        public boolean isSelect = false;

        private int number;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "cycle='" + cycle + '\'' +
                    ", id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    ", state='" + state + '\'' +
                    ", number=" + number +
                    '}';
        }
    }
}
