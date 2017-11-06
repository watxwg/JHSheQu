package com.jhcms.tuangou.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jhcms.tuangou.fragment.MerchantAlbumFragment;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.adapter.CouponsViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MerchantAlbumActivity extends SwipeBaseActivity {

    public static String SHOP_ID = "SHOP_ID";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tl_album)
    TabLayout tlAlbum;
    @Bind(R.id.vp_album)
    ViewPager vpAlbum;
    private String[] titles = {"商品", "环境", "其他"};
    private List<Fragment> fragments;
    private CouponsViewPagerAdapter pagerAdapter;
    private String shopId;

    @Override
    protected void initData() {
        shopId = getIntent().getStringExtra(SHOP_ID);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTitle.setText(R.string.商家相册);

        fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            MerchantAlbumFragment albumFragment = new MerchantAlbumFragment();
            fragments.add(albumFragment);
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            bundle.putString(SHOP_ID, shopId);
            albumFragment.setArguments(bundle);
        }
        pagerAdapter = new CouponsViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vpAlbum.setAdapter(pagerAdapter);
        tlAlbum.setupWithViewPager(vpAlbum);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_merchant_album);
        ButterKnife.bind(this);
    }
}
