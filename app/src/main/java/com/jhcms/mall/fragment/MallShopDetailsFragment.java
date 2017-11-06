package com.jhcms.mall.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhcms.common.widget.CustomViewpager;
import com.jhcms.waimaiV3.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/5/5.
 */
public class MallShopDetailsFragment extends Fragment {

    @Bind(R.id.rv_list)
    RecyclerView rvList;

    public MallShopDetailsFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public MallShopDetailsFragment(int mPostion, CustomViewpager vp) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mall_shop_details_fragment, null);

        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
