package com.jhcms.run.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.Text;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.ShopItemModel;
import com.jhcms.run.dialog.PickerDialog;
import com.jhcms.run.dialog.SelectTimeDialog;
import com.jhcms.run.fragment.Run_HomeFragment;
import com.jhcms.run.mode.AddressInfoModel;
import com.jhcms.run.mode.CreateOrderInfoModel;
import com.jhcms.run.mode.DayConfigInfoModel;
import com.jhcms.run.mode.HongbaoInfoModel;
import com.jhcms.run.mode.OrderPriceInfoModel;
import com.jhcms.run.mode.SelectTypeInfoModel;
import com.jhcms.run.mode.TimeInfoModel;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.activity.ToPayNewActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RunHelpDeloveryActivity extends SwipeBaseActivity {
    private static final String ORDER_ID_PARAM = "ordeId";
    private static final String AGAIN_PARAM = "again";
    private static final String WEIGHT_PARAM = "weight";
    private static final String TYPE_PARAM = "type";
    private static final String PRICE_PARAM = "price";
    private static final String CATE_ID_PARAM = "cateId";
    private static final String XIAOFEI_PARAM = "xiaofei";
    private static final int REQUEST_ADDRESS_CODE = 0x321;
    private static final int FAHUO_TYPE = 1;
    private static final int SHOUHUO_TYPE = -1;

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.tv_cate)
    TextView tvCate;
    @Bind(R.id.tv_weight)
    TextView tvWeight;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_fa_addr)
    TextView tvFaAddr;
    @Bind(R.id.tv_fa_addr_list)
    TextView tvFaAddrList;
    @Bind(R.id.tv_shou_addr)
    TextView tvShouAddr;
    @Bind(R.id.tv_shou_addr_list)
    TextView tvShouAddrList;
    @Bind(R.id.tv_get_time)
    TextView tvGetTime;
    @Bind(R.id.ll_time)
    LinearLayout llTime;
    @Bind(R.id.iv_info)
    ImageView ivInfo;
    @Bind(R.id.tv_run_price)
    TextView tvRunPrice;
    @Bind(R.id.ll_run_price)
    LinearLayout llRunPrice;
    @Bind(R.id.tv_xiaofei)
    TextView tvXiaofei;
    @Bind(R.id.ll_shangci)
    LinearLayout llShangci;
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
    private String type;
    private String weight;
    private String price;
    private String cateId;
    //可以使用的红包列表
    private List<HongbaoInfoModel> hongbao;
    private List<DayConfigInfoModel> day_config;
    private TimeInfoModel time;
    //标记当前选择地址类型：-1：收货地址 1：发货地址
    private int currentAddressType;
    private AddressInfoModel addrShou;
    private AddressInfoModel addrFa;
    //用户选择的红包
    private HongbaoInfoModel selectHongBao;
    //用户选择的配送时间
    private String selectTime;
    private DayConfigInfoModel selectDay;
    private ArrayList<String> xiaofeiData;

    /**
     * @param context
     * @param type    物品类型
     * @param weight  物品重量
     * @param price   物品价值
     * @return
     */
    public static Intent generateIntent(Context context, @Nullable String type, @Nullable String weight
            , @Nullable String price, @Nullable String cateId, @NonNull ArrayList<String> xiaofei
            , boolean isAgain, String orderId) {
        Intent intent = new Intent(context, RunHelpDeloveryActivity.class);
        intent.putExtra(TYPE_PARAM, type);
        intent.putExtra(WEIGHT_PARAM, weight);
        intent.putExtra(PRICE_PARAM, price);
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

    //选择类型下单
    private void requestTypeData() {
        String paramsData = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "song");
            jsonObject.put("weight", weight);
            jsonObject.put("cate_id", cateId);
            jsonObject.put("price", price);
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
        if (!TextUtils.isEmpty(data1.getWeight())) {
            tvWeight.setText(data1.getWeight());
            weight=data1.getWeight();
        }
        if (!TextUtils.isEmpty(data1.getPrice())) {
            tvPrice.setText(data1.getPrice());
            price=data1.getPrice();
        }
        if (data1.getCate()!=null&&!TextUtils.isEmpty(data1.getCate().getTitle())) {
            tvCate.setText(data1.getCate().getTitle());
            type=data1.getCate().getTitle();
        }
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_run_help_delovery);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        type = intent.getStringExtra(TYPE_PARAM);
        weight = intent.getStringExtra(WEIGHT_PARAM);
        price = intent.getStringExtra(PRICE_PARAM);
        cateId = intent.getStringExtra(CATE_ID_PARAM);
        xiaofeiData = (ArrayList<String>) intent.getSerializableExtra(XIAOFEI_PARAM);
        tvCate.setText(type);
        tvWeight.setText(weight);
        tvPrice.setText(price);
        tvRunPrice.setText(getString(R.string.mall_¥f, "0"));
        tvRunPrice.setTag("0");
        tvXiaofei.setText(getString(R.string.mall_¥f, "0"));
        tvXiaofei.setTag("0");
        if (hongbao != null && hongbao.size() > 0) {

            tvHongbao.setHint(R.string.请选择);
        }
        titleTv.setText(R.string.帮我送);
    }

    @OnClick({R.id.back_iv, R.id.tv_fa_addr_list, R.id.tv_shou_addr_list
            , R.id.ll_time, R.id.iv_info, R.id.ll_run_price, R.id.ll_shangci
            , R.id.ll_hongbao, R.id.tv_xiadan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                onBackPressed();
                break;
            case R.id.tv_fa_addr_list:
                currentAddressType = FAHUO_TYPE;
                startActivityForResult(new Intent(this, SelectAddressActivity.class), REQUEST_ADDRESS_CODE);
                break;
            case R.id.tv_shou_addr_list:
                currentAddressType = SHOUHUO_TYPE;
                startActivityForResult(new Intent(this, SelectAddressActivity.class), REQUEST_ADDRESS_CODE);
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
            case R.id.ll_run_price:
                break;
            case R.id.ll_shangci:
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADDRESS_CODE) {
            if (resultCode == RESULT_OK) {
                AddressInfoModel temp = (AddressInfoModel) data.getSerializableExtra(SelectAddressActivity.RESUTL);
                if (currentAddressType == SHOUHUO_TYPE) {
                    addrShou = temp;
                    tvShouAddr.setText(addrShou.getAddr());
                } else {
                    addrFa = temp;
                    tvFaAddr.setText(addrFa.getAddr());
                }
                if (addrShou != null && addrFa != null) {
                    requestPrice();
                }
            }
        }
    }

    /**
     * 获取订单金额
     */
    private void requestPrice() {
        if (addrFa == null || addrShou == null) {
            return;
        }
        String paramsData = null;
        try {
            JSONObject object = new JSONObject();
            object.put("type", "song");
            object.put("m_addr_id", addrFa.getAddr_id());
            object.put("s_addr_id", addrShou.getAddr_id());
            object.put("weight", weight);
            if (selectHongBao != null) {
                object.put("hongbao_id", selectHongBao);
            }
            if (!TextUtils.isEmpty(selectTime)) {
                object.put("pei_time", selectTime);
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
                    Toast.makeText(RunHelpDeloveryActivity.this, R.string.mall_程序出错, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
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
     * 创建订单
     */
    public void requestCreateOrder() {
        if (addrFa == null || addrShou == null) {
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
            object.put("product_info", type);
            object.put("type", "song");
            object.put("s_addr_id", addrShou.getAddr_id());
            object.put("m_addr_id", addrFa.getAddr_id());
            object.put("pei_time", selectTime);
            object.put("day", selectDay.getDate());
            object.put("weight", weight);
            object.put("price", price);
            object.put("tip",(String)tvXiaofei.getTag());
            if (selectHongBao != null) {
                object.put("hongbao_id", selectHongBao.getHondbao_id());
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
                    ToPayNewActivity.startActivity(RunHelpDeloveryActivity.this,data.getData().getOrder_id(),Double.parseDouble(totalPrice),ToPayNewActivity.TO_PAOTUI);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RunHelpDeloveryActivity.this, R.string.mall_程序出错, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
