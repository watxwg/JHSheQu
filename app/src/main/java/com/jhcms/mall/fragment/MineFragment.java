package com.jhcms.mall.fragment;

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
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.common.widget.ListenerScrollView;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.mall.activity.MallMyOrderActivity;
import com.jhcms.mall.activity.MallShippingAddressActivty;
import com.jhcms.mall.adapter.MineGrildAdpter;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.MallMemberInfoMode;
import com.jhcms.mall.model.OrderStatus;
import com.jhcms.shequ.activity.PersonalActivity;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.UserMsgActivity;
import com.jhcms.waimaiV3.activity.WebViewActivity;
import com.jhcms.waimaiV3.fragment.CustomerBaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/4/11.
 */
public class MineFragment extends CustomerBaseFragment {
    @Bind(R.id.mine_toolbar)
    Toolbar mineToolbar;
    @Bind(R.id.iv_mine_head)
    RoundImageView ivMineHead;
    @Bind(R.id.tv_mine_name)
    TextView tvMineName;
    @Bind(R.id.my_gd)
    GridViewForScrollView mMygridview;
    @Bind(R.id.ll_mine_address)
    LinearLayout llMineAddress;
    @Bind(R.id.ll_mine_goods)
    LinearLayout llMineGoods;
    @Bind(R.id.ll_mine_shop)
    LinearLayout llMineShop;
    @Bind(R.id.ll_mine_discount)
    LinearLayout llMineDiscount;
    @Bind(R.id.scrollView)
    ListenerScrollView scrollView;
    @Bind(R.id.iv_mine_bg)
    ImageView ivMineBg;
    @Bind(R.id.iv_msg)
    ImageView ivMsg;
    @Bind(R.id.tv_drop_out)
    TextView tvDropOut;

    private int[] title = {R.string.待付款, R.string.待发货, R.string.待收货, R.string.待评价, R.string.退款售后,};
    private String[] pics = {"mall_my_btn_order_status_once", "mall_my_btn_order_status_two", "mall_my_btn_order_status_three", "mall_my_btn_order_status_four", "mall_my_btn_order_status_five"};
    private ArrayList<OrderStatus> statusList;

    private boolean isPrepared = false;
    private DisplayMetrics metric;
    private Boolean mScaling = false;
    // 记录首次按下位置
    private float mFirstPosition = 0;
    private MineGrildAdpter mAdapter;
    private MallMemberInfoMode.DetailBean detailBean;
    private String goodsUrl;
    private String shopsUrl;
    private String couponUrl;
    private MallMemberInfoMode.DetailBean.OrderCountBean orderCount;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mall_mine, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void initData() {
        super.initData();
        setToolBar(mineToolbar);
        inintMyGridview();
        inintsrcollview();
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        lazyLoad();
    }

    private void inintMyGridview() {
        mAdapter = new MineGrildAdpter(getActivity());
        statusList = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            OrderStatus orderStatus = new OrderStatus(title[i], pics[i], 0);
            statusList.add(orderStatus);
        }
        mMygridview.setAdapter(mAdapter);
        mAdapter.setData(statusList);
        mAdapter.setOnItemClickListener(position -> {
            if (!TextUtils.isEmpty(Api.TOKEN)) {
                Intent intent = new Intent(getActivity(), MallMyOrderActivity.class);
                intent.putExtra(MallMyOrderActivity.TYPE, position);
                startActivity(intent);
            } else {
                Utils.GoLogin(getActivity());
            }
        });
    }

    private void inintsrcollview() {
        // 获取屏幕宽高
        metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 设置图片初始大小 这里我设为满屏的375:130
        ViewGroup.LayoutParams lp = ivMineBg.getLayoutParams();
        lp.width = metric.widthPixels;
        lp.height = metric.widthPixels * 130 / 375;
        ivMineBg.setLayoutParams(lp);
        /*展示数据*/
        scrollView.setOnTouchListener((v, event) -> {
            ViewGroup.LayoutParams params = ivMineBg
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
                    params.width = metric.widthPixels + distance;
                    params.height = (metric.widthPixels + distance) * 130 / 375;
                    ivMineBg.setLayoutParams(params);
                    return true; // 返回true表示已经完成触摸事件，不再处理
            }
            return false;
        });

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

        anim.addUpdateListener(animation -> {
            float cVal = (Float) animation.getAnimatedValue();
            lp.width = (int) (w - (w - newW) * cVal);
            lp.height = (int) (h - (h - newH) * cVal);
            ivMineBg.setLayoutParams(lp);
        });
        anim.start();
    }

    @Override
    protected void lazyLoad() {
        if (isVisible && isPrepared) {
            if (!TextUtils.isEmpty(Api.TOKEN)) {
                requestInfo();
            }
        }
    }

    private void requestInfo() {
        HttpUtils.postUrlWithBaseResponse(getActivity(), Api.MALL_MEMBER_INFO
                , null, true, new OnRequestSuccess<BaseResponse<MallMemberInfoMode>>() {
                    @Override
                    public void onSuccess(String url, BaseResponse<MallMemberInfoMode> data) {
                        dealWith(data);
                    }

                    @Override
                    public void onBeforeAnimate() {

                    }

                    @Override
                    public void onErrorAnimate() {

                    }
                });
    }

    private void dealWith(BaseResponse<MallMemberInfoMode> data) {
        detailBean = data.data.detail;
        Glide.with(getActivity())
                .load(Api.IMAGE_URL + detailBean.face)
                .into(ivMineHead);
        tvMineName.setText(detailBean.nickname);
        if (detailBean.msg_new_count > 0) {
            ivMsg.setImageResource(R.mipmap.icon_has_msg);
        } else {
            ivMsg.setImageResource(R.mipmap.icon_msg);
        }
        goodsUrl = detailBean.collect_goods_url;
        shopsUrl = detailBean.collect_shops_url;
        couponUrl = detailBean.coupon_url;
        orderCount = detailBean.order_count;
        statusList.clear();
        statusList.add(new OrderStatus(title[0], pics[0], detailBean.order_count.need_pay));
        statusList.add(new OrderStatus(title[1], pics[1], detailBean.order_count.need_fahuo));
        statusList.add(new OrderStatus(title[2], pics[2], detailBean.order_count.need_shouhuo));
        statusList.add(new OrderStatus(title[3], pics[3], detailBean.order_count.need_comment));
        statusList.add(new OrderStatus(title[4], pics[4], detailBean.order_count.refund));
        mAdapter.notifyDataSetChanged();
        tvDropOut.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_msg, R.id.ll_mine_info, R.id.myorder_lay, R.id.ll_mine_address, R.id.ll_mine_goods, R.id.ll_mine_shop, R.id.ll_mine_discount, R.id.tv_drop_out})
    public void onViewClicked(View view) {
        if (!Utils.isFastDoubleClick()) {
            if (!TextUtils.isEmpty(Api.TOKEN)) {
                Intent intent = new Intent();
                switch (view.getId()) {
                    case R.id.iv_msg:
                        intent.setClass(getActivity(), UserMsgActivity.class);
                        break;
                    case R.id.ll_mine_info:
                        intent.setClass(getActivity(), PersonalActivity.class);
                        break;
                    case R.id.myorder_lay:
                        intent.setClass(getActivity(), MallMyOrderActivity.class);
                        break;
                    case R.id.ll_mine_address:
                        intent.setClass(getActivity(), MallShippingAddressActivty.class);
                        break;
                    case R.id.ll_mine_goods:
                        if (!TextUtils.isEmpty(goodsUrl)) {
                            intent.setClass(getActivity(), WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, goodsUrl);
                        }
                        break;
                    case R.id.ll_mine_shop:
                        if (!TextUtils.isEmpty(shopsUrl)) {
                            intent.setClass(getActivity(), WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, shopsUrl);
                        }
                        break;
                    case R.id.ll_mine_discount:
                        if (!TextUtils.isEmpty(couponUrl)) {
                            intent.setClass(getActivity(), WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, couponUrl);
                        }
                        break;
                    case R.id.tv_drop_out:
                        logionOut();
                        return;
                }
                getActivity().startActivity(intent);
            } else {
                Utils.GoLogin(getActivity());
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
        HttpUtils.postUrl(getActivity(), Api.LOGIN_OUT, object.toString(), true, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                Api.TOKEN = "";
                tvMineName.setText("未登录");
                tvDropOut.setVisibility(View.GONE);
                ivMineHead.setImageResource(R.mipmap.icon_head);
                statusList.clear();
                for (int i = 0; i < title.length; i++) {
                    OrderStatus orderStatus = new OrderStatus(title[i], pics[i], 0);
                    statusList.add(orderStatus);
                }
                mAdapter.notifyDataSetChanged();
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
