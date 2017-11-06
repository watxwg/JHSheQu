package com.jhcms.common.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.jhcms.waimaiV3.MyApplication;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.BaseRequest;
import com.orhanobut.logger.Logger;

import okhttp3.Call;
import okhttp3.Response;

import static com.jhcms.common.utils.Api.CLIENT_OS;


/**
 * Created by admin on 2017/6/9.
 */
public class HttpUtils {
    /**
     * @param url 接口Api
     * @param paramsData 请求参数
     * @param isShow 是否显示默认动画
     * @param onRequestSuccessCallback 回调
     */
    static OkGo okGo = OkGo.getInstance();

    public static void postUrl(final Context context, final String url, final String paramsData, final boolean isShow, final OnRequestSuccessCallback onRequestSuccessCallback) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("User-Agent", MyApplication.useAgent);
        okGo.addCommonHeaders(headers)
                .post(Api.BASE_URL)
                .tag(context)
                .params("API", url)
                .params("CLIENT_API", Api.CLIENT_API)
                .params("CLIENT_OS", CLIENT_OS)
                .params("CLIENT_VER", Api.CLIENT_VER)
                .params("CITY_ID", Api.CITY_ID)
                .params("LAT", Api.LAT)
                .params("LNG", Api.LON)
                .params("REGISTRATION_ID", Api.REGISTRATION_ID)
                .params("TOKEN", Api.TOKEN)
                .params("data", paramsData == null ? "" : paramsData)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Logger.json(s);
                        try {
                            if (isShow) {
                                ProgressDialogUtil.dismiss(context);
                            }
                            onRequestSuccessCallback.onSuccess(url, s);
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        String logurl = Api.BASE_URL + "?API=" + url + "&CLIENT_API=" + Api.CLIENT_API + "&REGISTER_ID=" + Api.REGISTRATION_ID + "&LAT=" + Api.LAT + "&LNG=" + Api.LON + "&CLIENT_OS=" + CLIENT_OS
                                + "&CLIENT_VER=" + Api.CLIENT_VER + "&CITY_ID=" + Api.CITY_ID + "&TOKEN=" + Api.TOKEN + "&data=" + paramsData;
                        Logger.d(logurl);
                        if (isShow) {
                            ProgressDialogUtil.showProgressDialog(context);
                        }
                        onRequestSuccessCallback.onBeforeAnimate();
                    }


                    @Override
                    public void onAfter(@Nullable String s, @Nullable Exception e) {
                        super.onAfter(s, e);
                        if (isShow) {
                            ProgressDialogUtil.dismiss(context);
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (!TextUtils.isEmpty(e.getMessage())) {
                            Logger.d(e.getMessage().toString());
                        }
                        onRequestSuccessCallback.onErrorAnimate();
                        if (isShow) {
                            ProgressDialogUtil.dismiss(context);
                        }
                    }

                });
    }

    /**
     * post请求
     *
     * @param context
     * @param url              api
     * @param paramsData       接口参数
     * @param isShow           是否显示加载对话框
     * @param onRequestSuccess 请求成功的回调接口
     */
    public static void postUrlWithBaseResponse(final Context context
            , final String url
            , final String paramsData
            , final boolean isShow
            , final OnRequestSuccess onRequestSuccess) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("User-Agent", MyApplication.useAgent);
        okGo.addCommonHeaders(headers)
                .post(Api.BASE_URL)
                .tag(context)
                .params("API", url)
                .params("CLIENT_API", Api.CLIENT_API)
                .params("CLIENT_OS", CLIENT_OS)
                .params("CLIENT_VER", Api.CLIENT_VER)
                .params("CITY_ID", Api.CITY_ID)
                .params("LAT", Api.LAT)
                .params("LNG", Api.LON)
                .params("TOKEN", Api.TOKEN)
                .params("data", paramsData == null ? "" : paramsData)
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        String logurl = Api.BASE_URL + "?API=" + url + "&CLIENT_API=" + Api.CLIENT_API + "&REGISTER_ID=" + Api.REGISTRATION_ID + "&LAT=" + Api.LAT + "&LNG=" + Api.LON + "&CLIENT_OS=" + CLIENT_OS
                                + "&CLIENT_VER=" + Api.CLIENT_VER + "&CITY_ID=" + Api.CITY_ID + "&TOKEN=" + Api.TOKEN + "&data=" + paramsData;
                        Logger.d(logurl);
                        try {
                            if (isShow) {
                                ProgressDialogUtil.showProgressDialog(context);
                            }
                            onRequestSuccess.onBeforeAnimate();
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onSuccess(String t, Call call, Response response) {
                        Logger.json(t);
                        try {
                            onRequestSuccess.convert(t, url);
                        }catch (Exception e){
                            e.printStackTrace();

                            if (!TextUtils.isEmpty(e.getMessage())) {
                                Logger.e(e.getMessage().toString());
                            }
                            onRequestSuccess.onErrorAnimate();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (!TextUtils.isEmpty(e.getMessage())) {
                            Logger.e(e.getMessage().toString());
                        }
                        onRequestSuccess.onErrorAnimate();

                    }

                    @Override
                    public void onAfter(@Nullable String t, @Nullable Exception e) {
                        super.onAfter(t, e);
                        if (isShow) {
                            ProgressDialogUtil.dismiss(context);
                        }
                        onRequestSuccess.onAfter();
                    }
                });


    }


}
