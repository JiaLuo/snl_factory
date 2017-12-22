package com.shinaier.laundry.snlfactory.ordermanage.entities;

import java.io.Serializable;
import java.util.List;

/**
 * 给后台传颜色需要的需数据 转json
 *
 * Created by 张家洛 on 2017/12/21.
 */

public class ParserEntity implements Serializable {
    private List<String> options;
    private String content;

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
