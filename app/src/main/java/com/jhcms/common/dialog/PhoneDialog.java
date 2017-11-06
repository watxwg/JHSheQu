package com.jhcms.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.common.model.phonemodel;
import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/17.
 */
public class PhoneDialog  extends Dialog {
    @Bind(R.id.Listview)
    ListViewForScrollView mlistview;
    private ArrayList<phonemodel> mdatalist;
    private  Context context;
    public PhoneDialog(Context context,ArrayList<phonemodel> mdatalist) {
        super(context, R.style.Dialog);
        this.mdatalist=mdatalist;
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.phone_dialog_layout_item);
        setContentView(R.layout.phone_dialog_layout_item);
        ButterKnife.bind(this);
        mlistview.setAdapter(new Phoneadapter());
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //传入服务， parse（）解析号码
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + mdatalist.get(position).getTitle());
                intent1.setData(data);
                context.startActivity(intent1);
            }
        });
    }



    class  Phoneadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mdatalist.size();
        }

        @Override
        public Object getItem(int position) {
            return mdatalist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            viewholder viewholder=null;
            if(convertView==null){
                convertView= LayoutInflater.from(context).inflate(R.layout.all_item_adapter_phone,null);
                viewholder=new viewholder(convertView);
                convertView.setTag(viewholder);
            }else {
                viewholder= (Phoneadapter.viewholder) convertView.getTag();
            }
           viewholder.phoneText.setText(mdatalist.get(position).getTitle());
            return convertView;
        }

        class viewholder{
            @Bind(R.id.textview)
            TextView phoneText;
            private  viewholder(View view){
                ButterKnife.bind(this,view);
            }
        }
    }
}
