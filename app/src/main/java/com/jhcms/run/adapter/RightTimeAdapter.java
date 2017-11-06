package com.jhcms.run.adapter;

import android.content.Context;
import android.view.View;

import com.jhcms.mall.adapter.CommonAdapter;
import com.jhcms.mall.adapter.OnItemClickListener;
import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/10/10 17:36
 * 描述：
 */

public class RightTimeAdapter extends CommonAdapter<String> {
    private int selectIndex;
    private OnItemClickListener<String> mListener;

    public RightTimeAdapter(Context context, List<String> data) {
        super(context, data, R.layout.adapter_run_right_time_item);
        selectIndex=-1;
    }

    @Override
    public void convertViewData(CommonViewHolder holder, String bean) {
        int position = holder.getAdapterPosition();
        if(position==-1){
            return;
        }
        if(position==selectIndex){
            holder.setVisiability(R.id.iv_select, View.VISIBLE);
        }else{
            holder.setVisiability(R.id.iv_select, View.GONE);
        }
        holder.setTextViewText(R.id.tv_content,bean);
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> {
                selectIndex=position;
                notifyDataSetChanged();
                mListener.OnItemClickListener(bean,position);
            });
        }
    }
    public void setOnItemSelectListener(OnItemClickListener<String> listener){
        mListener = listener;
    }
    public void setData(List<String> data){
        mData.clear();
        mData.addAll(data);
        selectIndex=-1;
        notifyDataSetChanged();

    }

    
}
