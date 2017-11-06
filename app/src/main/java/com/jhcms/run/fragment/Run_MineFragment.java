package com.jhcms.run.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jhcms.common.listener.PermissionListener;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.ListenerScrollView;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.run.mode.Data_Run_Mine;
import com.jhcms.shequ.activity.PersonalActivity;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.ConsigneeAddressActivity;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.activity.UserMsgActivity;
import com.jhcms.waimaiV3.dialog.CallDialog;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyujie
 * on 2017/9/28.15:24
 * TODO
 */

public class Run_MineFragment extends WaiMai_BaseFragment {
    @Bind(R.id.mine_toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_mine_bg)
    ImageView ivMineBg;
    @Bind(R.id.iv_msg)
    ImageView ivMsg;
    @Bind(R.id.iv_mine_head)
    RoundImageView ivMineHead;
    @Bind(R.id.tv_mine_name)
    TextView tvMineName;
    @Bind(R.id.ll_mine_info)
    LinearLayout llMineInfo;
    @Bind(R.id.ll_mine_msg)
    LinearLayout llMineMsg;
    @Bind(R.id.ll_mine_redbag)
    LinearLayout llMineRedbag;
    @Bind(R.id.ll_mine_location)
    LinearLayout llMineLocation;
    @Bind(R.id.ll_mine_kefu)
    LinearLayout llMineKefu;
    @Bind(R.id.tv_drop_out)
    TextView tvDropOut;
    @Bind(R.id.scrollView)
    ListenerScrollView scrollView;


    // 是否正在放大
    private Boolean mScaling = false;
    // 记录首次按下位置
    private float mFirstPosition = 0;
    private DisplayMetrics metric;
    private boolean isVisible = true;
    private String phone;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_run_mine, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        setToolBar(toolbar);
        initHeadBg();
    }

    private void initHeadBg() {
        // 获取屏幕宽高
        metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 设置图片初始大小 这里我设为满屏的375:180
        ViewGroup.LayoutParams params = ivMineBg.getLayoutParams();
        params.width = metric.widthPixels;
        params.height = metric.widthPixels * 12 / 25;
        ivMineBg.setLayoutParams(params);

        scrollView.setOnTouchListener((v, event) -> {
            ViewGroup.LayoutParams lp = ivMineBg
                    .getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    mScaling = false;
                    replyImage();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!mScaling) {
                        if (scrollView.getScrollY() == 0) {
                            mFirstPosition = event.getY();
                        } else {
                            break;
                        }
                    }
                    int distance = (int) ((event.getY() - mFirstPosition) * 0.6);
                    if (distance < 0) {
                        break;
                    }
                    mScaling = true;
                    lp.width = metric.widthPixels + distance;
                    lp.height = (metric.widthPixels + distance) * 12 / 25;
                    ivMineBg.setLayoutParams(lp);
                    return true;
            }
            return false;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible) {
            if (!TextUtils.isEmpty(Api.TOKEN)) {
                requertData();
            }
        }

    }

    private void requertData() {
        try {
            HttpUtils.postUrlWithBaseResponse(getActivity(), Api.PAOTUI_MEMBER_INDEX
                    , null, true, new OnRequestSuccess<BaseResponse<Data_Run_Mine>>() {
                        @Override
                        public void onSuccess(String url, BaseResponse<Data_Run_Mine> data) {
//                            dealWith(data);
                            Data_Run_Mine runMine = data.getData();
                            /*客服电话*/
                            phone = runMine.getPhone();
                            /*用户名*/
                            tvMineName.setText(runMine.getMember().getNickname());
                            /*头像*/
                            Glide.with(getActivity())
                                    .load(Api.IMAGE_URL + runMine.getMember().getFace())
                                    .into(ivMineHead);
                            /*消息*/
                            if (runMine.getIs_read() > 0) {
                                ivMsg.setSelected(true);
                            } else {
                                ivMsg.setSelected(false);
                            }
                            tvDropOut.setVisibility(View.VISIBLE);

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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
    }

    // 回弹动画 (属性动画)
    public void replyImage() {
        final ViewGroup.LayoutParams lp = ivMineBg
                .getLayoutParams();
        final float w = ivMineBg.getLayoutParams().width;// 图片当前宽度
        final float h = ivMineBg.getLayoutParams().height;// 图片当前高度
        final float newW = metric.widthPixels;// 图片原宽度
        final float newH = metric.widthPixels * 12 / 25;// 图片原高度

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F)
                .setDuration(200);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                lp.width = (int) (w - (w - newW) * cVal);
                lp.height = (int) (h - (h - newH) * cVal);
                ivMineBg.setLayoutParams(lp);
            }
        });
        anim.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_mine_info, R.id.ll_mine_msg, R.id.ll_mine_redbag, R.id.ll_mine_location, R.id.ll_mine_kefu, R.id.tv_drop_out})
    public void onViewClicked(View view) {
        if (TextUtils.isEmpty(Api.TOKEN)) {
            Utils.GoLogin(getActivity());
        } else {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.ll_mine_info:
                    intent.setClass(getActivity(), PersonalActivity.class);
                    break;
                case R.id.ll_mine_msg:
                    intent.setClass(getActivity(), UserMsgActivity.class);
                    break;
                case R.id.ll_mine_redbag:
                    intent.setClass(getActivity(), ConsigneeAddressActivity.class);
                    break;
                case R.id.ll_mine_location:
                    intent.setClass(getActivity(), ConsigneeAddressActivity.class);
                    break;
                case R.id.ll_mine_kefu:
                    if (!TextUtils.isEmpty(phone)) {
                        SwipeBaseActivity.requestRuntimePermission(new String[]{Manifest.permission.CALL_PHONE}, new PermissionListener() {
                            @Override
                            public void onGranted() {
                                new CallDialog(getActivity())
                                        .setMessage(phone)
                                        .setTipTitle("联系客服")
                                        .setPositiveButton("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri
                                                        .parse("tel:" + phone));
                                                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    return;
                                                }
                                                getActivity().startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("取消", null)
                                        .show();
                            }

                            @Override
                            public void onDenied(List<String> permissions) {
                                showMissingPermissionDialog();
                            }
                        });

                    }
                    return;
                case R.id.tv_drop_out:
                    logionOut();
                    return;
            }
            getActivity().startActivity(intent);
        }

    }

    private void logionOut() {
        JSONObject object = new JSONObject();
        try {
            object.put("register_id", Api.REGISTRATION_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrl(getActivity(), Api.LOGIN_OUT, object.toString(), true, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                Api.TOKEN = null;
                tvMineName.setText("未登录");
                tvDropOut.setVisibility(View.GONE);
                ivMineHead.setImageResource(R.mipmap.icon_head);
            }

            @Override
            public void onBeforeAnimate() {

            }

            @Override
            public void onErrorAnimate() {

            }
        });
    }
}
