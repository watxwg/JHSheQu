package com.jhcms.waimaiV3.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.jhcms.common.dialog.TipDialog;
import com.jhcms.common.model.RefreshEvent;
import com.jhcms.common.model.Response_OrderFragment;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.model.ShopOrderModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.LookMerchantEvaluationActivity;
import com.jhcms.waimaiV3.activity.MerchantEvaluationActivity;
import com.jhcms.waimaiV3.activity.OrderDetailsActivity;
import com.jhcms.waimaiV3.activity.OrderDetailsGMSActivity;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.activity.ToPayActivity;
import com.jhcms.waimaiV3.adapter.WaiMaiOrderAdapter;
import com.jhcms.waimaiV3.dialog.OrderCancelDialog;
import com.jhcms.waimaiV3.model.Goods;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Wyj on 2017/3/22.
 */
public class WaiMai_OrderFragment extends WaiMai_BaseFragment implements OnRequestSuccessCallback {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.order_reclcleView)
    LRecyclerView orderReclcleView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.multiplestatusview)
    MultipleStatusView multiplestatusview;
    private WaiMaiOrderAdapter orderAdapter;
    /*是否正在刷新*/
    boolean isFresh = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int page = 1;
    private ArrayList<ShopOrderModel> mOrederDataList = new ArrayList<>();
    private int mPostion;
    private final String topaymodel = "Topay";
    private boolean isVisible = true;

    @Override
    protected View initView() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.fragment_waimai_order, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    protected void initData() {
        setToolBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> getActivity().finish());
        tvTitle.setText("订单");
        orderAdapter = new WaiMaiOrderAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(orderAdapter);
        orderReclcleView.setAdapter(mLRecyclerViewAdapter);

        orderReclcleView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        DividerDecoration divider = new DividerDecoration.Builder(getActivity())
                .setHeight(R.dimen.dp_10)
                .setColorResource(R.color.background)
                .build();
        orderReclcleView.addItemDecoration(divider);


        //设置头部加载颜色
        orderReclcleView.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        orderReclcleView.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        orderReclcleView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        orderReclcleView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        orderReclcleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式

        orderAdapter.setOnClickListener(new WaiMaiOrderAdapter.OnClickListener() {
            @Override
            public void OnClick(String goPay, int postion) {
                mPostion = postion;
                switch (goPay) {
                    //TODO 去支付
                    case "topay":
                        Topay(postion);
                        break;
                    //TODO 再来一单
                    case "again":
                        ShopCreatOrder(postion);
                        break;
                    //TODO 催单
                    case "reminder"://TODO 催单
                        orderRemind(postion);
                        break;
                    //TODO 确认送达
                    case "confirm"://TODO 确认送达
                        orderConfirm(postion);
                        break;
                    //TODO 申请客服介入
                    case "kefu":
                        callKefu(postion);
                        break;
                    //TODO 去评价
                    case "toevaluate":
                        Intent i = new Intent(getActivity(), MerchantEvaluationActivity.class);
                        i.putExtra("model", mOrederDataList.get(postion));
                        startActivity(i);
                        break;
                    //TODO 查看评价
                    case "lookevaluate":
                        Intent i2 = new Intent(getContext(), LookMerchantEvaluationActivity.class);
                        i2.putExtra("comment_id", mOrederDataList.get(postion).comment_id);
                        i2.putExtra("peitype", mOrederDataList.get(postion).getPei_type());
                        startActivity(i2);

                        break;
                    case "CancleOrder"://TODO   ToastUtil.show("取消订单");
                        CancelOrder(postion);
                        break;
                    case "refundorder":
                        showPopuwindowrefund(postion);
                        break;
                    case "CancleOrder2":
                        CancelOrder2(postion);
                        break;

                    case "all":
                        Intent intent = new Intent();
                        intent.putExtra("ORDER_ID",mOrederDataList.get(postion).getOrder_id());
                        if (MyApplication.MAP.equals(Api.GAODE)) {
                            intent.setClass(getContext(), OrderDetailsActivity.class);
                        } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                            intent.setClass(getContext(), OrderDetailsGMSActivity.class);
                        }
                        intent.putExtra(OrderDetailsActivity.ORDER_ID, mOrederDataList.get(postion).order_id);
                        getContext().startActivity(intent);
                        break;
                    case "goShop":
                        Intent intent4 = new Intent(getContext(), WaiMaiShopActivity.class);
                        intent4.putExtra("TYPE", mOrederDataList.get(postion).getShop_id());
                        startActivity(intent4);
                        break;

                }
            }
        });
        /**
         * 下拉刷新
         * //禁用下拉刷新功能
         mRecyclerView.setPullRefreshEnabled(false);
         */
        orderReclcleView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                page = 1;
                requestData(page);


            }
        });
        /**
         * 加载更多
         * //禁用自动加载更多功能
         mRecyclerView.setLoadMoreEnabled(false);
         */
        orderReclcleView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                requestData(page);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        orderReclcleView.refresh();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
        if (isVisible) {
            orderReclcleView.refresh();
        }
    }


    private void CancelOrder2(int postion) {
        try {
            JSONObject js = new JSONObject();
            js.put("order_id", mOrederDataList.get(postion).order_id);
            String str = js.toString();
            HttpUtils.postUrl(getActivity(), Api.WAIMAI_ORDER_CHARGEBACK, str, true, new OnRequestSuccessCallback() {
                @Override
                public void onSuccess(String url, String Json) {
                    if (url.equals(Api.WAIMAI_ORDER_CHARGEBACK)) {
                        requestData(1);
                    }
                }

                @Override
                public void onBeforeAnimate() {

                }

                @Override
                public void onErrorAnimate() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Topay(int postion) {
        Intent intent = new Intent(getContext(), ToPayActivity.class);
        intent.putExtra(topaymodel, mOrederDataList.get(postion));
        intent.putExtra("FromWhere", "WaiMai_OrderFragment");
        intent.putExtra("order_id", mOrederDataList.get(postion).getOrder_id());
        startActivity(intent);
    }

    /**
     * 客服电话
     *
     * @param postion
     */
    private void callKefu(final int postion) {
        TipDialog kefudialog = new TipDialog(getContext(), new TipDialog.setTipDialogCilck() {
            @Override
            public void setPositiveListener() {
                try {
                    JSONObject js = new JSONObject();
                    js.put("order_id", mOrederDataList.get(postion).order_id);
                    String str = js.toString();
                    HttpUtils.postUrl(getActivity(), Api.WAIMAI_ORDER_KEFU, str, true, new OnRequestSuccessCallback() {
                        @Override
                        public void onSuccess(String url, String Json) {
                            if (url.equals(Api.WAIMAI_ORDER_KEFU)) {
                                Gson gson = new Gson();
                                SharedResponse mdata = gson.fromJson(Json, SharedResponse.class);
                                if (mdata.error.equals("0")) {
                                    ToastUtil.show("申请成功耐心等待！");
                                    page = 1;
                                    requestData(page);
                                } else {
                                    ToastUtil.show(mdata.message);
                                }
                            }
                        }

                        @Override
                        public void onBeforeAnimate() {

                        }

                        @Override
                        public void onErrorAnimate() {

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
        kefudialog.setMessage("确认申请客服介入吗？");
        kefudialog.setSettextcolor(new TipDialog.TipPositiveAndtipNegativecolor() {
            @Override
            public void settingAllcolor(TextView tipPositive, TextView tipNegative) {
                tipPositive.setText("确认");
                tipNegative.setText("取消");
            }
        });
        kefudialog.show();


    }

    /**
     * 申请退款
     */

    private void showPopuwindowrefund(final int postion) {
        final OrderCancelDialog diaglog = new OrderCancelDialog(getContext());
        diaglog.setOnClickListener(new OrderCancelDialog.OnClickListener() {
            @Override
            public void clickListener(String content) {
                if (content == null) {
                    ToastUtil.show("理由不能为空！");
                    return;
                }
                try {
                    JSONObject js = new JSONObject();
                    js.put("order_id", mOrederDataList.get(postion).order_id);
                    js.put("reason", content);
                    String str = js.toString();
                    HttpUtils.postUrl(getActivity(), Api.WAIMAI_ORDER_PAYBACK, str, true, new OnRequestSuccessCallback() {
                        @Override
                        public void onSuccess(String url, String Json) {
                            Gson gson = new Gson();
                            SharedResponse mdata = gson.fromJson(Json, SharedResponse.class);
                            if (mdata.error.equals("0")) {
                                ToastUtil.show(mdata.message);
                                page = 1;
                                requestData(1);
                            } else {
                                ToastUtil.show(mdata.message);
                            }
                        }

                        @Override
                        public void onBeforeAnimate() {

                        }

                        @Override
                        public void onErrorAnimate() {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        diaglog.show();

    }

    /*确认送达*/
    private void orderConfirm(final int postion) {
        TipDialog Confirmdialog = new TipDialog(getContext(), new TipDialog.setTipDialogCilck() {
            @Override
            public void setPositiveListener() {
                try {
                    JSONObject js = new JSONObject();
                    js.put("order_id", mOrederDataList.get(postion).order_id);
                    String str = js.toString();
                    HttpUtils.postUrl(getActivity(), Api.WAIMAI_ORDER_CONFRIM, str, true, new OnRequestSuccessCallback() {
                        @Override
                        public void onSuccess(String url, String Json) {
                            if (url.equals(Api.WAIMAI_ORDER_CONFRIM)) {
                                Gson gson = new Gson();
                                SharedResponse mshare = gson.fromJson(Json, SharedResponse.class);
                                if (mshare.error.equals("0")) {
                                    page = 1;
                                    requestData(1);
                                } else {
                                    ToastUtil.show(mshare.message);
                                }

                            }
                        }

                        @Override
                        public void onBeforeAnimate() {

                        }

                        @Override
                        public void onErrorAnimate() {

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
        Confirmdialog.setMessage("确认收货吗？");
        Confirmdialog.setSettextcolor(new TipDialog.TipPositiveAndtipNegativecolor() {
            @Override
            public void settingAllcolor(TextView tipPositive, TextView tipNegative) {
                tipPositive.setText("确认");
                tipNegative.setText("取消");
            }
        });
        Confirmdialog.show();

    }

    /*推单*/
    private void orderRemind(int postion) {
        try {
            JSONObject js = new JSONObject();
            js.put("order_id", mOrederDataList.get(postion).order_id);
            String str = js.toString();
            HttpUtils.postUrl(getActivity(), Api.WAIMAI_ORDER_REMIND, str, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*取消订单*/
    private void CancelOrder(final int postion) {
        TipDialog canceldialog = new TipDialog(getContext(), new TipDialog.setTipDialogCilck() {
            @Override
            public void setPositiveListener() {
                try {
                    JSONObject js = new JSONObject();
                    js.put("order_id", mOrederDataList.get(postion).order_id);
                    String str = js.toString();
                    HttpUtils.postUrl(getActivity(), Api.WAIMAI_ORDER_CHARGEBACK, str, true, new OnRequestSuccessCallback() {
                        @Override
                        public void onSuccess(String url, String Json) {
                            if (url.equals(Api.WAIMAI_ORDER_CHARGEBACK)) {
                                Gson gson = new Gson();
                                SharedResponse mshare = gson.fromJson(Json, SharedResponse.class);
                                if (mshare.error.equals("0")) {
                                    page = 1;
                                    requestData(1);
                                } else {
                                    ToastUtil.show(mshare.message);
                                }

                            }
                        }

                        @Override
                        public void onBeforeAnimate() {

                        }

                        @Override
                        public void onErrorAnimate() {

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

    /**
     * 再来一单
     * TODO 跳到商家详情界面
     */
    private void ShopCreatOrder(int postion) {
        /**
         * "Goods{" +
         "productsEntity=" + productsEntity +
         ", product_id='" + product_id + '\'' +
         ", productId='" + productId + '\'' +
         ", shop_id='" + shop_id + '\'' +
         ", spec_id='" + spec_id + '\'' +
         ", price='" + price + '\'' +
         ", typeId=" + typeId +
         ", title='" + title + '\'' +
         ", name='" + name + '\'' +
         ", count=" + count +
         ", sale_sku=" + sale_sku +
         ", is_spec='" + is_spec + '\'' +
         ", good='" + good + '\'' +
         ", bad='" + bad + '\'' +
         ", logo='" + logo + '\'' +
         ", specSelect=" + specSelect +
         '}';
         */

        ArrayList<Goods> goodses = new ArrayList<>();
        for (int i = 0; i < mOrederDataList.get(postion).products.size(); i++) {
            Goods goods = new Goods();
            goods.productId = mOrederDataList.get(postion).products.get(i).getProduct_id();
            goods.shop_id = mOrederDataList.get(postion).shop_id;
            goods.spec_id = mOrederDataList.get(postion).products.get(i).getSpec_id();
            goods.price = mOrederDataList.get(postion).products.get(i).getProduct_price();
            goods.name = mOrederDataList.get(postion).products.get(i).getProduct_name();
            goods.sale_sku = mOrederDataList.get(postion).products.get(i).getSale_sku();
            goods.count = Integer.parseInt(mOrederDataList.get(postion).products.get(i).getProduct_number());
            goods.pagePrice = mOrederDataList.get(postion).products.get(i).getPackage_price();
            if (mOrederDataList.get(postion).products.get(i).getSpec_id().equals("0")) {
                goods.setIs_spec("0");
                goods.product_id = mOrederDataList.get(postion).products.get(i).getProduct_id();
                goods.setSpec_id(mOrederDataList.get(postion).products.get(i).getSpec_id());
            } else {
                goods.setIs_spec("1");
                goods.product_id = mOrederDataList.get(postion).products.get(i).getProduct_id() + mOrederDataList.get(postion).products.get(i).getSpec_id();
                goods.setSpec_id(mOrederDataList.get(postion).products.get(i).getSpec_id());
            }

            goodses.add(goods);
        }

        Gson gson = new Gson();
        String json = gson.toJson(goodses);
        Intent intent = new Intent(getContext(), WaiMaiShopActivity.class);
        intent.putExtra(WaiMaiShopActivity.PRODUCT_INFO, json);
        intent.putExtra(WaiMaiShopActivity.SHOP_ID, mOrederDataList.get(postion).getShop_id());
        startActivity(intent);


    }

    private void requestData(int page) {
        try {
            JSONObject js = new JSONObject();
            js.put("page", page);
            String str = js.toString();
            isFresh = true;
            HttpUtils.postUrl(getActivity(), Api.WAIMAI_ORDER, str, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSuccess(String url, String Json) {
        Gson gson = new Gson();
        switch (url) {
            case Api.WAIMAI_ORDER:
                Response_OrderFragment mdata = gson.fromJson(Json, Response_OrderFragment.class);
                if (mdata.error.equals("0")) {
                    BindViewByData(mdata.data.items);
                } else {
                    if (mdata.error.equals("101")) {
                        Api.TOKEN = null;
                        Utils.GoLogin(getActivity());
                    }
                    ToastUtil.show(mdata.message);
                }
                break;
            case Api.WAIMAI_ORDER_CHARGEBACK:
                SharedResponse mshare = gson.fromJson(Json, SharedResponse.class);
                if (mshare.error.equals("0")) {
                    page = 1;
                    requestData(1);
                } else {
                    ToastUtil.show(mshare.message);
                }
                break;
            case Api.WAIMAI_ORDER_REMIND:
                SharedResponse mshare1 = gson.fromJson(Json, SharedResponse.class);
                if (mshare1.error.equals("0")) {
                    ToastUtil.show("催单成功");
                    page = 1;
                    requestData(1);
                } else {
                    ToastUtil.show(mshare1.message);
                }
                break;
            case Api.WAIMAI_ORDER_CONFRIM:
                SharedResponse msharedata = gson.fromJson(Json, SharedResponse.class);
                if (msharedata.error.equals("0")) {
                    ToastUtil.show(msharedata.message);
                    page = 1;
                    requestData(1);
                } else {
                    ToastUtil.show(msharedata.message);
                }

                break;
        }
    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }

    /**
     * 绑定数据
     *
     * @param mOrederList
     */
    private void BindViewByData(ArrayList<ShopOrderModel> mOrederList) {
        multiplestatusview.showContent();
        if (page == 1) {
            mOrederDataList.clear();
            mOrederDataList.addAll(mOrederList);
            if (mOrederDataList.size() <= 0) {
                multiplestatusview.showEmpty();
            }
        } else if (page > 1) {
            if (mOrederList.size() > 0) {
                mOrederDataList.addAll(mOrederList);
            }
        }
        orderAdapter.setDataList(mOrederDataList);
        orderAdapter.notifyDataSetChanged();
        mLRecyclerViewAdapter.notifyDataSetChanged();
        orderReclcleView.refreshComplete(mOrederDataList.size());
        if (page != 1 && mOrederDataList.size() % 10 != 0) {
            orderReclcleView.setNoMore(true);
        }
        isFresh = false;
    }



     /*微信支付成功*/

    @Subscribe
    public void onMessageEvent(RefreshEvent event) {
        if (event.getmMsg().equals("weixin_pay_success")) {
            Intent intent = new Intent();
            if (MyApplication.MAP.equals(Api.GAODE)) {
                intent.setClass(getContext(), OrderDetailsActivity.class);
            } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                intent.setClass(getContext(), OrderDetailsGMSActivity.class);
            }
            intent.putExtra("order_id", mOrederDataList.get(mPostion).getOrder_id());
            startActivity(intent);
        }
    }

    public void scrollToTop() {
        if (!isFresh) {
            orderReclcleView.smoothScrollToPosition(0);
            orderReclcleView.refresh();
        }
    }
}
