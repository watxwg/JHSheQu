
package com.jhcms.tuangou.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jhcms.common.widget.CustomViewpager;
import com.jhcms.common.widget.MyListView;
import com.jhcms.waimaiV3.R;
import com.jhcms.tuangou.adapter.ParentAdapter;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class FrgTuanShops extends Fragment {
    private MyListView mShopsListview;
    private ParentAdapter mparenApapter;
    private ArrayList<String> list=new ArrayList<>();
    private CustomViewpager vp;
    private   View view;
    private  LayoutInflater inflater;
    private  ViewGroup container;
    Bundle savedInstanceState;
    private ImageView image;
    public FrgTuanShops(CustomViewpager vp) {
        this.vp = vp;
    }
    private  ArrayList<String>list1=null;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view  =inflater.inflate(R.layout.tuan_fragment_frg_shops,null);
        vp.setObjectForPosition(view,1);
        inintview(view);
        inintData();
        inintEvent();
        this.inflater=inflater;
        this.container=container;
        this.savedInstanceState=savedInstanceState;
        return view;
    }

    private void inintEvent() {
    }

    private void inintData() {
        for (int i=0;i<1;i++){
            list.add("item"+i);
        }
    }

    private void inintview(View view) {
    }
}
