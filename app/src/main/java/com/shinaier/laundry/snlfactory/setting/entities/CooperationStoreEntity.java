package com.shinaier.laundry.snlfactory.setting.entities;

/**
 * Created by 张家洛 on 2017/10/30.
 */

public class CooperationStoreEntity {
    private String name;
    private String num;
    public boolean isSelect = false;

    public CooperationStoreEntity(String name, String num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
