package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.GuideAdapter;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuideActivity extends AppCompatActivity {

    @Bind(R.id.vp_guide)
    ViewPager vpGuide;
    private static final int[] mImages = new int[]{R.mipmap.guideone,
            R.mipmap.guidetwo, R.mipmap.guidethree, R.mipmap.guidefour};
    private ArrayList<ImageView> mImageViewList;
    private GuideAdapter guideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Hawk.put("isFirst", false);
        mImageViewList = new ArrayList<ImageView>();
        // 初始化引导页的4个页面
        for (int i = 0; i < mImages.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(mImages[i]);// 设置引导页背景
            mImageViewList.add(image);
        }
        guideAdapter = new GuideAdapter(mImageViewList);
        vpGuide.setAdapter(guideAdapter);
        View view = mImageViewList.get(mImages.length - 1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isFastDoubleClick()) {
                    Intent i = new Intent(GuideActivity.this, WaiMaiMainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
