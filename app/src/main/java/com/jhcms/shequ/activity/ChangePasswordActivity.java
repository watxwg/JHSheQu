package com.jhcms.shequ.activity;

import android.content.Context;
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
import android.widget.PopupWindow;
import android.widget.ScrollView;
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
 * Created by Administrator on 2017/4/5.
 */
public class ChangePasswordActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {

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
    @Bind(R.id.password_et)
    EditText mPasswordEt;
    @Bind(R.id.confirm_password_et)
    EditText mConfirmPasswordEt;
    @Bind(R.id.commit_btn)
    Button mCommitBtn;

    String title, mobile;

    private TimeCount time;
    private String SMS_CODE = "";
    private boolean Isfalgforgot = true;
    private PopupWindow ImagePupwindow;

    @Override
    protected void initData() {
        setToolBar(toolbar);
        title = getIntent().getStringExtra("title");
        mobile = getIntent().getStringExtra("moblie");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText(title);
        if (TextUtils.isEmpty(mobile)) {
            mMobileEt.setEnabled(true);
            Isfalgforgot = true;
        } else {
            mMobileEt.setText(mobile);
            mMobileEt.setEnabled(false);
            Isfalgforgot = false;
        }
        time = new TimeCount(60000, 1000, mVerifyBtn, this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.verify_btn, R.id.commit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verify_btn:
                mobile = mMobileEt.getText().toString();
                if (TextUtils.isEmpty(mobile) && mobile.length() == 11) {
                    ToastUtil.show(getString(R.string.手机号为空));
                } else if (!Utils.isPhoneLegal(mobile)) {
                    ToastUtil.show(getString(R.string.手机格式有误));
                } else {
                    time.start();
                    RequestSMSData(Api.SHEQU_SMS_VERIFICATION);
                }
                break;
            case R.id.commit_btn:
                UpdateUserPassword();
                break;
        }
    }

    private void UpdateUserPassword() {
        if (TextUtils.isEmpty(mMobileEt.getText())) {
            ToastUtil.show(getString(R.string.手机号码不能为空));
            return;
        }
        if (TextUtils.isEmpty(mVerifyEt.getText())) {
            ToastUtil.show(getString(R.string.验证码不能为空));
            return;
        }
        if (TextUtils.isEmpty(mPasswordEt.getText())) {
            ToastUtil.show(getString(R.string.新密码不能为空));
            return;
        }
        if (mPasswordEt.getText().toString().trim().length() < 6) {
            ToastUtil.show(getString(R.string.密码长度不能少于6位));
            return;
        }
        if (TextUtils.isEmpty(mConfirmPasswordEt.getText())) {
            ToastUtil.show(getString(R.string.重复密码不能为空));
            return;
        }
        if (!mPasswordEt.getText().toString().equals(mConfirmPasswordEt.getText().toString())) {
            ToastUtil.show(getString(R.string.密码不一致));
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            if (Isfalgforgot) {
                jsonObject.put("mobile", mMobileEt.getText().toString());
            }
            jsonObject.put("sms_code", mVerifyEt.getText().toString());
            jsonObject.put("new_passwd", mConfirmPasswordEt.getText().toString());
            String str = jsonObject.toString();
            if (Isfalgforgot) {
                HttpUtils.postUrl(this, Api.SHEQU_USSERFOGOT, str, true, this);
            } else {
                HttpUtils.postUrl(this, Api.WAIMAI_PASSWAD, str, true, this);
            }
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
            ToastUtil.show("网络出现了小问题！");
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
        final View view = LayoutInflater.from(ChangePasswordActivity.this).inflate(R.layout.waimai_popu_imagewindow, null);
        ImagePupwindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        TextView cancelBtn = (TextView) view.findViewById(R.id.cancel);
        TextView truebtn = (TextView) view.findViewById(R.id.trueButton);
        final ImageView imagecode = (ImageView) view.findViewById(R.id.imagecode);
        requestImageCode(ChangePasswordActivity.this, imagecode);
        imagecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestImageCode(ChangePasswordActivity.this, imagecode);

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

    private void SendImagecode(String s) {
        try {
            JSONObject js = new JSONObject();
            js.put("mobile", mMobileEt.getText().toString());
            js.put("img_code", s);
            String str = js.toString();
            HttpUtils.postUrl(ChangePasswordActivity.this, Api.SHEQU_SMS_VERIFICATION, str, true, this);
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
                    String sms_conde = response.data.sms_code;
                    if (sms_conde.equals("1")) {
                        showImagePupwindows();
                    } else {
                        SMS_CODE = response.data.code;
                    }
                    break;
                case Api.WAIMAI_PASSWAD:
                    if (response.error.equals("0")) {
                        ToastUtil.show("密码修改成功");
                        if (Hawk.contains(HawkApi.SHEQU_USERINFO_TOKET)) {
                            Hawk.remove(HawkApi.SHEQU_USERINFO_TOKET);
                        }
                        Api.TOKEN = "";
                        EventBusEventModel mModel = new EventBusEventModel();
                        mModel.setType(1);
                        mModel.setMessage("回到登录界面 type=1");
                        EventBus.getDefault().post(mModel);
                        ChangePasswordActivity.this.finish();
                    } else {
                        ToastUtil.show(response.message);
                    }
                    break;
                case Api.SHEQU_USSERFOGOT://忘记密码
                    if (response.error.equals("0")) {
                        ToastUtil.show("修改成功");
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

}
