package com.shinaier.laundry.snlfactory.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/2.
 */

public class CheckClothesEntities {
    /**
     * {
     "code": 0,
     "msg": "SUCCESS",
     "result": [
     {
     "id": “10",           项目ID
     "color": “",             颜色
     "problem": “",           问题
     "forecast": “",        洗后预估
     "forecast_data": “”,        洗后预估数据空字符时：用户未选择
     "color_data": “[[\"red\",\"brown\"],\"fdfdfdf\"]",  颜色数据
     "problem_data": "[[\"dirty sugar\",\"hard tidy\"],\"hard clean\”]",
     问题描述数据:json格式,0索引为用户选择的列表,1索引为用户输入的内容
     "item_images": [
     "http://www.asyic.com/index/cu/erjvdnetgms.afm.jpg"   图片列表
     ]
     },
     {
     "id": “12",
     "color": "",
     "problem": "",
     "forecast": "",
     "item_images": []
     }
     ]
     }
     */
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<CheckClothesResult> results;

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

    public List<CheckClothesResult> getResults() {
        return results;
    }

    public void setResults(List<CheckClothesResult> results) {
        this.results = results;
    }

    public static class CheckClothesResult implements Parcelable{
        @SerializedName("id")
        private String id;
        @SerializedName("item_name")
        private String itemName;
        @SerializedName("color")
        private String color;
        @SerializedName("problem")
        private String problem;
        @SerializedName("forecast")
        private String forecast;
        @SerializedName("forecast_data")
        private String forecastData;
        @SerializedName("color_data")
        private String colorData;
        @SerializedName("problem_data")
        private String problemData;
        @SerializedName("item_images")
        private List<String> itemImages;

        protected CheckClothesResult(Parcel in) {
            id = in.readString();
            itemName = in.readString();
            color = in.readString();
            problem = in.readString();
            forecast = in.readString();
            forecastData = in.readString();
            colorData = in.readString();
            problemData = in.readString();
            itemImages = in.createStringArrayList();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(itemName);
            dest.writeString(color);
            dest.writeString(problem);
            dest.writeString(forecast);
            dest.writeString(forecastData);
            dest.writeString(colorData);
            dest.writeString(problemData);
            dest.writeStringList(itemImages);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CheckClothesResult> CREATOR = new Creator<CheckClothesResult>() {
            @Override
            public CheckClothesResult createFromParcel(Parcel in) {
                return new CheckClothesResult(in);
            }

            @Override
            public CheckClothesResult[] newArray(int size) {
                return new CheckClothesResult[size];
            }
        };

        public String getForecastData() {
            return forecastData;
        }

        public void setForecastData(String forecastData) {
            this.forecastData = forecastData;
        }

        public String getColorData() {
            return colorData;
        }

        public void setColorData(String colorData) {
            this.colorData = colorData;
        }

        public String getProblemData() {
            return problemData;
        }

        public void setProblemData(String problemData) {
            this.problemData = problemData;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getProblem() {
            return problem;
        }

        public void setProblem(String problem) {
            this.problem = problem;
        }

        public String getForecast() {
            return forecast;
        }

        public void setForecast(String forecast) {
            this.forecast = forecast;
        }

        public List<String> getItemImages() {
            return itemImages;
        }

        public void setItemImages(List<String> itemImages) {
            this.itemImages = itemImages;
        }

    }
}
