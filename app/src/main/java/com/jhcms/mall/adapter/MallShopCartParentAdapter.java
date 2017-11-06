package com.jhcms.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by admin on 2017/5/6.
 */
public class MallShopCartParentAdapter extends BaseAdapter {
    private Context context;
    private Map<String,ArrayList<String>> children;
    private  ArrayList<String> groups;

    public MallShopCartParentAdapter(ArrayList<String> groups, Context context, Map<String, ArrayList<String>> children) {
        this.context = context;
        this.children = children;
        this.groups=groups;
    }

    @Override
    public int getCount() {
        return groups.size();
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
        GroupHolder groupHolder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.mall_frgshopcar_groupview,null);
            groupHolder=new GroupHolder();
            groupHolder.cb_check = (CheckBox) convertView.findViewById(R.id.determine_chekbox);
            groupHolder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_source_name);
            groupHolder.mShopdelete= (ImageView) convertView.findViewById(R.id.shopdelete);
            groupHolder.MsonListview= (ListViewForScrollView) convertView.findViewById(R.id.sonlistview);
            groupHolder.mHeadview= (LinearLayout) convertView.findViewById(R.id.headview);
            convertView.setTag(groupHolder);
        }else {
            groupHolder= (GroupHolder) convertView.getTag();
        }
        final ArrayList<String> mDataList=children.get(groups.get(position));
        if(mDataList.size()>0){
            groupHolder.mHeadview.setVisibility(View.VISIBLE);
        }else {
            groupHolder.mHeadview.setVisibility(View.GONE);
        }
        final MallShopCartSonAdapter mAdapter=new MallShopCartSonAdapter(context,mDataList, Utils.getScreenW(context));
        groupHolder.MsonListview.setAdapter(mAdapter);
        groupHolder.MsonListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,"ok",Toast.LENGTH_LONG).show();
            }
        });
        mAdapter.setDeleteSonItemListener(new MallShopCartSonAdapter.DeleteSonItemListener() {//Todo 侧滑删除

            @Override
            public void DeleteSonItem(String value) {
                mDataList.remove(value);
                MallShopCartParentAdapter.this.notifyDataSetChanged();
                if(mDataList.size()==0){
                    children.remove(groups.get(position));
                    groups.remove(position);
                    MallShopCartParentAdapter.this.notifyDataSetChanged();
                }
            }
        });
        final GroupHolder finalGroupHolder = groupHolder;
        groupHolder.cb_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalGroupHolder.cb_check.isChecked()){
                    mAdapter.setAllCheck(true);
                    finalGroupHolder.setCount(mDataList.size());
                }else {
                    mAdapter.setAllCheck(false);
                    finalGroupHolder.setCount(mDataList.size());
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        groupHolder.mShopdelete.setOnClickListener(new View.OnClickListener() {//群组删除
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"已删除",Toast.LENGTH_LONG).show();
                MallShopCartParentAdapter.this.notifyDataSetChanged();
                children.remove(groups.get(position));
                groups.remove(position);
                MallShopCartParentAdapter.this.notifyDataSetChanged();
            }
        });
        mAdapter.setmGroupischeck(new MallShopCartSonAdapter.groupischeck() {
            @Override
            public void ischeck(boolean falg) {
                finalGroupHolder.cb_check.setChecked(falg);
            }
        });

        return convertView;
    }

    /**
     * 组元素绑定器
     *
     *
     */
    private class GroupHolder
    {
        CheckBox cb_check;
        TextView tv_group_name;
        ImageView mShopdelete;
        private ListViewForScrollView MsonListview;
        private LinearLayout mHeadview;
        private  int count=0;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
