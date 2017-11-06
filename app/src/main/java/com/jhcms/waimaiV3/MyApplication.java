package com.jhcms.waimaiV3;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.aitangba.swipeback.ActivityLifecycleHelper;
import com.jhcms.common.utils.Api;
import com.jhcms.shequ.model.HawkApi;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import org.litepal.LitePalApplication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by admin on 2016/3/1.
 */
public class MyApplication extends LitePalApplication {

    public static Context context;
    public static String useAgent;
    /*微信支付key*/
    public static String WX_APP_ID;
    public static String WX_APP_APPSECRET;
    public static String MAP = Api.GAODE;
    //跑腿小费选择列表
    public static ArrayList<String> runXiaoFei=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        //获取Context
        context = getApplicationContext();
        /*极光配置*/
        initJpush();
        /*初始化UM*/
        initUM();
        /*初始化Logger*/
        initLogger();
        initHawk();
        initToke();
        /*初始化滑动返回*/
        registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());
        initOkGo();
    }

    public static Context getContext() {
        return context;

    }

    private void initUM() {
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
    }

    private void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initOkGo() {
        //必须调用初始化
        OkGo.init(this);
        HttpHeaders headers = new HttpHeaders();
        headers.put("User-Agent", useAgent);
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
                    .debug("OkGo")
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(20000)  //全局的连接超时时间
                    .setReadTimeOut(20000)     //全局的读取超时时间
                    .setWriteTimeOut(20000)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //如果不想让框架管理cookie,以下不需要
//                .setCookieStore(new MemoryCookieStore())                //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore())         //cookie持久化存储，如果cookie不过期，则一直有效

                    //可以设置https的证书,以下几种方案根据需要自己设置,不需要不用设置
//                    .setCertificates()                                  //方法一：信任所有证书
//                    .setCertificates(getAssets().open("srca.cer"))      //方法二：也可以自己设置https证书
//                    .setCertificates(getAssets().open("aaaa.bks"), "123456", getAssets().open("srca.cer"))//方法三：传入bks证书,密码,和cer证书,支持双向加密

                    //可以添加全局拦截器,不会用的千万不要传,错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

//                    //这两行同上,不需要就不要传
                    .addCommonHeaders(headers);                                       //设置全局公共头
//                    .addCommonParams(params);                                          //设置全局公共参数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initToke() {
        Api.WAIMAI_TOKEN = Api.TOKEN = Hawk.get(HawkApi.SHEQU_USERINFO_TOKET, "");
    }

    private void initHawk() {
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(com.orhanobut.hawk.LogLevel.FULL)
                .build();
    }


    private void initLogger() {
        //log日志
        Logger.init("shequ")               // default tag : PRETTYLOGGER or use just init()
                .setMethodCount(3)            // default 2
                .hideThreadInfo()             // default it is shown
                .setLogLevel(LogLevel.FULL);  // LogLevel.FULL  打印日志  LogLevel.NONE  不打印日志
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        /*非默认值*/
        if (newConfig.fontScale != -1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != -1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            /*设置默认*/
            resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                createConfigurationContext(newConfig);
            } else {
                resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
            }
        }
        return resources;
    }


}
