package com.jhcms.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/5/27.
 */
public class MallRefundGridViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
    private  ClearImage clearImage;

    public void setClearImage(ClearImage clearImage) {
        this.clearImage = clearImage;
    }

    public MallRefundGridViewAdapter(Context context, ArrayList<String> Imagelist) {
        this.context = context;
        for(int i=0;i<Imagelist.size();i++){
            if(Imagelist.get(i).equals("photo")){
                Imagelist.remove(i);
            }
        }
        if(Imagelist.size()>=0&&Imagelist.size()<4){
            this.list = Imagelist;
            this.list.add("photo");
        }
        this.list=Imagelist;
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
        ViewHodel mViewHoldel=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.mall_refund_gridview_adapter_layout,null);
                mViewHoldel=new ViewHodel(convertView);
            convertView.setTag(mViewHoldel);
        }else {
            mViewHoldel= (ViewHodel) convertView.getTag();
        }
        if(list.get(position)=="photo"){
            Glide.with(context).load(R.mipmap.mall_my_btn_addpic)
                    .into(mViewHoldel.ImagePic);
            mViewHoldel.mFrameLayout.setVisibility(View.GONE);
        }else {
            //        glide
            Glide.with(context).load(list.get(position))
                    .into(mViewHoldel.ImagePic);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).equals("photo")){//调相机

                }
            }
        });

        mViewHoldel.ImageViewColse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImage.cleaImage(list.get(position));
            }
        });

        return convertView;
    }

    class ViewHodel{
        private ImageView ImagePic;
        private  ImageView ImageViewColse;
        private FrameLayout mFrameLayout;

        public  ViewHodel(View view){
            ImagePic= (ImageView) view.findViewById(R.id.ImagePic);
            ImageViewColse= (ImageView) view.findViewById(R.id.ImageColse);
            mFrameLayout= (FrameLayout) view.findViewById(R.id.ali_frame);
        }


    }

    public  interface  ClearImage{
        void  cleaImage(String uri);
    }
}
