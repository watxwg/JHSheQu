package com.jhcms.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.common.widget.ListViewForListView;
import com.jhcms.mall.activity.MallHotShopSellerRankingActivity;
import com.jhcms.waimaiV3.R;

/**
 * Created by admin on 2017/5/2.
 */
public class TypeRightParentAdpter extends BaseAdapter {
    private Context context;
    private  int mposition;

    public int getMposition() {
        return mposition;
    }

    public void setMposition(int mposition) {
        this.mposition = mposition;
    }

    public TypeRightParentAdpter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
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
        if(mposition==0) {
            viewholdel viewholdel = null;
            if (convertView == null) {
                viewholdel = new viewholdel();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_mall_frgtype_rightparent, null);
                viewholdel.myListView = (ListViewForListView) convertView.findViewById(R.id.rightparent_lv);
                viewholdel.hotview = convertView.findViewById(R.id.hotview);
                viewholdel.hottitle = (TextView) viewholdel.hotview.findViewById(R.id.hotTitle_tv);
                viewholdel.LoadMore = (TextView) viewholdel.hotview.findViewById(R.id.LoadMore);
                convertView.setTag(viewholdel);
            } else {
                viewholdel = (TypeRightParentAdpter.viewholdel) convertView.getTag();
            }
            if (position == 0) {
                viewholdel.hottitle.setText("热销商品排行");
                TypeRightSonAdpter typeRightSonAdpter = new TypeRightSonAdpter(context, true);
                viewholdel.myListView.setAdapter(typeRightSonAdpter);
                viewholdel.LoadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MallHotShopSellerRankingActivity.class);
                        context.startActivity(intent);
                    }
                });

            } else if (position == 1) {
                viewholdel.hottitle.setText("TOP店铺排行");
                TypeRightSonAdpter typeRightSonAdpter = new TypeRightSonAdpter(context, false);
                viewholdel.myListView.setAdapter(typeRightSonAdpter);
                viewholdel.LoadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            } else {
                TypeRightSonAdpter typeRightSonAdpter = new TypeRightSonAdpter(context, false);
                viewholdel.myListView.setAdapter(typeRightSonAdpter);
            }

            return convertView;
        }else {
            viewHoldeltwo viewHoldeltwo=null;
            if(convertView==null){
                convertView=LayoutInflater.from(context).inflate(R.layout.item_mall_frgtype_typerightparenadapter_two,null);
                viewHoldeltwo=new viewHoldeltwo();
                viewHoldeltwo.headview= (TextView) convertView.findViewById(R.id.headview_tv);
                viewHoldeltwo.grildview= (GridViewForScrollView) convertView.findViewById(R.id.girldeview);
                convertView.setTag(viewHoldeltwo);
            }else {
                viewHoldeltwo= (TypeRightParentAdpter.viewHoldeltwo) convertView.getTag();
            }
            viewHoldeltwo.headview.setText("item"+position);
            TypeRightParentItemTwoAdapter typeRightParentItemTwoAdapter=new TypeRightParentItemTwoAdapter(context);
            viewHoldeltwo.grildview.setAdapter(typeRightParentItemTwoAdapter);
            return  convertView;
        }
    }
    public class  viewholdel{
        public ListViewForListView myListView;
        private  View hotview;
        private TextView hottitle,LoadMore;
    }
    class  viewHoldeltwo{
        private TextView headview;
        private GridViewForScrollView grildview;
    }
}
