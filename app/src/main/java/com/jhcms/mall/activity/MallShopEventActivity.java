package com.jhcms.mall.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.mall.adapter.MallShopEventAdapter;
import com.jhcms.waimaiV3.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

/**
 * 活动列表
 */
public class MallShopEventActivity extends BaseActivity {
    private ListViewForScrollView mListview;
    private SpringView mRefreshLayout;
    private ImageView mIvBack;
    private View mTitleview;
    private TextView mTvtitle;
    private MallShopEventAdapter mAdapter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_shop_event);
    }

    @Override
    protected void initData() {
        inintRefreshlayout();
        inintListview();
    }

    private void inintListview() {
        mAdapter=new MallShopEventAdapter(MallShopEventActivity.this);
        mListview.setAdapter(mAdapter);
       mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent=new Intent(MallShopEventActivity.this,MallShopDivisionActivity.class);
               startActivity(intent);
           }
       });
    }

    private void inintRefreshlayout() {
        mRefreshLayout.setGive(SpringView.Give.BOTH);
        mRefreshLayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallShopEventActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        mRefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallShopEventActivity.this,"onRefresh", Toast.LENGTH_LONG).show();
                        mRefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);

            }
        });
        mRefreshLayout.setHeader(new DefaultHeader(MallShopEventActivity.this));
        mRefreshLayout.setFooter(new DefaultFooter(MallShopEventActivity.this));
        mRefreshLayout.setType(SpringView.Type.FOLLOW);
    }

    @Override
    protected void initActionBar() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallShopEventActivity.this.finish();
            }
        });
        mTvtitle.setText("活动列表");
    }

    @Override
    protected void initFindViewById() {
        mTitleview=this.findViewById(R.id.title_layout);
        mIvBack= (ImageView) mTitleview.findViewById(R.id.iv_back);
        mTvtitle= (TextView) mTitleview.findViewById(R.id.title_content);
        mRefreshLayout= (SpringView) this.findViewById(R.id.refreshlayout);
        mListview= (ListViewForScrollView) this.findViewById(R.id.listview);

    }

    @Override
    protected void initEvent() {

    }
}
