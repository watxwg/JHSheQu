package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/6/24.
 * TODO
 */

public class AddressItemAdapter extends RecyclerView.Adapter<AddressItemAdapter.MyViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<String> data;
    private List<String> subData;
    private OnAddItemClickListener listener;

    public AddressItemAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<String> data, List<String> subData) {
        this.data = data;
        this.subData = subData;
        notifyDataSetChanged();
    }

    public interface OnAddItemClickListener {
        void onAddItemClick(View v, int position);
    }

    public void setOnAddItemClickListener(OnAddItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_select_address_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            holder.address.setText(data.get(position));
            if (null != subData && subData.size() > 0) {
                if (!TextUtils.isEmpty(subData.get(position))) {
                    holder.subAddress.setVisibility(View.VISIBLE);
                    holder.subAddress.setText(subData.get(position));
                }
            } else {
                holder.subAddress.setVisibility(View.GONE);
            }
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != listener) {
                        listener.onAddItemClick(v, position);
                    }
                }
            });
        } catch (Exception e) {

        }


    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_root)
        LinearLayout root;
        @Bind(R.id.address)
        TextView address;

        @Bind(R.id.sub_address)
        TextView subAddress;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
