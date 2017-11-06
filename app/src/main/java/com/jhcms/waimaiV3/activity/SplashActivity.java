package com.jhcms.waimaiV3.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.coorchice.library.SuperTextView;
import com.google.gson.Gson;
import com.jhcms.common.listener.PermissionListener;
import com.jhcms.common.model.Data_Get_Adv;
import com.jhcms.common.model.Data_Get_WeChat;
import com.jhcms.common.model.Response_Get_WeChat;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.shequ.activity.MainActivity;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.orhanobut.hawk.Hawk;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Api.GET_WECHAT;
import static com.jhcms.common.utils.Utils.isFastDoubleClick;
import static com.jhcms.common.utils.Utils.saveCrashInfo2File;
import static com.jhcms.waimaiV3.MyApplication.WX_APP_APPSECRET;
import static com.jhcms.waimaiV3.MyApplication.WX_APP_ID;

public class SplashActivity extends BaseActivity {


    private int mCount = 5;
    private List<Data_Get_Adv.ItemsEntity> advItems;
    private Boolean isFirst = true;
    private ArrayList<String> bannerImages;
    private Thread thread;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_WECHAT_INFO:
                    getWechat();
                    break;
                case 0:
                    countTv.setText("跳过 " + mCount + "s");
                    if (mCount == 0) {
                        init();
                    }
                    break;
                case 1:
                    if (null != advItems && advItems.size() > 0) {
                        bannerImages = new ArrayList<>();
                        for (int i = 0; i < advItems.size(); i++) {
                            bannerImages.add(advItems.get(i).thumb);
                        }
                        initBanner(bannerImages);

                    } else {
                        ffAd.setVisibility(View.GONE);
                        init();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Bind(R.id.banner)
    ConvenientBanner banner;
    @Bind(R.id.count_tv)
    SuperTextView countTv;
    @Bind(R.id.ff_root)
    FrameLayout ffRoot;
    @Bind(R.id.ff_ad)
    FrameLayout ffAd;
    private final int GET_WECHAT_INFO = 0x100;

    @Override
    protected void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splish);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeThread();
    }

    @Override
    protected void initData() {
        MyApplication.useAgent = getUserAgent();
        advItems = new ArrayList<>();
        /*是否第一次打开app*/
        isFirst = Hawk.get("isFirst", true);
        /*广告位链接*/
        advItems = Hawk.get("advkey");
        handler.sendEmptyMessage(GET_WECHAT_INFO);
        ObjectAnimator animator = ObjectAnimator.ofFloat(ffRoot, "alpha", 0f, 1f);
        animator.setDuration(3000);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                requestRuntimePermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        handler.sendEmptyMessage(1);
                        thread = new Thread(() -> {
                            for (int i = 0; i < 5; i++) {
                                try {
                                    Thread.sleep(1000);
                                    mCount--;
                                    Message message = Message.obtain();
                                    message.what = 0;
                                    message.arg1 = mCount;
                                    handler.sendMessage(message);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });
            }
        });
    }

    private void init() {
        if (isFirst) {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();
    }

    public void getWechat() {
        HttpUtils.postUrl(this, GET_WECHAT, null, false, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                try {
                    Gson gson = new Gson();
                    Response_Get_WeChat getWeChat = gson.fromJson(Json, Response_Get_WeChat.class);
                    if (getWeChat.error.equals("0")) {
                        Data_Get_WeChat weChat = getWeChat.data;
                        WX_APP_ID = weChat.data.app_appid;
                        WX_APP_APPSECRET = weChat.data.app_appsecret;
                        //友盟分享  TODO 需要修改
                        PlatformConfig.setWeixin(weChat.data.app_appid, weChat.data.app_appsecret);
                        PlatformConfig.setQQZone("1105874442", "QZQwhz4x4ndf57H6");
                    }
                } catch (Exception e) {
                    ToastUtil.show(getString(R.string.数据解析异常));
                    saveCrashInfo2File(e);
                }

            }

            @Override
            public void onBeforeAnimate() {

            }

            @Override
            public void onErrorAnimate() {

            }
        });
    }

    private void initBanner(final List<String> images) {
        banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, images)
                .startTurning(Integer.MAX_VALUE)
                .setPageIndicator(new int[]{R.mipmap.icon_banner_normal, R.mipmap.icon_banner_choose})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(position -> {
                    if (!isFastDoubleClick()) {
                        if (!TextUtils.isEmpty(advItems.get(position).link) && advItems.get(position).link.startsWith("http")) {
                            Utils.dealWithHomeLink(SplashActivity.this, advItems.get(position).link, "splash");
                            removeThread();
                            finish();
                        }
                    }
                });
        ffAd.setVisibility(View.VISIBLE);
    }

    private void removeThread() {
        try {
            if (thread != null) {
                thread.interrupt();
            }
            handler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.count_tv)
    public void onViewClicked() {
        init();
    }

    public class LocalImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(context)
                    .load(Api.IMAGE_URL + data)
                    .placeholder(R.mipmap.splash) //占位图
                    .error(R.mipmap.splash)  //出错的占位图
                    .into(imageView);
        }
    }

    public String getUserAgent() {
        WebView webview = new WebView(SplashActivity.this);
        String user_agent = webview.getSettings().getUserAgentString() + "com.jhcms.android";
        return user_agent;
    }


}
