package com.jhcms.common.unmengshareutils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/5/9.
 */
public class MShareUnmengGridviewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GridviewModel> list;

    public MShareUnmengGridviewAdapter(Context context, ArrayList<GridviewModel> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        viewholde viewholde=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_share_unmeng_gridview_adapter,null);
            viewholde=new viewholde(convertView);
            convertView.setTag(viewholde);
        }else {
            viewholde= (MShareUnmengGridviewAdapter.viewholde) convertView.getTag();
        }
        viewholde.mShareImage.setImageResource(list.get(position).getImageRes());
        viewholde.mShareName.setText(list.get(position).getShareName());
        return convertView;
    }

    class  viewholde{
        private ImageView mShareImage;
        private TextView mShareName;

        public viewholde(View view) {
            mShareImage= (ImageView) view.findViewById(R.id.iv);
            mShareName= (TextView) view.findViewById(R.id.tv);
        }
    }
}
