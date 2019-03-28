package com.chumeng.yxfz.OKGO.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;


public abstract class DialogCallback<T> extends BaseCallback<T> {

    private ProgressDialog dialog;
    private Context context;


    public DialogCallback(final Context context, String dialogMessage) {
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(dialogMessage);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                OkGo.getInstance().cancelTag(context+"dialog");
            }
        });
    }

    public DialogCallback(final Context context, String dialogMessage, final Object okTag) {
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(dialogMessage);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                OkGo.getInstance().cancelTag(okTag);
            }
        });
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onSuccess(Response<T> response) {
        super.onSuccess(response);

    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        Toast.makeText(context, "网络出错，请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * T 不是String类型时需要重写此方法
     */
    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        return (T) response.body().string();
    }



}
