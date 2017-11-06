package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/4/21.
 */
public class SortLeftAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
    private  int checkId;

    public SortLeftAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        viewholder viewholder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.tuan_sortall_head,null);
            viewholder=new viewholder();
            viewholder.tvGoodsItemTitle= (TextView) convertView.findViewById(R.id.tvGoodsItemTitle);
            convertView.setTag(viewholder);
        }else {
            viewholder= (SortLeftAdapter.viewholder) convertView.getTag();
        }
        viewholder.tvGoodsItemTitle.setText(list.get(position));

            if (checkId == position) {
                convertView.setBackgroundResource(R.drawable.tuan_goods_category_list_bg_select);
            } else {
                convertView.setBackgroundResource(R.drawable.tuan_goods_category_list_bg_normal);
            }

        return convertView;
    }
    class viewholder{
        private TextView tvGoodsItemTitle;
    }
}
