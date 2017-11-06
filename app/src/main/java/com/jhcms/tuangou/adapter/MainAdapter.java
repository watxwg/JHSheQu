package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.common.stickylistheaders.StickyListHeadersAdapter;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.tuangou.model.listmode;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mac on 16-8-16.
 */
public class MainAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context context;
    private List<String> headList;
    private  ArrayList<listmode> bodyList;

    public MainAdapter(Context context) {
        this.context = context;
    }

    public void setHeadList(List<String> headList) {
        this.headList = headList;
        notifyDataSetChanged();
    }

    public ArrayList<listmode> getBodyList() {
        return bodyList;
    }

    public void setBodyList(ArrayList<listmode> bodyList) {
        this.bodyList = bodyList;
    }

    //设置数据的个数
    @Override
    public int getCount() {
        return headList.size();
    }

    //设置item的条数
    @Override
    public Object getItem(int i) {
        return bodyList.get(i);
    }

    //获得相应数据集合中特定位置的数据项
    @Override
    public long getItemId(int i) {
        return i;
    }

    //获得头部相应数据集合中特定位置的数据项
    @Override
    public long getHeaderId(int position) {
        return position;
    }

    //绑定内容的数据
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        BodyHolder bodyHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_tuan_goodssort_list, null);
            bodyHolder = new BodyHolder(view);
            view.setTag(bodyHolder);
        } else {
            bodyHolder = (BodyHolder) view.getTag();
        }
        //设置数据
       // bodyHolder.bodyTv.setText(bodyList.get(i));
//        for (int j=0;j<bodyList.get(i).getList().size();j++){
//            bodyHolder.tv1.setText(bodyList.get(i).getList().get(j));
//        }
        gridview gridview=new gridview(bodyList.get(i));
        bodyHolder.gridViewForScrollView.setAdapter(gridview);


        return view;
    }

    //绑定头部的数据
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {

        HeadHolder headHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.tuan_sortall_head, parent, false);
            headHolder = new HeadHolder(convertView);
            convertView.setTag(headHolder);
        } else {
            headHolder = (HeadHolder) convertView.getTag();
        }
        //设置数据
        headHolder.headTv.setText(headList.get(position));

        return convertView;
    }


    //头部的内部类
    class HeadHolder {
        private TextView headTv;

        public HeadHolder(View itemHeadView) {

            headTv = (TextView) itemHeadView.findViewById(R.id.tvGoodsItemTitle);
        }
    }

    //内容的内部类
    class BodyHolder {
        private GridViewForScrollView gridViewForScrollView;

        public BodyHolder(View view) {
            gridViewForScrollView= (GridViewForScrollView) view.findViewById(R.id.goods_gv);
        }

//        private TextView tv1,tv2,tv3,tv4;
//
//        public BodyHolder(View itemBodyView) {
//            tv1= (TextView) itemBodyView.findViewById(R.id.tv1);
//            tv2= (TextView) itemBodyView.findViewById(R.id.tv2);
//            tv3= (TextView) itemBodyView.findViewById(R.id.tv3);
//            tv4= (TextView) itemBodyView.findViewById(R.id.tv4);
//
//
//        }
    }

    class  gridview extends  BaseAdapter{
        private  listmode listmode;

        public gridview(com.jhcms.tuangou.model.listmode listmode) {
            this.listmode = listmode;
        }

        @Override
        public int getCount() {
            return listmode.getList().size();
        }

        @Override
        public Object getItem(int position) {
            return listmode.getList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            viewholder viewholder=null;
            if(convertView==null){
                convertView=LayoutInflater.from(context).inflate(R.layout.item_sortall_grive,null);

                viewholder=new viewholder();
                viewholder.tv= (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(viewholder);
            }else {
                viewholder= (MainAdapter.viewholder) convertView.getTag();
            }
            viewholder.tv.setText(listmode.getList().get(position));
            return convertView;
        }
    }

    class  viewholder{
        private  TextView tv;
    }


}