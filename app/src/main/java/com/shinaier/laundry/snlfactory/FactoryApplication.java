package com.shinaier.laundry.snlfactory;

import android.app.Application;

import com.common.utils.LogUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.shinaier.laundry.snlfactory.download.SystemParams;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 张家洛 on 2017/10/28.
 */

public class FactoryApplication extends Application {
    {
        PlatformConfig.setWeixin("wx9dc154e2c9c24fda", "9c7a2ce81acfce8a061e3f34e3460808");
        PlatformConfig.setQQZone("1105958999", "TeX8QZWgMad1GsYH");
        PlatformConfig.setSinaWeibo("4231394323", "f06421de21f050682b850196a24fb255","");
    }
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
