package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.jhcms.common.model.MyAddress;
import com.jhcms.common.model.Response_AddressResult;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.shequ.activity.SearchMapActivity;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.LabelAdapter;
import com.jhcms.waimaiV3.dialog.TipDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Wyj on 2017/5/9
 * TODO:修改删除地址
 */
public class AddAddressActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_consignee_name)
    EditText etConsigneeName;
    @Bind(R.id.et_consignee_phone)
    EditText etConsigneePhone;
    @Bind(R.id.tv_consignee_address)
    TextView tvConsigneeAddress;
    @Bind(R.id.ll_address)
    LinearLayout llAddress;
    @Bind(R.id.et_house_number)
    EditText etHouseNumber;
    @Bind(R.id.tv_add_save)
    TextView tvAddSave;
    @Bind(R.id.tv_add_detele)
    TextView tvAddDetele;
    @Bind(R.id.type_gridView)
    GridView typeGridView;
    private LabelAdapter adapter;
    List<String> labelList;
    /*跳转到地图搜索的请求码*/
    private int TO_SEARCH_MAP = 1000;
    /*地图搜索返回的门牌号*/
    private String snippet;
    /*地图搜索返回的地址*/
    private String addressTitle;
    private MyAddress mData;
    private String type;
    private String lat;
    private String lng;
    /**
     * 修改地址标记
     */
    public static String MODIFY = "ADDRESS_MODIFY";
    /**
     * 添加地址标记
     */
    public static String ADD = "ADDRESS_ADD";
    private int PLACE_PICKER_REQUEST = 0x100;
    private boolean isStartGMSLocation;
    private int typeId = 4;

    @Override
    protected void initData() {
        setToolBar(toolbar);
        type = getIntent().getStringExtra("type");

        /*修改地址*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        labelList = new ArrayList<>();
        labelList.add("家");
        labelList.add("公司");
        labelList.add("学校");
        labelList.add("其他");
        adapter = new LabelAdapter(this);
        typeGridView.setAdapter(adapter);
        adapter.setData(labelList);

        typeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeId = position + 1;
                adapter.setPosition(position);
            }
        });
        if (type.equals(MODIFY)) {
            tvTitle.setText("修改地址");
            tvAddDetele.setVisibility(View.VISIBLE);
            mData = (MyAddress) getIntent().getSerializableExtra("modle");
            bindViewData();
        } else if (type.equals(ADD)) {
            tvTitle.setText("新增地址");
            tvAddDetele.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
    }

    //TODO 绑定数据
    private void bindViewData() {
        etConsigneeName.setText(mData.contact);
        etConsigneePhone.setText(mData.mobile);
        tvConsigneeAddress.setText(mData.addr);
        etHouseNumber.setText(mData.house);
        typeId = mData.type - 1;
        adapter.setPosition(mData.type - 1);
        lat = mData.lat;
        lng = mData.lng;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStartGMSLocation) {
            ProgressDialogUtil.dismiss(this);
            isStartGMSLocation = false;
        }
    }

    @OnClick({R.id.ll_address, R.id.tv_add_save, R.id.tv_add_detele})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                if (MyApplication.MAP.equals(Api.GAODE)) {
                    Intent intent = new Intent(this, SearchMapActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    startActivityForResult(intent, TO_SEARCH_MAP);
                } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                    ProgressDialogUtil.showProgressDialog(this);
                    isStartGMSLocation = true;
                    try {
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.tv_add_save:
                if (isOk()) {
                    UpdateAddress(type);
                }
                break;
            case R.id.tv_add_detele:
                TipDialog dialog = new TipDialog(AddAddressActivity.this, new TipDialog.setTipDialogCilck() {
                    @Override
                    public void setPositiveListener() {//TODO 删除地址
                        DeleteADdress();
                        Toast.makeText(AddAddressActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void setNegativeListener() {

                    }
                });
                dialog.setMessage("确定要删除该地址吗?");
                dialog.show();
                break;
        }
    }

    private void DeleteADdress() {//删除地址
        try {
            JSONObject js = new JSONObject();
            js.put("addr_id", mData.addr_id);
            String str = js.toString();
            HttpUtils.postUrl(this, Api.WAIMAI_ADDRESS_DELETE, str, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UpdateAddress(String type) {

        JSONObject js = new JSONObject();
        try {
            if (type.equals(MODIFY)) {
                js.put("addr_id", mData.addr_id);
            }
            js.put("contact", etConsigneeName.getText().toString());
            js.put("mobile", etConsigneePhone.getText().toString());
            js.put("addr", tvConsigneeAddress.getText().toString());
            js.put("house", etHouseNumber.getText().toString());
            js.put("lat", lat);
            js.put("lng", lng);
            js.put("type", typeId);

            String str = js.toString();
            if (type.equals(MODIFY)) {
                HttpUtils.postUrl(this, Api.WAIMAI_ADDRESS_UPDATE, str, true, this);
            } else {
                HttpUtils.postUrl(this, Api.WAIMAI_ADDRESS_CREATE, str, true, this);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOk() {
        String name = etConsigneeName.getText().toString().trim();
        String phone = etConsigneePhone.getText().toString().trim();
        String housenum = etHouseNumber.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(AddAddressActivity.this, "请输入收货人姓名", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(AddAddressActivity.this, "请输入收货人手机号", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(housenum)) {
            Toast.makeText(AddAddressActivity.this, "请输入门牌号", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(tvConsigneeAddress.getText())) {
            ToastUtil.show("请选择地址！");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TO_SEARCH_MAP && data != null) {
                Bundle extras = data.getExtras();
                PoiItem poiItem = extras.getParcelable(SearchMapActivity.POIITEM);
                if (poiItem != null) {
                    snippet = poiItem.getSnippet();
                    addressTitle = poiItem.getTitle();
                    tvConsigneeAddress.setText(addressTitle);
                    etHouseNumber.setText(snippet);
                    LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                    if (latLonPoint != null) {
                        double latitude = latLonPoint.getLatitude();
                        double longitude = latLonPoint.getLongitude();
                        /*转百度位置*/
                        double[] doubles = Utils.gd_To_Bd(latitude, longitude);
                        lat = String.valueOf(doubles[0]);
                        lng = String.valueOf(doubles[1]);
                    }
                }
            } else if (requestCode == PLACE_PICKER_REQUEST && data != null) {
                ProgressDialogUtil.dismiss(this);
                Place place = PlacePicker.getPlace(this, data);
                tvConsigneeAddress.setText(place.getName());
                etHouseNumber.setText(place.getAddress());
                lat = String.valueOf(place.getLatLng().latitude);
                lng = String.valueOf(place.getLatLng().longitude);
            }
        }
    }

    @Override
    public void onSuccess(String url, String Json) {
        Gson gson = new Gson();
        switch (url) {
            case Api.WAIMAI_ADDRESS_UPDATE://修改地址
                Response_AddressResult mdata = gson.fromJson(Json, Response_AddressResult.class);
                if (mdata.error.equals("0")) {
                    Toast.makeText(AddAddressActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    ToastUtil.show(mdata.message);
                }
                break;
            case Api.WAIMAI_ADDRESS_CREATE://添加地址
                Response_AddressResult mdata1 = gson.fromJson(Json, Response_AddressResult.class);
                if (mdata1.error.equals("0")) {
                    Toast.makeText(AddAddressActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    ToastUtil.show(mdata1.message);
                }
                break;

            case Api.WAIMAI_ADDRESS_DELETE:
                SharedResponse shareResPonse = gson.fromJson(Json, SharedResponse.class);
                if (shareResPonse.error.equals("0")) {
                    Toast.makeText(AddAddressActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }
}
