package com.chumeng.yxfz.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chumeng.yxfz.Base.BaseActivity;
import com.chumeng.yxfz.Config.URL;
import com.chumeng.yxfz.Entity.EntityBaseData;
import com.chumeng.yxfz.OKGO.callback.JsonCallback;
import com.chumeng.yxfz.OKGO.callback.JsonDialogCallback;
import com.chumeng.yxfz.R;
import com.chumeng.yxfz.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 请输入手机号
     */
    private EditText mEditPhone;
    /**
     * 请输入验证码
     */
    private EditText mEditPhoneVerify;
    private TextView mTxtDownTime;
    private LinearLayout mRlResendVerify;
    /**
     * 请输入密码
     */
    private EditText mEditPwd;
    /**
     * 请输入用户名
     */
    private EditText mEditUserName;
    /**
     * 注册
     */
    private Button mAlBtRegister;
    private static final int RETRY_INTERVAL = 60;
    private int time = RETRY_INTERVAL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        addListener();
    }

    public void initView() {
        mEditPhone = (EditText) findViewById(R.id.edit_phone);
        mEditPhoneVerify = (EditText) findViewById(R.id.edit_phone_verify);
        mTxtDownTime = (TextView) findViewById(R.id.txt_downTime);
        mTxtDownTime.setText("获取验证码");
        mTxtDownTime.setTextColor(getResources().getColor(R.color.gray));
        mRlResendVerify = (LinearLayout) findViewById(R.id.rl_resend_verify);
        mRlResendVerify.setOnClickListener(this);
        mRlResendVerify.setEnabled(false);
        mEditPwd = (EditText) findViewById(R.id.edit_pwd);
        mEditUserName = (EditText) findViewById(R.id.edit_user_name);
        mAlBtRegister = (Button) findViewById(R.id.al_bt_register);
        mAlBtRegister.setOnClickListener(this);
    }

    @Override
    protected void addListener() {
        super.addListener();
        mEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==11){
                    mRlResendVerify.setEnabled(true);
                    mTxtDownTime.setTextColor(getResources().getColor(R.color.colorAccent));
                }else {
                    mRlResendVerify.setEnabled(false);
                    mTxtDownTime.setTextColor(getResources().getColor(R.color.gray));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_resend_verify:
                handler.removeMessages(0x100);
                countDownTime();
                netSetMsg();
                break;
            case R.id.al_bt_register:
                if (mEditPhone.getText().toString().isEmpty()){
                    ToastUtils.showShortToast("手机号码不能为空");
                    return;
                }
                if (mEditPhoneVerify.getText().toString().isEmpty()){
                ToastUtils.showShortToast("验证码不能为空");
                return;
            }
                if (mEditPwd.getText().toString().isEmpty()){
                    ToastUtils.showShortToast("密码不能为空");
                    return;
                }
                if (mEditUserName.getText().toString().isEmpty()){
                    ToastUtils.showShortToast("用户名不能为空");
                    return;
                }
                netRegister();
                break;
        }
    }

    private void netSetMsg() {
        OkGo.<EntityBaseData>post(URL.UrlRegSendCode).tag(this).params("phone",mEditPhone.getText().toString())
                .execute(new JsonDialogCallback<EntityBaseData>(this, "获取中...",this,EntityBaseData.class){
                    @Override
                    public void onSuccess(Response<EntityBaseData> response) {
                        super.onSuccess(response);
                        if (response.body().getCode()==1){
                            ToastUtils.showShortToast(response.body().getMessage());
                        }else {
                            ToastUtils.showShortToast(response.body().getMessage());
                        }
                    }
                });
    }

    private void netRegister() {
        OkGo.<EntityBaseData>post(URL.UrlSave_user_info)
                .tag(this)
                .params("phone",mEditPhone.getText().toString())
                .params("vcode",mEditPhoneVerify.getText().toString())
                .params("pass",mEditPwd.getText().toString())
                .params("username",mEditUserName.getText().toString())
                .execute(new JsonDialogCallback<EntityBaseData>(this, "注册中...",this,EntityBaseData.class){
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

    /**
     * 60s倒计时
     */
    private void countDownTime() {
        time--;
        if (time == 0) {
            sendError();
        } else {
            mTxtDownTime.setTextColor(getResources().getColor(R.color.gray));
            mTxtDownTime.setText(time + "s后重新获取");
            mRlResendVerify.setEnabled(false);
            handler.sendEmptyMessageDelayed(0x100, 1000);
        }

    }

    private void sendError() {
        handler.removeMessages(0x100);
        time = RETRY_INTERVAL;
        mTxtDownTime.setText("重新获取验证码");
        mRlResendVerify.setEnabled(true);
    }


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x100:
                    countDownTime();
                    break;
            }
        }
    };
}
