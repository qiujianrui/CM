package com.chumeng.yxfz.OKGO.callback;

import com.lzy.okgo.callback.AbsCallback;

import okhttp3.Response;

/**
 * Created by hzf on 2017/4/19.
 */

public class BaseCallback<T> extends AbsCallback<T> {
    @Override
    public T convertResponse(Response response) throws Throwable {
        return null;
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {

    }



}
