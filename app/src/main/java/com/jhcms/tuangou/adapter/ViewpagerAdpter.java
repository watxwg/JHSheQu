package com.jhcms.tuangou.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by admin on 2017/4/17.
 */
public class ViewpagerAdpter extends FragmentPagerAdapter {
    private ArrayList<Fragment> list;
    private  ArrayList<String> titleList;


    public ViewpagerAdpter(FragmentManager fm,ArrayList<Fragment> list,ArrayList<String> titlelist) {
        super(fm);
        this.list=list;
        this.titleList=titlelist;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
