
package com.jhcms.mall.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhcms.common.widget.CustomViewpager;
import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.mall.adapter.MallProuductEvaluateFrgAdapter;
import com.jhcms.waimaiV3.R;

@SuppressLint("ValidFragment")
public class MallProuductEvaluateFragment extends Fragment {
    private CustomViewpager vp;
    private MallProuductEvaluateFrgAdapter mAdapter;
    public MallProuductEvaluateFragment(CustomViewpager vp) {
        this.vp = vp;
    }
    private TextView mTvAll,mTvSatisfaction,mTvDissatisfaction,mTvhavepictures;
    private ListViewForScrollView listview;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mall_prouduct_evaluate,null);
        vp.setObjectForPosition(view,1);
        inintview(view);
        changestatu(1);
        inintevent();
        return view;
    }

    private void inintevent() {
        mTvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changestatu(1);
                mAdapter.setPositiontag(1);
                mAdapter.notifyDataSetChanged();
            }
        });
        mTvSatisfaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changestatu(2);
                mAdapter.setPositiontag(2);
                mAdapter.notifyDataSetChanged();
            }
        });

        mTvDissatisfaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changestatu(3);
                mAdapter.setPositiontag(3);
                mAdapter.notifyDataSetChanged();
            }
        });


        mTvhavepictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changestatu(4);
                mAdapter.setPositiontag(4);
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    private void inintview(View view) {
        mTvAll= (TextView) view.findViewById(R.id.textviewall);
        mTvSatisfaction= (TextView) view.findViewById(R.id.textviewSatisfaction);
        mTvDissatisfaction= (TextView) view.findViewById(R.id.textviewDissatisfaction);
        mTvhavepictures= (TextView) view.findViewById(R.id.tvhavepictures);
        listview= (ListViewForScrollView) view.findViewById(R.id.listview);
        mAdapter=new MallProuductEvaluateFrgAdapter(getContext());
        mAdapter.setPositiontag(1);
        listview.setAdapter(mAdapter);
        listview.setFocusable(false);
    }
    public  void  changestatu(int postion){
        switch (postion){
            case 1:
                mTvAll.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall_blue));
                mTvAll.setTextColor(Color.WHITE);

                mTvSatisfaction.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall));
                mTvSatisfaction.setTextColor(getContext().getResources().getColor(R.color.title_color));

                mTvDissatisfaction.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall));
                mTvDissatisfaction.setTextColor(getContext().getResources().getColor(R.color.title_color));

                mTvhavepictures.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall));
                mTvhavepictures.setTextColor(getContext().getResources().getColor(R.color.title_color));

                break;
            case 2:
                mTvSatisfaction.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall_blue));
                mTvSatisfaction.setTextColor(Color.WHITE);

                mTvAll.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall));
                mTvAll.setTextColor(getContext().getResources().getColor(R.color.title_color));

                mTvDissatisfaction.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall));
                mTvDissatisfaction.setTextColor(getContext().getResources().getColor(R.color.title_color));

                mTvhavepictures.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall));
                mTvhavepictures.setTextColor(getContext().getResources().getColor(R.color.title_color));

                break;
            case 3:
                mTvDissatisfaction.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall_blue));
                mTvDissatisfaction.setTextColor(getContext().getResources().getColor(R.color.title_color));

                mTvSatisfaction.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall));
                mTvSatisfaction.setTextColor(getContext().getResources().getColor(R.color.title_color));

                mTvAll.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall));
                mTvAll.setTextColor(getContext().getResources().getColor(R.color.title_color));

                mTvhavepictures.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall));
                mTvhavepictures.setTextColor(getContext().getResources().getColor(R.color.title_color));
                break;
            case 4:
                mTvhavepictures.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall_blue));
                mTvhavepictures.setTextColor(Color.WHITE);

                mTvSatisfaction.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall));
                mTvSatisfaction.setTextColor(getContext().getResources().getColor(R.color.title_color));

                mTvDissatisfaction.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall));
                mTvDissatisfaction.setTextColor(getContext().getResources().getColor(R.color.title_color));

                mTvAll.setBackground(getContext().getResources().getDrawable(R.drawable.bg_mall));
                mTvAll.setTextColor(getContext().getResources().getColor(R.color.title_color));

                break;
        }
    }
}
