package com.jhcms.mall.activity;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.mall.adapter.MallTopShopRankingActivityAdapter;
import com.jhcms.waimaiV3.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

/**
 * 收藏的商品
 */
public class MallFavoritesGoodsActivity extends BaseActivity {
    private View mTitleView;
    private ImageView mIvBack;
    private TextView mTitlteContent;
    private SpringView RefreshLayout;
    private MallTopShopRankingActivityAdapter mAdapter;
    private ListViewForScrollView mListview;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_favorites_goods);
    }

    @Override
    protected void initData() {
        initRefreshlayout();
        inintListview();
    }

    private void inintListview() {
        mAdapter=new MallTopShopRankingActivityAdapter(MallFavoritesGoodsActivity.this);
        mAdapter.setMallFavoritesflag(true);
        mListview.setAdapter(mAdapter);
    }

    private void initRefreshlayout() {
        RefreshLayout.setGive(SpringView.Give.BOTH);
        RefreshLayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallFavoritesGoodsActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        RefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallFavoritesGoodsActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        RefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);

            }
        });
        RefreshLayout.setHeader(new DefaultHeader(MallFavoritesGoodsActivity.this));
        RefreshLayout.setFooter(new DefaultFooter(MallFavoritesGoodsActivity.this));
        RefreshLayout.setType(SpringView.Type.FOLLOW);
    }

    @Override
    protected void initActionBar() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallFavoritesGoodsActivity.this.finish();
            }
        });
        mTitlteContent.setText("收藏的商品");

    }

    @Override
    protected void initFindViewById() {
        mTitleView=this.findViewById(R.id.title);
        mIvBack= (ImageView) mTitleView.findViewById(R.id.back_iv);
        mTitlteContent= (TextView) mTitleView.findViewById(R.id.title_tv);
        RefreshLayout= (SpringView) this.findViewById(R.id.refreshlayout);
        mListview= (ListViewForScrollView) this.findViewById(R.id.listview);
    }

    @Override
    protected void initEvent() {

    }
}
