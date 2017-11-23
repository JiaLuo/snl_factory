package com.shinaier.laundry.snlfactory.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by 张家洛 on 2017/8/24.
 */

public class ScrollViewToTop extends ScrollView {
    public ScrollViewToTop(Context context) {
        super(context);
    }

    public ScrollViewToTop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewToTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }
}
