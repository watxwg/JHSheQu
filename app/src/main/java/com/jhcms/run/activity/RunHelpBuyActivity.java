package com.jhcms.run.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.route.TMC;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.run.dialog.PickerDialog;
import com.jhcms.run.dialog.SelectTimeDialog;
import com.jhcms.run.mode.AddressInfoModel;
import com.jhcms.run.mode.CateInfoModel;
import com.jhcms.run.mode.CreateOrderInfoModel;
import com.jhcms.run.mode.DayConfigInfoModel;
import com.jhcms.run.mode.HongbaoInfoModel;
import com.jhcms.run.mode.OrderPriceInfoModel;
import com.jhcms.run.mode.SelectTypeInfoModel;
import com.jhcms.run.mode.TimeInfoModel;
import com.jhcms.shequ.activity.ChangeMobileActivity;
import com.jhcms.shequ.activity.SearchMapActivity;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.activity.ToPayNewActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RunHelpBuyActivity extends SwipeBaseActivity {
    private static final String CATE_ID_PARAM = "cateId";
    private static final String ORDER_ID_PARAM = "ordeId";
    private static final String AGAIN_PARAM = "again";
    private static final String CATE_NAME_PARAM = "cateName";
    private static final String XIAOFEI_PARAM = "xiaofei";
    private static final int REQUEST_SHOU_ADDRESS_CODE = 0x321;
    private static final int REQUEST_GOU_ADDRESS_CODE = 0x322;

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.tfl_tag)
    TagFlowLayout tflTag;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_gou_addr)
    TextView tvGouAddr;
    @Bind(R.id.ll_addr_gou)
    LinearLayout llAddrGou;
    @Bind(R.id.tv_shou_addr)
    TextView tvShouAddr;
    @Bind(R.id.tv_shou_addr_list)
    TextView tvShouAddrList;
    @Bind(R.id.ll_shou)
    LinearLayout llShou;
    @Bind(R.id.tv_get_time)
    TextView tvGetTime;
    @Bind(R.id.ll_time)
    LinearLayout llTime;
    @Bind(R.id.et_yugufeiyong)
    EditText etYugufeiyong;
    @Bind(R.id.iv_info)
    ImageView ivInfo;
    @Bind(R.id.tv_run_price)
    TextView tvRunPrice;
    @Bind(R.id.tv_xiaofei)
    TextView tvXiaofei;
    @Bind(R.id.ll_xiaofei)
    LinearLayout llXiaofei;
    @Bind(R.id.tv_hongbao)
    TextView tvHongbao;
    @Bind(R.id.ll_hongbao)
    LinearLayout llHongbao;
    @Bind(R.id.cb_agree_protocol)
    CheckBox cbAgreeProtocol;
    @Bind(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @Bind(R.id.tv_xiadan)
    TextView tvXiadan;
    private ArrayList<String> xiaofeiData;
    private String cateId;
    private List<HongbaoInfoModel> hongbao;
    private List<DayConfigInfoModel> day_config;
    private TimeInfoModel time;
    private AddressInfoModel addrShou;
    private DayConfigInfoModel selectDay;
    private String selectTime;
    private ArrayList<String> tagData;
    private TagAdapter<String> tagAdapter;
    private HongbaoInfoModel selectHongBao;
    private AddressInfoModel addrGou;

    /**
     * @param context
     * @param name    物品名称
     * @param cateId  物品类型id
     * @param xiaofei 小费列表
     * @return
     */
    public static Intent generateIntent(Context context, @Nullable String name
            , @Nullable String cateId, @NonNull ArrayList<String> xiaofei
            , boolean isAgain, @Nullable String orderId) {
        Intent intent = new Intent(context, RunHelpBuyActivity.class);
        intent.putExtra(CATE_NAME_PARAM, name);
        intent.putExtra(CATE_ID_PARAM, cateId);
        intent.putExtra(XIAOFEI_PARAM, xiaofei);
        intent.putExtra(AGAIN_PARAM, isAgain);
        intent.putExtra(ORDER_ID_PARAM, orderId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        if (getIntent().getBooleanExtra(AGAIN_PARAM, false)) {
            String orderId = getIntent().getStringExtra(ORDER_ID_PARAM);
            if (!TextUtils.isEmpty(orderId)) {
                requestAgainData(orderId);
            }else {
                Toast.makeText(this, R.string.mall_程序出错, Toast.LENGTH_SHORT).show();
            }
        } else {

            requestTypeData();
        }
    }

    /**
     * 再来一单
     */
    private void requestAgainData(String orderId) {
        String paramsData = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("order_id", orderId);
            paramsData = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.mall_程序出错, Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils.postUrlWithBaseResponse(this, Api.PAOTUI_ORDER_AGAIN, paramsData, true, new OnRequestSuccess<BaseResponse<SelectTypeInfoModel>>() {
            @Override
            public void onSuccess(String url, BaseResponse<SelectTypeInfoModel> data) {
                super.onSuccess(url, data);
                SelectTypeInfoModel data1 = data.getData();
                processSelectTypeData(data1);
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_run_help_buy);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String shopName = intent.getStringExtra(CATE_NAME_PARAM);
        cateId = intent.getStringExtra(CATE_ID_PARAM);
        xiaofeiData = (ArrayList<String>) intent.getSerializableExtra(XIAOFEI_PARAM);
        titleTv.setText(R.string.帮我买);
        etContent.setText(shopName);
        etContent.setSelection(etContent.getText().toString().length());
        tagData = new ArrayList<String>();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        tagAdapter = new TagAdapter<String>(tagData) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) layoutInflater.inflate(R.layout.run_tag_layout, parent, false);
                tv.setText(s);
                return tv;
            }
        };
        tflTag.setAdapter(tagAdapter);
        tflTag.setOnTagClickListener((view, position, parent) -> {
            String s = ((TextView) view).getText().toString();
            if (!TextUtils.isEmpty(etContent.getText().toString())) {
                s = " " + s;
            }
            etContent.setText(etContent.getText().toString() + s);
            etContent.setSelection(etContent.getText().toString().length());
            return true;
        });

        tvRunPrice.setText(getString(R.string.mall_¥f, "0"));
        tvRunPrice.setTag("0");
        tvXiaofei.setText(getString(R.string.mall_¥f, "0"));
        tvXiaofei.setTag("0");
        if (hongbao != null && hongbao.size() > 0) {

            tvHongbao.setHint(R.string.请选择);
        }
        titleTv.setText(R.string.帮我买);
    }

    @OnClick({R.id.back_iv, R.id.ll_addr_gou, R.id.ll_shou, R.id.ll_time, R.id.iv_info, R.id.ll_xiaofei, R.id.ll_hongbao, R.id.tv_xiadan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                onBackPressed();
                break;
            case R.id.ll_addr_gou:
                Intent intent2 = new Intent(this, BuyAddressSetActivity.class);
                startActivityForResult(intent2, REQUEST_GOU_ADDRESS_CODE);
                break;
            case R.id.ll_shou:
                startActivityForResult(new Intent(this, SelectAddressActivity.class), REQUEST_SHOU_ADDRESS_CODE);
                break;
            case R.id.ll_time:
                showTimeDialog();
                break;
            case R.id.iv_info:
                String temp = "0";
                String runPrice = tvRunPrice.getText().toString();
                if (!TextUtils.isEmpty(runPrice) && runPrice.contains(getString(R.string.元))) {
                    temp = runPrice.substring(0, runPrice.indexOf(getString(R.string.元)));
                }
                Intent intent = ChargeExplainActivity.generateIntent(this, temp);
                startActivity(intent);
                break;
            case R.id.ll_xiaofei:

                showPickerDialog(xiaofeiData, getString(R.string.元), getString(R.string.选择小费金额));
                break;
            case R.id.ll_hongbao:
                if (hongbao != null && hongbao.size() > 0) {
                    showHongbaoDialog(hongbao, getString(R.string.元), getString(R.string.选择红包金额));
                }
                break;
            case R.id.tv_xiadan:
                requestCreateOrder();
                break;
        }
    }

    /**
     * 选择服务时间对话框
     */
    private void showTimeDialog() {
        SelectTimeDialog dialog = new SelectTimeDialog(time.getSet_time(), time.getNomal_time(), day_config);
        dialog.setShowBoottom(true);
        dialog.setHeight(0.65f, getResources().getDisplayMetrics());
        dialog.setOnTimeSelectListener(((dayConfigInfoModel, time1) -> {
            tvGetTime.setText(dayConfigInfoModel.getDay() + " " + time1);
            selectDay = dayConfigInfoModel;
            selectTime = time1;
            requestPrice();
        }));
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    /**
     * 获取订单金额
     */
    private void requestPrice() {
        if (addrShou == null) {
            return;
        }
        String paramsData = null;
        try {
            JSONObject object = new JSONObject();
            object.put("type", "mai");
            object.put("s_addr_id", addrShou.getAddr_id());
            if (selectHongBao != null) {
                object.put("hongbao_id", selectHongBao);
            }
            if (!TextUtils.isEmpty(selectTime)) {
                object.put("pei_time", selectTime);
            }
            if (addrGou != null) {
                object.put("m_lng", addrGou.getLng());
                object.put("m_lat", addrGou.getLat());
            }
            paramsData = object.toString();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.mall_程序出错, Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils.postUrlWithBaseResponse(this, Api.PAOTUI_GET_ORDER_PRICE, paramsData, true, new OnRequestSuccess<BaseResponse<OrderPriceInfoModel>>() {
            @Override
            public void onSuccess(String url, BaseResponse<OrderPriceInfoModel> data) {
                super.onSuccess(url, data);
                try {
                    //订单总价
                    float totalPrice = 0;
                    hongbao = data.getData().getHongbao();
                    if (hongbao != null && hongbao.size() > 0) {

                        tvHongbao.setHint(R.string.请选择);
                    }
                    tvRunPrice.setText(getString(R.string.mall_¥f, data.getData().getPei_amount()));
                    String xiaofei = (String) tvXiaofei.getTag();
                    if (!TextUtils.isEmpty(xiaofei)) {
                        totalPrice = Float.parseFloat(xiaofei) + Float.parseFloat(data.getData().getPei_amount());
                    } else {
                        totalPrice = Float.parseFloat(data.getData().getPei_amount());
                    }
                    tvGoodsPrice.setText(getString(R.string.mall_¥f, totalPrice + ""));
                    tvGoodsPrice.setTag(totalPrice + "");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RunHelpBuyActivity.this, R.string.mall_程序出错, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    /**
     * @param data     供选择的数据
     * @param modifier 数据后的修饰文本
     * @param desc     对话框顶部描述
     */
    private void showPickerDialog(@NonNull List<String> data, String modifier, String desc) {
        PickerDialog dialog = new PickerDialog();
        if (data == null) {
            return;
        }
        dialog.setData(data, modifier);
        dialog.setDesc(desc);
        dialog.setWidth(0.75f, getResources().getDisplayMetrics());
        dialog.setOnSelectListener((select, position) -> {
            tvXiaofei.setTag(select);
            tvXiaofei.setText(getString(R.string.mall_¥f, select));
            try {
                float totalPrice = Float.parseFloat(select) + Float.parseFloat((String) tvRunPrice.getTag());
                tvGoodsPrice.setText(getString(R.string.mall_¥f, totalPrice + ""));
                tvGoodsPrice.setTag(totalPrice + "");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, R.string.mall_程序出错, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show(getSupportFragmentManager(), "pickerDialog");
    }

    /**
     * 红包对话框
     *
     * @param hongbaoData 红包列表
     * @param modifier    数据修饰字符
     * @param desc        对话框描述
     */
    private void showHongbaoDialog(List<HongbaoInfoModel> hongbaoData, String modifier, String desc) {
        PickerDialog dialog = new PickerDialog();
        if (hongbaoData == null || hongbaoData.size() == 0) {
            return;
        }
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < hongbaoData.size(); i++) {
            temp.add(hongbaoData.get(i).getAmount());
        }
        dialog.setData(temp, modifier);
        dialog.setDesc(desc);
        dialog.setWidth(0.75f, getResources().getDisplayMetrics());
        dialog.setOnSelectListener((select, position) -> {
            selectHongBao = hongbaoData.get(position);
            tvHongbao.setText(getString(R.string.mall_¥f, selectHongBao.getAmount()));
            requestPrice();
        });
        dialog.show(getSupportFragmentManager(), "pickerDialog");
    }

    //选择类型下单
    private void requestTypeData() {
        String paramsData = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "mai");
            jsonObject.put("cate_id", cateId);
            paramsData = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.mall_程序出错, Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils.postUrlWithBaseResponse(this, Api.PAOTUI_SELECT_ORDER_TYPE, paramsData, true, new OnRequestSuccess<BaseResponse<SelectTypeInfoModel>>() {
            @Override
            public void onSuccess(String url, BaseResponse<SelectTypeInfoModel> data) {
                super.onSuccess(url, data);
                SelectTypeInfoModel data1 = data.getData();
                processSelectTypeData(data1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SHOU_ADDRESS_CODE) {
            if (resultCode == RESULT_OK) {
                addrShou = (AddressInfoModel) data.getSerializableExtra(SelectAddressActivity.RESUTL);
                if (addrShou != null) {
                    requestPrice();
                    tvShouAddr.setText(addrShou.getAddr());
                }
            }
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GOU_ADDRESS_CODE && data != null) {
                AddressInfoModel temp = new AddressInfoModel();
                String address = data.getStringExtra(BuyAddressSetActivity.ADDRESS);
                String lat = data.getStringExtra(BuyAddressSetActivity.LAT);
                String lng = data.getStringExtra(BuyAddressSetActivity.LNG);
                temp.setAddr(address);
                temp.setLat(lat);
                temp.setLng(lng);
                addrGou = temp;
                tvGouAddr.setText(addrGou.getAddr());
            }
        }
    }

    /**
     * 处理选择下单类型数据
     *
     * @param data1
     */
    private void processSelectTypeData(SelectTypeInfoModel data1) {
        hongbao = data1.getHongbao();
        if (hongbao != null && hongbao.size() > 0) {

            tvHongbao.setHint(R.string.请选择);
        }
        day_config = data1.getDay_config();
        String pei_amount = data1.getPei_amount();
        if (TextUtils.isEmpty(pei_amount)) {
            pei_amount = "0";
        }
        time = data1.getTime();
        tvGoodsPrice.setText(getString(R.string.mall_¥f, pei_amount));
        tvGoodsPrice.setTag(pei_amount);
        tvRunPrice.setText(getString(R.string.元f, pei_amount));
        tvRunPrice.setTag(pei_amount);
        AddressInfoModel addr = data1.getAddr();
        if (addr != null) {
            addrShou = addr;
            tvShouAddr.setText(addr.getAddr());
        }
        CateInfoModel cate = data1.getCate();
        if (cate.getConfig() != null && cate.getConfig().size() > 0) {
            tagData.clear();
            tagData.addAll(cate.getConfig());
            tagAdapter.notifyDataChanged();
        }
        if(!TextUtils.isEmpty(data1.getCate().getTitle())){
            etContent.setText(data1.getCate().getTitle());
            etContent.setSelection(etContent.getText().toString().length());
        }
    }

    /**
     * 创建订单
     */
    public void requestCreateOrder() {
        if (addrShou == null) {
            Toast.makeText(this, R.string.请选择地址, Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectDay == null || TextUtils.isEmpty(selectTime)) {
            Toast.makeText(this, R.string.请选择取货时间, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!cbAgreeProtocol.isChecked()) {
            Toast.makeText(this, R.string.请同意用户协议, Toast.LENGTH_SHORT).show();
            return;
        }
        String paramsData = null;
        try {
            JSONObject object = new JSONObject();
            object.put("product_info", etContent.getText().toString());
            object.put("type", "mai");
            object.put("s_addr_id", addrShou.getAddr_id());
            object.put("pei_time", selectTime);
            object.put("day", selectDay.getDate());
            object.put("tip",(String)tvXiaofei .getTag());
            if (selectHongBao != null) {
                object.put("hongbao_id", selectHongBao.getHondbao_id());
            }
            String yuji = etYugufeiyong.getText().toString();
            if (TextUtils.isEmpty(yuji)) {
                yuji = "0";
            }
            object.put("yuji_price", yuji);
            if (addrGou != null) {
                object.put("m_addr", addrGou.getAddr());
                object.put("m_lng", addrGou.getLng());
                object.put("m_lat", addrGou.getLat());
            }
            paramsData = object.toString();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.mall_程序出错, Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils.postUrlWithBaseResponse(this, Api.PAOTUI_CREATE_ORDER, paramsData, true, new OnRequestSuccess<BaseResponse<CreateOrderInfoModel>>() {
            @Override
            public void onSuccess(String url, BaseResponse<CreateOrderInfoModel> data) {
                super.onSuccess(url, data);
                try {

                    String totalPrice = (String) tvGoodsPrice.getTag();
                    ToPayNewActivity.startActivity(RunHelpBuyActivity.this,data.getData().getOrder_id(),Double.parseDouble(totalPrice),ToPayNewActivity.TO_PAOTUI);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RunHelpBuyActivity.this, R.string.mall_程序出错, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
