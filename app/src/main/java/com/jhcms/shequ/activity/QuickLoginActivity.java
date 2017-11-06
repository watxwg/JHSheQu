package com.jhcms.shequ.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Response;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.TimeCount;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.shequ.model.EventBusEventModel;
import com.jhcms.shequ.model.HawkApi;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.orhanobut.hawk.Hawk;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/4/6.
 */
public class QuickLoginActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.mobile_et)
    EditText mMobileEt;
    @Bind(R.id.verify_btn)
    Button mVerifyBtn;
    @Bind(R.id.verify_et)
    EditText mVerifyEt;
    @Bind(R.id.login_btn)
    Button mLoginBtn;
    @Bind(R.id.login_tv)
    TextView mLoginTv;
    @Bind(R.id.wechat_ll)
    LinearLayout mWechatLl;

    String mobile, verifyCode, password;

    private TimeCount time;
    private String SMS_CODE = "";
    private String wx_openid;
    private String wx_unionid;
    private String wx_nickname;
    private String wx_headimgurl;
    private String type;
    private PopupWindow ImagePupwindow;

    @Override
    protected void initData() {
        setToolBar(toolbar);
        type = getIntent().getStringExtra("type");
        if (type.equals("wxbind")) {
            wx_openid = getIntent().getStringExtra("wx_openid");
            wx_unionid = getIntent().getStringExtra("wx_unionid");
            wx_nickname = getIntent().getStringExtra("wx_nickname");
            wx_headimgurl = getIntent().getStringExtra("wx_headimgurl");
            tvTitle.setText(R.string.微信绑定);
            mLoginBtn.setText(R.string.绑定);
        } else {
            tvTitle.setText(R.string.快捷登录);
            mLoginBtn.setText(R.string.登录);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
        time = new TimeCount(60000, 1000, mVerifyBtn, this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_quick_login);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.verify_btn, R.id.login_btn, R.id.login_tv, R.id.wechat_ll, R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verify_btn:
                mobile = mMobileEt.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.show(getString(R.string.手机号为空));
                } else if (!Utils.isPhoneLegal(mobile)) {
                    ToastUtil.show(getString(R.string.手机格式有误));
                } else {
                    time.start();
                    RequestSMSData(Api.SHEQU_SMS_VERIFICATION);
                }
                break;
            case R.id.login_btn:
                if (!TextUtils.isEmpty(type) && type.equals("wxbind")) {
                    RequestRegisterData(Api.WXBIND);
                } else {
                    loginData();
                }
                break;
            case R.id.wechat_ll:
                inintThirdParty();
                break;
            case R.id.button:
                showImagePupwindows();
                break;
        }
    }

    private void loginData() {
        try {
            if (TextUtils.isEmpty(mMobileEt.getText())) {
                ToastUtil.show("手机号不能为空！");
                return;
            }

            if (TextUtils.isEmpty(mVerifyEt.getText())) {
                ToastUtil.show("验证码不能为空！");
                return;
            }
            JSONObject js = new JSONObject();
            js.put("mobile", mMobileEt.getText().toString());
            js.put("sms_code", mVerifyEt.getText().toString());
            String str = js.toString();
            HttpUtils.postUrl(this, Api.LOGIN, str, true, this);

        } catch (Exception e) {

        }
    }


    private void inintThirdParty() {
        UMShareAPI.get(this).doOauthVerify(QuickLoginActivity.this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                ProgressDialogUtil.showProgressDialog(QuickLoginActivity.this);
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                String temp = "";
                for (String key : map.keySet()) {
                    temp = temp + key + " : " + map.get(key) + "\n";
                }
                ProgressDialogUtil.dismiss(QuickLoginActivity.this);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                ToastUtil.show("授权失败");
                LogUtil.e("onStart: " + 3 + "错误" + throwable.getMessage());
                ProgressDialogUtil.dismiss(QuickLoginActivity.this);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                ToastUtil.show("授权取消");
                ProgressDialogUtil.dismiss(QuickLoginActivity.this);
            }
        });
    }

    private void RequestRegisterData(String shequRegister) {
        if (TextUtils.isEmpty(mMobileEt.getText().toString())) {
            ToastUtil.show(getString(R.string.手机号不能为空));
            return;
        }
        if (TextUtils.isEmpty(mVerifyEt.getText())) {
            ToastUtil.show(getString(R.string.验证码不能为空));
            return;
        }
        JSONObject object = new JSONObject();
        try {
            object.put("mobile", mMobileEt.getText().toString());
            object.put("sms_code", SMS_CODE);
            object.put("register_id", Api.REGISTRATION_ID);
            if (!TextUtils.isEmpty(type) && type.equals("wxbind")) {
                object.put("wx_openid", wx_openid);
                object.put("wx_unionid", wx_unionid);
                object.put("wx_nickname", wx_nickname);
                object.put("wx_headimgurl", wx_headimgurl);
            }
            String str = object.toString();
            HttpUtils.postUrl(this, shequRegister, str, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RequestSMSData(String shequSmsVerification) {
        JSONObject object = new JSONObject();
        try {
            object.put("mobile", mMobileEt.getText().toString());
            String str = object.toString();
            HttpUtils.postUrl(this, shequSmsVerification, str, true, this);
        } catch (Exception e) {
            ToastUtil.show(getString(R.string.网络出现了小问题));
        }
    }

    /**
     * 显示图片验证码
     */
    private void showImagePupwindows() {
        final View view = LayoutInflater.from(QuickLoginActivity.this).inflate(R.layout.waimai_popu_imagewindow, null);
        ImagePupwindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        TextView cancelBtn = (TextView) view.findViewById(R.id.cancel);
        TextView truebtn = (TextView) view.findViewById(R.id.trueButton);
        final ImageView imagecode = (ImageView) view.findViewById(R.id.imagecode);
        requestImageCode(QuickLoginActivity.this, imagecode);
        imagecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestImageCode(QuickLoginActivity.this, imagecode);
            }
        });
        ScrollView layout = (ScrollView) view.findViewById(R.id.layout);
        layout.getBackground().setAlpha(255 / 2);
        final EditText imageEdtext = (EditText) view.findViewById(R.id.ImagecodeEd);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ImagePupwindow != null && ImagePupwindow.isShowing()) {
                    ImagePupwindow.dismiss();
                }
            }
        });
        truebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(imageEdtext.getText())) {
                    ToastUtil.show("图片验证码不能为空!");
                    return;
                }
                if (ImagePupwindow != null && ImagePupwindow.isShowing()) {
                    ImagePupwindow.dismiss();
                    SendImagecode(imageEdtext.getText().toString());
                }


            }


        });
        ImagePupwindow.setContentView(view);
        if (ImagePupwindow != null && !ImagePupwindow.isShowing()) {
            ImagePupwindow.showAtLocation(this.findViewById(R.id.top), Gravity.CENTER, 0, 0);
        }
    }

    private void requestImageCode(Context context, final ImageView image) {
        final OkGo okGo = OkGo.getInstance();
        HttpHeaders headers = new HttpHeaders();
        okGo.addCommonHeaders(headers)
                .post(Api.BASE_URL)
                .tag(context)
                .addCookie("aaaa", "bbbb")
                .params("API", Api.WAIMAI_VERIFY)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, okhttp3.Response response) {
                        image.setImageBitmap(bitmap);
                    }
                });
    }

    private void SendImagecode(String s) {
        try {
            JSONObject js = new JSONObject();
            js.put("mobile", mMobileEt.getText().toString());
            js.put("img_code", s);
            String str = js.toString();
            HttpUtils.postUrl(QuickLoginActivity.this, Api.SHEQU_SMS_VERIFICATION, str, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Response response = gson.fromJson(Json, Response.class);
            switch (url) {
                case Api.SHEQU_SMS_VERIFICATION:
                    if (response.error.equals("0")) {
                        String sms_conde = response.data.sms_code;
                        if (sms_conde.equals("1")) {
                            showImagePupwindows();

                        } else {
                            SMS_CODE = response.data.code;
                        }
                    } else {
                        ToastUtil.show(response.message);
                    }
                    break;
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
                        EventBusEventModel model = new EventBusEventModel();
                        model.setType(0);
                        model.setMessage("回到主界面");
                        EventBus.getDefault().post(model);

                        finish();
                    } else {
                        ToastUtil.show(response.message);
                    }
                    break;
                case Api.WXBIND:
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

            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(QuickLoginActivity.this).onActivityResult(requestCode, resultCode, data);
    }

}
