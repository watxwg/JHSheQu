package com.jhcms.shequ.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
 * Created by Administrator on 2017/4/6.
 */
public class ChangeMobileActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {

    @Bind(R.id.mobile_et)
    EditText mMobileEt;
    @Bind(R.id.new_mobile_et)
    EditText mNewMobileEt;
    @Bind(R.id.verify_btn)
    Button mVerifyBtn;
    @Bind(R.id.confirm_password_et)
    EditText mConfirmPasswordEt;
    @Bind(R.id.commit_btn)
    Button mCommitBtn;
    @Bind(R.id.mtvPhone)
    TextView mtvPhone;
    String mobile, password;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.top)
    LinearLayout top;
    private TimeCount time;
    private String kefuPhone;
    private PopupWindow ImagePupwindow;

    @Override
    protected void initData() {
        setToolBar(toolbar);
        tvTitle.setText("换绑手机号");
        toolbar.setNavigationOnClickListener(v -> finish());
        time = new TimeCount(60000, 1000, mVerifyBtn, this);
        inintGetIntent();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_mobile);
        ButterKnife.bind(this);
    }

    private void inintGetIntent() {
        Intent inten = getIntent();
        if (inten != null) {
            mMobileEt.setText(inten.getStringExtra("moblie"));
            kefuPhone = inten.getStringExtra("kefu");
            mtvPhone.setText("若旧手机丢失请联系客服，客服热线:" + kefuPhone);
        }
    }


    @OnClick({R.id.verify_btn, R.id.commit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verify_btn:
                mobile = mNewMobileEt.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.show("手机号不能为空！");
                } else {
                    time.start();
                    RequestSMSData(Api.SHEQU_SMS_VERIFICATION);
                }
                break;
            case R.id.commit_btn:
                RequestMobileData(Api.SHEQU_UPDATEMOBILE);
                break;
        }
    }

    private void RequestMobileData(String shequUpdatemobile) {
        if (TextUtils.isEmpty(mMobileEt.getText())) {
            ToastUtil.show("手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(mNewMobileEt.getText())) {
            ToastUtil.show("新手机号不能为空！");
            return;
        }
        if (!Utils.isPhoneLegal(mNewMobileEt.getText().toString())) {
            ToastUtil.show("新手机号格式不正确");
            return;
        }
        if (TextUtils.isEmpty(mConfirmPasswordEt.getText())) {
            ToastUtil.show("验证码不能为空！");
            return;
        }

        JSONObject js = new JSONObject();
        try {
            js.put("sms_code", mConfirmPasswordEt.getText().toString().trim());
            js.put("new_mobile", mNewMobileEt.getText().toString());
            String str = js.toString();
            HttpUtils.postUrl(this, shequUpdatemobile, str, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RequestSMSData(String shequSmsVerification) {
        JSONObject object = new JSONObject();
        try {
            object.put("mobile", mNewMobileEt.getText().toString());
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
        final View view = LayoutInflater.from(ChangeMobileActivity.this).inflate(R.layout.waimai_popu_imagewindow, null);
        ImagePupwindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        TextView cancelBtn = (TextView) view.findViewById(R.id.cancel);
        TextView truebtn = (TextView) view.findViewById(R.id.trueButton);
        final ImageView imagecode = (ImageView) view.findViewById(R.id.imagecode);
        requestImageCode(ChangeMobileActivity.this, imagecode);
        imagecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestImageCode(ChangeMobileActivity.this, imagecode);
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
            js.put("mobile", mNewMobileEt.getText().toString());
            js.put("img_code", s);
            String str = js.toString();
            HttpUtils.postUrl(ChangeMobileActivity.this, Api.SHEQU_SMS_VERIFICATION, str, true, this);
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
                        }
                    } else {
                        ToastUtil.show(response.message);
                    }
                    break;
                case Api.SHEQU_UPDATEMOBILE:
                    if (response.error.equals("0")) {
                        ToastUtil.show("手机修改成功");
                        if (Hawk.contains(HawkApi.SHEQU_USERINFO_TOKET)) {
                            Hawk.remove(HawkApi.SHEQU_USERINFO_TOKET);
                            Api.TOKEN = "";
                            EventBusEventModel mModel = new EventBusEventModel();
                            mModel.setType(1);
                            mModel.setMessage("回到登录界面 type=1");
                            EventBus.getDefault().post(mModel);
                            ChangeMobileActivity.this.finish();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
