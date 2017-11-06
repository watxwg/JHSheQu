package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Wyj on 2017/5/24
 * TODO:商家资质
 */
public class ShopDetailsImageAdapter extends RecyclerView.Adapter<ShopDetailsImageAdapter.MyViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private OnItemClickListener listener;
    private List<String> data;

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void OnItemClick(View v, int position);
    }

    public ShopDetailsImageAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_shopdetails_image_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.width = (Utils.getScreenW(context) - Utils.dip2px(context, 40)) / 3;
        params.height = params.width * 8 / 11;
        holder.cvRoot.setLayoutParams(params);
        Utils.LoadStrPicture(context, Api.IMAGE_URL + data.get(position), holder.ivShopLogo);
        holder.cvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else if (data.size() > 3) {
            return 3;
        } else {
            return data.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_shop_logo)
        ImageView ivShopLogo;
        @Bind(R.id.cv_root)
        CardView cvRoot;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
