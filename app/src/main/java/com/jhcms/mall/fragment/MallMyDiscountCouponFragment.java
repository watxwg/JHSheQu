package com.jhcms.mall.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jhcms.common.widget.ListViewForListView;
import com.jhcms.mall.adapter.MallMyDiscountCouponAdapter;
import com.jhcms.waimaiV3.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

/**
 * 我的优惠fragment
 */
@SuppressLint("ValidFragment")
public class MallMyDiscountCouponFragment extends Fragment {
    private  View view;
    private SpringView mRefreshLayout;
    private ListViewForListView mListview;
    private  int Postion;//0 代表可用 1代表不可用
    private MallMyDiscountCouponAdapter mAdapter;
    public MallMyDiscountCouponFragment(int Postion){
        super();
        this.Postion=Postion;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_mall_my_discount_coupon, container, false);
        inintView(view);
        inintData();
        return view;
    }

    private void inintData() {
        initRefreshlayout();
        inintmListview();
    }

    private void inintmListview() {
        mAdapter=new MallMyDiscountCouponAdapter(getContext(),Postion);
        mListview.setAdapter(mAdapter);
    }

    private void initRefreshlayout() {
        mRefreshLayout.setGive(SpringView.Give.BOTH);
        mRefreshLayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"onRefresh",Toast.LENGTH_LONG).show();
                        mRefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"onRefresh",Toast.LENGTH_LONG).show();
                        mRefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);

            }
        });
        mRefreshLayout.setHeader(new DefaultHeader(getContext()));
        mRefreshLayout.setFooter(new DefaultFooter(getContext()));
        mRefreshLayout.setType(SpringView.Type.FOLLOW);
    }

    private void inintView(View view) {
        mRefreshLayout= (SpringView) view.findViewById(R.id.refreshlayout);
        mListview= (ListViewForListView) view.findViewById(R.id.listview);

    }
}
