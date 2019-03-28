package com.chumeng.yxfz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chumeng.yxfz.Base.BaseActivity;
import com.chumeng.yxfz.Config.Configs;
import com.chumeng.yxfz.Config.URL;
import com.chumeng.yxfz.Dialog.DialogListFragment;
import com.chumeng.yxfz.Entity.EntityBaseData;
import com.chumeng.yxfz.Entity.EntityListData;
import com.chumeng.yxfz.OKGO.callback.JsonDialogCallback;
import com.chumeng.yxfz.R;
import com.chumeng.yxfz.util.ToastUtils;
import com.chumeng.yxfz.util.UtilUploadImage;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrderActivity extends BaseActivity implements View.OnClickListener {


    private static final int IMAGE_PICKER1 = 0x331;
    private FrameLayout mFlUp1;
    private Intent mIntent;
    /**
     * 请输入标题,如黄金升王者
     */
    private EditText mEditTitle;
    /**
     * 请点击选择
     */
    private TextView mTvSystem;
    /**
     * 请点击选择
     */
    private TextView mTvArea;
    /**
     * 请点击选择
     */
    private TextView mTvStartStar;
    /**
     * 请点击选择
     */
    private TextView mTvEndStar;

    private DialogListFragment mDialogListFragment;
    private String end_star, start_star, area, type;
    /**
     * 请点击选择
     */
    private TextView mTvType;
    /**
     * 我要下单
     */
    private TextView mTvDoCreate;
    private List<String> imgs;
    /**
     * 请输入备注
     */
    private EditText mEditContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        initView();
    }


    public void initView() {
        mFlUp1 = (FrameLayout) findViewById(R.id.fl_up1);
        mFlUp1.setOnClickListener(this);
        mEditTitle = (EditText) findViewById(R.id.edit_title);
        mTvSystem = (TextView) findViewById(R.id.tv_system);
        mTvArea = (TextView) findViewById(R.id.tv_area);
        mTvStartStar = (TextView) findViewById(R.id.tv_start_star);
        mTvEndStar = (TextView) findViewById(R.id.tv_end_star);
        mTvSystem.setOnClickListener(this);
        mTvArea.setOnClickListener(this);
        mTvStartStar.setOnClickListener(this);
        mTvEndStar.setOnClickListener(this);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mTvType.setOnClickListener(this);
        mTvDoCreate = (TextView) findViewById(R.id.tv_doCreate);
        mTvDoCreate.setOnClickListener(this);
        mEditContent = (EditText) findViewById(R.id.edit_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.fl_up1:
                mIntent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(mIntent, IMAGE_PICKER1);
                break;
            case R.id.tv_system:
                break;
            case R.id.tv_area:
                getArea();
                break;
            case R.id.tv_start_star:
                getStartStar();
                break;
            case R.id.tv_end_star:
                getEndStar();
                break;
            case R.id.tv_type:
                getType();
                break;
            case R.id.tv_doCreate:
                doCreate();
                break;
        }
    }

    private void doCreate() {
        OkGo.<EntityBaseData>post(URL.UrlDoCreate).tag("UrlGetGameArea")
                .params("game_id", Configs.gameId1)
                .params("type", type)
                .params("title", mEditTitle.getText().toString())
                .params("content", mEditContent.getText().toString())
                .params("area",area)
                .params("start_star",start_star)
                .params("end_star",end_star)
                .params("imgs","")
                .execute(new JsonDialogCallback<EntityBaseData>(this, "下单中...","UrlGetGameArea",EntityBaseData.class){
                    @Override
                    public void onSuccess(Response<EntityBaseData> response) {
                        super.onSuccess(response);
                        if (response.body().getCode()==1){
                            ToastUtils.showShortToast(response.body().getMessage());
                            finish();
                        }else {
                            ToastUtils.showShortToast(response.body().getMessage());
                        }
                    }
                });

    }

    private void getType() {
        List<EntityListData.DataBean> list = new ArrayList<>();
        for (String s : Configs.type) {
            list.add(new EntityListData.DataBean(s));
        }
        mDialogListFragment = new DialogListFragment(list, mTvType, new DialogListFragment.CallBackListener() {
            @Override
            public void onPosition(EntityListData.DataBean dataBean) {
                type = dataBean.getName();
            }
        });
        mDialogListFragment.show(getFragmentManager(), "list");
    }

    private void getArea() {
        OkGo.<EntityListData>post(URL.UrlGetGameArea).tag(this)
                .params("game_id", Configs.gameId1)
                .execute(new JsonDialogCallback<EntityListData>(this, "请求中..,", this,EntityListData.class) {
                    @Override
                    public void onSuccess(Response<EntityListData> response) {
                        super.onSuccess(response);
                        mDialogListFragment = new DialogListFragment(response.body().getData(), mTvArea, new DialogListFragment.CallBackListener() {
                            @Override
                            public void onPosition(EntityListData.DataBean dataBean) {
                                area = dataBean.getId();
                            }
                        });
                        mDialogListFragment.show(getFragmentManager(), "list");
                    }


                });
    }

    private void getStartStar() {
        OkGo.<EntityListData>post(URL.UrlGetGameStart).params("game_id", Configs.gameId1)
                .execute(new JsonDialogCallback<EntityListData>(this, "请求中..,", EntityListData.class) {
                    @Override
                    public void onSuccess(Response<EntityListData> response) {
                        super.onSuccess(response);
                        mDialogListFragment = new DialogListFragment(response.body().getData(), mTvStartStar, new DialogListFragment.CallBackListener() {
                            @Override
                            public void onPosition(EntityListData.DataBean dataBean) {
                                start_star = dataBean.getId();
                            }
                        });
                        mDialogListFragment.show(getFragmentManager(), "list");
                    }


                });
    }

    private void getEndStar() {
        OkGo.<EntityListData>post(URL.UrlGetGameEnd).params("game_id", Configs.gameId1)
                .execute(new JsonDialogCallback<EntityListData>(this, "请求中..,", EntityListData.class) {
                    @Override
                    public void onSuccess(Response<EntityListData> response) {
                        super.onSuccess(response);
                        mDialogListFragment = new DialogListFragment(response.body().getData(), mTvEndStar, new DialogListFragment.CallBackListener() {
                            @Override
                            public void onPosition(EntityListData.DataBean dataBean) {
                                end_star = dataBean.getId();
                            }
                        });
                        mDialogListFragment.show(getFragmentManager(), "list");
                    }


                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER1 && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            UtilUploadImage.uploadImage(images, this, new UtilUploadImage.OnGetImageListener() {
                @Override
                public void getImage(String name, String url) {
                    if (name != null && url != null) {
                        imgs.add(name);
                    }
                }
            });
        }
    }
}
