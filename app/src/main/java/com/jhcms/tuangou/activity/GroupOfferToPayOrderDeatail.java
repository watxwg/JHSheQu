package com.jhcms.tuangou.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.jhcms.common.model.Response_GroupOfferToPayOrderDeatail;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.BaseActivity;
import com.jhcms.waimaiV3.R;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroupOfferToPayOrderDeatail extends BaseActivity {
    public static String ORDER_ID = "ORDER_ID";
    @Bind(R.id.ShopName)
    TextView ShopName;
    @Bind(R.id.shop_location)
    ImageView shopLocation;
    @Bind(R.id.shop_location_tv)
    TextView shopLocationTv;
    @Bind(R.id.Shop_call)
    ImageView ShopCall;
    @Bind(R.id.OderId)
    TextView OderId;
    @Bind(R.id.OlderPay)
    TextView OlderPay;
    @Bind(R.id.Newpay)
    TextView Newpay;
    @Bind(R.id.oderCount)
    TextView oderCount;
    @Bind(R.id.onlineTime)
    TextView onlineTime;
    @Bind(R.id.PhoneNumber)
    TextView PhoneNumber;
    @Bind(R.id.layout)
    SpringView layout;
    @Bind(R.id.cancelOrder_tv)
    TextView cancelOrderTv;
    @Bind(R.id.playStatu)
    TextView playStatu;
    @Bind(R.id.ShenQingtuiKuan_tv)
    TextView ShenQingtuiKuanTv;
    @Bind(R.id.ComeToEvaluate_tv)
    TextView ComeToEvaluateTv;
    @Bind(R.id.LookEvaluate_tv)
    TextView LookEvaluateTv;
    @Bind(R.id.Canceled_tv)
    TextView CanceledTv;
    @Bind(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @Bind(R.id.content_view)
    LinearLayout topLayout;
    @Bind(R.id.multiplestatusview)
    MultipleStatusView multiplestatusview;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private String order_id;
    private Response_GroupOfferToPayOrderDeatail Mdatamodel;
    private boolean isFristRefrsh = true;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_group_offer_to_pay_order_deatail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        InintIntent();
        RequestData();
        InintRefrsh();
        InintEvent();
    }

    @Override
    protected void initActionBar() {

    }

    @Override
    protected void initFindViewById() {

    }

    @Override
    protected void initEvent() {

    }

    private void InintEvent() {
        tvTitle.setText("订单详情");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ShopCall.setOnClickListener(v -> Utils.callPhone(GroupOfferToPayOrderDeatail.this, Mdatamodel.getData().getOrder().getPhone()));
        playStatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupOfferToPayOrderDeatail.this, TuanToPayActivity.class);
                intent.putExtra("OrderId", Mdatamodel.getData().getOrder().getOrder_id());
                intent.putExtra("Money", Mdatamodel.getData().getOrder().getTotal_price());
                startActivity(intent);
            }
        });
        LookEvaluateTv.setOnClickListener(new View.OnClickListener() {//查看评价
            @Override
            public void onClick(View v) {//TODO 查看评价
                Intent intent = new Intent(GroupOfferToPayOrderDeatail.this, LookTuanOrderMerchantEvaluationActivity.class);
                intent.putExtra("comment_id", Mdatamodel.getData().getOrder().getComment_id());
                startActivity(intent);
            }
        });
        ComeToEvaluateTv.setOnClickListener(new View.OnClickListener() {//TOdo 去评价
            @Override
            public void onClick(View v) {//TODO 去评价
                Intent intent = new Intent(GroupOfferToPayOrderDeatail.this, TuanOrderEvaluateActivity.class);
                intent.putExtra("order_id", Mdatamodel.getData().getOrder().getOrder_id());
                intent.putExtra("type", "maidan");
                intent.putExtra("shop_logo", Mdatamodel.getData().getOrder().getShop_logo());
                intent.putExtra("shop_title", Mdatamodel.getData().getOrder().getShop_title());
                startActivity(intent);
            }
        });

    }

    private void InintRefrsh() {
        layout.setGive(SpringView.Give.TOP);
        layout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                RequestData();
            }

            @Override
            public void onLoadmore() {

            }
        });
        layout.setHeader(new DefaultHeader(this));
        layout.setType(SpringView.Type.FOLLOW);

    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestData();
    }

    private void InintIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            order_id = intent.getStringExtra(ORDER_ID);
        }
        RequestData();
    }

    private void RequestData() {
        try {
            JSONObject js = new JSONObject();
            js.put("order_id", order_id);
            HttpUtils.postUrl(GroupOfferToPayOrderDeatail.this, Api.TUAN_ORDER_MAIDAN_DETAIL, js.toString(), false, new OnRequestSuccessCallback() {
                @Override
                public void onSuccess(String url, String Json) {
                    layout.onFinishFreshAndLoad();
                    if (isFristRefrsh) {
                        multiplestatusview.showContent();
                        isFristRefrsh = false;
                    }
                    if (!isFristRefrsh) {
                        dismiss(GroupOfferToPayOrderDeatail.this);
                    }
                    Gson gson = new Gson();
                    try {
                        Mdatamodel = gson.fromJson(Json, Response_GroupOfferToPayOrderDeatail.class);
                        BindviewData(Mdatamodel);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.show("网络出现问题");
                    }
                }

                @Override
                public void onBeforeAnimate() {
                    if (isFristRefrsh) {
                        multiplestatusview.showLoading();
                    }
                    if (!isFristRefrsh) {
                        showProgressDialog(GroupOfferToPayOrderDeatail.this);
                    }

                }

                @Override
                public void onErrorAnimate() {
                    layout.onFinishFreshAndLoad();
                    if (isFristRefrsh) {
                        multiplestatusview.showError();
                        isFristRefrsh = false;
                    } else {
                        dismiss(GroupOfferToPayOrderDeatail.this);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show("网络出现问题！");
        }

    }

    private void BindviewData(Response_GroupOfferToPayOrderDeatail mdatamodel) {
        ShopName.setText(mdatamodel.getData().getOrder().getShop_title());
        shopLocationTv.setText(mdatamodel.getData().getOrder().getAddr());
        OderId.setText(mdatamodel.getData().getOrder().getOrder_id());
        OlderPay.setText("￥" + mdatamodel.getData().getOrder().getTotal_price());
        Newpay.setText("￥" + mdatamodel.getData().getOrder().getAmount());
        oderCount.setText("商家优惠" +
                (Float.parseFloat(mdatamodel.getData().getOrder().getTotal_price()) - Float.parseFloat(mdatamodel.getData().getOrder().getAmount())));
        onlineTime.setText(Utils.convertDate(Long.parseLong(mdatamodel.getData().getOrder().getDateline()), null));
        PhoneNumber.setText(mdatamodel.getData().getOrder().getPhone());
        InIntOderStatu(mdatamodel);
    }

    private void InIntOderStatu(Response_GroupOfferToPayOrderDeatail mdatamodel) {
        if (mdatamodel.getData().getOrder().getOrder_status().equals("-1")) {//取消订单
            bottomLayout.setVisibility(View.GONE);
        } else if (mdatamodel.getData().getOrder().getOrder_status().equals("0")) {//未付款
            bottomLayout.setVisibility(View.VISIBLE);
            playStatu.setVisibility(View.VISIBLE);
            cancelOrderTv.setVisibility(View.GONE);
            ShenQingtuiKuanTv.setVisibility(View.VISIBLE);
            ComeToEvaluateTv.setVisibility(View.GONE);
            LookEvaluateTv.setVisibility(View.GONE);
            CanceledTv.setVisibility(View.GONE);

        } else if (mdatamodel.getData().getOrder().getOrder_status().equals("8") && mdatamodel.getData().getOrder().getComment_status().equals("0")) {//去评价
            bottomLayout.setVisibility(View.VISIBLE);
            playStatu.setVisibility(View.GONE);
            cancelOrderTv.setVisibility(View.GONE);
            ShenQingtuiKuanTv.setVisibility(View.GONE);
            ComeToEvaluateTv.setVisibility(View.VISIBLE);
            LookEvaluateTv.setVisibility(View.GONE);
            CanceledTv.setVisibility(View.GONE);
        } else if (mdatamodel.getData().getOrder().getOrder_status().equals("8") && mdatamodel.getData().getOrder().getComment_status().equals("1")) {
            bottomLayout.setVisibility(View.VISIBLE);
            playStatu.setVisibility(View.GONE);
            cancelOrderTv.setVisibility(View.GONE);
            ShenQingtuiKuanTv.setVisibility(View.GONE);
            ComeToEvaluateTv.setVisibility(View.GONE);
            LookEvaluateTv.setVisibility(View.VISIBLE);
            CanceledTv.setVisibility(View.GONE);
        }
    }

}
