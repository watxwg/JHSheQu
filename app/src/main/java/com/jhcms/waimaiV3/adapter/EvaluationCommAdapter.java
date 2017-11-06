package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.model.ProductModle;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/5/25.
 * TODO
 */
public class EvaluationCommAdapter extends RecyclerView.Adapter<EvaluationCommAdapter.MyViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private Map<Integer, Boolean> comm = new HashMap<>();
    private ArrayList<ProductModle> mDatalist;
     private  boolean isevaluate=true;

    public boolean isevaluate() {
        return isevaluate;
    }

    public void setIsevaluate(boolean isevaluate) {
        this.isevaluate = isevaluate;
    }

    public void setmDatalist(ArrayList<ProductModle> mDatalist) {
        this.mDatalist = mDatalist;
    }

    public EvaluationCommAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_evacomm_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvComment.setText(mDatalist.get(position).getProduct_name());

        holder.ivGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isevaluate) {
                    comm.put(position, true);
                    holder.ivBad.setSelected(false);
                    holder.ivGood.setSelected(true);
                }
            }
        });
        holder.ivBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isevaluate) {
                    comm.put(position, false);
                    holder.ivBad.setSelected(true);
                    holder.ivGood.setSelected(false);
                }
            }
        });
        if (!isevaluate){
            if(mDatalist.get(position).getPingjia().equals("1")){
                holder.ivGood.setSelected(true);
                holder.ivBad.setSelected(false);
            }else {
                holder.ivBad.setSelected(true);
                holder.ivGood.setSelected(false);
            }
        }else {
            comm.put(position, true);
            holder.ivBad.setSelected(false);
            holder.ivGood.setSelected(true);
        }
    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    public Map<Integer, Boolean> getCommScore() {
        return comm;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_comment)
        TextView tvComment;
        @Bind(R.id.iv_good)
        ImageView ivGood;
        @Bind(R.id.iv_bad)
        ImageView ivBad;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
