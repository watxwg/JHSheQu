package com.jhcms.mall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/13 15:51
 * 描述：店铺详情
 */

public class MallShopDetailPagerAdapter extends FragmentPagerAdapter{
    private final List<Fragment> mFragments;
    private final List<String> mTitles;

    public MallShopDetailPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments==null?0:mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
