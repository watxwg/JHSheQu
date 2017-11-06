package com.jhcms.shequ.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jhcms.shequ.activity.SearchMerchantGoodsActivity;
import com.jhcms.shequ.adapter.TypeLeftAdapter;
import com.jhcms.shequ.adapter.TypeRightAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/22.
 */
public class TypeFragment extends WaiMai_BaseFragment {


    @Bind(R.id.left_lv)
    ListView mLeftLv;
    @Bind(R.id.right_lv)
    ListView mRightLv;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;

    TypeLeftAdapter leftAdapter;
    TypeRightAdapter rightAdapter;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_type, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        setToolBar(llRoot);
        leftAdapter = new TypeLeftAdapter(getActivity());
        rightAdapter = new TypeRightAdapter(getActivity());

        mLeftLv.setAdapter(leftAdapter);
        mLeftLv.setOnItemClickListener((parent, view, position, id) -> {
            mRightLv.smoothScrollToPosition(0);
            leftAdapter.curPosition = position;
            leftAdapter.notifyDataSetChanged();
        });
        mRightLv.setAdapter(rightAdapter);
    }


    @OnClick(R.id.tv_search)
    public void onClick() {
        startActivity(new Intent(getActivity(), SearchMerchantGoodsActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
