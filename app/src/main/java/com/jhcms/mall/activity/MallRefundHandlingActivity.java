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
 * 描述：退款处理中界面
 */
public class MallRefundHandlingActivity extends AppCompatActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_refund_number)
    TextView tvRefundNumber;
    @Bind(R.id.tv_refund_way)
    TextView tvRefundWay;
    @Bind(R.id.tv_refund_amount)
    TextView tvRefundAmount;
    @Bind(R.id.tv_refund_explain)
    TextView tvRefundExplain;
    @Bind(R.id.tv_refund_phone)
    TextView tvRefundPhone;
    @Bind(R.id.tv_refund_time)
    TextView tvRefundTime;
    @Bind(R.id.tv_view_detail)
    TextView tvViewDetail;
    @Bind(R.id.tv_weiquan)
    TextView tvWeiquan;
    @Bind(R.id.activity_mall_refund_handling)
    LinearLayout activityMallRefundHandling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_refund_handling);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_view_detail, R.id.tv_weiquan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_view_detail:
                break;
            case R.id.tv_weiquan:
                break;
        }
    }
}
