package com.jhcms.run.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.jhcms.run.activity.RunHelpBuyActivity;
import com.jhcms.run.activity.RunHelpDeloveryActivity;
import com.jhcms.run.mode.Data_Run_Order_UnDone;
import com.jhcms.run.utils.RunOrderStatus;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by wangyujie
 * on 2017/10/9.15:13
 * TODO
 */

public class RunOrderAdapter extends RecyclerView.Adapter<RunOrderAdapter.MyViewHolder> {
    private Context mContext;
    private List<Data_Run_Order_UnDone.ItemsBean> data = new ArrayList<>();
    private OnItemStatus itemStatus;

    public RunOrderAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_run_order_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Data_Run_Order_UnDone.ItemsBean itemsBean = data.get(position);
        /*跑腿类型*/
        if ("mai".equals(itemsBean.getFrom())) {
            holder.stvType.setText(R.string.帮我买);
        } else if ("song".equals(itemsBean.getFrom())) {
            holder.stvType.setText(R.string.帮我送);
        }
        /*订单状态*/
        holder.tvOrderMsg.setText(itemsBean.getMsg());

        /*购买信息*/
        if (itemsBean.getProduct() != null && itemsBean.getProduct().size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < itemsBean.getProduct().size(); i++) {
                sb.append(itemsBean.getProduct().get(i) + " ");
            }
            holder.tvOrderInfo.setText(sb.toString());
        }
        /*购买地址*/
        holder.tvGouAddr.setText(itemsBean.getM_addr());
        /*发货地址*/
        holder.tvFaAddr.setText(itemsBean.getS_addr());
        /*跑腿费*/
        holder.tvPeiAmount.setText("￥" + itemsBean.getPei_amount());
        holder.llOrder.setOnClickListener(v -> {
            if (itemStatus != null) {
                itemStatus.detail(position);
            }
        });
        /*按钮状态*/
        int status = RunOrderStatus.dealWith(itemsBean.getBtn());
        switch (status) {
            case RunOrderStatus.RUN_STATUS_PAY:
                holder.tvPay.setVisibility(View.VISIBLE);
                holder.tvPay.setText("立即支付");
                holder.tvPay.setOnClickListener(v -> {
                    if (itemStatus != null) {
                        itemStatus.pay(position);
                    }
                });
                holder.tvOne.setVisibility(View.GONE);
                break;
            case RunOrderStatus.RUN_STATUS_CONFIRM:
                holder.tvPay.setVisibility(View.GONE);
                holder.tvOne.setVisibility(View.VISIBLE);
                holder.tvOne.setText("确认订单");
                holder.tvOne.setOnClickListener(v -> {
                    if (itemStatus != null) {
                        itemStatus.confirm(position);
                    }
                });
                break;
            case RunOrderStatus.RUN_STATUS_PAY_CANCEL:
                holder.tvPay.setVisibility(View.VISIBLE);
                holder.tvPay.setText("立即支付");
                holder.tvPay.setOnClickListener(v -> {
                    if (itemStatus != null) {
                        itemStatus.pay(position);
                    }
                });
                holder.tvOne.setVisibility(View.VISIBLE);
                holder.tvOne.setText("取消订单");
                holder.tvOne.setOnClickListener(v -> {
                    if (itemStatus != null) {
                        itemStatus.cancel(position);
                    }
                });
                break;
            case RunOrderStatus.RUN_STATUS_AGAIN_COMMENT:
                holder.tvPay.setVisibility(View.VISIBLE);
                holder.tvPay.setText("评价");
                holder.tvPay.setOnClickListener(v -> {
                    if (itemStatus != null) {
                        itemStatus.comment(position);
                    }
                });
                holder.tvOne.setVisibility(View.VISIBLE);
                holder.tvOne.setText("再来一单");
                holder.tvOne.setOnClickListener(v -> {
                    if (itemStatus != null) {
                        itemStatus.again(position);
                    }

                });
                break;
            case RunOrderStatus.RUN_STATUS_CANCEL:
                holder.tvPay.setVisibility(View.GONE);
                holder.tvOne.setVisibility(View.VISIBLE);
                holder.tvOne.setText("取消订单");
                holder.tvOne.setOnClickListener(v -> {
                    if (itemStatus != null) {
                        itemStatus.cancel(position);
                    }
                });
                break;
            case RunOrderStatus.RUN_STATUS_CUI:
                holder.tvPay.setVisibility(View.GONE);
                holder.tvOne.setVisibility(View.VISIBLE);
                holder.tvOne.setText("催单");
                holder.tvOne.setOnClickListener(v -> {
                    if (itemStatus != null) {
                        itemStatus.cui(position);
                    }
                });
                break;
            case RunOrderStatus.RUN_STATUS_AGAIN:
                holder.tvPay.setVisibility(View.GONE);
                holder.tvOne.setVisibility(View.VISIBLE);
                holder.tvOne.setText("再来一单");
                holder.tvOne.setOnClickListener(v -> {
                    if (itemStatus != null) {
                        itemStatus.again(position);
                    }
                });
                break;
            case RunOrderStatus.STATUS_NULL:
                holder.tvPay.setVisibility(View.GONE);
                holder.tvOne.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<Data_Run_Order_UnDone.ItemsBean> list) {
        data.clear();
        data.addAll(list);
    }

    public void addAll(List<Data_Run_Order_UnDone.ItemsBean> list) {
        int lastIndex = data.size();
        if (data.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public List<Data_Run_Order_UnDone.ItemsBean> getDatas() {
        return data;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.stv_type)
        SuperTextView stvType;
        @Bind(R.id.tv_order_info)
        TextView tvOrderInfo;
        @Bind(R.id.tv_order_msg)
        TextView tvOrderMsg;
        @Bind(R.id.tv_gou_addr)
        TextView tvGouAddr;
        @Bind(R.id.tv_fa_addr)
        TextView tvFaAddr;
        @Bind(R.id.tv_pei_amount)
        TextView tvPeiAmount;
        @Bind(R.id.ll_pei_amount)
        LinearLayout llPeiAmount;
        @Bind(R.id.tv_one)
        TextView tvOne;
        @Bind(R.id.tv_pay)
        TextView tvPay;
        @Bind(R.id.ll_order)
        LinearLayout llOrder;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemStatus {
        /*订单详情*/
        void detail(int podition);

        /*去支付*/
        void pay(int podition);

        /*取消订单*/
        void cancel(int podition);

        /*催单*/
        void cui(int podition);

        /*确认收货*/
        void confirm(int podition);

        /*评价*/
        void comment(int podition);

        /*再来一单*/
        void again(int podition);
    }

    public void setOnItemStatus(OnItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

}
