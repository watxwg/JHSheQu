package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.MerchantEvaluationActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/5/27.
 * TODO
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyAdapter> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<String> mData = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void ItemClickListener(String type, int position);
    }

    public PhotoAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_photo_item, parent, false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(final MyAdapter holder, final int position) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.width = (Utils.getScreenW(context)-24) / 5;
        params.height = Utils.getScreenW(context) / 5;
        holder.layout.setLayoutParams(params);
        if(mData.get(position).equals("photo")){
//            holder.Nodata.setLayoutParams(params);
            holder.ivPhoto.setImageResource(R.mipmap.add_pingjia_pic);
            holder.ivClose.setVisibility(View.GONE);
            holder.Nodata.setVisibility(View.VISIBLE);
            holder.DataListview.setVisibility(View.GONE);
        }else {
//            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            params1.width = Utils.getScreenW(context) / 5;
//            params1.height = Utils.getScreenW(context) / 5;
//            holder.DataListview.setLayoutParams(params1);
            Utils.LoadStrPicture(context,mData.get(position),holder.ivPhoto);
            holder.ivClose.setVisibility(View.VISIBLE);
            holder.Nodata.setVisibility(View.GONE);
            holder.DataListview.setVisibility(View.VISIBLE);
        }
        holder.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.ItemClickListener(MerchantEvaluationActivity.DELETE, position);
                }
            }
        });
        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.ItemClickListener(MerchantEvaluationActivity.PREVIEW, position);
                }

            }
        });
        holder.Nodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.ItemClickListener(MerchantEvaluationActivity.NODATA, position);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<String> data) {
        for(int i=0;i<data.size();i++){
            if(data.get(i).equals("photo")){
                data.remove(i);
            }
        }
        if(data.size()>=0&&data.size()<5){
            this.mData = data;
            this.mData.add("photo");
        }
        notifyDataSetChanged();
    }

    public void addData(int position, String data) {
        mData.add(position, data);
        /*刷新适配器*/
        notifyItemInserted(position);

    }

    public void removeData(int position) {
        mData.remove(position);
        /*刷新数据*/
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, mData.size());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_photo)
        ImageView ivPhoto;
        @Bind(R.id.iv_close)
        ImageView ivClose;
        @Bind(R.id.ll_take_photo)
        LinearLayout Nodata;
        @Bind(R.id.dataview)
        RelativeLayout DataListview;
        @Bind(R.id.layout)
        LinearLayout layout;
        MyAdapter(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
