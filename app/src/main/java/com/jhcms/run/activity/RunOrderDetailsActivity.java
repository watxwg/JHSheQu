package com.jhcms.run.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.classic.common.MultipleStatusView;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.run.adapter.RunOrderStatusAdapter;
import com.jhcms.run.mode.Data_Run_Order_Details;
import com.jhcms.run.mode.Data_Run_Order_UnDone;
import com.jhcms.run.utils.RunOrderStatus;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.ToPayNewActivity;
import com.jhcms.waimaiV3.dialog.CallPhoneDialog;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;


public class RunOrderDetailsActivity extends AppCompatActivity {

    private static String ORDER_ID = "ORDER_ID";
    /**
     * 拨打骑手电话标记
     */
    public static final String CALL_STAFF = "CALL_STAFF";
    /**
     * 拨打客服电话标记
     */
    public static final String CALL_CSD = "CALL_CSD";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ll_status_to_be_paid)
    LinearLayout llStatusToBePaid;
    @Bind(R.id.iv_order_status)
    ImageView ivOrderStatus;
    @Bind(R.id.tv_order_status)
    TextView tvOrderStatus;
    @Bind(R.id.tv_staff)
    TextView tvStaff;
    @Bind(R.id.ll_staff)
    LinearLayout llStaff;
    @Bind(R.id.map_staff)
    MapView mapStaff;
    @Bind(R.id.rl_staff_info)
    RelativeLayout rlStaffInfo;
    @Bind(R.id.tv_gou_addr)
    TextView tvGouAddr;
    @Bind(R.id.tv_fa_addr)
    TextView tvFaAddr;
    @Bind(R.id.ll_order)
    LinearLayout llOrder;
    @Bind(R.id.ll_order_article)
    LinearLayout llOrderArticle;
    @Bind(R.id.tv_delivery_prices)
    TextView tvDeliveryPrices;
    @Bind(R.id.tv_tip)
    TextView tvTip;
    @Bind(R.id.ll_tip)
    LinearLayout llTip;
    @Bind(R.id.tv_red)
    TextView tvRed;
    @Bind(R.id.ll_red)
    LinearLayout llRed;
    @Bind(R.id.tv_actually_paid)
    TextView tvActuallyPaid;
    @Bind(R.id.tv_delivery_time)
    TextView tvDeliveryTime;
    @Bind(R.id.ll_delivery_time)
    LinearLayout llDeliveryTime;
    @Bind(R.id.tv_distance)
    TextView tvDistance;
    @Bind(R.id.ll_distance)
    LinearLayout llDistance;
    @Bind(R.id.tv_rider)
    TextView tvRider;
    @Bind(R.id.tv_rider_phone)
    TextView tvRiderPhone;
    @Bind(R.id.ll_rider)
    LinearLayout llRider;
    @Bind(R.id.ll_distribution)
    LinearLayout llDistribution;
    @Bind(R.id.tv_order_id)
    TextView tvOrderId;
    @Bind(R.id.ll_order_id)
    LinearLayout llOrderId;
    @Bind(R.id.tv_order_time)
    TextView tvOrderTime;
    @Bind(R.id.ll_order_time)
    LinearLayout llOrderTime;
    @Bind(R.id.tv_payment)
    TextView tvPayment;
    @Bind(R.id.ll_payment)
    LinearLayout llPayment;
    @Bind(R.id.ll_information)
    LinearLayout llInformation;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.iv_call)
    ImageView ivCall;
    @Bind(R.id.tv_one)
    TextView tvOne;
    @Bind(R.id.tv_pay)
    TextView tvPay;
    @Bind(R.id.ll_btn_status)
    LinearLayout llBtnStatus;
    @Bind(R.id.content_view)
    RelativeLayout contentView;
    @Bind(R.id.multiplestatusview)
    MultipleStatusView statusview;
    @Bind(R.id.tv_order_info)
    TextView tvOrderInfo;
    @Bind(R.id.rl_order_evaluation)
    RelativeLayout rlOrderEvaluation;
    @Bind(R.id.ll_evaluation)
    LinearLayout llEvaluation;
    @Bind(R.id.rb_evaluation)
    RatingBar rbEvaluation;
    @Bind(R.id.rv_status)
    RecyclerView rvStatus;
    @Bind(R.id.ll_order_daizf)
    LinearLayout llOrderDaizf;
    @Bind(R.id.ll_order_daijd)
    LinearLayout llOrderDaijd;
    @Bind(R.id.countdown)
    CountdownView countdownView;
    private AMap aMap;
    private MarkerOptions markerOptions;
    private Marker marker;
    private String order_id;
    /**
     * 第一次进入订单详情
     */
    private boolean isFirst = true;
    private CallPhoneDialog phoneDialog;
    private Data_Run_Order_Details details;
    private RunOrderStatusAdapter statusAdapter;
    private boolean isShowStaffPhone = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_order_details);
        ButterKnife.bind(this);
        mapStaff.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        order_id = getIntent().getStringExtra(ORDER_ID);
        if (!TextUtils.isEmpty(order_id)) {
            requestData(false);
        } else {
            statusview.showError();
        }
        tvTitle.setText(R.string.订单详情);
        toolbar.setNavigationOnClickListener(v -> finish());
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(true);
            }
        });

        statusAdapter = new RunOrderStatusAdapter(this);


    }

    /**
     * @param isShow 显示默认动画
     */
    private void requestData(boolean isShow) {
        JSONObject object = new JSONObject();
        try {
            object.put("order_id", order_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(this, Api.PAOTUI_ORDER_DETAIL, object.toString(), isShow, new OnRequestSuccess<BaseResponse<Data_Run_Order_Details>>() {
            @Override
            public void onSuccess(String url, BaseResponse<Data_Run_Order_Details> data) {
                super.onSuccess(url, data);
                if (data.error == 0) {
                    details = data.getData();
                    /*---------------------订单状态-------------------------*/
                    if (details.getShow_label() != null && details.getShow_label().size() > 0) {
                        rvStatus.setLayoutManager(new GridLayoutManager(RunOrderDetailsActivity.this, details.getShow_label().size()));
                        rvStatus.setAdapter(statusAdapter);
                        statusAdapter.setData(details.getShow_label());
                    }
                    tvOrderStatus.setText(details.getMsg());
                    /*待接单、配送中、已完成*/
                    llOrderDaijd.setVisibility(View.VISIBLE);
                    /*待支付*/
                    llOrderDaizf.setVisibility(View.GONE);
                    /*地图*/
                    rlStaffInfo.setVisibility(View.GONE);
                    if (!"1".equals(details.getStaff().getStaff_id()) && ("1".equals(details.getOrder_status()) || "3".equals(details.getOrder_status()))) {
                        /*取货&送货*/
                        ivOrderStatus.setImageResource(R.mipmap.order_status_peisong);
                        rlStaffInfo.setVisibility(View.VISIBLE);
                        tvStaff.setText(details.getStaff().getName() + "\t" + details.getStaff().getMobile());
                        if (aMap == null) {
                            aMap = mapStaff.getMap();
                        }
                        LatLng latLng = null;
                        if (details.getStaff().getLat() != null && details.getStaff().getLng() != null) {
                            CoordinateConverter converter = new CoordinateConverter(RunOrderDetailsActivity.this);
                            converter.from(CoordinateConverter.CoordType.BAIDU);
                            converter.coord(new LatLng(Double.parseDouble(details.getStaff().getLat()), Double.parseDouble(details.getStaff().getLng())));
                            latLng = converter.convert();
                            addMarkersToMap(latLng);// 往地图上添加marker
                        }
                    } else if ("4".equals(details.getOrder_status()) || "8".equals(details.getOrder_status())) {
                        /*已完成*/
                        ivOrderStatus.setImageResource(R.mipmap.order_status_success);
                    } else if ("0".equals(details.getOrder_status())) {
                        isShowStaffPhone = false;
                        if ("0".equals(details.getPay_status())) {
                            /*待支付*/
                            llOrderDaizf.setVisibility(View.VISIBLE);
                            llOrderDaijd.setVisibility(View.GONE);
                            long time = Long.parseLong(details.getNo_pay_canel_time()) - System.currentTimeMillis() / 1000;
                            countdownView.start(time * 1000); // 毫秒
                            countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                                @Override
                                public void onEnd(CountdownView cv) {
                                    dealWithOrder(Api.PAOTUI_ORDER_CANEL, details.getOrder_id());
                                }
                            });

                        } else if ("1".equals(details.getPay_status())) {
                            /*等待骑手接单*/
                            ivOrderStatus.setImageResource(R.mipmap.order_status_daijiedan);
                        }

                    } else if ("-1".equals(details.getOrder_status())) {
                        /*已取消*/
                        isShowStaffPhone = false;
                        ivOrderStatus.setImageResource(R.mipmap.order_status_cancle);
                    }

                    /*---------------------物品信息-------------------------*/
                    if (details.getProduct_info() != null && details.getProduct_info().size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < details.getProduct_info().size(); i++) {
                            sb.append(details.getProduct_info().get(i) + " ");
                        }
                        tvOrderInfo.setText(sb.toString());
                    }
                     /*购买地址*/
                    tvGouAddr.setText(details.getM_addr());
                    /*发货地址*/
                    tvFaAddr.setText(details.getS_addr());
                    /*--------------------支付信息--------------------------*/
                    /*配送费*/
                    tvDeliveryPrices.setText("￥" + details.getPei_amount());
                    /*小费*/
                    tvTip.setText("￥" + details.getTip());
                    /*红包*/
                    tvRed.setText("-￥" + details.getHongbao());
                    /*实付*/
                    tvActuallyPaid.setText("￥" + details.getPayed_money());

                    /*--------------------配送信息------------------------*/
                    /*送达时间*/
                    tvDeliveryTime.setText(details.getPei_time());
                    /*配送距离*/
                    tvDistance.setText(details.getPei_distance());
                    /*配送骑手*/
                    if (details.getStaff() != null && !"0".equals(details.getStaff().getStaff_id())) {
                        tvRider.setText(details.getStaff().getName());
                        tvRiderPhone.setText(details.getStaff().getMobile());
                    } else {
                        tvRider.setText("---");
                        tvRiderPhone.setText("---");
                    }
                    /*--------------------订单信息------------------------*/
                    /*订单号码*/
                    tvOrderId.setText(details.getOrder_id());
                    /*下单时间*/
                    tvOrderTime.setText(details.getDateline());
                    /*支付方式*/
                    tvPayment.setText(details.getPay_type());
                    /*--------------------评价信息------------------------*/
                    if (details.getComment() != null && !"0".equals(details.getComment().getComment_id())) {
                        llEvaluation.setVisibility(View.VISIBLE);
                        rbEvaluation.setRating(Integer.parseInt(details.getComment().getScore()));
                    } else {
                        llEvaluation.setVisibility(View.GONE);
                    }
                    phoneDialog = new CallPhoneDialog(RunOrderDetailsActivity.this, isShowStaffPhone);
                    /*--------------------按钮信息------------------------*/
                    int status = RunOrderStatus.dealWith(details.getBtn());
                    llBtnStatus.setVisibility(View.VISIBLE);
                    switch (status) {
                        case RunOrderStatus.RUN_STATUS_PAY:
                            tvPay.setVisibility(View.VISIBLE);
                            tvPay.setText("立即支付");
                            tvPay.setOnClickListener(v -> {
                                ToPayNewActivity.startActivity(RunOrderDetailsActivity.this, details.getOrder_id(), Double.parseDouble(details.getPei_amount()), ToPayNewActivity.TO_PAOTUI);
                            });
                            tvOne.setVisibility(View.GONE);
                            break;
                        case RunOrderStatus.RUN_STATUS_PAY_CANCEL:
                            tvPay.setVisibility(View.VISIBLE);
                            tvPay.setText("立即支付");
                            tvPay.setOnClickListener(v -> {
                                ToPayNewActivity.startActivity(RunOrderDetailsActivity.this, details.getOrder_id(), Double.parseDouble(details.getPei_amount()), ToPayNewActivity.TO_PAOTUI);
                            });
                            tvOne.setVisibility(View.VISIBLE);
                            tvOne.setText("取消订单");
                            tvOne.setOnClickListener(v -> {
                                dealWithOrder(Api.PAOTUI_ORDER_CANEL, details.getOrder_id());
                            });

                            break;
                        case RunOrderStatus.RUN_STATUS_CONFIRM:
                            tvPay.setVisibility(View.GONE);
                            tvOne.setVisibility(View.VISIBLE);
                            tvOne.setText("确认订单");
                            tvOne.setOnClickListener(v -> {
                                dealWithOrder(Api.PAOTUI_ORDER_CONFIRM, details.getOrder_id());
                            });
                            break;
                        case RunOrderStatus.RUN_STATUS_AGAIN_COMMENT:
                            tvPay.setVisibility(View.VISIBLE);
                            tvPay.setText("评价");
                            tvPay.setOnClickListener(v -> {
                                RunOrderEvaluationActivity.startActivity(RunOrderDetailsActivity.this, details.getOrder_id());
                            });
                            tvOne.setVisibility(View.VISIBLE);
                            tvOne.setText("再来一单");
                            tvOne.setOnClickListener(v -> {

                            });

                            break;
                        case RunOrderStatus.RUN_STATUS_CANCEL:
                            tvPay.setVisibility(View.GONE);
                            tvOne.setVisibility(View.VISIBLE);
                            tvOne.setText("取消订单");
                            tvOne.setOnClickListener(v -> {
                                dealWithOrder(Api.PAOTUI_ORDER_CANEL, details.getOrder_id());
                            });
                            break;
                        case RunOrderStatus.RUN_STATUS_CUI:
                            tvPay.setVisibility(View.GONE);
                            tvOne.setVisibility(View.VISIBLE);
                            tvOne.setText("催单");
                            tvOne.setOnClickListener(v -> {
                                dealWithOrder(Api.PAOTUI_ORDER_CUI, details.getOrder_id());
                            });
                            break;
                        case RunOrderStatus.RUN_STATUS_AGAIN:
                            tvPay.setVisibility(View.GONE);
                            tvOne.setVisibility(View.VISIBLE);
                            tvOne.setText("再来一单");
                            tvOne.setOnClickListener(v -> {

                            });
                            break;
                        case RunOrderStatus.STATUS_NULL:
                            llBtnStatus.setVisibility(View.GONE);
                            break;
                    }
                } else {
                    statusview.showError();
                    ToastUtil.show(data.message);
                }
                isFirst = false;
                if (!isShow) {
                    statusview.showContent();
                } else {
                    mRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onBeforeAnimate() {
                super.onBeforeAnimate();
                if (!isShow) {
                    statusview.showLoading();
                }
            }

            @Override
            public void onErrorAnimate() {
                super.onErrorAnimate();
                statusview.showError();
            }
        });
    }

    /**
     * 处理订单
     *
     * @param api
     * @param order_id
     */
    private void dealWithOrder(String api, String order_id) {
        JSONObject object = new JSONObject();
        try {
            object.put("order_id", order_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(this, api, object.toString(), true, new OnRequestSuccess<BaseResponse<Data_Run_Order_UnDone>>() {
            @Override
            public void onSuccess(String url, BaseResponse<Data_Run_Order_UnDone> data) {
                super.onSuccess(url, data);
                ToastUtil.show(data.message);
                requestData(true);

            }
        });

    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(LatLng latLng) {
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 18, 30, 30)), null);
        markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_qishou))
                .snippet(details.getJuli())
                .draggable(true);
        if (marker != null) {
            marker.destroy();
        }
        marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        /*带动画移动*/
        aMap.animateCamera(update, 1000, callback);
    }

    @OnClick({R.id.tv_one, R.id.tv_pay, R.id.iv_call, R.id.rl_order_evaluation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_one:
                break;
            case R.id.tv_pay:
                break;
            case R.id.iv_call:
                if (phoneDialog != null) {
                    phoneDialog.show();
                    phoneDialog.setHindShop(true);
                    phoneDialog.setOnCallPhoneListener(type -> {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        switch (type) {
                            case CALL_STAFF:
                                Uri data1 = Uri.parse("tel:" + details.getStaff_mobile());
                                intent.setData(data1);
                                startActivity(intent);
                                break;
                            case CALL_CSD:
                                Uri data2 = Uri.parse("tel:" + details.getKefu_mobile());
                                intent.setData(data2);
                                startActivity(intent);
                                break;
                        }
                    });
                }
                break;
            case R.id.rl_order_evaluation:
                RunOrderCommentActivity.startActivity(this, details.getComment().getComment_id());
                break;
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapStaff.onResume();
        MobclickAgent.onResume(this);
        if (!isFirst) {
            requestData(true);
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapStaff.onPause();
        MobclickAgent.onPause(this);

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapStaff.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapStaff.onDestroy();
    }

    public static void startActivity(Context context, String order_id) {
        Intent intent = new Intent(context, RunOrderDetailsActivity.class);
        intent.putExtra(ORDER_ID, order_id);
        context.startActivity(intent);
    }
}
