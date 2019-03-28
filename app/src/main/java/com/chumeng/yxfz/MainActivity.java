package com.chumeng.yxfz;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.chumeng.yxfz.Activity.LoginActivity;
import com.chumeng.yxfz.Activity.PlaceOrderActivity;
import com.chumeng.yxfz.Fragment.CourseFragment;
import com.chumeng.yxfz.Fragment.HomeFragment;
import com.chumeng.yxfz.View.MyFragmentTabHost;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mMainFragment;
    private TabWidget mTabs;
    private MyFragmentTabHost mTabHost;
    private Drawable drawable;
    private View mTabView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mMainFragment = (FrameLayout) findViewById(R.id.main_fragment);
        mTabs = (TabWidget) findViewById(R.id.tabs);
        mTabHost = (MyFragmentTabHost) findViewById(R.id.tabHost);
        initTab();
    }

    private void initTab() {
        mTabHost = (MyFragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.main_fragment);
        //去掉fragmentTabHost分割线
        mTabHost.getTabWidget().setDividerDrawable(null);
        drawable = getResources().getDrawable(R.mipmap.ic_home);
        mTabView = getTabView("首页", R.layout.tab_layout, drawable);
        mTabHost.addTab(mTabHost.newTabSpec("0").setIndicator(mTabView), HomeFragment.class, null);
        drawable = getResources().getDrawable(R.mipmap.ic_tc);
        mTabView = getTabView("套餐", R.layout.tab_layout, drawable);
        mTabHost.addTab(mTabHost.newTabSpec("1").setIndicator(mTabView), CourseFragment.class, null);
        drawable = getResources().getDrawable(R.mipmap.ic_xd);
        mTabView = getTabView("我要下单", R.layout.tab_layout, drawable);
        mTabHost.addTab(mTabHost.newTabSpec("2").setIndicator(mTabView), null, null);
        drawable = getResources().getDrawable(R.mipmap.ic_hd);
        mTabView = getTabView("活动", R.layout.tab_layout, drawable);
        mTabHost.addTab(mTabHost.newTabSpec("3").setIndicator(mTabView), HomeFragment.class, null);
        drawable = getResources().getDrawable(R.mipmap.ic_me);
        mTabView = getTabView("我的", R.layout.tab_layout, drawable);
        mTabHost.addTab(mTabHost.newTabSpec("4").setIndicator(mTabView), HomeFragment.class, null);

        mTabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, PlaceOrderActivity.class));
            }
        });
        mTabHost.getTabWidget().getChildTabViewAt(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }


    private View getTabView(String name, int layoutID, Drawable drawable) {
        View view = getLayoutInflater().inflate(layoutID, null);
        TextView textView = (TextView) view.findViewById(R.id.txt_tab);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_tab);
        textView.setText(name);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        imageView.setImageDrawable(drawable);
        return view;
    }
}
