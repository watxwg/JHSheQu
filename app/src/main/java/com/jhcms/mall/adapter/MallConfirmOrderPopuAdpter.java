package com.jhcms.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

/**
 * Created by admin on 2017/5/8.
 */
public class MallConfirmOrderPopuAdpter  extends BaseAdapter{
    private Context context;
    public MallConfirmOrderPopuAdpter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_confirm_order_popu_adpter,null);
            viewholder=new viewholder(convertView);
            convertView.setTag(viewholder);
        }else {
            viewholder= (MallConfirmOrderPopuAdpter.viewholder) convertView.getTag();
        }
        final MallConfirmOrderPopuAdpter.viewholder finalViewholder = viewholder;
        viewholder.mTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }
   class viewholder{
       private TextView mTextview;
       public  viewholder(View view){
           mTextview= (TextView) view.findViewById(R.id.textcontent);
       }
   }
}
