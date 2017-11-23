package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/16.
 */

public class EvaluateEntities {
    @SerializedName("count")
    private int count;
    @SerializedName("data")
    private List<Data> datas;
    @SerializedName("retcode")
    private int retcode;
    @SerializedName("status")
    private String status;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class Data{
        @SerializedName("grade")
        private String grade;
        @SerializedName("id")
        private String id;
        @SerializedName("mer_content")
        private String merContent;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("time")
        private String time;
        @SerializedName("user_content")
        private String userContent;

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMerContent() {
            return merContent;
        }

        public void setMerContent(String merContent) {
            this.merContent = merContent;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUserContent() {
            return userContent;
        }

        public void setUserContent(String userContent) {
            this.userContent = userContent;
        }
    }
 }
