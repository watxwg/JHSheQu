package com.jhcms.shequ.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Wyj on 2017/5/18
 * TODO:
 */
public class SearchMapAddressAdapter extends RecyclerView.Adapter<SearchMapAddressAdapter.MapViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private ArrayList<PoiItem> mapPoiItems;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public SearchMapAddressAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MapViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_map_address_item, parent, false);
        return new MapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MapViewHolder holder, final int position) {
        holder.tvAddressTitle.setText(mapPoiItems.get(position).getTitle());
        holder.tvAddressContent.setText(mapPoiItems.get(position).getSnippet());
        holder.llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mapPoiItems == null ? 0 : mapPoiItems.size();
    }

    public void setData(ArrayList<PoiItem> mapPoiItems) {
        this.mapPoiItems = mapPoiItems;
        notifyDataSetChanged();
    }

    public class MapViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_address_title)
        TextView tvAddressTitle;
        @Bind(R.id.tv_address_content)
        TextView tvAddressContent;
        @Bind(R.id.ll_address)
        LinearLayout llAddress;

        public MapViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
