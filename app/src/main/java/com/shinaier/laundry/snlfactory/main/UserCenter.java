package com.shinaier.laundry.snlfactory.main;

import android.content.Context;

import com.common.utils.PreferencesUtils;
import com.common.utils.StringUtil;

/**
 * Created by 张家洛 on 2017/2/9.
 */

public class UserCenter {
    public static UserCenter instance = null;

    public static final String USER_PRE_KEY_TOKEN = "user_pre_token";
    public static final String USER_PRE_KEY_IS_ROOT = "user_pre_is_root";
    public static final String USER_PRE_KEY_UID = "user_pre_uid";
    public static final String USER_PRE_KEY_ROLE = "user_pre_role";
    public static final String USER_PRE_KEY_STATE = "user_pre_state";

    private UserCenter(){
    }

    public static synchronized UserCenter getInstance(){
        if(instance == null){
            instance = new UserCenter();
        }
        return instance;
    }

    public static boolean isLogin(Context context){
        String token = PreferencesUtils.getString(context,USER_PRE_KEY_TOKEN);
        if(StringUtil.isEmpty(token)){
            return false;
        }else {
            return true;
        }
    }

    public static String getToken(Context context){
        return PreferencesUtils.getString(context, USER_PRE_KEY_TOKEN);
    }

    public static void setToken(Context context, String token) {
        PreferencesUtils.putString(context, USER_PRE_KEY_TOKEN, token);
    }

    public static void setUid(Context context, String uid){
        PreferencesUtils.putString(context,USER_PRE_KEY_UID,uid);
    }

    public static String getUid(Context context){
        return PreferencesUtils.getString(context,USER_PRE_KEY_UID);
    }

    public static void setRole(Context context, String role){
        PreferencesUtils.putString(context,USER_PRE_KEY_ROLE,role);
    }

    public static String getRole(Context context){
        return PreferencesUtils.getString(context,USER_PRE_KEY_ROLE);
    }

    public static void setRoot(Context context,String isRoot){
        PreferencesUtils.putString(context,USER_PRE_KEY_IS_ROOT,isRoot);
    }

    public static String getRoot(Context context){
        return PreferencesUtils.getString(context,USER_PRE_KEY_IS_ROOT);
    }

    public static void saveLoginStatus(Context context, boolean flag){
        PreferencesUtils.putBoolean(context,"USER_PRE_KEY_TOKEN",flag);
    }

    public static void setState(Context context,String state){
        PreferencesUtils.putString(context,USER_PRE_KEY_STATE,state);
    }

    public static String getState(Context context){
        return PreferencesUtils.getString(context,USER_PRE_KEY_STATE);
    }


    public static void cleanLoginInfo(Context context){
        PreferencesUtils.putString(context, USER_PRE_KEY_TOKEN, "");
        PreferencesUtils.putString(context,USER_PRE_KEY_UID,"");
        PreferencesUtils.putString(context,USER_PRE_KEY_ROLE,"");
    }
}
