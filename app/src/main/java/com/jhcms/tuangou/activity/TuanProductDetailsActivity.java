package com.jhcms.tuangou.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;
import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.jhcms.common.listener.PermissionListener;
import com.jhcms.common.model.Data_Group_Good_Detail;
import com.jhcms.common.model.Response_Group_Good_Detail;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.NestedScrollView;
import com.jhcms.tuangou.CustomView.StarBar;
import com.jhcms.tuangou.adapter.TuiJianAdapter;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.ShopMapActivity;
import com.jhcms.waimaiV3.activity.ShopMapActivityGMS;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.activity.WebViewActivity;
import com.jhcms.waimaiV3.dialog.CallDialog;
import com.jhcms.waimaiV3.dialog.ShareDialog;
import com.jhcms.waimaiV3.model.ShareItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

public class TuanProductDetailsActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {
    public static String TUAN_ID = "TUAN_ID";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appBar)
    AppBarLayout appBar;
    @Bind(R.id.tv_product_name)
    TextView tvProductName;
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.tv_per_capita)
    TextView tvPerCapita;
    @Bind(R.id.tv_comm_oldPrices)
    TextView tvCommOldPrices;
    @Bind(R.id.tv_pay)
    TextView tvPay;
    @Bind(R.id.ll_title)
    LinearLayout llTitle;
    @Bind(R.id.tv_evaluation)
    TextView tvEvaluation;
    @Bind(R.id.evaluation_starBar)
    StarBar evaluationStarBar;
    @Bind(R.id.tv_evaluation_score)
    TextView tvEvaluationScore;
    @Bind(R.id.tv_evaluation_num)
    TextView tvEvaluationNum;
    @Bind(R.id.tv_shop_info)
    TextView tvShopInfo;
    @Bind(R.id.tv_shop_locatioin)
    TextView tvShopLocatioin;
    @Bind(R.id.ll_evaluation)
    LinearLayout llEvaluation;
    @Bind(R.id.ll_group)
    LinearLayout llGroup;
    @Bind(R.id.rl_tuan)
    RelativeLayout rlTuan;
    @Bind(R.id.rv_tuijian)
    RecyclerView rvTuijian;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.sv_root)
    NestedScrollView svRoot;
    @Bind(R.id.tv_float_price)
    TextView tvFloatPrice;
    @Bind(R.id.tv_float_oldPrices)
    TextView tvFloatOldPrices;
    @Bind(R.id.tv_float_pay)
    TextView tvFloatPay;
    @Bind(R.id.ll_float)
    LinearLayout llFloat;
    @Bind(R.id.content_view)
    CoordinatorLayout contentView;
    @Bind(R.id.main_multiplestatusview)
    MultipleStatusView statusview;

    @Bind(R.id.tv_product_title)
    TextView tvProductTitle;
    @Bind(R.id.tv_product_num)
    TextView tvProductNum;
    @Bind(R.id.tv_product_price)
    TextView tvProductPrice;

    @Bind(R.id.ll_product)
    LinearLayout llProduct;
    @Bind(R.id.iv_product_logo)
    ImageView ivProductLogo;

    @Bind(R.id.tv_valid_period)
    TextView tvValidPeriod;
    @Bind(R.id.tv_use_time)
    TextView tvUseTime;
    @Bind(R.id.tv_booking_line)
    TextView tvBookingLine;
    @Bind(R.id.ll_rule)
    LinearLayout llRule;
    @Bind(R.id.ll_tuijian)
    LinearLayout llTuiJian;

    private TuiJianAdapter tuiJianAdapter;
    private Data_Group_Good_Detail.DetailBean detail;
    private ShareDialog dialog;
    private ShareItem shareItems;
    private int collect = 0;
    private String tuanId;


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void initData() {
        tuanId = getIntent().getStringExtra(TUAN_ID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tuiJianAdapter = new TuiJianAdapter(this);
        rvTuijian.setAdapter(tuiJianAdapter);
        rvTuijian.setNestedScrollingEnabled(false);
        rvTuijian.setLayoutManager(new GridLayoutManager(this, 2));
        rvTuijian.addItemDecoration(Utils.setDivider(this, R.dimen.dp_050, R.color.qing));
        svRoot.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= 0) {//往上滑动
                    int height = rlTop.getHeight();
                    if (height != llContent.getPaddingTop()) {//如果滑动时高度有误先矫正高度
                        ViewGroup.LayoutParams layoutParams = rlTop.getLayoutParams();
                        layoutParams.height = llContent.getPaddingTop();
                        rlTop.setLayoutParams(layoutParams);
                    }
                    boolean overTitle = scrollY >= height;
                    llFloat.setVisibility(overTitle ? View.VISIBLE : View.GONE);
                    llTitle.setVisibility(overTitle ? View.INVISIBLE : View.VISIBLE);
                    rlTop.setVisibility(overTitle ? View.GONE : View.VISIBLE);
                    rlTop.scrollTo(0, scrollY / 3);
                } else {//下拉滑动
                    rlTop.scrollTo(0, 0);//不能有滑动偏移
                    ViewGroup.LayoutParams layoutParams = rlTop.getLayoutParams();
                    layoutParams.height = llContent.getPaddingTop() - scrollY;
                    rlTop.setLayoutParams(layoutParams);
                }
            }
        });
        requestProduct(tuanId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_group_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collect == 1) {
            /*已收藏*/
            menu.findItem(R.id.action_collected).setVisible(true);
            menu.findItem(R.id.action_collect).setVisible(false);
        } else {
            /*未收藏*/
            menu.findItem(R.id.action_collected).setVisible(false);
            menu.findItem(R.id.action_collect).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 处理分享店铺
     */
    private void initShareShop() {
        dialog = new ShareDialog(this);
        dialog.setItem(shareItems);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_collect:
            case R.id.action_collected:
                if (!TextUtils.isEmpty(Api.TOKEN)) {
                    requestCollect(Api.WAIMAI_TUAN_SHOP_COLLECT);
                } else {
                    Utils.GoLogin(this);
                }
                break;
            case R.id.action_share:
                initShareShop();
                break;
        }
        return true;
    }

    /**
     * type	是	string	类型 收藏团购商家 ‘tuan’, 收藏团购：tuan_product
     * status	是	int	要操作的状态 0；取消收藏 1；收藏
     * can_id	是	int	类型
     */
    private void requestCollect(String api) {
        JSONObject object = new JSONObject();
        try {
            object.put("type", "tuan_product");
            object.put("status", collect == 1 ? 0 : 1);
            object.put("can_id", tuanId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrl(this, api, object.toString(), true, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                try {
                    Gson gson = new Gson();
                    SharedResponse response = gson.fromJson(Json, SharedResponse.class);
                    if (response.error.equals("0")) {
                        if (collect == 0) {
                            collect = 1;
                        } else {
                            collect = 0;
                        }
                        invalidateOptionsMenu();
                    }
                    ToastUtil.show(response.message);
                } catch (Exception e) {
                    ToastUtil.show(getString(R.string.接口异常));
                    saveCrashInfo2File(e);
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

    private void requestProduct(String tuanId) {
        JSONObject object = new JSONObject();
        try {
            object.put("tuan_id", tuanId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrl(this, Api.WAIMAI_TUAN_SHOP_TUAN, object.toString(), false, this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_product_details);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_pay, R.id.ll_evaluation, R.id.tv_float_pay, R.id.iv_shop_call, R.id.ll_details, R.id.tv_shop_locatioin})
    public void onViewClicked(View view) {
        if (!Utils.isFastDoubleClick()) {
            Intent intent = new Intent();
            switch (view.getId()) {
                //TODO 立即抢购
                case R.id.tv_pay:
                case R.id.tv_float_pay:
                    if (!Utils.isFastDoubleClick()) {
                        if (!TextUtils.isEmpty(Api.TOKEN)) {
                            intent.setClass(this, TuanConfirmOrderActivity.class);
                            intent.putExtra(GraphicDetailsActivity.DETAIL, detail);
                            startActivity(intent);
                        } else {
                            Utils.GoLogin(this);
                        }
                    }
                    break;
                //TODO 评价
                case R.id.ll_evaluation:
                    intent.setClass(this, WebViewActivity.class);
                    intent.putExtra(WebViewActivity.URL, detail.comment_url);
                    startActivity(intent);
                    break;
                //TODO 商家电话
                case R.id.iv_shop_call:
                    if (!TextUtils.isEmpty(detail.shop.phone)) {
                        SwipeBaseActivity.requestRuntimePermission(new String[]{Manifest.permission.CALL_PHONE}, new PermissionListener() {
                            @Override
                            public void onGranted() {
                                new CallDialog(TuanProductDetailsActivity.this)
                                        .setMessage(detail.shop.phone)
                                        .setTipTitle("拨打商家电话")
                                        .setPositiveButton("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri
                                                        .parse("tel:" + detail.shop.phone));
                                                if (ActivityCompat.checkSelfPermission(TuanProductDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    return;
                                                }
                                                startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("取消", null)
                                        .show();
                            }

                            @Override
                            public void onDenied(List<String> permissions) {
                            }
                        });
                    }

                    break;
                //TODO 图文详细
                case R.id.ll_details:
                    intent.setClass(this, GraphicDetailsActivity.class);
                    intent.putExtra(GraphicDetailsActivity.DETAIL, detail);
                    startActivity(intent);
                    break;
                case R.id.tv_shop_locatioin:

                    if (MyApplication.MAP.equals(Api.GAODE)) {
                        CoordinateConverter converter = new CoordinateConverter(this);
                        // CoordType.GPS 待转换坐标类型
                        converter.from(CoordinateConverter.CoordType.BAIDU);
                        // sourceLatLng待转换坐标点 LatLng类型
                        converter.coord(new LatLng(Double.parseDouble(detail.shop.lat), Double.parseDouble(detail.shop.lng)));
                        // 执行转换操作
                        LatLng desLatLng = converter.convert();
                        intent.setClass(this, ShopMapActivity.class);
                        intent.putExtra("lat", desLatLng.latitude);
                        intent.putExtra("lng", desLatLng.longitude);
                    } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                        intent.setClass(this, ShopMapActivityGMS.class);
                        intent.putExtra("lat", Double.parseDouble(detail.shop.lat));
                        intent.putExtra("lng", Double.parseDouble(detail.shop.lng));
                        intent.putExtra("address", detail.shop.addr);
                    }
                    startActivity(intent);

                    break;
            }
        }
    }

    @Override
    public void onSuccess(String url, String Json) {

        Gson gson = new Gson();
        Response_Group_Good_Detail groupGoodDetail = gson.fromJson(Json, Response_Group_Good_Detail.class);
        if (groupGoodDetail.error.equals("0")) {
            detail = groupGoodDetail.data.detail;
                /*商品名*/
            toolbar.setTitle(detail.title);
            tvProductName.setText(detail.title);
                /*logo*/
            Utils.LoadStrPicture(this, Api.IMAGE_URL + detail.photo, ivProductLogo);
                /*价格*/
            tvFloatPrice.setText("￥" + detail.price);
            tvPerCapita.setText("￥" + detail.price);
            tvFloatOldPrices.setText("￥" + detail.market_price);
            tvCommOldPrices.setText("￥" + detail.market_price);

            Data_Group_Good_Detail.DetailBean.ShopBean shop = detail.shop;
                /*商户信息*/
            tvShopInfo.setText(shop.title);
            tvShopLocatioin.setText(shop.addr);

                /*评分*/
            evaluationStarBar.setStarMark(Float.valueOf(shop.avg_score));
            tvEvaluationScore.setText(shop.avg_score + "分");
            tvEvaluationNum.setText(shop.comments + "人评价");


                /*套餐详情*/
            tvProductTitle.setText(detail.title);
            tvProductNum.setText("X" + detail.min_buy);
            tvProductPrice.setText("￥" + detail.price);
            /*套餐详情*/
            dealWith(llProduct, detail.desc);


            /*相关推荐*/
            if (null != detail.tuijian && detail.tuijian.size() > 0) {
                llTuiJian.setVisibility(View.VISIBLE);
                tuiJianAdapter.setData(detail.tuijian);
            } else {
                llTuiJian.setVisibility(View.GONE);
            }
            /*是否收藏*/
            collect = detail.collect;
            invalidateOptionsMenu();

            /*分享*/
            shareItems = new ShareItem();
            shareItems.setTitle(detail.share_info.title);
            shareItems.setLogo(Api.IMAGE_URL + detail.share_info.imgUrl);
            shareItems.setUrl(detail.share_info.link);
            shareItems.setDescription(detail.share_info.desc);


            /*使用规则*/
            if (TextUtils.isEmpty(detail.info_date)) {
                tvValidPeriod.setVisibility(View.GONE);
            } else {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    tvValidPeriod.setText(Html.fromHtml(String.format("•<font color=\"#ff6600\">有效期:" + "</font>%s", detail.info_date), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    tvValidPeriod.setText(Html.fromHtml(String.format("•<font color=\"#ff6600\">有效期:" + "</font>%s", detail.info_date)));
                }
                tvValidPeriod.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(detail.shop.phone)) {
                tvBookingLine.setVisibility(View.GONE);
            } else {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    tvBookingLine.setText(Html.fromHtml(String.format("•<font color=\"#ff6600\">预约电话:" + "</font>%s", detail.shop.phone), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    tvBookingLine.setText(Html.fromHtml(String.format("•<font color=\"#ff6600\">预约电话:" + "</font>%s", detail.shop.phone)));
                }

                tvBookingLine.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(detail.info_time)) {
                tvUseTime.setVisibility(View.GONE);
            } else {
                tvUseTime.setVisibility(View.VISIBLE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    tvUseTime.setText(Html.fromHtml(String.format("•<font color=\"#ff6600\">使用时间:" + "</font>%s", detail.info_time), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    tvUseTime.setText(Html.fromHtml(String.format("•<font color=\"#ff6600\">使用时间:" + "</font>%s", detail.info_time)));
                }
            }

            /*使用规则*/
            dealWith(llRule, detail.notice);


            statusview.showContent();


        } else {
            ToastUtil.show(groupGoodDetail.message);
            statusview.showError();
        }
        try {
        } catch (Exception e) {
            ToastUtil.show(getString(R.string.接口异常));
            statusview.showError();
            statusview.showError();
            saveCrashInfo2File(e);
        }


    }


    private void dealWith(LinearLayout llRoot, List<String> desc) {
        llRoot.removeAllViews();
        if (desc.size() > 0) {
            llRoot.setVisibility(View.VISIBLE);
            for (int i = 0; i < desc.size(); i++) {
                LinearLayout firstLl = new LinearLayout(this);
                View inflate = LayoutInflater.from(this).inflate(R.layout.group_product, firstLl, false);
                TextView textView = (TextView) inflate.findViewById(R.id.tv_group_product_title);
                textView.setText(desc.get(i));
                firstLl.addView(inflate);
                llRoot.addView(firstLl);
            }
        } else {
            llRoot.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBeforeAnimate() {
        statusview.showLoading();
    }

    @Override
    public void onErrorAnimate() {
        statusview.showError();
    }
}
