package com.jhcms.waimaiV3.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;
import com.classic.common.MultipleStatusView;
import com.coorchice.library.SuperTextView;
import com.google.gson.Gson;
import com.jhcms.common.listener.PermissionListener;
import com.jhcms.common.model.Data_WaiMai_Info;
import com.jhcms.common.model.Response_WaiMai_Info;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.PicturePreviewActivity;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.activity.ShopMapActivity;
import com.jhcms.waimaiV3.activity.ShopMapActivityGMS;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.adapter.ShopDetailsImageAdapter;
import com.jhcms.waimaiV3.dialog.CallDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

/**
 * Created by Wyj on 2017/5/6
 * TODO:店铺详情
 * client/v3/waimai/shop/info
 */
public class WaiMaiShopDetailsFragment extends CustomerBaseFragment implements OnRequestSuccessCallback {

    @Bind(R.id.tv_shop_phone)
    TextView tvShopPhone;
    @Bind(R.id.ll_shop_phone)
    LinearLayout llShopPhone;
    @Bind(R.id.tv_shop_address)
    TextView tvShopAddress;
    @Bind(R.id.ll_shop_address)
    LinearLayout llShopAddress;
    @Bind(R.id.tv_yy_peitime)
    TextView tvYyPeitime;
    @Bind(R.id.all_youhui)
    LinearLayout allYouhui;
    @Bind(R.id.rv_qualification)
    RecyclerView rvQualification;
    @Bind(R.id.rv_real_scene)
    RecyclerView rvRealScene;
    @Bind(R.id.tv_pei_type)
    TextView tvPeiType;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    private ShopDetailsImageAdapter realSceneAdapter, qualificationAdapter;
    private String shop_id;
    private Response_WaiMai_Info shopInfo;
    private Data_WaiMai_Info data;
    /**
     * 商家详情
     */
    private Data_WaiMai_Info.DetailEntity detail;
    /**
     * 商家电话
     */
    private String phone;
    private SuperTextView tvYouhuiWord;
    private TextView tvYouhuiTitle;
    private String lat, lng;



    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_waimai_details, null);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    protected void initData() {
        super.initData();
        /*商家资质*/
        qualificationAdapter = new ShopDetailsImageAdapter(getActivity());
        rvQualification.setNestedScrollingEnabled(false);
        rvQualification.setAdapter(qualificationAdapter);
        rvQualification.setLayoutManager(new GridLayoutManager(getActivity(), 3));


        /*商家实景*/
        realSceneAdapter = new ShopDetailsImageAdapter(getActivity());
        rvRealScene.setNestedScrollingEnabled(false);
        rvRealScene.setAdapter(realSceneAdapter);
        rvRealScene.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        qualificationAdapter.setOnItemClickListener(new ShopDetailsImageAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                /*detail.verify*/
                Intent intent = new Intent(getActivity(), PicturePreviewActivity.class);
                intent.putExtra(PicturePreviewActivity.POSITION, position);
                intent.putStringArrayListExtra(PicturePreviewActivity.IMAGELIST, (ArrayList<String>) detail.verify);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(v,
                        v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                ActivityCompat.startActivity(getActivity(), intent,
                        compat.toBundle());
            }
        });
        realSceneAdapter.setOnItemClickListener(new ShopDetailsImageAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), PicturePreviewActivity.class);
                intent.putExtra(PicturePreviewActivity.POSITION, position);
                intent.putStringArrayListExtra(PicturePreviewActivity.IMAGELIST, (ArrayList<String>) detail.album);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(v,
                        v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                ActivityCompat.startActivity(getActivity(), intent,
                        compat.toBundle());
            }
        });
        requestShopDetail(Api.WAIMAI_SHOP_INFO, shop_id);
    }

    private void initHuoDong(List<Data_WaiMai_Info.DetailEntity.HuodongEntity> huodong) {
        /**
         * 加载全部活动
         * */
        allYouhui.removeAllViews();
        for (int i = 0; i < huodong.size(); i++) {
            LinearLayout firstLl = new LinearLayout(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.youhuiquan, firstLl, false);
            tvYouhuiWord = (SuperTextView) view.findViewById(R.id.tv_youhui_word);
            tvYouhuiTitle = (TextView) view.findViewById(R.id.tv_youhui_title);
            tvYouhuiWord.setText(huodong.get(i).word);
            tvYouhuiWord.setSolid(Color.parseColor("#" + huodong.get(i).color));
            tvYouhuiTitle.setText(huodong.get(i).title);
            tvYouhuiTitle.setTextColor(Color.BLACK);
            firstLl.addView(view);
            allYouhui.addView(firstLl);
        }
    }

    private void requestShopDetail(String api, String shopId) {
        JSONObject object = new JSONObject();
        try {
            object.put("shop_id", shopId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(getActivity(), api, str, false, this);
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
        shop_id = bundle.getString(WaiMaiShopActivity.SHOP_ID);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            shopInfo = gson.fromJson(Json, Response_WaiMai_Info.class);
            if (shopInfo.error.equals("0")) {
                data = shopInfo.data;
                detail = data.detail;

                /*商家电话*/
                phone = detail.phone;
                tvShopPhone.setText(phone);
                /*商家位置坐标*/
                lat = detail.lat;
                lng = detail.lng;
                /*商户地址*/
                tvShopAddress.setText(detail.addr);
                /*处理活动*/
                if (null != detail.huodong && detail.huodong.size() > 0) {
                    allYouhui.setVisibility(View.VISIBLE);
                    initHuoDong(detail.huodong);
                } else {
                    allYouhui.setVisibility(View.GONE);
                }
                /*营业时间*/
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < detail.yy_peitime.size(); i++) {
                    String peitime = detail.yy_peitime.get(i).stime + "-" + detail.yy_peitime.get(i).ltime;
                    sb.append(peitime + "\t");
                }
                tvYyPeitime.setText("营业时间：" + sb);
                /*配送方式*/
                if (detail.pei_type.equals("0")) {
                    tvPeiType.setText(String.format(getString(R.string.配送服务), getResources().getString(R.string.商家配送)));
                } else if (detail.pei_type.equals("1")) {
                    tvPeiType.setText(String.format(getString(R.string.配送服务), getResources().getString(R.string.平台配送)));
                }

                /*商家资质*/
                qualificationAdapter.setData(detail.verify);
                /*商家实景*/
                realSceneAdapter.setData(detail.album);
                statusview.showContent();
            } else {
                ToastUtil.show(shopInfo.message);
                statusview.showError();
            }

        } catch (Exception e) {
            ToastUtil.show(getString(R.string.接口异常));
            statusview.showError();
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

    @OnClick({R.id.ll_shop_phone, R.id.ll_shop_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shop_phone:
                SwipeBaseActivity.requestRuntimePermission(new String[]{Manifest.permission.CALL_PHONE}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        new CallDialog(getActivity())
                                .setMessage(phone)
                                .setTipTitle("拨打商家电话")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri
                                                .parse("tel:" + phone));
                                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            return;
                                        }
                                        getActivity().startActivity(intent);
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        showMissingPermissionDialog();
                    }
                });
                break;
            case R.id.ll_shop_address:
                if (!Utils.isFastDoubleClick()) {
                    Intent intent = new Intent();
                    if (MyApplication.MAP.equals(Api.GAODE)) {
                        CoordinateConverter converter = new CoordinateConverter(getActivity());
                        // CoordType.GPS 待转换坐标类型
                        converter.from(CoordinateConverter.CoordType.BAIDU);
                        // sourceLatLng待转换坐标点 LatLng类型
                        converter.coord(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                        // 执行转换操作
                        LatLng desLatLng = converter.convert();
                        intent.setClass(getActivity(), ShopMapActivity.class);
                        intent.putExtra("lat", desLatLng.latitude);
                        intent.putExtra("lng", desLatLng.longitude);
                    } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                        intent.setClass(getActivity(), ShopMapActivityGMS.class);
                        intent.putExtra("lat", Double.parseDouble(lat));
                        intent.putExtra("lng", Double.parseDouble(lng));
                        intent.putExtra("address", detail.addr);
                    }
                    startActivity(intent);
                }

                break;
        }
    }
}
