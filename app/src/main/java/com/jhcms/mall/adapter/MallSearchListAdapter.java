package com.jhcms.mall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者：WangWei
 * 日期：2017/8/22 16:11
 * 描述：搜索界面的列表数据适配器
 */

public class MallSearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = 10;
    private static final int NORMAL = 20;
    private List<String> mData;
    private LayoutInflater inflater;
    private boolean mIsShowHeader;
    private View headerView;
    private OnItemClickListener mListener;

    /**
     * @param context
     * @param data
     * @param isShowHeader 是否显示headerView
     */
    public MallSearchListAdapter(Context context, List<String> data, boolean isShowHeader, @NonNull View headerView) {
        mData = data;
        inflater = LayoutInflater.from(context);
        mIsShowHeader = isShowHeader;
        this.headerView = headerView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && headerView != null && mIsShowHeader) {
            return HEADER;

        } else {
            return NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return new HeaderViewHolder(headerView);
        } else {
            View view = inflater.inflate(R.layout.mall_list_item_search, parent, false);
            return new NormalViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == HEADER) {
            return;
        }
        NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
        //防止数组越界
        if (headerView != null && mIsShowHeader) {

            normalViewHolder.tvName.setText(mData.get(position - 1));
        }else{
            normalViewHolder.tvName.setText(mData.get(position));

        }
        normalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(mData.get(position), position);
                }
            }
        });
    }

    public void setHeaderShow(boolean isShowHeader) {
        mIsShowHeader = isShowHeader;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (headerView != null && mIsShowHeader) {
            return mData == null ? 1 : mData.size() + 1;

        } else {
            return mData == null ? 0 : mData.size();
        }
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public NormalViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String s, int position);
    }
}
