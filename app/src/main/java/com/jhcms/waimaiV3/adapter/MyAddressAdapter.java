package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.jhcms.common.model.MyAddress;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/6/30.
 * TODO 选择位置中我的收货地址adapter
 */

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<MyAddress> data;
    private OnAddressItemClick itemClick;

    public MyAddressAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.adapter_myaddress_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MyAddress myAddress = data.get(position);
        holder.tvUserInfo.setText(myAddress.contact + "-" + myAddress.mobile);
        holder.tvUserAddress.setText(myAddress.addr);
        switch (myAddress.type) {
            case 1:
                holder.tvType.setText("家");
                holder.tvType.setSolid(context.getResources().getColor(R.color.color_yan));
                break;
            case 2:
                holder.tvType.setText("公司");
                holder.tvType.setSolid(context.getResources().getColor(R.color.color_blue));
                break;
            case 3:
                holder.tvType.setText("学校");
                holder.tvType.setSolid(context.getResources().getColor(R.color.themColor));
                break;
            case 4:
                holder.tvType.setText("其他");
                holder.tvType.setSolid(context.getResources().getColor(R.color.second_txt_color));
                break;
        }
        holder.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != itemClick) {
                    itemClick.onAddressItemClick(position);
                }

            }
        });
    }

    public interface OnAddressItemClick {
        void onAddressItemClick(int position);
    }

    public void setOnAddressItemClick(OnAddressItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<MyAddress> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_userInfo)
        TextView tvUserInfo;
        @Bind(R.id.tv_type)
        SuperTextView tvType;
        @Bind(R.id.tv_userAddress)
        TextView tvUserAddress;
        @Bind(R.id.ll_root)
        LinearLayout llRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
