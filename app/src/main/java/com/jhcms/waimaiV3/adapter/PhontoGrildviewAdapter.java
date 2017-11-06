package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jhcms.common.model.photoModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/7/6.
 */
public class PhontoGrildviewAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<photoModel> photos;
    private OnPicItemClickListener listener;

    public PhontoGrildviewAdapter(ArrayList<photoModel> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public interface OnPicItemClickListener {
        void picItemClick(View view ,int position);
    }
    public void setOnPicItemClickListener(OnPicItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        viewholder viewholder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.waimai_phonto_grildview_item_adapter, null);
            viewholder = new viewholder(convertView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (PhontoGrildviewAdapter.viewholder) convertView.getTag();
        }
        Utils.LoadStrPicture(context, Api.IMAGE_URL + photos.get(position).getPhoto(), viewholder.imageView);


        viewholder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.picItemClick(view, position);
                }

            }
        });


        return convertView;
    }

    class viewholder {
        ImageView imageView;

        public viewholder(View view) {
            imageView = (ImageView) view.findViewById(R.id.image);
        }
    }
}
