package com.jhcms.shequ.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.model.BankModel;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/9/8.
 */
public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.ViewHolder> {
    private Context context;
    private onItemClickListent mOnItemClickListent;
    private ArrayList<BankModel>  mdatalist;

    public BankListAdapter(Context context, ArrayList<BankModel> mdatalist) {
        this.context = context;
        this.mdatalist = mdatalist;
    }

    public void setmOnItemClickListent(onItemClickListent mOnItemClickListent) {
        this.mOnItemClickListent = mOnItemClickListent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bank_activity_list_layout_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        mOnItemClickListent.onitemClickListent(position);
        switch (mdatalist.get(position).getCard_type()){
            case "1":
                holder.IvBank.setImageResource(R.mipmap.card_visa);
                break;
            case "2":
                holder.IvBank.setImageResource(R.mipmap.card_master);
                break;
            case "3":
                holder.IvBank.setImageResource(R.mipmap.card_ae);
                break;

        }
        holder.TvBankName.setText(mdatalist.get(position).getCard_name());
        holder.TvBankNumber.setText(mdatalist.get(position).getCard_label());
        holder.mLinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListent.onitemClickListent(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdatalist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.Iv_Bank)
        ImageView IvBank;
        @Bind(R.id.Tv_BankName)
        TextView TvBankName;
        @Bind(R.id.Tv_bankNumber)
        TextView TvBankNumber;
        @Bind(R.id.layout_card_item)
        LinearLayout mLinearlayout;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onItemClickListent{
        void onitemClickListent(int postion);
    }
}
