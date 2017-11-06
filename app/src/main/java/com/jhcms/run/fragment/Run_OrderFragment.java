package com.jhcms.run.fragment;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.CouponsViewPagerAdapter;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * on 2017/9/28.15:23
 * TODO
 */

public class Run_OrderFragment extends WaiMai_BaseFragment {
    @Bind(R.id.mine_toolbar)
    Toolbar mineToolbar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.vp_order)
    ViewPager vpOrder;
    private String[] titles = {"未完成", "已完成", "全部"};
    private List<Fragment> fragments;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_run_order, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        setToolBar(mineToolbar);
        fragments = new ArrayList<>();
        /*RunOrderUnDoneFragment unDoneFragment = new RunOrderUnDoneFragment();
        RunOrderCompletedFragment completedFragment = new RunOrderCompletedFragment();
        RunOrderAllFragment allFragment = new RunOrderAllFragment();
        fragments.add(unDoneFragment);
        fragments.add(completedFragment);
        fragments.add(allFragment);*/
        /*status	是	int	1:全部 2:进行中 3：已完成*/
        fragments.add(new RunOrderAllFragment(2));
        fragments.add(new RunOrderAllFragment(3));
        fragments.add(new RunOrderAllFragment(1));
        CouponsViewPagerAdapter adapter = new CouponsViewPagerAdapter(getActivity().getSupportFragmentManager(), fragments, titles);
        vpOrder.setAdapter(adapter);
        tabLayout.setupWithViewPager(vpOrder);
    }

    @Override
    public void onStart() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
