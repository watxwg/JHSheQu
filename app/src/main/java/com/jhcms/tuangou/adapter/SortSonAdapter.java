package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/4/18.
 */
public class SortSonAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
    private  int currpostion;

    public SortSonAdapter(Context context, ArrayList<String> list, int currpostion) {
        this.context = context;
        this.list = list;
        this.currpostion = currpostion;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position-1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewhodle viewhodle;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_tuan_frggoods_sonadapter,null);
            viewhodle=new viewhodle();
            viewhodle.mIvSoncontent= (ImageView) convertView.findViewById(R.id.soncontent_iv);
            viewhodle.mTvSoncontent= (TextView) convertView.findViewById(R.id.soncontent_tv);
            convertView.setTag(viewhodle);
        }else {
            viewhodle= (SortSonAdapter.viewhodle) convertView.getTag();
        }
        viewhodle.mTvSoncontent.setText(list.get(position));
        if(currpostion==position){
            viewhodle.mIvSoncontent.setVisibility(View.VISIBLE);
            viewhodle.mTvSoncontent.setTextColor(Color.parseColor("#20ad20"));
        }else {
            viewhodle.mIvSoncontent.setVisibility(View.GONE);
            viewhodle.mTvSoncontent.setTextColor(Color.parseColor("#333333"));
        }
        return convertView;
    }


    class  viewhodle{
        private TextView mTvSoncontent;
        private ImageView mIvSoncontent;
    }
}
