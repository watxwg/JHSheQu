package com.jhcms.mall.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.common.widget.ListViewForListView;
import com.jhcms.mall.adapter.MallHotShopSellerRankingAdpter;
import com.jhcms.waimaiV3.R;

/**
 * 热销商品排行
 */
public class MallHotShopSellerRankingActivity extends BaseActivity {
    private View titleview;
    private ImageView mIvBack;
    private TextView mTvtitle;
    private ListViewForListView mDataListview;
    private GridViewForScrollView mDatagrildView;
    private MallHotShopSellerRankingAdpter madapter;
    private  ImageView isonce;
    boolean isonceview=true;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_hot_shop_seller_ranking);
    }

    @Override
    protected void initData() {
     inintlistview();
     inintgrildview();   
    }

    private void inintgrildview() {
        madapter=new MallHotShopSellerRankingAdpter(MallHotShopSellerRankingActivity.this,false);
        mDatagrildView.setAdapter(madapter);
    }

    private void inintlistview() {
        madapter=new MallHotShopSellerRankingAdpter(MallHotShopSellerRankingActivity.this,true);
        mDataListview.setAdapter(madapter);
    }

    @Override
    protected void initActionBar() {
    mIvBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MallHotShopSellerRankingActivity.this.finish();
        }
    });
        mTvtitle.setText("热销商品排行");
    }

    @Override
    protected void initFindViewById() {
        titleview=this.findViewById(R.id.title);
        mIvBack= (ImageView) titleview.findViewById(R.id.back_iv);
        mTvtitle= (TextView) titleview.findViewById(R.id.title_tv);
        mDataListview= (ListViewForListView) this.findViewById(R.id.hotShop_lv);
        mDatagrildView= (GridViewForScrollView) this.findViewById(R.id.hotshop_gv);
        isonce= (ImageView) this.findViewById(R.id.isonce);
    }

    @Override
    protected void initEvent() {
        inintSwitch();
    }

    private void inintSwitch() {
        isonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MallHotShopSellerRankingActivity.this,"ok",Toast.LENGTH_LONG).show();
                if(isonceview){
                    isonce.setImageResource(R.mipmap.mall_btn_one_fr);
                    mDataListview.setVisibility(View.GONE);
                    mDatagrildView.setVisibility(View.VISIBLE);
                    isonceview=false;
                }else {
                    isonce.setImageResource(R.mipmap.mall_btn_two_fr);
                    mDatagrildView.setVisibility(View.GONE);
                    mDataListview.setVisibility(View.VISIBLE);
                    isonceview=true;
                }
            }
        });
    }
}
