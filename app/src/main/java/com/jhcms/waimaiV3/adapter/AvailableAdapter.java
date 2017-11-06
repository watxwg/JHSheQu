package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.waimaiV3.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

/**
 * Created by Wyj on 2017/4/19.
 */
public class AvailableAdapter extends RecyclerView.Adapter<AvailableAdapter.AvaiableViewHolder> {
    private int type;
    private Context context;
    private LayoutInflater inflater;

    public AvailableAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AvaiableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_avaiable_item, parent, false);
        return new AvaiableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AvaiableViewHolder holder, final int position) {
        //TODO 不可用
        if (type == 1) {
            Glide.with(context)
                    .load(R.mipmap.pic_pro_big)
                    .bitmapTransform(new GrayscaleTransformation(context))
                    .into(holder.ivQuanLogo);
            holder.tvQuanTitle.setTextColor(context.getResources().getColor(R.color.third_txt_color));
            holder.tvQuanPrice.setTextColor(context.getResources().getColor(R.color.third_txt_color));
            holder.btQuan.setClickable(false);
            holder.btQuan.setBackgroundResource(R.drawable.shap_bg_line_radius);
        } else {
            //TODO 可使用
            holder.btQuan.setClickable(true);
            holder.btQuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "正在使用"+position, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class AvaiableViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_quan_logo)
        RoundImageView ivQuanLogo;
        @Bind(R.id.tv_quan_title)
        TextView tvQuanTitle;
        @Bind(R.id.tv_quan_price)
        TextView tvQuanPrice;
        @Bind(R.id.tv_quan_manjian)
        TextView tvQuanManjian;
        @Bind(R.id.tv_quan_time)
        TextView tvQuanTime;
        @Bind(R.id.bt_quan)
        Button btQuan;

        public AvaiableViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
