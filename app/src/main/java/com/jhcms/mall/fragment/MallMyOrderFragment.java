package com.jhcms.mall.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.activity.MallMyOrderActivity;
import com.jhcms.mall.adapter.MallMyOrderFragmentAdapter;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.MallOrderItemMode;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.ToPayNewActivity;
import com.jhcms.waimaiV3.dialog.CallDialog;
import com.jhcms.waimaiV3.fragment.CustomerBaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class MallMyOrderFragment extends CustomerBaseFragment {
    @Bind(R.id.content_view)
    LRecyclerView contentView;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    private MallMyOrderFragmentAdapter mAdapter;
    private int type;
    private LinearLayoutManager linearLayoutManager;
    private LRecyclerViewAdapter listViewLAdapter;
    private int pageNum;
    private boolean isPrepared = false;
    private MallMyOrderActivity activity;

    public MallMyOrderFragment(int type) {
        this.type = type;
    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mall_my_order, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public void initData() {
        activity = ((MallMyOrderActivity) getActivity());
        /*ListView 数据*/
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new MallMyOrderFragmentAdapter(getActivity());
        listViewLAdapter = new LRecyclerViewAdapter(mAdapter);
        contentView.setLayoutManager(linearLayoutManager);
        contentView.setAdapter(listViewLAdapter);
        contentView.addItemDecoration(Utils.setDivider(getActivity(), R.dimen.dp_5, R.color.background));

        mAdapter.setOnItemOrderListener(new MallMyOrderFragmentAdapter.OnItemOrderListener() {
            @Override
            public void cancleOrder(MallOrderItemMode.ItemsBean msgBean) {
                 /*订单取消(client/v3/mall/order/cancel*/
                new CallDialog(getActivity())
                        .setMessage("是否取消此订单")
                        .setTipTitle("温馨提示")
                        .setPositiveButton("是", v -> dealWithOrder(Api.MALL_ORDER_CANCEL, msgBean.order_id))
                        .setNegativeButton("否", null)
                        .show();
            }

            @Override
            public void confirmOrder(MallOrderItemMode.ItemsBean msgBean) {
                /*确认收货(client/v3/mall/order/complete)*/
                new CallDialog(getActivity())
                        .setMessage("是否要确认收货")
                        .setTipTitle("温馨提示")
                        .setPositiveButton("是", v -> dealWithOrder(Api.MALL_ORDER_COMPLETE, msgBean.order_id))
                        .setNegativeButton("否", null)
                        .show();
            }

            @Override
            public void payOrder(MallOrderItemMode.ItemsBean msgBean) {
                Intent intent = new Intent(getActivity(), ToPayNewActivity.class);
                intent.putExtra(ToPayNewActivity.ORDER_ID, msgBean.order_id);
                intent.putExtra(ToPayNewActivity.MONEY, msgBean.amount);
                intent.putExtra(ToPayNewActivity.FROM, ToPayNewActivity.TO_MALL);
                startActivity(intent);
            }
        });
        /*处理刷新*/
        initRefresh();
        isPrepared = true;
    }

    /**
     * 处理取消订单和确认收货
     *
     * @param api
     * @param order_id
     */
    private void dealWithOrder(String api, String order_id) {
        JSONObject object = new JSONObject();
        try {
            object.put("order_id", order_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(getActivity(), api
                , object.toString(), true, new OnRequestSuccess<BaseResponse<MallOrderItemMode>>() {
                    @Override
                    public void onSuccess(String url, BaseResponse<MallOrderItemMode> data) {
                        if (data.error == 0) {
                            contentView.refresh();
                        }
                        ToastUtil.show(data.message);
                    }

                    @Override
                    public void onBeforeAnimate() {

                    }

                    @Override
                    public void onErrorAnimate() {
                    }
                });

    }

    private void initRefresh() {
        //设置头部加载颜色
        contentView.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        contentView.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        contentView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        contentView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        contentView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式
        contentView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                request(pageNum);
            }
        });
        /**
         * 加载更多
         * //禁用自动加载更多功能
         */
        contentView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                request(pageNum);
            }
        });

    }

    /**
     * @param pageNum 分页码
     *                type
     *                类型 0 全部 1待付款 2待发货 3待收货 4待评价 5退款/售后
     */
    private void request(int pageNum) {
        JSONObject object = new JSONObject();
        try {
            object.put("page", pageNum);
            object.put("type", type);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(getActivity(), Api.MALL_ORDER_ITEMS
                , object.toString(), true, new OnRequestSuccess<BaseResponse<MallOrderItemMode>>() {
                    @Override
                    public void onSuccess(String url, BaseResponse<MallOrderItemMode> data) {
                        if (data.error == 0) {
                            statusview.showContent();
                            MallOrderItemMode.CountBean count = data.data.count;
                            activity.setOrderNum(count);
                            List<MallOrderItemMode.ItemsBean> items = data.data.items;
                            if (pageNum == 1) {
                                if (items.size() == 0) {
                                    statusview.showEmpty();
                                } else {
                                    mAdapter.setDataList(items);
                                }
                            } else {
                                if (items.size() == 0) {
                                    contentView.setNoMore(true);
                                } else {
                                    mAdapter.addAll(items);
                                }
                            }
                            contentView.refreshComplete(items.size());
                        } else {
                            statusview.showError();
                            ToastUtil.show(data.message);
                        }
                    }

                    @Override
                    public void onBeforeAnimate() {

                    }

                    @Override
                    public void onErrorAnimate() {
                        statusview.showError();
                    }
                });

    }

    @Override
    protected void lazyLoad() {
        if (isVisible && isPrepared) {
            if (contentView != null) {
                contentView.refresh();
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
