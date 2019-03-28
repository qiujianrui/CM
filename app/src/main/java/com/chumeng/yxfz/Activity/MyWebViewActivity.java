package com.chumeng.yxfz.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chumeng.yxfz.Base.BaseActivity;
import com.chumeng.yxfz.Config.URL;
import com.chumeng.yxfz.R;
import com.chumeng.yxfz.util.ToastUtils;
import com.lzy.okgo.OkGo;

import com.lzy.okgo.request.base.Request;


import java.io.File;
import java.util.List;

import okhttp3.Cookie;


public class MyWebViewActivity extends BaseActivity {
    public static final int TYPE_DIGGING = 100;//矿区咨讯
    public static final int TYPE_BANNER = 200;//宏观咨讯
    public static final int TYPE_RELAXEDMOMENT = 300;//轻松一刻
    public static final int WEB_TYPE_URL = 1;//1:url模式
    public static final int WEB_TYPE_DATA = 2;//2:HTML数据模式（请求接口）
    private ProgressBar progressBar;
    private WebView webView;
    private WebSettings settings;
    private String url = "";
    private int type;
    private int webType;//webview加载模式
    private String id = "";
    private ImageView back;
    private TextView txt_title, txt_error;
    private RelativeLayout rl_title;
    private ImageView img_loading;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        useImmerseStatusBar(true, R.color.white);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initWebView();
        loadWebView();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        webView.reload ();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    public static void openWebViewHideTitle(Context context, String url) {
        Intent intent = new Intent(context, MyWebViewActivity.class);
        intent.putExtra("webType", WEB_TYPE_URL);
        intent.putExtra("url", url);
        intent.putExtra("titleHide", true);
        context.startActivity(intent);
    }

    public static void openWebView(Context context, String url) {
        Intent intent = new Intent(context, MyWebViewActivity.class);
        intent.putExtra("webType", WEB_TYPE_URL);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void openWebView(Context context, String url, String title) {
        Intent intent = new Intent(context, MyWebViewActivity.class);
        intent.putExtra("webType", WEB_TYPE_URL);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }



    private void initWebView() {
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        animationDrawable = (AnimationDrawable) img_loading.getDrawable();

        txt_error = (TextView) findViewById(R.id.txt_error);
        txt_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                webView.reload();
                txt_error.setVisibility(View.GONE);
            }
        });
        webView = (WebView) findViewById(R.id.webView);
        txt_title = (TextView) findViewById(R.id.txt_title);
        if (getIntent().getStringExtra("title") != null) {
            txt_title.setText(getIntent().getStringExtra("title"));
        }
        if (getIntent().getBooleanExtra("titleHide", false)) {
            rl_title.setVisibility(View.GONE);
        }
        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        settings.supportMultipleWindows();//多窗口
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setAllowFileAccess(true);//设置可以访问文件
//        settings.setAppCacheEnabled(true);//开启 Application Caches 功能
        settings.setSupportZoom(false);  //支持缩放 支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(false);//设置内置的缩放控件。
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //解决https地址中存在http图片不显示问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (!animationDrawable.isRunning()) {
                    animationDrawable.start();
                }
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.GONE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }

            }

            /**
             * 过时的方法：openFileChooser
             */
            public void openFileChooser(ValueCallback<Uri> filePathCallback) {

            }

            /**
             * 过时的方法：openFileChooser
             */
            public void openFileChooser(ValueCallback filePathCallback, String acceptType) {

            }

            /**
             * 过时的方法：openFileChooser
             */
            public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {

            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                super.onShowFileChooser(webView, filePathCallback, fileChooserParams);


                Log.d("jere", "onShowFileChooser: 被调用几次？");
                /**
                 * 返回true，如果filePathCallback被调用；返回false，如果忽略处理
                 */

                return true;
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                txt_title.setText(title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d("jere", "onJsAlert: " + url + "===" + message + "=====" + result);
                new AlertDialog.Builder(MyWebViewActivity.this)
                        .setTitle("提示")
                        .setMessage(message)
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//
//                            }
//                        })
                        .setNegativeButton("确定", null)
                        .show();
                result.confirm();//这里必须调用，否则页面会阻塞造成假死
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                Log.d("jere", "onJsConfirm: " + url + "===" + message + "=====" + result);
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Log.d("jere", "onJsPrompt: " + url + "===" + message + "=====" + result);
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("jere", "onPageFinished==" + url);
                imgReset();
                img_loading.setVisibility(View.GONE);
                animationDrawable.stop();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.loadData("", "text/html", "UTF-8");
                Log.d("jere", "error==" + error + "--request===" + request);
                txt_error.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
                Log.d("jere", "handler==" + handler + "host==" + host);
            }
        });

//
    }


    private void loadWebView() {
        webType = getIntent().getIntExtra("webType", 0);
        switch (webType) {
            case WEB_TYPE_URL:
                url = getIntent().getStringExtra("url");
                /**------------同步cook到url-------------------------------------------------*/
//                CookieStore cookieStore = OLDOkGo.getInstance().getCookieJar().getCookieStore();
//                HttpUrl httpUrl = HttpUrl.parse(Host.Host);
//                List<Cookie> cookies = cookieStore.getCookie(httpUrl);
//                syncCookie(url, cookies, this);
                /**--------------------------------------------------------------------------*/
                webView.loadUrl(url);
                break;


        }


    }



    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        try {
            webView.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName('img'); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "var img = objs[i];   " +
                    "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                    "}" +
                    "})()");
        } catch (Exception e) {

        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {

    }






    /**
     * 将cookie同步到WebView
     *
     * @param url     WebView要加载的url
     * @param cookies 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public static boolean syncCookie(String url, List<Cookie> cookies, Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(context);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        for (Cookie c : cookies) {
            cookieManager.setCookie(url, c.toString());//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
        }
        String newCookie = cookieManager.getCookie(url);
        return TextUtils.isEmpty(newCookie) ? false : true;
    }

}
