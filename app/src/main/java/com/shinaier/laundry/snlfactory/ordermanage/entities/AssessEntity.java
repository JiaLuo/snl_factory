package com.shinaier.laundry.snlfactory.ordermanage.entities;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/9.
 */

public class AssessEntity {
    private List<List<AssessSettingEntity>> assess;

    public List<List<AssessSettingEntity>> getAssesses() {
        return assess;
    }

    public void setAssesses(List<List<AssessSettingEntity>> assesses) {
        this.assess = assesses;
    }
}
