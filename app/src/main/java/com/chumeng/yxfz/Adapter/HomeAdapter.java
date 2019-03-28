package com.chumeng.yxfz.Adapter;

import android.content.Context;

import com.chumeng.yxfz.Base.moreAdapter.CommonBaseAdapter;
import com.chumeng.yxfz.Base.moreAdapter.ViewHolder;
import com.chumeng.yxfz.Entity.EntityOrderList;
import com.chumeng.yxfz.R;

import java.util.List;

/**
 * Created by qiu on 2018/8/14.
 */

public class HomeAdapter extends CommonBaseAdapter<EntityOrderList.DataBean.ListInfoBean> {
    public HomeAdapter(Context context, List<EntityOrderList.DataBean.ListInfoBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, EntityOrderList.DataBean.ListInfoBean data, int position) {
            holder.setText(R.id.tv_title,data.getTitle());
            holder.setText(R.id.tv_content,data.getStart_star()+"-"+data.getEnd_star());
            holder.setText(R.id.tv_area,data.getArea());
            holder.setText(R.id.tv_price,data.getPrice());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_home;
    }


}
