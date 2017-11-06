package com.jhcms.shequ.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.jhcms.shequ.adapter.OrderTypeAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/22.
 */
public class OrderFragment extends WaiMai_BaseFragment {

    @Bind(R.id.type_gv)
    GridView mTypeGv;

    OrderTypeAdapter orderTypeAdapter;
    String[] infos = new String[]{"外送", "团购", "优惠买单", "上门服务", "跑腿", "商城"};
    int[] images = new int[]{R.mipmap.icon_waimai, R.mipmap.icon_group, R.mipmap.icon_youhui, R.mipmap.icon_service, R.mipmap.icon_paotui, R.mipmap.icon_mall};
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_order, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        setToolBar(toolbar);
        tvTitle.setText("订单");
        orderTypeAdapter = new OrderTypeAdapter(getActivity());
        orderTypeAdapter.setInfos(infos);
        orderTypeAdapter.setImages(images);
        mTypeGv.setAdapter(orderTypeAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
