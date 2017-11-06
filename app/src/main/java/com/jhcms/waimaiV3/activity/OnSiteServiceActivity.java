package com.jhcms.waimaiV3.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.jhcms.common.widget.RecycleViewBaseAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.OnSiteServiceAdapter;
import com.jhcms.waimaiV3.model.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by Wyj on 2017/5/9
 * TODO:
 */
public class OnSiteServiceActivity extends SwipeBaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.banner)
    ConvenientBanner banner;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.faButton)
    FloatingActionButton faButton;
    private OnSiteServiceAdapter adapter;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_on_site_service);
        ButterKnife.bind(this);
    }
    @Override
    protected void initData() {
        tvTitle.setText("上门服务");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*轮播图*/
        String[] urls = getResources().getStringArray(R.array.url);
        List list = Arrays.asList(urls);
        List<String> banners = new ArrayList(list);
        banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, banners)
                .startTurning(2000)
                .setPageIndicator(new int[]{R.mipmap.icon_banner_normal, R.mipmap.icon_banner_choose})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                        if (bannerData.get(position).link.startsWith("http")) {
//                            Intent intent = new Intent();
//                            intent.setClass(getActivity(), TuanActivity.class);
//                            intent.putExtra("url", bannerData.get(position).link);
//                            startActivity(intent);
//                        }
                        Toast.makeText(OnSiteServiceActivity.this, position + "", Toast.LENGTH_SHORT).show();
                    }
                });

        ArrayList<Items> dataList = new ArrayList<>();
        Integer[] imageViewPath = {R.mipmap.icon_baojie, R.mipmap.icon_chuanglian, R.mipmap.icon_kongtiao,
                R.mipmap.icon_shafa, R.mipmap.icon_xiyiji, R.mipmap.icon_qiangmian,
                R.mipmap.icon_mensuo, R.mipmap.icon_paiwu, R.mipmap.icon_weiyu};
        List imageViews = Arrays.asList(imageViewPath);
        List<Integer> images = new ArrayList(imageViews);
        final String[] title = getResources().getStringArray(R.array.shangmenfuwu);
        for (int i = 0; i < images.size(); i++) {
            Items itemModel = new Items();
            itemModel.title = title[i];
            itemModel.images = images.get(i);
            dataList.add(itemModel);
        }

        /*列表*/
        adapter = new OnSiteServiceAdapter(this);
        recycleView.setFocusable(false);
        recycleView.setAdapter(adapter);
        adapter.setDataList(dataList);
        recycleView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter.setOnClickListener(new RecycleViewBaseAdapter.OnClickListener() {
            @Override
            public void cilck(View view, int position) {
                startActivity(new Intent(OnSiteServiceActivity.this,OnSiteServiceListActivity.class));
                Toast.makeText(OnSiteServiceActivity.this, title[position], Toast.LENGTH_SHORT).show();
            }
        });
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OnSiteServiceActivity.this, "订单", Toast.LENGTH_SHORT).show();
            }
        });



    }


    public class LocalImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(context).load(data).into(imageView);
        }
    }
}
