package com.chumeng.yxfz.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chumeng.yxfz.Activity.OrderDetailActivity;
import com.chumeng.yxfz.Adapter.BannerPagerAdapter;
import com.chumeng.yxfz.Adapter.DropMenuListAdapter;
import com.chumeng.yxfz.Adapter.HomeAdapter;
import com.chumeng.yxfz.Base.moreAdapter.OnItemClickListener;
import com.chumeng.yxfz.Base.moreAdapter.ViewHolder;
import com.chumeng.yxfz.Config.Configs;
import com.chumeng.yxfz.Config.URL;
import com.chumeng.yxfz.Entity.EntityBanner;
import com.chumeng.yxfz.Entity.EntityOrderList;
import com.chumeng.yxfz.Entity.EntityOrderNum;
import com.chumeng.yxfz.OKGO.callback.JsonCallback;
import com.chumeng.yxfz.R;
import com.chumeng.yxfz.View.AutoCarouselViewPager;
import com.chumeng.yxfz.View.DropDownMenu.DropDownMenu;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {
    private AutoCarouselViewPager mViewpager;
//    private DropDownMenu mDropDownMenu;
    private RecyclerView mRecyclerList;
    private FrameLayout mFlBanner;
    private CircleIndicator mIndicator;
    private BannerPagerAdapter bannerAdapter;
    private ArrayList<View> list;
    private List<View> mViewList;

    private HomeAdapter mHomeAdapter;
    /**
     * 服务用户数： 接单数：
     */
    private TextView mTvOrder;
    private int page = Configs.page;
    private SwipeRefreshLayout mRefresh;
    private View viewList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mTvOrder = (TextView) view.findViewById(R.id.tv_Order);
        mViewpager = (AutoCarouselViewPager) view.findViewById(R.id.viewpager);
//        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);
        viewList = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_list,null);

        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        mRecyclerList = (RecyclerView) view.findViewById(R.id.recycler_list);
//        mRecyclerList = new RecyclerView(getActivity());
        mRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeAdapter = new HomeAdapter(getActivity(), null, true);
        mRecyclerList.setAdapter(mHomeAdapter);
        mFlBanner = view.findViewById(R.id.fl_banner);
        mIndicator = view.findViewById(R.id.indicator);
        mHomeAdapter.setOnItemClickListener(new OnItemClickListener<EntityOrderList.DataBean.ListInfoBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, EntityOrderList.DataBean.ListInfoBean data, int position) {
                Intent intent=new Intent(getActivity(),OrderDetailActivity.class);
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    page=Configs.page;
                    netData();
            }
        });
        initViewPager();
//        initMenu();
        netOrder();//获取接单数及服务数
        netData();

    }

    private void netOrder() {
        OkGo.<EntityOrderNum>post(URL.UrlGetOrder).execute(new JsonCallback<EntityOrderNum>(EntityOrderNum.class) {
            @Override
            public void onSuccess(Response<EntityOrderNum> response) {
                super.onSuccess(response);
                if (response.body().getCode() == 1) {
                    mTvOrder.setText("服务用户数:" + response.body().getData().getU_num() + "     接单数:" + response.body().getData().getO_num());
                }
            }
        });
    }

    private void initViewPager() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int widthWindows = wm.getDefaultDisplay().getWidth();
        int height = (int) (widthWindows / 2.85);
        LinearLayout.LayoutParams lp = new AppBarLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        mFlBanner.setLayoutParams(lp);
        mFlBanner.requestLayout();

        /*-------------------------------*/
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels == 0.0) {
                    if (position == list.size() - 1) {
                        mViewpager.setCurrentItem(1, false);
                    } else if (position == 0) {
                        mViewpager.setCurrentItem(list.size() - 2, false);
                    } else {
                        mViewpager.setCurrentItem(position);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        netBannerPager();


    }

    private void netBannerPager() {
        OkGo.<EntityBanner>post(URL.UrlAdverts).execute(new JsonCallback<EntityBanner>(EntityBanner.class) {
            @Override
            public void onSuccess(Response<EntityBanner> response) {
                super.onSuccess(response);
//                if (response.body().getCode() == 1) {
//                    /**加载广告页数据*/
//                    list = new ArrayList<>();
//                    for (int i = 0; i < response.body().getData().size(); i++) {
//                        View view = View.inflate(getActivity(), R.layout.fragment_pager_image, null);
//                        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.img);
//                        simpleDraweeView.setImageURI(response.body().getData().get(i).getImg());
//                        list.add(view);
//                    }
//                    bannerAdapter = new BannerPagerAdapter(list, getActivity(), response.body().getData());
//                    mViewpager.setAdapter(bannerAdapter);
//                    mIndicator.setViewPager(mViewpager);
//                }
            }
        });
    }

//    private void initMenu() {
//        mViewList = new ArrayList<>();
//
//        RecyclerView systemList = new RecyclerView(getActivity());
//        systemList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        final DropMenuListAdapter systemAdapter = new DropMenuListAdapter(getActivity(), Arrays.asList(Configs.system), false);
//        systemList.setAdapter(systemAdapter);
//        systemAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
//            @Override
//            public void onItemClick(ViewHolder viewHolder, String data, int position) {
//                systemAdapter.setCheckItem(position);
//                mDropDownMenu.setTabText(position == 0 ? Configs.headers[0] : Configs.system[position]);
//                mDropDownMenu.closeMenu();
//                netData();
//            }
//        });
//
//        RecyclerView serverList = new RecyclerView(getActivity());
//        serverList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        final DropMenuListAdapter serverAdapter = new DropMenuListAdapter(getActivity(), Arrays.asList(Configs.server), false);
//        serverList.setAdapter(serverAdapter);
//        serverAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
//            @Override
//            public void onItemClick(ViewHolder viewHolder, String data, int position) {
//                serverAdapter.setCheckItem(position);
//                mDropDownMenu.setTabText(position == 0 ? Configs.headers[1] : Configs.server[position]);
//                mDropDownMenu.closeMenu();
//                netData();
//            }
//        });
//
//        RecyclerView startDYList = new RecyclerView(getActivity());
//        startDYList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        final DropMenuListAdapter startDYAdapter = new DropMenuListAdapter(getActivity(), Arrays.asList(Configs.DY), false);
//        startDYList.setAdapter(startDYAdapter);
//        startDYAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
//            @Override
//            public void onItemClick(ViewHolder viewHolder, String data, int position) {
//                startDYAdapter.setCheckItem(position);
//                mDropDownMenu.setTabText(position == 0 ? Configs.headers[2] : Configs.DY[position]);
//                mDropDownMenu.closeMenu();
//                netData();
//            }
//        });
//
//        RecyclerView endDYList = new RecyclerView(getActivity());
//        endDYList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        final DropMenuListAdapter endDYAdapter = new DropMenuListAdapter(getActivity(), Arrays.asList(Configs.DY), false);
//        endDYList.setAdapter(endDYAdapter);
//        endDYAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
//            @Override
//            public void onItemClick(ViewHolder viewHolder, String data, int position) {
//                endDYAdapter.setCheckItem(position);
//                mDropDownMenu.setTabText(position == 0 ? Configs.headers[3] : Configs.DY[position]);
//                mDropDownMenu.closeMenu();
//                netData();
//            }
//        });
//
//        mViewList.add(systemList);
//        mViewList.add(serverList);
//        mViewList.add(startDYList);
//        mViewList.add(endDYList);
//        mDropDownMenu.setDropDownMenu(Arrays.asList(Configs.headers), mViewList, viewList);
//    }

    private void netData() {
        mHomeAdapter.setLoadingView();
        OkGo.<EntityOrderList>post(URL.UrlOrderList).params("page", page++).params("limit", Configs.limit)
                .params("game_id", "")
                .params("area", "")
                .params("game_start", "")
                .params("game_end", "")
                .execute(new JsonCallback<EntityOrderList>(EntityOrderList.class) {
                    @Override
                    public void onStart(Request<EntityOrderList, ? extends Request> request) {
                        super.onStart(request);
                        mRefresh.setRefreshing(true);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onSuccess(Response<EntityOrderList> response) {
                        super.onSuccess(response);
                        if (response.body().getCode() == 1) {
                            mHomeAdapter.setData(page, response.body().getData().getList_info());
                        } else {
                            if (page == Configs.page + 1) {
                                mHomeAdapter.setData(page, null);
                            } else {
                                mHomeAdapter.loadMoreEnd(page);
                            }
                        }
                    }
                });
    }
}
