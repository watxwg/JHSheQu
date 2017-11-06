package com.jhcms.shequ.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jhcms.shequ.fragment.HomeFragment;
import com.jhcms.shequ.fragment.MineFragment;
import com.jhcms.shequ.fragment.OrderFragment;
import com.jhcms.shequ.fragment.ShopCarFragment;
import com.jhcms.shequ.fragment.TypeFragment;


/**
 * Created by Administrator on 2016/12/6.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private static int TCOUNT = 5;

    public MainPagerAdapter(FragmentManager paramFragmentManager) {
        super(paramFragmentManager);
    }

    public int getCount() {
        return TCOUNT;
    }

    public Fragment getItem(int paramInt) {
        if (paramInt == 0) {
            return new HomeFragment();
        } else if (paramInt == 1) {
            return new TypeFragment();
        } else if (paramInt == 2) {
            return new ShopCarFragment();
        }else if (paramInt == 3) {
            return new OrderFragment();
        } else if (paramInt == 4) {
            return new MineFragment();
        }
        return null;
    }
}
