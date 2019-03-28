package com.chumeng.yxfz.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.view.Window;
import android.widget.Toast;

import com.chumeng.yxfz.Config.URL;
import com.chumeng.yxfz.Entity.EntityUpImgs;
import com.chumeng.yxfz.OKGO.callback.JsonCallback;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.luban.Luban;
import com.lzy.imagepicker.luban.OnCompressListener;
import com.lzy.okgo.OkGo;


import java.io.File;
import java.util.ArrayList;

/**
 * Created by qiu on 2018/8/20.
 */

public class UtilUploadImage {
    private static ProgressDialog progressDialog;
    public interface OnGetImageListener {
        void getImage(String name, String url);
    }
    /**
     * 获取压缩图片保存路径
     *
     * @return
     */
    private static String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/mwt/LBimage/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }
    private static void initDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("图片上传中，请稍后...");
    }
    public static void uploadImage(ArrayList<ImageItem> imageItems, final Context context, final OnGetImageListener listener) {
        initDialog(context);
        if (imageItems != null && imageItems.size() > 0) {
            for (int i = 0; i < imageItems.size(); i++) {
                luBanImage(context, new File(imageItems.get(i).path), 512, true, listener);
            }
        }
    }

    private static void luBanImage(final Context context, File file, int size, final Boolean isShowProgress, final OnGetImageListener listener) {
        Luban.with(context).load(file).ignoreBy(size).setTargetDir(getPath()).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                if (isShowProgress) {
                    if (progressDialog == null) {
                        initDialog(context);
                    }
                    progressDialog.show();
                }
            }

            @Override
            public void onSuccess(File file) {
                sendImage(context, file, listener);
            }

            @Override
            public void onError(Throwable e) {
                if (isShowProgress) {
                    progressDialog.dismiss();
                }
                Toast.makeText(context, "请重新选择图片！", Toast.LENGTH_SHORT).show();
            }
        }).launch();
    }

    /**
     * 上传图片
     *
     * @param context
     * @param file
     * @param listener
     */
    private static void sendImage(final Context context, File file, final OnGetImageListener listener) {
        OkGo.<EntityUpImgs>post(URL.UrlUpload).tag(context)
                .params("img", file)
                .execute(new JsonCallback<EntityUpImgs>(EntityUpImgs.class) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<EntityUpImgs> response) {
                        super.onSuccess(response);
                        try {
                            EntityUpImgs body = response.body();
                            if (body.getCode()==1){
                            if (listener != null) {
                                listener.getImage(body.getData(), body.getData());
                            }
                            }else {
                                Toast.makeText(context,body.getMessage(),Toast.LENGTH_SHORT ).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(context,"图片上传失败",Toast.LENGTH_SHORT ).show();
                            if (listener != null) {
                                listener.getImage(null, null);
                            }
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<EntityUpImgs> response) {
                        super.onError(response);
                        Toast.makeText(context,"图片上传失败",Toast.LENGTH_SHORT ).show();
                        if (listener != null) {
                            listener.getImage(null, null);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (progressDialog != null)
                            progressDialog.dismiss();
                    }
                });
    }
}
