package com.jhcms.shequ.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jhcms.common.dialog.actionsheet.ActionSheetDialog;
import com.jhcms.common.dialog.actionsheet.OnOperItemClickL;
import com.jhcms.common.galleryfinal.GlideImageLoader;
import com.jhcms.common.model.Data;
import com.jhcms.common.model.Response;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.DownUtils;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.shequ.model.EventBusEventModel;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.BaseRequest;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/4/6.
 */
public class PersonalActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {
    private final int CAPTURE_IMAGE = 10001;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.head_iv)
    RoundImageView headIv;
    @Bind(R.id.head_layout)
    RelativeLayout headLayout;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.sex_tv)
    TextView sexTv;
    @Bind(R.id.mobile_tv)
    TextView mobileTv;
    @Bind(R.id.status_tv)
    TextView statusTv;
    @Bind(R.id.change_tv)
    TextView changeTv;
    @Bind(R.id.versionname_tv)
    TextView versionnameTv;

    private PopupWindow mSexPopuwindows;
    private String nickname;
    private String moblie;
    private int sex;
    private String kefuMoble;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                RequestData();
            }
        }
    };
    private Data data;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Message message = new Message();
        message.what = 0;
        mHandler.sendMessage(message);
    }

    @Override
    protected void initData() {
        setToolBar(toolbar);
        tvTitle.setText("账户信息");
        versionnameTv.setText(Utils.getVersion());
        toolbar.setNavigationOnClickListener(v -> finish());
        inintGallerFinal();

    }

    private void inintmSexPopuwindows() {
        View view = LayoutInflater.from(PersonalActivity.this).inflate(R.layout.waimai_sex_layout, null);
        TextView mtvnan = (TextView) view.findViewById(R.id.textnan);
        TextView mTvnv = (TextView) view.findViewById(R.id.textnv);
        final ImageView mImageNan = (ImageView) view.findViewById(R.id.nanImage);
        final ImageView mImageNv = (ImageView) view.findViewById(R.id.nvImage);
        mtvnan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = 1;
                updataStatuSex(mImageNan, mImageNv, sex);
                if (mSexPopuwindows != null && mSexPopuwindows.isShowing()) {
                    mSexPopuwindows.dismiss();
                }
                updataUserSex(sex);
            }
        });
        mTvnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = 2;
                updataStatuSex(mImageNan, mImageNv, sex);
                if (mSexPopuwindows != null && mSexPopuwindows.isShowing()) {
                    mSexPopuwindows.dismiss();
                }
                updataUserSex(sex);
            }
        });

        mImageNan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = 1;
                updataStatuSex(mImageNan, mImageNv, sex);
                if (mSexPopuwindows != null && mSexPopuwindows.isShowing()) {
                    mSexPopuwindows.dismiss();
                }
                updataUserSex(sex);
            }
        });

        mImageNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = 2;
                updataStatuSex(mImageNan, mImageNv, sex);
                if (mSexPopuwindows != null && mSexPopuwindows.isShowing()) {
                    mSexPopuwindows.dismiss();
                }
                updataUserSex(sex);
            }
        });
        updataStatuSex(mImageNan, mImageNv, sex);
        mSexPopuwindows = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mSexPopuwindows.setContentView(view);
        mSexPopuwindows.setOutsideTouchable(true);
        mSexPopuwindows.setBackgroundDrawable(new BitmapDrawable());
        mSexPopuwindows.setFocusable(true);// 获取焦点
        mSexPopuwindows.setClippingEnabled(false);
        mSexPopuwindows.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mSexPopuwindows.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        updataStatuSex(mImageNan, mImageNv, sex);
        if (mSexPopuwindows != null && !mSexPopuwindows.isShowing()) {
            mSexPopuwindows.showAtLocation(findViewById(R.id.all_rlay), Gravity.BOTTOM, 0, Utils.getNavigationBarHeight(PersonalActivity.this));
        }

    }

    private void updataUserSex(int sex) {
        try {
            JSONObject js = new JSONObject();
            js.put("sex", sex);
            String str = js.toString();
            HttpUtils.postUrl(this, Api.WAIMAI_UPSATESEX, str, true, this);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show("网络出现了问题");
        }
    }

    public void updataStatuSex(ImageView mImageNan, ImageView mImageNv, int flag) {
        switch (flag) {
            case 1:
                mImageNv.setImageResource(R.mipmap.index_selector_disable);
                mImageNan.setImageResource(R.mipmap.index_selector_enable);
                break;
            case 2:
                mImageNan.setImageResource(R.mipmap.index_selector_disable);
                mImageNv.setImageResource(R.mipmap.index_selector_enable);
                break;
        }

    }

    private void inintGallerFinal() {
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(PersonalActivity.this, new GlideImageLoader(), theme).build();
        GalleryFinal.init(coreConfig);
    }

    private void RequestData() {
        try {
            HttpUtils.postUrl(this, Api.SHEQU_USERINFO, null, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.head_layout, R.id.name_tv, R.id.sex_tv, R.id.rl_version, R.id.mobile_tv, R.id.status_tv, R.id.change_tv})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.head_layout:
                ActionSheetDialogNoTitle();
                break;
            case R.id.name_tv:
                Intent intent1 = new Intent(PersonalActivity.this, RenameActivity.class);
                intent1.putExtra("nickname", nickname);
                startActivity(intent1);
                break;
            case R.id.sex_tv:
                inintmSexPopuwindows();
                break;
            case R.id.rl_version:
                DownUtils.getAppver(this, true);
                break;
            case R.id.mobile_tv:
                intent.setClass(PersonalActivity.this, ChangeMobileActivity.class);
                intent.putExtra("moblie", moblie);
                intent.putExtra("kefu", kefuMoble);
                startActivity(intent);
                break;
            case R.id.status_tv:
                if (TextUtils.isEmpty(data.wx_unionid) && TextUtils.isEmpty(data.wx_openid)) {
                    if (UMShareAPI.get(PersonalActivity.this).isAuthorize(PersonalActivity.this, SHARE_MEDIA.WEIXIN)) {
                        UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {

                            }

                            @Override
                            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                                bindWeixin();
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media, int i) {

                            }
                        });
                    } else {
                        bindWeixin();
                    }

                } else {
                    unbindWeixin();
                }
                break;
            case R.id.change_tv:
                intent.setClass(PersonalActivity.this, ChangePasswordActivity.class);
                intent.putExtra("moblie", moblie);
                intent.putExtra("title", "修改密码");
                startActivity(intent);
                break;
        }
    }

    private void unbindWeixin() {
        HttpUtils.postUrl(this, Api.WAIMAI_NOBINDWEIXIN, null, true, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                Gson gson = new Gson();
                SharedResponse mshare = gson.fromJson(Json, SharedResponse.class);
                if (mshare.error.equals("0")) {
                    ToastUtil.show("解绑成功");
                    Message message = Message.obtain();
                    message.what = 0;
                    mHandler.sendMessage(message);
                } else {
                    ToastUtil.show("解绑失败");
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

    private void bindWeixin() {
        UMShareAPI.get(this).getPlatformInfo(PersonalActivity.this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                ProgressDialogUtil.showProgressDialog(PersonalActivity.this);
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                ProgressDialogUtil.dismiss(PersonalActivity.this);
                String wx_openid = map.get("openid");
                String wx_nickname = map.get("screen_name");
                String wx_face = map.get("profile_image_url");
                String wx_unionid = map.get("unionid");

                try {
                    JSONObject js = new JSONObject();
                    js.put("wx_openid", wx_openid);
                    js.put("wx_nickname", wx_nickname);

                    if (wx_face == null) {
                        js.put("wx_face", Api.IMAGE_URL + data.face);
                    } else if (wx_face.length() <= 0) {
                        js.put("wx_face", Api.IMAGE_URL + data.face);
                    } else {
                        js.put("wx_face", wx_face);
                    }

                    js.put("wx_unionid", wx_unionid);
                    String str = js.toString();
                    HttpUtils.postUrl(PersonalActivity.this, Api.WAIMAI_BINDWEIXIN, str, true, new OnRequestSuccessCallback() {
                        @Override
                        public void onSuccess(String url, String Json) {
                            Gson gson = new Gson();
                            SharedResponse mshare = gson.fromJson(Json, SharedResponse.class);
                            if (mshare.error.equals("0")) {
                                ToastUtil.show("绑定成功");
                                Message message = Message.obtain();
                                message.what = 0;
                                mHandler.sendMessage(message);

                            } else {
                                ToastUtil.show("绑定失败");
                            }
                        }

                        @Override
                        public void onBeforeAnimate() {

                        }

                        @Override
                        public void onErrorAnimate() {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                ProgressDialogUtil.dismiss(PersonalActivity.this);
                ToastUtil.show("授权失败");

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                ProgressDialogUtil.dismiss(PersonalActivity.this);
                ToastUtil.show("授权取消");
            }
        });
    }

    private void ActionSheetDialogNoTitle() {
        View decorView = getWindow().getDecorView();
        final String[] stringItems = {getString(R.string.从相册上传头像), getString(R.string.拍照换头像)};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, decorView);
        dialog.isTitleShow(false).show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {//从相册选取图片
                    openGallerySingle();
                } else {
                    openCamera();
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 单选打开相册
     */
    private void openGallerySingle() {
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setForceCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        GalleryFinal.openGallerySingle(10001, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                Glide.with(PersonalActivity.this).load(resultList.get(0).getPhotoPath()).into(headIv);
                UploadFaceImage(resultList.get(0).getPhotoPath());
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
            }
        });
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setEnablePreview(true)
                .setForceCrop(true)
                .setCropSquare(true)//裁剪正方形
                .build();
        GalleryFinal.openCamera(CAPTURE_IMAGE, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                Glide.with(PersonalActivity.this).load(resultList.get(0).getPhotoPath()).into(headIv);
                UploadFaceImage(resultList.get(0).getPhotoPath());
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
            }
        });
    }

    /**
     * 上传头像
     *
     * @param path
     */
    private void UploadFaceImage(String path) {
        ArrayList<File> files = new ArrayList<>();
        files.add(new File(path));
        HttpHeaders headers = new HttpHeaders();
        headers.put("User-Agent", MyApplication.useAgent);
        final OkGo okGo = OkGo.getInstance();
        okGo.addCommonHeaders(headers)
                .post(Api.BASE_URL)
                .tag(PersonalActivity.this)
                .params("API", Api.SHEQU_UPLOADFACE)
                .params("CLIENT_API", Api.CLIENT_API)
                .params("CLIENT_OS", Api.CLIENT_OS)
                .params("CLIENT_VER", Api.CLIENT_VER)
                .params("CITY_ID", Api.CITY_ID)
                .params("TOKEN", Api.TOKEN)
                .addFileParams("face", files)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        Gson gson = new Gson();
                        SharedResponse mshare = gson.fromJson(s, SharedResponse.class);
                        if (mshare.error.equals("0")) {
                            ToastUtil.show("上传成功");
                            ProgressDialogUtil.dismiss(PersonalActivity.this);
                            RequestData();
                        } else {
                            ToastUtil.show(mshare.message);
                        }

                    }

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        ProgressDialogUtil.showProgressDialog(PersonalActivity.this);
                    }

                    @Override
                    public void onAfter(@Nullable String s, @Nullable Exception e) {
                        super.onAfter(s, e);
                        ProgressDialogUtil.dismiss(PersonalActivity.this);
                    }
                });
    }

    private void bindViewData(Response response) {
        if (response.data != null && response.data.face != null) {
            Glide.with(PersonalActivity.this).load(Api.IMAGE_URL + response.data.face).into(headIv);
        }
        String wx_unionid = response.data.wx_unionid;
        String wx_openid = response.data.wx_openid;
        data = response.data;
        if (!TextUtils.isEmpty(wx_unionid) || !TextUtils.isEmpty(wx_openid)) {
            statusTv.setText("解绑微信");
        } else {
            statusTv.setText("去绑定");
        }
        kefuMoble = response.data.kefu_phone;
        if (response.data.sex != null) {
            sex = Integer.parseInt(response.data.sex);
            if (sex == 1) {
                sexTv.setText("男");
            } else if (sex == 2) {
                sexTv.setText("女");
            }
        } else {
            sexTv.setText("请选择性别");
        }
        if (response.data.nickname != null) {
            nameTv.setText(response.data.nickname);
            nickname = response.data.nickname;
        }
        if (response.data.mobile != null) {
            moblie = response.data.mobile;
            mobileTv.setText(response.data.mobile);
        }
    }

    @Override
    public void onSuccess(String url, String Json) {
        Gson gson = new Gson();
        switch (url) {
            case Api.SHEQU_USERINFO:
                Response response = gson.fromJson(Json, Response.class);
                if (response.error.equals("0")) {
                    bindViewData(response);
                }

                break;
            case Api.WAIMAI_UPSATESEX:
                SharedResponse mshre = gson.fromJson(Json, SharedResponse.class);
                if (mshre.error.equals("0")) {
                    ToastUtil.show("修改成功");
                    if (sex == 1) {
                        sexTv.setText("男");
                    } else if (sex == 2) {
                        sexTv.setText("女");
                    }
                }
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(EventBusEventModel event) {
        if (event.getType() == 1) {
            PersonalActivity.this.finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
}