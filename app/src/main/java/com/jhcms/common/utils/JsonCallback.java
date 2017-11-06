package com.jhcms.common.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jhcms.mall.model.BaseResponse;
import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 作者：WangWei
 * 日期：2017/8/21 11:25
 * 描述：针对网络库OkGo的自定义callback，传入的泛型T中还必须包含一层泛型，否则会报错，例如T为：BaseResponse<String>
 */

public class JsonCallback<T> extends AbsCallback<T> {
    @Override
    public void onSuccess(T t, Call call, Response response) {

    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        Type genType = getClass().getGenericSuperclass();
        Log.e("----------", "convertSuccess: "+genType.getClass().getName());
        //这里得到的是T的类型
        Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        Log.e("----------", "convertSuccess: "+type.getClass().getName());
        if(!(type instanceof ParameterizedType)){
            throw new IllegalArgumentException("BaseResponse中没有传入泛型");
        }
        Type rawType = ((ParameterizedType) type).getRawType();
        Type typeArguement = ((ParameterizedType) type).getActualTypeArguments()[0];
        Gson gson = new Gson();

        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }
        JsonReader jsonReader =new JsonReader(body.charStream());
        if(rawType!= BaseResponse.class){
            T data = gson.fromJson(jsonReader,type);
            response.close();
            return data;
        }else{
            BaseResponse baseResponse = gson.fromJson(jsonReader,type);
            response.close();
            int error = baseResponse.error;
            if(error==0){
                return (T)baseResponse;
            }else if(error==101){
                throw new IllegalStateException("用户未登录");
            }else{
                throw new IllegalStateException("错误代码:"+error+",错误信息:"+baseResponse.message);
            }
        }
    }
}
