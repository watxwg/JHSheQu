package com.jhcms.tuangou.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.CouponsViewPagerAdapter;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/7/13.
 * TODO 团购订单
 */

public class Group_OrderFragment extends WaiMai_BaseFragment {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tl_order)
    TabLayout tlOrder;
    @Bind(R.id.vp_order)
    ViewPager vpOrder;
    private String[] titles = {"团购订单", "优惠订单"};
    private List<Fragment> fragments;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_group_order, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        setToolBar(toolbar);
        tvTitle.setText("订单");
        fragments = new ArrayList<>();
        fragments.add(new Group_BuyOrdersFragment());
        fragments.add(new Group_OfferToPayFragment());
        vpOrder.setAdapter(new CouponsViewPagerAdapter(getActivity().getSupportFragmentManager(), fragments, titles));
        vpOrder.setCurrentItem(0);
        tlOrder.setupWithViewPager(vpOrder);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
