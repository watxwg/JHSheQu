package com.jhcms.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.widget.RecycleViewBaseAdapter;
import com.jhcms.common.widget.SuperViewHolder;
import com.jhcms.mall.model.MallOrderItemMode;
import com.jhcms.mall.utils.MallOrderStatus;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WebViewActivity;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by admin on 2017/5/26.
 */
public class MallMyOrderFragmentAdapter extends RecycleViewBaseAdapter<MallOrderItemMode.ItemsBean> {

    private TextView tvShopname;
    private TextView tvShopstatu;
    private RecyclerView recyclerView;
    private TextView tvCommNum;
    private TextView tvShopPrice;
    private LinearLayout llBottom;
    private TextView tvTwo;
    private TextView tvOne;
    private TextView tvPay;
    private NumberFormat nf;
    private OnItemOrderListener orderListener;

    public MallMyOrderFragmentAdapter(Context context) {
        super(context);
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_mall_myorder_fragment_adapter;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        MallOrderItemMode.ItemsBean itemsBean = mDataList.get(position);
        tvShopname = holder.getView(R.id.tv_shopname);
        tvShopstatu = holder.getView(R.id.tv_shopstatu);
        recyclerView = holder.getView(R.id.recycler_view);
        tvShopPrice = holder.getView(R.id.tv_shop_pices);
        tvCommNum = holder.getView(R.id.tv_comm_num);
        llBottom = holder.getView(R.id.ll_bottom);
        tvTwo = holder.getView(R.id.tv_two);
        tvOne = holder.getView(R.id.tv_one);
        tvPay = holder.getView(R.id.tv_pay);
        tvShopname.setText(itemsBean.shop_title);
        tvShopstatu.setText(itemsBean.msg.showmsg);
        String freight = itemsBean.freight > 0 ? "含运费" + nf.format(itemsBean.freight) : "免运费";
        tvCommNum.setText(String.format(mContext.getString(R.string.共几件商品), itemsBean.product_number));
        tvShopPrice.setText("合计:" + nf.format(itemsBean.amount) + "(" + freight + ")");
        List<MallOrderItemMode.ItemsBean.ProductsBean> products = itemsBean.products;
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setFocusable(false);
        MallOrderProductAdapter productAdapter = new MallOrderProductAdapter(mContext, itemsBean.msg.show_url.detail);
        recyclerView.setAdapter(productAdapter);
        productAdapter.setData(products);


        if (null == itemsBean.msg.show_btn) {
            llBottom.setVisibility(View.GONE);
        } else {
            llBottom.setVisibility(View.VISIBLE);
            int orderStatus = MallOrderStatus.dealWith(itemsBean.msg);
            tvPay.setVisibility(View.GONE);
            Intent intent = new Intent();
            switch (orderStatus) {
                case MallOrderStatus.STATUS_WULIU_CONFIRM:
                    tvOne.setVisibility(View.VISIBLE);
                    tvOne.setText("确认订单");
                    tvOne.setOnClickListener(v -> {
                        if (null != orderListener) {
                            orderListener.confirmOrder(itemsBean);
                        }
                    });
                    tvTwo.setVisibility(View.VISIBLE);
                    tvTwo.setText("查看物流");
                    tvTwo.setOnClickListener(v -> {
                        intent.setClass(mContext, WebViewActivity.class);
                        intent.putExtra(WebViewActivity.URL, itemsBean.msg.show_url.wuliu);
                        mContext.startActivity(intent);
                    });
                    break;
                case MallOrderStatus.STATUS_COMMENT_WULIU:
                    tvOne.setVisibility(View.VISIBLE);
                    tvOne.setText("查看物流");
                    tvOne.setOnClickListener(v -> {
                        intent.setClass(mContext, WebViewActivity.class);
                        intent.putExtra(WebViewActivity.URL, itemsBean.msg.show_url.wuliu);
                        mContext.startActivity(intent);
                    });
                    tvTwo.setVisibility(View.VISIBLE);
                    tvTwo.setText("去评价");
                    tvTwo.setOnClickListener(v -> {
                        intent.setClass(mContext, WebViewActivity.class);
                        intent.putExtra(WebViewActivity.URL, itemsBean.msg.show_url.comment);
                        mContext.startActivity(intent);
                    });
                    break;
                case MallOrderStatus.STATUS_WULIU:
                    tvOne.setVisibility(View.VISIBLE);
                    tvOne.setText("查看物流");
                    tvOne.setOnClickListener(v -> {
                        intent.setClass(mContext, WebViewActivity.class);
                        intent.putExtra(WebViewActivity.URL, itemsBean.msg.show_url.wuliu);
                        mContext.startActivity(intent);
                    });
                    tvTwo.setVisibility(View.GONE);
                    break;
                case MallOrderStatus.STATUS_PAY_CANCEL:
                    tvOne.setVisibility(View.VISIBLE);
                    tvOne.setText("取消订单");
                    tvOne.setOnClickListener(v -> {
                        if (null != orderListener) {
                            orderListener.cancleOrder(itemsBean);
                        }
                    });
                    tvTwo.setVisibility(View.GONE);
                    tvPay.setVisibility(View.VISIBLE);
                    tvPay.setOnClickListener(v -> {
                        if (null != orderListener) {
                            orderListener.payOrder(itemsBean);
                        }


                    });
                    break;
            }
        }
    }


    public interface OnItemOrderListener {
        void cancleOrder(MallOrderItemMode.ItemsBean msgBean);

        void confirmOrder(MallOrderItemMode.ItemsBean msgBean);

        void payOrder(MallOrderItemMode.ItemsBean msgBean);
    }


    public void setOnItemOrderListener(OnItemOrderListener orderListener) {
        this.orderListener = orderListener;
    }

}
