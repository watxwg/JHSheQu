package com.jhcms.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/5/6.
 */
public class MallShopCartSonAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> mdatalist;
    private Boolean IsAllCheck = false;//是不是全选
    private groupischeck mGroupischeck;
    private int mScreentWidth;
    private int count = 0;//选中个数

    public void setmGroupischeck(groupischeck mGroupischeck) {
        this.mGroupischeck = mGroupischeck;
    }

    public void setAllCheck(Boolean allCheck) {
        IsAllCheck = allCheck;
    }

    public MallShopCartSonAdapter(Context context, ArrayList<String> mdatalist, int screenWidth) {
        this.context = context;
        this.mdatalist = mdatalist;
        this.mScreentWidth = screenWidth;
    }

    private DeleteSonItemListener deleteSonItemListener;

    public void setDeleteSonItemListener(DeleteSonItemListener deleteSonItemListener) {
        this.deleteSonItemListener = deleteSonItemListener;
    }

    @Override
    public int getCount() {
        return mdatalist.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.mall_item_shopcart_prouct, null);
            childHolder = new ChildHolder();
            childHolder.cb_check = (CheckBox) convertView.findViewById(R.id.check_box);
            childHolder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_intro);
            childHolder.iv_decrease = (TextView) convertView.findViewById(R.id.desc);
            childHolder.tv_price = (TextView) convertView.findViewById(R.id.price);
            childHolder.mDeleteView = (TextView) convertView.findViewById(R.id.delete);
            childHolder.tv_count = (TextView) convertView.findViewById(R.id.shopcount);
            childHolder.mShopimage = (ImageView) convertView.findViewById(R.id.iv_adapter_list_pic);
            childHolder.content = convertView.findViewById(R.id.ll_content);
            childHolder.action = convertView.findViewById(R.id.ll_action);
            childHolder.hSView = (HorizontalScrollView) convertView.findViewById(R.id.hsv);
            ViewGroup.LayoutParams lp = childHolder.content.getLayoutParams();
            lp.width = mScreentWidth;
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        childHolder.mDeleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSonItemListener.DeleteSonItem(mdatalist.get(position));
            }
        });
        if (IsAllCheck) {
            childHolder.cb_check.setChecked(true);
        } else {
            childHolder.cb_check.setChecked(false);
        }

        final ChildHolder finalChildHolder = childHolder;
        childHolder.cb_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalChildHolder.cb_check.isChecked()) {
                    count++;
                    if (count == mdatalist.size()) {
                        mGroupischeck.ischeck(true);
                    } else {
                        mGroupischeck.ischeck(false);
                    }
                } else {
                    count--;
                    if (count < 0) {
                        finalChildHolder.count = 0;
                    } else {
                        finalChildHolder.count = count;
                    }

                    if (count == mdatalist.size()) {
                        mGroupischeck.ischeck(true);
                    } else {
                        mGroupischeck.ischeck(false);
                    }
                }
            }
        });
        return convertView;
    }

    /**
     * 子元素绑定器
     */
    private class ChildHolder {
        CheckBox cb_check;
        TextView tv_product_name;
        TextView tv_price;
        ImageView mShopimage;
        TextView tv_count;
        TextView iv_decrease;
        View content;
        TextView mDeleteView;
        HorizontalScrollView hSView;
        View action;
        int count;
    }

    public interface DeleteSonItemListener {
        void DeleteSonItem(String value);
    }

    public interface groupischeck {
        void ischeck(boolean falg);
    }
}
