package com.jhcms.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhcms.waimaiV3.R;

/**
 * Created by admin
 * on 2017/8/25.
 * TODO
 */

class MallShopCartCommAdapterNew extends RecyclerView.Adapter<MallShopCartCommAdapterNew.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;

    public MallShopCartCommAdapterNew(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(layoutInflater.inflate(R.layout.adapter_shop_car_comm_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
