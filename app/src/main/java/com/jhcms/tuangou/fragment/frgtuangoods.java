package com.jhcms.tuangou.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.utils.CommonAdapter;
import com.jhcms.common.utils.ViewHolder;
import com.jhcms.common.widget.CustomViewpager;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.common.widget.ListViewForListView;
import com.jhcms.common.widget.MyListView;
import com.jhcms.tuangou.activity.TuanProductDetailsActivity;
import com.jhcms.waimaiV3.R;
import com.jhcms.tuangou.adapter.SortAllApter;
import com.jhcms.tuangou.adapter.SortSonAdapter;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
class Frgtuangoods extends Fragment {
    private GridViewForScrollView mGridview;
    private ArrayList<String> listsort=new ArrayList<>();
    private ImageView mSingle_Iv;
    boolean flagSingle=false;
    private ListViewForListView mListview;
    private LinearLayout mlinearlayout;
    private LinearLayout Sort_linlayoutl, Arealinelayout,orderlayout;
    private TextView SortTextView,AreaTextview,ordertv;
    private ImageView SortIv,AreaIv,orderIv;
    private CustomViewpager vp;
    private SortAllApter mSortAllapter=null;
    private PopupWindow mSortpopupWimdow;
    private  LinearLayout mSelectedllayout;
    private  int    mCurrentPos=0;
    private SortSonAdapter mSortSonAdapter;
    private  PopupWindow oderPopupwindow;
    ArrayList<String> list=new ArrayList<>();
    public Frgtuangoods(CustomViewpager vp) {
        this.vp = vp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tuan_fragment_frgtuangoods,null);
        vp.setObjectForPosition(view,0);
        inintview(view);
        inintData();
        inintSelection();
        inintSortpopw();
        inintoderPopupwindow();
        return view;
    }

    private void inintoderPopupwindow() {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.tuna_frggoods_oder,null);
        final MyListView oderlistview= (MyListView) view.findViewById(R.id.oder_lv);
        final ArrayList<String> list=new ArrayList<>();
        oderPopupwindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        oderPopupwindow.setContentView(view);
        oderPopupwindow.setOutsideTouchable(true);
        oderPopupwindow.setBackgroundDrawable(new BitmapDrawable());
        oderPopupwindow.setFocusable(true);// 获取焦点
        oderPopupwindow.setClippingEnabled(false);
        list.add("智能排序");
        list.add("距离最近");
        list.add("好评优先");
        list.add("人均最低");
        SortSonAdapter mOderAdapter=new SortSonAdapter(getContext(),list,0);
        oderlistview.setAdapter(mOderAdapter);
        oderlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SortSonAdapter mOderAdapter=new SortSonAdapter(getContext(),list,position);
                oderlistview.setAdapter(mOderAdapter);
                Toast.makeText(getContext(),list.get(position),Toast.LENGTH_LONG).show();
                if(oderPopupwindow!=null&&oderPopupwindow.isShowing()){
                    oderPopupwindow.dismiss();
                }
                upstatus(null);
            }
        });
        if(!oderPopupwindow.isShowing()){
            upstatus(null);
        }



    }

    private void inintSortpopw() {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.tuan_frggoods_sort,null);
        final MyListView mSortAlllv= (MyListView) view.findViewById(R.id.sortall_lv);
        for (int i=0;i<11;i++){
            listsort.add("item"+i);
        }
        final  MyListView mSortsonListview= (MyListView) view.findViewById(R.id.sonSort_lv);
        mSortSonAdapter=new SortSonAdapter(getContext(),listsort,0);


        mSortAllapter=new SortAllApter(mCurrentPos,getActivity(),listsort);
        mSortAlllv.setAdapter(mSortAllapter);
        mSortAlllv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentPos=position;
                mSortAllapter=new SortAllApter(mCurrentPos,getContext(),listsort);
                mSortAlllv.setAdapter(mSortAllapter);
            }
        });
        mSortsonListview.setAdapter(mSortSonAdapter);
        mSortsonListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSortSonAdapter=new SortSonAdapter(getContext(),listsort,position);
                mSortsonListview.setAdapter(mSortSonAdapter);
                if(mSortpopupWimdow!=null&&mSortpopupWimdow.isShowing()){
                    mSortpopupWimdow.dismiss();
                    upstatus(null);
                    Toast.makeText(getContext(),position+"",Toast.LENGTH_LONG).show();
                }
            }
        });
        mSortpopupWimdow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        mSortpopupWimdow.setContentView(view);
        mSortpopupWimdow.setOutsideTouchable(true);
        mSortpopupWimdow.setBackgroundDrawable(new BitmapDrawable());
        mSortpopupWimdow.setFocusable(true);// 获取焦点
        if(!mSortpopupWimdow.isShowing()){
            upstatus(null);
        }

    }

    private void inintSelection() {
        //单双选择
        mSingle_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upstatus(null);
                if(!flagSingle){
                    flagSingle=true;
                    mSingle_Iv.setImageResource(R.mipmap.tuan_btn_switch_dan);

                }else {
                    flagSingle=false;
                    mSingle_Iv.setImageResource(R.mipmap.tuan_btn_switch_two_yes);
                }

                if(flagSingle){
                    mGridview.setVisibility(View.GONE);
                    mListview.setVisibility(View.VISIBLE);
                }else {
                    mGridview.setVisibility(View.VISIBLE);
                    mListview.setVisibility(View.GONE);
                }
            }
        });
        inintselected();

    }

    private void inintselected() {
        Sort_linlayoutl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upstatus(SortTextView);
//                if(mSortpopupWimdow!=null&&!mSortpopupWimdow.isShowing()){
                    mSortpopupWimdow.showAsDropDown(mSelectedllayout);
//                }
            }
        });
        Arealinelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upstatus(AreaTextview);
                mSortpopupWimdow.showAsDropDown(mSelectedllayout);
            }
        });

        orderlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upstatus(ordertv);
                if(oderPopupwindow!=null&&!oderPopupwindow.isShowing()){
                    oderPopupwindow.showAsDropDown(mSelectedllayout);
                }

                
            }
        });

    }

    private void inintData() {
        for (int i=0;i<10;i++){
            list.add("item"+i);
        }
        mGridview.setAdapter(new CommonAdapter<String>(getContext(),list,R.layout.item_tuan_frg_gridview) {
            @Override
            public void convert(ViewHolder helper, String item) {

            }
        });
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), TuanProductDetailsActivity.class);
                getContext().startActivity(intent);
            }
        });
        mListview.setAdapter(new CommonAdapter<String>(getContext(),list,R.layout.item_tuan_frgment_listview) {
            @Override
            public void convert(ViewHolder helper, String item) {

            }
        });
    }

    private void inintview(View view) {
        mGridview= (GridViewForScrollView) view.findViewById(R.id.goods_gv);
        mSingle_Iv= (ImageView) view.findViewById(R.id.single_Iv);
        mListview= (ListViewForListView) view.findViewById(R.id.goods_lv);
        mlinearlayout= (LinearLayout) view.findViewById(R.id.linerlayout);
        Sort_linlayoutl= (LinearLayout) view.findViewById(R.id.Sort_linlayout);
        Arealinelayout= (LinearLayout) view.findViewById(R.id.Aredlayout);
        orderlayout= (LinearLayout) view.findViewById(R.id.orderlayout);
        SortTextView= (TextView) view.findViewById(R.id.sorttv);
        SortIv= (ImageView) view.findViewById(R.id.sortiv);
        AreaTextview= (TextView) view.findViewById(R.id.Areatv);
        AreaIv= (ImageView) view.findViewById(R.id.Areaiv);
        ordertv= (TextView) view.findViewById(R.id.order_tv);
        orderIv= (ImageView) view.findViewById(R.id.oder_iv);
        mSelectedllayout= (LinearLayout) view.findViewById(R.id.selected_llayout);

    }
    public  void  upstatus(TextView tv){
        if (tv!=null) {
            switch (tv.getId()) {
                case R.id.sorttv:
                    SortTextView.setTextColor(Color.parseColor("#20AD20"));
                    SortIv.setImageResource(R.mipmap.icon_black_arrow_up);
                    AreaTextview.setTextColor(Color.parseColor("#333333"));
                    AreaIv.setImageResource(R.mipmap.icon_black_arrow_down);
                    ordertv.setTextColor(Color.parseColor("#333333"));
                    orderIv.setImageResource(R.mipmap.icon_black_arrow_down);
                    break;
                case R.id.Areatv:
                    AreaTextview.setTextColor(Color.parseColor("#20AD20"));
                    AreaIv.setImageResource(R.mipmap.icon_black_arrow_up);
                    SortTextView.setTextColor(Color.parseColor("#333333"));
                    SortIv.setImageResource(R.mipmap.icon_black_arrow_down);
                    ordertv.setTextColor(Color.parseColor("#333333"));
                    orderIv.setImageResource(R.mipmap.icon_black_arrow_down);
                    break;
                case R.id.order_tv:
                    ordertv.setTextColor(Color.parseColor("#20AD20"));
                    orderIv.setImageResource(R.mipmap.icon_black_arrow_up);
                    AreaTextview.setTextColor(Color.parseColor("#333333"));
                    AreaIv.setImageResource(R.mipmap.icon_black_arrow_down);
                    SortTextView.setTextColor(Color.parseColor("#333333"));
                    SortIv.setImageResource(R.mipmap.icon_black_arrow_down);
                    break;
            }
        }else {
            ordertv.setTextColor(Color.parseColor("#333333"));
            orderIv.setImageResource(R.mipmap.icon_black_arrow_down);
            AreaTextview.setTextColor(Color.parseColor("#333333"));
            AreaIv.setImageResource(R.mipmap.icon_black_arrow_down);
            SortTextView.setTextColor(Color.parseColor("#333333"));
            SortIv.setImageResource(R.mipmap.icon_black_arrow_down);
        }

    }



}
