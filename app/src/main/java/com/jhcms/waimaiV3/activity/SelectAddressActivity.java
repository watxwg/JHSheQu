package com.jhcms.waimaiV3.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.gson.Gson;
import com.jhcms.common.listener.PermissionListener;
import com.jhcms.common.model.MyAddress;
import com.jhcms.common.model.Response_ConsigneeAddressActivity;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.DividerListItemDecoration;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.AddressItemAdapter;
import com.jhcms.waimaiV3.adapter.MyAddressAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SelectAddressActivity extends SwipeBaseActivity implements PoiSearch.OnPoiSearchListener, Inputtips.InputtipsListener, OnRequestSuccessCallback {


    public static String CITY_CODE = "CITY_CODE";
    public static String CITY_NAME = "CITY_NAME";
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.input_address)
    EditText inputAddress;
    @Bind(R.id.tv_cancle)
    TextView tvCancle;
    @Bind(R.id.current_address)
    TextView currentAddress;
    @Bind(R.id.reposition)
    TextView reposition;
    @Bind(R.id.rv_myAddress)
    RecyclerView rvMyAddress;
    @Bind(R.id.ll_myAddress)
    LinearLayout llMyAddress;
    @Bind(R.id.no_search_layout)
    LinearLayout noSearchLayout;
    @Bind(R.id.neighboring_address)
    RecyclerView rvAddress;
    @Bind(R.id.search_address)
    RecyclerView rvSearchAddress;
    private double latitude;
    private double longitude;
    List<String> data = new ArrayList<>();
    List<LatLonPoint> pointData = new ArrayList<>();
    private DividerListItemDecoration divider;
    private String cityCode;
    AddressItemAdapter addressAdapter, searchAddressAdapter;
    AMapLocationClientOption mLocationOption = null;
    AMapLocationClient mLocationClient;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    RequestAddressListData(1);
                    break;
            }
        }
    };
    private MyAddressAdapter myAddressAdapter;
    private List<MyAddress> myAddressList;
    private int SELECT_CITY_REQUEST_CODE = 0x111;
    private boolean isReposition = false;


    @Override
    protected void initData() {
        setToolBar(toolbar);
        tvTitle.setText("选择位置");
        toolbar.setNavigationIcon(R.mipmap.icon_web_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        permission();
        /*登陆的时候请求个人地址*/
        if (!TextUtils.isEmpty(Api.TOKEN)) {
            mHandler.sendEmptyMessage(0);
            llMyAddress.setVisibility(View.VISIBLE);
        } else {
            llMyAddress.setVisibility(View.GONE);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
        divider = new DividerListItemDecoration.Builder(this)
                .setHeight(R.dimen.dp_1)
                .setColorResource(R.color.background)
                .build();
        /*附近的地址*/
        addressAdapter = new AddressItemAdapter(this);
        rvAddress.addItemDecoration(divider);
        rvAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAddress.setAdapter(addressAdapter);

        /*我的收货地址*/
        rvMyAddress.addItemDecoration(divider);
        rvMyAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        myAddressAdapter = new MyAddressAdapter(this);
        rvMyAddress.setAdapter(myAddressAdapter);
        myAddressAdapter.setOnAddressItemClick(new MyAddressAdapter.OnAddressItemClick() {
            @Override
            public void onAddressItemClick(int position) {
                try {
                    Intent resultI = new Intent();
                    resultI.putExtra("address", myAddressList.get(position).addr);
                    resultI.putExtra("lat", Double.parseDouble(myAddressList.get(position).lat));
                    resultI.putExtra("lng", Double.parseDouble(myAddressList.get(position).lng));
                    setResult(RESULT_OK, resultI);
                    finish();
                } catch (Exception e) {
                    Snackbar.make(rvAddress, R.string.获取不到相关地址信息, Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        /*搜索关键字的位置*/
        searchAddressAdapter = new AddressItemAdapter(this);
        rvSearchAddress.setAdapter(searchAddressAdapter);
        rvSearchAddress.addItemDecoration(divider);
        rvSearchAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rvAddress.setNestedScrollingEnabled(false);
        rvSearchAddress.setNestedScrollingEnabled(false);
        rvMyAddress.setNestedScrollingEnabled(false);
        addressAdapter.setOnAddItemClickListener(new AddressItemAdapter.OnAddItemClickListener() {
            @Override
            public void onAddItemClick(View v, int position) {
                dealWithItemAdd(position);
            }
        });
        searchAddressAdapter.setOnAddItemClickListener(new AddressItemAdapter.OnAddItemClickListener() {
            @Override
            public void onAddItemClick(View v, int position) {
                dealWithItemAdd(position);
            }
        });


        inputAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().isEmpty() || charSequence.length() == 0) {
                    noSearchLayout.setVisibility(View.VISIBLE);
                    rvSearchAddress.setVisibility(View.GONE);
                    isReposition = true;
                    mLocationClient.startLocation();
                } else {
                    searchInput(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void dealWithItemAdd(int position) {
        try {
            Intent resultI = new Intent();
            resultI.putExtra("address", data.get(position));
            double[] doubles = Utils.gd_To_Bd(pointData.get(position).getLatitude(), pointData.get(position).getLongitude());
            resultI.putExtra("lat", doubles[0]);
            resultI.putExtra("lng", doubles[1]);
            setResult(RESULT_OK, resultI);
            finish();
        } catch (Exception e) {
            Snackbar.make(rvAddress, R.string.获取不到相关地址信息, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理搜索关键字
     *
     * @param charSequence
     */
    private void searchInput(CharSequence charSequence) {
        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
        InputtipsQuery inputquery = new InputtipsQuery(charSequence.toString(), cityCode);
        inputquery.setCityLimit(true);//限制在当前城市
        Inputtips inputTips = new Inputtips(SelectAddressActivity.this, inputquery);
        inputTips.setInputtipsListener(SelectAddressActivity.this);
        inputTips.requestInputtipsAsyn();
    }

    private void RequestAddressListData(int page) {
        try {
            JSONObject js = new JSONObject();
            js.put("page", page);
            String str = js.toString();
            HttpUtils.postUrl(this, Api.WAIMAI_ADDRESS_LIST, str, false, this);

        } catch (Exception e) {
            ToastUtil.show("网络出现了小问题！");
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);
    }

    private void permission() {
        SwipeBaseActivity.requestRuntimePermission(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, new PermissionListener() {
            @Override
            public void onGranted() {
                ProgressDialogUtil.showProgressDialog(SelectAddressActivity.this);
                initLocation();
            }

            @Override
            public void onDenied(List<String> permissions) {
//                showMissingPermissionDialog();
            }
        });
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
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


    @OnClick({R.id.current_address, R.id.reposition, R.id.tv_cancle, R.id.tv_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.current_address:
                Intent resultI = new Intent();
                resultI.putExtra("address", currentAddress.getText().toString());
                double[] doubles = Utils.gd_To_Bd(latitude, longitude);
                resultI.putExtra("lat", doubles[0]);
                resultI.putExtra("lng", doubles[1]);
                setResult(RESULT_OK, resultI);
                finish();
                break;
            case R.id.reposition:
                if (null != mLocationClient) {
                    isReposition = true;
                    mLocationClient.startLocation();
                }
                break;
            case R.id.tv_cancle:
                inputAddress.setText("");
                break;
            case R.id.tv_city:
                Intent intent = new Intent(this, SelectCityActivity.class);
                intent.putExtra(SelectCityActivity.CITY, cityName);
                startActivityForResult(intent, SELECT_CITY_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_CITY_REQUEST_CODE) {
            cityCode = data.getStringExtra(CITY_CODE);
            cityName = data.getStringExtra(CITY_NAME);
            tvCity.setText(cityName);
        }
    }

    public String cityName;
    public AMapLocationListener mLocationListener = new AMapLocationListener() {


        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            try {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        latitude = aMapLocation.getLatitude();
                        longitude = aMapLocation.getLongitude();
                        cityName = aMapLocation.getCity();
                        /*第一次定位*/
                        if (!isReposition) {
                            cityCode = aMapLocation.getCityCode();//城市编码
                            tvCity.setText(cityName);
                            searchAdd(aMapLocation, cityCode);
                        } else {
                            searchAdd(aMapLocation, aMapLocation.getCityCode());
                        }
                        String address = aMapLocation.getAddress();//地址
                        String street = aMapLocation.getStreet();
                        String aoiName = aMapLocation.getAoiName();//获取当前定位点的AOI信息
                        currentAddress.setText(aMapLocation.getCity() +
                                aMapLocation.getDistrict() +
                                aMapLocation.getStreet() +
                                aMapLocation.getStreetNum());
                        LogUtil.e("latitude: " + latitude);
                        LogUtil.e("longitude: " + longitude);
                        LogUtil.e("address: " + address);
                        LogUtil.e("street: " + street);
                        LogUtil.e("cityCode: " + cityCode);
                        LogUtil.e("aoiName: " + aoiName);
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Snackbar.make(rvAddress, R.string.获取位置失败, Snackbar.LENGTH_SHORT).show();
                        ProgressDialogUtil.dismiss(SelectAddressActivity.this);
                        LogUtil.e("location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            } catch (Exception e) {
            }
        }
    };

    /**
     * 搜索当前位置--附近的地址
     *
     * @param aMapLocation
     * @param cityCode
     */
    private void searchAdd(AMapLocation aMapLocation, String cityCode) {
        PoiSearch.Query query = new PoiSearch.Query("", "商务住宅", cityCode);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查第一页
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(
                aMapLocation.getLatitude(),
                aMapLocation.getLongitude()), 1000));//设置周边搜索的中心点以及区域
        poiSearch.setOnPoiSearchListener(this);//设置数据返回的监听器
        poiSearch.searchPOIAsyn();//开始搜索
        ProgressDialogUtil.dismiss(SelectAddressActivity.this);
    }

    /**
     * 搜索附近的地址结果
     *
     * @param poiResult
     * @param code
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int code) {
        List<PoiItem> list = poiResult.getPois();
        data.clear();
        pointData.clear();
        if (list.size() > 0) {
            List<String> subData = new ArrayList<>();
            for (PoiItem p : list) {
                data.add(p.getTitle());
                subData.add(p.getSnippet());
                pointData.add(p.getLatLonPoint());
            }
            addressAdapter.setData(data, subData);
        }


    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 关键字搜索的结果
     *
     * @param list
     * @param rCode
     */
    @Override
    public void onGetInputtips(List<Tip> list, int rCode) {
        if (rCode == 1000) {// 正确返回
            data.clear();
            pointData.clear();
            rvSearchAddress.setVisibility(View.VISIBLE);
            noSearchLayout.setVisibility(View.GONE);
            if (list.size() > 0) {
                List<String> subData = new ArrayList<>();
                for (int i = 1; i < list.size(); i++) {
                    if (list.get(i).getPoint() != null) {
                        data.add(list.get(i).getName());
                        subData.add(list.get(i).getAddress());
                        pointData.add(list.get(i).getPoint());
                    }
                }
                searchAddressAdapter.setData(data, subData);
            } else {
                searchAddressAdapter.setData(data, null);
                Snackbar.make(rvAddress, R.string.没有搜索到相关地址, Snackbar.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * 关闭界面
     */
    private void closeActivity() {
        if (inputAddress.getText().toString().isEmpty()) {
            finish();
        } else {
            inputAddress.setText("");
            isReposition = true;
            mLocationClient.startLocation();
            rvSearchAddress.setVisibility(View.GONE);
            noSearchLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }


    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Response_ConsigneeAddressActivity ResponseData = gson.fromJson(Json, Response_ConsigneeAddressActivity.class);
            if (ResponseData.error.equals("0")) {
                myAddressList = ResponseData.data.items;
                if (null != myAddressList && myAddressList.size() > 0) {
                    myAddressAdapter.setData(myAddressList);
                } else {
                    rvMyAddress.setVisibility(View.GONE);
                }

            } else {
                ToastUtil.show(ResponseData.message);
                rvMyAddress.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            rvMyAddress.setVisibility(View.GONE);
        }


    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }
}
