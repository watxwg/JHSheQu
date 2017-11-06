package com.jhcms.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.model.MallOrderItemMode;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WebViewActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * on 2017/9/14.17:12
 * TODO
 */

class MallOrderProductAdapter extends RecyclerView.Adapter<MallOrderProductAdapter.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private String detailUrl;
    private Context mContext;
    private List<MallOrderItemMode.ItemsBean.ProductsBean> data;

    public MallOrderProductAdapter(Context context, String detail) {
        mContext = context;
        detailUrl = detail;
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_mall_product_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<MallOrderItemMode.ItemsBean.ProductsBean> data) {
        this.data = data;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_shop_logo)
        ImageView ivShopLogo;
        @Bind(R.id.tv_comment_name)
        TextView tvCommentName;
        @Bind(R.id.tv_comment_spec)
        TextView tvCommentSpec;
        @Bind(R.id.tv_comm_pices)
        TextView tvCommPices;
        @Bind(R.id.tv_comm_num)
        TextView tvCommNum;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(int position) {
            MallOrderItemMode.ItemsBean.ProductsBean productsBean = data.get(position);
            Utils.LoadStrPicture(mContext, Api.IMAGE_URL + productsBean.photo, ivShopLogo);
            tvCommentName.setText(productsBean.product_name);
            tvCommPices.setText("￥" + productsBean.product_price);
            tvCommentSpec.setText("规格 " + productsBean.stock_real_name);
            tvCommNum.setText("X" + productsBean.product_number);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL, detailUrl);
                mContext.startActivity(intent);
            });
        }
    }
}
