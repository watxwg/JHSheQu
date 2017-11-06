package com.jhcms.mall.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhcms.mall.adapter.viewholder.CommonViewHolder;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/8/28 11:02
 * 描述：通用的RecyclerView的Adapter，针对只有一种的ViewType的，
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder>{
    public Context mContext;
    public LayoutInflater inflater;
    public List<T> mData;
    public int mLayoutId;
    public CommonAdapter(Context context, List<T> data, @LayoutRes int layoutId){
        mData = data;
        inflater = LayoutInflater.from(context);
        mContext = context;
        mLayoutId=layoutId;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.get(mLayoutId,mContext,parent);

    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        T t = mData.get(position);
        convertViewData(holder,t);
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public abstract void convertViewData(CommonViewHolder holder,T bean);
}
