

package com.jhcms.shequ.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;

/**
 * Created by Administrator on 2017/3/22.
 */
public class ShopCarFragment extends WaiMai_BaseFragment {
    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_shopcar, null);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
