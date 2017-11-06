package com.jhcms.shequ.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.widget.ClearEditText;
import com.jhcms.shequ.adapter.SearchMapAddressAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.AddAddressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Wyj on 2017/5/18
 * TODO: 选择地址界面
 */

public class SearchMapActivity extends Activity implements LocationSource, AMapLocationListener, AMap.OnCameraChangeListener, PoiSearch.OnPoiSearchListener, View.OnFocusChangeListener, TextWatcher, TextView.OnEditorActionListener {
    @Bind(R.id.rv_keyword)
    RecyclerView rvKeyword;
    @Bind(R.id.et_content)
    ClearEditText etContent;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.map)
    MapView mMapView;
    @Bind(R.id.rv_map_address)
    RecyclerView rvMapAddress;
    @Bind(R.id.ll_map)
    LinearLayout llMap;
    AMap aMap;
    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    // Poi查询条件类
    private PoiSearch.Query query;

    private PoiSearch poiSearch;
    private MyLocationStyle myLocationStyle;
    private int currentPage = 0;// 搜索结果页面，从0开始计数
    // poi搜索类型
    private String deepType = "";
    private LatLonPoint point;
    /*地图搜索poi数据*/
    private ArrayList<PoiItem> mapPoiItems;
    /*poi返回结果*/
    private PoiResult poiResult;
    private SearchMapAddressAdapter mapAddressAdapter, keyWordAddressAdapter;
    private ProgressDialog progDialog;
    private boolean isHasFocus = false;
    private String searchText;
    private ArrayList<PoiItem> keyWordPoiItems;
    private Intent intent;
    public static String POIITEM = "poiitem";
    private String lat, lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*竖屏*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search_map);

        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
        initData();

    }

    private void initData() {
        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
            // 设置定位监听
            aMap.setLocationSource(this);
            aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationEnabled(true);
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.strokeColor(Color.TRANSPARENT);// 设置圆形的边框颜色
            myLocationStyle.radiusFillColor(Color.TRANSPARENT);// 设置圆形的填充颜色
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        }
        etContent.setOnFocusChangeListener(this);//焦点监听
        etContent.addTextChangedListener(this);// 添加文本输入框监听事件
        etContent.setOnEditorActionListener(this);

        /*地图搜索*/
        mapAddressAdapter = new SearchMapAddressAdapter(this);
        rvMapAddress.setAdapter(mapAddressAdapter);
        rvMapAddress.setLayoutManager(new LinearLayoutManager(this));

        /*关键字搜索*/
        keyWordAddressAdapter = new SearchMapAddressAdapter(this);
        rvKeyword.setAdapter(keyWordAddressAdapter);
        rvKeyword.setLayoutManager(new LinearLayoutManager(this));
        intent = new Intent();

        /*地图搜索点击事件*/
        mapAddressAdapter.setOnItemClickListener(new SearchMapAddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mapPoiItems != null) {
                    dealWithPoiItem(mapPoiItems, position);
                }

            }
        });
        /*关键字搜索点击事件*/
        keyWordAddressAdapter.setOnItemClickListener(new SearchMapAddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (keyWordPoiItems != null) {
                    dealWithPoiItem(keyWordPoiItems, position);
                }
            }
        });


    }

    /**
     * 处理点击返回结果
     *
     * @param poiItems
     * @param position
     */
    private void dealWithPoiItem(ArrayList<PoiItem> poiItems, int position) {
        PoiItem poiItem = poiItems.get(position);
        intent.setClass(SearchMapActivity.this, AddAddressActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(POIITEM, poiItem);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 关闭关键字搜索
     */
    private void close() {
        if (isHasFocus) {
            /*清除焦点*/
            etContent.clearFocus();
            /*隐藏键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
            /*显示地图*/
            llMap.setVisibility(View.VISIBLE);
            llSearch.setVisibility(View.GONE);
            /*隐藏关键字搜索结果*/
            rvKeyword.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(etContent.getText().toString())) {
                etContent.setText("");
            }
            isHasFocus = false;
        } else {
            finish();
        }
    }


    /**
     * 地图搜索
     */
    protected void doSearchQuery(LatLonPoint point) {
        showProgressDialog("正在搜索中");// 显示进度框
        aMap.setOnMapClickListener(null);// 进行poi搜索时清除掉地图点击事件
        currentPage = 0;
        /**
         * 1、构造 PoiSearch.Query 对象，通过 PoiSearch.Query(String query, String ctgr, String city) 设置搜索条件
         * 第一个参数表示搜索字符串，
         * 第二个参数表示poi搜索类型，
         * 第三个参数表示poi搜索区域（空字符串代表全国）
         * */
        query = new PoiSearch.Query("", deepType, "");
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        if (point != null) {
            /**
             * 2、构造 PoiSearch 对象，并设置监听
             * */
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            /**
             * 周边检索POI
             * 设置搜索区域为以lp点为圆心，其周围2000米范围
             * */
            poiSearch.setBound(new PoiSearch.SearchBound(point, 2000, true));
            /**
             *3、调用 PoiSearch 的 searchPOIAsyn() 方法发送请求
             * */
            poiSearch.searchPOIAsyn();// 异步搜索
        }


    }

    /**
     * 关键字搜索
     *
     * @param searchText
     */
    protected void doKeyWordSearchQuery(String searchText) {
        showProgressDialog("正在搜索中");// 显示进度框
        aMap.setOnMapClickListener(null);// 进行poi搜索时清除掉地图点击事件
        currentPage = 0;
        query = new PoiSearch.Query(searchText, deepType, "0551");
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int rCode) {
                dissmissProgressDialog();// 隐藏对话框
                if (rCode == 1000) {
                    if (result != null && result.getQuery() != null) {// 搜索poi的结果
                        if (result.getQuery().equals(query)) {// 是否是同一条
                            keyWordPoiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                            List<SuggestionCity> suggestionCities = result.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                            if (keyWordPoiItems != null && keyWordPoiItems.size() > 0) {
                                keyWordAddressAdapter.setData(keyWordPoiItems);
                            } else if (suggestionCities != null && suggestionCities.size() > 0) {

                            } else {
                                ToastUtil.show(getResources().getString(R.string.no_result));
                            }
                        }
                    } else {
                        ToastUtil.show(getResources().getString(R.string.no_result));
                    }
                } else if (rCode == 27) {
                    ToastUtil.show(getResources().getString(R.string.error_network));
                } else if (rCode == 32) {
                    ToastUtil.show(getResources().getString(R.string.error_key));
                } else {
                    ToastUtil.show(getResources().getString(R.string.搜索失败));
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();// 异步搜索
    }


    /**
     * 在activate()中设置定位初始化及启动定位
     *
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    /**
     * 在deactivate()中写停止定位的相关调用
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                /*移动至当前地点*/
                if (TextUtils.isEmpty(lat) && TextUtils.isEmpty(lng)) {
                    changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 18, 30, 30)), null);
                    addMarkersToMap(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
                    /*默认当前位置*/
                    point = new LatLonPoint(aMapLocation.getLatitude(),
                            aMapLocation.getLongitude());
                    doSearchQuery(point);

                } else {
                    CoordinateConverter converter = new CoordinateConverter(this);
                    // CoordType.BAIDU 待转换坐标类型
                    converter.from(CoordinateConverter.CoordType.BAIDU);
                    // sourceLatLng待转换坐标点 LatLng类型
                    converter.coord(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                    // 执行转换操作
                    LatLng desLatLng = converter.convert();
                    changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(desLatLng, 18, 30, 30)), null);
                    addMarkersToMap(desLatLng);
                    /*默认当前位置*/
                    point = new LatLonPoint(desLatLng.latitude,
                            desLatLng.longitude);
                    doSearchQuery(point);
                }
                mlocationClient.stopLocation();
                /*设置移动监听*/
                aMap.setOnCameraChangeListener(this);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                LogUtil.e("AmapErr" + errText);
            }
        }
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

    /**
     * 对正在移动地图事件回调
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    /**
     * 对移动地图结束事件回调
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        point = new LatLonPoint(cameraPosition.target.latitude,
                cameraPosition.target.longitude);// 默认当前位置
        doSearchQuery(point);
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.waimai_my_btn_location));
//        markerOptions.title("我的位置");
        Marker marker = aMap.addMarker(markerOptions);
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        marker.setPositionByPixels(width / 2, (height - 48) / 4);
        marker.showInfoWindow();
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog(String msg) {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage(msg);
        progDialog.show();
    }


    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dissmissProgressDialog();// 隐藏对话框
        /*返回结果成功或者失败的响应码。1000为成功，其他为失败*/
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    mapPoiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (mapPoiItems != null && mapPoiItems.size() > 0) {
                        mapAddressAdapter.setData(mapPoiItems);
                    } else if (suggestionCities != null && suggestionCities.size() > 0) {
                    } else {
                        showProgressDialog("定位中..");
                        if (mlocationClient != null) {
                            mlocationClient.startLocation();
                        } else {
                            mapAddressAdapter.setData(null);
                        }
                    }
                } else {
                }
            } else {
                ToastUtil.show(getResources().getString(R.string.no_result));
            }
        } else if (rCode == 27) {
            ToastUtil.show(getResources().getString(R.string.error_network));
        } else if (rCode == 32) {
            ToastUtil.show(getResources().getString(R.string.error_key));
        } else {
            ToastUtil.show(getResources().getString(R.string.搜索失败));
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 焦点监听
     *
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        isHasFocus = hasFocus;
        if (hasFocus) {
            // 此处为得到焦点时的处理内容
            llMap.setVisibility(View.GONE);
            rvKeyword.setVisibility(View.VISIBLE);
            llSearch.setVisibility(View.VISIBLE);
        } else {
            // 此处为失去焦点时的处理内容
            llMap.setVisibility(View.VISIBLE);
            rvKeyword.setVisibility(View.GONE);
            llSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchText = s.toString().trim();
        if (TextUtils.isEmpty(searchText)) {
            keyWordAddressAdapter.setData(null);
            return;
        } else {
            doKeyWordSearchQuery(searchText);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 监听手机返回键
     */
    @Override
    public void onBackPressed() {
        close();
    }

    /**
     * 点击键盘搜索按钮
     *
     * @param v
     * @param actionId
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchContent();
            return true;
        }
        return false;
    }

    /**
     * 处理关键字搜索
     */
    private void searchContent() {
        if (!TextUtils.isEmpty(searchText)) {
            doKeyWordSearchQuery(searchText);
        } else {
            ToastUtil.show("请输入搜索内容");
        }
    }

    @OnClick(R.id.iv_search)
    public void onClick(View v) {
        searchContent();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
