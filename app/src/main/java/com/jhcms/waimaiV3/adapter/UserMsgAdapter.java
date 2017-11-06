package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.model.Data_WaiMai_Msg;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/6/30.
 * TODO
 */

public class UserMsgAdapter extends RecyclerView.Adapter<UserMsgAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<Data_WaiMai_Msg.ItemsEntity> data;
    private OnMsgClickListener listener;

    public UserMsgAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(layoutInflater.inflate(R.layout.adapter_msg_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).title);
        holder.tvInfo.setText(data.get(position).content);
        /*是否已读 0:未读 1:已读*/
        if (data.get(position).is_read.equals("1")) {
            holder.ivMsg.setSelected(true);
        } else {
            holder.ivMsg.setSelected(false);
        }

        holder.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onMsgClick(position);
                }
            }
        });
    }


    public interface OnMsgClickListener {
        void onMsgClick(int position);
    }

    public void setOnMsgClickListener(OnMsgClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<Data_WaiMai_Msg.ItemsEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_msg)
        ImageView ivMsg;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_info)
        TextView tvInfo;
        @Bind(R.id.ll_root)
        LinearLayout llRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
