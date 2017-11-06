package com.jhcms.shequ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/23.
 */
public class StoreAdapter extends BaseAdapter {

    private Context context;
    HashMap<Integer,Boolean> isShow = new HashMap<>();

    onShowLister showLister;

    public interface onShowLister{
        public void onShow();
    }

    public StoreAdapter(Context context,onShowLister showLister) {
        this.context = context;
        this.showLister = showLister;
    }

    @Override
    public int getCount() {
        return 2;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_store, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(i==0){
            holder.mQuanTv.setVisibility(View.VISIBLE);
            holder.mTuanTv.setVisibility(View.VISIBLE);
            holder.mDiscountTv.setVisibility(View.GONE);
            holder.mPayTv.setVisibility(View.GONE);
        }else {
            holder.mQuanTv.setVisibility(View.VISIBLE);
            holder.mTuanTv.setVisibility(View.VISIBLE);
            holder.mDiscountTv.setVisibility(View.VISIBLE);
            holder.mPayTv.setVisibility(View.VISIBLE);
        }

        holder.mActiveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.e("iiiii="+i+";isShow="+isShow.get(i));
                if(isShow.get(i)==null){
                    isShow.put(i,false);
                }
                if(isShow.get(i)){
                    isShow.put(i,false);
                    holder.mQuanTv.setVisibility(View.GONE);
                    holder.mTuanTv.setVisibility(View.GONE);
                    holder.mDiscountTv.setVisibility(View.GONE);
                    holder.mPayTv.setVisibility(View.GONE);
                }else{
                    isShow.put(i, true);
                    holder.mQuanTv.setVisibility(View.VISIBLE);
                    holder.mTuanTv.setVisibility(View.VISIBLE);
                    holder.mDiscountTv.setVisibility(View.VISIBLE);
                    holder.mPayTv.setVisibility(View.VISIBLE);
                }
                showLister.onShow();
            }
        });

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.shop_logo)
        ImageView mShopLogoIv;
        @Bind(R.id.name_tv)
        TextView mNameTv;
        @Bind(R.id.song_iv)
        ImageView mSongIv;
        @Bind(R.id.shop_star)
        RatingBar mShopStar;
        @Bind(R.id.score_tv)
        TextView mScoreTv;
        @Bind(R.id.sell_count)
        TextView mSellCount;
        @Bind(R.id.cost_tv)
        TextView mCostTv;
        @Bind(R.id.distance)
        TextView mDistanceTv;
        @Bind(R.id.pei_time)
        TextView mPeiTimeTv;
        @Bind(R.id.first_tv)
        TextView mFirstTv;
        @Bind(R.id.active_tv)
        TextView mActiveTv;
        @Bind(R.id.jian_tv)
        TextView mJianTv;
        @Bind(R.id.quan_tv)
        TextView mQuanTv;
        @Bind(R.id.tuan_tv)
        TextView mTuanTv;
        @Bind(R.id.discount_tv)
        TextView mDiscountTv;
        @Bind(R.id.pay_tv)
        TextView mPayTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
