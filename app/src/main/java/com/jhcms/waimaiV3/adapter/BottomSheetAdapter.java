package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.model.PayMent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie.
 * Date 2017/5/14
 * Time 15:09
 * TODO
 */
public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private int type;
    private int selectId = 0;
    private OnItemClickListener itemClickListener;
    private List<String> data;
    private int[] payIcon = {R.mipmap.icon_alipay, R.mipmap.icon_wxpay, R.mipmap.icon_balancepay};
    private PayMent payMent;

    public void setSelectId(int selectId) {
        this.selectId = selectId;
        notifyDataSetChanged();
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setPayMent(PayMent payMent) {
        this.payMent = payMent;
        notifyDataSetChanged();
    }

    public static interface OnItemClickListener {
        void onItemClick(int position);
    }

    public BottomSheetAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_bottomsheet_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //TODO 支付方式
        if (type == 0) {
            holder.ivLeftBtn.setVisibility(View.GONE);
            holder.ivPayIcon.setVisibility(View.GONE);
            if (position == 0 && payMent.online_pay.equals("0")) {
                holder.ivRightBtn.setVisibility(View.GONE);
                holder.tvNot.setText("(商家不支持在线支付)");
                holder.tvNot.setVisibility(View.VISIBLE);
                holder.llRoot.setEnabled(false);
            } else if (position == 0 && payMent.online_pay.equals("1")) {
                holder.ivRightBtn.setVisibility(View.VISIBLE);
                holder.tvNot.setVisibility(View.GONE);
                holder.llRoot.setEnabled(true);
            }
            if (position == 1 && payMent.is_daofu.equals("0")) {
                holder.tvNot.setText("(商家不支持货到付款)");
                holder.ivRightBtn.setVisibility(View.GONE);
                holder.tvNot.setVisibility(View.VISIBLE);
                holder.llRoot.setEnabled(false);
            } else if (position == 1 && payMent.is_daofu.equals("1")) {
                holder.ivRightBtn.setVisibility(View.VISIBLE);
                holder.tvNot.setVisibility(View.GONE);
                holder.llRoot.setEnabled(true);
            }

        } else if (type == 1) {
            holder.ivLeftBtn.setVisibility(View.GONE);
            holder.ivPayIcon.setVisibility(View.GONE);
            holder.tvNot.setVisibility(View.GONE);
            holder.ivRightBtn.setVisibility(View.VISIBLE);
        } else if (type == 2) { //TODO 去支付
            holder.ivLeftBtn.setVisibility(View.VISIBLE);
            holder.ivPayIcon.setVisibility(View.VISIBLE);
            holder.tvNot.setVisibility(View.GONE);
            holder.ivRightBtn.setVisibility(View.GONE);
            holder.ivPayIcon.setImageResource(payIcon[position]);
        }
        holder.tvTitle.setText(data.get(position));
        holder.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position);
                }
            }
        });
        if (position == selectId) {
            holder.ivLeftBtn.setSelected(true);
            holder.ivRightBtn.setSelected(true);
        } else {
            holder.ivLeftBtn.setSelected(false);
            holder.ivRightBtn.setSelected(false);
        }


    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * type == 0 支付方式（隐藏左侧圆形按钮）
     * type == 1 红包、优惠券（隐藏左侧圆形按钮和中间字体）
     * type == 2 隐藏右侧圆按钮（去支付）
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_left_btn)
        ImageView ivLeftBtn;
        @Bind(R.id.iv_pay_icon)
        ImageView ivPayIcon;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_not)
        TextView tvNot;
        @Bind(R.id.iv_right_btn)
        ImageView ivRightBtn;
        @Bind(R.id.ll_root)
        LinearLayout llRoot;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
