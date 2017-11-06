package com.jhcms.mall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhcms.mall.model.interfaces.CategoryInterface;
import com.jhcms.mall.model.interfaces.RightCatagoryInterface;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangWei
 * 日期：2017/8/24 14:55
 * 描述：商品商家列表界面 分类popupwindow右侧列表数据适配器
 */

public class PopRightFenLeiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_CONTENT_ITEM = 10;
    private static final int NORMAL_ITEM = 20;
    private LayoutInflater inflater;
    private Context mContext;
    private int currentSelect;
    private List<? extends CategoryInterface> mData;
    private OnItemClickListener mListener;

    public PopRightFenLeiAdapter(Context context, List<? extends CategoryInterface> data) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        currentSelect = -1;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == NO_CONTENT_ITEM) {
            view = inflater.inflate(R.layout.mall_list_item_no_content, parent, false);
            return new NoContentViewHodler(view);
        } else {
            view = inflater.inflate(R.layout.mall_list_item_select, parent, false);
            return new NormalViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        if (getItemViewType(position) == NO_CONTENT_ITEM) {
            holder.itemView.setBackgroundResource(R.color.mall_color_f4f4f4);
            return;
        }
        NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
        CategoryInterface categoryInterface = mData.get(position);
        normalViewHolder.tvSelectName.setText(categoryInterface.getTitleImp());
        if (currentSelect == position) {
            normalViewHolder.tvSelectName.setTextColor(Color.parseColor(mContext.getString(R.string.mall_color_theme)));
            normalViewHolder.vSelected.setVisibility(View.VISIBLE);
        } else {
            normalViewHolder.tvSelectName.setTextColor(Color.parseColor(mContext.getString(R.string.mall_color_4a4c4d)));
            normalViewHolder.vSelected.setVisibility(View.GONE);
        }
        normalViewHolder.itemView.setOnClickListener(v -> {
            currentSelect= position;
            notifyDataSetChanged();
            if (mListener != null) {
                mListener.OnItemClick(categoryInterface,position);
            }
        });
    }

    public void resetSelectIndex(){
        currentSelect=-1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData != null && mData.size() > 0) {
            return NORMAL_ITEM;
        } else {
            return NO_CONTENT_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return (mData == null || mData.size()==0)? 1 : mData.size();
    }

    public static class NoContentViewHodler extends RecyclerView.ViewHolder{

        public NoContentViewHodler(View itemView) {
            super(itemView);
        }
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_select_name)
        TextView tvSelectName;
        @Bind(R.id.v_selected)
        View vSelected;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void OnItemClick(CategoryInterface categoryInterface, int position);
    }
}
