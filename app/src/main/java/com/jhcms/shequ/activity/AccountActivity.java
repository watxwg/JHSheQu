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
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Response;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.TimeCount;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.shequ.model.EventBusEventModel;
import com.jhcms.shequ.model.HawkApi;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.activity.WebViewActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/4/6.
 */
public class AccountActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {


    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.mobile_et)
    EditText mobileEt;
    @Bind(R.id.verify_btn)
    Button verifyBtn;
    @Bind(R.id.verify_et)
    EditText verifyEt;
    @Bind(R.id.password_et)
    EditText passwordEt;
    @Bind(R.id.confirm_password_et)
    EditText confirmPasswordEt;
    @Bind(R.id.commit_btn)
    Button commitBtn;
    @Bind(R.id.lookxieyi)
    TextView mLookxieyi;
    private TimeCount time;
    private String SMS_CODE = "";
    private String mobile;
    private PopupWindow ImagePupwindow;


    @Override
    protected void initData() {
        setToolBar(toolbar);
        tvTitle.setText("注册");
        time = new TimeCount(60000, 1000, verifyBtn, this);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        mLookxieyi.setText("<查看用户协议>");
    }

    /**
     * 会员注册
     *
     * @param api 网址
     */
    private void RequestRegisterData(final String api) {
        if (TextUtils.isEmpty(mobileEt.getText())) {
            ToastUtil.show(getString(R.string.手机号码不能为空));
            return;
        }
        if (TextUtils.isEmpty(verifyEt.getText())) {
            ToastUtil.show(getString(R.string.验证码不能为空));
            return;
        }
        if (TextUtils.isEmpty(passwordEt.getText())) {
            ToastUtil.show(getString(R.string.密码不能为空));
            return;
        }
        if (passwordEt.getText().toString().trim().length() < 6) {
            ToastUtil.show(getString(R.string.密码长度不能少于6位));
            return;
        }
        if (TextUtils.isEmpty(confirmPasswordEt.getText())) {
            ToastUtil.show(getString(R.string.重复密码不能为空));
            return;
        }
        if (!passwordEt.getText().toString().equals(confirmPasswordEt.getText().toString())) {
            ToastUtil.show(getString(R.string.密码不一致));
            return;
        }

        JSONObject object = new JSONObject();
        try {
            object.put("mobile", mobileEt.getText().toString());
            object.put("passwd", passwordEt.getText().toString());
            object.put("sms_code", verifyEt.getText().toString());
            object.put("register_id", Api.REGISTRATION_ID);
            String str = object.toString();
            HttpUtils.postUrl(this, api, str, true, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.verify_btn, R.id.commit_btn, R.id.lookxieyi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verify_btn:
                mobile = mobileEt.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.show(getString(R.string.手机号为空));
                } else if (!Utils.isPhoneLegal(mobile)) {
                    ToastUtil.show(getString(R.string.手机格式有误));
                } else {
                    time.start();
                    RequestSMSData(Api.SHEQU_SMS_VERIFICATION);
                }
                break;
            case R.id.commit_btn:
                RequestRegisterData(Api.SHEQU_REGISTER);
                break;
            case R.id.lookxieyi:
                Intent intent = new Intent(AccountActivity.this, WebViewActivity.class);
                intent.putExtra("WEB_URL", "http://o2o.jhcms.cn/about/protocol.html");
                startActivity(intent);
                break;
        }
    }

    /**
     * 短信验证
     *
     * @param api
     */
    private void RequestSMSData(String api) {
        JSONObject object = new JSONObject();
        try {
            object.put("mobile", mobileEt.getText().toString());
            String str = object.toString();
            HttpUtils.postUrl(this, api, str, true, this);
        } catch (Exception e) {
            ToastUtil.show(getString(R.string.网络出现了小问题));
        }
    }

    /**
     * 求情图片验证码
     *
     * @param context
     * @param image
     */
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

    /**
     * 显示图片验证码
     */
    private void showImagePupwindows() {
        final View view = LayoutInflater.from(AccountActivity.this).inflate(R.layout.waimai_popu_imagewindow, null);
        ImagePupwindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        TextView cancelBtn = (TextView) view.findViewById(R.id.cancel);
        TextView truebtn = (TextView) view.findViewById(R.id.trueButton);
        final ImageView imagecode = (ImageView) view.findViewById(R.id.imagecode);
        requestImageCode(AccountActivity.this, imagecode);
        imagecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestImageCode(AccountActivity.this, imagecode);

            }
        });
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ImagePupwindow != null && ImagePupwindow.isShowing()) {
                    ImagePupwindow.dismiss();
                }
            }
        });
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

    private void SendImagecode(String s) {
        try {
            JSONObject js = new JSONObject();
            js.put("mobile", mobileEt.getText().toString());
            js.put("img_code", s);
            String str = js.toString();
            HttpUtils.postUrl(AccountActivity.this, Api.SHEQU_SMS_VERIFICATION, str, true, this);
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
                case Api.SHEQU_REGISTER:
                    if (response.error.equals("0")) {
                        //回到个人中心界面
                        if (response.data.token != null) {
                            Api.TOKEN = response.data.token;
                            if (Hawk.contains(HawkApi.SHEQU_USERINFO_TOKET)) {
                                Hawk.remove(HawkApi.SHEQU_USERINFO_TOKET);
                            }
                            Hawk.put(HawkApi.SHEQU_USERINFO_TOKET, response.data.token);
                        }
                        EventBusEventModel mModel = new EventBusEventModel();
                        mModel.setType(0);
                        mModel.setMessage("回到个人中心界面 type=0");
                        EventBus.getDefault().post(mModel);
                        ToastUtil.show(getString(R.string.注册成功));
                        finish();
                    } else {
                        ToastUtil.show(response.message);
                    }
                    break;
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
}
