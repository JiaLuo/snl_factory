package com.shinaier.laundry.snlfactory;

import android.app.Application;

import com.common.utils.LogUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.shinaier.laundry.snlfactory.download.SystemParams;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 张家洛 on 2017/10/28.
 */

public class FactoryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try{
            JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
        }catch (Exception e){
            //避免第三方应用引起应用崩溃
        }
        Fresco.initialize(this);
        SystemParams.init(this);
        if(LogUtil.isDebug){
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(
                                    Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(
                                    Stetho.defaultInspectorModulesProvider(this))
                            .build());
        }
    }
}
