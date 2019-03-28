package com.chumeng.yxfz.util;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.chumeng.yxfz.BaseApplication;

/**
 * Created by THINK on 2017/10/19.
 */

public class ToastUtils {
    private static Toast mToast;
    private static Toast mCenterToast;

    public static void showShortToast(String txt) {
        if (TextUtils.isEmpty(txt)){
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getContext(), txt, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(txt);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
    public static void showShortCenterToast(String txt) {
        if (TextUtils.isEmpty(txt)){
            return;
        }
        if (mCenterToast == null) {
            mCenterToast = Toast.makeText(BaseApplication.getContext(), txt, Toast.LENGTH_SHORT);
            mCenterToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mCenterToast.setText(txt);
            mCenterToast.setDuration(Toast.LENGTH_SHORT);
        }
        mCenterToast.show();
    }
    public static void showLongToast(String txt){
        if (TextUtils.isEmpty(txt)){
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getContext(), txt, Toast.LENGTH_LONG);
        } else {
            mToast.setText(txt);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }
    public static void showLongCenterToast(String txt){
        if (TextUtils.isEmpty(txt)){
            return;
        }
        if (mCenterToast == null) {
            mCenterToast = Toast.makeText(BaseApplication.getContext(), txt, Toast.LENGTH_LONG);
            mCenterToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mCenterToast.setText(txt);
            mCenterToast.setDuration(Toast.LENGTH_LONG);
        }
        mCenterToast.show();
    }
    //取消吐司
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
        if (mCenterToast!=null){
            mCenterToast.cancel();
        }
    }
}
