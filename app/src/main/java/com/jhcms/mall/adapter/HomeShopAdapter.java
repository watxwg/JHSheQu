package com.jhcms.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.model.CateModel;
import com.jhcms.mall.model.ShopItemModel;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangWei
 * 日期：2017/8/21 15:25
 * 描述：首页Cate数据适配器
 */

public class HomeShopAdapter extends RecyclerView.Adapter<HomeShopAdapter.NormalViewHolder>{

    private final LayoutInflater inflater;
    private Context mContext;
    private List<ShopItemModel> mData;
    private OnShopClickListener mListener;

    public HomeShopAdapter(Context context, List<ShopItemModel> data){
        inflater = LayoutInflater.from(context);
        mData=data;

    }
    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mall_list_item_home_shop, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder,final int position) {
        final ShopItemModel shopItemModel = mData.get(position);
        Utils.LoadStrPicture(mContext,Api.IMAGE_URL+shopItemModel.getLogo(),holder.ivShopLogo);
        holder.tvShopName.setText(shopItemModel.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onShopClick(shopItemModel,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_shop_logo)
        ImageView ivShopLogo;
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public void setOnShopClickListener(OnShopClickListener listener){
        mListener = listener;
    }
    public interface OnShopClickListener{
        void onShopClick(ShopItemModel shopItemModel,int position);
    }
}
