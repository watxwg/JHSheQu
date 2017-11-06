package com.jhcms.tuangou.activity;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.waimaiV3.R;
import com.jhcms.tuangou.adapter.TuanFavorableBuyOrderAdapter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

/**
 * 优惠买单订单
 */
public class TuanFavorableBuyOrderActvity extends BaseActivity {
    private View mHeadView;
    private ImageView mIvBack;
    private TextView mTitleContent;
    private SpringView mRefrshlayout;
    private ListViewForScrollView mlistview;
    private TuanFavorableBuyOrderAdapter mAdapter;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_favorable_buy_order_actvity);
    }

    @Override
    protected void initData() {
        inintRefrshlayout();
        inintlistview();
    }

    private void inintlistview() {
        mAdapter=new TuanFavorableBuyOrderAdapter(TuanFavorableBuyOrderActvity.this);
        mlistview.setAdapter(mAdapter);
    }

    private void inintRefrshlayout() {
        mRefrshlayout.setGive(SpringView.Give.BOTH);//摄制刷新方式
        mRefrshlayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TuanFavorableBuyOrderActvity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        mRefrshlayout.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TuanFavorableBuyOrderActvity.this,"onLoadmore",Toast.LENGTH_LONG).show();
                        mRefrshlayout.onFinishFreshAndLoad();
                    }
                }, 1000);

            }
        });
        mRefrshlayout.setHeader(new DefaultHeader(this));
        mRefrshlayout.setFooter(new DefaultFooter(this));
        mRefrshlayout.setType(SpringView.Type.FOLLOW);
    }

    @Override
    protected void initActionBar() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuanFavorableBuyOrderActvity.this.finish();
            }
        });
        mTitleContent.setText("优惠买单订单");
    }

    @Override
    protected void initFindViewById() {
        mHeadView=this.findViewById(R.id.title);
        mIvBack= (ImageView) mHeadView.findViewById(R.id.back_iv);
        mTitleContent= (TextView) mHeadView.findViewById(R.id.title_tv);
        mRefrshlayout= (SpringView) this.findViewById(R.id.layout);
        mlistview= (ListViewForScrollView) this.findViewById(R.id.listview);


    }

    @Override
    protected void initEvent() {

    }
}
