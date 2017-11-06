package com.jhcms.run.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.jhcms.mall.adapter.CommonAdapter;
import com.jhcms.mall.adapter.OnItemClickListener;
import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.run.mode.AddressInfoModel;
import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/10/11 14:06
 * 描述：地址选择
 */

public class AddressSelectAdapter extends CommonAdapter<AddressInfoModel>{
    private OnItemClickListener<AddressInfoModel> mListener;

    public AddressSelectAdapter(Context context, List<AddressInfoModel> data) {
        super(context, data, R.layout.adapter_run_address_item);
    }

    @Override
    public void convertViewData(CommonViewHolder holder, AddressInfoModel bean) {
        holder.setTextViewText(R.id.tv_name,bean.getContact()+"-"+bean.getMobile());
        holder.setTextViewText(R.id.tv_address_detail,mContext.getString(R.string.mall_收获地址,bean.getAddr()));
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> mListener.OnItemClickListener(bean,holder.getAdapterPosition()));
        }
    }
    public void setOnItemClickListener(OnItemClickListener<AddressInfoModel> listener){
        mListener = listener;
    }
}
