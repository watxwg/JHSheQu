package com.jhcms.waimaiV3.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.CouponsViewPagerAdapter;
import com.jhcms.waimaiV3.fragment.WaiMai_AvailableFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by Wyj on 2017/5/9
 * TODO:
 */
public class MineCouponsActivity extends SwipeBaseActivity {

    List<Fragment> fragmentList;
    String[] titles = {"可使用", "不可用"};
    @Bind(R.id.mine_toolbar)
    Toolbar mineToolbar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.coupons_viewPager)
    ViewPager couponsViewPager;
    private CouponsViewPagerAdapter pagerAdapter;



    @Override
    protected void initView() {
        setContentView(R.layout.activity_mine_coupons);
        ButterKnife.bind(this);
    }
    @Override
    protected void initData() {
        fragmentList = new ArrayList<>();
        mineToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        for (int i = 0; i < 2; i++) {
            WaiMai_AvailableFragment availableFragment = new WaiMai_AvailableFragment();
            fragmentList.add(availableFragment);//TODO 可使用 不可用
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            availableFragment.setArguments(bundle);
        }
        pagerAdapter = new CouponsViewPagerAdapter(getSupportFragmentManager(), fragmentList, titles);
        couponsViewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(couponsViewPager);
    }


    @Override
    protected void onStart() {
        super.onStart();
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 30, 30);
            }
        });
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
