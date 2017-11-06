package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.widget.SectionedBaseAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.model.Dish;
import com.jhcms.waimaiV3.model.DishItem;
import com.jhcms.waimaiV3.model.DishModel;
import com.jhcms.waimaiV3.model.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Wyj on 2017/4/24.
 */
public class OnSiteServiceRightAdapter extends SectionedBaseAdapter {
    ArrayList<Items> dataList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private DishModel dishModel;


    Integer[] imageViewPath = {R.mipmap.icon_xiyiji, R.mipmap.icon_qiangmian,
            R.mipmap.icon_mensuo, R.mipmap.icon_paiwu, R.mipmap.icon_weiyu};
    List imageViews = Arrays.asList(imageViewPath);
    List<Integer> images = new ArrayList(imageViews);
    private SectionItemAdapter sectionItemAdapter;


    public OnSiteServiceRightAdapter(Context context, DishModel dishModel) {
        this.context = context;
        this.dishModel = dishModel;
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < images.size(); i++) {
            Items itemModel = new Items();
            itemModel.title = "item" + i;
            itemModel.images = images.get(i);
            dataList.add(itemModel);
        }
    }

    @Override
    public DishItem getItem(int section, int position) {
        return dishModel.getRightList().get(section).getDishes().get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return dishModel.getRightList().size();
    }

    @Override
    public int getCountForSection(int section) {
        return dishModel.getRightList().get(section).getDishes().size();
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        SectionItem sectionItem = null;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.adapter_on_site_service_right_item, parent, false);
            sectionItem = new SectionItem();
            sectionItem.recycleView = (RecyclerView) convertView.findViewById(R.id.recycleView);

            convertView.setTag(sectionItem);
        } else {
            sectionItem = (SectionItem) convertView.getTag();
        }
        sectionItem.recycleView.setNestedScrollingEnabled(false);
        sectionItemAdapter = new SectionItemAdapter(context);
        sectionItem.recycleView.setAdapter(sectionItemAdapter);
        sectionItem.recycleView.setLayoutManager(new GridLayoutManager(context, 3));
        sectionItemAdapter.setDataList(dataList);
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        SectionHeaderItem sectionHeaderItem;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_on_site_service_right_header, parent, false);
            sectionHeaderItem = new SectionHeaderItem();
            sectionHeaderItem.tv_header = (TextView) convertView.findViewById(R.id.tv_header);
            convertView.setTag(sectionHeaderItem);
        } else {
            sectionHeaderItem = (SectionHeaderItem) convertView.getTag();
        }

        Dish dish = dishModel.getRightList().get(section);
        sectionHeaderItem.tv_header.setText(dish.getType());

        return convertView;
    }


    static class SectionHeaderItem {
        TextView tv_header;
    }

    static class SectionItem {
        RecyclerView recycleView;
        TextView tv_item;
        ImageView iv_image;
    }

}
