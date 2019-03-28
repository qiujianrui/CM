package com.chumeng.yxfz.Base;

import android.view.View;

import com.chumeng.yxfz.View.pagestate.MyPageListener;
import com.chumeng.yxfz.View.pagestate.MyPageManager;
import com.chumeng.yxfz.View.pagestate.OnPageManagerOnClick;

/**
 * Created by qiu on 2018/8/23.
 */

public abstract class ContentBaseActivity extends BaseActivity implements OnPageManagerOnClick {
    public MyPageManager pageStateManager;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        pageStateManager=new MyPageManager().init(this, new MyPageListener() {
            @Override
            protected void onReallyRetry() {

            }
        },this);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRetryClick() {

    }
}
