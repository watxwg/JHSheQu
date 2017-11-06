package com.jhcms.mall.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.ListViewForListView;
import com.jhcms.waimaiV3.R;
import com.jhcms.tuangou.adapter.MallMyOrderFragmentSonAdapter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

/**
 * 我的订单状态详情页
 */
public class MallMyOrderStatudDtailsActivity extends BaseActivity {
    private View mTitleview;
    private ImageView IvBack;
    private TextView mTvtitle;
    private SpringView RefreshLayout;
    /**
     * flag where from MallMyOrderFragmentAdapter
     * 0 全部
     * 1代付款
     * 2 代发货
     * 3 待收货
     * 4 待评价
     * 5 交易关闭
     */
    private int mFlagPostion;
    private ListViewForListView mListview;
    private MallMyOrderFragmentSonAdapter mAdapter;
    private  TextView mCancelOrder;
    private  TextView mTrueGetGood;
    private  TextView mplayStatu;
    private  TextView mLookLogistics;
    private LinearLayout mBottomLayout;
    private  TextView mPlayOrderStatu;
    private TextView  mPlaytime;
    private  ImageView mPaystatuImage;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_my_order_statud_dtails);
    }

    @Override
    protected void initData() {
        initRefreshlayout();
        inintIntent();
        InintMlistview();
    }

    private void InintMlistview() {
        mAdapter=new MallMyOrderFragmentSonAdapter(MallMyOrderStatudDtailsActivity.this);
        mAdapter.setmFlagPostion(mFlagPostion);
        mListview.setAdapter(mAdapter);
    }

    private void inintIntent() {
        Intent intent=getIntent();
        if(intent!=null){
           mFlagPostion= intent.getIntExtra("flag",-1);
        }
        /**
         * 判断网络传过来的数据
         */
        switch (mFlagPostion){
            case 0:
                break;
            case 1:
                mPlayOrderStatu.setText("待付款");
                mPaystatuImage.setImageResource(R.mipmap.mall_my_icon_orderdes_status);
                mPlaytime.setText("请于23时56分29秒内付款，超时订单将自动关闭");
                mCancelOrder.setVisibility(View.VISIBLE);
                mplayStatu.setVisibility(View.VISIBLE);
                mLookLogistics.setVisibility(View.GONE);
                mTrueGetGood.setVisibility(View.GONE);
                mBottomLayout.setVisibility(View.VISIBLE);
                break;
            case 2:
                mPlayOrderStatu.setText("等待商家发货");
                mPaystatuImage.setImageResource(R.mipmap.mall_my_icon_orderdes_status_two);
                mPlaytime.setText("商家已发货");
                mCancelOrder.setVisibility(View.VISIBLE);
                mplayStatu.setVisibility(View.VISIBLE);
                mLookLogistics.setVisibility(View.GONE);
                mTrueGetGood.setVisibility(View.GONE);
                mBottomLayout.setVisibility(View.VISIBLE);

                break;
            case 3:
                mPlayOrderStatu.setText("等待买家收货");
                mPaystatuImage.setImageResource(R.mipmap.mall_my_icon_orderdes_status_there);
                mPlaytime.setText("买家已付款");
                mCancelOrder.setVisibility(View.VISIBLE);
                mplayStatu.setVisibility(View.VISIBLE);
                mLookLogistics.setVisibility(View.GONE);
                mTrueGetGood.setVisibility(View.GONE);
                mBottomLayout.setVisibility(View.VISIBLE);

                break;
            case 4:
                mPlayOrderStatu.setText("交易成功");
                mPaystatuImage.setImageResource(R.mipmap.mall_my_icon_orderdes_status_frou);
                mPlaytime.setText("等待买家评价");
                mCancelOrder.setVisibility(View.VISIBLE);
                mplayStatu.setVisibility(View.VISIBLE);
                mLookLogistics.setVisibility(View.GONE);
                mTrueGetGood.setVisibility(View.GONE);
                mBottomLayout.setVisibility(View.VISIBLE);
                break;
            case 5:
                mPlayOrderStatu.setText("交易关闭");
                mPaystatuImage.setImageResource(R.mipmap.mall_my_icon_orderdes_status_five);
                mPlaytime.setVisibility(View.GONE);
                mCancelOrder.setVisibility(View.GONE);
                mplayStatu.setVisibility(View.GONE);
                mLookLogistics.setVisibility(View.GONE);
                mTrueGetGood.setVisibility(View.GONE);
                mBottomLayout.setVisibility(View.GONE);
                break;
        }
    }

    private void initRefreshlayout() {
        RefreshLayout.setGive(SpringView.Give.BOTH);
        RefreshLayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallMyOrderStatudDtailsActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        RefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallMyOrderStatudDtailsActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        RefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);

            }
        });
        RefreshLayout.setHeader(new DefaultHeader(MallMyOrderStatudDtailsActivity.this));
        RefreshLayout.setFooter(new DefaultFooter(MallMyOrderStatudDtailsActivity.this));
        RefreshLayout.setType(SpringView.Type.FOLLOW);
    }

    @Override
    protected void initActionBar() {
        IvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallMyOrderStatudDtailsActivity.this.finish();
            }
        });
        mTvtitle.setText("我的收货地址");
    }

    @Override
    protected void initFindViewById() {
        mTitleview=this.findViewById(R.id.title);
        IvBack= (ImageView) mTitleview.findViewById(R.id.back_iv);
        mTvtitle= (TextView) mTitleview.findViewById(R.id.title_tv);
        RefreshLayout= (SpringView) this.findViewById(R.id.refreshlayout);
        mListview= (ListViewForListView) this.findViewById(R.id.mlistview);
        mCancelOrder= (TextView) this.findViewById(R.id.cancelOrder_tv);
        mTrueGetGood= (TextView) this.findViewById(R.id.TrueGetGood_tv);
        mplayStatu= (TextView) this.findViewById(R.id.playStatu);
        mLookLogistics= (TextView) this.findViewById(R.id.mLookLogistics_tv);
        mPlayOrderStatu= (TextView) this.findViewById(R.id.payorderStatu);
        mPlaytime= (TextView) this.findViewById(R.id.playtime);
        mPaystatuImage= (ImageView) this.findViewById(R.id.paystatuImage);
        mBottomLayout= (LinearLayout) this.findViewById(R.id.bottomLayout);
    }

    @Override
    protected void initEvent() {

    }
}
