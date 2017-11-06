package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/4/18.
 */
public class SortAllApter extends BaseAdapter {
    private Context context;
    private  int    mCurrentPos;
    private ArrayList<String> list;

    public SortAllApter(int mCurrentPos, Context context, ArrayList<String> list) {
        this.mCurrentPos = mCurrentPos;
        this.context = context;
        this.list = list;
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewhold viewhold=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_tuan_frggoods_sortall,null);
            viewhold=new viewhold();
            viewhold.mtv= (TextView) convertView.findViewById(R.id.itemSort_tv);
            viewhold.miv= (TextView) convertView.findViewById(R.id.imageview);
            convertView.setTag(viewhold);
        }else {
            viewhold= (SortAllApter.viewhold) convertView.getTag();
        }
        if(mCurrentPos == position){
            viewhold.mtv.setEnabled(true);
            viewhold.miv.setBackgroundColor(Color.parseColor("#20ad20"));
            viewhold.mtv.setBackgroundColor(Color.parseColor("#ffffff"));

        } else {
            viewhold.mtv.setEnabled(false);
            viewhold.miv.setBackgroundColor(Color.parseColor("#f4f4f4"));
            viewhold.mtv.setBackgroundColor(Color.parseColor("#f4f4f4"));
        }
        return convertView;
    }
    class  viewhold{
        private TextView mtv;
        private TextView miv;
    }
}
