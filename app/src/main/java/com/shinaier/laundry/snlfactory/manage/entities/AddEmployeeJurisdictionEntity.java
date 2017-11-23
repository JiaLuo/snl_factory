package com.shinaier.laundry.snlfactory.manage.entities;

/**
 * Created by 张家洛 on 2017/10/30.
 */

public class AddEmployeeJurisdictionEntity {
    private String name;
    public boolean isSelect = false;

    public AddEmployeeJurisdictionEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
