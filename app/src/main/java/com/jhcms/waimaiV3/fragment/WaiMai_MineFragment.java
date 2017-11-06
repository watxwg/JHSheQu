package com.jhcms.waimaiV3.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
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
import com.google.gson.Gson;
import com.jhcms.common.model.Response_Mine;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.ListenerScrollView;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.shequ.activity.PersonalActivity;
import com.jhcms.shequ.model.HawkApi;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.ConsigneeAddressActivity;
import com.jhcms.waimaiV3.activity.WaimaiBalanceActivity;
import com.jhcms.waimaiV3.activity.WebViewActivity;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Wyj on 2017/3/22.
 */
public class WaiMai_MineFragment extends WaiMai_BaseFragment implements OnRequestSuccessCallback {

    @Bind(R.id.mine_toolbar)
    Toolbar mineToolbar;
    @Bind(R.id.iv_mine_head)
    RoundImageView ivMineHead;
    @Bind(R.id.tv_mine_name)
    TextView tvMineName;
    @Bind(R.id.ll_mine_location)
    LinearLayout llMineLocation;
    @Bind(R.id.ll_mine_collect)
    LinearLayout llMineCollect;
    @Bind(R.id.ll_mine_coupons)
    LinearLayout llMineCoupons;
    @Bind(R.id.iv_mine_bg)
    ImageView ivMineBg;
    @Bind(R.id.scrollView)
    ListenerScrollView scrollView;
    @Bind(R.id.tv_drop_out)
    TextView tvDropOut;
    // 是否正在放大
    private Boolean mScaling = false;
    // 记录首次按下位置
    private float mFirstPosition = 0;
    private DisplayMetrics metric;
    Response_Mine mDatamodel;
    private boolean isVisible = true;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_waimai_mine, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        setToolBar(mineToolbar);
        // 获取屏幕宽高
        metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 设置图片初始大小 这里我设为满屏的375:130
        ViewGroup.LayoutParams params = ivMineBg.getLayoutParams();
        params.width = metric.widthPixels;
        params.height = metric.widthPixels * 130 / 375;
        ivMineBg.setLayoutParams(params);
        /*展示数据*/
        mineToolbar.setNavigationOnClickListener(v -> getActivity().finish());

        scrollView.setOnTouchListener((v, event) -> {
            ViewGroup.LayoutParams lp = ivMineBg
                    .getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    // 手指离开后恢复图片
                    mScaling = false;
                    replyImage();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!mScaling) {
                        if (scrollView.getScrollY() == 0) {
                            mFirstPosition = event.getY();// 滚动到顶部时记录位置，否则正常返回
                        } else {
                            break;
                        }
                    }
                    int distance = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
                    if (distance < 0) { // 当前位置比记录位置要小，正常返回
                        break;
                    }
                    // 处理放大
                    mScaling = true;
                    lp.width = metric.widthPixels + distance;
                    lp.height = (metric.widthPixels + distance) * 130 / 375;
                    ivMineBg.setLayoutParams(lp);
                    return true; // 返回true表示已经完成触摸事件，不再处理
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
            } else {
                tvDropOut.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
    }

    private void requertData() {
        try {
            HttpUtils.postUrl(getActivity(), Api.SHEQU_USERINFO, null, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 回弹动画 (使用了属性动画)
    public void replyImage() {
        final ViewGroup.LayoutParams lp = ivMineBg
                .getLayoutParams();
        final float w = ivMineBg.getLayoutParams().width;// 图片当前宽度
        final float h = ivMineBg.getLayoutParams().height;// 图片当前高度
        final float newW = metric.widthPixels;// 图片原宽度
        final float newH = metric.widthPixels * 130 / 375;// 图片原高度

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

    @OnClick({R.id.ll_mine_info, R.id.ll_mine_yue, R.id.ll_mine_location, R.id.ll_mine_collect, R.id.ll_mine_coupons, R.id.ll_mine_redbag, R.id.ll_mine_integral, R.id.tv_drop_out, R.id.ll_invite_friends})
    public void onClick(View view) {
        if (TextUtils.isEmpty(Api.TOKEN)) {
            Utils.GoLogin(getActivity());
        } else {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.ll_mine_info://TODO 个人信息
                    intent.setClass(getActivity(), PersonalActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll_mine_location://TODO 收货地址
                    intent.setClass(getActivity(), ConsigneeAddressActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll_mine_collect://TODO 我的收藏
                    intent.setClass(getContext(), WebViewActivity.class);
                    intent.putExtra("WEB_URL", mDatamodel.getData().getCollect_url());
                    startActivity(intent);
                    break;
                case R.id.ll_mine_coupons://TODO 优惠券
                    intent.setClass(getContext(), WebViewActivity.class);
                    intent.putExtra("WEB_URL", mDatamodel.getData().getCoupon_url());
                    startActivity(intent);
                    break;
                case R.id.ll_mine_redbag://TODO 我的红包
                    intent.setClass(getContext(), WebViewActivity.class);
                    intent.putExtra("WEB_URL", mDatamodel.getData().getHongbao_url());
                    startActivity(intent);
                    break;
                case R.id.ll_mine_integral://TODO 我的积分
                    intent.setClass(getContext(), WebViewActivity.class);
                    intent.putExtra("WEB_URL", mDatamodel.getData().getJifen_url());
                    startActivity(intent);
                    break;
                case R.id.tv_drop_out://TODO 退出登录
                    logionOut();
                    break;
                case R.id.ll_mine_yue://TODO 余额
                    Intent intent5 = new Intent(getContext(), WaimaiBalanceActivity.class);
                    startActivity(intent5);
                    break;
                case R.id.ll_invite_friends://TODO 邀请好友
                    intent.setClass(getContext(), WebViewActivity.class);
                    intent.putExtra("WEB_URL", mDatamodel.getData().getShare_url());
                    startActivity(intent);
                    break;
            }
        }
    }

    private void logionOut() {
        JSONObject object = new JSONObject();
        try {
            object.put("register_id", Api.REGISTRATION_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrl(getActivity(), Api.LOGIN_OUT, object.toString(), true, this);
    }


    public void updaLoginStatu() {
        tvMineName.setText("未登录");
        tvDropOut.setVisibility(View.GONE);
        ivMineHead.setImageResource(R.mipmap.icon_head);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void BindViewData(Response_Mine mDatamodel) {
        Glide.with(getContext()).load(Api.IMAGE_URL + mDatamodel.getData().getFace()).into(ivMineHead);
        tvMineName.setText(mDatamodel.getData().getNickname());
    }

    @Override
    public void onSuccess(String url, String Json) {
        Gson gson = new Gson();
        try {
            switch (url) {
                case Api.SHEQU_USERINFO:
                    mDatamodel = gson.fromJson(Json, Response_Mine.class);
                    if (mDatamodel.error.equals("0")) {
                        BindViewData(mDatamodel);
                        tvDropOut.setVisibility(View.VISIBLE);
                    } else {
                        if (mDatamodel.error.equals("101")) {
                            Api.TOKEN = null;
                            Utils.GoLogin(getActivity());
                        }
                        ToastUtil.show(mDatamodel.message);
                    }
                    break;
                case Api.LOGIN_OUT:
                    if (Hawk.contains(HawkApi.SHEQU_USERINFO_TOKET)) {
                        Hawk.remove(HawkApi.SHEQU_USERINFO_TOKET);
                    }
                    Api.TOKEN = null;
                    updaLoginStatu();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show("解析错误！");
        }
    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }
}
