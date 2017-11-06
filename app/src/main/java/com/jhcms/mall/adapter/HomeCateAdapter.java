package com.jhcms.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.model.CateModel;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangWei
 * 日期：2017/8/21 15:25
 * 描述：首页Cate数据适配器
 */

public class HomeCateAdapter extends RecyclerView.Adapter<HomeCateAdapter.NormalViewHolder>{

    private final LayoutInflater inflater;
    private Context mContext;
    private List<CateModel> mData;
    private OnCateClickListener mListener;

    public HomeCateAdapter(Context context, List<CateModel> data){
        inflater = LayoutInflater.from(context);
        mData=data;

    }
    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mall_list_item_home_cate, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder,final int position) {
        final CateModel cateModel = mData.get(position);
        Log.e("=========", "onBindViewHolder: "+position);
        Utils.LoadStrPicture(mContext,Api.IMAGE_URL+cateModel.getThumb(),holder.ivCate);
        holder.tvCateName.setText(cateModel.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onCateClick(cateModel,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_cate)
        ImageView ivCate;
        @Bind(R.id.tv_cate_name)
        TextView tvCateName;
        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public void setOnCateClickListener(OnCateClickListener listener){
        mListener = listener;
    }
    public interface OnCateClickListener{
        void onCateClick(CateModel cateModel,int position);
    }
}
