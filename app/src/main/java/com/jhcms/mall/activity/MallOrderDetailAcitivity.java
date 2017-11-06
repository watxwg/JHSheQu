package com.jhcms.mall.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：WangWei
 * 描述：订单详情界面
 */
public class MallOrderDetailAcitivity extends AppCompatActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_order_status)
    ImageView ivOrderStatus;
    @Bind(R.id.tv_order_status)
    TextView tvOrderStatus;
    @Bind(R.id.tv_order_status_des)
    TextView tvOrderStatusDes;
    @Bind(R.id.tv_receing_name)
    TextView tvReceingName;
    @Bind(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @Bind(R.id.tv_receing_address)
    TextView tvReceingAddress;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.ll_product_container)
    LinearLayout llProductContainer;
    @Bind(R.id.tv_delivery_way)
    TextView tvDeliveryWay;
    @Bind(R.id.tv_youhui_num)
    TextView tvYouhuiNum;
    @Bind(R.id.tv_total_price)
    TextView tvTotalPrice;
    @Bind(R.id.tv_delivery_prices)
    TextView tvDeliveryPrices;
    @Bind(R.id.tv_true_price)
    TextView tvTruePrice;
    @Bind(R.id.ll_order_info_container)
    LinearLayout llOrderInfoContainer;
    @Bind(R.id.ll_my_evaluate)
    LinearLayout llMyEvaluate;
    @Bind(R.id.tv_view_logistics)
    TextView tvViewLogistics;
    @Bind(R.id.tv_confirm_receiving)
    TextView tvConfirmReceiving;
    @Bind(R.id.tv_go_to_evaluate)
    TextView tvGoToEvaluate;
    @Bind(R.id.ll_status_btn_conatainer)
    LinearLayout llStatusBtnConatainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_order_detail_acitivity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.ll_my_evaluate
            , R.id.tv_view_logistics, R.id.tv_confirm_receiving
            , R.id.tv_go_to_evaluate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.ll_my_evaluate:
                break;
            case R.id.tv_view_logistics:
                break;
            case R.id.tv_confirm_receiving:
                break;
            case R.id.tv_go_to_evaluate:
                break;
        }
    }
}
