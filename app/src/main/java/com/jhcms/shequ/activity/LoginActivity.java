package com.jhcms.shequ.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Response;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.ClearEditText;
import com.jhcms.shequ.model.EventBusEventModel;
import com.jhcms.shequ.model.HawkApi;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.orhanobut.hawk.Hawk;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

/**
 * Created by Administrator on 2017/4/5.
 */
public class LoginActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.mobile_et)
    ClearEditText mobileEt;
    @Bind(R.id.password_et)
    ClearEditText passwordEt;
    @Bind(R.id.account_tv)
    TextView accountTv;
    @Bind(R.id.login_tv)
    TextView loginTv;
    @Bind(R.id.quick_login_tv)
    TextView quickLoginTv;
    @Bind(R.id.forget_tv)
    TextView forgetTv;
    @Bind(R.id.wechat_ll)
    LinearLayout wechatLl;
    private String mobile;
    private String passwd;
    private String openid;
    private String unionid;
    private String name;
    private String imageUrl;

    @Override
    protected void initData() {
        setToolBar(toolbar);
        tvTitle.setText("登录");
        toolbar.setNavigationOnClickListener(v -> finish());
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    private void inintThirdParty() {
        UMShareAPI.get(this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                ProgressDialogUtil.showProgressDialog(LoginActivity.this);
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                String temp = "";
                for (String key : map.keySet()) {
                    temp = temp + key + " : " + map.get(key) + "\n";
                }
                openid = map.get("openid");
                unionid = map.get("unionid");
                name = map.get("name");
                imageUrl = map.get("profile_image_url");
                requestWXBind(openid, unionid);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                ToastUtil.show("授权失败");
                LogUtil.e("onStart: " + 3 + "错误" + throwable.getMessage());
                ProgressDialogUtil.dismiss(LoginActivity.this);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                ToastUtil.show("授权取消");
                ProgressDialogUtil.dismiss(LoginActivity.this);
            }
        });
    }


    private void requestWXBind(String openid, String unionid) {
        JSONObject object = new JSONObject();
        try {
            object.put("wx_openid", openid);
            object.put("wx_unionid", unionid);
            HttpUtils.postUrl(this, Api.WXLOGIN, object.toString(), true, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.account_tv, R.id.login_tv, R.id.quick_login_tv, R.id.forget_tv, R.id.wechat_ll})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.account_tv:
                intent.setClass(LoginActivity.this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.login_tv:
                login();
                break;
            case R.id.quick_login_tv:
                intent.setClass(LoginActivity.this, QuickLoginActivity.class);
                intent.putExtra("type", "wxlogin");
                startActivity(intent);
                break;
            case R.id.forget_tv:
                intent.setClass(LoginActivity.this, ChangePasswordActivity.class);
                intent.putExtra("title", "忘记密码");
                startActivity(intent);
                break;
            case R.id.wechat_ll:
                if (UMShareAPI.get(this).isAuthorize(this, SHARE_MEDIA.WEIXIN)) {
                    UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                            inintThirdParty();
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media, int i) {

                        }
                    });
                } else {
                    inintThirdParty();
                }
                break;
        }
    }

    private void login() {
        mobile = mobileEt.getText().toString();
        passwd = passwordEt.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.show("请输入手机号");
        } else if (!Utils.isPhoneLegal(mobile)) {
            ToastUtil.show("请输入正确的手机号");
        } else if (TextUtils.isEmpty(passwd)) {
            ToastUtil.show("请输入密码");
        } else {
            requestLogin(Api.LOGIN);
        }
    }

    private void requestLogin(String api) {
        JSONObject object = new JSONObject();
        try {
            object.put("mobile", mobile);
            object.put("passwd", passwd);
            object.put("register_id", Api.REGISTRATION_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, api, str, true, this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(EventBusEventModel event) {
        if (event.getType() == 0) {
            LoginActivity.this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(LoginActivity.this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Response response = gson.fromJson(Json, Response.class);
            switch (url) {
                case Api.LOGIN:
                    if (response.error.equals("0")) {
                        if (response.data.token != null) {
                            Api.TOKEN = response.data.token;
                            Hawk.put("user", response.data);
                            if (Hawk.contains(HawkApi.SHEQU_USERINFO_TOKET)) {
                                Hawk.remove(HawkApi.SHEQU_USERINFO_TOKET);
                            }
                            Hawk.put(HawkApi.SHEQU_USERINFO_TOKET, response.data.token);
                        }
                        finish();
                    } else {
                        ToastUtil.show(response.message);
                    }
                    break;
                case Api.WXLOGIN:
                    if (response.error.equals("0")) {
                        if (response.data.wxtype.equals("wxbind")) {
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, QuickLoginActivity.class);
                            intent.putExtra("type", "wxbind");
                            intent.putExtra("wx_openid", openid);
                            intent.putExtra("wx_unionid", unionid);
                            intent.putExtra("wx_nickname", name);
                            intent.putExtra("wx_headimgurl", imageUrl);
                            startActivity(intent);
                            finish();
                        } else if (response.data.wxtype.equals("wxlogin")) {
                            if (response.data.token != null) {
                                Api.TOKEN = response.data.token;
                                Hawk.put("user", response.data);
                                if (Hawk.contains(HawkApi.SHEQU_USERINFO_TOKET)) {
                                    Hawk.remove(HawkApi.SHEQU_USERINFO_TOKET);
                                }
                                Hawk.put(HawkApi.SHEQU_USERINFO_TOKET, response.data.token);
                            }
                            finish();
                        }
                    }


                    break;
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

}
