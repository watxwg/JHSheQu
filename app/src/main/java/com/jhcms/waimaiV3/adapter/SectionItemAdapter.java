package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RecycleViewBaseAdapter;
import com.jhcms.common.widget.SuperViewHolder;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.model.Items;

/**
 * Created by Wyj on 2017/4/24.
 */
public class SectionItemAdapter extends RecycleViewBaseAdapter<Items> {

    private ImageView ivImageView;
    private TextView tvTitle;
    private LinearLayout root;


    public SectionItemAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_section_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {

        Items items = mDataList.get(position);
        root = holder.getView(R.id.ll_root);
        ivImageView = holder.getView(R.id.iv_imageView);
        tvTitle = holder.getView(R.id.tv_title);
        Utils.LoadPicture(mContext, items.images, ivImageView);
        tvTitle.setText(items.title);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.cilck(v,position);
                }
            }
        });

    }

}
