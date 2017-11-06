package com.jhcms.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

/**
 * Created by admin on 2017/5/5.
 */
public class MallShopDetailsAdapter extends BaseAdapter {
    private Context context;

    public MallShopDetailsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_guess, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView mPicIv;
        TextView mNameTv;
        TextView mPriceTv;
        TextView mShopNameTv;
        ImageView mSubIv;
        TextView mNumberTv;
        ImageView mAddIv;

        ViewHolder(View view) {
            mPicIv= (ImageView) view.findViewById(R.id.pic_iv);
            mNameTv= (TextView) view.findViewById(R.id.name_tv);
            mPriceTv= (TextView) view.findViewById(R.id.price_tv);
            mShopNameTv= (TextView) view.findViewById(R.id.shop_name_tv);
            mSubIv= (ImageView) view.findViewById(R.id.sub_iv);
            mNumberTv= (TextView) view.findViewById(R.id.number_tv);
            mAddIv= (ImageView) view.findViewById(R.id.add_iv);
        }
    }
}
