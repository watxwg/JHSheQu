package com.jhcms.mall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhcms.mall.model.interfaces.CategoryInterface;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangWei
 * 日期：2017/8/24 11:27
 * 描述：左边分类列表数据适配器
 */

public class LeftFenLeiAdapter extends RecyclerView.Adapter<LeftFenLeiAdapter.NormalViewHolder> {
    private LayoutInflater inflater;
    private Context mContext;
    private int currentSelect;
    private List<? extends CategoryInterface> mData;
    private OnItemclickListener mListener;

    public LeftFenLeiAdapter(Context context, List<? extends CategoryInterface> data) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        currentSelect = 0;
        mData = data;
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mall_list_item_fenlei_left, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder,final int position) {
        final CategoryInterface categoryInterface = mData.get(position);
        holder.tvContent.setText(categoryInterface.getTitleImp());
        if (currentSelect == position) {
            holder.vTag.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundResource(R.color.white);
            holder.tvContent.setTextColor(Color.parseColor(mContext.getString(R.string.mall_color_theme)));
        }else{
            holder.vTag.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundResource(R.color.mall_color_f4f4f4);
            holder.tvContent.setTextColor(Color.parseColor(mContext.getString(R.string.mall_color_4a4c4d)));
        }
//        if (mData.get(position).hasSubDataImp()) {
//            holder.vArrow.setVisibility(View.VISIBLE);
//        } else {
//
//            holder.vArrow.setVisibility(View.INVISIBLE);
//        }
        holder.itemView.setOnClickListener(v->{

            if (mListener != null) {
                mListener.onItemClickListener(categoryInterface,position);
            }
            currentSelect = holder.getAdapterPosition();
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.v_tag)
        View vTag;
        @Bind(R.id.v_arrow)
        View vArrow;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface OnItemclickListener{
        void onItemClickListener(CategoryInterface catagoryInterface, int position);
    }
    public void setOnItemClickListener(OnItemclickListener listener){
        mListener = listener;
    }
}
