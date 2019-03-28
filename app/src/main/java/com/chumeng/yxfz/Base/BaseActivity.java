package com.chumeng.yxfz.Base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.chumeng.yxfz.R;
import com.chumeng.yxfz.View.pagestate.MyPageListener;
import com.chumeng.yxfz.View.pagestate.MyPageManager;
import com.chumeng.yxfz.View.pagestate.OnPageManagerOnClick;
import com.chumeng.yxfz.util.SystemBarTintManager;
import com.chumeng.yxfz.util.ToastUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * @author hzf
 * @Description: TODO(activity的基类，最好所有的activity都继承该类)
 */
public abstract class BaseActivity extends AppCompatActivity implements
        View.OnClickListener {
    protected boolean isUsedImmerseStatusBar = false;// 标识是否使用沉浸式状态栏
    protected int ImmerseStatusBarColor;// 使用沉浸式状态栏的颜色
    private boolean isFromStackTop = false;// 记录当finish当前activity时，是否从APPManager的栈顶移除activity
    protected Activity mActivity;



    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (isUsedImmerseStatusBar)
            initStatusBar(ImmerseStatusBarColor);

        mActivity = this;
    }

    protected void useImmerseStatusBar(boolean isUsed, int color) {
        this.isUsedImmerseStatusBar = isUsed;
        this.ImmerseStatusBarColor = color;
    }

    /**
     * 初始化沉浸式状态栏
     */
    private void initStatusBar(int color) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);// 通知栏所需颜色
            if (setMiuiStatusBarDarkMode(this, true)) {
//                Log.d("jere状态栏","miui");
            } else if (setMeizuStatusBarDarkIcon(this, true)) {
//                Log.d("jere状态栏","meizu");
            } else //适配系统少于6.0 非miui及魅族 状态栏字体为白色，看不到字体的问题
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                Log.d("jere状态栏","Android6.0");
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else tintManager.setStatusBarTintResource(R.color.statusBarOther);
        }
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(color);// 通知栏所需颜色
//        }
//        boolean changeB = setMiuiStatusBarDarkMode(this, true);
//        if (!changeB) {
//            setMeizuStatusBarDarkIcon(this, true);
//        }

    }

    /**
     * MIUI内置了可以修改状态栏的模式
     *
     * @param activity
     * @param darkmode
     * @return
     */
    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 魅族
     *
     * @param activity
     * @param dark
     * @return
     */
    public static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected void initView() {

    }

    protected void initData() {

    }

    protected void addListener() {

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计启动次数

    }


    @Override
    protected void onPause() {
        super.onPause();
        //友盟统计启动次数

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.cancelToast();
    }

    public void finish(boolean isFromStackTop) {
        this.isFromStackTop = isFromStackTop;
        finish();
    }




}
