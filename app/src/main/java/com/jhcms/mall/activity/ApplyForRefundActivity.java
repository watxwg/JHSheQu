package com.jhcms.mall.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：WangWei
 * 描述：申请退款界面
 */
public class ApplyForRefundActivity extends AppCompatActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_refund_way)
    TextView tvRefundWay;
    @Bind(R.id.ll_refund_way)
    LinearLayout llRefundWay;
    @Bind(R.id.tv_order_status)
    TextView tvOrderStatus;
    @Bind(R.id.ll_order_status)
    LinearLayout llOrderStatus;
    @Bind(R.id.tv_refund_amount)
    TextView tvRefundAmount;
    @Bind(R.id.et_refund_explain)
    EditText etRefundExplain;
    @Bind(R.id.et_phone_num)
    EditText etPhoneNum;
    @Bind(R.id.iv_add_pic)
    ImageView ivAddPic;
    @Bind(R.id.ll_image_container)
    LinearLayout llImageContainer;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    @Bind(R.id.activity_apply_for_refund)
    LinearLayout activityApplyForRefund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_refund);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.ll_refund_way, R.id.ll_order_status, R.id.iv_add_pic, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.ll_refund_way:
                break;
            case R.id.ll_order_status:
                break;
            case R.id.iv_add_pic:
                break;
            case R.id.tv_submit:
                break;
        }
    }
}
