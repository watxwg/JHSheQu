package com.jhcms.tuangou.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.jhcms.common.model.Data_Group_Order_Details;
import com.jhcms.common.model.Response;
import com.jhcms.common.model.Response_Group_Order_Details;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.BaseActivity;
import com.jhcms.waimaiV3.activity.ToPayNewActivity;
import com.jhcms.waimaiV3.dialog.CallDialog;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.waimaiV3.R.id.ll_voucher;
import static com.jhcms.waimaiV3.R.id.tv_valid_period;

public class TuanOrderDetailsActivity extends BaseActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_detail_logo)
    ImageView ivDetailLogo;
    @Bind(R.id.tv_detail_name)
    TextView tvDetailName;
    @Bind(R.id.tv_detail_num)
    TextView tvDetailNum;
    @Bind(R.id.tv_detail_prices)
    TextView tvDetailPrices;
    @Bind(R.id.tv_detail_old_prices)
    TextView tvDetailOldPrices;
    @Bind(R.id.ll_details_product)
    LinearLayout llDetailsProduct;
    @Bind(tv_valid_period)
    TextView tvValidPeriod;
    @Bind(R.id.iv_voucher)
    ImageView ivVoucher;
    @Bind(R.id.tv_voucher_code)
    TextView tvVoucherCode;
    @Bind(R.id.tv_voucher_status)
    TextView tvVoucherStatus;
    @Bind(ll_voucher)
    LinearLayout llVoucher;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.tv_shop_locatioin)
    TextView tvShopLocatioin;
    @Bind(R.id.iv_shop_call)
    ImageView ivShopCall;
    @Bind(R.id.tv_order_id)
    TextView tvOrderId;
    @Bind(R.id.tv_user_phone)
    TextView tvUserPhone;
    @Bind(R.id.tv_order_time)
    TextView tvOrderTime;
    @Bind(R.id.ll_order_time)
    LinearLayout llOrderTime;
    @Bind(R.id.tv_order_num)
    TextView tvOrderNum;
    @Bind(R.id.tv_order_price)
    TextView tvOrderPrice;
    @Bind(R.id.layout)
    SpringView mrefrshLayour;
    @Bind(R.id.tv_cancelorder)
    TextView tvCancelorder;
    @Bind(R.id.tv_play)
    TextView tvPlay;
    @Bind(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    private Data_Group_Order_Details.OrderBean order;
    private String orderId;
    public static String ORDER_ID = "ORDER_ID";

    /*按钮状态*/
    private String btType;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_order_details);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        orderId = getIntent().getStringExtra(ORDER_ID);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        tvTitle.setText(getResources().getString(R.string.订单详情));

        inintrefrshlayout();
        requestDetails(orderId, false);
    }

    @Override
    public void onBackPressed() {
        close();
    }

    private void close() {
        Intent intent = new Intent(this, TuanGouMainActivity.class);
        intent.putExtra("type", "ORDER");
        startActivity(intent);
        finish();
    }

    /**
     * @param order_id
     * @param show     是否显示默认动画
     */
    private void requestDetails(final String order_id, final boolean show) {
        final JSONObject object = new JSONObject();
        try {
            object.put("order_id", order_id);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        HttpUtils.postUrl(this, Api.WAIMAI_TUAN_ORDER_DETAIL, object.toString(), show, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {


                Gson gson = new Gson();
                Response_Group_Order_Details details = gson.fromJson(Json, Response_Group_Order_Details.class);
                if (details.error.equals("0")) {
                    order = details.data.order;
                    /*团购商品名称*/
                    tvDetailName.setText(order.tuan_title);
                     /*团购商品图片*/
                    Utils.LoadStrPicture(TuanOrderDetailsActivity.this, Api.IMAGE_URL + order.tuan_photo, ivDetailLogo);
                    /*购买份数*/
                    tvDetailNum.setText(order.tuan_number + "份");
                    /*售价*/
                    tvDetailPrices.setText("￥" + order.tuan_price);
                    /*市场价*/
                    tvDetailOldPrices.setText("￥" + order.market_price);
                    /*商家名称*/
                    tvShopName.setText(order.shop_title);
                    /*商家位置*/
                    tvShopLocatioin.setText(order.addr);
                    /*订单编号*/
                    tvOrderId.setText(order.order_id);
                    /*购买者手机号*/
                    tvUserPhone.setText(order.mobile);
                    /*付款时间*/
                    tvOrderTime.setText(Utils.convertDate(order.pay_time, null));
                    /*购买数量*/
                    tvOrderNum.setText(order.tuan_number);
                    /*总价*/
                    tvOrderPrice.setText("￥" + order.total_price);
                    /*有效期*/
                    tvValidPeriod.setText("有效期至" + Utils.convertDate(order.ltime, null));
                    /*购券码*/
                    tvVoucherCode.setText("购券码" + order.ticket_number);
                    /*团购券状态*/

//                    ticket_status 团购券0 未使用 1 已核销 -1 已退款
                    if (!TextUtils.isEmpty(order.ticket_status)) {
                        if (order.ticket_status.equals("0")) {
                            tvVoucherStatus.setText("待使用");
                        } else if (order.ticket_status.equals("1")) {
                            tvVoucherStatus.setText("已使用");
                        } else if (order.ticket_status.equals("-1")) {
                            tvVoucherStatus.setText("已退款");
                        }
                    }

                    if (!TextUtils.isEmpty(order.pay_status)) {
                        if (order.pay_status.equals("1")) {
                            llVoucher.setVisibility(View.VISIBLE);
                            llOrderTime.setVisibility(View.VISIBLE);
                        } else {
                            llVoucher.setVisibility(View.GONE);
                            llOrderTime.setVisibility(View.GONE);
                        }
                    }

//                  order_status  -1 已取消 0未支付   5 已支付代销费  8 已完成

//                    comment_status 0 未评价  1 已评价
                    if (!TextUtils.isEmpty(order.order_status)) {
                        bottomLayout.setVisibility(View.VISIBLE);
                        tvPlay.setVisibility(View.GONE);
                        if (order.order_status.equals("-1")) {
                            bottomLayout.setVisibility(View.GONE);
                        } else if (order.order_status.equals("0")) {
                            tvPlay.setVisibility(View.VISIBLE);
                            tvCancelorder.setText("取消订单");
                            tvCancelorder.setTag("CANCEL");
                            tvPlay.setText("去付款");
                        } else if (order.order_status.equals("5")) {
                            tvCancelorder.setText("取消订单");
                            tvCancelorder.setTag("CANCEL");
                        } else if (order.order_status.equals("8") && order.comment_status.equals("0")) {
                            tvCancelorder.setText("去评价");
                            tvCancelorder.setTag("TOEVALUATE");
                        } else if (order.order_status.equals("8") && order.comment_status.equals("1")) {
                            tvCancelorder.setText("查看评价");
                            tvCancelorder.setTag("LOOKEVALUATE");
                        }
                    }


                    statusview.showContent();
                } else {
                    ToastUtil.show(details.message);
                    statusview.showError();
                }
                if (null != mrefrshLayour) {
                    mrefrshLayour.onFinishFreshAndLoad();
                }

            }

            @Override
            public void onBeforeAnimate() {
                if (!show) {
                    statusview.showLoading();
                }
            }

            @Override
            public void onErrorAnimate() {
                if (!show) {
                    statusview.showError();
                }
            }
        });
    }


    private void inintrefrshlayout() {
        mrefrshLayour.setGive(SpringView.Give.TOP);
        mrefrshLayour.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                requestDetails(orderId, true);
            }

            @Override
            public void onLoadmore() {

            }
        });
        mrefrshLayour.setHeader(new DefaultHeader(this));
        mrefrshLayour.setType(SpringView.Type.FOLLOW);
    }


    @OnClick({R.id.ll_details_product, R.id.iv_voucher, R.id.tv_shop_locatioin, R.id.iv_shop_call, R.id.tv_cancelorder, R.id.tv_play})
    public void onViewClicked(View view) {
        if (!Utils.isFastDoubleClick()) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.ll_details_product:
                    intent.setClass(this, TuanProductDetailsActivity.class);
                    intent.putExtra(TuanProductDetailsActivity.TUAN_ID, order.tuan_id);
                    startActivity(intent);
                    break;
                case R.id.iv_voucher:
                    intent.setClass(this, TuanBulkVolumeActivity.class);
                    intent.putExtra("ticket_number", order.ticket_number);
                    intent.putExtra("ShopCount", order.tuan_number);
                    intent.putExtra("Ticket_status", order.ticket_status);
                    startActivity(intent);
                    break;
                case R.id.tv_shop_locatioin:

                    break;
                case R.id.iv_shop_call:
                    if (!TextUtils.isEmpty(order.phone)) {
                        Utils.callPhone(TuanOrderDetailsActivity.this,order.phone);
                    }
                    break;
                case R.id.tv_cancelorder:
                    if (tvCancelorder.getTag().equals("CANCEL")) {
                        new CallDialog(TuanOrderDetailsActivity.this)
                                .setMessage("确认取得订单")
                                .setTipTitle("提示")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        cancleOrder(Api.WAIMAI_TUAN_ORDER_CANCEL);
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    } else if (tvCancelorder.getTag().equals("TOEVALUATE")) {
                        intent.setClass(this, TuanOrderEvaluateActivity.class);
                        intent.putExtra("order_id", orderId);
                        intent.putExtra("type", "tuan");
                        intent.putExtra("shop_logo", order.tuan_photo);
                        intent.putExtra("shop_title", order.tuan_title);
                        startActivity(intent);
                    } else if (tvCancelorder.getTag().equals("LOOKEVALUATE")) {
                        intent.setClass(this, LookTuanOrderMerchantEvaluationActivity.class);
                        intent.putExtra("comment_id", order.comment_id);
                        startActivity(intent);
                    }
                    break;
                case R.id.tv_play:
                    intent.setClass(this, ToPayNewActivity.class);
                    intent.putExtra(ToPayNewActivity.ORDER_ID, order.order_id);
                    intent.putExtra(ToPayNewActivity.MONEY, order.amount);
                    intent.putExtra(ToPayNewActivity.FROM, ToPayNewActivity.TO_GROUP);
                    startActivity(intent);
                    finish();
                    break;
            }
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        requestDetails(orderId, true);
    }

    private void cancleOrder(String api) {
        JSONObject object = new JSONObject();
        try {
            object.put("order_id", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrl(this, api, object.toString(), true, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                Gson gson = new Gson();
                Response response = gson.fromJson(Json, Response.class);
                if (response.error.equals("0")) {
                    ToastUtil.show("取消成功");
                } else {
                    ToastUtil.show(response.message);
                }
                requestDetails(orderId, true);
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
