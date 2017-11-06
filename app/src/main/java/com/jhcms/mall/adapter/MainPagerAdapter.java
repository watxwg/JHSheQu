package com.jhcms.mall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jhcms.mall.fragment.HomeFragment;
import com.jhcms.mall.fragment.MineFragment;
import com.jhcms.mall.fragment.ShopCarFragment;
import com.jhcms.mall.fragment.TypeFragment;


/**
 * Created by Administrator on 2016/12/6.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private static int TCOUNT = 4;

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
            return new MineFragment();
        }
        return null;
    }
}
