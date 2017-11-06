package com.jhcms.common.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.jhcms.mall.model.BaseResponse;
import com.orhanobut.logger.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 作者：WangWei
 * 日期：2017/8/21 11:59
 * 描述：网络请求成功回调接口
 */

public abstract class OnRequestSuccess<T> implements JsonConvert<T>{
    @Override
    public void convert(String json, String url) {
        Type genType = getClass().getGenericSuperclass();
        //这里得到的是T的类型
        Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        Log.e("----------", "convertSuccess: "+type.getClass().getName());
        if(!(type instanceof ParameterizedType)){
            throw new IllegalArgumentException("BaseResponse中没有传入泛型");
        }
        Type rawType = ((ParameterizedType) type).getRawType();
        Type typeArguement = ((ParameterizedType) type).getActualTypeArguments()[0];
        Gson gson = new Gson();

        if (json == null) {
            return;
        }
        Logger.json(json);
        if(rawType!= BaseResponse.class){
            T data = gson.fromJson(json,type);
            onSuccess(url,data);
        }else{
            BaseResponse baseResponse = gson.fromJson(json,type);
            int error = baseResponse.error;
            if(error==0){
                onSuccess(url, (T) baseResponse);
            }else if(error==101){
                ToastUtil.show(baseResponse.message);
                Utils.GoLogin(ActivityCollector.getTopActivity());
                throw new IllegalStateException("用户未登录");
            }else{
                ToastUtil.show(baseResponse.message);
                throw new IllegalStateException("错误代码:"+error+",错误信息:"+baseResponse.message);
            }
        }
    }

    public void onSuccess(String url, T data){};
    public void onBeforeAnimate(){};
    public void onErrorAnimate(){};
    public void onAfter(){};
}
