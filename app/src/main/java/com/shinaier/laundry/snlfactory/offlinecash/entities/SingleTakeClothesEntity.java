package com.shinaier.laundry.snlfactory.offlinecash.entities;

/**
 * Created by 张家洛 on 2017/8/11.
 */

public class SingleTakeClothesEntity {
    private String itemId;
    private String price;
    private String status;
    private String putNumber;
    private String name;
    private String number;

    public boolean isSelect = false;

    public SingleTakeClothesEntity(String itemId, String price, String status, String putNumber, String name, String number) {
        this.itemId = itemId;
        this.price = price;
        this.status = status;
        this.putNumber = putNumber;
        this.name = name;
        this.number = number;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPutNumber() {
        return putNumber;
    }

    public void setPutNumber(String putNumber) {
        this.putNumber = putNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
