package com.jhcms.waimaiV3.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.jhcms.common.model.ShopDetail;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Wyj on 2017/5/9
 * TODO:商家公告
 */
public class WaiMaiShopAnnouncementActivity extends Activity {
    public static String SHOP_DETAIL = "SHOP_DETAIL";

    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.tv_shop_status)
    SuperTextView tvShopStatus;
    @Bind(R.id.rb_service)
    RatingBar rbService;
    @Bind(R.id.all_youhui)
    LinearLayout allYouhui;
    @Bind(R.id.tv_announcment)
    TextView tvAnnouncment;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    private ShopDetail shopDetail;
    private List<ShopDetail.HuodongEntity> huodong;
    private SuperTextView tvYouhuiWord;
    private TextView tvYouhuiTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waimai_shop_announcement);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.alpha_out, R.anim.alpha_in);
    }

    private void initData() {
        shopDetail = (ShopDetail) getIntent().getSerializableExtra(SHOP_DETAIL);
        /*店铺名字*/
        tvShopName.setText(shopDetail.title);
        if (shopDetail.yy_status.equals("1") && shopDetail.yysj_status.equals("1")) {
            tvShopStatus.setText(R.string.营业中);
            tvShopStatus.setSolid(getResources().getColor(R.color.themColor));
        } else {
            tvShopStatus.setText(R.string.打烊);
            tvShopStatus.setSolid(getResources().getColor(R.color.third_txt_color));
        }
        /*分数*/
        rbService.setRating(Float.parseFloat(shopDetail.avg_score));
        /*公告*/
        tvAnnouncment.setText(TextUtils.isEmpty(shopDetail.delcare) ? "暂无公告" : shopDetail.delcare);
        huodong = shopDetail.huodong;
        allYouhui.removeAllViews();
        if (huodong.size() > 0) {
            for (int i = 0; i < huodong.size(); i++) {
                LinearLayout firstLl = new LinearLayout(this);
                View view = LayoutInflater.from(this).inflate(R.layout.youhuiquan, firstLl, false);
                tvYouhuiWord = (SuperTextView) view.findViewById(R.id.tv_youhui_word);
                tvYouhuiTitle = (TextView) view.findViewById(R.id.tv_youhui_title);
                tvYouhuiWord.setText(huodong.get(i).word);
                tvYouhuiWord.setSolid(Color.parseColor("#" + huodong.get(i).color));
                tvYouhuiTitle.setText(huodong.get(i).title);

                firstLl.addView(view);
                allYouhui.addView(firstLl);
            }
        }
    }

    @OnClick(R.id.iv_close)
    public void onClick() {
        finish();
    }
}
