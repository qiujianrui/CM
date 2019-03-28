package com.chumeng.yxfz.Base.moreAdapter;


import android.support.v7.widget.RecyclerView;

/**
 * Author: Othershe
 * Time: 2016/8/29 10:48
 */
public interface OnMultiItemClickListeners<T> {
    void onItemClick(RecyclerView.ViewHolder viewHolder, T data, int position, int viewType);
}
