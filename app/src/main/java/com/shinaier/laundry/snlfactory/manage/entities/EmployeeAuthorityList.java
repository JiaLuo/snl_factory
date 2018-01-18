package com.shinaier.laundry.snlfactory.manage.entities;

/**
 * Created by 张家洛 on 2017/11/2.
 */

public class EmployeeAuthorityList {
    private String name;
    private String num;
    private String state;

    public boolean isSelect = false;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
