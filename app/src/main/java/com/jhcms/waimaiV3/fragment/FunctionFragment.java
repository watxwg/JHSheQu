package com.jhcms.waimaiV3.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.jhcms.common.model.IndexCate;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.HomeTitleItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.jhcms.common.utils.Utils.isFastDoubleClick;

/**
 * Created by admin
 * on 2017/8/19.
 * TODO
 */

public class FunctionFragment extends WaiMai_BaseFragment {
    private final String type;
    HomeTitleItemAdapter functionAdapter;
    List<IndexCate> function = new ArrayList<IndexCate>();
    @Bind(R.id.function_gridview)
    GridView mGridView;


    @SuppressLint("ValidFragment")
    public FunctionFragment(List<IndexCate> function, String type) {
        this.function = function;
        this.type = type;
    }


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_function, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mGridView.setFocusable(false);
        functionAdapter = new HomeTitleItemAdapter(getActivity());
        functionAdapter.setData(function);
        mGridView.setAdapter(functionAdapter);
        mGridView.setOnItemClickListener((parent, view1, position, id) -> {
            if (!isFastDoubleClick()) {
                String link = function.get(position).link;
                Utils.dealWithHomeLink(getActivity(), link, null);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
