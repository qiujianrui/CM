package com.chumeng.yxfz.Base.moreAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.chumeng.yxfz.BaseApplication;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * Author: Othershe
 * Time: 2016/8/29 09:49
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;

    /**
     * 私有构造方法
     *
     * @param itemView
     */
    private ViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static ViewHolder create(Context context, int layoutId, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(itemView);
    }

    public static ViewHolder create(View itemView) {
        return new ViewHolder(itemView);
    }

    /**
     * 通过id获得控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public View getSwipeView() {
        ViewGroup itemLayout = ((ViewGroup) mConvertView);
        if (itemLayout.getChildCount() == 2) {
            return itemLayout.getChildAt(1);
        }
        return null;
    }
    public void setImageUrl(int viewId,String url){
        SimpleDraweeView simpleDraweeView=getView(viewId);
        simpleDraweeView.setImageURI(url);
    }

    public void setImageUrl(int viewId, Uri url){
        SimpleDraweeView simpleDraweeView=getView(viewId);
        simpleDraweeView.setImageURI(String.valueOf(url));
    }
    public void setTextColor(int viewId,int colorId){
        TextView textView=getView(viewId);
        textView.setTextColor(colorId);
    }
    public void setTextColorRes(int viewId, int colorId) {
        TextView textView = getView(viewId);
        textView.setTextColor(BaseApplication.getContext().getResources().getColor(colorId));
    }
    public void setProgress(int viewId, int progress){
        ProgressBar progressBar=getView(viewId);
        progressBar.setProgress(progress);
    }
    public void setTypeface(int viewId, String path,Context context){
        TextView textView = getView(viewId);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), path));
    }
    public void setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
    }
    public void setSelect(int viewId,boolean select){
        View view=getView(viewId);
        view.setSelected(select);
    }
    public void setTextFor(int viewId,String[] strings){
        TextView textView = getView(viewId);
        String msg = "";
        for (int i=0;i<strings.length;i++){
             msg = new StringBuffer().append(strings[i]).toString();
        }
    }
    public void setVISIBLE(int viewId){
        View view=getView(viewId);
        view.setVisibility(View.VISIBLE);
    }
    public void setGONE(int viewId){
        View view=getView(viewId);
        view.setVisibility(View.GONE);
    }
    public void setText(int viewId, int textId) {
        TextView textView = getView(viewId);
        textView.setText(textId);
    }

    public void setSrc(int viewId,int imgId){
        ImageView img = getView(viewId);
        img.setImageResource(imgId);
    }

    public View setOnClickListener(int viewId, View.OnClickListener clickListener) {
        View view = getView(viewId);
        view.setOnClickListener(clickListener);
        return view;
    }

    public void setBgRes(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
    }

    public void setBgColor(int viewId, int colorId) {
        View view = getView(viewId);
        view.setBackgroundColor(colorId);
    }

    public void setBackgroundColor(int viewId,int colorId){
        View view=getView(viewId);
        view.setBackgroundColor(colorId);

    }
    public void setBackgroundResource(int viewId,int resourceId){
        View view = getView(viewId);
        view.setBackgroundResource(resourceId);
    }


    public void setCheck(int viewId, boolean isCheck){
        CheckBox checkBox=getView(viewId);
        checkBox.setChecked(isCheck);
    }
    /**点赞列表初始化*/
   /* public void initPraiseTextView(int viewId, List<PraiseTextView.PraiseInfo> data,int mNameTextColor,int mIcon,String mMiddleStr){
        PraiseTextView praiseTextView=getView(viewId);
        if (data!=null) {
            praiseTextView.setData(data);
        }
        praiseTextView.setNameTextColor(mNameTextColor);
        praiseTextView.setIcon(mIcon);
        praiseTextView.setMiddleStr(mMiddleStr);
        praiseTextView.setIconSize (new Rect(0,0,50,50));
    }


    *//**评论列表*//*
    public CommentListView getCommentListView(int viewId, List<CommentItem> data){
        CommentListView commentListView=getView(viewId);
        if (data!=null){
        commentListView.setDatas(data);
        }
        return commentListView;
    }
    *//**评论列表*//*
    public CommentListView getCommentListViewNoData(int viewId){
        CommentListView commentListView=getView(viewId);
        return commentListView;
    }*/
}
