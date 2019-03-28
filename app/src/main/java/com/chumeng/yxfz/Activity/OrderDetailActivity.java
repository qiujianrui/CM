package com.chumeng.yxfz.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chumeng.yxfz.Base.BaseActivity;
import com.chumeng.yxfz.Base.ContentBaseActivity;
import com.chumeng.yxfz.Entity.EntityOrderList;
import com.chumeng.yxfz.R;

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mLlBack;
    private TextView mTxtTitle;
    /**
     * 接单
     */
    private Button mBtnTake;
    /**
     * 订单标题:
     */
    private TextView mTvTitle;
    /**
     * 订单状态:
     */
    private TextView mTvState;
    /**
     * 订单编号:
     */
    private TextView mTvOrderNo;
    /**
     * 订单类型:
     */
    private TextView mTvType;
    /**
     * 游戏区服:
     */
    private TextView mTvArea;
    /**
     * 开始段位:
     */
    private TextView mTvStartStar;
    /**
     * 结束段位:
     */
    private TextView mTvEndStar;
    /**
     * 17￥
     */
    private TextView mTvPrice;
    /**
     * 备注:
     */
    private TextView mTvContent;
    private  EntityOrderList.DataBean.ListInfoBean mInfoBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initView();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }



    public void initView() {
        mLlBack = (LinearLayout) findViewById(R.id.ll_back);
        mLlBack.setOnClickListener(this);
        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        mTxtTitle.setText("订单详情");
        mBtnTake = (Button) findViewById(R.id.btn_take);
        mBtnTake.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvState = (TextView) findViewById(R.id.tv_state);
        mTvOrderNo = (TextView) findViewById(R.id.tv_order_no);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mTvArea = (TextView) findViewById(R.id.tv_area);
        mTvStartStar = (TextView) findViewById(R.id.tv_start_star);
        mTvEndStar = (TextView) findViewById(R.id.tv_end_star);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    protected void initData() {
        super.initData();
        mInfoBean= (EntityOrderList.DataBean.ListInfoBean) getIntent().getSerializableExtra("data");
        mTvTitle.setText("订单详情:"+mInfoBean.getTitle());
        mTvArea.setText("游戏区服:"+mInfoBean.getArea());
        mTvState.setText("订单状态:"+mInfoBean.getState()+"注意");
        mTvOrderNo.setText("订单编号:"+mInfoBean.getOrder_no());
        mTvType.setText("订单类型:"+mInfoBean.getType());
        mTvStartStar.setText("开始段位:"+mInfoBean.getStart_star());
        mTvEndStar.setText("结束段位:"+mInfoBean.getEnd_star());
        mTvPrice.setText(mInfoBean.getPrice());
        mTvContent.setText(mInfoBean.getContent());
    }
}
