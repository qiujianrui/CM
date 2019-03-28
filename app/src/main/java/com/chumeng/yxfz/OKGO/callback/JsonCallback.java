package com.chumeng.yxfz.OKGO.callback;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;

import okhttp3.Response;

/**
 * Created by THINK on 2017/10/25.
 */

public class JsonCallback<T> extends AbsCallback<T> {
    protected Class<T> className;

    public JsonCallback(Class<T> c) {
        className = c;
    }



    @Override
    public T convertResponse(Response response) throws Throwable {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(response.body().string(), className);
            response.close();
            return t;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {

    }
}
