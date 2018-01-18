package com.shinaier.laundry.snlfactory.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/2/16.
 */

public class EvaluateEntities {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<EvaluateResult> results;

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

    public List<EvaluateResult> getResults() {
        return results;
    }

    public void setResults(List<EvaluateResult> results) {
        this.results = results;
    }

    public class EvaluateResult{
        @SerializedName("id")
        private String id;
        @SerializedName("level")
        private String level;
        @SerializedName("ucomment")
        private String uComment;
        @SerializedName("manswer")
        private String mAnswer;
        @SerializedName("has_answer")
        private String hasAnswer;
        @SerializedName("ctime")
        private String cTime;
        @SerializedName("atime")
        private String aTime;
        @SerializedName("uname")
        private String uName;
        @SerializedName("header")
        private String header;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getuComment() {
            return uComment;
        }

        public void setuComment(String uComment) {
            this.uComment = uComment;
        }

        public String getmAnswer() {
            return mAnswer;
        }

        public void setmAnswer(String mAnswer) {
            this.mAnswer = mAnswer;
        }

        public String getHasAnswer() {
            return hasAnswer;
        }

        public void setHasAnswer(String hasAnswer) {
            this.hasAnswer = hasAnswer;
        }

        public String getcTime() {
            return cTime;
        }

        public void setcTime(String cTime) {
            this.cTime = cTime;
        }

        public String getaTime() {
            return aTime;
        }

        public void setaTime(String aTime) {
            this.aTime = aTime;
        }

        public String getuName() {
            return uName;
        }

        public void setuName(String uName) {
            this.uName = uName;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }
    }
 }
