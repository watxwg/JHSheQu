package com.jhcms.waimaiV3.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jhcms.common.model.IndexCate;
import com.jhcms.waimaiV3.fragment.FunctionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * on 2017/8/19.
 * TODO
 */

public class FunctionPagerAdapter extends FragmentPagerAdapter {
    private String type;
    private int pageCount, pageSize;
    private List<IndexCate> indexCate;

    /**
     * @param paramFragmentManager
     * @param indexCate            所有分类数据
     * @param pageCount            总页数
     * @param pageSize             每一页显示的个数
     * @param type                 外卖or团购
     */
    public FunctionPagerAdapter(FragmentManager paramFragmentManager, List<IndexCate> indexCate, int pageCount, int pageSize, String type) {
        super(paramFragmentManager);
        this.pageCount = pageCount;
        this.indexCate = indexCate;
        this.pageSize = pageSize;
        this.type = type;
    }

    public int getCount() {
        return pageCount;
    }

    public Fragment getItem(int paramInt) {
        List<IndexCate> function = new ArrayList<IndexCate>();
        if (paramInt < pageCount - 1) {
            for (int i = paramInt * pageSize; i < pageSize * (paramInt + 1); i++) {
                function.add(indexCate.get(i));
            }
        } else {
            for (int i = paramInt * pageSize; i < indexCate.size(); i++) {
                function.add(indexCate.get(i));
            }
        }
        return new FunctionFragment(function,type);
    }
}
