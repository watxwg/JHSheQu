package com.jhcms.waimaiV3.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.AvailableAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Wyj on 2017/4/18.
 */
public class WaiMai_AvailableFragment extends CustomerBaseFragment {

    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    private boolean isPrepared = false;
    private AvailableAdapter adapter;
    private int type;





    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_waimai_available, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void initData() {
        type = getArguments().getInt("type", 0);
        adapter = new AvailableAdapter(getActivity(), type);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (isVisible && isPrepared) {
            Toast.makeText(getActivity(), "看的见" + type, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
