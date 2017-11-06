package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.model.Data_Group_Home;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.tuangou.activity.TuanProductDetailsActivity;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/17.
 */
public class SonAdapter extends BaseAdapter {
    private List<Data_Group_Home.ShopItemsEntity.ProductsEntity> list;
    private LayoutInflater inflater;
    private Context context;

    public SonAdapter(Context context, List<Data_Group_Home.ShopItemsEntity.ProductsEntity> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_tuan_frgment_listview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final Data_Group_Home.ShopItemsEntity.ProductsEntity productsEntity = list.get(position);
        Utils.LoadStrPicture(context, Api.IMAGE_URL + productsEntity.photo, holder.ivCommPic);
        holder.tvCommTitle.setText(productsEntity.title);
        holder.tvCommPrices.setText("￥" + productsEntity.price);
        holder.tvCommOldPrices.setText("￥" + productsEntity.market_price);
        holder.tvCommSold.setText("已售" + productsEntity.sales);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isFastDoubleClick()) {
                    Intent intent = new Intent(context, TuanProductDetailsActivity.class);
                    intent.putExtra(TuanProductDetailsActivity.TUAN_ID, productsEntity.tuan_id);
                    context.startActivity(intent);
                }

            }
        });
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_comm_pic)
        ImageView ivCommPic;
        @Bind(R.id.tv_comm_title)
        TextView tvCommTitle;
        @Bind(R.id.tv_comm_prices)
        TextView tvCommPrices;
        @Bind(R.id.tv_comm_oldPrices)
        TextView tvCommOldPrices;
        @Bind(R.id.tv_comm_sold)
        TextView tvCommSold;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
