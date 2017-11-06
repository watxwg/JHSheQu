package com.jhcms.mall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.BinderThread;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhcms.mall.litepal.MallSearchHistory;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangWei
 * 日期：2017/8/24 09:31
 * 描述：商品商家界面 综合popupwindos列表的数据适配器
 */

public class ListSelectAdapter extends RecyclerView.Adapter<ListSelectAdapter.NormalViewHolder>{
    private LayoutInflater inflater;
    private List<String> mSelectData;
    private int currentSelect;
    private OnItemSelectListener mListener;

    public ListSelectAdapter(Context context, List<String> selectData){
        inflater = LayoutInflater.from(context);
        mSelectData=new ArrayList<>();
        mSelectData.addAll(selectData);
        currentSelect = -1;
    }
    public void setData(List<String> data){
        mSelectData.clear();
        mSelectData.addAll(data);
        notifyDataSetChanged();
    }
    public void resetSelect(){
        currentSelect=-1;
        notifyDataSetChanged();
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mall_list_item_select, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder,final int position) {
        holder.tvSelectName.setText(mSelectData.get(position));
        if(position==currentSelect){
            holder.vSelected.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundResource(R.color.mall_color_f7f7f7);
            holder.tvSelectName.setTextColor(Color.parseColor("#20ad20"));
        }else{
            holder.tvSelectName.setTextColor(Color.parseColor("#4a4c4d"));
            holder.vSelected.setVisibility(View.GONE);
            holder.itemView.setBackgroundResource(R.color.white);
        }
        holder.itemView.setOnClickListener(v->{
            currentSelect=position;
            notifyDataSetChanged();
            if (mListener != null) {
                mListener.onItemSelect(mSelectData.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSelectData==null?0:mSelectData.size();
    }
    public void setOnItemSelectListener(OnItemSelectListener listener){
        mListener = listener;
    }
    public interface OnItemSelectListener{
        void onItemSelect(String select,int position);
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_select_name)
        TextView tvSelectName;
        @Bind(R.id.v_selected)
        View vSelected;
        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
