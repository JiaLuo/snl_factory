package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/8/3.
 */

public class OfflineHangOnEntity {
    /**
     * {
     "code": 0,
     "msg": "SUCCESS",
     "result": [
     {
     "id": "181",  项目编号
     "item_name": "衬衣",  项目名称
     "clean_sn": "",     水洗码
     "take_time": "1514682000",  取衣时间
     "state": 1   状态（0，无状态；1，提醒；2，预警）
     }
     ]
     }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<OfflineHangOnResult> results;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<OfflineHangOnResult> getResults() {
        return results;
    }

    public void setResults(List<OfflineHangOnResult> results) {
        this.results = results;
    }

    public class OfflineHangOnResult{
        @SerializedName("id")
        private String id;
        @SerializedName("clean_sn")
        private String cleanSn;
        @SerializedName("item_name")
        private String itemName;
        @SerializedName("take_time")
        private String takeTime;
        @SerializedName("state")
        private String state;
        @SerializedName("assist")
        private String assist;

        public boolean isSelect = false;

        public String getAssist() {
            return assist;
        }

        public void setAssist(String assist) {
            this.assist = assist;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCleanSn() {
            return cleanSn;
        }

        public void setCleanSn(String cleanSn) {
            this.cleanSn = cleanSn;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getTakeTime() {
            return takeTime;
        }

        public void setTakeTime(String takeTime) {
            this.takeTime = takeTime;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
