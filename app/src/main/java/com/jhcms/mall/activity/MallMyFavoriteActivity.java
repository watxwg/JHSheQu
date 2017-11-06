package com.jhcms.mall.activity;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.MallMyFavoriteAdapter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

public class MallMyFavoriteActivity extends com.jhcms.common.widget.BaseActivity {
    private SpringView  RefreshLayout;
    private ListViewForScrollView mLvlistview;
    private MallMyFavoriteAdapter mAdapter;
    private View mTitleview;
    private ImageView IvBack;
    private TextView mTvtitle;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_my_favorite);
    }

    @Override
    protected void initData() {
        initRefreshlayout();
        inintListview();
    }

    private void inintListview() {
        mAdapter=new MallMyFavoriteAdapter(MallMyFavoriteActivity.this);
        mLvlistview.setAdapter(mAdapter);
    }

    private void initRefreshlayout() {
        RefreshLayout.setGive(SpringView.Give.BOTH);
        RefreshLayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallMyFavoriteActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        RefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallMyFavoriteActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        RefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);

            }
        });
        RefreshLayout.setHeader(new DefaultHeader(MallMyFavoriteActivity.this));
        RefreshLayout.setFooter(new DefaultFooter(MallMyFavoriteActivity.this));
        RefreshLayout.setType(SpringView.Type.FOLLOW);
    }

    @Override
    protected void initActionBar() {
        IvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallMyFavoriteActivity.this.finish();
            }
        });
        mTvtitle.setText("收藏商品");

    }

    @Override
    protected void initFindViewById() {
        RefreshLayout= (SpringView) this.findViewById(R.id.refreshlayout);
        mLvlistview= (ListViewForScrollView) this.findViewById(R.id.listview);
        mTitleview=this.findViewById(R.id.title);
        IvBack= (ImageView) mTitleview.findViewById(R.id.back_iv);
        mTvtitle= (TextView) mTitleview.findViewById(R.id.title_tv);
    }

    @Override
    protected void initEvent() {

    }
}
