package com.shinaier.laundry.snlfactory.view.linechartview;

import android.graphics.RectF;

/**
 * Created by lijian on 2016/11/14.
 */

public abstract class BaseRender {
    RectF _rectMain;
    MappingManager _MappingManager;

    public BaseRender(RectF _rectMain, MappingManager _MappingManager) {
        this._rectMain = _rectMain;
        this._MappingManager = _MappingManager;
    }
}
