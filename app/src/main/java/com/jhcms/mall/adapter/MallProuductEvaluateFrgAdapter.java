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
 * Created by admin on 2017/5/3.
 */
public class MallProuductEvaluateFrgAdapter extends BaseAdapter {
    private Context context;
    private  int positiontag=1;

    public MallProuductEvaluateFrgAdapter(Context context) {
        this.context = context;
    }

    public int getPositiontag() {
        return positiontag;
    }

    public void setPositiontag(int positiontag) {
        this.positiontag = positiontag;
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
        viewholde viewholde=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_prouduct_evaluate_frg_adapter,null);
            viewholde=new viewholde();
            viewholde.mUserimage= (ImageView) convertView.findViewById(R.id.userimage);
            viewholde.mUserName= (TextView) convertView.findViewById(R.id.username);
            viewholde.mUserStandard= (TextView) convertView.findViewById(R.id.standard);
            viewholde.mUserTime= (TextView) convertView.findViewById(R.id.usertime);
            viewholde.mUsercontent= (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(viewholde);

        }else {
            viewholde= (MallProuductEvaluateFrgAdapter.viewholde) convertView.getTag();
        }
        viewholde.mUserName.setText("张三丰"+positiontag);
        return convertView;
    }

    class  viewholde{
        private ImageView mUserimage;
        private TextView mUserName,mUserStandard,mUserTime,mUsercontent;
    }
}
