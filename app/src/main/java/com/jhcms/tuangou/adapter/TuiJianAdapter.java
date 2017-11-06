package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhcms.common.model.Data_Group_Shop_Detail;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.tuangou.activity.TuanProductDetailsActivity;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/7/19.
 * TODO
 */

public class TuiJianAdapter extends RecyclerView.Adapter<TuiJianAdapter.MyViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<Data_Group_Shop_Detail.DetailBean.TuijianBean> data;

    public TuiJianAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_tuijian_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Data_Group_Shop_Detail.DetailBean.TuijianBean tuijianBean = data.get(position);
        holder.bindData(tuijianBean);

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<Data_Group_Shop_Detail.DetailBean.TuijianBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_comm_pic)
        ImageView ivCommPic;
        @Bind(R.id.tv_comm_title)
        TextView tvCommTitle;
        @Bind(R.id.tv_comm_prices)
        TextView tvCommPrices;
        @Bind(R.id.tv_comm_oldPrices)
        TextView tvCommOldPrices;
        @Bind(R.id.tv_comm_sold)
        TextView tvCommSold;
        @Bind(R.id.ll_comm)
        RelativeLayout llComm;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final Data_Group_Shop_Detail.DetailBean.TuijianBean tuijianBean) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.width = Utils.getScreenW(context) / 2 - Utils.dip2px(context, 1);
            params.height = params.width;
            ivCommPic.setLayoutParams(params);

            Utils.LoadStrPicture(context, Api.IMAGE_URL + tuijianBean.photo, ivCommPic);
            tvCommTitle.setText(tuijianBean.title);
            tvCommPrices.setText("￥"+tuijianBean.price);
            tvCommOldPrices.setText("￥" + tuijianBean.market_price);
            tvCommSold.setText("已售" + tuijianBean.sales);
            llComm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Utils.isFastDoubleClick()) {
                        Intent intent = new Intent(context, TuanProductDetailsActivity.class);
                        intent.putExtra(TuanProductDetailsActivity.TUAN_ID, tuijianBean.tuan_id);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
