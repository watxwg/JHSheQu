package com.jhcms.waimaiV3.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.jhcms.common.listener.PermissionListener;
import com.jhcms.common.model.Adv;
import com.jhcms.common.model.Banner;
import com.jhcms.common.model.IndexCate;
import com.jhcms.common.model.Response_WaiMai_Home;
import com.jhcms.common.model.ShopHongBao;
import com.jhcms.common.model.ShopItems;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.DownUtils;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.AutoScrollTextView;
import com.jhcms.shequ.activity.WaiMaiSearchGoodsActivity;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiBusinessListActivity;
import com.jhcms.waimaiV3.activity.SelectAddressActivity;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.activity.UserMsgActivity;
import com.jhcms.waimaiV3.adapter.FunctionPagerAdapter;
import com.jhcms.waimaiV3.adapter.ShopItemAdapter;
import com.jhcms.waimaiV3.dialog.HongBaoDialog;
import com.jhcms.waimaiV3.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.jhcms.common.utils.Utils.isFastDoubleClick;
import static com.jhcms.common.utils.Utils.saveCrashInfo2File;


/**
 * Created by Wyj on 2017/3/22.
 * Android 5.0以上超出边界圆形水波纹
 * android:background="?android:attr/actionBarItemBackground"
 * <p>
 * Android 5.0以上超出边界内圆形水波纹
 * ?android:attr/selectableItemBackground
 */
public class WaiMai_HomeFragment extends WaiMai_BaseFragment implements AdapterView.OnItemClickListener, Toolbar.OnMenuItemClickListener, OnRequestSuccessCallback {
    @Bind(R.id.address)
    AutoScrollTextView tvAddress;
    @Bind(R.id.home_toolbar)
    Toolbar homeToolbar;
    @Bind(R.id.shop_list)
    LRecyclerView shopList;
    @Bind(R.id.back_top_iv)
    ImageView backTopIv;

    private ShopItemAdapter shopAdapter;

    private boolean isFresh = false;//是否处在刷新状态

    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private LinearLayout headerView;
    private ConvenientBanner banner;
    private ViewPager mPager;
    private LinearLayout mLlDot;
    private ImageView ivHomeAd;
    private ImageView ad01Wai;
    private ImageView ad02Wai;
    private ImageView ad03Wai;


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private List<Banner> bannerList;
    private List<String> bannerImages;
    private List<IndexCate> indexCate;
    private List<ShopItems> shopItems;
    /**
     * 获取纬度
     */
    private double latitude;
    /**
     * 获取纬度
     */
    private double longitude;
    private Response_WaiMai_Home response;
    private List<Adv> advList;
    private int SEARCH_ADDRESS = 0x123;
    private int PLACE_PICKER_REQUEST = 0x100;
    private TextView tvMoreShop;
    public static String REFRESH_SHOPITEM = "REFRESH_SHOPITEM";

    /**
     * 谷歌
     */
    private GoogleApiClient mGoogleApiClient;
    /**
     * 是否打开谷歌地点选择界面
     */
    private boolean isStartGMSLocation = false;
    private FunctionPagerAdapter functionPagerAdapter;
    private RelativeLayout mRlVp;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_waimai_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        setToolBar(homeToolbar);
        homeToolbar.inflateMenu(R.menu.menu_search);
        homeToolbar.setNavigationOnClickListener(v -> {
            if (!isFastDoubleClick()) {
                if (TextUtils.isEmpty(Api.TOKEN)) {
                    Utils.GoLogin(getActivity());
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), UserMsgActivity.class);
                    startActivity(intent);
                }
            }
        });
        homeToolbar.setOnMenuItemClickListener(this);
        /*申请定位权限*/
        permission();
        shopAdapter = new ShopItemAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(shopAdapter);
        shopList.setAdapter(mLRecyclerViewAdapter);
        shopList.setLayoutManager(new LinearLayoutManager(getActivity()));

        homeToolbar.getBackground().mutate().setAlpha(0);

        shopList.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onScrollUp() {
            }

            @Override
            public void onScrollDown() {
            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {
                /**
                 * 刷新的时候上滑停止刷新
                 * */
                if (isFresh && distanceY > 10) {
                    shopList.refreshComplete(0);
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                }

                /**
                 * 标题栏渐变
                 * */
                if (distanceY <= banner.getHeight() && distanceY >= 0) {
                    float scale = (float) distanceY / banner.getHeight();
                    float alpha = (255 * scale);
                    tvAddress.getBackground().mutate().setAlpha((int) (255 - alpha));
                    homeToolbar.getBackground().mutate().setAlpha((int) alpha);
                    if (shopList.isOnTop()) {
                        homeToolbar.getBackground().mutate().setAlpha(0);
                    }
                } else if (distanceY > banner.getHeight()) {
                    tvAddress.getBackground().mutate().setAlpha(0);
                    homeToolbar.getBackground().mutate().setAlpha(255);
                }
                if (distanceY > headerView.getHeight() - homeToolbar.getHeight() - homeToolbar.getHeight() + Utils.dip2px(getActivity(), 15)) {
                    backTopIv.setVisibility(View.VISIBLE);
                } else {
                    backTopIv.setVisibility(View.GONE);
                }


            }

            @Override
            public void onScrollStateChanged(int state) {

            }
        });

        //TODO 添加轮播图
        headerView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_banner_header, (ViewGroup) getActivity().findViewById(android.R.id.content), false);

        banner = (ConvenientBanner) headerView.findViewById(R.id.banner);
        ivHomeAd = (ImageView) headerView.findViewById(R.id.iv_home_ad);
        mPager = (ViewPager) headerView.findViewById(R.id.viewpager);
        mLlDot = (LinearLayout) headerView.findViewById(R.id.ll_dot);
        mRlVp = (RelativeLayout) headerView.findViewById(R.id.rl_vp);

        ad01Wai = (ImageView) headerView.findViewById(R.id.ad01_wai);
        ad02Wai = (ImageView) headerView.findViewById(R.id.ad02_wai);
        ad03Wai = (ImageView) headerView.findViewById(R.id.ad03_wai);
        tvMoreShop = (TextView) headerView.findViewById(R.id.tv_more_shop);
        tvMoreShop.setOnClickListener(v -> startActivity(new Intent(getActivity(), WaiMaiBusinessListActivity.class)));

        mLRecyclerViewAdapter.addHeaderView(headerView);


        //设置头部加载颜色
        shopList.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        shopList.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        shopList.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");


        shopList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        shopList.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式


        /**
         * 下拉刷新
         * //禁用下拉刷新功能
         mRecyclerView.setPullRefreshEnabled(false);
         */
        shopList.setOnRefreshListener(() -> {
            isFresh = true;
            if (latitude == 0.0 || longitude == 0.0)
                permission();
            else
                RequestData(Api.WAIMAI_HOME, latitude, longitude);
        });
        /**
         * 加载更多
         * //禁用自动加载更多功能
         */
        shopList.setLoadMoreEnabled(false);
        //TODO 店铺点击事件
        shopAdapter.setOnClickListener(shopId -> {
            if (!isFastDoubleClick()) {
                Intent intent = new Intent(getActivity(), WaiMaiShopActivity.class);
                intent.putExtra(WaiMaiShopActivity.SHOP_ID, shopId);
                startActivity(intent);
            }
        });
    }

    /**
     * 处理轮播图
     *
     * @param images
     */
    private void initBanner(final List<String> images) {
        banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, images)
                .startTurning(2000)
                .setPageIndicator(new int[]{R.mipmap.icon_banner_normal, R.mipmap.icon_banner_choose})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(position -> {
                    if (!isFastDoubleClick()) {
                        if (!TextUtils.isEmpty(bannerList.get(position).link) && bannerList.get(position).link.startsWith("http")) {
                            Utils.dealWithHomeLink(getActivity(), bannerList.get(position).link, null);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCheckPermission) {
            permission();
            isCheckPermission = false;
        }
        if (isStartGMSLocation) {
            ProgressDialogUtil.dismiss(getActivity());
            isStartGMSLocation = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.address, R.id.back_top_iv})
    public void click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.address:
                if (MyApplication.MAP.equals(Api.GAODE)) {
                    intent.setClass(getActivity(), SelectAddressActivity.class);
                    startActivityForResult(intent, SEARCH_ADDRESS);
                } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                    ProgressDialogUtil.showProgressDialog(getActivity());
                    isStartGMSLocation = true;
                    try {
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.back_top_iv:
                shopList.smoothScrollToPosition(0);
                break;
        }
    }


    /*首页分类Item点击事件*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!isFastDoubleClick()) {
            String link = indexCate.get(position).link;
            /**
             * http://waimai.o2o.jhcms.cn/shoplist/index-1.html
             * 商家列表
             * */
            Utils.dealWithHomeLink(getActivity(), link, null);
        }
    }


    @Override
    public void onSuccess(String url, String Json) {
        switch (url) {
            case Api.WAIMAI_HOME:
                try {
                    Gson gson = new Gson();
                    response = gson.fromJson(Json, Response_WaiMai_Home.class);
                    if (response.error.equals("0")) {
                        bannerList = response.data.banner;
                        indexCate = response.data.cate_adv;
                        advList = response.data.adv;
                        /*外卖首页分类数据处理*/
                        initCate(indexCate);
                        /*外卖首页店铺列表*/
                        shopItems = response.data.shop_items;
                        if (null != bannerList && bannerList.size() > 0) {
                            bannerImages = new ArrayList<>();
                            for (int i = 0; i < bannerList.size(); i++) {
                                bannerImages.add(bannerList.get(i).thumb);
                            }
                            initBanner(bannerImages);
                        }
                        if (null != advList && advList.size() > 0) {
                            initAdv(advList);
                        }
                        /*天降红包*/
                        ShopHongBao shopHongBao = response.data.hongbao;
                        dealWith(shopHongBao);

                        shopAdapter.setDataList(shopItems);
                        /*刷新完成*/
                        shopList.refreshComplete(shopItems.size());
                        shopList.setNoMore(true);
                        isFresh = false;
                        DownUtils.getAppver(getActivity(), false);
                    } else {
                        ToastUtil.show(response.message);
                    }
                } catch (Exception e) {
                    shopList.refreshComplete(0);
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    ToastUtil.show(getString(R.string.数据解析异常));
                    saveCrashInfo2File(e);
                } finally {
                    ProgressDialogUtil.dismiss(getContext());
                }
                break;
        }
    }

    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 8;

    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;

    /**
     * @param indexCate
     */
    private void initCate(List<IndexCate> indexCate) {
        if (indexCate.size() > 0) {
            mRlVp.setVisibility(View.VISIBLE);

            //总的页数=总数/每页数量，并取整
            pageCount = (int) Math.ceil(indexCate.size() * 1.0 / pageSize);

            mLlDot.removeAllViews();

            /*小于1页时候隐藏底下的圆点*/
            if (indexCate.size() <= pageSize) {
                mLlDot.setVisibility(View.GONE);
            } else {
                mLlDot.setVisibility(View.VISIBLE);
            }

            for (int i = 0; i < pageCount; i++) {
                // 添加指示点
                ImageView point = new ImageView(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin = 8;
                point.setLayoutParams(params);
                point.setBackgroundResource(R.drawable.point_bg);
                /*默认选中第一页*/
                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    point.setEnabled(false);
                }
                mLlDot.addView(point);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.width = Utils.getScreenW(getActivity());
            if (indexCate.size() <= pageSize / 2) {
                params.height = Utils.dip2px(getActivity(), 100);
            } else if (indexCate.size() > pageSize / 2 && indexCate.size() <= pageSize) {
                params.height = Utils.dip2px(getActivity(), 180);
            } else {
                params.height = Utils.dip2px(getActivity(), 200);
            }
            mRlVp.setLayoutParams(params);
            functionPagerAdapter = new FunctionPagerAdapter(getFragmentManager(), indexCate, pageCount, pageSize, "WAIMAI");
            mPager.setAdapter(functionPagerAdapter);
            mPager.setOffscreenPageLimit(pageCount - 1);
            mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    // 把上一个点设为false
                    mLlDot.getChildAt(curIndex).setEnabled(false);
                    mLlDot.getChildAt(position).setEnabled(true);
                    curIndex = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            mRlVp.setVisibility(View.GONE);
        }
    }

    private void dealWith(ShopHongBao shopHongBao) {
        if (null != shopHongBao.items && shopHongBao.items.size() > 0) {
            /*TODO 弹出红包*/
            HongBaoDialog dialog = new HongBaoDialog(getActivity(), shopHongBao.items);
            dialog.show();

        }
    }

    private void initAdv(final List<Adv> advList) {
        if (advList.size() >= 0 && null != advList.get(0)) {
            Utils.LoadStrPicture(getActivity(), Api.IMAGE_URL + advList.get(0).thumb, ivHomeAd);
            ivHomeAd.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(advList.get(0).link) && advList.get(0).link.startsWith("http")) {
                    Utils.dealWithHomeLink(getActivity(), advList.get(0).link, null);
                }
            });
        }
        if (advList.size() >= 1 && null != advList.get(1)) {
            Utils.LoadStrPicture(getActivity(), Api.IMAGE_URL + advList.get(1).thumb, ad01Wai);
            ad01Wai.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(advList.get(1).link) && advList.get(1).link.startsWith("http")) {
                    Utils.dealWithHomeLink(getActivity(), advList.get(1).link, null);
                }
            });
        }
        if (advList.size() >= 2 && null != advList.get(2)) {
            Utils.LoadStrPicture(getActivity(), Api.IMAGE_URL + advList.get(2).thumb, ad02Wai);
            ad02Wai.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(advList.get(2).link) && advList.get(2).link.startsWith("http")) {
                    Utils.dealWithHomeLink(getActivity(), advList.get(2).link, null);
                }
            });
        }
        if (advList.size() >= 3 && null != advList.get(3)) {
            Utils.LoadStrPicture(getActivity(), Api.IMAGE_URL + advList.get(3).thumb, ad03Wai);
            ad03Wai.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(advList.get(3).link) && advList.get(3).link.startsWith("http")) {
                    Utils.dealWithHomeLink(getActivity(), advList.get(3).link, null);
                }
            });
        }

    }

    @Override
    public void onBeforeAnimate() {
    }

    @Override
    public void onErrorAnimate() {
        shopList.refreshComplete(0);
        isFresh = false;
    }


    public class LocalImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Utils.LoadStrPicture(context, Api.IMAGE_URL + data, imageView);
        }
    }

    /**
     * ScrollView 滚动到顶部
     *
     * @param
     */
    public void scrollToTop() {
        if (!isFresh) {
            shopList.smoothScrollToPosition(0);
            shopList.refresh();
        }
    }

    private void permission() {
        SwipeBaseActivity.requestRuntimePermission(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, new PermissionListener() {
            @Override
            public void onGranted() {
                ProgressDialogUtil.showProgressDialog(getActivity());
                initLocation();
            }

            @Override
            public void onDenied(List<String> permissions) {
                showMissingPermissionDialog();
            }
        });
    }

    private void initLocation() {
        if (MyApplication.MAP.equals(Api.GOOGLE)) {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(getActivity())
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(getActivity(), connectionResult -> {
                    })
                    .build();


            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
            result.setResultCallback(placeLikelihoods -> {
                for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                    //定位成功回调信息，设置相关消息
                    latitude = placeLikelihood.getPlace().getLatLng().latitude;//获取纬度
                    longitude = placeLikelihood.getPlace().getLatLng().longitude;//获取经度
                    Api.LAT = latitude;
                    Api.LON = longitude;
                    tvAddress.setText(placeLikelihood.getPlace().getName());
                    ProgressDialogUtil.dismiss(getContext());
                    shopList.refresh();
                    return;
                }
                placeLikelihoods.release();
            });
        } else if (MyApplication.MAP.equals(Api.GAODE)) {
            //初始化定位
            mLocationClient = new AMapLocationClient(getActivity());
            //设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //获取一次定位结果：
            //该方法默认为false。
            mLocationOption.setOnceLocation(true);
            //获取最近3s内精度最高的一次定位结果：
            mLocationOption.setOnceLocationLatest(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置是否允许模拟位置,默认为false，不允许模拟位置
            mLocationOption.setMockEnable(false);
            //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
            mLocationOption.setHttpTimeOut(10000);
            //关闭缓存机制
            mLocationOption.setLocationCacheEnable(false);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();
        }


    }


    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    latitude = aMapLocation.getLatitude();
                    longitude = aMapLocation.getLongitude();
                    double[] doubles = Utils.gd_To_Bd(latitude, longitude);
                    Api.LAT = latitude = doubles[0];
                    Api.LON = longitude = doubles[1];
                    String address = aMapLocation.getAddress();//地址
                    String cityCode = aMapLocation.getCityCode();//城市编码
                    String aoiName = aMapLocation.getAoiName();//获取当前定位点的AOI信息
                    if (TextUtils.isEmpty(aoiName)) {
                        tvAddress.setText(address);
                    } else {
                        tvAddress.setText(aoiName);
                    }
                    ProgressDialogUtil.dismiss(getContext());
                    RequestData(Api.WAIMAI_HOME, latitude, longitude);
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    ToastUtil.show(getString(R.string.获取位置失败));
                    ProgressDialogUtil.dismiss(getContext());
                    shopList.refreshComplete(0);
                    LogUtil.e("location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    private void RequestData(String api, double latitude, double longitude) {

        JSONObject object = new JSONObject();
        try {
            object.put("lng", longitude);
            object.put("lat", latitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(getActivity(), api, str, true, this);
    }

    /**
     * 返回的全是百度经纬度
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            /**
             * 高德 地址选择界面返回
             * */
            if (requestCode == SEARCH_ADDRESS) {
                String location = data.getStringExtra("address");
                tvAddress.setText(location);
                Api.LAT = latitude = data.getDoubleExtra("lat", 0.0);
                Api.LON = longitude = data.getDoubleExtra("lng", 0.0);
                shopList.refresh();
            } else if (requestCode == PLACE_PICKER_REQUEST) {
                /*谷歌选择地点返回结果*/
                ProgressDialogUtil.dismiss(getActivity());
                Place place = PlacePicker.getPlace(getActivity(), data);
                tvAddress.setText(place.getName());
                Api.LAT = latitude = place.getLatLng().latitude;
                Api.LON = longitude = place.getLatLng().longitude;
                shopList.refresh();
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            startActivity(new Intent(getActivity(), WaiMaiSearchGoodsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(REFRESH_SHOPITEM)) {
            shopAdapter.notifyDataSetChanged();
        }
    }

}