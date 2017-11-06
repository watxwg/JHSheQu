package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Data_WaiMai_PayOrder;
import com.jhcms.common.model.RefreshEvent;
import com.jhcms.common.model.Response_Orederdetail;
import com.jhcms.common.model.Response_WaiMai_PayOrder;
import com.jhcms.common.model.ShopOrderModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.WaiMaiPay;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ToPayActivity extends PayActivity implements OnRequestSuccessCallback {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.OrderId)
    TextView mTvOrderId;
    @Bind(R.id.PayMoney)
    TextView mTvPayMoney;
    @Bind(R.id.layzhifubao)
    LinearLayout mLayalipay;
    @Bind(R.id.mIvAlipay)
    ImageView mIvalipay;
    @Bind(R.id.wechat_ll)
    LinearLayout mLaywechat;
    @Bind(R.id.mIvwechat)
    ImageView mIvwechat;
    @Bind(R.id.layyue)
    LinearLayout mlaybalance;
    @Bind(R.id.mIvyue)
    ImageView mIvbalance;
    @Bind(R.id.submit_bt)
    TextView mTvSubmit;
    private ShopOrderModel mDataModel;
    private int Postion = 0;
    private Response_Orederdetail mData;
    private String FromWhere;
    private String orderid;
    /**
     * 支付标志
     */
    private String code = "alipay";

    @Override
    protected void initData() {
        tvTitle.setText("支付页面");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        inintent();
        BindViewData();
        inintEvent();
        updataStatu(0);
        EventBus.getDefault().register(this);
    }

    private void inintEvent() {
        mLayalipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Postion = 0;
                updataStatu(Postion);
            }
        });

        mLaywechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Postion = 1;
                updataStatu(Postion);
            }
        });
        mlaybalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Postion = 2;
                updataStatu(Postion);
            }
        });
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Postion) {
                    case 0:
                          /*TODO 支付宝支付*/
                        code = "alipay";
                        payOrder(Api.WAIMAI_SHOP_PAY_ORDER);
                        break;
                    case 1:
                         /*TODO 微信支付*/
                        code = "wxpay";
                        payOrder(Api.WAIMAI_SHOP_PAY_ORDER);
                        break;
                    case 2:
                         /*TODO 余额支付*/
                        code = "money";
                        payOrder(Api.WAIMAI_SHOP_PAY_ORDER);
                        break;
                }
            }
        });
    }

    private void payOrder(String waimaiShopPayOrder) {
        JSONObject object = new JSONObject();
        try {
            /*订单ID*/
            object.put("order_id", orderid);
            object.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, waimaiShopPayOrder, str, true, this);
    }

    public void updataStatu(int postion) {
        switch (postion) {
            case 0:
                mIvalipay.setImageResource(R.mipmap.index_selector_enable);
                mIvwechat.setImageResource(R.mipmap.index_selector_disable);
                mIvbalance.setImageResource(R.mipmap.index_selector_disable);
                break;
            case 1:
                mIvalipay.setImageResource(R.mipmap.index_selector_disable);
                mIvwechat.setImageResource(R.mipmap.index_selector_enable);
                mIvbalance.setImageResource(R.mipmap.index_selector_disable);
                break;
            case 2:
                mIvalipay.setImageResource(R.mipmap.index_selector_disable);
                mIvwechat.setImageResource(R.mipmap.index_selector_disable);
                mIvbalance.setImageResource(R.mipmap.index_selector_enable);
                break;
        }
    }


    private void inintent() {
        Intent intent = getIntent();
        if (intent != null) {
            mDataModel = (ShopOrderModel) intent.getSerializableExtra("Topay");
            mData = (Response_Orederdetail) intent.getSerializableExtra("topaymodelfromOrderDetailsActivity");
            FromWhere = intent.getStringExtra("FromWhere");
            orderid = intent.getStringExtra("order_id");
        }

    }

    private void BindViewData() {
        if (mDataModel != null) {
            mTvOrderId.setText(mDataModel.getOrder_id());
            mTvPayMoney.setText("￥" + mDataModel.getAmount());
            mTvOrderId.setTag(mDataModel.getOrder_id());
        } else if (mData != null) {
            mTvOrderId.setText(mData.getData().getDetail().getOrder_id());
            mTvPayMoney.setText("￥" + mData.getData().getDetail().getAmount());
            mTvOrderId.setTag(mData.getData().getDetail().getOrder_id());
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_to_pay);
        ButterKnife.bind(this);


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
                    if (FromWhere != null && FromWhere.length() > 0) {
                        Intent intent = new Intent();
                        if (MyApplication.MAP.equals(Api.GAODE)) {
                            intent.setClass(ToPayActivity.this, OrderDetailsActivity.class);
                            intent.putExtra(OrderDetailsActivity.ORDER_ID, orderid);
                        } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                            intent.setClass(ToPayActivity.this, OrderDetailsGMSActivity.class);
                            intent.putExtra(OrderDetailsGMSActivity.ORDER_ID, orderid);
                        }
                        startActivity(intent);
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
            if (FromWhere != null && FromWhere.length() > 0) {
                Intent intent = new Intent();
                if (MyApplication.MAP.equals(Api.GAODE)) {
                    intent.setClass(ToPayActivity.this, OrderDetailsActivity.class);
                } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                    intent.setClass(ToPayActivity.this, OrderDetailsGMSActivity.class);
                }
                intent.putExtra("order_id", orderid);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onSuccess(String url, String Json) {
        switch (url) {
            case Api.WAIMAI_SHOP_PAY_ORDER:
                try {
                    Gson gson = new Gson();
                    Response_WaiMai_PayOrder payOrder = gson.fromJson(Json, Response_WaiMai_PayOrder.class);
                    if (payOrder.error.equals("0")) {
                        if (code.equals("money")) {
                            ToastUtil.show(payOrder.message);
                            Intent intent = new Intent();
                            if (MyApplication.MAP.equals(Api.GAODE)) {
                                intent.setClass(ToPayActivity.this, OrderDetailsActivity.class);
                            } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                                intent.setClass(ToPayActivity.this, OrderDetailsGMSActivity.class);
                            }
                            intent.putExtra("order_id", orderid);
                            startActivity(intent);
                            finish();
                        } else {
                            Data_WaiMai_PayOrder data = payOrder.data;
                            /*处理微信、支付宝支付*/
                            PayOrder(code, data);
                        }
                    } else {
                        ToastUtil.show(payOrder.message);
                    }
                } catch (Exception e) {
                    ToastUtil.show("解析错误！");
                }

                break;

        }
    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
