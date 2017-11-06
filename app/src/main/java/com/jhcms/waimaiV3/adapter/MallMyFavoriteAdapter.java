package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

/**
 * Created by admin on 2017/5/17.
 */
public class MallMyFavoriteAdapter extends BaseAdapter {
    private Context context;
    public MallMyFavoriteAdapter(Context context) {
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
        viewholder viewholder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_myfavorite_adpter_layout,null);
            viewholder=new viewholder(convertView);
            convertView.setTag(viewholder);
        }else {
            viewholder= (MallMyFavoriteAdapter.viewholder) convertView.getTag();
        }
        return convertView;
    }

    class viewholder{
        private ImageView mIvFavorite;
        private TextView mTvFavoriteTitleContent;
        private  TextView mTvFavoriteStandard;
        private  TextView mTvfavoritemoney;
        private  TextView mTvSellcount;
        private  TextView mTvAttention;

        public  viewholder(View view){
            mIvFavorite= (ImageView) view.findViewById(R.id.favorite_iv);
            mTvFavoriteTitleContent= (TextView) view.findViewById(R.id.favoritetitlecontemt_tv);
            mTvFavoriteStandard= (TextView) view.findViewById(R.id.foaoritestandard_tv);
            mTvfavoritemoney= (TextView) view.findViewById(R.id.money);
            mTvSellcount= (TextView) view.findViewById(R.id.sellconut);
            mTvAttention= (TextView) view.findViewById(R.id.attention);
        }
    }
}
