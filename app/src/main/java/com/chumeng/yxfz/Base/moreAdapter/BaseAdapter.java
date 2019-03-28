package com.chumeng.yxfz.Base.moreAdapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.chumeng.yxfz.Config.Configs;
import com.chumeng.yxfz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/29 09:46
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static boolean isNet = false; //用于判断是否是网络加载数据 非第一次加载数据 展示EmptyView

    public static final int TYPE_COMMON_VIEW = 100001;//普通类型 Item
    public static final int TYPE_FOOTER_VIEW = 100002;//footer类型 Item
    public static final int TYPE_EMPTY_VIEW = 100003;//empty view，即初始化加载时的提示View
    public static final int TYPE_HEADER_VIEW = 100004;//header view

    private OnLoadMoreListener mLoadMoreListener;

    protected Context mContext;
    protected List<T> mDatas;

    private boolean mOpenLoadMore;//是否开启加载更多
    private boolean isAutoLoadMore = true;//是否自动加载，当数据不满一屏幕会自动加载
    private boolean mloading = false; //判断是否正在加载 避免多次加载 true是正在加载 false是加载完成

    private View mLoadingView;
    private View mLoadFailedView;
    private View mLoadEndView;
    private View mEmptyView;
    private RelativeLayout mFooterLayout;//footer view
    private View mHeaderView;//头部view
    private String emptyTip;

    public void setAutoLoadMore(boolean autoLoadMore) {
        isAutoLoadMore = autoLoadMore;
    }

    protected abstract int getViewType(int position, T data);

    public BaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        isNet = false;
        mContext = context;
        mDatas = datas == null ? new ArrayList<T>() : datas;
        mOpenLoadMore = isOpenLoadMore;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_FOOTER_VIEW:
                if (mFooterLayout == null) {
                    mFooterLayout = new RelativeLayout(mContext);
                }
                viewHolder = ViewHolder.create(mFooterLayout);
                break;
            case TYPE_EMPTY_VIEW:
                viewHolder = ViewHolder.create(mEmptyView);
                break;
            case TYPE_HEADER_VIEW:
                viewHolder = ViewHolder.create(mHeaderView);
                break;
        }
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        int countH = mHeaderView == null ? 0 : 1;//有头部则加1
        if (mDatas.isEmpty() && mEmptyView != null && isNet) {
            return 1 + countH;
        }
        return mDatas.size() + getFooterViewCount() + countH;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.isEmpty() && mEmptyView != null && isNet) {
            return TYPE_EMPTY_VIEW;
        }
        if (isHeaderView(position)) {
            return TYPE_HEADER_VIEW;
        }
        if (isFooterView(position)) {
            return TYPE_FOOTER_VIEW;
        }

        position = mHeaderView == null ? position : position - 1;//有头部则加1
        return getViewType(position, mDatas.get(position));
    }

    /**
     * 根据positiond得到data
     * 注意： 如果有添加头部position要减1
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        if (mDatas.isEmpty()) {
            return null;
        }
        return mDatas.get(position);
    }

    /**
     * 是否是HeaderView
     *
     * @param position
     * @return
     */
    private boolean isHeaderView(int position) {
        if (mHeaderView != null && position == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否有HeaderVIEW
     *
     * @return
     */
    public boolean isHasHeaderView() {
        return mHeaderView == null ? false : true;
    }

    /**
     * 是否是FooterView
     *
     * @param position
     * @return
     */
    private boolean isFooterView(int position) {
        return mOpenLoadMore && position >= getItemCount() - 1;
    }

    protected boolean isCommonItemView(int viewType) {
        return viewType != TYPE_EMPTY_VIEW && viewType != TYPE_FOOTER_VIEW && viewType != TYPE_HEADER_VIEW;
    }

    /**
     * StaggeredGridLayoutManager模式时，FooterView可占据一行
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isFooterView(holder.getLayoutPosition())) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
        if (isHeaderView(holder.getLayoutPosition())) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * GridLayoutManager模式时， FooterView可占据一行，判断RecyclerView是否到达底部
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isFooterView(position)) {
                        return gridManager.getSpanCount();
                    }
                    if (isHeaderView(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        startLoadMore(recyclerView, layoutManager);
    }


    /**
     * 判断列表是否滑动到底部
     *
     * @param recyclerView
     * @param layoutManager
     */
    private void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        if (!mOpenLoadMore || mLoadMoreListener == null) {
            return;
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    Log.d("jere","onScrollStateChanged==="+isAutoLoadMore);
                    if (!isAutoLoadMore && findLastVisibleItemPosition(layoutManager) + 1 == getItemCount() && !mloading) {
                        scrollLoadMore();
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.d("jere","onScroll==="+isAutoLoadMore);
                if (isAutoLoadMore && findLastVisibleItemPosition(layoutManager) + 1 == getItemCount() && !mloading) {
                    scrollLoadMore();
                } else if (isAutoLoadMore) {
                    isAutoLoadMore = false;
                }
            }
        });
    }

    /**
     * 到达底部开始刷新
     */
    private void scrollLoadMore() {
        mloading = true;
        setLoadingView(R.layout.load_loading_layout);
        if (mFooterLayout.getChildAt(0) == mLoadingView) {
            if (mLoadMoreListener != null) {
                mLoadMoreListener.onLoadMore(false);
            }
        }
    }

    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager)
                    .findLastVisibleItemPositions(null);
            return Util.findMax(lastVisibleItemPositions);
        }
        return -1;
    }

    /**
     * 清空footer view
     */
    private void removeFooterView() {
        mFooterLayout.removeAllViews();
    }

    /**
     * 添加新的footer view
     *
     * @param footerView
     */
    private void addFooterView(View footerView) {
        if (footerView == null) {
            return;
        }

        if (mFooterLayout == null) {
            mFooterLayout = new RelativeLayout(mContext);
        }
        removeFooterView();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mFooterLayout.addView(footerView, params);
    }

    /**
     * 添加头部
     *
     * @param mHeaderView
     */
    public void addHeaderView(View mHeaderView) {
        if (mHeaderView == null) {
            return;
        }
        this.mHeaderView = mHeaderView;
    }

    /**
     * 刷新加载更多的数据
     *
     * @param datas
     */
    public void setLoadMoreData(List<T> datas) {
        if (datas != null && datas.size() != 0) {
            int size = mDatas.size();
            mDatas.addAll(datas);
            if (mHeaderView == null) {
                notifyItemInserted(size);
//                setLoadEndView(R.layout.load_end_layout);
            } else {
                notifyItemInserted(size + 1);
//                setLoadEndView(R.layout.load_end_layout);
            }
        } else {
            setLoadEndView(R.layout.load_end_layout);
        }
        mloading = false;
    }

    /**
     * 下拉刷新，得到的新数据查到原数据起始
     *
     * @param datas
     */
    public void setData(List<T> datas) {
        if ( datas != null && datas.size() != 0) {
            mDatas.addAll(0, datas);
            notifyDataSetChanged();
        } else  {
            Toast.makeText(mContext, "没有更多数据了", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初次加载、或下拉刷新要替换全部旧数据时刷新数据
     *
     * @param datas
     */
    public void setNewData(List<T> datas) {
        mloading = false;
        isNet = true;
        if (!isAutoLoadMore) {
            isAutoLoadMore = true;
        }
        if (datas != null && datas.size() != 0) {
            mDatas.clear();
            mDatas.addAll(datas);
            notifyDataSetChanged();
            if (mDatas.size() < Configs.limit) {
                setLoadEndView(R.layout.load_end_layout);
            }
        } else if (datas == null || datas.size() == 0) {
            mDatas.clear();
            if (mEmptyView == null) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT);
                RelativeLayout relativeLayout = new RelativeLayout(mContext);
                relativeLayout.setLayoutParams(params);
                View emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty, relativeLayout, false);
                TextView tv_empty_tip = (TextView) emptyView.findViewById(R.id.tv_empty_tip);
                if (!TextUtils.isEmpty(emptyTip)) {
                    tv_empty_tip.setText(emptyTip);
                }
                setEmptyView(emptyView);
            }
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public void clearData() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    /**
     * 初始化加载中布局
     *
     * @param loadingView
     */
    public void setLoadingView(View loadingView) {
        mLoadingView = loadingView;
        addFooterView(mLoadingView);
    }

    public void setLoadingView(int loadingId) {
        setLoadingView(Util.inflate(mContext, loadingId));
    }

    public void setLoadingView() {
        setLoadingView(R.layout.load_loading_layout);
    }



    /**
     * 初始加载失败布局
     *
     * @param loadFailedView
     */
    public void setLoadFailedView(View loadFailedView) {
        if (loadFailedView == null) {
            return;
        }
        mLoadFailedView = loadFailedView;
        addFooterView(mLoadFailedView);
        mLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFooterView(mLoadingView);
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.onLoadMore(true);
                }
            }
        });
    }

    public void setLoadFailedView(int loadFailedId) {
        setLoadFailedView(Util.inflate(mContext, loadFailedId));
    }

    public void setLoadFailedView() {
        setLoadFailedView(R.layout.load_failed_layout);
    }

    /**
     * 初始化全部加载完成布局
     *
     * @param loadEndView
     */
    public void setLoadEndView(View loadEndView) {
        mLoadEndView = loadEndView;
        addFooterView(mLoadEndView);
    }

    public void setLoadEndView(int loadEndId) {
        setLoadEndView(Util.inflate(mContext, loadEndId));
    }

    /**
     * 初始化emptyView
     *
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    /**
     * 返回 footer view数量
     *
     * @return
     */
    public int getFooterViewCount() {
        return mOpenLoadMore && !mDatas.isEmpty() ? 1 : 0;
    }

    /**
     * 设置空数据提示
     */
    public void setEmptyTip(String emptyTip) {
        this.emptyTip = emptyTip;
    }



    public void setLoadEndView() {
        setLoadEndView(R.layout.load_end_layout);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    /**
     * 分页加载时加载更多错误展示
     */
    public int loadMoreFail(int pageNo) {
        if (pageNo == Configs.page + 1) {
            this.setNewData(null);
        } else {
            this.setLoadFailedView();
        }
        return --pageNo;
    }

    /**
     * 分页加载时没有加载更多展示
     */
    public void loadMoreEnd(int pageNo) {
        if (pageNo ==  Configs.page + 1) {
            this.setNewData(null);
        } else {
            this.setLoadEndView();
        }
    }

    /**
     * 分页加载时选择加载更多或刷新
     *
     * @param pageNo
     * @param list
     */
    public void setData(int pageNo, List<T> list) {
        if (pageNo ==  Configs.page + 1) {
            setNewData(list);
        } else {
            setLoadMoreData(list);
        }
    }

    public List getList() {
        return mDatas;
    }
}
