package com.jhcms.tuangou.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.jhcms.common.model.Data_Group_Good_Detail;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GraphicDetailsActivity extends SwipeBaseActivity {

    public static String DETAIL = "GraphicDetails";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.wv_details)
    WebView wvDetails;
    @Bind(R.id.tv_per_capita)
    TextView tvPerCapita;
    @Bind(R.id.tv_comm_oldPrices)
    TextView tvCommOldPrices;
    @Bind(R.id.tv_pay)
    TextView tvPay;
    private Data_Group_Good_Detail.DetailBean details;

    @Override
    protected void initData() {
        setToolBar(toolbar);
        details = (Data_Group_Good_Detail.DetailBean) getIntent().getSerializableExtra(DETAIL);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText(R.string.图文详情);
        wvDetails.getSettings().setSupportZoom(true);
        wvDetails.getSettings().setBuiltInZoomControls(true);
        wvDetails.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvDetails.getSettings().setLoadWithOverviewMode(true);


        wvDetails.loadDataWithBaseURL("", details.detail, "text/html", "UTF-8", "");
        tvPerCapita.setText("￥" + details.price);
        tvCommOldPrices.setText("￥" + details.market_price);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_graphic_details);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.tv_pay)
    public void onViewClicked() {
        Intent intent = new Intent(this, TuanConfirmOrderActivity.class);
        intent.putExtra(GraphicDetailsActivity.DETAIL, details);
        startActivity(intent);
    }
}
