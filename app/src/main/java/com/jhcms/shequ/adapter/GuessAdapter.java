package com.jhcms.shequ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/24.
 */
public class GuessAdapter extends BaseAdapter {

    private Context context;

    public GuessAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_guess, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.pic_iv)
        ImageView mPicIv;
        @Bind(R.id.name_tv)
        TextView mNameTv;
        @Bind(R.id.price_tv)
        TextView mPriceTv;
        @Bind(R.id.shop_name_tv)
        TextView mShopNameTv;
        @Bind(R.id.sub_iv)
        ImageView mSubIv;
        @Bind(R.id.number_tv)
        TextView mNumberTv;
        @Bind(R.id.add_iv)
        ImageView mAddIv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
