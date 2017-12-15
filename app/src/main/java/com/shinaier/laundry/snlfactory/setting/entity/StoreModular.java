package com.shinaier.laundry.snlfactory.setting.entity;

/**
 * Created by 张家洛 on 2017/12/13.
 */

public class StoreModular {
    private String module;
    private String moduleName;
    public int isSelect = 1;

    public StoreModular(String module, String moduleName) {
        this.module = module;
        this.moduleName = moduleName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
