package com.jhcms.shequ.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * Created by Wyj on 2017/4/8.
 */
public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.MyViewHolder> {
    private final Context context;
    LayoutInflater layoutInflater;
    private TextView tvHistory;
    private List<String> data;
    private OnClickListener listener;

    public interface OnClickListener {
        public void onClick(View view, int position);
    }

    public SearchHistoryAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_history_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvHistory.setText(data.get(position));
        holder.tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHistory;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvHistory = (TextView) itemView.findViewById(R.id.tv_history);
        }
    }
}
