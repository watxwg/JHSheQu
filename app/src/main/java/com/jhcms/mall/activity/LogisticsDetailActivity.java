package com.jhcms.mall.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;
import com.liaoinstan.springview.widget.SpringView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：WangWei
 * 描述：物流详情界面
 */
public class LogisticsDetailActivity extends AppCompatActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_logistics_status)
    TextView tvLogisticsStatus;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    @Bind(R.id.spring)
    SpringView spring;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
    }
}
