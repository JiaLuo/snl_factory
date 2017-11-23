package com.shinaier.laundry.snlfactory.network.entity;

import java.util.List;

/**
 * Created by 张家洛 on 2017/7/26.
 */

public class StatisticsEntity {

    private String time;
    private List<DateInfo> dateInfo;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<DateInfo> getDateInfo() {
        return dateInfo;
    }

    public void setDateInfo(List<DateInfo> dateInfo) {
        this.dateInfo = dateInfo;
    }

    public class DateInfo{
        private String type;
        private String money;

        public DateInfo(String type, String money) {
            this.type = type;
            this.money = money;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
