package com.shinaier.laundry.snlfactory.ordermanage.entities;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/9.
 */

public class ColorsEntity {
    private List<List<ColorSettingEntity>> colorsEntities;

    public List<List<ColorSettingEntity>> getColorsEntities() {
        return colorsEntities;
    }

    public void setColorsEntities(List<List<ColorSettingEntity>> colorsEntities) {
        this.colorsEntities = colorsEntities;
    }
}
