package com.chumeng.yxfz.OKGO.callback;

import android.content.Context;

import com.google.gson.Gson;


public class JsonDialogCallback<T> extends DialogCallback<T> {
    public Class<T> className;

    public JsonDialogCallback(Context context, String dialogMessage, Class<T> c) {
        super(context, dialogMessage);
        className = c;
    }

    public JsonDialogCallback(Context context, String dialogMessage, Object okTag, Class<T> c) {
        super(context, dialogMessage, okTag);
        className = c;
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(response.body().string(), className);
            response.close();
            return t;
        }catch (Exception e){
            return null;
        }
    }


}
