package com.chumeng.yxfz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chumeng.yxfz.Base.BaseActivity;
import com.chumeng.yxfz.Config.URL;
import com.chumeng.yxfz.Entity.EntityLogin;
import com.chumeng.yxfz.OKGO.callback.JsonDialogCallback;
import com.chumeng.yxfz.R;
import com.chumeng.yxfz.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    /**
     * 请输入账号
     */
    private EditText alEtUsername;
    private ImageView alIvClean;
    /**
     * 请输入密码
     */
    private EditText alEtPwd;
    private Button alBtLogin;
    /**
     * 还没有账号?立即注册
     */
    private Button mAlBtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        useImmerseStatusBar(true, android.R.color.transparent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    protected void initView() {
        alEtUsername = (EditText) findViewById(R.id.al_et_username);
        alIvClean = (ImageView) findViewById(R.id.al_iv_clean);
        alEtPwd = (EditText) findViewById(R.id.al_et_pwd);
        alBtLogin = (Button) findViewById(R.id.al_bt_login);
        alBtLogin.setOnClickListener(this);
        alIvClean.setOnClickListener(this);
        alEtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    alIvClean.setVisibility(View.VISIBLE);
                } else {
                    alIvClean.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mAlBtRegister = (Button) findViewById(R.id.al_bt_register);
        mAlBtRegister.setOnClickListener(this);
    }


    /**
     * 登录
     */
    private void login() {
        final String account = alEtUsername.getText().toString().trim();
        String pwd = alEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else {
            OkGo.<EntityLogin>post(URL.UrlLoginAction).tag(this)
                    .params("phone",account)
                    .params("pass",pwd)
                    .execute(new JsonDialogCallback<EntityLogin>(this, "登录中...",this,EntityLogin.class)
                    {
                        @Override
                        public void onSuccess(Response<EntityLogin> response) {
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


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.al_bt_login:
                login();
                break;
            case R.id.al_iv_clean:
                alEtUsername.setText("");
                alIvClean.setVisibility(View.GONE);
                break;
            case R.id.al_bt_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
