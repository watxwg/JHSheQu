package com.jhcms.tuangou.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.coorchice.library.SuperTextView;
import com.google.gson.Gson;
import com.jhcms.common.model.Data_Group_Shop_Detail;
import com.jhcms.common.model.Response_Group_Shop_Detail;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.NestedScrollView;
import com.jhcms.tuangou.CustomView.StarBar;
import com.jhcms.tuangou.adapter.SonRvAdapter;
import com.jhcms.tuangou.adapter.TuiJianAdapter;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
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

public class TuanShopDetailActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {
    public static String SHOP_ID = "SHOP_ID";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appBar)
    AppBarLayout appBar;
    @Bind(R.id.iv_shop_logo)
    ImageView ivShopLogo;
    @Bind(R.id.tv_photo_count)
    TextView tvPhotoCount;
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.starBar)
    StarBar starBar;
    @Bind(R.id.tv_shop_score)
    TextView tvShopScore;
    @Bind(R.id.tv_per_capita)
    TextView tvPerCapita;
    @Bind(R.id.tv_pay)
    TextView tvPay;
    @Bind(R.id.ll_title)
    RelativeLayout llTitle;
    @Bind(R.id.tv_shop_locatioin)
    TextView tvShopLocatioin;
    @Bind(R.id.iv_shop_call)
    ImageView ivShopCall;
    @Bind(R.id.ll_group)
    LinearLayout llGroup;
    @Bind(R.id.rv_shop_group)
    RecyclerView rvShopGroup;
    @Bind(R.id.tv_more_num)
    TextView tvMoreNum;
    @Bind(R.id.loadmorelayout)
    LinearLayout loadmorelayout;
    @Bind(R.id.user_starBar)
    StarBar userStarBar;
    @Bind(R.id.tv_user_score)
    TextView tvUserScore;
    @Bind(R.id.tv_business_hours)
    TextView tvBusinessHours;
    @Bind(R.id.ll_business_hours)
    LinearLayout llBusinessHours;
    @Bind(R.id.tv_free_parking)
    TextView tvFreeParking;
    @Bind(R.id.ll_free_parking)
    LinearLayout llFreeParking;
    @Bind(R.id.tv_wi_fi)
    TextView tvWiFi;
    @Bind(R.id.ll_wi_fi)
    LinearLayout llWiFi;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.sv_root)
    NestedScrollView svRoot;
    @Bind(R.id.tv_float_price)
    TextView tvFloatPrice;
    @Bind(R.id.tv_float_pay)
    TextView tvFloatPay;
    @Bind(R.id.ll_float)
    LinearLayout llFloat;
    @Bind(R.id.content_view)
    CoordinatorLayout rootLayout;
    @Bind(R.id.main_multiplestatusview)
    MultipleStatusView statusview;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.stv_wai)
    SuperTextView stvWai;
    @Bind(R.id.stv_tuan)
    SuperTextView stvTuan;
    @Bind(R.id.ll_waimai)
    LinearLayout llWaimai;
    @Bind(R.id.tv_waimai)
    TextView tvWaimai;
    @Bind(R.id.tv_tuan)
    TextView tvTuan;
    @Bind(R.id.rl_tuan)
    RelativeLayout rlTuan;
    @Bind(R.id.tv_comments_num)
    TextView tvCommentsNum;
    @Bind(R.id.tv_press)
    TextView tv_press;

    @Bind(R.id.rv_tuijian)
    RecyclerView rvTuijian;
    @Bind(R.id.ll_more_info)
    LinearLayout llMoreInfo;
    @Bind(R.id.ll_tuijian)
    LinearLayout llTuiJian;
    private String shopId;
    private SonRvAdapter sonAdapter;
    private TuiJianAdapter tuiJianAdapter;
    private int collect;

    private ShareDialog dialog;
    private ShareItem shareItems;
    private String phone;
    private Data_Group_Shop_Detail.DetailBean shopDetail;


    @Override
    protected void initView() {
        setContentView(R.layout.tuan_activity_shop_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        shopId = getIntent().getStringExtra(SHOP_ID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sonAdapter = new SonRvAdapter(this);
        rvShopGroup.setAdapter(sonAdapter);
        rvShopGroup.setNestedScrollingEnabled(false);
        rvShopGroup.setLayoutManager(new LinearLayoutManager(this));
        rvShopGroup.addItemDecoration(Utils.setDivider(this, R.dimen.dp_050, R.color.qing));

        tuiJianAdapter = new TuiJianAdapter(this);
        rvTuijian.setAdapter(tuiJianAdapter);
        rvTuijian.setLayoutManager(new GridLayoutManager(this, 2));
        rvTuijian.setNestedScrollingEnabled(false);
        rvTuijian.addItemDecoration(Utils.setDivider(this, R.dimen.dp_050, R.color.qing));

        requestShopDetail(Api.WAIMAI_TUAN_SHOP_DETAIL, shopId);
        svRoot.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= 0) {//往上滑动
                    int height = rlTop.getHeight();
                    if (height != tv_press.getHeight()) {//如果滑动时高度有误先矫正高度
                        ViewGroup.LayoutParams layoutParams = rlTop.getLayoutParams();
                        layoutParams.height = tv_press.getHeight();
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
                    layoutParams.height = tv_press.getHeight() - scrollY;
                    rlTop.setLayoutParams(layoutParams);
                }
            }
        });


    }

    @OnClick({R.id.tv_pay, R.id.tv_press, R.id.iv_shop_call, R.id.ll_waimai, R.id.tv_float_pay, R.id.loadmorelayout, R.id.tv_shop_locatioin, R.id.ll_user_comment})
    public void onViewClicked(View view) {
        if (!Utils.isFastDoubleClick()) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.tv_press:
                    intent.setClass(this, MerchantAlbumActivity.class);
                    intent.putExtra(MerchantAlbumActivity.SHOP_ID, shopId);
                    startActivity(intent);
                    break;
                case R.id.iv_shop_call:
                    new CallDialog(this)
                            .setMessage(phone)
                            .setTipTitle("拨打商家电话")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri
                                            .parse("tel:" + phone));
                                    if (ActivityCompat.checkSelfPermission(TuanShopDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
                //TODO 跳到外卖
                case R.id.ll_waimai:
                    intent.setClass(this, WaiMaiShopActivity.class);
                    intent.putExtra(WaiMaiShopActivity.SHOP_ID, shopId);
                    startActivity(intent);
                    break;
                //TODO 买单
                case R.id.tv_pay:
                case R.id.tv_float_pay:
                    if (!Utils.isFastDoubleClick()) {
                        intent.setClass(this, TuanOfferToPayActivity.class);
                        intent.putExtra(TuanOfferToPayActivity.TYPE, shopDetail.options.type);
                        intent.putExtra(TuanOfferToPayActivity.SHOPDETAIL, shopDetail);
                        startActivity(intent);
                    }
                    break;
                case R.id.loadmorelayout:
                    intent.setClass(this, TuanShopAllGoodsActivity.class);
                    intent.putExtra(TuanShopAllGoodsActivity.SHOP_ID, shopId);
                    startActivity(intent);
                    break;
                case R.id.tv_shop_locatioin:
                    if (MyApplication.MAP.equals(Api.GAODE)) {
                        CoordinateConverter converter = new CoordinateConverter(this);
                        // CoordType.GPS 待转换坐标类型
                        converter.from(CoordinateConverter.CoordType.BAIDU);
                        // sourceLatLng待转换坐标点 LatLng类型
                        converter.coord(new LatLng(Double.parseDouble(shopDetail.lat), Double.parseDouble(shopDetail.lng)));
                        // 执行转换操作
                        LatLng desLatLng = converter.convert();
                        intent.setClass(this, ShopMapActivity.class);
                        intent.putExtra("lat", desLatLng.latitude);
                        intent.putExtra("lng", desLatLng.longitude);
                    } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                        intent.setClass(this, ShopMapActivityGMS.class);
                        intent.putExtra("lat", Double.parseDouble(shopDetail.lat));
                        intent.putExtra("lng", Double.parseDouble(shopDetail.lng));
                        intent.putExtra("address", shopDetail.addr);
                    }
                    startActivity(intent);
                    break;
                case R.id.ll_user_comment:
                    intent.setClass(this, WebViewActivity.class);
                    intent.putExtra(WebViewActivity.URL, shopDetail.comment_url);
                    startActivity(intent);
                    break;
            }
        }

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
            object.put("type", "tuan");
            object.put("status", collect == 1 ? 0 : 1);
            object.put("can_id", shopId);
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

    /**
     * 处理分享店铺
     */
    private void initShareShop() {
        dialog = new ShareDialog(this);
        dialog.setItem(shareItems);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void requestShopDetail(String api, String shopId) {
        JSONObject object = new JSONObject();
        try {
            object.put("shop_id", shopId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, api, str, false, this);
    }


    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Response_Group_Shop_Detail groupShopDetail = gson.fromJson(Json, Response_Group_Shop_Detail.class);
            if (groupShopDetail.error.equals("0")) {
                shopDetail = groupShopDetail.data.detail;
            /*标题*/
                toolbar.setTitle(shopDetail.title);
            /*收藏*/
                collect = shopDetail.collect;
                invalidateOptionsMenu();
                phone = shopDetail.phone;
            /*分享*/
                shareItems = new ShareItem();

                shareItems.setTitle(shopDetail.share_info.title);
                shareItems.setLogo(Api.IMAGE_URL + shopDetail.share_info.imgUrl);
                shareItems.setUrl(shopDetail.share_info.link);
                shareItems.setDescription(shopDetail.share_info.desc);
                /*商家图片*/
                Utils.LoadStrPicture(this, Api.IMAGE_URL + shopDetail.banner, ivShopLogo);
                /*商家相册数量*/
                tvPhotoCount.setText(shopDetail.photo_count + "张");
                /*商家名称*/
                tvShopName.setText(shopDetail.title);
                /*商家评分*/
                starBar.setStarMark(TextUtils.isEmpty(shopDetail.avg_score) ? 0 : Float.parseFloat(shopDetail.avg_score));
                tvShopScore.setText(TextUtils.isEmpty(shopDetail.avg_score) ? "0" : shopDetail.avg_score + "分");
                /*人均*/
                tvFloatPrice.setText(shopDetail.avg_amount);
                tvPerCapita.setText("人均：￥" + shopDetail.avg_amount);
            /*买单*/
                if (shopDetail.have_maidan.equals("0")) {
                    tvFloatPay.setVisibility(View.GONE);
                    tvPay.setVisibility(View.GONE);
                } else {
                    tvFloatPay.setVisibility(View.VISIBLE);
                    tvPay.setVisibility(View.VISIBLE);
                }
                /*店铺位置*/
                tvShopLocatioin.setText(shopDetail.addr);
            /*外卖*/
                if (null != shopDetail.waimai && !TextUtils.isEmpty(shopDetail.waimai.title)) {
                    llWaimai.setVisibility(View.VISIBLE);
                    stvWai.setText(shopDetail.waimai.word);
                    tvWaimai.setText(shopDetail.waimai.title);
                    stvWai.setSolid(Color.parseColor("#" + shopDetail.waimai.color));
                } else {
                    llWaimai.setVisibility(View.GONE);
                }
            /*团购*/
                if (null != shopDetail.tuan && !TextUtils.isEmpty(shopDetail.tuan.title)) {
                    rlTuan.setVisibility(View.VISIBLE);
                    stvTuan.setText(shopDetail.tuan.word);
                    tvTuan.setText(shopDetail.tuan.title);
                    stvTuan.setSolid(Color.parseColor("#" + shopDetail.tuan.color));
                    List<Data_Group_Shop_Detail.DetailBean.TuanBean.ProductBean> productBeanList = shopDetail.tuan.product;
                    sonAdapter.setData(productBeanList);
                    if (shopDetail.other_count > 0) {
                        loadmorelayout.setVisibility(View.VISIBLE);
                        tvMoreNum.setText("其他" + shopDetail.other_count + "个商品");
                    } else {
                        loadmorelayout.setVisibility(View.GONE);
                    }
                } else {
                    rlTuan.setVisibility(View.GONE);
                }
            /*用户评价打分*/
                userStarBar.setStarMark(TextUtils.isEmpty(shopDetail.avg_score) ? 0 : Float.parseFloat(shopDetail.avg_score));
            /*用户分*/
                tvUserScore.setText(TextUtils.isEmpty(shopDetail.avg_score) ? "0" : shopDetail.avg_score + "分");
            /*评价数*/
                tvCommentsNum.setText(shopDetail.comments + "人评价");
                /*更多信息*/
                if ("0".equals(shopDetail.is_cart) && "0".equals(shopDetail.is_wifi)) {
                    llMoreInfo.setVisibility(View.GONE);
                } else {
                    llMoreInfo.setVisibility(View.VISIBLE);
                }
            /*is_wifi*/
                if (shopDetail.is_wifi.equals("1")) {
                    llWiFi.setVisibility(View.VISIBLE);
                } else {
                    llWiFi.setVisibility(View.GONE);
                }
            /*is_cart*/
                if (shopDetail.is_cart.equals("1")) {
                    llFreeParking.setVisibility(View.VISIBLE);
                } else {
                    llFreeParking.setVisibility(View.GONE);
                }

            /*推荐*/
                List<Data_Group_Shop_Detail.DetailBean.TuijianBean> tuijianBeen = shopDetail.tuijian;
                if (null != tuijianBeen && tuijianBeen.size() > 0) {
                    llTuiJian.setVisibility(View.VISIBLE);
                    tuiJianAdapter.setData(tuijianBeen);
                } else {
                    llTuiJian.setVisibility(View.GONE);
                }

                statusview.showContent();
            } else {
                statusview.showError();
                ToastUtil.show(groupShopDetail.message);
            }

        } catch (Exception e) {
            statusview.showError();
            ToastUtil.show(getString(R.string.接口异常));
            saveCrashInfo2File(e);
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
