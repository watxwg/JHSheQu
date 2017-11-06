package com.jhcms.shequ.weight;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/1.
 * TODO
 */


public class HomeAdapter extends RecyclerView.Adapter<BetterViewHolder> {

    private TypeFactory mTypeFactory;
    private List<Visitable> mVisitables;


    public HomeAdapter(TypeFactory typeFactory) {
        mTypeFactory = typeFactory;

    }


    public void setData(List<Visitable> visitables) {
        mVisitables = visitables;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mVisitables.get(position).type(mTypeFactory);
    }

    @Override
    public BetterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mTypeFactory.onCreateViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(BetterViewHolder holder, int position) {
        holder.bindItem(mVisitables.get(position));
    }

    @Override
    public int getItemCount() {
        return mVisitables == null ? 0 : mVisitables.size();
    }

}