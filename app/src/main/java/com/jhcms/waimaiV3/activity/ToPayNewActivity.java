package com.jhcms.waimaiV3.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Data_WaiMai_PayOrder;
import com.jhcms.common.model.RefreshEvent;
import com.jhcms.common.model.Response_Mine;
import com.jhcms.common.model.Response_WaiMai_PayOrder;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.utils.WaiMaiPay;
import com.jhcms.common.widget.AutofitTextView;
import com.jhcms.tuangou.activity.GroupOfferToPayOrderDeatail;
import com.jhcms.tuangou.activity.TuanOrderDetailsActivity;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.lang.Double.parseDouble;

public class ToPayNewActivity extends PayActivity implements OnRequestSuccessCallback {

    public static String ORDER_ID = "ORDER_ID";
    public static String MONEY = "MONEY";

    public static String FROM = "FROM";
    /**
     * 按返回键回退到商城订单详情
     */
    public static String TO_MALL = "TO_MALL";
    /**
     * 按返回键回退到团购订单详情
     */
    public static String TO_GROUP = "TO_GROUP";
    /**
     * 按返回键回退到外卖订单详情
     */
    public static String TO_WAIMAI = "TO_WAIMAI";
    /**
     * 按返回键回退到优惠买单订单详情
     */
    public static String TO_OFFER = "TO_OFFER";
    /**
     * 按返回键回退到网页
     */
    public static String TO_WEBVIEW = "TO_WEBVIEW";
    /**
     * 网页回调地址
     */
    public static String BACK_URL = "BACK_URL";
    /**
     * 按返回键回退到跑腿订单详情
     * */
    public static String TO_PAOTUI = "TO_PAOTUI";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_order_id)
    TextView tvOrderId;
    @Bind(R.id.tv_paymoney)
    TextView tvPaymoney;
    @Bind(R.id.tv_balance)
    AutofitTextView tvBalance;
    @Bind(R.id.tv_balance_info)
    AutofitTextView tvBalanceInfo;
    @Bind(R.id.iv_balance_btn)
    ImageView ivBalanceBtn;
    @Bind(R.id.ll_balance)
    LinearLayout llBalance;
    @Bind(R.id.iv_alipay_btn)
    ImageView ivAlipayBtn;
    @Bind(R.id.ll_alipay)
    LinearLayout llAlipay;
    @Bind(R.id.iv_wxpay_btn)
    ImageView ivWxpayBtn;
    @Bind(R.id.ll_wxpay)
    LinearLayout llWxpay;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;


    private String orderId, from;

    private NumberFormat nf;
    /**
     * 支付金额
     */
    private double money;
    private String code = "alipay";
    /**
     * 余额支付状态是否打开
     */
    private boolean isBalance = false;

    private int use_money = 1;
    private String backUrl;


    @Override
    protected void initData() {
        tvTitle.setText(R.string.支付页面);
        toolbar.setNavigationOnClickListener(v -> {
            if (TO_OFFER.equals(from) || TO_MALL.equals(from)) {
                finish();
            } else {
                goBack();
            }

        });
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        getuserInfo();
        Intent intent = getIntent();
        orderId = intent.getStringExtra(ORDER_ID);
        money = intent.getDoubleExtra(MONEY, 0.0);
        from = intent.getStringExtra(FROM);
        backUrl = intent.getStringExtra(BACK_URL);
        tvOrderId.setText(orderId);
        tvPaymoney.setText(nf.format(money));
    }

    public static void startActivity(Context context, String order_id, double money, String from) {
        Intent intent = new Intent(context, ToPayNewActivity.class);
        intent.putExtra(ORDER_ID, order_id);
        intent.putExtra(ToPayNewActivity.MONEY, money);
        intent.putExtra(ToPayNewActivity.FROM, from);
        context.startActivity(intent);
    }

    private void getuserInfo() {
        try {
            HttpUtils.postUrl(this, Api.SHEQU_USERINFO, null, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param ban  余额
     * @param need 还需支付
     */
    private void dealWithBalance(String ban, String need, boolean isShowNeed) {
        if (isShowNeed) {
            tvBalanceInfo.setVisibility(View.VISIBLE);
        } else {
            tvBalanceInfo.setVisibility(View.GONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvBalance.setText(
                    Html.fromHtml(String.format(
                            "<font color=\"#999999\">余额:" +
                                    "</font><font color=\"#ff6600\">%s</font> ",
                            nf.format(parseDouble(ban)), Html.FROM_HTML_MODE_LEGACY)));
            tvBalanceInfo.setText(
                    Html.fromHtml(String.format(
                            "<font color=\"#999999\">还需支付:" +
                                    "</font><font color=\"#ff6600\">%s</font>", nf.format(parseDouble(need)), Html.FROM_HTML_MODE_LEGACY)));
        } else {
            tvBalance.setText(Html.fromHtml(
                    String.format("<font color=\"#999999\">余额:" +
                                    "</font><font color=\"#ff6600\">%s</font> ",
                            nf.format(parseDouble(ban)))));
            tvBalanceInfo.setText(Html.fromHtml(
                    String.format("<font color=\"#999999\">还需支付:" +
                                    "</font><font color=\"#ff6600\">%s</font>",
                            nf.format(parseDouble(need)))));
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_to_pay_new);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.ll_balance, R.id.ll_alipay, R.id.ll_wxpay, R.id.tv_submit, R.id.iv_balance_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_balance_btn:
            case R.id.ll_balance:
                /*TODO 余额支付*/
                if (isBalance) {
                    //余额大于支付金额 支付宝微信不可点
                    isBalance = false;
                    use_money = 0;
                    ivBalanceBtn.setSelected(isBalance);
                    llAlipay.setClickable(true);
                    llWxpay.setClickable(true);
                    if (code.equals("alipay")) {
                        ivAlipayBtn.setSelected(true);
                    } else if (code.equals("wxpay")) {
                        ivWxpayBtn.setSelected(true);
                    } else {
                        code = "alipay";
                        ivAlipayBtn.setSelected(true);
                    }
                    dealWithBalance(balance, String.valueOf(money), false);
                } else {
                    isBalance = true;
                    use_money = 1;
                    ivBalanceBtn.setSelected(isBalance);
                    //余额大于支付金额 支付宝微信可点
                    if (Double.parseDouble(balance) >= money) {
                        llAlipay.setClickable(false);
                        ivAlipayBtn.setSelected(false);
                        ivWxpayBtn.setSelected(false);
                        llWxpay.setClickable(false);
                        dealWithBalance(balance, "0", false);
                        code = "money";
                    } else {
                        llAlipay.setClickable(true);
                        llWxpay.setClickable(true);
                        if (code.equals("alipay")) {
                            ivAlipayBtn.setSelected(true);
                        } else if (code.equals("wxpay")) {
                            ivWxpayBtn.setSelected(true);
                        }
                        dealWithBalance(balance, String.valueOf(money - Double.parseDouble(balance)), true);
                    }
                }
                break;
            case R.id.ll_alipay:
                /*TODO 支付宝支付*/
                code = "alipay";
                ivAlipayBtn.setSelected(true);
                ivWxpayBtn.setSelected(false);
                break;
            case R.id.ll_wxpay:
                 /*TODO 微信支付*/
                code = "wxpay";
                ivAlipayBtn.setSelected(false);
                ivWxpayBtn.setSelected(true);
                break;
            case R.id.tv_submit:
                if (!Utils.isFastDoubleClick()) {
                    payOrder(Api.WAIMAI_SHOP_PAY_ORDER);
                }
                break;
        }
    }


    public void payOrder(String api) {
        JSONObject object = new JSONObject();
        try {
            /*订单ID*/
            object.put("order_id", orderId);
            object.put("code", code);
            object.put("use_money", use_money);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, api, str, true, this);
    }

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            switch (url) {
                case Api.WAIMAI_SHOP_PAY_ORDER:
                    Response_WaiMai_PayOrder payOrder = gson.fromJson(Json, Response_WaiMai_PayOrder.class);
                    if (payOrder.error.equals("0")) {
                        if (null != payOrder.data.go_order_detail && "1".equals(payOrder.data.go_order_detail)) {
                            ToastUtil.show(payOrder.message);
                            goBack();
                            dealwithWebPay();
                        } else {
                            if (code.equals("money")) {
                                ToastUtil.show(payOrder.message);
                                //TODO 处理余额支付成功后的走向
                                goBack();
                                dealwithWebPay();
                            } else {
                                Data_WaiMai_PayOrder data = payOrder.data;
                                /*处理微信、支付宝支付*/
                                PayOrder(code, data);
                                dealwithWebPay();
                            }
                        }
                    } else {
                        ToastUtil.show(payOrder.message);
                    }
                    break;
                case Api.SHEQU_USERINFO:
                    Response_Mine mDatamodel = gson.fromJson(Json, Response_Mine.class);
                    if (mDatamodel.error.equals("0")) {
                        balance = mDatamodel.getData().getMoney();
                        initBalance(balance);

                    } else {
                        ToastUtil.show(mDatamodel.message);
                    }
                    break;
            }

        } catch (Exception e) {
            ToastUtil.show("解析错误！");
        }
    }

    private void dealwithWebPay() {
        if (!TextUtils.isEmpty(from) && TO_WEBVIEW.equals(from)) {
            Intent intent = new Intent();
            intent.setClass(ToPayNewActivity.this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.URL, backUrl);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 余额
     */
    private String balance;

    private void initBalance(String balance) {
        if (parseDouble(balance) > 0) {
            //余额大于0
            isBalance = true;
            use_money = 1;
            ivBalanceBtn.setClickable(isBalance);
            ivBalanceBtn.setSelected(isBalance);
            //余额大于支付金额 支付宝微信可点
            if (parseDouble(balance) >= money) {
                llAlipay.setClickable(false);
                llWxpay.setClickable(false);
                dealWithBalance(balance, "0", false);
                code = "money";
            } else {
                llAlipay.setClickable(true);
                code = "alipay";
                ivAlipayBtn.setSelected(true);
                llWxpay.setClickable(true);
                dealWithBalance(balance, String.valueOf(money - parseDouble(balance)), true);
            }
        } else {
            isBalance = false;
            llBalance.setClickable(isBalance);
            ivBalanceBtn.setSelected(isBalance);
            code = "alipay";
            ivAlipayBtn.setSelected(true);
            use_money = 0;
            dealWithBalance(balance, String.valueOf(money - parseDouble(balance)), false);
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
                    //TODO 处理支付宝支付成功后的走向
                    goBack();
                }
            }
        });
    }
    /*微信支付成功*/

    @Subscribe
    public void onMessageEvent(RefreshEvent event) {
        if (event.getmMsg().equals("weixin_pay_success")) {
            //TODO 处理微信支付成功后的走向
            goBack();
        }
    }

    @Override
    public void onBackPressed() {
        if (TO_OFFER.equals(from) || TO_MALL.equals(from)) {
            finish();
        } else {
            goBack();
        }
    }

    private void goBack() {
        if (!TextUtils.isEmpty(from)) {
            Intent intent = new Intent();
            if (from.equals(TO_WAIMAI)) {
                if (MyApplication.MAP.equals(Api.GAODE)) {
                    intent.setClass(ToPayNewActivity.this, OrderDetailsActivity.class);
                    intent.putExtra(OrderDetailsActivity.ORDER_ID, orderId);
                } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                    intent.setClass(ToPayNewActivity.this, OrderDetailsGMSActivity.class);
                    intent.putExtra(OrderDetailsGMSActivity.ORDER_ID, orderId);
                }
            } else if (from.equals(TO_GROUP)) {
                intent.setClass(ToPayNewActivity.this, TuanOrderDetailsActivity.class);
                intent.putExtra(TuanOrderDetailsActivity.ORDER_ID, orderId);
            } else if (from.equals(TO_OFFER)) {
                intent.setClass(ToPayNewActivity.this, GroupOfferToPayOrderDeatail.class);
                intent.putExtra(GroupOfferToPayOrderDeatail.ORDER_ID, orderId);
            } else {
                finish();
                return;
            }
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }


}
