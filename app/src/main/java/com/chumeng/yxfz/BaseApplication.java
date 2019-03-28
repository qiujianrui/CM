package com.chumeng.yxfz;

import android.app.Application;
import android.content.Context;

import com.chumeng.yxfz.View.pagestate.MyPageManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.imagepicker.initialize.ImagePickerInitialize;
import com.lzy.okgo.OkGo;

/**
 * Created by qiu on 2018/8/13.
 */

public class BaseApplication extends Application{
    private static BaseApplication mAppliication;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppliication = this;
        OkGo.getInstance().init(this);
        MyPageManager.initWhenAppOnCreate(getApplicationContext(),R.layout.pager_empty,R.layout.pager_loading,R.layout.pager_error);
        Fresco.initialize(this);//2222
        ImagePickerInitialize.initialize();
        Fresco.initialize(this);
        ImagePickerInitialize.initialize();//1111
        //11
    }

    public static Context getContext() {
        return mAppliication;
    }
}
