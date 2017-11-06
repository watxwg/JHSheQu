package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.common.model.ShopCommentSwitch;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujiew .
 * Date 2017/5/9
 * Time 21:53
 * TODO: 店铺详情--评价--评价类型
 */
public class CommentTypeAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ShopCommentSwitch> data;
    private int selectId;

    public CommentTypeAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<ShopCommentSwitch> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_commenttype_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvType.setText(data.get(position).name + "(" + data.get(position).num + ")");
        if (position == selectId) {
            holder.tvType.setSelected(true);
        } else {
            holder.tvType.setSelected(false);
        }
        return view;
    }

    public void setPisition(int selectId) {
        this.selectId = selectId;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.tv_type)
        TextView tvType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
