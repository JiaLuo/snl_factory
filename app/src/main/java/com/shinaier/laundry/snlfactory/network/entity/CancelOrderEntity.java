package com.shinaier.laundry.snlfactory.network.entity;

/**
 * Created by 张家洛 on 2017/2/28.
 */

public class CancelOrderEntity  {
    private String orderInfo;
    private int  isVisibled = 1;

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public int getIsVisibled() {
        return isVisibled;
    }

    public void setIsVisibled(int isVisibled) {
        this.isVisibled = isVisibled;
    }
}
