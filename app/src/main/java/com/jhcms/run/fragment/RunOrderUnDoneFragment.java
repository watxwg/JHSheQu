package com.jhcms.run.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.run.adapter.RunOrderAdapter;
import com.jhcms.run.mode.Data_Run_Order_UnDone;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.fragment.CustomerBaseFragment;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * on 2017/10/9.14:57
 * TODO 跑腿未完成订单
 */

public class RunOrderUnDoneFragment extends CustomerBaseFragment {
    @Bind(R.id.content_view)
    LRecyclerView list;
    @Bind(R.id.main_multiplestatusview)
    MultipleStatusView statusview;
    private boolean isPrepared = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private RunOrderAdapter orderAdapter;
    private int page = 1;

    @Override
    protected void initData() {
        super.initData();

        orderAdapter = new RunOrderAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(orderAdapter);
        list.setAdapter(mLRecyclerViewAdapter);

        list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        DividerDecoration divider = new DividerDecoration.Builder(getActivity())
                .setHeight(R.dimen.dp_10)
                .setColorResource(R.color.background)
                .build();
        list.addItemDecoration(divider);


        //设置头部加载颜色
        list.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        list.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        list.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式
        list.setOnRefreshListener(() -> {
            /*
            status	是	int	1:全部 2:进行中 3：已完成
            page	是	int	页码*/
            page = 1;
            requestOrder(page);

        });
        list.setOnLoadMoreListener(() -> {
            page++;
            requestOrder(page);
        });
        isPrepared = true;
    }

    private void requestOrder(int page) {
        JSONObject object = new JSONObject();
        try {
            object.put("status", 2);
            object.put("page", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(getActivity(), Api.PAOTUI_ORDER_ITEMS, object.toString(), true, new OnRequestSuccess<BaseResponse<Data_Run_Order_UnDone>>() {
            @Override
            public void onSuccess(String url, BaseResponse<Data_Run_Order_UnDone> data) {
                super.onSuccess(url, data);
                if (data.error == 0) {
                    Data_Run_Order_UnDone orderData = data.getData();
                    List<Data_Run_Order_UnDone.ItemsBean> orderDataItems = orderData.getItems();
                    statusview.showContent();
                    if (page == 1) {
                        if (orderDataItems != null && orderDataItems.size() > 0) {
                            orderAdapter.setData(orderDataItems);
                        } else {
                            statusview.showEmpty();
                        }
                    } else {
                        if (orderDataItems != null && orderDataItems.size() > 0) {
                            orderAdapter.addAll(orderDataItems);
                        } else {
                            list.setNoMore(true);
                        }
                    }
                    list.refreshComplete(orderDataItems.size());
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    statusview.showError();
                    ToastUtil.show(data.message);
                }

            }
        });
    }

    @Override
    protected void lazyLoad() {
        if (isVisible && isPrepared) {
            if (list != null) {
                list.refresh();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_run_order_all, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
