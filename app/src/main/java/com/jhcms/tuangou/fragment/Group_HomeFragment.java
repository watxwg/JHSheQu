package com.jhcms.tuangou.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
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
import com.jhcms.common.model.Data_Group_Home;
import com.jhcms.common.model.IndexCate;
import com.jhcms.common.model.Response_Group_Home;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.AutoScrollTextView;
import com.jhcms.tuangou.activity.TuanSearchActivity;
import com.jhcms.tuangou.activity.TuanSearchGoodsActivity;
import com.jhcms.tuangou.adapter.AdapterGroupAdv;
import com.jhcms.tuangou.adapter.ParentAdapter;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SelectAddressActivity;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.adapter.FunctionPagerAdapter;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.jhcms.common.utils.Utils.isFastDoubleClick;


/**
 * Created by wangyujie
 * Date 2017/7/13.
 * TODO 团购首页
 */

public class Group_HomeFragment extends WaiMai_BaseFragment implements OnRequestSuccessCallback, Toolbar.OnMenuItemClickListener {

    @Bind(R.id.shop_list)
    LRecyclerView shopList;
    @Bind(R.id.address)
    AutoScrollTextView mAddress;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private List<String> images;
    private ParentAdapter mparenApapter;
    private List<Data_Group_Home.BannerEntity> banners;
    private List<IndexCate> indexCate;
    private FunctionPagerAdapter functionPagerAdapter;
    private AdapterGroupAdv adapterGroupAdv;
    /**
     * 谷歌
     */
    private GoogleApiClient mGoogleApiClient;
    /**
     * 是否打开谷歌地点选择界面
     */
    private boolean isStartGMSLocation = false;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    /**
     * 获取纬度
     */
    private double latitude;
    /**
     * 获取纬度
     */
    private double longitude;
    private int SEARCH_ADDRESS = 0x123;
    private int PLACE_PICKER_REQUEST = 0x100;
    private LinearLayout headerView;


    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ConvenientBanner mBanner;
    private RelativeLayout rlVp;
    private ViewPager viewpager;
    private LinearLayout llDot;
    private RecyclerView advGv;
    private TextView tvMoreShop;
    private boolean isFresh = false;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_group_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        setToolBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_search);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(v -> getActivity().finish());
        toolbar.getBackground().mutate().setAlpha(0);
        /*申请定位权限*/
        permission();
        mparenApapter = new ParentAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mparenApapter);
        shopList.setFocusable(false);
        shopList.setAdapter(mLRecyclerViewAdapter);
        shopList.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                if (distanceY <= mBanner.getHeight() && distanceY >= 0) {
                    float scale = (float) distanceY / mBanner.getHeight();
                    float alpha = (255 * scale);
                    mAddress.getBackground().mutate().setAlpha((int) (255 - alpha));
                    toolbar.getBackground().mutate().setAlpha((int) alpha);
                    if (shopList.isOnTop()) {
                        toolbar.getBackground().mutate().setAlpha(0);
                    }
                } else if (distanceY > mBanner.getHeight()) {
                    mAddress.getBackground().mutate().setAlpha(0);
                    toolbar.getBackground().mutate().setAlpha(255);
                }
            }

            @Override
            public void onScrollStateChanged(int state) {

            }
        });
        //TODO 添加轮播图
        headerView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_group_banner_header, (ViewGroup) getActivity().findViewById(android.R.id.content), false);


        mBanner = (ConvenientBanner) headerView.findViewById(R.id.convenientBanner);
        rlVp = (RelativeLayout) headerView.findViewById(R.id.rl_vp);
        viewpager = (ViewPager) headerView.findViewById(R.id.viewpager);
        llDot = (LinearLayout) headerView.findViewById(R.id.ll_dot);
        advGv = (RecyclerView) headerView.findViewById(R.id.adv_gv);
        tvMoreShop = (TextView) headerView.findViewById(R.id.tv_more_shop);

        tvMoreShop.setOnClickListener(v -> startActivity(new Intent(getActivity(), TuanSearchGoodsActivity.class)));


        advGv.setNestedScrollingEnabled(true);
        adapterGroupAdv = new AdapterGroupAdv(getActivity());
        advGv.setAdapter(adapterGroupAdv);
        advGv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        advGv.addItemDecoration(Utils.setDivider(getActivity(), R.dimen.dp_1, R.color.qing));

        mLRecyclerViewAdapter.addHeaderView(headerView);
        //设置头部加载颜色
        shopList.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        shopList.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        shopList.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");


        shopList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        shopList.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式

        shopList.setOnRefreshListener(() -> {
            isFresh = true;
            if (latitude == 0.0 || longitude == 0.0)
                permission();
            else
                RequestData(Api.GROUP_HOME);
        });
        /**
         * 加载更多
         * //禁用自动加载更多功能
         */
        shopList.setLoadMoreEnabled(false);


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
                    mAddress.setText(placeLikelihood.getPlace().getName());
                    ProgressDialogUtil.dismiss(getContext());
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
                    Api.CITY_CODE = aMapLocation.getCityCode();
                    String aoiName = aMapLocation.getAoiName();//获取当前定位点的AOI信息
                    if (TextUtils.isEmpty(aoiName)) {
                        mAddress.setText(address);
                    } else {
                        mAddress.setText(aoiName);
                    }
                    ProgressDialogUtil.dismiss(getContext());
                    RequestData(Api.GROUP_HOME);
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    ToastUtil.show(getString(R.string.获取位置失败));
                    ProgressDialogUtil.dismiss(getContext());
                    LogUtil.e("location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    private void RequestData(String api) {
        HttpUtils.postUrl(getActivity(), api, null, true, this);
    }


    /*初始化bunner*/
    private void inintBanner(final List<Data_Group_Home.BannerEntity> banner) {

        images = new ArrayList<>();
        for (int i = 0; i < banner.size(); i++) {
            images.add(Api.IMAGE_URL + banner.get(i).thumb);
        }
        mBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, images);    //设置需要切换的View
        mBanner.setPointViewVisible(true);  //设置指示器是否可见
        mBanner.setPageIndicator(new int[]{R.mipmap.icon_banner_normal, R.mipmap.icon_banner_choose}); //设置指示器圆点
        mBanner.startTurning(2000);     //设置自动切换（同时设置了切换时间间隔）
        mBanner.stopTurning(); //关闭自动切换
        mBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);//设置指示器位置（左、中、右）
        mBanner.setOnItemClickListener(position -> {
            if (!isFastDoubleClick()) {
                if (!TextUtils.isEmpty(banner.get(position).link)) {
                    Utils.dealWithHomeLink(getActivity(), banner.get(position).link, null);
                }
            }
        }); //设置点击监听事件
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

    private void inintgridview(final List<IndexCate> indexCate) {
        if (indexCate.size() > 0) {
            rlVp.setVisibility(View.VISIBLE);
            //总的页数=总数/每页数量，并取整
            pageCount = (int) Math.ceil(indexCate.size() * 1.0 / pageSize);
            llDot.removeAllViews();
            /*小于1页时候隐藏底下的圆点*/
            if (indexCate.size() <= pageSize) {
                llDot.setVisibility(View.GONE);
            } else {
                llDot.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < pageCount; i++) {
                // 添加指示点
                ImageView point = new ImageView(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin = 10;
                point.setLayoutParams(params);
                point.setBackgroundResource(R.drawable.point_bg);
                /*默认选中第一页*/
                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    point.setEnabled(false);
                }
                llDot.addView(point);
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
            rlVp.setLayoutParams(params);


            functionPagerAdapter = new FunctionPagerAdapter(getFragmentManager(), indexCate, pageCount, pageSize, "GROUP");
            viewpager.setAdapter(functionPagerAdapter);

            viewpager.setOffscreenPageLimit(pageCount - 1);
            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    // 把上一个点设为false
                    llDot.getChildAt(curIndex).setEnabled(false);
                    llDot.getChildAt(position).setEnabled(true);
                    curIndex = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            rlVp.setVisibility(View.GONE);
        }
    }


    private void inintmShopsListview(List<Data_Group_Home.ShopItemsEntity> shopItems) {
        if (shopItems != null && shopItems.size() > 0) {
            mparenApapter.setData(shopItems);
        }
    }

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Response_Group_Home response = gson.fromJson(Json, Response_Group_Home.class);
            if (response.error.equals("0")) {
                banners = response.data.banner;
                inintBanner(banners);
                indexCate = response.data.index_cate;
                inintgridview(indexCate);
                List<Data_Group_Home.AdvEntity> adv = response.data.adv;
                adapterGroupAdv.setData(adv);
                List<Data_Group_Home.ShopItemsEntity> shopItems = response.data.shop_items;
                inintmShopsListview(shopItems);
            /*刷新完成*/
                shopList.refreshComplete(shopItems.size());
                shopList.setNoMore(true);
                isFresh = false;
            } else {
                shopList.refreshComplete(0);
                mLRecyclerViewAdapter.notifyDataSetChanged();
                ToastUtil.show(response.message);
            }
        } catch (Exception e) {
            shopList.refreshComplete(0);
            mLRecyclerViewAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {
        shopList.refreshComplete(0);
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            startActivity(new Intent(getActivity(), TuanSearchActivity.class));
        }
        return super.onOptionsItemSelected(item);

    }


    @OnClick(R.id.address)
    public void onViewClicked() {
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
    }


    class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Utils.LoadStrPicture(context, data, imageView);

        }
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
                mAddress.setText(location);
                Api.LAT = latitude = data.getDoubleExtra("lat", 0.0);
                Api.LON = longitude = data.getDoubleExtra("lng", 0.0);
            } else if (requestCode == PLACE_PICKER_REQUEST) {
                /*谷歌选择地点返回结果*/
                ProgressDialogUtil.dismiss(getActivity());
                Place place = PlacePicker.getPlace(getActivity(), data);
                mAddress.setText(place.getName());
                Api.LAT = latitude = place.getLatLng().latitude;
                Api.LON = longitude = place.getLatLng().longitude;
            }
            shopList.refresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
