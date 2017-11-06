package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;
import com.classic.common.MultipleStatusView;
import com.coorchice.library.SuperTextView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.gson.Gson;
import com.jhcms.common.model.Data_WaiMai_ConfirmOrder;
import com.jhcms.common.model.Data_WaiMai_PayOrder;
import com.jhcms.common.model.Data_WaiMai_PreinfoOrder;
import com.jhcms.common.model.MyAddress;
import com.jhcms.common.model.RefreshEvent;
import com.jhcms.common.model.Response_WaiMai_ConfirmOrder;
import com.jhcms.common.model.Response_WaiMai_CreateOrder;
import com.jhcms.common.model.Response_WaiMai_PayOrder;
import com.jhcms.common.model.Response_WaiMai_PreinfoOrder;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.DividerListItemDecoration;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.utils.WaiMaiPay;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.BottomSheetAdapter;
import com.jhcms.waimaiV3.adapter.ServerTimeLeftAdapter;
import com.jhcms.waimaiV3.adapter.ServerTimeRightAdapter;
import com.jhcms.waimaiV3.fragment.WaiMai_HomeFragment;
import com.jhcms.waimaiV3.litepal.Shop;
import com.jhcms.waimaiV3.model.Goods;
import com.jhcms.waimaiV3.model.MessageEvent;
import com.jhcms.waimaiV3.model.PayMent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;
import static java.lang.Double.parseDouble;

/**
 * Created by wangyujie.
 * Date 2017/5/14
 * Time 15:09
 * TODO 处理确认订单
 */
public class ConfirmOrderActivity extends PayActivity implements OnRequestSuccessCallback {
    public static String SHOP_CART = "SHOP_CART";
    public static String SHOP_ID = "TYPE";
    public static String PEI_TYPE = "PEI_TYPE";
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
    @Bind(R.id.tv_service_time)
    TextView tvServiceTime;
    @Bind(R.id.ll_service_time)
    LinearLayout llServiceTime;
    @Bind(R.id.tv_payment_method)
    TextView tvPaymentMethod;
    @Bind(R.id.ll_payment_method)
    LinearLayout llPaymentMethod;
    @Bind(R.id.tv_red)
    TextView tvRed;
    @Bind(R.id.ll_red)
    LinearLayout llRed;
    @Bind(R.id.tv_coupon)
    TextView tvCoupon;
    @Bind(R.id.ll_coupon)
    LinearLayout llCoupon;
    @Bind(R.id.ll_allcomm)
    LinearLayout llAllcomm;
    @Bind(R.id.ll_delivery_fee)
    LinearLayout llDeliveryFee;
    @Bind(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;
    @Bind(R.id.ll_packing_fee)
    LinearLayout llPackingFee;
    @Bind(R.id.tv_packing_fee)
    TextView tvPackingFee;
    @Bind(R.id.tv_number_products)
    TextView tvNumberProducts;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.et_message)
    EditText etMessage;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.ll_shop_name)
    LinearLayout llShopName;
    @Bind(R.id.bottomsheet)
    BottomSheetLayout bottomsheet;
    @Bind(R.id.switchId)
    SwitchCompat switchId;
    @Bind(R.id.tv_ziti)
    TextView tvZiti;
    @Bind(R.id.tv_timeInfo)
    TextView tvTimeInfo;
    @Bind(R.id.ll_ziti)
    LinearLayout llZiti;
    @Bind(R.id.multiplestatusview)
    MultipleStatusView multiplestatusview;
    @Bind(R.id.ll_youhui)
    LinearLayout llYouhui;
    @Bind(R.id.ll_isziti)
    LinearLayout llIsZiti;
    /**
     * 送达时间BottomSheetLayout布局
     */
    private View serviceTimeView;
    /**
     * 支付方式、红包、优惠券、去支付BottomSheetLayout布局
     */
    private View payMentView;
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
    /**
     * BottomSheetLayout 中 adapter
     */
    private BottomSheetAdapter sheetAdapter;
    /**
     * 间隔线
     */
    private DividerListItemDecoration divider;
    private final String PAYMENTMETHOD = "选择支付方式";
    private final String REDENVELOPES = "选择红包";
    private final String COUPON = "选择优惠券";
    private final String TOPAY = "应支付金额";
    /*去支付*/
    private String[] payType;
    private List<String> payTypeList;
    /*支付方式*/
    private String[] payMent;
    private List<String> payMentList;
    /*优惠券*/
    private String[] coupon;
    /*选择优惠券数据*/
    private List<String> couponList;
    /*红包*/
    private String[] redEnvelopes;
    /*选择红包数据*/
    private List<String> redEnvelopesList;

    private int PAYMENTMETHOD_ID = 0;
    private int COUPON_ID = 0;
    private int TOPAY_ID = 0;
    private int REDENVELOPES_ID = 0;

    /**
     * 选择送达时间中关闭按钮
     */
    private ImageView ivServerTimeClose;
    /**
     * 选择送达时间中左边listview
     */
    private ListView leftList;
    /**
     * 选择送达时间中右边listview
     */
    private ListView rightList;
    private ServerTimeLeftAdapter leftAdapter;
    private ServerTimeRightAdapter rightAdapter;
    private int leftPosition = 0;
    private int rightPosition = 0;
    private List<String> leftTime;
    private List<String> rightTime;
    /*菜单中的 元素*/
    private TextView tvCommTitle, tvCommNum, tvCommPices;
    private List<Goods> arrayList;
    private String shop_id;
    private String spec_id;
    private Response_WaiMai_ConfirmOrder confirmOrder;
    private Data_WaiMai_ConfirmOrder data;
    private List<Data_WaiMai_ConfirmOrder.YouhuiEntity> youhui;
    private Data_WaiMai_ConfirmOrder.SetTimeDateEntity setTimeDate;
    private PayMent payMentInfo;
    /**
     * 是否选择自提
     */
    private boolean isZiTi = false;
    /**
     * 支付总金额
     */
    private String amount;
    /**
     * 是否是在线支付：0货到付款，1在线支付
     */
    private int online_pay;
    /**
     * 优惠券ID:-1不使用，0默认
     */
    private int coupon_id;
    /**
     * 红包ID:-1不使用，0默认
     */
    private int hongbao_id;
    /**
     * 配送方式:0商家，1平台，3自提
     */
    private String pei_type;
    /**
     * 保存商家详情界面传过来的 pei_type
     */
    private String peiType;
    /**
     * 订单备注
     */
    private String note;
    /**
     * 购物车数据
     */
    private String products;

    /**
     * 配送时间：Y-m-d H:i:s
     */
    private String pei_time;
    private List<Data_WaiMai_ConfirmOrder.DayDatesEntity> day_dates;
    private Response_WaiMai_CreateOrder createOrder;
    /**
     * 订单Id
     */
    private String order_id;
    /**
     * 支付标志
     */
    private String code;
    /**
     * 地址ID
     */
    private String addr_id;
    /**
     * 是否自提:0不自提，1自提
     */
    private int is_ziti;
    /**
     * 商品的总价
     */
    private String product_price;
    private Response_WaiMai_PreinfoOrder preinfoOrder;
    private Data_WaiMai_PreinfoOrder preinfoData;
    String product_id;
    private int REQUEST_ADDRESS = 0x111;
    private String shop_lng;
    private String shop_lat;
    private NumberFormat nf;
    /**
     * 红包金额
     */
    private String hongbaoAmount = "0";

    /**
     * 优惠券金额
     */
    private String couponAmount = "0";
    /**
     * 优惠金额
     */
    private double youhuiAmount = 0;
    private List<Data_WaiMai_ConfirmOrder.HongbaosEntity> hongbaos;
    private double tobepaid;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void initData() {
        Intent intent = getIntent();
        arrayList = (List<Goods>) intent.getSerializableExtra(SHOP_CART);
        shop_id = intent.getStringExtra(SHOP_ID);
        peiType = pei_type = intent.getStringExtra(PEI_TYPE);

         /*getCurrencyInstance 返回当前缺省语言环境的通用格式*/
        nf = NumberFormat.getCurrencyInstance();
        /*设置数值的小数部分允许的最大位数为2*/
        nf.setMaximumFractionDigits(2);
        setData();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTitle.setText("确认订单");
        switchId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                /*TODO 自提*/
                if (isChecked) {
                    isZiTi = true;
                    tvTimeInfo.setText("自提时间");
                    llAddress.setVisibility(View.GONE);
                    pei_type = "3";
                    llZiti.setVisibility(View.VISIBLE);
                    is_ziti = 1;
                } else {
                    isZiTi = false;
                    tvTimeInfo.setText("送达时间");
                    pei_type = peiType;
                    llAddress.setVisibility(View.VISIBLE);
                    llZiti.setVisibility(View.GONE);
                    is_ziti = 0;
                }
                if (leftPosition == 0) {
                    tvServiceTime.setText(getRightTime(leftPosition).get(rightPosition));
                } else {
                    tvServiceTime.setText(leftTime.get(leftPosition) + getRightTime(leftPosition).get(rightPosition));
                }
                preinfoOrder(Api.WAIMAI_SHOP_ORDER_PREINFO);
            }
        });
        divider = new DividerListItemDecoration.Builder(this)
                .setHeight(R.dimen.dp_1)
                .setColorResource(R.color.background)
                .build();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arrayList.size(); i++) {
            Goods goods = arrayList.get(i);
            int count = goods.count;

            if (goods.is_spec.equals("0")) {
                spec_id = "0";
                product_id = goods.product_id;
            } else {
                spec_id = goods.spec_id;
                product_id = goods.productId;
            }
            sb.append(product_id + ":" + spec_id + ":" + count);
            if (i != arrayList.size() - 1) {
                sb.append(",");
            }
        }
        products = sb.toString();
        requestOrder(Api.WAIMAI_SHOP_PLACE_AN_ORDER, shop_id, products);
    }


    private void initAllComm(List<Data_WaiMai_ConfirmOrder.ProductListsEntity> product_lists) {
        llAllcomm.removeAllViews();
        for (int i = 0; i < product_lists.size(); i++) {
            Data_WaiMai_ConfirmOrder.ProductListsEntity product = product_lists.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.confirm_menu, null);
            tvCommTitle = (TextView) view.findViewById(R.id.tv_comm_title);
            tvCommNum = (TextView) view.findViewById(R.id.tv_comm_num);
            tvCommPices = (TextView) view.findViewById(R.id.tv_comm_pices);
            if (TextUtils.isEmpty(product.spec_name)) {
                tvCommTitle.setText(product.title);
            } else {
                tvCommTitle.setText(product.title + "(" + product.spec_name + ")");
            }
            tvCommNum.setText("X" + product.num);
            tvCommPices.setText(nf.format(parseDouble(product.price)));
            llAllcomm.addView(view);
        }
    }

    private void requestOrder(String api, String shop_id, String product) {
        JSONObject object = new JSONObject();
        try {
            object.put("shop_id", shop_id);
            object.put("products", product);
            object.put("lng", Api.LON);
            object.put("lat", Api.LAT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, api, str, false, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                /*TODO 进入确认订单界面第一个请求的接口*/
//                try {
                    Gson gson = new Gson();
                    confirmOrder = gson.fromJson(Json, Response_WaiMai_ConfirmOrder.class);
                    if (confirmOrder.error.equals("0")) {
                        data = confirmOrder.data;
                        if (data.is_ziti == 0) {
                            llIsZiti.setVisibility(View.GONE);
                            switchId.setEnabled(false);
                        } else {
                            llIsZiti.setVisibility(View.VISIBLE);
                            switchId.setEnabled(true);
                        }
                        /*用户的收货地址信息*/
                        Data_WaiMai_ConfirmOrder.MAddrEntity mAddr = data.m_addr;

                        addr_id = mAddr.addr_id;
                        if (!addr_id.equals("0")) {
                            tvUserAddress.setVisibility(View.VISIBLE);
                            tvUserInfo.setText(String.format(getString(R.string.收货人info), mAddr.contact, mAddr.mobile));
                            tvUserAddress.setText(String.format(getString(R.string.收货地址info), mAddr.addr + mAddr.house));
                        } else {
                            tvUserInfo.setText("请选择收货地址");
                            tvUserAddress.setVisibility(View.GONE);
                        }
                    /*自提地址*/
                        tvZiti.setText(mAddr.addr + mAddr.house);
                    /*商家信息*/
                        tvShopName.setText(data.shop_title);
                    /*商品菜单*/
                        initAllComm(data.product_lists);
                    /*配送费*/
                        llDeliveryFee.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(data.freight_stage) && Double.parseDouble(data.freight_stage) > 0) {
                            tvDeliveryFee.setText(nf.format(parseDouble(data.freight_stage)));
                        } else {
                            tvDeliveryFee.setText("免配送费");
                        }
                    /*餐盒打包费*/
                        llPackingFee.setVisibility(View.VISIBLE);
                        tvPackingFee.setText(nf.format(Double.parseDouble(data.package_price)));
                    /*商品个数*/
                        tvNumberProducts.setText(String.format(getString(R.string.商品个数), data.product_number));
                    /*商品的总价*/
                        product_price = data.product_price;
                    /*支付金额*/
                        amount = data.amount;
                    /*待支付*/
                        tvTobePaid.setText(nf.format(Double.parseDouble(amount)));
                    /*合计*/
                        tvTotal.setText(nf.format(Double.parseDouble(amount)));
                    /*满减优惠信息*/
                        youhui = data.youhui;
                        if (null != youhui && youhui.size() > 0) {
                            llYouhui.setVisibility(View.VISIBLE);
                            initYouhui(youhui);
                        } else {
                            llYouhui.setVisibility(View.GONE);
                        }
                    /*送达时间*/
                        day_dates = data.day_dates;
                        setTimeDate = data.set_time_date;
                        initTime(day_dates);

                        pei_time = "0";
                    /*支付方式*/
                        payMentInfo = new PayMent(data.online_pay, data.is_daofu);
                        if (data.online_pay.equals("1")) {
                            tvPaymentMethod.setText(payMentList.get(PAYMENTMETHOD_ID));
                            online_pay = 1;
                        } else if (data.is_daofu.equals("1")) {
                            PAYMENTMETHOD_ID = 1;
                            tvPaymentMethod.setText(payMentList.get(PAYMENTMETHOD_ID));
                            online_pay = 0;
                        }
                    /*红包*/
                        if (null != data.hongbaos && data.hongbaos.size() > 0) {
                            hongbaos = data.hongbaos;
                            llRed.setClickable(true);
                            hongbao_id = data.hongbao_id;
                            hongbaoAmount = data.hongbao_amount;
                            tvRed.setText("-￥" + hongbaoAmount);
                            redEnvelopesList = new ArrayList<>();
                            for (int i = 0; i < data.hongbaos.size() + 1; i++) {
                                if (i == data.hongbaos.size()) {
                                    redEnvelopesList.add(getString(R.string.不使用红包));
                                } else {
                                    redEnvelopesList.add(String.format(getString(R.string.选择红包), data.hongbaos.get(i).amount, data.hongbaos.get(i).min_amount));
                                }
                            }
                        } else {
                            hongbao_id = -1;
                            tvRed.setText("暂无可用红包");
                            tvRed.setTextColor(getResources().getColor(R.color.third_txt_color));
                            llRed.setClickable(false);
                        }
                        /**
                         * 优惠券
                         * 优惠券coupon_id:-1不使用，0默认
                         * */

                        if (null != data.coupons && data.coupons.size() > 0) {
                            llCoupon.setClickable(true);
                            coupon_id = data.coupon_id;
                            couponAmount = data.coupon_amount;
                            tvCoupon.setText("-￥" + couponAmount);
                            couponList = new ArrayList<>();
                            for (int i = 0; i < data.coupons.size() + 1; i++) {
                                if (i == data.coupons.size()) {
                                    couponList.add(getString(R.string.不使用优惠券));
                                } else {
                                    couponList.add(String.format(getString(R.string.选择优惠券), data.coupons.get(i).coupon_amount, data.coupons.get(i).order_amount));
                                }
                            }
                        } else {
                            coupon_id = -1;
                            tvCoupon.setText("暂无可用优惠券");
                            tvCoupon.setTextColor(getResources().getColor(R.color.third_txt_color));
                            llCoupon.setClickable(false);
                        }
                        multiplestatusview.showContent();
                    } else {
                        ToastUtil.show(confirmOrder.message);
                        multiplestatusview.showError();
                    }

//                } catch (Exception e) {
//                    ToastUtil.show(getString(R.string.数据解析异常));
//                    multiplestatusview.showError();
//                    saveCrashInfo2File(e);
//                }
            }

            @Override
            public void onBeforeAnimate() {
                multiplestatusview.showLoading();

            }

            @Override
            public void onErrorAnimate() {
                multiplestatusview.showError();

            }
        });
    }


    /**
     * 模拟数据
     */
    private void setData() {
        /*支付类型*/
        payType = getResources().getStringArray(R.array.paytype);
        payTypeList = new ArrayList<>();
        for (int i = 0; i < payType.length; i++) {
            payTypeList.add(payType[i]);
        }
        /*支付方式*/
        payMent = getResources().getStringArray(R.array.payment);
        payMentList = new ArrayList<>();
        for (int j = 0; j < payMent.length; j++) {
            payMentList.add(payMent[j]);
        }

    }

    @OnClick({R.id.tv_toPay, R.id.ll_address, R.id.ll_ziti, R.id.ll_service_time, R.id.ll_payment_method, R.id.ll_red, R.id.ll_shop_name, R.id.ll_coupon})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_address:
                intent.setClass(this, ConsigneeAddressActivity.class);
                intent.putExtra("addr_id", addr_id);
                intent.putExtra("shop_id", shop_id);
                startActivityForResult(intent, REQUEST_ADDRESS);
                break;
            case R.id.ll_ziti:
                if (MyApplication.MAP.equals(Api.GAODE)) {
                    CoordinateConverter converter = new CoordinateConverter(this);
                    // CoordType.GPS 待转换坐标类型
                    converter.from(CoordinateConverter.CoordType.BAIDU);
                    // sourceLatLng待转换坐标点 LatLng类型
                    converter.coord(new LatLng(parseDouble(shop_lat), parseDouble(shop_lng)));
                    // 执行转换操作
                    LatLng desLatLng = converter.convert();
                    intent.setClass(this, ShopMapActivity.class);
                    intent.putExtra("lat", desLatLng.latitude);
                    intent.putExtra("lng", desLatLng.longitude);
                } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                    intent.setClass(this, ShopMapActivityGMS.class);
                    intent.putExtra("lat", Double.parseDouble(shop_lat));
                    intent.putExtra("lng", Double.parseDouble(shop_lng));
                    intent.putExtra("address", preinfoData.shop_addr);
                }
                startActivity(intent);
                break;
            //TODO 送达时间
            case R.id.ll_service_time:
                showServiceTime();
                break;
            //TODO 支付方式
            case R.id.ll_payment_method:
                showPayMent(PAYMENTMETHOD);
                break;
            case R.id.ll_red://TODO 红包
                showPayMent(REDENVELOPES);
                break;
            case R.id.ll_shop_name:
//                ToastUtil.show("去店铺");
                break;
            case R.id.ll_coupon:
                showPayMent(COUPON);
                break;
            case R.id.tv_toPay:
                /*判断用户是否点击送达、自提时间*/
                if (null == pei_time) {
                    if (isZiTi) {
                        ToastUtil.show("请选择自提时间");
                    } else {
                        ToastUtil.show("请选择送达时间");
                    }
                    showServiceTime();
                    return;
                }
                if (TextUtils.isEmpty(tvPaymentMethod.getText().toString())) {
                    ToastUtil.show("请选择支付方式");
                    showPayMent(PAYMENTMETHOD);
                    return;
                }
                note = etMessage.getText().toString().trim();
                CreateOrder(note);

                break;
        }
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
                            /*TODO 选择支付方式*/
                            case PAYMENTMETHOD:
                                tvPaymentMethod.setText(payMentList.get(PAYMENTMETHOD_ID));
                                /*TODO 在线支付*/
                                if (PAYMENTMETHOD_ID == 0) {
                                    online_pay = 1;
                                } else if (PAYMENTMETHOD_ID == 1) {
                                    online_pay = 0;
                                }
                                preinfoOrder(Api.WAIMAI_SHOP_ORDER_PREINFO);
                                break;
                            /*TODO 优惠券*/
                            case COUPON:
                                if (COUPON_ID == data.coupons.size()) {
                                    coupon_id = -1;
                                } else {
                                    coupon_id = data.coupons.get(COUPON_ID).coupon_id;
                                }
                                preinfoOrder(Api.WAIMAI_SHOP_ORDER_PREINFO);
                                break;
                            /*TODO 红包*/
                            case REDENVELOPES:
                                /*如果REDENVELOPES_ID == 选择红包的个数 则不使用红包*/
                                if (REDENVELOPES_ID == hongbaos.size()) {
                                    hongbao_id = -1;
                                } else {
                                    hongbao_id = hongbaos.get(REDENVELOPES_ID).hongbao_id;
                                }
                                preinfoOrder(Api.WAIMAI_SHOP_ORDER_PREINFO);
                                break;
                            /*TODO 去支付*/
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


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
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
            /*TODO 支付方式*/
            case PAYMENTMETHOD:
                llGoPay.setVisibility(View.GONE);
                tvPrice.setVisibility(View.GONE);
                tvBottomTitle.setText(PAYMENTMETHOD);
                sheetAdapter.setType(0);
                sheetAdapter.setData(payMentList);
                sheetAdapter.setPayMent(payMentInfo);
                sheetAdapter.setSelectId(PAYMENTMETHOD_ID);
                break;
            /*TODO 红包*/
            case REDENVELOPES:
                llGoPay.setVisibility(View.GONE);
                tvPrice.setVisibility(View.GONE);
                tvBottomTitle.setText(REDENVELOPES);
                sheetAdapter.setType(1);
                sheetAdapter.setData(redEnvelopesList);
                sheetAdapter.setSelectId(REDENVELOPES_ID);
                break;
            /*TODO 选择优惠券*/
            case COUPON:
                llGoPay.setVisibility(View.GONE);
                tvPrice.setVisibility(View.GONE);
                tvBottomTitle.setText(COUPON);
                sheetAdapter.setType(1);
                sheetAdapter.setData(couponList);
                sheetAdapter.setSelectId(COUPON_ID);
                break;
            /*TODO 去支付*/
            case TOPAY:
                llGoPay.setVisibility(View.VISIBLE);
                tvPrice.setVisibility(View.VISIBLE);
                tvBottomTitle.setText(TOPAY);
                tvPrice.setText(nf.format(parseDouble(amount)));
                llGoPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (TOPAY_ID) {
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
                            default:
                                break;
                        }
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
                    /*TODO 支付方式*/
                    case PAYMENTMETHOD:
                        PAYMENTMETHOD_ID = position;
                        closeBottomsheet();
                        break;
                    /*TODO 红包*/
                    case REDENVELOPES:
                        REDENVELOPES_ID = position;
                        closeBottomsheet();
                        break;
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
     * TODO 订单预处理
     * shop_id	        是	int	商家ID
     * addr_id	        是	int	用户的地址ID
     * coupon_id	    是	int	优惠券ID:-1不使用，0默认
     * hongbao_id	    是	int	红包ID:-1不使用，0默认
     * is_ziti	        是	int	是否自提:0不自提，1自提
     * product_price	是	float	商品的总价
     * total_price	    是	float	订单总金额
     */

    private void preinfoOrder(String api) {
        JSONObject object = new JSONObject();
        try {
            /*订单ID*/
            object.put("shop_id", shop_id);
            object.put("addr_id", addr_id);
            object.put("coupon_id", coupon_id);
            object.put("hongbao_id", hongbao_id);
            object.put("is_ziti", is_ziti);
            object.put("products", products);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, api, str, true, this);
    }

    /**
     * TODO 支付接口
     *
     * @param api
     */
    private void payOrder(String api) {
        JSONObject object = new JSONObject();
        try {
            /*订单ID*/
            object.put("order_id", order_id);
            object.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, api, str, true, this);
    }

    /**
     * @param code wxpay alipay
     * @param data 支付需要的参数
     */
    private void PayOrder(String code, Data_WaiMai_PayOrder data) {
        DealWithPayOrder(code, data, new WaiMaiPay.OnPayListener() {
            @Override
            public void onFinish(boolean success) {
                enterOrderDetails();

            }
        });
    }

    /*微信支付成功*/
    @Subscribe
    public void onMessageEvent(RefreshEvent event) {
        if (event.getmMsg().equals("weixin_pay_success")) {
            enterOrderDetails();
        }
    }


    /**
     * TODO 创建订单
     *
     * @param intro
     */
    private void CreateOrder(String intro) {
        JSONObject object = new JSONObject();
        try {
            /*商家ID*/
            object.put("shop_id", shop_id);
            /*用户的地址ID*/
            object.put("addr_id", addr_id);
            /*优惠券ID:-1不使用，0默认*/
            object.put("coupon_id", coupon_id);
            /*红包ID:-1不使用，0默认，其他为hongbaoid*/
            object.put("hongbao_id", hongbao_id);
            /*配送方式:0商家，1平台，3自提*/
            object.put("pei_type", pei_type);
            /*商品的总价*/
            object.put("product_price", data.product_price);
            /*订单总金额*/
            object.put("total_price", amount);
            /*配送时间：Y-m-d H:i:s*/
            object.put("pei_time", pei_time);
            /*是否是在线支付：0货到付款，1在线支付*/
            object.put("online_pay", online_pay);
            /*购物车数据*/
            object.put("products", products);
            /*订单备注*/
            object.put("intro", intro);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, Api.WAIMAI_SHOP_ORDER_CREATE, str, true, this);
    }

    private void findViews(View view) {
        tvBottomTitle = (TextView) view.findViewById(R.id.tv_bottomTitle);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        llGoPay = (LinearLayout) view.findViewById(R.id.ll_goPay);
        rlBottom = (RecyclerView) view.findViewById(R.id.rl_bottom);
        ivClose = (ImageView) view.findViewById(R.id.iv_close);
    }

    private void showServiceTime() {
        if (serviceTimeView == null) {
            serviceTimeView = createServiceTimeView();
        }
        if (bottomsheet.isSheetShowing()) {
            bottomsheet.setInterceptContentTouch(true);
            bottomsheet.dismissSheet();
        } else {
            bottomsheet.showWithSheetView(serviceTimeView);
            bottomsheet.addOnSheetStateChangeListener(new BottomSheetLayout.OnSheetStateChangeListener() {
                @Override
                public void onSheetStateChanged(BottomSheetLayout.State state) {
                        /*formatLayout 消失*/
                    if (state.toString().equals("HIDDEN")) {

                        if (leftPosition == 0) {
                            tvServiceTime.setText(getRightTime(leftPosition).get(rightPosition));
                        } else {
                            tvServiceTime.setText(leftTime.get(leftPosition) + getRightTime(leftPosition).get(rightPosition));
                        }
                        if (leftPosition == 0 && rightPosition == 0) {
                            pei_time = "0";
                        } else {
                            pei_time = Utils.convertTime(day_dates.get(leftPosition).date + " " + getRightTime(leftPosition).get(rightPosition));
                        }
                    } else if (state.toString().equals("PEEKED")) {
                            /*formatLayout 展开*/
                    }
                }
            });
        }
    }

    private View createServiceTimeView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_srvice_time_sheet, (ViewGroup) getWindow().getDecorView(), false);
        ivServerTimeClose = (ImageView) view.findViewById(R.id.iv_close);
        leftList = (ListView) view.findViewById(R.id.left_list);
        rightList = (ListView) view.findViewById(R.id.right_list);
        ivServerTimeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeBottomsheet();
            }
        });

        leftAdapter = new ServerTimeLeftAdapter(this);
        rightAdapter = new ServerTimeRightAdapter(this);

        leftList.setAdapter(leftAdapter);
        leftAdapter.setData(leftTime);
        leftAdapter.setPosition(leftPosition);
        rightList.setAdapter(rightAdapter);
        rightAdapter.setData(getRightTime(leftPosition));
        rightAdapter.setPosition(rightPosition);

        leftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                rightPosition = 0;
                rightAdapter.setPosition(0);
                leftPosition = position;
                leftAdapter.setPosition(leftPosition);
                rightAdapter.setData(getRightTime(position));
                rightList.setSelection(0);
            }
        });
        rightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                rightPosition = position;
                rightAdapter.setPosition(rightPosition);
                closeBottomsheet();
//                ToastUtil.show(getRightTime(leftPosition).get(rightPosition));
            }
        });


        return view;
    }

    /**
     * 关闭弹窗
     */
    public void closeBottomsheet() {
        if (bottomsheet != null && bottomsheet.isSheetShowing()) {
            bottomsheet.dismissSheet();
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
                        Data_WaiMai_PayOrder data = payOrder.data;
                        if (code.equals("money")) {
                            ToastUtil.show(payOrder.message);
                            enterOrderDetails();
                        } else {
                            /*处理微信、支付宝支付*/
                            PayOrder(code, data);
                        }
                    } else {
                        ToastUtil.show(payOrder.message);
                    }

                } catch (Exception e) {
                    ToastUtil.show(getString(R.string.数据解析异常));
                    saveCrashInfo2File(e);
                } finally {
                    ProgressDialogUtil.dismiss(ConfirmOrderActivity.this);
                }
                break;
            case Api.WAIMAI_SHOP_ORDER_CREATE:
                try {
                    Gson gson = new Gson();
                    createOrder = gson.fromJson(Json, Response_WaiMai_CreateOrder.class);
                    if (createOrder.error.equals("0")) {
                        order_id = createOrder.data.order_id;
                        if (online_pay == 0) {
                            enterOrderDetails();
                        } else {
                            showPayMent(TOPAY);
                        }
                    } else {
                        ToastUtil.show(createOrder.message);
                    }
                } catch (Exception e) {
                    ToastUtil.show(getString(R.string.数据解析异常));
                    saveCrashInfo2File(e);
                } finally {
                    ProgressDialogUtil.dismiss(ConfirmOrderActivity.this);
                }
                break;
            case Api.WAIMAI_SHOP_ORDER_PREINFO:
                try {
                    Gson gson = new Gson();
                    preinfoOrder = gson.fromJson(Json, Response_WaiMai_PreinfoOrder.class);
                    if (preinfoOrder.error.equals("0")) {
                        preinfoData = preinfoOrder.data;
                        if (preinfoData.is_ziti == 0) {
                            switchId.setEnabled(false);
                        } else {
                            switchId.setEnabled(true);
                        }
                        /*自提地址*/
                        tvZiti.setText(preinfoData.shop_addr);
                        /*商家经纬度*/
                        shop_lng = preinfoData.shop_lng;
                        shop_lat = preinfoData.shop_lat;
                        /*商家信息*/
                        tvShopName.setText(preinfoData.shop_title);
                        /*商品菜单*/
                        initAllComm(preinfoData.product_lists);
                        /*配送费*/
                        if (is_ziti == 0) {
                            llDeliveryFee.setVisibility(View.VISIBLE);
                            if (!TextUtils.isEmpty(preinfoData.freight_stage) && parseDouble(preinfoData.freight_stage) > 0) {
                                tvDeliveryFee.setText(nf.format(parseDouble(preinfoData.freight_stage)));
                            } else {
                                tvDeliveryFee.setText("免配送费");
                            }
                        } else {
                            llDeliveryFee.setVisibility(View.GONE);
                        }

                        /*餐盒打包费*/
                        llPackingFee.setVisibility(View.VISIBLE);
                        tvPackingFee.setText(nf.format(parseDouble(preinfoData.package_price)));

                        /*商品个数*/
                        tvNumberProducts.setText(String.format(getString(R.string.商品个数), preinfoData.product_number));
                        /*商品的总价*/
                        product_price = preinfoData.product_price;
                        if (online_pay == 1) {
                            llRed.setVisibility(View.VISIBLE);
                            llCoupon.setVisibility(View.VISIBLE);
                            /*满减优惠信息*/
                            if (null != youhui && youhui.size() > 0) {
                                llYouhui.setVisibility(View.VISIBLE);
                                initYouhui(youhui);
                            } else {
                                llYouhui.setVisibility(View.GONE);
                            }
                             /*支付金额*/
                            amount = preinfoData.amount;
                        } else if (online_pay == 0) {
                            llRed.setVisibility(View.GONE);
                            llCoupon.setVisibility(View.GONE);
                            llYouhui.setVisibility(View.GONE);
                            amount = String.valueOf(Double.parseDouble(preinfoData.amount) + Double.parseDouble(hongbaoAmount) + Double.parseDouble(couponAmount) + youhuiAmount);
                        }
                         /*待支付*/
                        tvTobePaid.setText(nf.format(Double.parseDouble(amount)));
                        /*合计*/
                        tvTotal.setText(nf.format(Double.parseDouble(amount)));
                        /*支付方式*/
                        payMentInfo = new PayMent(preinfoData.online_pay, preinfoData.is_daofu);
                        /*红包*/
                        if (null != preinfoData.hongbaos && preinfoData.hongbaos.size() > 0) {
                            hongbaos = preinfoData.hongbaos;
                            llRed.setClickable(true);
                            hongbaoAmount = preinfoData.hongbao_amount;
                            if (parseDouble(hongbaoAmount) > 0) {
                                tvRed.setText("-￥" + hongbaoAmount);
                            } else {
                                tvRed.setText("不使用红包");
                            }
                            redEnvelopesList = new ArrayList<>();
                            for (int i = 0; i < preinfoData.hongbaos.size() + 1; i++) {
                                if (i == preinfoData.hongbaos.size()) {
                                    redEnvelopesList.add(getString(R.string.不使用红包));
                                } else {
                                    redEnvelopesList.add(String.format(getString(R.string.选择红包), preinfoData.hongbaos.get(i).amount, preinfoData.hongbaos.get(i).min_amount));
                                }
                            }
                        } else {
                            hongbao_id = -1;
                            tvRed.setText("暂无可用红包");
                            tvRed.setTextColor(getResources().getColor(R.color.third_txt_color));
                            llRed.setClickable(false);
                        }
                        /**
                         * 优惠券
                         * 优惠券coupon_id:-1不使用，0默认
                         * */

                        if (null != preinfoData.coupons && preinfoData.coupons.size() > 0) {
                            llCoupon.setClickable(true);
                            couponAmount = preinfoData.coupon_amount;
                            if (parseDouble(couponAmount) > 0) {
                                tvCoupon.setText("-￥" + couponAmount);
                            } else {
                                tvCoupon.setText("不是用优惠券");
                            }
                            couponList = new ArrayList<>();
                            for (int i = 0; i < preinfoData.coupons.size() + 1; i++) {
                                if (i == preinfoData.coupons.size()) {
                                    couponList.add(getString(R.string.不使用优惠券));
                                } else {
                                    couponList.add(String.format(getString(R.string.选择优惠券), preinfoData.coupons.get(i).coupon_amount, preinfoData.coupons.get(i).order_amount));
                                }
                            }
                        } else {
                            coupon_id = -1;
                            tvCoupon.setText("暂无可用优惠券");
                            tvCoupon.setTextColor(getResources().getColor(R.color.third_txt_color));
                            llCoupon.setClickable(false);
                        }
                    } else {
                        ToastUtil.show(preinfoOrder.message);
                    }
                } catch (Exception e) {
                    ToastUtil.show(getString(R.string.数据解析异常));
                    saveCrashInfo2File(e);
                } finally {
                    ProgressDialogUtil.dismiss(ConfirmOrderActivity.this);
                }
                break;
        }
    }

    /**
     * 进入订单详情界面
     */
    private void enterOrderDetails() {
        Intent intent = new Intent();
        if (MyApplication.MAP.equals(Api.GAODE)) {
            intent.setClass(ConfirmOrderActivity.this, OrderDetailsActivity.class);
            intent.putExtra(OrderDetailsActivity.ORDER_ID, order_id);
        } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
            intent.setClass(ConfirmOrderActivity.this, OrderDetailsGMSActivity.class);
            intent.putExtra(OrderDetailsGMSActivity.ORDER_ID, order_id);
        }
        startActivity(intent);
        /**
         * 删除对应店铺中购物车信息
         * */
        DataSupport.deleteAll(Shop.class, "shop_id = ? ", shop_id);
        EventBus.getDefault().post(new MessageEvent(WaiMai_HomeFragment.REFRESH_SHOPITEM));
        finish();
    }

    /**
     * 获取左侧时间列表
     *
     * @param day_dates
     */
    private void initTime(List<Data_WaiMai_ConfirmOrder.DayDatesEntity> day_dates) {
        leftTime = new ArrayList<>();
        for (int i = 0; i < day_dates.size(); i++) {
            leftTime.add(day_dates.get(i).day);
        }
        if (leftPosition == 0) {
            tvServiceTime.setText(getRightTime(leftPosition).get(rightPosition));
        } else {
            tvServiceTime.setText(leftTime.get(leftPosition) + getRightTime(leftPosition).get(rightPosition));
        }

    }

    /**
     * 获取右侧的时间列表
     *
     * @param leftPosition
     * @return
     */
    public List<String> getRightTime(int leftPosition) {
        rightTime = new ArrayList<>();
        /*当点击当天时间是 获取的是当天可选时间*/
        if (leftPosition == 0) {
            if (setTimeDate.set_time.size() > 0) {
                for (int i = 0; i < setTimeDate.set_time.size(); i++) {
                    if (i == 0) {
                        if (isZiTi) {
                            rightTime.add("立即自提");
                        } else {
                            rightTime.add("尽快送达");
                        }
                    } else {
                        rightTime.add(setTimeDate.set_time.get(i));
                    }
                }
            } else {
                if (isZiTi) {
                    rightTime.add("立即自提");
                } else {
                    rightTime.add("尽快送达");
                }
            }

        } else {
            for (int i = 0; i < setTimeDate.nomal_time.size(); i++) {
                rightTime.add(setTimeDate.nomal_time.get(i));
            }
        }
        return rightTime;
    }

    private SuperTextView tvJian;
    private TextView tvJianinfo;
    private TextView tvJianprices;

    private void initYouhui(List<Data_WaiMai_ConfirmOrder.YouhuiEntity> youhui) {
        llYouhui.removeAllViews();
        youhuiAmount = 0;
        for (int i = 0; i < youhui.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.confir_youhui, null);
            tvJian = (SuperTextView) view.findViewById(R.id.tv_jian);
            tvJianinfo = (TextView) view.findViewById(R.id.tv_jianinfo);
            tvJianprices = (TextView) view.findViewById(R.id.tv_jianprices);

            Data_WaiMai_ConfirmOrder.YouhuiEntity youhuiEntity = youhui.get(i);
            tvJian.setText(youhuiEntity.word);
            tvJian.setSolid(Color.parseColor("#" + youhuiEntity.color));
            tvJianinfo.setText(youhuiEntity.title);
            tvJianprices.setText("-￥" + youhuiEntity.amount);
            llYouhui.addView(view);
            youhuiAmount += Double.parseDouble(youhuiEntity.amount);
        }
    }

    @Override
    public void onBeforeAnimate() {
    }

    @Override
    public void onErrorAnimate() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ADDRESS && null != data) {
                MyAddress address = (MyAddress) data.getSerializableExtra("address");
                addr_id = address.addr_id;
                if (!addr_id.equals("0")) {
                    tvUserAddress.setVisibility(View.VISIBLE);
                    tvUserInfo.setText(String.format(getString(R.string.收货人info), address.contact, address.mobile));
                    tvUserAddress.setText(String.format(getString(R.string.收货地址info), address.addr + address.house));
                    preinfoOrder(Api.WAIMAI_SHOP_ORDER_PREINFO);
                } else {
                    tvUserInfo.setText("请选择收货地址");
                    tvUserAddress.setVisibility(View.GONE);
                }
            }
        }
    }
}
