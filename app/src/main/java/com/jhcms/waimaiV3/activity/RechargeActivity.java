package com.jhcms.waimaiV3.activity;

import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Data_WaiMai_PayOrder;
import com.jhcms.common.model.Response_Recharg;
import com.jhcms.common.model.Response_WaiMai_PayOrder;
import com.jhcms.common.model.grildmodel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.WaiMaiPay;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.RechargeAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RechargeActivity extends PayActivity implements OnRequestSuccessCallback {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.gridview)
    GridViewForScrollView gridview;
    private RechargeAdapter mAdapter;
    private PopupWindow mPayPopuwindows;
    private int paypostiion = -1;
    /**
     * 支付标志
     */
    private String code;

    @Bind(R.id.chongzhi)
    TextView mtvchongzhi;
    private ArrayList<grildmodel> mdatalist = new ArrayList<>();
    private int mPostion = -1;

    @Override
    protected void initData() {
        tvTitle.setText(getResources().getString(R.string.余额充值));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RequestData();
        mAdapter = new RechargeAdapter(RechargeActivity.this, mdatalist);
        gridview.setAdapter(mAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPostion = position;
                mAdapter.setSelectpostion(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        mtvchongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPostion == -1) {
                    ToastUtil.show("请选择面额");
                    return;
                }
                inintPayPopuwindows();


            }
        });
    }

    private void RequestData() {
        try {
            HttpUtils.postUrl(this, Api.WAIMAI_PACKAGE, null, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestchongzhi() {
        try {
            JSONObject js = new JSONObject();
            js.put("amount", mdatalist.get(mPostion).getChong());
            js.put("code", code);
            String str = js.toString();
            HttpUtils.postUrl(this, Api.WAIMAI_PAYMENT_MONEY, str, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void inintPayPopuwindows() {
        View view = LayoutInflater.from(RechargeActivity.this).inflate(R.layout.waimai_recharge_layout, null);
        ImageView mIvClose = (ImageView) view.findViewById(R.id.iv_close);
        LinearLayout toplayout = (LinearLayout) view.findViewById(R.id.topLayout);
        toplayout.getBackground().setAlpha(255 / 2);
        toplayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPayPopuwindows != null && mPayPopuwindows.isShowing()) {
                    mPayPopuwindows.dismiss();
                }
            }
        });
        final LinearLayout alilay = (LinearLayout) view.findViewById(R.id.alipayLayout);
        LinearLayout weixinlayout = (LinearLayout) view.findViewById(R.id.weixinlay);
        final ImageView aliIv = (ImageView) view.findViewById(R.id.alipayIv);
        final ImageView weixiimage = (ImageView) view.findViewById(R.id.weixiiv);
        TextView mTvgopay = (TextView) view.findViewById(R.id.gopay);
        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvPrice.setText("￥" + mdatalist.get(mPostion).getChong() + ".00");
        mPayPopuwindows = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPayPopuwindows.setContentView(view);
        mPayPopuwindows.setOutsideTouchable(true);
        mPayPopuwindows.setFocusable(true);// 获取焦点
        mPayPopuwindows.setClippingEnabled(false);
        // backgroundAlpha(0.5f);
        mPayPopuwindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        mPayPopuwindows.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPayPopuwindows != null && mPayPopuwindows.isShowing()) {
                    mPayPopuwindows.dismiss();
                }
            }
        });
        paypostiion = 1;
        updatapayStatu(paypostiion, aliIv, weixiimage);
        code = "alipay";
        alilay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paypostiion = 1;
                updatapayStatu(paypostiion, aliIv, weixiimage);
                code = "alipay";

            }
        });
        weixinlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paypostiion = 2;
                updatapayStatu(paypostiion, aliIv, weixiimage);

                code = "wxpay";
            }
        });
        mTvgopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestchongzhi();
                if (mPayPopuwindows != null && mPayPopuwindows.isShowing()) {
                    mPayPopuwindows.dismiss();
                }
            }
        });
        if (mPayPopuwindows != null && !mPayPopuwindows.isShowing()) {
            mPayPopuwindows.showAtLocation(findViewById(R.id.all_rlay), Gravity.BOTTOM, 0, com.jhcms.common.utils.Utils.getNavigationBarHeight(RechargeActivity.this));
        }
    }

    private void updatapayStatu(int paypostiion, ImageView aliIv, ImageView weixiimage) {
        if (paypostiion == 1) {
            weixiimage.setImageResource(R.mipmap.index_selector_disable);
            aliIv.setImageResource(R.mipmap.index_selector_enable);
        } else if (paypostiion == 2) {
            aliIv.setImageResource(R.mipmap.index_selector_disable);
            weixiimage.setImageResource(R.mipmap.index_selector_enable);
        } else {
            aliIv.setImageResource(R.mipmap.index_selector_disable);
            weixiimage.setImageResource(R.mipmap.index_selector_disable);
        }
    }


    @Override
    public void onSuccess(String url, String Json) {
        Gson gson = new Gson();
        switch (url) {
            case Api.WAIMAI_PACKAGE:
                try {
                    Response_Recharg mModel = gson.fromJson(Json, Response_Recharg.class);
                    if (mModel.error.equals("0")) {
                        mdatalist.addAll(mModel.getData().getItems());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.show(mModel.message);
                    }
                } catch (Exception e) {
                    ToastUtil.show("网络出现问题了！");
                }

                break;
            case Api.WAIMAI_PAYMENT_MONEY:
                try {
                    Response_WaiMai_PayOrder payOrder = gson.fromJson(Json, Response_WaiMai_PayOrder.class);
                    if (payOrder.error.equals("0")) {
                        Data_WaiMai_PayOrder data = payOrder.data;
                        PayOrder(code, data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.show("网络出现问题");
                }
                break;
        }
    }

    private void PayOrder(String code, Data_WaiMai_PayOrder data) {
        DealWithPayOrder(code, data, new WaiMaiPay.OnPayListener() {
            @Override
            public void onFinish(boolean success) {
                if (success) {
                    ToastUtil.show("支付成功");
                    finish();
                }
            }
        });
    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }
}
