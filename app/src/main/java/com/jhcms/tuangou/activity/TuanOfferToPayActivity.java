package com.jhcms.tuangou.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Data_Group_Shop_Detail;
import com.jhcms.common.model.Response;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.CashierInputFilter;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.widget.MyEditText;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.activity.ToPayNewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * TODO 优惠买单
 */
public class TuanOfferToPayActivity extends SwipeBaseActivity {
    public static String TYPE = "TYPE";
    public static String SHOPDETAIL = "SHOPDETAIL";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.dealMoney_tv)
    EditText dealMoneyTv;
    @Bind(R.id.YesOrNoConcessions_tv)
    ImageView YesOrNoConcessionsTv;
    @Bind(R.id.NoConcessionsMoney_ed)
    MyEditText NoConcessionsMoneyEd;
    @Bind(R.id.NoConcessions_lay)
    LinearLayout NoConcessionsLay;
    @Bind(R.id.tv_quan_manjian)
    TextView tvQuanManjian;
    @Bind(R.id.ConcessionsMoney_tv)
    TextView ConcessionsMoneyTv;
    @Bind(R.id.truePaymoney_ed)
    TextView truePaymoneyEd;
    @Bind(R.id.SubmitButton_tv)
    TextView SubmitButtonTv;
    @Bind(R.id.ll_quan_manjian)
    LinearLayout llQuanManjian;
    @Bind(R.id.YesOrNoConcessions_ll)
    LinearLayout YesOrNoConcessionsLl;
    @Bind(R.id.tv_buf)
    TextView tvBuf;
    @Bind(R.id.tv_symbol)
    TextView tvSymbol;
    @Bind(R.id.tv_hint)
    TextView tvHint;

    private boolean isYouhui = false;
    /**
     * 类型 0:满减, 1:折扣
     */
    private String type;

    private Data_Group_Shop_Detail.DetailBean shopDetail;
    private String youhui;
    private NumberFormat nf;
    /**
     * 消费金额
     */
    private String conPrice;
    /**
     * 不享受优惠金额
     */
    private String notEnjoy;
    /**
     * 消费金额
     */
    private Double actualPrices;
    /**
     * 不享受优惠金额
     */
    private Double notEnjoyPrices = 0.0;

    InputFilter[] filters = {new CashierInputFilter()};
    /**
     * 不享受优惠金额的焦点
     */
    private boolean noconcessionsFocus = false;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_offer_to_pay);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        setToolBar(toolbar);
        type = getIntent().getStringExtra(TYPE);
        shopDetail = (Data_Group_Shop_Detail.DetailBean) getIntent().getSerializableExtra(SHOPDETAIL);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvTitle.setText(R.string.优惠买单);
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        SubmitButtonTv.setEnabled(false);
        dealMoneyTv.setFilters(filters);
        NoConcessionsMoneyEd.setFilters(filters);
        /**
         * 消费金额
         */
        dealMoneyTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtil.i("onTextChanged---->" + s);
                conPrice = s.toString();
                int bg = TextUtils.isEmpty(s) ? R.drawable.selector_tuan__offer_to_pay_butn_bg : R.drawable.tuan_red_bag;
                SubmitButtonTv.setBackground(getResources().getDrawable(bg));
                SubmitButtonTv.setEnabled(TextUtils.isEmpty(s) ? false : true);
                dealWithMone(conPrice, notEnjoy);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    NoConcessionsMoneyEd.setMoneyIsEmpty(false);
                    tvBuf.setVisibility(View.VISIBLE);
                    float textWidth = getTextWidth(dealMoneyTv);
                    tvBuf.setX(-textWidth);
                    tvHint.setVisibility(View.GONE);
                } else {
                    NoConcessionsMoneyEd.setMoneyIsEmpty(true);
                    tvBuf.setVisibility(View.GONE);
                    tvHint.setVisibility(View.VISIBLE);
                }
            }
        });


        /**
         * 不享受优惠金额
         */
        NoConcessionsMoneyEd.setOnFocusChangeListener((v, hasFocus) -> {
            noconcessionsFocus = hasFocus;
        });

        NoConcessionsMoneyEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notEnjoy = s.toString();
                if (!TextUtils.isEmpty(conPrice)) {
                    dealWithMone(conPrice, notEnjoy);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    tvSymbol.setVisibility(View.VISIBLE);
                    float textWidth = getTextWidth(NoConcessionsMoneyEd);
                    tvSymbol.setX(-textWidth);
                } else {
                    tvSymbol.setVisibility(View.GONE);
                }
            }
        });
        /**
         *
         * 类型
         * 0:满减, 每满1000减100元 每满2000减200元 最大优惠(1000元)
         * 1:折扣  限时折扣：消费打9.5折，最高减100元
         *
         * */
        if (!TextUtils.isEmpty(type)) {
            llQuanManjian.setVisibility(View.VISIBLE);
            YesOrNoConcessionsLl.setVisibility(View.VISIBLE);
            if (type.equals("0")) {
                if (null != shopDetail.options.config && shopDetail.options.config.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < shopDetail.options.config.size(); i++) {
                        sb.append(String.format("每满<font color=\"#ff6600\">%s" + "</font> 减<font color=\"#ff6600\">%s" + "</font>元 ", shopDetail.options.config.get(i).m, shopDetail.options.config.get(i).d));
                    }
                    sb.append(String.format("最大优惠(<font color=\"#ff6600\">%s" + "</font>元)", shopDetail.options.max_youhui));
                    youhui = sb.toString();
                }
            } else {
                youhui = String.format("限时折扣：消费打<font color=\"#ff6600\">%s" + "</font>折，最高减<font color=\"#ff6600\">%s" + "</font>元", (shopDetail.options.discount / 10) + "", shopDetail.options.max_youhui);
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                tvQuanManjian.setText(Html.fromHtml(youhui, Html.FROM_HTML_MODE_LEGACY));
            } else {
                tvQuanManjian.setText(Html.fromHtml(youhui));
            }
        } else {
            YesOrNoConcessionsLl.setVisibility(View.GONE);
            llQuanManjian.setVisibility(View.GONE);
        }


    }

    private float getTextWidth(EditText editText) {
        TextPaint mTextPaint = editText.getPaint();
        return mTextPaint.measureText(editText.getText().toString());
    }

    /**
     * 处理实际支付金额
     *
     * @param actual   实际消费金额
     * @param notEnjoy 不参与优惠的金额
     */
    private double money;

    private void dealWithMone(String actual, String notEnjoy) {
        LogUtil.i("实际消费金额" + actual);
        LogUtil.i("不参与优惠的金额" + notEnjoy);

        /*消费金额不为空*/
        if (!TextUtils.isEmpty(actual)) {
            /*输入不享受优惠状态打开*/
            if (isYouhui) {
                /*不享受优惠的金额不为空*/
                if (!TextUtils.isEmpty(notEnjoy)) {
                    notEnjoyPrices = Double.valueOf(notEnjoy);
                    /*如果不享受优惠的金额大于消费金额*/
                    if (notEnjoyPrices > Double.valueOf(actual)) {
                        /*只有当不享受优惠获取焦点时候才提示*/
                        if (noconcessionsFocus) {
                            ToastUtil.show("不享受优惠金额不能大于消费金额");
                        }
                        money = Double.valueOf(actual);
                        NoConcessionsMoneyEd.setText(actual);
                        NoConcessionsMoneyEd.setSelection(actual.length());
                        truePaymoneyEd.setText(nf.format(money));
                        return;
                    } else {
                        actualPrices = Double.valueOf(actual) - notEnjoyPrices;
                    }
                } else {
                    actualPrices = Double.valueOf(actual);
                    notEnjoyPrices = 0.0;
                }
            } else {
                actualPrices = Double.valueOf(actual);
                notEnjoyPrices = 0.0;
            }


            if (!TextUtils.isEmpty(type)) {
                if (type.equals("1")) {
                    /*折扣*/
                    money = actualPrices * (shopDetail.options.discount / 100) + notEnjoyPrices;
                    /*如果满减大于最大优惠数*/
                    if ((actualPrices - money) >= Double.valueOf(shopDetail.options.max_youhui)) {
                        money = actualPrices - Double.valueOf(shopDetail.options.max_youhui) + notEnjoyPrices;
                    }
                    truePaymoneyEd.setText(nf.format(money));
                } else if (type.equals("0")) {
                    /*每满减*/
                    for (int i = shopDetail.options.config.size() - 1; i >= 0; i--) {
                        /*消费金额满足满减优惠*/
                        if (actualPrices >= Double.valueOf(shopDetail.options.config.get(i).m)) {
                            double v = actualPrices / Double.valueOf(shopDetail.options.config.get(i).m);
                            double floor = Math.floor(v);
                            /*满减优惠总金额*/
                            double v1 = floor * Double.valueOf(shopDetail.options.config.get(i).d);

                            money = actualPrices - v1 + notEnjoyPrices;
                            /*如果满减大于最大优惠则*/
                            if (v1 >= Double.valueOf(shopDetail.options.max_youhui)) {
                                money = actualPrices - Double.valueOf(shopDetail.options.max_youhui) + notEnjoyPrices;
                            }
                            truePaymoneyEd.setText(nf.format(money));
                            return;
                        } else {
                            money = actualPrices + notEnjoyPrices;
                            truePaymoneyEd.setText(nf.format(money));
                        }
                    }
                }
            } else {
                money = actualPrices + notEnjoyPrices;
                truePaymoneyEd.setText(nf.format(money));
            }
        } else {
            /*清空不享受优惠金额的数据*/
            handler.sendEmptyMessage(1);
            money = 0;
            truePaymoneyEd.setText(nf.format(money));
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    NoConcessionsMoneyEd.setText("");
                    break;
            }
        }
    };

    private void inintsubmintBtn() {
        JSONObject object = new JSONObject();
        try {
            object.put("shop_id", shopDetail.shop_id);
            object.put("money", money);
            object.put("nomoney", notEnjoyPrices);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrl(this, Api.TUAN_ORDER_MAIDAN_CREATE, object.toString(), true, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                try {
                    Gson gson = new Gson();
                    Response response = gson.fromJson(Json, Response.class);
                    if (response.error.equals("0")) {
                        Intent intent = new Intent(TuanOfferToPayActivity.this, ToPayNewActivity.class);
                        intent.putExtra(ToPayNewActivity.ORDER_ID, response.data.order_id);
                        intent.putExtra(ToPayNewActivity.MONEY, money);
                        intent.putExtra(ToPayNewActivity.FROM, ToPayNewActivity.TO_OFFER);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtil.show(response.message);
                    }
                } catch (Exception e) {
                    ToastUtil.show(getString(R.string.数据解析异常));
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


    @OnClick({R.id.YesOrNoConcessions_ll, R.id.SubmitButton_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.YesOrNoConcessions_ll:
                if (isYouhui) {
                    YesOrNoConcessionsTv.setSelected(false);
                    NoConcessionsLay.setVisibility(View.GONE);
                    isYouhui = false;
                } else {
                    YesOrNoConcessionsTv.setSelected(true);
                    NoConcessionsLay.setVisibility(View.VISIBLE);
                    isYouhui = true;
                }
                dealWithMone(conPrice, notEnjoy);
                break;
            case R.id.SubmitButton_tv:
                inintsubmintBtn();
                break;
        }
    }
}
