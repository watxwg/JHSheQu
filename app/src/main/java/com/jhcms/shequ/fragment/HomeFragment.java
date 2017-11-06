package com.jhcms.shequ.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.jhcms.common.listener.PermissionListener;
import com.jhcms.common.model.Adv;
import com.jhcms.common.model.Banner;
import com.jhcms.common.model.Data_WaiMai_Home;
import com.jhcms.common.model.IndexCate;
import com.jhcms.common.model.Response_WaiMai_Home;
import com.jhcms.common.model.ShopItems;
import com.jhcms.common.model.ShopLikes;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.AutoScrollTextView;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.mall.activity.MallActivity;
import com.jhcms.shequ.activity.WaiMaiSearchGoodsActivity;
import com.jhcms.shequ.adapter.AdvAdapter;
import com.jhcms.shequ.adapter.ModuleAdapter;
import com.jhcms.shequ.adapter.YouLikeAdapterAdapter;
import com.jhcms.shequ.weight.Category;
import com.jhcms.shequ.weight.HomeAdapter;
import com.jhcms.shequ.weight.ListTypeFactory;
import com.jhcms.shequ.weight.Product;
import com.jhcms.shequ.weight.ProductList;
import com.jhcms.shequ.weight.Visitable;
import com.jhcms.tuangou.activity.TuanGouMainActivity;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiBusinessListActivity;
import com.jhcms.waimaiV3.activity.SelectAddressActivity;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.activity.UserMsgActivity;
import com.jhcms.waimaiV3.activity.WaiMaiMainActivity;
import com.jhcms.waimaiV3.adapter.ShopItemAdapter;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;

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
 * Created by Administrator on 2017/3/22.
 */
public class HomeFragment extends WaiMai_BaseFragment implements Toolbar.OnMenuItemClickListener, OnRequestSuccessCallback {
    @Bind(R.id.lRecycleView)
    LRecyclerView lRecycleView;
    @Bind(R.id.address)
    AutoScrollTextView mAddressTv;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.back_top_iv)
    ImageView backTopIv;


    private ConvenientBanner banner;
    private GridViewForScrollView titleGv;
    private ViewFlipper marqueeView;
    private ImageView ivHomeAd, ivNewMore;
    private LinearLayout llNearbyShops;
    private RecyclerView rvNearbyShops;
    private LinearLayout llMorenearbyShops;
    private LinearLayout llYouLike;
    private RecyclerView rvYouLike;

    /*轮播图图片资源*/
    List<String> bannerImages;

    ModuleAdapter moduleAdapter;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    /*广告位接口数据*/
    private List<Adv> advsList;
    /*分类接口数据*/
    private List<IndexCate> indexCate;
    /*轮播图接口数据*/
    private List<Banner> bannersList;
    /*商户接口数据*/
    private List<ShopItems> shopItems;
    private LinearLayout homeHeaderView;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private HomeAdapter homeAdapter;
    private List<Visitable> mVisitables;
    private ShopItemAdapter nearbyAdapter;
    private YouLikeAdapterAdapter youLikeAdapter;
    private Response_WaiMai_Home response;
    private RecyclerView recyclerView;
    private AdvAdapter advAdapter;
    private boolean isStartGMSLocation = false;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        setToolBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_search);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(v -> {
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
        /*标题栏渐变*/
        onScrollChange();
         /*申请定位权限*/
        permission();
        lRecycleView.setFocusable(false);
        lRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        homeAdapter = new HomeAdapter(new ListTypeFactory());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(homeAdapter);
        lRecycleView.setAdapter(lRecyclerViewAdapter);

        //TODO 设置刷新样式
        //设置头部加载颜色
        lRecycleView.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        lRecycleView.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        lRecycleView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        lRecycleView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        lRecycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式


        lRecyclerViewAdapter.setSpanSizeLookup((gridLayoutManager, position) -> {
            Object item = mVisitables.get(position);
            return (item instanceof ProductList || item instanceof Category) ? gridLayoutManager.getSpanCount() : 1;

        });

        homeHeaderView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_header, (ViewGroup) getActivity().findViewById(android.R.id.content), false);

        banner = (ConvenientBanner) homeHeaderView.findViewById(R.id.banner);
        titleGv = (GridViewForScrollView) homeHeaderView.findViewById(R.id.title_gv);
        titleGv.setFocusable(false);
        marqueeView = (ViewFlipper) homeHeaderView.findViewById(R.id.marquee_view);
        ivHomeAd = (ImageView) homeHeaderView.findViewById(R.id.iv_home_ad);
        ivNewMore = (ImageView) homeHeaderView.findViewById(R.id.iv_new_more);

        /*五张广告位图片*/
        recyclerView = (RecyclerView) homeHeaderView.findViewById(R.id.recyclerView);
        GridLayoutManager layoutManage = new GridLayoutManager(getActivity(), 6);
        layoutManage.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            /**
             * @param position
             * @return 合并列
             */
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 1 || position == 2)
                    return 2;
                return 3;
            }
        });
        recyclerView.setLayoutManager(layoutManage);
        recyclerView.setNestedScrollingEnabled(false);
        advAdapter = new AdvAdapter(getActivity());
        recyclerView.setAdapter(advAdapter);

        /*首页分类*/
        titleGv.setFocusable(false);
        moduleAdapter = new ModuleAdapter(getActivity());
        titleGv.setAdapter(moduleAdapter);

        titleGv.setOnItemClickListener((parent, view3, position, id) -> {
            Intent intent = new Intent();
            if (indexCate.get(position).link.contains("waimai")) {
                intent.setClass(getActivity(), WaiMaiMainActivity.class);
            } else if (indexCate.get(position).link.contains("mall")) {
                intent.setClass(getActivity(), MallActivity.class);
            } else if (indexCate.get(position).link.contains("tuan")) {
                intent.setClass(getActivity(), TuanGouMainActivity.class);
            } else {
                return;
            }
            startActivity(intent);
        });


        lRecyclerViewAdapter.addHeaderView(homeHeaderView);


        View footer = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_footer, null);

        llNearbyShops = (LinearLayout) footer.findViewById(R.id.ll_nearby_shops);
        rvNearbyShops = (RecyclerView) footer.findViewById(R.id.rv_nearby_shops);
        llMorenearbyShops = (LinearLayout) footer.findViewById(R.id.ll__morenearby_shops);
        llYouLike = (LinearLayout) footer.findViewById(R.id.ll_you_like);
        rvYouLike = (RecyclerView) footer.findViewById(R.id.rv_you_like);


        rvNearbyShops.setNestedScrollingEnabled(false);
        rvYouLike.setNestedScrollingEnabled(false);


        nearbyAdapter = new ShopItemAdapter(getActivity());
        rvNearbyShops.setAdapter(nearbyAdapter);
        nearbyAdapter.setOnClickListener(shopId -> {
            if (!isFastDoubleClick()) {
                Intent intent = new Intent(getActivity(), WaiMaiShopActivity.class);
                intent.putExtra(WaiMaiShopActivity.SHOP_ID, shopId);
                startActivity(intent);
            }
        });
        rvNearbyShops.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));

        llMorenearbyShops.setOnClickListener(v -> startActivity(new Intent(getActivity(), WaiMaiBusinessListActivity.class)));


        youLikeAdapter = new YouLikeAdapterAdapter(getActivity());
        rvYouLike.setAdapter(youLikeAdapter);
        rvYouLike.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        lRecyclerViewAdapter.addFooterView(footer);

        lRecycleView.setOnRefreshListener(() -> {
            if (latitude == 0.0 || longitude == 0.0)
                permission();
            else
                RequestData(Api.CLIENT_HOME, latitude, longitude);

        });

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


    private void onScrollChange() {
        toolbar.getBackground().mutate().setAlpha(0);
        lRecycleView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {
                /**
                 * 顶部透明度渐变
                 */
                if (null != banner && banner.getHeight() > 0) {
                    int height = banner.getHeight();
                    if (distanceY < height) {
                        int alpha = (int) (new Float(distanceY) / new Float(height) * 255);
                        if (alpha <= 0) {
                            alpha = 0;
                        }
                        toolbar.getBackground().mutate().setAlpha(alpha);
                        mAddressTv.getBackground().mutate().setAlpha(255 - alpha);
                    } else {
                        mAddressTv.getBackground().mutate().setAlpha(0);
                        toolbar.getBackground().mutate().setAlpha(255);
                    }
                }
                int screenH = Utils.getScreenH(getActivity()) * 2 / 3;
                if (distanceY < screenH) {
                    backTopIv.setVisibility(View.GONE);
                } else {
                    backTopIv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(int state) {

            }
        });

    }


    // 轮播图
    private void onBanner(final List<Banner> banners) {
        bannerImages = new ArrayList<>();
        for (int i = 0; i < banners.size(); i++) {
            bannerImages.add(banners.get(i).thumb);
        }

        banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, bannerImages)
                .startTurning(3000)
                .setPageIndicator(new int[]{R.mipmap.icon_banner_normal, R.mipmap.icon_banner_choose})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(position -> {
                    if (!Utils.isFastDoubleClick()) {
                        Utils.dealWithHomeLink(getActivity(), banners.get(position).link, null);
                    }
                });
    }

    private int SEARCH_ADDRESS = 0x123;
    private int PLACE_PICKER_REQUEST = 0x100;

    @OnClick({R.id.back_top_iv, R.id.address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_top_iv:
                lRecycleView.smoothScrollToPosition(0);
                break;
            case R.id.address:/*定位*/
                Intent intent = new Intent();
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
    public void onSuccess(String url, String Json) {
        switch (url) {
            case Api.CLIENT_HOME:
                try {
                    Gson gson = new Gson();
                    response = gson.fromJson(Json, Response_WaiMai_Home.class);
                    if (response.error.equals("0")) {
                        advsList = response.data.advs;
                        /*五张广告位*/
                        advAdapter.setAdv(advsList);

                        List<Data_WaiMai_Home.News> news = response.data.news;
                        /*今日头条*/
                        initNews(news);

                        Data_WaiMai_Home.AdvButton advButtom = response.data.adv_buttom;
                        /*今日头条下面的广告位*/
                        initAdvButtom(advButtom);

                        indexCate = response.data.index_cate;
                        /*分类*/
                        moduleAdapter.setData(indexCate);

                        bannersList = response.data.banners;
                        /*轮播图*/
                        onBanner(bannersList);

                        shopItems = response.data.items;
                        /*附近商家*/
                        nearbyAdapter.setDataList(shopItems);
                        /*猜你喜欢*/
                        List<ShopLikes> likes = response.data.likes;
                        youLikeAdapter.setDataList(likes);
                        youLikeAdapter.notifyDataSetChanged();

                        /*活动*/
                        List<Data_WaiMai_Home.HuoDong> huodong = response.data.huodong;
                        initHuoDong(huodong);
                        lRecycleView.refreshComplete(huodong.size());
                        lRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.show(response.message);
                    }
                } catch (Exception e) {
                    ToastUtil.show(getString(R.string.数据解析异常));
                    saveCrashInfo2File(e);
                } finally {
                    lRecycleView.refreshComplete(0);
                    lRecyclerViewAdapter.notifyDataSetChanged();
                    ProgressDialogUtil.dismiss(getContext());
                }

                break;
        }
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

    private void initHuoDong(List<Data_WaiMai_Home.HuoDong> huodong) {
        if (null != huodong && huodong.size() > 0) {
            List<Product> products;
            mVisitables = new ArrayList<>();
            for (int i = 0; i < huodong.size(); i++) {
                products = new ArrayList<>();
                mVisitables.add(new Category(huodong.get(i).title, huodong.get(i).more_url));
                List<Data_WaiMai_Home.HuoDong.ItemsBean> items = huodong.get(i).items;
                for (int j = 0; j < items.size(); j++) {
                    Product product = new Product();
                    product.setActive_id(items.get(j).active_id);
                    product.setLink(items.get(j).link);
                    product.setThumb(items.get(j).thumb);
                    products.add(product);
                }
                mVisitables.add(new ProductList(products));
            }
            homeAdapter.setData(mVisitables);
        }
    }

    private void initNews(List<Data_WaiMai_Home.News> news) {
        if (null != news && news.size() > 0) {
            for (int i = 0; i < news.size(); i++) {
                TextView view = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.shequnoticelayout, null);
                String title = news.get(i).title;
                String link = news.get(i).link;
                view.setText(title);
                marqueeView.addView(view);
                view.setOnClickListener(v -> Utils.dealWithHomeLink(getActivity(), link, null));
            }
        }
        ivNewMore.setOnClickListener(v -> Utils.dealWithHomeLink(getActivity(), response.data.news_more, null));
    }

    private void initAdvButtom(Data_WaiMai_Home.AdvButton advButtom) {
        Utils.LoadStrPicture(getActivity(), Api.IMAGE_URL + advButtom.thumb, ivHomeAd);
        ivHomeAd.setOnClickListener(v -> {
            Utils.dealWithHomeLink(getActivity(), advButtom.link, null);
        });
    }

    @Override
    public void onBeforeAnimate() {
    }

    @Override
    public void onErrorAnimate() {
        lRecycleView.refreshComplete(0);
        lRecyclerViewAdapter.notifyDataSetChanged();
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

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(10000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        mLocationOption.setOnceLocationLatest(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private double latitude;
    private double longitude;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    latitude = aMapLocation.getLatitude();//获取纬度
                    longitude = aMapLocation.getLongitude();//获取经度
                    double[] doubles = Utils.gd_To_Bd(latitude, longitude);
                    Api.LAT = latitude = doubles[0];
                    Api.LON = longitude = doubles[1];
                    String address = aMapLocation.getAddress();//地址
                    String aoiName = aMapLocation.getAoiName();//获取当前定位点的AOI信息
                    if (TextUtils.isEmpty(aoiName)) {
                        mAddressTv.setText(address);
                    } else {
                        mAddressTv.setText(aoiName);
                    }
                    ProgressDialogUtil.dismiss(getContext());
                    handler.sendEmptyMessage(0);
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    ToastUtil.show(getString(R.string.获取位置失败));
                    ProgressDialogUtil.dismiss(getContext());
                    lRecycleView.refreshComplete(0);
                    LogUtil.e("location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    RequestData(Api.CLIENT_HOME, latitude, longitude);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            /**
             * 高德 地址选择界面返回
             * */
            if (requestCode == SEARCH_ADDRESS) {
                String location = data.getStringExtra("address");
                mAddressTv.setText(location);
                Api.LAT = latitude = data.getDoubleExtra("lat", 0.0);
                Api.LON = longitude = data.getDoubleExtra("lng", 0.0);
            } else if (requestCode == PLACE_PICKER_REQUEST) {
                /*谷歌选择地点返回结果*/
                ProgressDialogUtil.dismiss(getActivity());
                Place place = PlacePicker.getPlace(getActivity(), data);
                mAddressTv.setText(place.getName());
                Api.LAT = latitude = place.getLatLng().latitude;
                Api.LON = longitude = place.getLatLng().longitude;
            }
            lRecycleView.refresh();
        }
    }

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


}
