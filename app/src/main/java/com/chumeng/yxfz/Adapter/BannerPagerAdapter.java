package com.chumeng.yxfz.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.chumeng.yxfz.Activity.MyWebViewActivity;
import com.chumeng.yxfz.Entity.EntityBanner;
import com.chumeng.yxfz.util.ToastUtils;


import java.util.List;

/**
 * Created by 邱 on 2017/2/15.
 */

public class BannerPagerAdapter extends PagerAdapter {
    private List<View> listImg;
    private Context context;
    private List<EntityBanner.DataBean> datas;

    public BannerPagerAdapter(List<View> listImg, Context context, List<EntityBanner.DataBean> datas) {
        this.listImg = listImg;
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return listImg.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        container.addView(listImg.get(position));
        listImg.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWebViewActivity.openWebView(context, datas.get(position).getHref());
            }
        });

        return listImg.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
