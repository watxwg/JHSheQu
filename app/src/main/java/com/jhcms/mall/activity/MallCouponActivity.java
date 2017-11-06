package com.jhcms.mall.activity;


import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.ListViewForListView;
import com.jhcms.mall.adapter.MallCouponAdapter;
import com.jhcms.waimaiV3.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

public class MallCouponActivity extends BaseActivity {
    private View mTitleview;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private SpringView mRefreshLayout;
    private ListViewForListView mListview;
    private MallCouponAdapter mAdapter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_coupon);
    }

    @Override
    protected void initData() {
        inintRefreshlayout();
        inintListview();
    }

    private void inintListview() {
        mAdapter=new MallCouponAdapter(MallCouponActivity.this);
        mListview.setAdapter(mAdapter);
    }

    private void inintRefreshlayout() {
        mRefreshLayout.setGive(SpringView.Give.BOTH);
        mRefreshLayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallCouponActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        mRefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallCouponActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        mRefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);

            }
        });
        mRefreshLayout.setHeader(new DefaultHeader(MallCouponActivity.this));
        mRefreshLayout.setFooter(new DefaultFooter(MallCouponActivity.this));
        mRefreshLayout.setType(SpringView.Type.FOLLOW);
    }
    @Override
    protected void initActionBar() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallCouponActivity.this.finish();
            }
        });
        mTvTitle.setText("领取优惠券");
    }

    @Override
    protected void initFindViewById() {
        mTitleview=this.findViewById(R.id.title);
        mIvBack= (ImageView) mTitleview.findViewById(R.id.back_iv);
        mTvTitle= (TextView) mTitleview.findViewById(R.id.title_tv);
        mRefreshLayout= (SpringView) findViewById(R.id.refreshLayout);
        mListview= (ListViewForListView) this.findViewById(R.id.lsitview);
    }

    @Override
    protected void initEvent() {

    }
}
