package com.shinaier.laundry.snlfactory.view.linechartview;

/**
 * Created by lijian on 2016/11/13.
 */

public class DefaultHighLightValueAdapter implements IValueAdapter {

    public DefaultHighLightValueAdapter() {

    }

    @Override
    public String value2String(double value) {
        return value + "";
    }

}
