package com.shinaier.laundry.snlfactory.setting.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by 张家洛 on 2017/7/18.
 */

public class CollectClothesDialog extends Dialog {

    public CollectClothesDialog(@NonNull Context context, @StyleRes int themeResId, View layout) {
        super(context, themeResId);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.CENTER;
        window.setAttributes(attributes);
    }
}
