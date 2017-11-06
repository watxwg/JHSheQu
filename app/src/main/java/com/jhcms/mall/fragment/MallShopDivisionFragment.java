package com.jhcms.mall.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jhcms.common.widget.CustomViewpager;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.mall.adapter.MallShopDivisionFragmentAdapter;
import com.jhcms.waimaiV3.R;

@SuppressLint("ValidFragment")
public class MallShopDivisionFragment extends Fragment {
private GridViewForScrollView mgrildView;
    private MallShopDivisionFragmentAdapter mAdapter;
    private CustomViewpager mViewpager;
    private int mPostion;

    @SuppressLint("ValidFragment")
    public MallShopDivisionFragment(CustomViewpager mViewpager, int mPostion) {
        this.mViewpager = mViewpager;
        this.mPostion=mPostion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_mall_shop_division, container, false);
        mViewpager.setObjectForPosition(view,mPostion);
        inintview(view);
        inintData();
        return view;
    }

    private void inintData() {
        mAdapter=new MallShopDivisionFragmentAdapter(getContext());
        mgrildView.setAdapter(mAdapter);
        mgrildView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"item"+position,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void inintview(View view) {
        mgrildView= (GridViewForScrollView) view.findViewById(R.id.hotshop_gv);
    }

}
