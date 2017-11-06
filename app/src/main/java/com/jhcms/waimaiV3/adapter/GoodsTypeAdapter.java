package com.jhcms.waimaiV3.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.model.GoodsItem;
import com.jhcms.waimaiV3.model.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wyj on 2017/5/8
 * TODO:商品类型adapter
 */
public class GoodsTypeAdapter extends RecyclerView.Adapter<GoodsTypeAdapter.ViewHolder> {

    public int selectTypeId;
    public WaiMaiShopActivity activity;
    public ArrayList<GoodsItem> dataList;
    private onClickListener listener;
    private List<Type> data;

    public void setData(List<Type> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public interface onClickListener {
        void onClick(int typeId);
    }

    public GoodsTypeAdapter(WaiMaiShopActivity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_goods_type_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Type item = data.get(position);
        holder.bindData(item, position);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public void setSelectTypeId(int selectTypeId) {
        this.selectTypeId = selectTypeId;
        notifyDataSetChanged();
    }

    public int getSelectTypeId() {
        return selectTypeId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvCount, type;
        private View view;
        private Type item;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            type = (TextView) itemView.findViewById(R.id.type);
            view = itemView.findViewById(R.id.view);
            itemView.setOnClickListener(this);
        }

        public void bindData(Type item, int position) {
            this.item = item;
            type.setText(item.itemsEntity.title);

            int count = activity.getSelectedGroupCountByTypeId(item.typeId);
            tvCount.setText(String.valueOf(count));
            if (count < 1) {
                tvCount.setVisibility(View.GONE);
            } else {
                tvCount.setVisibility(View.VISIBLE);
            }

            if (item.typeId == selectTypeId) {
                view.setSelected(true);
                itemView.setBackgroundColor(Color.WHITE);
            } else {
                view.setSelected(false);
                itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        @Override
        public void onClick(View v) {
            if (null != listener) {
                listener.onClick(item.typeId);
            }
        }
    }

    public void setOnClickListener(onClickListener listener) {
        this.listener = listener;
    }
}