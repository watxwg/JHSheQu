package com.jhcms.mall.activity;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.classic.common.MultipleStatusView;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.widget.ClearEditText;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.MallAddrAllAreas;
import com.jhcms.mall.model.MallAddrDetail;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MallAddNewAddressActivity extends SwipeBaseActivity {

    public static String FLAG = "FLAG";
    public static String ADDR_ID = "ADDR_ID";

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_receiver)
    ClearEditText etReceiver;
    @Bind(R.id.et_phone)
    ClearEditText etPhone;
    @Bind(R.id.tv_province)
    TextView tvProvince;
    @Bind(R.id.et_detailsaddress)
    ClearEditText etDetailsaddress;
    @Bind(R.id.iv_default)
    ImageView ivDefault;
    @Bind(R.id.tv_saveaddress)
    TextView tvSaveaddress;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    /**
     * 修改地址标记
     */
    public static String MODIFY = "MALL_ADDRESS_MODIFY";
    /**
     * 添加地址标记
     */
    public static String ADD = "MALL_ADDRESS_ADD";
    private List<MallAddrAllAreas.ItemsBean> options1Items;
    private ArrayList<ArrayList<String>> options2Items;
    private ArrayList<ArrayList<ArrayList<String>>> options3Items;
    private MallAddrDetail.DetailBean detailBean;
    /**
     * 上个界面传来的标记位
     */
    private String flag;
    /**
     * 地址id
     */
    private String addr_id;

    /**
     * 省ID
     */
    private String province_id;
    /**
     * 市ID
     */
    private String city_id;
    /**
     * 地区ID
     */
    private String area_id;
    /**
     * 区号
     */
    private String city_code;
    /**
     * 省
     */
    private String province_name;
    /**
     * 市
     */
    private String city_name;
    /**
     * 区
     */
    private String area_name;
    /**
     * 详细地址
     */
    private String addr;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 是否是默认地址
     * 1 默认 0不默认
     */
    private int is_default = 0;
    private String api;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_add_new_address);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        setToolBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        flag = getIntent().getStringExtra(FLAG);
        if (MODIFY.equals(flag)) {
            tvTitle.setText(R.string.修改地址);
            addr_id = getIntent().getStringExtra(ADDR_ID);
            requestAdd(addr_id);
        } else {
            tvTitle.setText(R.string.新增地址);
        }
    }


    @OnClick({R.id.tv_province, R.id.ll_default, R.id.tv_saveaddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_province:
                if (null != options1Items) {
                    ShowPickerView();
                } else {
                    requestCity();
                }
                break;
            case R.id.ll_default:
                if (is_default == 0) {
                    is_default = 1;
                } else {
                    is_default = 0;
                }
                ivDefault.setSelected(is_default == 1 ? true : false);
                break;
            case R.id.tv_saveaddress:
                if (isFillIn()) {
                    createAdd();
                }
                break;
        }
    }

    /**
     * 创建、修改地址
     */
    private void createAdd() {
        contact = etReceiver.getText().toString().trim();
        mobile = etPhone.getText().toString().trim();
        addr = etDetailsaddress.getText().toString().trim();
        JSONObject object = new JSONObject();
        try {
            if (ADD.equals(flag)) {
                api = Api.MALL_ADDR_CREATE;
            } else {
                api = Api.MALL_ADDR_EDIT;
                object.put("addr_id", addr_id);
            }
            object.put("province_id", province_id);
            object.put("province_name", province_name);
            object.put("city_code", city_code);
            object.put("city_id", city_id);
            object.put("city_name", city_name);
            object.put("area_id", area_id);
            object.put("area_name", area_name);
            object.put("contact", contact);
            object.put("addr", addr);
            object.put("mobile", mobile);
            object.put("is_default", is_default);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(this, api
                , object.toString(), true, new OnRequestSuccess<BaseResponse<MallAddrDetail>>() {
                    @Override
                    public void onSuccess(String url, BaseResponse<MallAddrDetail> data) {
                        if (data.error == 0) {
                            finish();
                        } else {
                            ToastUtil.show(data.getMessage());
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
     * @return 判断信息是否填写完整
     */
    public boolean isFillIn() {
        if (TextUtils.isEmpty(etReceiver.getText().toString().trim())) {
            ToastUtil.show("请填写收货人姓名");
            return false;
        } else if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
            ToastUtil.show("请填写收货人手机号");
            return false;
        } else if (TextUtils.isEmpty(etDetailsaddress.getText().toString().trim())) {
            ToastUtil.show("请填写详细地址");
            return false;
        } else if (TextUtils.isEmpty(province_id)) {
            ToastUtil.show("请选择省市区");
            return false;
        }
        return true;
    }

    /**
     * @param addr_id 根据addr_id 请求地址详情
     */
    private void requestAdd(String addr_id) {
        JSONObject object = new JSONObject();
        try {
            object.put("addr_id", addr_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_ADDR_DETAIL
                , object.toString(), false, new OnRequestSuccess<BaseResponse<MallAddrDetail>>() {
                    @Override
                    public void onSuccess(String url, BaseResponse<MallAddrDetail> data) {
                        if (data.error == 0) {
                            detailBean = data.data.detail;
                            contact = detailBean.contact;
                            etReceiver.setText(contact);
                            mobile = detailBean.mobile;
                            etPhone.setText(mobile);
                            province_name = detailBean.province_name;
                            province_id = detailBean.province_id;
                            city_name = detailBean.city_name;
                            city_id = detailBean.city_id;
                            area_name = detailBean.area_name;
                            area_id = detailBean.area_id;
                            addr = detailBean.addr;
                            tvProvince.setText(province_name + city_name + area_name);
                            etDetailsaddress.setText(addr);
                            if ("1".equals(detailBean.is_default)) {
                                ivDefault.setSelected(true);
                                is_default = 1;
                            } else {
                                ivDefault.setSelected(false);
                            }
                            statusview.showContent();
                        } else {
                            statusview.showError();
                            ToastUtil.show(data.message);
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
                });
    }

    /**
     * 请求省市区城市列表
     */
    private void requestCity() {
        HttpUtils.postUrlWithBaseResponse(this, Api.MALL_ADDR_ALL_AREAS
                , null, true, new OnRequestSuccess<BaseResponse<MallAddrAllAreas>>() {
                    @Override
                    public void onSuccess(String url, BaseResponse<MallAddrAllAreas> data) {
                        if (data.error == 0) {
                            options1Items = new ArrayList<MallAddrAllAreas.ItemsBean>();
                            options2Items = new ArrayList<ArrayList<String>>();
                            options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
                            options1Items = data.data.items;
                            for (int i = 0; i < options1Items.size(); i++) {
                                ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
                                ArrayList<ArrayList<String>> AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
                                if (options1Items.get(i).city == null || options1Items.get(i).city.size() == 0) {
                                    CityList.add("");
                                } else {
                                    for (int j = 0; j < options1Items.get(i).city.size(); j++) {
                                        CityList.add(options1Items.get(i).city.get(j).region_name);
                                        ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                                        //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                                        if (options1Items.get(i).city.get(j).area == null || options1Items.get(i).city.get(j).area.size() == 0) {
                                            City_AreaList.add("");
                                        } else {
                                            for (int k = 0; k < options1Items.get(i).city.get(j).area.size(); k++) {
                                                City_AreaList.add(options1Items.get(i).city.get(j).area.get(k).region_name);
                                            }
                                        }
                                        AreaList.add(City_AreaList);
                                    }
                                    /**
                                     * 添加城市数据
                                     */
                                    options2Items.add(CityList);

                                    /**
                                     * 添加地区数据
                                     */
                                    options3Items.add(AreaList);
                                }
                            }
                            ShowPickerView();
                        } else {
                            ToastUtil.show(data.message);
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
     * 弹出选择器
     */
    private void ShowPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                province_id = options1Items.get(options1).region_id;
                province_name = options1Items.get(options1).region_name;
                city_code = options1Items.get(options1).city_code;

                city_id = options1Items.get(options1).city.get(options2).region_id;
                city_name = options2Items.get(options1).get(options2);

                area_id = options1Items.get(options1).city.get(options2).area.get(options3).region_id;
                area_name = options3Items.get(options1).get(options2).get(options3);

                String tx = options1Items.get(options1).region_name +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                tvProvince.setText(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setCancelColor(R.color.themColor)
                .setSubmitColor(R.color.themColor)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
}
