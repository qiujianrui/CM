package com.chumeng.yxfz.Adapter;

import android.content.Context;
import android.graphics.Color;

import com.chumeng.yxfz.Base.moreAdapter.CommonBaseAdapter;
import com.chumeng.yxfz.Base.moreAdapter.ViewHolder;
import com.chumeng.yxfz.R;

import java.util.List;

/**
 * Created by qiu on 2018/8/14.
 */

public class DropMenuListAdapter extends CommonBaseAdapter<String>{
    private int checkItemPosition = 0;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public DropMenuListAdapter(Context context, List<String> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, String data, int position) {
        holder.setText(R.id.text,data);
        if (checkItemPosition==position){
            holder.setTextColorRes(R.id.text, R.color.colorAccent);
        }else {
            holder.setTextColorRes(R.id.text,R.color.colorTvDefault);
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_list_drop_down;
    }
}
