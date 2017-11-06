package com.jhcms.mall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by admin on 2017/5/3.
 */
public class MallProductDetailsViewPagerAdapter extends FragmentPagerAdapter {
  private ArrayList<Fragment> fgments;
    private ArrayList<String>Title;


    public MallProductDetailsViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fgments, ArrayList<String> title) {
        super(fm);
        this.fgments = fgments;
        Title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return fgments.get(position);
    }

    @Override
    public int getCount() {
        return fgments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return Title.get(position);
    }
}
