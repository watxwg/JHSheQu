package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
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
import com.coorchice.library.SuperTextView;
import com.google.gson.Gson;
import com.jhcms.common.dialog.TipDialog;
import com.jhcms.common.model.Data_Orederdetail;
import com.jhcms.common.model.Response_Orederdetail;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.model.ShopOrderModel;
import com.jhcms.common.model.StaffModel;
import com.jhcms.common.model.YouHuiModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.ListViewForListView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.OrderDetailsActivityYouHuiAdapter;
import com.jhcms.waimaiV3.dialog.CallPhoneDialog;
import com.jhcms.waimaiV3.dialog.OrderCancelDialog;
import com.jhcms.waimaiV3.dialog.ShareDialog;
import com.jhcms.waimaiV3.model.Goods;
import com.jhcms.waimaiV3.model.ShareItem;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

/**
 * Created by Wyj on 2017/5/9
 * TODO:订单详情
 */
public class OrderDetailsActivity extends AppCompatActivity {

    /**
     * 拨打商家电话标记
     */
    public static final String CALL_SHOP = "CALL_SHOP";
    /**
     * 拨打骑手电话标记
     */
    public static final String CALL_STAFF = "CALL_STAFF";
    /**
     * 拨打客服电话标记
     */
    public static final String CALL_CSD = "CALL_CSD";
    public static String ORDER_ID = "ORDER_ID";
    private final String topaymodelfromOrderDetailsActivity = "topaymodelfromOrderDetailsActivity";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_order_status)
    TextView tvOrderStatus;
    @Bind(R.id.order_description)
    LinearLayout mLayOrderDescription;
    @Bind(R.id.stv_four)
    SuperTextView stvFour;
    @Bind(R.id.stv_three)
    SuperTextView stvThree;
    @Bind(R.id.stv_two)
    SuperTextView stvTwo;
    @Bind(R.id.stv_one)
    SuperTextView stvOne;
    @Bind(R.id.tv_staff)
    TextView tvStaff;
    @Bind(R.id.iv_staff_call)
    ImageView ivStaffCall;
    @Bind(R.id.map_staff)
    MapView mapStaff;
    @Bind(R.id.rl_staff_info)
    RelativeLayout rlStaffInfo;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.ll_shop_name)
    LinearLayout llShopName;
    @Bind(R.id.ll_allcomm)
    LinearLayout llAllcomm;
    @Bind(R.id.tv_delivery_prices)
    TextView tvDeliveryPrices;
    @Bind(R.id.tv_packing_prices)
    TextView tvPackingPrices;
    @Bind(R.id.youhui)
    ListViewForListView mYouhuiListview;
    @Bind(R.id.tv_total_discount)
    TextView tvTotalDiscount;
    @Bind(R.id.tv_paid)
    TextView tvPaid;
    @Bind(R.id.tv_delivery_time)
    TextView tvDeliveryTime;
    @Bind(R.id.tv_delivery_address)
    TextView tvDeliveryAddress;
    @Bind(R.id.tv_delivery_mode)
    TextView tvDeliveryMode;
    @Bind(R.id.ll_delivery)
    LinearLayout llDelivery;
    @Bind(R.id.tv_order_num)
    TextView tvOrderNum;
    @Bind(R.id.tv_order_payway)
    TextView tvOrderPayway;
    @Bind(R.id.tv_order_time)
    TextView tvOrderTime;
    @Bind(R.id.tv_order_note)
    TextView tvOrderNote;
    @Bind(R.id.iv_saoma)
    ImageView ivSaoma;
    @Bind(R.id.iv_redbag)
    ImageView ivRedbag;
    @Bind(R.id.iv_call)
    ImageView ivCall;
    @Bind(R.id.iv_complain)
    ImageView ivComplain;
    @Bind(R.id.StvSeven)
    SuperTextView StvSeven;
    @Bind(R.id.StvEight)
    SuperTextView StvEight;
    @Bind(R.id.Stvfive)
    SuperTextView STvFive;
    @Bind(R.id.StvSix)
    SuperTextView mStvSix;
    @Bind(R.id.again)
    SuperTextView mTvagain;
    @Bind(R.id.multiplestatusview)
    MultipleStatusView multiplestatusview;
    @Bind(R.id.countdown)
    CountdownView countdownView;
    @Bind(R.id.lat_delivery_address)
    LinearLayout lat_delivery_address;
    @Bind(R.id.personguser)
    TextView persongUser;
    @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    long miaoshu = 0;
    private TextView tvCommTitle, tvCommNum, tvCommPices;
    private AMap aMap;
    private MarkerOptions markerOptions;
    private Marker marker;
    private CallPhoneDialog callPhoneDialog;
    private String order_id;
    private OrderDetailsActivityYouHuiAdapter madapter;
    private boolean staffFlag = false;
    private LatLng userLatlng;
    private ShareDialog dialog;
    private OrderCancelDialog orderCancelDialog;
    private Response_Orederdetail mData;
    private PopupWindow mZiTiMaPopuwindow;
    private boolean isRefresh = false;
    private String ShopAnduser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        MobclickAgent.setScenarioType(OrderDetailsActivity.this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setCatchUncaughtExceptions(true);
        ButterKnife.bind(this);
        mapStaff.onCreate(savedInstanceState);
//        location();
        inintIntent();
        initData();
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                RequestData(true);
            }
        });
    }

    private void inintIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            order_id = intent.getStringExtra(ORDER_ID);
            RequestData(false);
        }
    }

    private void RequestData(final boolean isShow) {
        try {
            JSONObject js = new JSONObject();
            js.put("order_id", order_id);
            String str = js.toString();
            HttpUtils.postUrl(this, Api.WAIMAI_ORDER_DETAIL, str, isShow, new OnRequestSuccessCallback() {
                @Override
                public void onSuccess(String url, String Json) {
                    if (isRefresh) {
                        isRefresh = false;
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                    Gson gson = new Gson();
                    switch (url) {
                        case Api.WAIMAI_ORDER_DETAIL:
                            try {
                                Response_Orederdetail mdata = gson.fromJson(Json, Response_Orederdetail.class);
                                if (mdata.error.equals("0")) {
                                    BinddataView(mdata);
                                    multiplestatusview.showContent();
                                } else {
                                    multiplestatusview.showError();
                                    ToastUtil.show(mdata.message);
                                }
                            } catch (Exception e) {
                                ToastUtil.show(getString(R.string.数据解析异常));
                                multiplestatusview.showError();
                                saveCrashInfo2File(e);
                            }
                            break;
                    }
                }

                @Override
                public void onBeforeAnimate() {
                    if (!isShow) {
                        multiplestatusview.showLoading();
                    }


                }

                @Override
                public void onErrorAnimate() {
                    multiplestatusview.showError();
                    if (isRefresh) {
                        isRefresh = false;
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自提单隐藏配送信息
     */
    private void initData() {
        tvTitle.setText(R.string.订单详情);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GO_ORDER();
            }
        });
        orderCancelDialog = new OrderCancelDialog(this);
    }

    @Override
    public void onBackPressed() {
        GO_ORDER();
    }

    private void GO_ORDER() {
        Intent intent = new Intent(OrderDetailsActivity.this, WaiMaiMainActivity.class);
        intent.putExtra(WaiMaiMainActivity.TYPE, "GO_ORDER");
        startActivity(intent);
        finish();
    }

    private void initOrderMap(Response_Orederdetail mdata) {
        StaffModel staff = mdata.getData().getDetail().getStaff();
        if (staff.getLat() == null) {
            rlStaffInfo.setVisibility(View.GONE);
            return;
        }
        if (staff.getName() != null && staff.getMobile() != null) {
            tvStaff.setText(getString(R.string.骑手) + staff.getName() + "\t\t" + staff.getMobile());
        }
        if (aMap == null) {
            staffFlag = true;
            aMap = mapStaff.getMap();
        }
        LatLng latLng = null;
        if (staff.getLat() != null && staff.getLng() != null) {
            CoordinateConverter converter = new CoordinateConverter(this);
            // CoordType.BAIDU 待转换坐标类型
            converter.from(CoordinateConverter.CoordType.BAIDU);
            // sourceLatLng待转换坐标点 LatLng类型
            converter.coord(new LatLng(Double.parseDouble(staff.getLat()), Double.parseDouble(staff.getLng())));
            // 执行转换操作
            latLng = converter.convert();


        }
        if (userLatlng != null) {
            addMarkersToMap(latLng);// 往地图上添加marker
        }
    }


    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(LatLng latLng) {

        CoordinateConverter converter = new CoordinateConverter(this);
        // CoordType.BAIDU 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.BAIDU);
        // sourceLatLng待转换坐标点 LatLng类型
        converter.coord(new LatLng(userLatlng.latitude, userLatlng.longitude));
        // 执行转换操作
        LatLng UserLatLng = converter.convert();

        int distance = (int) AMapUtils.calculateLineDistance(latLng, UserLatLng);
        String str = null;
        if (distance / 1000 > 0) {
            str = distance / 1000 + "." + (String.valueOf(distance % 1000).substring(0, 1)) + "km";
        } else {
            str = distance + "m";
        }
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 18, 30, 30)), null);
        markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_qishou))
                .title(getString(R.string.骑手位置))
                .snippet("距" + ShopAnduser + "还有" + str)
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
        /*不带动画*/
//        aMap.moveCamera(update);
        /*带动画移动*/
        aMap.animateCamera(update, 1000, callback);
    }


    private void initOrderInfo(Response_Orederdetail mdata) {
        /*订单号*/
        tvOrderNum.setText(getString(R.string.订单号) + mdata.getData().getDetail().getOrder_id());
        /*支付方式*/
        if (mdata.getData().getDetail().getOnline_pay().equals("1")) {
            tvOrderPayway.setText(R.string.在线支付_方式);
        } else if (mdata.getData().getDetail().getOnline_pay().equals("0")) {
            tvOrderPayway.setText(R.string.货到付款_方式);
        }

        if (!mdata.getData().getDetail().getDateline().equals("0")) {
             /*下单时间*/
            tvOrderTime.setText(getString(R.string.下单时间) + Utils.convertDate(Long.parseLong(mdata.getData().getDetail().getDateline()), null));
        } else {
            tvOrderTime.setText("下单时间：未支付");
        }

        /*订单备注*/
        if (mdata.getData().getDetail().getIntro() != null && mdata.getData().getDetail().getIntro().length() > 0) {
            tvOrderNote.setText("订单备注：" + mdata.getData().getDetail().getIntro());
        } else {
            tvOrderNote.setText("订单备注：无");
        }
    }

    private void initDeliveryInfo(Response_Orederdetail mdata) {
        if (mdata.getData().getDetail().getPei_type().equals("3")) {
              /*送达时间*/
            if (mdata.getData().getDetail().getPei_time().equals("0")) {
                tvDeliveryTime.setText("送达时间：立即自提");
            } else {
                tvDeliveryTime.setText("送达时间：" + Utils.convertDate(Long.parseLong(mdata.getData().getDetail().getPei_time()), null));
            }
        } else {
            if (mdata.getData().getDetail().getPei_time().equals("0")) {
                tvDeliveryTime.setText("送达时间：尽快送达");
            } else {
                tvDeliveryTime.setText("送达时间：" + Utils.convertDate(Long.parseLong(mdata.getData().getDetail().getPei_time()), null));
            }
        }

        if (mdata.getData().getDetail().getPei_type().equals("3")) {
            /*配送方式*/
            tvDeliveryMode.setText("配送方式：自提");
            if (mdata.getData().getDetail().getSpend_number() != null && mdata.getData().getDetail().getSpend_number().length() > 0) {
                ivSaoma.setTag(mdata.getData().getDetail().getSpend_number());
            }
            lat_delivery_address.setVisibility(View.GONE);
        } else if (mdata.getData().getDetail().getPei_type().equals("0")) {
            tvDeliveryMode.setText("配送方式：商家配送");
            ivSaoma.setVisibility(View.GONE);
             /*收货地址*/
            tvDeliveryAddress.setText(mdata.getData().getDetail().getContact() + "\n" + mdata.getData().getDetail().getMobile() + "\n" +
                    mdata.getData().getDetail().getAddr() + "\n" + mdata.getData().getDetail().getHouse());
        } else if (mdata.getData().getDetail().getPei_type().equals("1")) {
            tvDeliveryMode.setText("配送方式：平台送");
            ivSaoma.setVisibility(View.GONE);
             /*收货地址*/
            tvDeliveryAddress.setText(mdata.getData().getDetail().getContact() + "\n" + mdata.getData().getDetail().getMobile() + "\n" +
                    mdata.getData().getDetail().getAddr() + "\n" + mdata.getData().getDetail().getHouse());
        }

    }

    private void initShop(Response_Orederdetail mdata) {

        /*店铺名*/
        tvShopName.setText(mdata.getData().getDetail().getWaimai().getTitle());
        /*配送费*/
        if (mdata.getData().getDetail().getFreight().equals("0.00")) {
            tvDeliveryPrices.setText("免费配送");
        } else {
            tvDeliveryPrices.setText("￥" + mdata.getData().getDetail().getFreight());
        }
        /*打包费*/
        tvPackingPrices.setText("￥" + mdata.getData().getDetail().getPackage_price());
        /*首单立减*/
        inintYouhuiList(mdata.getData().getDetail().getYouhui());
        float youhui = Float.parseFloat(mdata.getData().getDetail().getTotal_price()) - Float.parseFloat(mdata.getData().getDetail().getAmount());
        /*总计  优惠*/
        tvTotalDiscount.setText(String.format(getString(R.string.总计￥), mdata.getData().getDetail().getTotal_price()) + "\t\t" + "优惠￥" + youhui);
        /*实付*/
        tvPaid.setText("￥" + mdata.getData().getDetail().getAmount());
        /*菜品详情*/
        llAllcomm.removeAllViews();
        if (mdata.getData().getDetail().getProducts().size() > 0) {
            for (int i = 0; i < mdata.getData().getDetail().getProducts().size(); i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.order_detail_menu, null);
                tvCommTitle = (TextView) view.findViewById(R.id.tv_comm_title);
                tvCommNum = (TextView) view.findViewById(R.id.tv_comm_num);
                tvCommPices = (TextView) view.findViewById(R.id.tv_comm_pices);
                tvCommTitle.setText(mdata.getData().getDetail().getProducts().get(i).getProduct_name());
                tvCommNum.setText("X" + mdata.getData().getDetail().getProducts().get(i).getProduct_number());
                tvCommPices.setText("￥" + Float.parseFloat(mdata.getData().getDetail().getProducts().get(i).getProduct_price()) + "");
                llAllcomm.addView(view);
            }
        }

    }

    @OnClick({R.id.stv_four, R.id.again, R.id.stv_three, R.id.tv_order_status, R.id.stv_two, R.id.stv_one, R.id.Stvfive, R.id.StvSix, R.id.StvEight, R.id.StvSeven, R.id.iv_staff_call, R.id.ll_shop_name, R.id.iv_saoma, R.id.iv_redbag, R.id.iv_call, R.id.iv_complain})
    public void onClick(View view) {
        Intent intent3 = new Intent();
        switch (view.getId()) {
            case R.id.stv_one:
                /*去支付*/
                Intent intent = new Intent(OrderDetailsActivity.this, ToPayActivity.class);
                intent.putExtra(topaymodelfromOrderDetailsActivity, mData);
                intent.putExtra("order_id", mData.getData().getDetail().getOrder_id());
                startActivity(intent);
                break;
            case R.id.stv_two://推单
                orderRemind(mData.getData().getDetail().getOrder_id());
                break;
            case R.id.stv_three:
                /*取消订单*/
                CancelOrder(mData.getData().getDetail().getOrder_id());
                break;
            case R.id.stv_four:
                callkefu(mData.getData());
                break;
            case R.id.Stvfive://确认到达
                orderConfirm();
                break;
            case R.id.StvSix://申请退款
                showPopuwindowrefund();
                break;
            case R.id.StvSeven://TODO 查看网页评价
                Intent i2 = new Intent(OrderDetailsActivity.this, LookMerchantEvaluationActivity.class);
                i2.putExtra("comment_id", mData.getData().getDetail().getComment_id());
                i2.putExtra("peitype", mData.getData().getDetail().getPei_type());
                startActivity(i2);

                break;
            case R.id.StvEight://TODO 去评价
                ShopOrderModel shoporrder = new ShopOrderModel();
                shoporrder.shop_logo = mData.getData().getDetail().getWaimai().getLogo();
                shoporrder.shop_title = mData.getData().getDetail().getWaimai().getTitle();
                shoporrder.products = mData.getData().getDetail().getProducts();
                shoporrder.time = mData.getData().getDetail().getTime();
                shoporrder.order_id = mData.getData().getDetail().getOrder_id();
                shoporrder.spend_number = mData.getData().getDetail().getSpend_number();
                shoporrder.jifen_total = mData.getData().getDetail().getJifen_total();
                shoporrder.pei_type = mData.getData().getDetail().getPei_type();
                Intent i = new Intent(OrderDetailsActivity.this, MerchantEvaluationActivity.class);
                i.putExtra("model", shoporrder);
                startActivity(i);
                break;
            case R.id.iv_staff_call:
//                ToastUtil.show("配送员电话");
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + ivStaffCall.getTag());
                intent1.setData(data);
                startActivity(intent1);
                break;
            case R.id.ll_shop_name:
                intent3.setClass(OrderDetailsActivity.this, WaiMaiShopActivity.class);
                intent3.putExtra("TYPE", mData.getData().getDetail().getWaimai().getShop_id());
                startActivity(intent3);
                break;
            case R.id.iv_saoma://TODO 打开自提码
                inintmZiTiMaPopuwindow();
                break;
            case R.id.iv_redbag:
                ShareItem shareItems = new ShareItem();
                shareItems.setTitle(mData.getData().getDetail().getHongbao().getTitle());
                shareItems.setLogo(Api.IMAGE_URL + mData.getData().getDetail().getHongbao().getImgUrl());
                shareItems.setUrl(mData.getData().getDetail().getHongbao().getLink());
                shareItems.setDescription(mData.getData().getDetail().getHongbao().getDesc());
                dialog = new ShareDialog(this);
                dialog.setItem(shareItems);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;


            case R.id.iv_call:
                callPhoneDialog.show();
                callPhoneDialog.setOnCallPhoneListener(new CallPhoneDialog.OnSelectListener() {
                    @Override
                    public void selectListener(String type) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        switch (type) {
                            case CALL_SHOP:
                                Uri data = Uri.parse("tel:" + mData.getData().getDetail().getWaimai().getPhone());
                                intent.setData(data);
                                startActivity(intent);
                                break;
                            case CALL_STAFF:
                                Uri data1 = Uri.parse("tel:" + mData.getData().getDetail().getStaff().getMobile());
                                intent.setData(data1);
                                startActivity(intent);
                                break;
                            case CALL_CSD:
                                Uri data2 = Uri.parse("tel:" + mData.getData().getDetail().getKefu_phone());
                                intent.setData(data2);
                                startActivity(intent);
                                break;
                        }
                    }
                });
                break;
            case R.id.again:
                ShopCreatOrder();
                break;
            case R.id.iv_complain://TODO 投诉
                if (mData.getData().getDetail().getComplaint().equals("0")) {
                    Intent intent4 = new Intent(OrderDetailsActivity.this, ComplainActivity.class);
                    intent4.putExtra("orderid", mData.getData().getDetail().getOrder_id());
                    intent4.putExtra("phone", mData.getData().getDetail().getWaimai().getPhone());
                    startActivity(intent4);
                } else {
                    ToastUtil.show("已投诉");
                }

                break;
            case R.id.tv_order_status:
                Intent intent5 = new Intent(OrderDetailsActivity.this, WebViewActivity.class);
                intent5.putExtra("WEB_URL", mData.getData().getDetail().getLink());
                startActivity(intent5);
                break;
        }
    }

    private void inintmZiTiMaPopuwindow() {
        View view = LayoutInflater.from(OrderDetailsActivity.this).inflate(R.layout.waimai_order_zitima_layout, null);
        TextView mTvZiTiMaTitle = (TextView) view.findViewById(R.id.ZiTiMaTitle_tv);
        mTvZiTiMaTitle.setText(ivSaoma.getTag() + "");
        ImageView mIvZiTiMa = (ImageView) view.findViewById(R.id.ZiTiMaTitle_Iv);
        LinearLayout mLayZiTiMa = (LinearLayout) view.findViewById(R.id.ZiTiMaTitle_Layout);
        mZiTiMaPopuwindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        if (mData.getData().getDetail().getSpend_status().equals("0")) {
            mIvZiTiMa.setAlpha(255);
        } else {
            mIvZiTiMa.setAlpha(255 / 2);
        }
        mZiTiMaPopuwindow.setContentView(view);
        mZiTiMaPopuwindow.setOutsideTouchable(true);
        mZiTiMaPopuwindow.setFocusable(true);// 获取焦点

        mLayZiTiMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mZiTiMaPopuwindow != null && mZiTiMaPopuwindow.isShowing()) {
                    mZiTiMaPopuwindow.dismiss();
                }
            }
        });

        Bitmap bitmap = CodeUtils.createImage(ivSaoma.getTag() + "", 80, 80, null);
        mIvZiTiMa.setImageBitmap(bitmap);

        if (mZiTiMaPopuwindow != null && !mZiTiMaPopuwindow.isShowing()) {
            mZiTiMaPopuwindow.showAtLocation(this.findViewById(R.id.multiplestatusview), Gravity.CENTER, 0, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 再来一大单//TODO 跳到商家详情界面
     */
    private void ShopCreatOrder() {
        /**
         * "Goods{" +
         "productsEntity=" + productsEntity +
         ", product_id='" + product_id + '\'' +
         ", productId='" + productId + '\'' +
         ", shop_id='" + shop_id + '\'' +
         ", spec_id='" + spec_id + '\'' +
         ", price='" + price + '\'' +
         ", typeId=" + typeId +
         ", title='" + title + '\'' +
         ", name='" + name + '\'' +
         ", count=" + count +
         ", sale_sku=" + sale_sku +
         ", is_spec='" + is_spec + '\'' +
         ", good='" + good + '\'' +
         ", bad='" + bad + '\'' +
         ", logo='" + logo + '\'' +
         ", specSelect=" + specSelect +
         '}';
         */

        ArrayList<Goods> goodses = new ArrayList<>();
        for (int i = 0; i < mData.getData().getDetail().getProducts().size(); i++) {
            Goods goods = new Goods();
//            goods.product_id = mData.getData().getDetail().getProducts().get(i).getProduct_id();
            goods.productId = mData.getData().getDetail().getProducts().get(i).getProduct_id();
            goods.shop_id = mData.getData().getDetail().getWaimai().getShop_id();
            goods.spec_id = mData.getData().getDetail().getProducts().get(i).getSpec_id();
            goods.sale_sku = mData.getData().getDetail().getProducts().get(i).getSale_sku();
            goods.price = mData.getData().getDetail().getProducts().get(i).getProduct_price();
            goods.name = mData.getData().getDetail().getProducts().get(i).getProduct_name();
            goods.count = Integer.parseInt(mData.getData().getDetail().getProducts().get(i).getProduct_number());
            goods.pagePrice = mData.getData().getDetail().getProducts().get(i).getPackage_price();
            if (mData.getData().getDetail().getProducts().get(i).getSpec_id().equals("0")) {
                goods.setIs_spec("0");
                goods.product_id = mData.getData().getDetail().getProducts().get(i).getProduct_id();
                goods.setSpec_id(mData.getData().getDetail().getProducts().get(i).getSpec_id());
            } else {
                goods.setIs_spec("1");
                goods.product_id = mData.getData().getDetail().getProducts().get(i).getProduct_id() + mData.getData().getDetail().getProducts().get(i).getSpec_id();
                goods.setSpec_id(mData.getData().getDetail().getProducts().get(i).getSpec_id());
            }
            goodses.add(goods);
        }
        Gson gson = new Gson();
        String json = gson.toJson(goodses);
        Intent intent = new Intent(OrderDetailsActivity.this, WaiMaiShopActivity.class);
        intent.putExtra("PRODUCT_INFO", json);
        intent.putExtra("TYPE", mData.getData().getDetail().getWaimai().getShop_id());
        startActivity(intent);


    }


    /**
     * 申请退款
     */

    private void showPopuwindowrefund() {
        final OrderCancelDialog diaglog = new OrderCancelDialog(OrderDetailsActivity.this);
        diaglog.setOnClickListener(new OrderCancelDialog.OnClickListener() {
            @Override
            public void clickListener(String content) {
                if (content == null) {
                    ToastUtil.show("理由不能为空！");
                    return;
                }
                try {
                    JSONObject js = new JSONObject();
                    js.put("order_id", mData.getData().getDetail().getOrder_id());
                    js.put("reason", content);
                    String str = js.toString();
                    HttpUtils.postUrl(OrderDetailsActivity.this, Api.WAIMAI_ORDER_PAYBACK, str, true, new OnRequestSuccessCallback() {
                        @Override
                        public void onSuccess(String url, String Json) {
                            Gson gson = new Gson();
                            SharedResponse mdata = gson.fromJson(Json, SharedResponse.class);
                            if (mdata.error.equals("0")) {
                                ToastUtil.show(mdata.message);
                                RequestData(true);
                            } else {
                                ToastUtil.show(mdata.message);
                            }
                        }

                        @Override
                        public void onBeforeAnimate() {

                        }

                        @Override
                        public void onErrorAnimate() {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        diaglog.show();

    }

    /**
     * 确认到达
     */
    private void orderConfirm() {
        TipDialog Confirmdialog = new TipDialog(OrderDetailsActivity.this, new TipDialog.setTipDialogCilck() {
            @Override
            public void setPositiveListener() {
                try {
                    JSONObject js = new JSONObject();
                    js.put("order_id", mData.getData().getDetail().getOrder_id());
                    String str = js.toString();
                    HttpUtils.postUrl(OrderDetailsActivity.this, Api.WAIMAI_ORDER_CONFRIM, str, true, new OnRequestSuccessCallback() {
                        @Override
                        public void onSuccess(String url, String Json) {
                            if (url.equals(Api.WAIMAI_ORDER_CONFRIM)) {
                                Gson gson = new Gson();
                                SharedResponse mshare = gson.fromJson(Json, SharedResponse.class);
                                if (mshare.error.equals("0")) {
                                    RequestData(true);
                                } else {
                                    ToastUtil.show(mshare.message);
                                }

                            }
                        }

                        @Override
                        public void onBeforeAnimate() {

                        }

                        @Override
                        public void onErrorAnimate() {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void setNegativeListener() {

            }
        });
        Confirmdialog.setMessage("确认收货吗？");
        Confirmdialog.setSettextcolor(new TipDialog.TipPositiveAndtipNegativecolor() {
            @Override
            public void settingAllcolor(TextView tipPositive, TextView tipNegative) {
                tipPositive.setText("确认");
                tipNegative.setText("取消");
            }
        });
        Confirmdialog.show();
    }

    /**
     * 客服电话
     *
     * @param data
     */
    private void callkefu(final Data_Orederdetail data) {
        TipDialog kefudialog = new TipDialog(OrderDetailsActivity.this, new TipDialog.setTipDialogCilck() {
            @Override
            public void setPositiveListener() {
                try {
                    JSONObject js = new JSONObject();
                    js.put("order_id", data.getDetail().getOrder_id());
                    String str = js.toString();
                    HttpUtils.postUrl(OrderDetailsActivity.this, Api.WAIMAI_ORDER_KEFU, str, true, new OnRequestSuccessCallback() {
                        @Override
                        public void onSuccess(String url, String Json) {
                            if (url.equals(Api.WAIMAI_ORDER_KEFU)) {
                                Gson gson = new Gson();
                                SharedResponse mdata = gson.fromJson(Json, SharedResponse.class);
                                if (mdata.error.equals("0")) {
                                    ToastUtil.show("申请成功耐心等待！");
                                    RequestData(true);
                                } else {
                                    ToastUtil.show(mdata.message);
                                }
                            }
                        }

                        @Override
                        public void onBeforeAnimate() {

                        }

                        @Override
                        public void onErrorAnimate() {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void setNegativeListener() {

            }
        });
        kefudialog.setMessage("确认申请客服介入吗？");
        kefudialog.setSettextcolor(new TipDialog.TipPositiveAndtipNegativecolor() {
            @Override
            public void settingAllcolor(TextView tipPositive, TextView tipNegative) {
                tipPositive.setText("确认");
                tipNegative.setText("取消");
            }
        });
        kefudialog.show();
    }

    private void CancelOrder(final String order_id) {
        TipDialog canceldialog = new TipDialog(OrderDetailsActivity.this, new TipDialog.setTipDialogCilck() {
            @Override
            public void setPositiveListener() {
                mLayOrderDescription.setVisibility(View.GONE);
                try {
                    JSONObject js = new JSONObject();
                    js.put("order_id", order_id);
                    String str = js.toString();
                    HttpUtils.postUrl(OrderDetailsActivity.this, Api.WAIMAI_ORDER_CHARGEBACK, str, true, new OnRequestSuccessCallback() {
                        @Override
                        public void onSuccess(String url, String Json) {
                            if (url.equals(Api.WAIMAI_ORDER_CHARGEBACK)) {
                                Gson gson = new Gson();
                                SharedResponse mshare = gson.fromJson(Json, SharedResponse.class);
                                if (mshare.error.equals("0")) {
                                    RequestData(true);

                                } else {
                                    ToastUtil.show(mshare.message);
                                }

                            }
                        }

                        @Override
                        public void onBeforeAnimate() {

                        }

                        @Override
                        public void onErrorAnimate() {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void setNegativeListener() {

            }
        });
        canceldialog.setMessage("确认取消订单吗？");
        canceldialog.setSettextcolor(new TipDialog.TipPositiveAndtipNegativecolor() {
            @Override
            public void settingAllcolor(TextView tipPositive, TextView tipNegative) {
                tipPositive.setText("确认");
                tipNegative.setText("取消");
            }
        });
        canceldialog.show();

    }

    private void orderRemind(String order_id) {
        try {
            JSONObject js = new JSONObject();
            js.put("order_id", order_id);
            String str = js.toString();
            HttpUtils.postUrl(this, Api.WAIMAI_ORDER_REMIND, str, true, new OnRequestSuccessCallback() {
                @Override
                public void onSuccess(String url, String Json) {
                    try {
                        Gson gson = new Gson();
                        SharedResponse mshare1 = gson.fromJson(Json, SharedResponse.class);
                        if (mshare1.error.equals("0")) {
                            ToastUtil.show(getString(R.string.催单成功));
                            RequestData(true);
                        }
                    } catch (Exception e) {
                        ToastUtil.show(getString(R.string.数据解析异常));
                        saveCrashInfo2File(e);
                    } finally {
                        ProgressDialogUtil.dismiss(OrderDetailsActivity.this);
                    }

                }

                @Override
                public void onBeforeAnimate() {

                }

                @Override
                public void onErrorAnimate() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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
        RequestData(true);
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

    private void BinddataView(Response_Orederdetail mdata) {
        mData = mdata;
        ivStaffCall.setTag(mdata.getData().getDetail().getStaff().getMobile());
        /*订单状态*/
        initOrderStatus(mdata);
        /*配送员位置*/
        initOrderMap(mdata);
        /*店铺信息*/
        initShop(mdata);

        /*配送信息*/
        initDeliveryInfo(mdata);
        /*订单信息*/
        initOrderInfo(mdata);

    }

    private void inintYouhuiList(ArrayList<YouHuiModel> youhui) {
        madapter = new OrderDetailsActivityYouHuiAdapter(OrderDetailsActivity.this, youhui);
        mYouhuiListview.setAdapter(madapter);

    }

    private void initOrderStatus(Response_Orederdetail mdata) {
        tvOrderStatus.setText(mdata.getData().getDetail().getMsg());


        if (Integer.parseInt(mdata.getData().getDetail().getPay_status()) == 1) {//已付款
            if (mdata.getData().getDetail().getRefund().equals("-1")) {//正常单
                switch (Integer.parseInt(mdata.getData().getDetail().getOrder_status())) {
                    case -1://已取消
                        mTvagain.setVisibility(View.VISIBLE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvTwo.setVisibility(View.GONE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.GONE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        rlStaffInfo.setVisibility(View.GONE);
                        ivComplain.setVisibility(View.VISIBLE);
                        ivRedbag.setVisibility(View.GONE);
                        ivSaoma.setVisibility(View.VISIBLE);
                        break;
                    case 0://等待商户接单
                        mTvagain.setVisibility(View.VISIBLE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.VISIBLE);//取消订单
                        stvTwo.setVisibility(View.GONE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.GONE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//去评价
                        StvSeven.setVisibility(View.GONE);//查看评价
                        rlStaffInfo.setVisibility(View.GONE);
                        ivComplain.setVisibility(View.VISIBLE);
                        ivRedbag.setVisibility(View.VISIBLE);
                        ivSaoma.setVisibility(View.GONE);
                        break;
                    case 1://商户已结单预订单
                        mTvagain.setVisibility(View.GONE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.VISIBLE);//确认到达
                        mStvSix.setVisibility(View.VISIBLE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        ivSaoma.setVisibility(View.GONE);
                        if (!mdata.getData().getDetail().getPei_type().equals("3")) {//自提
                            if (Long.parseLong(mdata.getData().getDetail().getCui_time()) > 0) {
                                stvTwo.setVisibility(View.GONE);//推单
                            } else {
                                stvTwo.setVisibility(View.VISIBLE);//推单
                            }

                            if (mdata.getData().getDetail().getPei_type().equals(1)) {
                                userLatlng = new LatLng(Double.parseDouble(mdata.getData().getDetail().getWaimai().getLat()), Double.parseDouble(mdata.getData().getDetail().getWaimai().getLng()));
                                ShopAnduser = "商店";
                            }
                            ivSaoma.setVisibility(View.VISIBLE);
                        } else {
                            ivSaoma.setVisibility(View.VISIBLE);
                            stvTwo.setVisibility(View.GONE);
                            STvFive.setText("确认自提");
                        }
                        rlStaffInfo.setVisibility(View.GONE);
                        if (mdata.getData().getDetail().getPei_type().equals("1")) {
                            STvFive.setVisibility(View.GONE);
                            ivSaoma.setVisibility(View.VISIBLE);
                            if (mdata.getData().getDetail().getStaff().getName() != null) {
                                persongUser.setVisibility(View.VISIBLE);
                                persongUser.setText("配送人员：" + mData.getData().getDetail().getStaff().getName() + mdata.getData().getDetail().getStaff().getMobile());
                            }
                        } else {
                            STvFive.setVisibility(View.VISIBLE);
                        }

                        break;
                    case 2://商家已接单
                        mTvagain.setVisibility(View.GONE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvTwo.setVisibility(View.VISIBLE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        mStvSix.setVisibility(View.VISIBLE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        if (!mdata.getData().getDetail().getPei_type().equals("3")) {
                            if (Long.parseLong(mdata.getData().getDetail().getCui_time()) > 0) {
                                stvTwo.setVisibility(View.GONE);//推单
                            } else {
                                stvTwo.setVisibility(View.VISIBLE);//推单
                            }
//                            ivSaoma.setVisibility(View.VISIBLE);
                        } else {
                            stvTwo.setVisibility(View.GONE);
                            ivRedbag.setVisibility(View.VISIBLE);
                            ivSaoma.setVisibility(View.VISIBLE);
                        }
                        if (mdata.getData().getDetail().getPei_type().equals("0")) {
                            STvFive.setVisibility(View.VISIBLE);//确认到达
                        } else if (mdata.getData().getDetail().getPei_type().equals("1")) {
                            STvFive.setVisibility(View.GONE);//确认到达
                            if (mdata.getData().getDetail().getStaff().getName() != null) {
                                persongUser.setVisibility(View.VISIBLE);
                                persongUser.setText("配送人员：" + mData.getData().getDetail().getStaff().getName() + mdata.getData().getDetail().getStaff().getMobile());
                            }
                            userLatlng = new LatLng(Double.parseDouble(mdata.getData().getDetail().getWaimai().getLat()), Double.parseDouble(mdata.getData().getDetail().getWaimai().getLng()));
                            ShopAnduser = "商店";
                        } else {
                            STvFive.setVisibility(View.VISIBLE);
                            STvFive.setText("确认自提");
                        }
                        rlStaffInfo.setVisibility(View.VISIBLE);
                        break;
                    case 3://商家配送中
                        if (!mdata.getData().getDetail().getPei_type().equals("3")) {
                            if (Long.parseLong(mdata.getData().getDetail().getCui_time()) > 0) {
                                stvTwo.setVisibility(View.GONE);//推单
                            } else {
                                stvTwo.setVisibility(View.VISIBLE);//推单
                            }
                            if (mdata.getData().getDetail().getPei_type().equals("1")) {
                                if (mdata.getData().getDetail().getStaff().getName() != null) {
                                    persongUser.setVisibility(View.VISIBLE);
                                    persongUser.setText("配送人员：" + mData.getData().getDetail().getStaff().getName() + mdata.getData().getDetail().getStaff().getMobile());
                                }
                                userLatlng = new LatLng(Double.parseDouble(mdata.getData().getDetail().getLat()), Double.parseDouble(mdata.getData().getDetail().getLng()));
                                ShopAnduser = "您";
                            }
                        } else if (mdata.getData().getDetail().getPei_type().equals("3")) {
                            STvFive.setText("确认自提");
                            stvTwo.setVisibility(View.GONE);
                            ivSaoma.setVisibility(View.VISIBLE);
                        }
                        mTvagain.setVisibility(View.GONE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.VISIBLE);//确认到达
                        mStvSix.setVisibility(View.VISIBLE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        rlStaffInfo.setVisibility(View.VISIBLE);
                        staffFlag = true;
                        break;

                    case 4://商家已送达
                        mTvagain.setVisibility(View.VISIBLE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvTwo.setVisibility(View.GONE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.VISIBLE);//确认到达
                        mStvSix.setVisibility(View.VISIBLE);//申请退款
                        StvEight.setVisibility(View.GONE);//去评价
                        StvSeven.setVisibility(View.GONE);//查看评价
                        rlStaffInfo.setVisibility(View.GONE);
                        staffFlag = true;
                        if (mdata.getData().getDetail().getPei_type().equals("3")) {
                            ivSaoma.setVisibility(View.VISIBLE);
                            STvFive.setText("确认自提");
                        } else if (mdata.getData().getDetail().getPei_type().equals("1")) {
                            persongUser.setVisibility(View.VISIBLE);
                            persongUser.setText("配送人员：" + mData.getData().getDetail().getStaff().getName() + mdata.getData().getDetail().getStaff().getMobile());
                        } else {
                            ivSaoma.setVisibility(View.GONE);
                        }
                        break;
                    case 8://等待评价
                        mTvagain.setVisibility(View.VISIBLE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvTwo.setVisibility(View.GONE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.GONE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        rlStaffInfo.setVisibility(View.GONE);
                        if (mdata.getData().getDetail().getComment_status().equals("1")) {
                            StvSeven.setVisibility(View.VISIBLE);//查看评价
                        } else {
                            StvEight.setVisibility(View.VISIBLE);//去评价
                        }
                        if (mdata.getData().getDetail().getPei_type().equals("3")) {
                            ivSaoma.setVisibility(View.VISIBLE);
                        } else {
                            ivSaoma.setVisibility(View.GONE);
                            if (mdata.getData().getDetail().getStaff().getName() != null) {
                                persongUser.setVisibility(View.VISIBLE);
                                persongUser.setText("配送人员：" + mData.getData().getDetail().getStaff().getName() + mdata.getData().getDetail().getStaff().getMobile());

                            }
                        }
                        break;
                }

            } else {//订单异常
                if (mdata.getData().getDetail().getRefund().equals("0")) {//TODO 退款处理中
                    mTvagain.setVisibility(View.VISIBLE);//在来一单
                    stvFour.setVisibility(View.GONE);//客服
                    stvThree.setVisibility(View.GONE);//取消订单
                    stvTwo.setVisibility(View.GONE);//推单
                    stvOne.setVisibility(View.GONE);//去支付
                    STvFive.setVisibility(View.GONE);//确认到达
                    mStvSix.setVisibility(View.GONE);//申请退款
                    StvEight.setVisibility(View.GONE);//去评价
                    StvSeven.setVisibility(View.GONE);//查看评价
                    rlStaffInfo.setVisibility(View.GONE);
                    ivSaoma.setVisibility(View.GONE);
                } else if (mdata.getData().getDetail().getRefund().equals("1")) {//TODO 同意退款
                    mTvagain.setVisibility(View.VISIBLE);//在来一单
                    stvFour.setVisibility(View.GONE);//客服
                    stvThree.setVisibility(View.GONE);//取消订单
                    stvTwo.setVisibility(View.GONE);//推单
                    stvOne.setVisibility(View.GONE);//去支付
                    STvFive.setVisibility(View.GONE);//确认到达
                    mStvSix.setVisibility(View.GONE);//申请退款
                    StvEight.setVisibility(View.GONE);//去评价
                    StvSeven.setVisibility(View.GONE);//查看评价
                    rlStaffInfo.setVisibility(View.GONE);
                    ivSaoma.setVisibility(View.GONE);
                    ivRedbag.setVisibility(View.GONE);

                }
                if (mdata.getData().getDetail().getRefund().equals("2")) {//商家拒绝退款
                    if (mdata.getData().getDetail().getPei_type().equals("1")) {
                        mTvagain.setVisibility(View.GONE);//在来一单
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvTwo.setVisibility(View.GONE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.VISIBLE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        rlStaffInfo.setVisibility(View.GONE);
                        stvFour.setVisibility(View.VISIBLE);//客服
                        rlStaffInfo.setVisibility(View.GONE);
                        ivSaoma.setVisibility(View.GONE);
                        if (mdata.getData().getDetail().getPei_type().equals("1")) {
                            if (mdata.getData().getDetail().getStaff().getName() != null) {
                                rlStaffInfo.setVisibility(View.VISIBLE);
                                persongUser.setVisibility(View.VISIBLE);
                                persongUser.setText("配送人员：" + mData.getData().getDetail().getStaff().getName() + mdata.getData().getDetail().getStaff().getMobile());
                            }
                            if (mdata.getData().getDetail().getOrder_status().equals("4")) {
                                rlStaffInfo.setVisibility(View.GONE);
                            }

                            switch (Integer.parseInt(mdata.getData().getDetail().getOrder_status())) {
                                case 1:
                                    if (mdata.getData().getDetail().getPei_type().equals("1")) {
                                        userLatlng = new LatLng(Double.parseDouble(mdata.getData().getDetail().getWaimai().getLat()), Double.parseDouble(mdata.getData().getDetail().getWaimai().getLng()));
                                        ShopAnduser = "商店";
                                    }
                                    break;
                                case 2:
                                    if (mdata.getData().getDetail().getPei_type().equals("1")) {
                                        userLatlng = new LatLng(Double.parseDouble(mdata.getData().getDetail().getWaimai().getLat()), Double.parseDouble(mdata.getData().getDetail().getWaimai().getLng()));
                                        ShopAnduser = "商店";
                                    }
                                    break;
                                case 3:
                                    if (mdata.getData().getDetail().getPei_type().equals("1")) {
                                        userLatlng = new LatLng(Double.parseDouble(mdata.getData().getDetail().getLat()), Double.parseDouble(mdata.getData().getDetail().getLng()));
                                        ShopAnduser = "您";
                                    }
                                    break;
                                case 4:
                                    if (mdata.getData().getDetail().getPei_type().equals("1")) {
                                        userLatlng = new LatLng(Double.parseDouble(mdata.getData().getDetail().getLat()), Double.parseDouble(mdata.getData().getDetail().getLng()));
                                        ShopAnduser = "您";
                                    }
                                    break;
                            }
                        }

                    } else {
                        mTvagain.setVisibility(View.GONE);//在来一单
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvTwo.setVisibility(View.GONE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.VISIBLE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        rlStaffInfo.setVisibility(View.GONE);
                        stvFour.setVisibility(View.VISIBLE);//客服
                        rlStaffInfo.setVisibility(View.GONE);
                        ivSaoma.setVisibility(View.GONE);

                    }


                }

                if (mdata.getData().getDetail().getRefund().equals("3")) {//客服介入中
                    if (mdata.getData().getDetail().getOrder_status().equals("8")) {//平台拒绝退款
                        mTvagain.setVisibility(View.VISIBLE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvTwo.setVisibility(View.GONE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.GONE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        rlStaffInfo.setVisibility(View.GONE);
                        ivSaoma.setVisibility(View.GONE);
                        if (mdata.getData().getDetail().getComment_status().equals("1")) {
                            StvSeven.setVisibility(View.VISIBLE);//查看评价

                        } else {
                            StvEight.setVisibility(View.VISIBLE);//去评价
                        }

                    } else {
                        mTvagain.setVisibility(View.VISIBLE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvTwo.setVisibility(View.GONE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.GONE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        rlStaffInfo.setVisibility(View.GONE);
                        ivSaoma.setVisibility(View.GONE);
                        if (mdata.getData().getDetail().getComment_status().equals("1")) {
                            if (mdata.getData().getDetail().getStaff().getName() != null) {
                                persongUser.setVisibility(View.VISIBLE);
                                persongUser.setText("配送人员：" + mData.getData().getDetail().getStaff().getName() + mdata.getData().getDetail().getStaff().getMobile());
                            }
                        }
                    }

                }

            }


        } else if (mdata.getData().getDetail().getPay_status().equals("0")) {//未支付
            if (mdata.getData().getDetail().getOnline_pay().equals("0")) {//货到付款
                ivRedbag.setVisibility(View.GONE);
                switch (Integer.parseInt(mdata.getData().getDetail().getOrder_status())) {
                    case -1://已取消
                        mTvagain.setVisibility(View.VISIBLE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvTwo.setVisibility(View.GONE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.GONE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        rlStaffInfo.setVisibility(View.GONE);
                        ivComplain.setVisibility(View.VISIBLE);
                        ivRedbag.setVisibility(View.GONE);
                        if (mdata.getData().getDetail().getPei_type().equals("3")) {
                            ivSaoma.setVisibility(View.GONE);
                        } else {
                            ivSaoma.setVisibility(View.VISIBLE);
                        }

                        break;
                    case 0://等待商户接单
                        mTvagain.setVisibility(View.VISIBLE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.VISIBLE);//取消订单
                        stvTwo.setVisibility(View.GONE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.GONE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//去评价
                        StvSeven.setVisibility(View.GONE);//查看评价
                        rlStaffInfo.setVisibility(View.GONE);
                        ivComplain.setVisibility(View.VISIBLE);
                        ivRedbag.setVisibility(View.GONE);
                        ivSaoma.setVisibility(View.GONE);
                        break;
                    case 1://商户已结单预订单
                        mTvagain.setVisibility(View.GONE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.VISIBLE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        ivSaoma.setVisibility(View.GONE);
                        if (!mdata.getData().getDetail().getPei_type().equals("3")) {//不是平台送
                            if (Long.parseLong(mdata.getData().getDetail().getCui_time()) > 0) {
                                stvTwo.setVisibility(View.GONE);//推单
                            } else {
                                stvTwo.setVisibility(View.VISIBLE);//推单
                            }

                        } else {
                            stvTwo.setVisibility(View.GONE);
                            STvFive.setText("确认自提");
                            ivSaoma.setVisibility(View.VISIBLE);
                        }
                        rlStaffInfo.setVisibility(View.GONE);

                        break;
                    case 2://商家已接单
                        mTvagain.setVisibility(View.GONE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvTwo.setVisibility(View.VISIBLE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        if (!mdata.getData().getDetail().getPei_type().equals("3")) {
                            if (Long.parseLong(mdata.getData().getDetail().getCui_time()) > 0) {
                                stvTwo.setVisibility(View.GONE);//推单
                            } else {
                                stvTwo.setVisibility(View.VISIBLE);//推单
                            }
//                            ivSaoma.setVisibility(View.VISIBLE);
                        } else {
                            stvTwo.setVisibility(View.GONE);
                            ivSaoma.setVisibility(View.VISIBLE);
                        }
                        if (mdata.getData().getDetail().getPei_type().equals("0")) {
                            STvFive.setVisibility(View.VISIBLE);//确认到达
                        } else if (mdata.getData().getDetail().getPei_type().equals("1")) {
                            STvFive.setVisibility(View.GONE);//确认到达
//                            persongUser.setVisibility(View.VISIBLE);
//                            persongUser.setText("配送人员：" + mData.getData().getDetail().getStaff().getName() + mdata.getData().getDetail().getStaff().getMobile());
                        } else {
                            STvFive.setVisibility(View.VISIBLE);
                            STvFive.setText("确认自提");
                            mStvSix.setVisibility(View.GONE);
                        }
                        rlStaffInfo.setVisibility(View.VISIBLE);


                        break;
                    case 3://商家配送中
                        if (!mdata.getData().getDetail().getPei_type().equals("3")) {
                            if (Long.parseLong(mdata.getData().getDetail().getCui_time()) > 0) {
                                stvTwo.setVisibility(View.GONE);//推单
                            } else {
                                stvTwo.setVisibility(View.VISIBLE);//推单
                            }
                            if (mdata.getData().getDetail().getPei_type().equals("1")) {
                                persongUser.setVisibility(View.VISIBLE);
                                persongUser.setText("配送人员：" + mData.getData().getDetail().getStaff().getName() + mdata.getData().getDetail().getStaff().getMobile());

                            }
                        } else {
                            STvFive.setText("确认自提");
                            stvTwo.setVisibility(View.GONE);
                            ivSaoma.setVisibility(View.VISIBLE);
                        }
                        mTvagain.setVisibility(View.GONE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.VISIBLE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        rlStaffInfo.setVisibility(View.VISIBLE);
                        staffFlag = true;


                        break;

                    case 4://商家已送达
                        mTvagain.setVisibility(View.VISIBLE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvTwo.setVisibility(View.GONE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.VISIBLE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//去评价
                        StvSeven.setVisibility(View.GONE);//查看评价
                        rlStaffInfo.setVisibility(View.GONE);
                        staffFlag = true;
                        if (mdata.getData().getDetail().getPei_type().equals("3")) {
                            ivSaoma.setVisibility(View.VISIBLE);
                            STvFive.setText("确认自提");
                        } else if (mdata.getData().getDetail().getPei_type().equals("1")) {
                            STvFive.setText("确认送达");
                            persongUser.setVisibility(View.VISIBLE);
                            persongUser.setText("配送人员：" + mData.getData().getDetail().getStaff().getName() + mdata.getData().getDetail().getStaff().getMobile());

                        } else {
                            ivSaoma.setVisibility(View.GONE);
                        }
                        break;
                    case 8://等待评价
                        mTvagain.setVisibility(View.VISIBLE);//在来一单
                        stvFour.setVisibility(View.GONE);//客服
                        stvThree.setVisibility(View.GONE);//取消订单
                        stvTwo.setVisibility(View.GONE);//推单
                        stvOne.setVisibility(View.GONE);//去支付
                        STvFive.setVisibility(View.GONE);//确认到达
                        mStvSix.setVisibility(View.GONE);//申请退款
                        StvEight.setVisibility(View.GONE);//查看评价
                        StvSeven.setVisibility(View.GONE);//去评价
                        rlStaffInfo.setVisibility(View.GONE);
                        if (mdata.getData().getDetail().getComment_status().equals("1")) {
                            StvSeven.setVisibility(View.VISIBLE);//查看评价
                            persongUser.setVisibility(View.VISIBLE);
                            persongUser.setText("配送人员：" + mData.getData().getDetail().getStaff().getName() + mdata.getData().getDetail().getStaff().getMobile());

                        } else {
                            StvEight.setVisibility(View.VISIBLE);//去评价
                        }
                        if (mdata.getData().getDetail().getPei_type().equals("3")) {
                            ivSaoma.setVisibility(View.VISIBLE);
                        } else {
                            ivSaoma.setVisibility(View.GONE);
                        }
                        break;
                }

            } else {
                if (mdata.getData().getDetail().getOrder_status().equals("-1")) {//取消订单
                    mTvagain.setVisibility(View.VISIBLE);//在来一单
                    stvFour.setVisibility(View.GONE);//客服
                    stvThree.setVisibility(View.GONE);//取消订单
                    stvTwo.setVisibility(View.GONE);//推单
                    stvOne.setVisibility(View.GONE);//去支付
                    STvFive.setVisibility(View.GONE);//确认到达
                    mStvSix.setVisibility(View.GONE);//申请退款
                    StvEight.setVisibility(View.GONE);//查看评价
                    StvSeven.setVisibility(View.GONE);//去评价
                    ivComplain.setVisibility(View.GONE);
                    ivRedbag.setVisibility(View.GONE);
                    ivSaoma.setVisibility(View.GONE);

                } else if (mdata.getData().getDetail().getOrder_status().equals("0")) {//未付款
                    mTvagain.setVisibility(View.GONE);//在来一单
                    stvFour.setVisibility(View.GONE);//客服
                    stvThree.setVisibility(View.VISIBLE);//取消订单
                    stvTwo.setVisibility(View.GONE);//推单
                    stvOne.setVisibility(View.VISIBLE);//去支付
                    STvFive.setVisibility(View.GONE);//确认到达
                    mStvSix.setVisibility(View.GONE);//申请退款
                    StvEight.setVisibility(View.GONE);//查看评价
                    StvSeven.setVisibility(View.GONE);//去评价
                    ivComplain.setVisibility(View.GONE);
                    ivRedbag.setVisibility(View.GONE);
                    ivSaoma.setVisibility(View.GONE);
                    Date dt = new Date();
                    mLayOrderDescription.setVisibility(View.VISIBLE);
                    long str = Integer.parseInt(mdata.getData().getDetail().getPay_ltime()) * 60 * 1000 - (dt.getTime() - Long.parseLong(mdata.getData().getDetail().getDateline()) * 1000);
                    countdownView.start(str);
                    countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                        @Override
                        public void onEnd(CountdownView cv) {
                            mLayOrderDescription.setVisibility(View.GONE);
                            CancelOrder2();
                        }
                    });
                    ivSaoma.setVisibility(View.GONE);
                }
            }
        }
        callPhoneDialog = new CallPhoneDialog(this, staffFlag);
    }

    private void CancelOrder2() {
        try {
            JSONObject js = new JSONObject();
            js.put("order_id", order_id);
            String str = js.toString();
            HttpUtils.postUrl(this, Api.WAIMAI_ORDER_CHARGEBACK, str, true, new OnRequestSuccessCallback() {
                @Override
                public void onSuccess(String url, String Json) {
                    if (url.equals(Api.WAIMAI_ORDER_CHARGEBACK)) {
                        Gson gson = new Gson();
                        SharedResponse mshare = gson.fromJson(Json, SharedResponse.class);
                        if (mshare.error.equals("0")) {
                            RequestData(true);
                        } else {
                            ToastUtil.show(mshare.message);
                        }

                    }
                }

                @Override
                public void onBeforeAnimate() {

                }

                @Override
                public void onErrorAnimate() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
