package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.model.WebJson;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/7/19.
 */
public class WebviewMoreAdapter extends BaseAdapter {
   private WebJson webJson;
    private Context context;

    public WebviewMoreAdapter(WebJson webJson, Context context) {
        this.webJson = webJson;
        this.context = context;
    }

    @Override
    public int getCount() {
        return webJson.getParams().getItems().size();
    }

    @Override
    public Object getItem(int position) {
        return webJson.getParams().getItems().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewholde viewholde=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_webview_more_adapter,null);
            viewholde=new viewholde(convertView);
            convertView.setTag(viewholde);
        }else {
            viewholde= (WebviewMoreAdapter.viewholde) convertView.getTag();
        }

        if(webJson.getParams().getItems().get(position).getType().equals("search")){
            viewholde.textView.setText("搜索");
            viewholde.imageView.setImageResource(R.mipmap.icon_search);
        }else if (webJson.getParams().getItems().get(position).getType().equals("phone")){
            viewholde.textView.setText("电话");
            viewholde.imageView.setImageResource(R.mipmap.btn_call_white_webview);
        }else if(webJson.getParams().getItems().get(position).getType().equals("share")){
            viewholde.textView.setText("分享");
            viewholde.imageView.setImageResource(R.mipmap.navbar_btn_share);
        }else  if(webJson.getParams().getItems().get(position).getType().equals("text")){
            viewholde.textView.setText(webJson.getParams().getItems().get(position).getParams().getTitle());
            viewholde.imageView.setImageResource(R.mipmap.icon_text_white);
        }
        return convertView;
    }

    class  viewholde{
        TextView textView;
        ImageView imageView;
        public  viewholde(View view){
            textView= (TextView) view.findViewById(R.id.textview);
            imageView= (ImageView) view.findViewById(R.id.image);
        }
    }
}
