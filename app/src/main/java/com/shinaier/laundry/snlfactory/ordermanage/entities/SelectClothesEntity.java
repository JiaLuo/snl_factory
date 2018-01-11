package com.shinaier.laundry.snlfactory.ordermanage.entities;

/**
 * Created by 张家洛 on 2018/1/2.
 */

public class SelectClothesEntity {
    private int id;
    private long count;

    public SelectClothesEntity(int id, long count) {
        this.id = id;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
