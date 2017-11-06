package com.jhcms.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhcms.common.utils.Utils;
import com.jhcms.mall.model.OrderStatus;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/5/18.
 */
public class MineGrildAdpter extends BaseAdapter {
    private Context context;
    private ArrayList<OrderStatus> data;
    private OnItemClickListener listener;

    public MineGrildAdpter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mall_mine_gridview_adaper, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = Utils.getScreenW(context) / 5;
        params.height = params.width * 15 / 17;
        viewholder.rlRoot.setLayoutParams(params);

        viewholder.mMytitle.setText(context.getText(data.get(position).title));
        viewholder.mMyImageview.setBackgroundResource(data.get(position).getImageResourceId(context));
        if (data.get(position).count == 0) {
            viewholder.mynumber.setVisibility(View.GONE);
        } else {
            viewholder.mynumber.setVisibility(View.VISIBLE);
            viewholder.mynumber.setText(String.valueOf(data.get(position).count));
        }
        viewholder.rlRoot.setOnClickListener(v -> {
            if (null != listener) {
                listener.onItemClick(position + 1);
            }
        });
        return convertView;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(ArrayList<OrderStatus> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder {
        private ImageView mMyImageview;
        private TextView mMytitle;
        private TextView mynumber;
        private RelativeLayout rlRoot;

        public ViewHolder(View view) {
            mMyImageview = (ImageView) view.findViewById(R.id.myimage_iv);
            mMytitle = (TextView) view.findViewById(R.id.mytitle_tv);
            mynumber = (TextView) view.findViewById(R.id.mynumber_tv);
            rlRoot = (RelativeLayout) view.findViewById(R.id.rl_root);
        }
    }
}
