package com.jhcms.tuangou.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.jhcms.common.dialog.TipDialog;
import com.jhcms.common.model.Group_BuyOrdersFragment_Model;
import com.jhcms.common.model.Response_Group_BuyOrdersFragment;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.tuangou.activity.LookTuanOrderMerchantEvaluationActivity;
import com.jhcms.tuangou.activity.TuanBulkVolumeActivity;
import com.jhcms.tuangou.activity.TuanOrderEvaluateActivity;
import com.jhcms.tuangou.activity.TuanToPayActivity;
import com.jhcms.tuangou.adapter.TuanOrderAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/7/14.
 * TODO 团购订单Fragment
 */

public class Group_BuyOrdersFragment extends WaiMai_BaseFragment implements OnRequestSuccessCallback {

    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    @Bind(R.id.content_view)
    LRecyclerView orderReclcleView;
    private TuanOrderAdapter mAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int page = 1;
    private Response_Group_BuyOrdersFragment mDataModel;
    private ArrayList<Group_BuyOrdersFragment_Model> mDataList = new ArrayList<>();

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_group_buyorders, null);
        ButterKnife.bind(Group_BuyOrdersFragment.this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        inintlistvew();
    }


    public void requestData() {
        if (TextUtils.isEmpty(Api.TOKEN)) {
            orderReclcleView.refreshComplete(0);
            mLRecyclerViewAdapter.notifyDataSetChanged();
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page", page);

            HttpUtils.postUrl(getContext(), Api.WAIMAI_TUAN_ORDER, jsonObject.toString(), false, new OnRequestSuccessCallback() {
                @Override
                public void onSuccess(String url, String Json) {
                    DismissDialog();
                    try {
                        Gson gson = new Gson();
                        mDataModel = gson.fromJson(Json, Response_Group_BuyOrdersFragment.class);
                        if (mDataModel.error.equals("0")) {
                            BindViewData(mDataModel);
                        } else {
                            orderReclcleView.refreshComplete(0);
                            mLRecyclerViewAdapter.notifyDataSetChanged();
                            if (mDataModel.error.equals("101")) {
                                Api.TOKEN = null;
                                Utils.GoLogin(getActivity());
                            }
                            ToastUtil.show(mDataModel.message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onBeforeAnimate() {
                    ShowLoadingDialog();
                }

                @Override
                public void onErrorAnimate() {
                    orderReclcleView.refreshComplete(0);
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    DismissDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        if (orderReclcleView != null) {
            orderReclcleView.refresh();
        } else {
            page = 1;
            requestData();
        }
    }

    private void inintlistvew() {
        mAdapter = new TuanOrderAdapter(getContext(), mDataList);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        orderReclcleView.setAdapter(mLRecyclerViewAdapter);
        orderReclcleView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        orderReclcleView.addItemDecoration(Utils.setDivider(getActivity(), R.dimen.dp_10, R.color.background));
        //设置头部加载颜色
        orderReclcleView.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        orderReclcleView.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        orderReclcleView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        orderReclcleView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        orderReclcleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);


        /**
         * 下拉刷新
         */
        orderReclcleView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                requestData();
            }
        });
        /**
         * 加载更多
         */
        orderReclcleView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                requestData();
            }
        });


        mAdapter.setmOderstatuClickListener(new TuanOrderAdapter.OderstatuClickListener() {
            @Override
            public void OderstatuClick(String type, int postion) {
                Intent i = new Intent();
                switch (type) {
                    case "ToPay":
                        TopayOrder(postion);
                        break;
                    case "CancelOrder":
                        cancelorder(postion);
                        break;
                    case "LookCoupon"://TODO    ToastUtil.show("查看券码！");
                        Intent intent = new Intent(getContext(), TuanBulkVolumeActivity.class);
                        intent.putExtra("ticket_number", mDataList.get(postion).getSpend_number());
                        intent.putExtra("ShopCount", mDataList.get(postion).getTuan_number());
                        intent.putExtra("Ticket_status", mDataList.get(postion).getSpend_status());
                        startActivity(intent);
                        break;
                    case "ToComment":
                        i.setClass(getContext(), TuanOrderEvaluateActivity.class);
                        i.putExtra("order_id", mDataList.get(postion).getOrder_id());
                        i.putExtra("type", "tuan");
                        i.putExtra("shop_logo", mDataList.get(postion).getShop_logo());
                        i.putExtra("shop_title", mDataList.get(postion).getShop_title());
                        startActivity(i);
                        break;
                    case "LookComment":
                        i.setClass(getContext(), LookTuanOrderMerchantEvaluationActivity.class);
                        i.putExtra("comment_id", mDataList.get(postion).getComment_id());
                        startActivity(i);
                        break;
                }
            }
        });
        statusview.setOnRetryClickListener(v -> {
            orderReclcleView.refresh();
        });
    }

    private void TopayOrder(int postion) {
        Intent intent = new Intent(getContext(), TuanToPayActivity.class);
        intent.putExtra("OrderId", mDataList.get(postion).getOrder_id());
        intent.putExtra("Money", mDataList.get(postion).getTotal_price());
        intent.putExtra("FromWhere", "Group_BuyOrdersFragment");
        startActivity(intent);
    }

    private void cancelorder(final int postion) {
        final TipDialog canceldialog = new TipDialog(getContext(), new TipDialog.setTipDialogCilck() {
            @Override
            public void setPositiveListener() {
                try {
                    final JSONObject js = new JSONObject();
                    js.put("order_id", mDataList.get(postion).getOrder_id());
                    HttpUtils.postUrl(getContext(), Api.WAIMAI_TUAN_ORDER_CANCEL, js.toString(), false, new OnRequestSuccessCallback() {
                        @Override
                        public void onSuccess(String url, String Json) {
                            DismissDialog();
                            Gson gson = new Gson();
                            try {
                                SharedResponse mModel = gson.fromJson(Json, SharedResponse.class);
                                if (mModel.error.equals("0")) {
                                    ToastUtil.show("取消成功");
                                    requestData();
                                } else {
                                    ToastUtil.show(mModel.message);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onBeforeAnimate() {
                            ShowLoadingDialog();

                        }

                        @Override
                        public void onErrorAnimate() {
                            DismissDialog();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void setNegativeListener() {


            }
        });
        canceldialog.setMessage("确认取消订单吗？");
        canceldialog.setSettextcolor(new TipDialog.TipPositiveAndtipNegativecolor() {
            @Override
            public void settingAllcolor(TextView tipPositive, TextView tipNegative) {
                tipPositive.setText("确认");
                tipNegative.setText("取消");
            }
        });
        canceldialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void BindViewData(Response_Group_BuyOrdersFragment mDataModel) {
        statusview.showContent();
        if (page == 1) {
            mDataList.clear();
            if (mDataModel.getData().getItems().size() == 0) {
                statusview.showEmpty();
            }
        } else {
            if (mDataModel.getData().getItems().size() == 0) {
                orderReclcleView.setNoMore(true);
            }
        }
        if (mDataModel.getData().getItems() != null && mDataModel.getData().getItems().size() > 0) {
            mDataList.addAll(mDataModel.getData().getItems());
        }
        mAdapter.notifyDataSetChanged();
        orderReclcleView.refreshComplete(mDataList.size());
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onSuccess(String url, String Json) {
        DismissDialog();
    }

    @Override
    public void onBeforeAnimate() {
        ShowLoadingDialog();
    }

    @Override
    public void onErrorAnimate() {
        DismissDialog();
    }
}
