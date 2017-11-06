package com.jhcms.shequ.weight;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/1.
 * TODO
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<Product> products;
    private Context context;
    private OnMoreListener moreListener;

    public interface OnMoreListener {
        void moreListener(int position);
    }

    public void setData(List<Product> products) {
        this.products = products;
    }

    public void setOnMoreListener(OnMoreListener moreListener) {
        this.moreListener = moreListener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        Product product = products.get(position);
        Utils.LoadStrPicture(context, Api.IMAGE_URL + product.thumb, holder.cover);
        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreListener != null) {
                    moreListener.moreListener(position);
                }
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin_horizontal = context.getResources().getDimensionPixelSize(R.dimen.dp_8);
        if (position % 2 == 0) {
            lp.setMargins(margin_horizontal, margin_horizontal / 2, margin_horizontal / 2, margin_horizontal / 2);
        } else {
            lp.setMargins(margin_horizontal / 2, margin_horizontal / 2, margin_horizontal, margin_horizontal / 2);
        }
        holder.itemView.setLayoutParams(lp);
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;

        ProductViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.cover);
        }
    }
}

