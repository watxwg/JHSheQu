package com.jhcms.tuangou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Data_WaiMai_PayOrder;
import com.jhcms.common.model.RefreshEvent;
import com.jhcms.common.model.Response_WaiMai_PayOrder;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.WaiMaiPay;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.PayActivity;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TuanToPayActivity extends PayActivity implements OnRequestSuccessCallback {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.orderId)
    TextView orderIdTv;
    @Bind(R.id.PayMoney)
    TextView PayMoney;
    @Bind(R.id.aliImage)
    ImageView aliImage;
    @Bind(R.id.weixinImage)
    ImageView weixinImage;
    @Bind(R.id.yueImage)
    ImageView yueImage;
    @Bind(R.id.submit_bt)
    TextView submitBt;
    String OrderId;
    String Money;
    String code;
    @Bind(R.id.alipay_layout)
    LinearLayout alipayLayout;
    @Bind(R.id.weixinLayout)
    LinearLayout weixinLayout;
    @Bind(R.id.yuelayout)
    LinearLayout yuelayout;
    private String FromWhere;//Group_BuyOrdersFragment


    @Override
    protected void initData() {
        InintIntent();
        Inintevent();
        UpdataStatu(0);
        code = "alipay";
    }

    private void InintIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            OrderId = intent.getStringExtra("OrderId");
            Money = intent.getStringExtra("Money");
            FromWhere = intent.getStringExtra("FromWhere");
        }
        orderIdTv.setText(OrderId);
        PayMoney.setText(Money);
    }

    private void Inintevent() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTv.setText("支付页面");
        alipayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdataStatu(0);
                code = "alipay";
            }
        });
        weixinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdataStatu(1);
                code = "wxpay";
            }
        });
        yuelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdataStatu(2);
                code = "money";
            }
        });
        submitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payOrder(Api.WAIMAI_SHOP_PAY_ORDER);
            }
        });
    }

    private void payOrder(String waimaiShopPayOrder) {
        JSONObject object = new JSONObject();
        try {
            /*订单ID*/
            object.put("order_id", OrderId);
            object.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, waimaiShopPayOrder, str, false, this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_to_pay);
        ButterKnife.bind(this);
    }

    public void UpdataStatu(int postion) {
        if (postion == 0) {
            aliImage.setImageResource(R.mipmap.index_selector_enable);
            weixinImage.setImageResource(R.mipmap.index_selector_disable);
            yueImage.setImageResource(R.mipmap.index_selector_disable);
        } else if (postion == 1) {
            aliImage.setImageResource(R.mipmap.index_selector_disable);
            weixinImage.setImageResource(R.mipmap.index_selector_enable);
            yueImage.setImageResource(R.mipmap.index_selector_disable);
        } else if (postion == 2) {
            aliImage.setImageResource(R.mipmap.index_selector_disable);
            weixinImage.setImageResource(R.mipmap.index_selector_disable);
            yueImage.setImageResource(R.mipmap.index_selector_enable);
        }

    }

    /**
     * @param code wxpay alipay
     * @param data 支付需要的参数
     */
    private void PayOrder(String code, Data_WaiMai_PayOrder data) {
        DealWithPayOrder(code, data, new WaiMaiPay.OnPayListener() {
            @Override
            public void onFinish(boolean success) {
                if (success) {
                    if (FromWhere != null&&FromWhere.length() > 0 && FromWhere.equals("Group_BuyOrdersFragment")) {
                        finish();
                        Intent intent = new Intent(TuanToPayActivity.this, TuanOrderDetailsActivity.class);
                        intent.putExtra("ORDER_ID", OrderId);
                        startActivity(intent);
                    }else if(FromWhere != null && FromWhere.length() > 0&&FromWhere.equals("Group_OfferToPayFragment")){
                        finish();
                        Intent intent = new Intent(TuanToPayActivity.this, GroupOfferToPayOrderDeatail.class);
                        intent.putExtra("ORDER_ID", OrderId);
                        startActivity(intent);
                    }else {
                        finish();
                    }

                }
            }
        });
    }

     /*微信支付成功*/

    @Subscribe
    public void onMessageEvent(RefreshEvent event) {
        if (event.getmMsg().equals("weixin_pay_success")) {
            if (FromWhere != null && FromWhere.length() > 0&&FromWhere.equals("Group_BuyOrdersFragment")) {
                finish();
                Intent intent = new Intent(TuanToPayActivity.this, TuanOrderDetailsActivity.class);
                intent.putExtra("ORDER_ID", OrderId);
                startActivity(intent);

            }else if(FromWhere != null && FromWhere.length() > 0&&FromWhere.equals("Group_OfferToPayFragment")){
                finish();
                Intent intent = new Intent(TuanToPayActivity.this, GroupOfferToPayOrderDeatail.class);
                intent.putExtra("ORDER_ID", OrderId);
                startActivity(intent);
            }else {
                finish();
            }
        }
    }



    @Override
    public void onSuccess(String url, String Json) {
        ProgressDialogUtil.dismiss(TuanToPayActivity.this);
        Gson gson = new Gson();
        try {
            Response_WaiMai_PayOrder payOrder = gson.fromJson(Json, Response_WaiMai_PayOrder.class);
            if (payOrder.error.equals("0")) {
                if (code.equals("money")) {
                    ToastUtil.show(payOrder.message);
                    if (FromWhere != null && FromWhere.length() > 0&&FromWhere.equals("Group_BuyOrdersFragment")) {
                        finish();
                        Intent intent = new Intent(TuanToPayActivity.this, TuanOrderDetailsActivity.class);
                        intent.putExtra("ORDER_ID", OrderId);
                        startActivity(intent);
                        finish();
                    }else if(FromWhere != null && FromWhere.length() > 0&&FromWhere.equals("Group_OfferToPayFragment")){
                        finish();
                        Intent intent = new Intent(TuanToPayActivity.this, GroupOfferToPayOrderDeatail.class);
                        intent.putExtra("ORDER_ID", OrderId);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Data_WaiMai_PayOrder data = payOrder.data;
                            /*处理微信、支付宝支付*/
                    PayOrder(code, data);
                }
            } else {
                ToastUtil.show(payOrder.message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show("网络出现问题");
        }
    }

    @Override
    public void onBeforeAnimate() {
        ProgressDialogUtil.showProgressDialog(TuanToPayActivity.this);
    }

    @Override
    public void onErrorAnimate() {
        ProgressDialogUtil.dismiss(TuanToPayActivity.this);
    }
}
