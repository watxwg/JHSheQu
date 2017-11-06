package com.jhcms.tuangou.activity;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.waimaiV3.R;


public class TuanShopGetailsTxtActivity extends BaseActivity {
    private TextView mShopcontent,mShopbuyContent;
    private ImageView mIvBack;
    private  TextView mTitleContent;
    @Override
    protected void initView() {
        setContentView(R.layout.tuan_activity_shop_getails_txt);
    }

    @Override
    protected void initData() {
        initshopcontent();
        ininttuanshopcontent();

    }

    private void initshopcontent() {
//        • 免费提供蜡烛1包+刀1把+小盘1包
//        • 可在蛋糕上写8个字
//        • 免费提供包装
        StringBuilder   builder=new StringBuilder();
        builder.append("• 免费提供蜡烛1包+刀1把+小盘1包").append("\n").append("• 可在蛋糕上写8个字").append("\n").append(" •免费提供包装");
        mShopcontent.setText(builder.toString());


    }

    private void ininttuanshopcontent() {
        StringBuilder budilder=new StringBuilder();
        String content1 = " <font color='red'style=\"font-family: .PingFangSC-Regular,font-size: 24px,color: #001500;\n" +
                "letter-spacing: 0;\n" +
                "line-height: 60px;\">• 有效期：</font>2016.1.1 至 2016.11.28（周末、法定节假日通用）！";
        budilder.append(content1).append("<br>")
                .append("<font color='red'style=\"font-family: .PingFangSC-Regular,font-size: 24px,color: #001500;letter-spacing: 0;  line-height: 60px;\">")
                .append("• 使用时间：</font>").append("08:30-17:30,自提蛋糕时间09：00-20：00，")
                .append("<br>")
                .append("<font color='red'style=\"font-family: .PingFangSC-Regular,font-size: 24px,color: #001500;letter-spacing: 0;  line-height: 60px;\">")
                .append("• 预约电话 ：</font>").append("4008875657").append("<br>").append("<font color='red'>使用规则 ：</font>")
                .append("<br>")
                .append("<font style=\"font-family: .PingFangSC-Regular,font-size:24px,color: #001500;letter-spacing: 0;  line-height: 60px;\">")
                .append("• 提前4小时预定，拍下后请拨打4008875657预约电话</font>").append("<br>")
                .append("<font style=\"font-family: .PingFangSC-Regular,font-size: 24px,color: #001500;letter-spacing: 0;  line-height: 60px;\">")
                .append("• 团购用户不可同时享受商家其他优惠</font>");
        mShopbuyContent.setText(Html.fromHtml(budilder.toString()));

    }
    @Override
    protected void initActionBar() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuanShopGetailsTxtActivity.this.finish();
            }
        });
        mTitleContent.setText("图文详情");

    }

    @Override
    protected void initFindViewById() {
        mShopcontent= (TextView) this.findViewById(R.id.shop_content);
        mShopbuyContent= (TextView) this.findViewById(R.id.Shop_buy_content);
        View view=this.findViewById(R.id.title_layout);
        mIvBack= (ImageView) view.findViewById(R.id.iv_back);
        mTitleContent= (TextView) view.findViewById(R.id.title_content);
        mTitleContent= (TextView) view.findViewById(R.id.title_content);

    }

    @Override
    protected void initEvent() {


    }
}
