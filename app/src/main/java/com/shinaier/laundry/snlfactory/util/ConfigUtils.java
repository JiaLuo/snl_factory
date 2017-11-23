package com.shinaier.laundry.snlfactory.util;

import android.content.Context;

import com.common.utils.PreferencesUtils;

/**
 * Created by 张家洛 on 2016/12/3.
 */

public class ConfigUtils {
    public static final String CONFIG_PRE_KEY_SHOW_GUIDE = "pre_key_show_guide";

    public static void setShowGuide(Context context, boolean isShow){
        PreferencesUtils.putBoolean(context, CONFIG_PRE_KEY_SHOW_GUIDE, isShow);
    }

    public static boolean isShowGuide(Context context){
        return PreferencesUtils.getBoolean(context, CONFIG_PRE_KEY_SHOW_GUIDE);
    }
}
