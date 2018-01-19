package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2018/1/18.
 */

public class LeaveFactoryEntities {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private IntoFactoryResult result;

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

    public IntoFactoryResult getResult() {
        return result;
    }

    public void setResult(IntoFactoryResult result) {
        this.result = result;
    }

    public class IntoFactoryResult{
        @SerializedName("league")
        private IntoFactoryLegue league;
        @SerializedName("data")
        private List<IntoFactoryData> datas;

        public IntoFactoryLegue getLeague() {
            return league;
        }

        public void setLeague(IntoFactoryLegue league) {
            this.league = league;
        }

        public List<IntoFactoryData> getDatas() {
            return datas;
        }

        public void setDatas(List<IntoFactoryData> datas) {
            this.datas = datas;
        }

        public class IntoFactoryData{
            @SerializedName("date")
            private String date;
            @SerializedName("list")
            private List<IntoFactoryList> lists;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public List<IntoFactoryList> getLists() {
                return lists;
            }

            public void setLists(List<IntoFactoryList> lists) {
                this.lists = lists;
            }

            public class IntoFactoryList{
                @SerializedName("id")
                private String id;
                @SerializedName("item_name")
                private String itemName;
                @SerializedName("clean_sn")
                private String cleanSn;
                @SerializedName("time")
                private String time;
                @SerializedName("put_sn")
                private String putSn;
                @SerializedName("assist")
                private String assist;

                public boolean isSelect = false;

                public String getAssist() {
                    return assist;
                }

                public void setAssist(String assist) {
                    this.assist = assist;
                }

                public String getPutSn() {
                    return putSn;
                }

                public void setPutSn(String putSn) {
                    this.putSn = putSn;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getItemName() {
                    return itemName;
                }

                public void setItemName(String itemName) {
                    this.itemName = itemName;
                }

                public String getCleanSn() {
                    return cleanSn;
                }

                public void setCleanSn(String cleanSn) {
                    this.cleanSn = cleanSn;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }
        }

        public class IntoFactoryLegue{
            @SerializedName("accept_id")
            private String acceptId;
            @SerializedName("mname")
            private String mName;

            public String getAcceptId() {
                return acceptId;
            }

            public void setAcceptId(String acceptId) {
                this.acceptId = acceptId;
            }

            public String getmName() {
                return mName;
            }

            public void setmName(String mName) {
                this.mName = mName;
            }
        }
    }
}
