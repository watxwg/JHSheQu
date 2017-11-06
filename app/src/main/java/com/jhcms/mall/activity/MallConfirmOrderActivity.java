package com.jhcms.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.gson.Gson;
import com.jhcms.common.model.Data_WaiMai_PayOrder;
import com.jhcms.common.model.RefreshEvent;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.DividerListItemDecoration;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.WaiMaiPay;
import com.jhcms.mall.adapter.ConfirmAdapter;
import com.jhcms.mall.model.AddressInfoModel;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.CouponOfOrderModel;
import com.jhcms.mall.model.OrderModel;
import com.jhcms.mall.model.OrderPreprocessModel;
import com.jhcms.mall.model.ResultModel;
import com.jhcms.mall.model.ShopOrderInfoModel;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.PayActivity;
import com.jhcms.waimaiV3.adapter.BottomSheetAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.waimaiV3.R.id.multiplestatusview;

public class MallConfirmOrderActivity extends PayActivity {
    private static final String TAG = MallConfirmOrderActivity.class.getSimpleName();
    private static final String PRODUCT_INFO = "productInfo";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_tobePaid)
    TextView tvTobePaid;
    @Bind(R.id.tv_toPay)
    TextView tvToPay;
    @Bind(R.id.ll_pay)
    LinearLayout llPay;
    @Bind(R.id.tv_userInfo)
    TextView tvUserInfo;
    @Bind(R.id.tv_userAddress)
    TextView tvUserAddress;
    @Bind(R.id.ll_address)
    LinearLayout llAddress;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.content_view)
    RelativeLayout contentView;
    @Bind(multiplestatusview)
    MultipleStatusView statusview;
    @Bind(R.id.bottomsheet)
    BottomSheetLayout bottomsheet;
    private int REQUEST_ADDRESS = 0x1001;
    private View payMentView;
    private final String COUPON = "选择优惠券";
    private final String TOPAY = "应支付金额";
    private BottomSheetAdapter sheetAdapter;
    private int COUPON_ID = 0;
    private int TOPAY_ID = 0;
    private NumberFormat nf;
    private String code;
    private List<ShopOrderInfoModel> mShopOrderData;
    private ConfirmAdapter confirmAdapter;
    //选择的地址id
    private String selectAddrId;
    private String mProductInfo;
    private List<String> orderIdList;
    private View couponView;
    private String[] payType;
    private ArrayList payTypeList;
    private String mOrderId;
    private DividerListItemDecoration divider;
    private boolean isPaySuccess = false;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_confirm_order);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static Intent generateIntent(Context context, String productInfo) {
        Intent intent = new Intent(context, MallConfirmOrderActivity.class);
        intent.putExtra(PRODUCT_INFO, productInfo);
        return intent;
    }

    @Override
    protected void initData() {
        mProductInfo = getIntent().getStringExtra(PRODUCT_INFO);
        setData();
        requestData(mProductInfo, null, null, false);
        tvTitle.setText(R.string.确认下单);
        toolbar.setNavigationOnClickListener(v -> finish());
        mShopOrderData = new ArrayList<>();
        confirmAdapter = new ConfirmAdapter(this, mShopOrderData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(confirmAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        divider = new DividerListItemDecoration.Builder(this)
                .setHeight(R.dimen.dp_1)
                .setColorResource(R.color.background)
                .build();
        //监听点击使用优惠券
        confirmAdapter.setOnClickCouponListener(((position, coupons, couponSelectListener) -> showCoupon(coupons, couponSelectListener)));

        confirmAdapter.setOnCouponChangedListener(() -> requestData(mProductInfo, getCouponIds(), selectAddrId, true));


        /*getCurrencyInstance 返回当前缺省语言环境的通用格式*/
        nf = NumberFormat.getCurrencyInstance();
        /*设置数值的小数部分允许的最大位数为2*/
        nf.setMaximumFractionDigits(2);
    }

    private HashMap<String, String> getCouponIds() {
        if (mShopOrderData == null || mShopOrderData.size() == 0) {
            return null;
        }
        HashMap<String, String> couponIds = new HashMap<>();
        for (int i = 0; i < mShopOrderData.size(); i++) {
            ShopOrderInfoModel shopOrderInfoModel = mShopOrderData.get(i);
            couponIds.put(shopOrderInfoModel.getShop_id(), shopOrderInfoModel.getCoupon_id());
        }
        return couponIds;
    }

    /**
     * 请求数据,修改地址或优惠券后，都需要重新请求
     *
     * @param productInfo   购物车信息
     * @param couponIds     优惠券id
     * @param addrId        地址id
     * @param isShowDefault 是否显示默认动画
     */
    private void requestData(@NonNull String productInfo, @Nullable HashMap<String, String> couponIds, @Nullable String addrId, @Nullable boolean isShowDefault) {

        String paramData = null;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("products", productInfo);
            if (couponIds != null && couponIds.size() > 0) {
                jsonObject.put("coupon_ids", new JSONObject(couponIds));
            }
            if (!TextUtils.isEmpty(addrId)) {
                jsonObject.put("addr_id", addrId);
            }
            paramData = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show(R.string.mall_程序出错);
            return;
        }
        if (!isShowDefault) {
            statusview.showLoading();
        }
        Log.e(TAG, "requestData: "+paramData );
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_ORDER_PREPROCESS, paramData, isShowDefault, new OnRequestSuccess<BaseResponse<ResultModel<OrderPreprocessModel>>>() {

            @Override
            public void onSuccess(String url, BaseResponse<ResultModel<OrderPreprocessModel>> data) {
                super.onSuccess(url, data);
                OrderPreprocessModel result = data.getData().getResult();
                initBaseInfo(result);
                mShopOrderData.clear();
                mShopOrderData.addAll(result.getOrder_info().values());
                confirmAdapter.notifyDataSetChanged();
                statusview.showContent();
            }

            @Override
            public void onErrorAnimate() {
                super.onErrorAnimate();
                statusview.showError();
            }
        });
    }

    /**
     * 创建订单
     */
    private void requestCreateOrder() {
        String params = buildRequestParams();
        if (params == null) {
            return;
        }
        Log.e(TAG, "requestCreateOrder: "+params );
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_ORDER_CREATE, params, true, new OnRequestSuccess<BaseResponse<OrderModel>>() {
            @Override
            public void onSuccess(String url, BaseResponse<OrderModel> data) {
                super.onSuccess(url, data);

                StringBuilder sb = new StringBuilder();
                orderIdList = data.getData().getOrder_ids();
                for (int i = 0; i < orderIdList.size(); i++) {
                    if (i == orderIdList.size() - 1) {
                        sb.append(orderIdList.get(i));
                    } else {
                        sb.append(orderIdList.get(i) + "_");
                    }
                }
                mOrderId = sb.toString();
                showPayMent(TOPAY);
            }
        });
    }

    /**
     * 拼接创建订单请求参数
     */
    @Nullable
    private String buildRequestParams() {
        String paramData = null;
        try {
            HashMap<String, String> intro = new HashMap<>();
            HashMap<String, String> coupon_ids = new HashMap<>();
            for (int i = 0; i < mShopOrderData.size(); i++) {
                ShopOrderInfoModel shopOrderInfoModel = mShopOrderData.get(i);
                if (!TextUtils.isEmpty(shopOrderInfoModel.getMarkInfo())) {

                    intro.put(shopOrderInfoModel.getShop_id(), shopOrderInfoModel.getMarkInfo());
                }
                if (!TextUtils.isEmpty(shopOrderInfoModel.getCoupon_id())) {
                    coupon_ids.put(shopOrderInfoModel.getShop_id(), shopOrderInfoModel.getCoupon_id());
                }

            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("addr_id", selectAddrId);
            jsonObject.put("products", mProductInfo);
            if (intro.size() > 0) {
                jsonObject.put("intro", new JSONObject(intro));
            }
            if (coupon_ids.size() > 0) {
                jsonObject.put("coupon_ids", new JSONObject(coupon_ids));
            }
            paramData = jsonObject.toString();
            return paramData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 初始化地址信息
     *
     * @param result
     */
    double totalPrice;

    private void initBaseInfo(OrderPreprocessModel result) {
        AddressInfoModel addr = result.getAddr();
        tvUserInfo.setText(getString(R.string.mall_收货人f, addr.getContact() + " " + addr.getMobile()));
        tvUserAddress.setText(getString(R.string.mall_收获地址, addr.getProvince_name() + addr.getCity_name() + addr.getArea_name() + addr.getAddr()));
        selectAddrId = addr.getAddr_id();
        double totalCouponsAmount = 0;
        try {
            totalPrice = 0;
            for (Map.Entry<String, ShopOrderInfoModel> entry : result.getOrder_info().entrySet()) {
                ShopOrderInfoModel value = entry.getValue();
                Double d = Double.parseDouble(value.getTotal_price());
                d+=value.getFreight();
                totalPrice += d;
                CouponOfOrderModel coupon = value.getCoupon();
                if(!TextUtils.isEmpty(coupon.getCoupon_amount())){
                    totalCouponsAmount+=Double.parseDouble(coupon.getCoupon_amount());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        totalPrice-=totalCouponsAmount;
        tvTobePaid.setText(nf.format(totalPrice));
    }


    @OnClick({R.id.tv_toPay, R.id.ll_address})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_toPay:
                for (int i = 0; i < mShopOrderData.size(); i++) {
                    ShopOrderInfoModel shopOrderInfoModel = mShopOrderData.get(i);
                }
                requestCreateOrder();

                break;
            case R.id.ll_address:
                intent.setClass(this, MallShippingAddressActivty.class);
                intent.putExtra("addr_id", selectAddrId);
                startActivityForResult(intent, REQUEST_ADDRESS);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_ADDRESS == requestCode && resultCode == RESULT_OK) {
            if (data != null) {
                selectAddrId = data.getStringExtra("addr_id");
                requestData(mProductInfo, getCouponIds(), selectAddrId, true);
            }
        }
    }

    //优惠券对话框
    private void showCoupon(@NonNull List<CouponOfOrderModel> data, ConfirmAdapter.OnCouponSelectListener listener) {
        couponView = createCouponView(data, listener);
        if (bottomsheet.isSheetShowing()) {
            bottomsheet.dismissSheet();
        } else {
            bottomsheet.showWithSheetView(couponView);

        }
    }

    private View createCouponView(@NonNull List<CouponOfOrderModel> data, ConfirmAdapter.OnCouponSelectListener listener) {
        List<String> stringData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            stringData.add(data.get(i).getTitle());
        }
        View view = LayoutInflater.from(this).inflate(R.layout.payment, null);
        findViews(view);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeBottomsheet();
            }
        });
        sheetAdapter = new BottomSheetAdapter(this);
        rlBottom.setAdapter(sheetAdapter);
        rlBottom.setLayoutManager(new LinearLayoutManager(this));
        rlBottom.addItemDecoration(divider);
        llGoPay.setVisibility(View.GONE);
        tvPrice.setVisibility(View.GONE);
        tvBottomTitle.setText(COUPON);
        sheetAdapter.setType(1);
        sheetAdapter.setData(stringData);
        sheetAdapter.setSelectId(COUPON_ID);
        sheetAdapter.setOnItemClickListener(position -> {
            if (listener != null) {
                listener.onCouponSelect(data.get(position));
            }
            closeBottomsheet();
        });

        return view;
    }

    private void showPayMent(final String type) {
        payMentView = createPayMentView(type);
        if (bottomsheet.isSheetShowing()) {
            bottomsheet.dismissSheet();
        } else {
            bottomsheet.showWithSheetView(payMentView);
            bottomsheet.addOnSheetStateChangeListener(new BottomSheetLayout.OnSheetStateChangeListener() {
                @Override
                public void onSheetStateChanged(BottomSheetLayout.State state) {
                    /*TODO formatLayout 消失*/
                    if (state.toString().equals("HIDDEN")) {
                        switch (type) {
                            /*TODO 优惠券*/
                            case COUPON:
                                /*if (COUPON_ID == data.coupons.size()) {
                                    coupon_id = -1;
                                } else {
                                    coupon_id = data.coupons.get(COUPON_ID).coupon_id;
                                }
                                preinfoOrder(Api.WAIMAI_SHOP_ORDER_PREINFO);*/
                                break;
                            case TOPAY:
                                enterOrderDetails();
                                break;
                        }
                    } else if (state.toString().equals("PEEKED")) {
                            /*formatLayout 展开*/
                    }
                }
            });
        }

    }

    private View createPayMentView(final String mode) {
        View view = LayoutInflater.from(this).inflate(R.layout.payment, null);
        findViews(view);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeBottomsheet();
            }
        });
        sheetAdapter = new BottomSheetAdapter(this);
        rlBottom.setAdapter(sheetAdapter);
        rlBottom.setLayoutManager(new LinearLayoutManager(this));
        rlBottom.addItemDecoration(divider);
        switch (mode) {
            /*TODO 选择优惠券*/
            case COUPON:
                llGoPay.setVisibility(View.GONE);
                tvPrice.setVisibility(View.GONE);
                tvBottomTitle.setText(COUPON);
                sheetAdapter.setType(1);
//                sheetAdapter.setData(couponList);
                sheetAdapter.setSelectId(COUPON_ID);
                break;
            /*TODO 去支付*/
            case TOPAY:
                llGoPay.setVisibility(View.VISIBLE);
                tvPrice.setVisibility(View.VISIBLE);
                tvBottomTitle.setText(TOPAY);
                tvPrice.setText(nf.format(totalPrice));
                llGoPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (TOPAY_ID) {
                            case 0:
                                /*TODO 支付宝支付*/
                                code = "alipay";
                                break;
                            case 1:
                                /*TODO 微信支付*/
                                code = "wxpay";
                                break;
                            case 2:
                                /*TODO 余额支付*/
                                code = "money";
                                break;
                            default:
                                break;
                        }
                        payOrder(Api.MALL_PAYMENT_ORDERS);
                    }
                });
                sheetAdapter.setType(2);
                sheetAdapter.setData(payTypeList);
                sheetAdapter.setSelectId(TOPAY_ID);
                break;
        }


        sheetAdapter.setOnItemClickListener(new BottomSheetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (mode) {
                    /*TODO 选择优惠券*/
                    case COUPON:
                        COUPON_ID = position;
                        closeBottomsheet();
                        break;
                    /*TODO 去支付*/
                    case TOPAY:
                        TOPAY_ID = position;
                        break;
                }
                sheetAdapter.setSelectId(position);
            }
        });
        return view;
    }

    /**
     * 支付方式数据
     */
    private void setData() {
        /*支付类型*/
        payType = getResources().getStringArray(R.array.paytype);
        payTypeList = new ArrayList<>();
        for (int i = 0; i < payType.length; i++) {
            payTypeList.add(payType[i]);
        }
    }

    private void payOrder(String api) {
        JSONObject object = new JSONObject();
        try {
            /*订单ID*/
            object.put("orders_str", mOrderId);
            object.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrlWithBaseResponse(this, api, str, true, new OnRequestSuccess<BaseResponse<Data_WaiMai_PayOrder>>() {
            @Override
            public void onSuccess(String url, BaseResponse<Data_WaiMai_PayOrder> data) {
                super.onSuccess(url, data);
                if (data.error == 0) {
                    Data_WaiMai_PayOrder payOrder = data.data;
                    if (code.equals("money")) {
                        ToastUtil.show(data.message);
                        isPaySuccess = true;
                        enterOrderDetails();
                    } else {
                            /*处理微信、支付宝支付*/
                        PayOrder(code, payOrder);
                    }
                } else {
                    ToastUtil.show(data.message);
                }

            }
        });


    }

    /**
     * @param code wxpay alipay
     * @param data 支付需要的参数
     */
    private void PayOrder(String code, Data_WaiMai_PayOrder data) {
        DealWithPayOrder(code, data, new WaiMaiPay.OnPayListener() {
            @Override
            public void onFinish(boolean success) {
                isPaySuccess = success;
                enterOrderDetails();
            }
        });
    }

    /*微信支付成功*/
    @Subscribe
    public void onMessageEvent(RefreshEvent event) {
        if (event.getmMsg().equals("weixin_pay_success")) {
            isPaySuccess = true;
            enterOrderDetails();
        }
    }

    /**
     * 进入订单详情界面
     * 1:待付款
     * 2:待发货
     */
    private void enterOrderDetails() {
        Intent intent = new Intent(this, MallMyOrderActivity.class);
        if (isPaySuccess) {
            intent.putExtra(MallMyOrderActivity.TYPE, 2);
        } else {
            intent.putExtra(MallMyOrderActivity.TYPE, 1);
        }
        startActivity(intent);
        finish();
    }

    /**
     * BottomSheetLayout中标题，和去支付中的价格
     */
    private TextView tvBottomTitle, tvPrice;
    /**
     * BottomSheetLayout中的recycleview
     */
    private RecyclerView rlBottom;
    /**
     * BottomSheetLayout中去支付按钮
     */
    private LinearLayout llGoPay;
    /**
     * BottomSheetLayout中关闭图片按钮
     */
    private ImageView ivClose;

    private void findViews(View view) {
        tvBottomTitle = (TextView) view.findViewById(R.id.tv_bottomTitle);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        llGoPay = (LinearLayout) view.findViewById(R.id.ll_goPay);
        rlBottom = (RecyclerView) view.findViewById(R.id.rl_bottom);
        ivClose = (ImageView) view.findViewById(R.id.iv_close);
    }

    /**
     * 关闭弹窗
     */
    public void closeBottomsheet() {
        if (bottomsheet != null && bottomsheet.isSheetShowing()) {
            bottomsheet.dismissSheet();
        }
    }
}
