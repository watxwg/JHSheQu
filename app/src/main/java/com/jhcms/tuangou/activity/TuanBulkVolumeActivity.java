package com.jhcms.tuangou.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.waimaiV3.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 团购卷
 */
public class TuanBulkVolumeActivity extends BaseActivity {
    @Bind(R.id.shopname)
    TextView shopname;
    @Bind(R.id.shopcount)
    TextView shopcount;
    @Bind(R.id.shopImage)
    ImageView shopImage;
    @Bind(R.id.shopStatue)
    TextView shopStatue;
    @Bind(R.id.ticket_number)
    TextView ticketNumber;
    @Bind(R.id.linearlayout_image)
    LinearLayout linearlayoutImage;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private View mHeadview;
    String ticket_number;
    String ShopCount;
    String Ticket_status;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_bulk_volume);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initnTent();
        bindViewData();

    }

    private void bindViewData() {
        ticketNumber.setText(ticket_number);
        shopcount.setText(ShopCount);
        Bitmap bitmap = CodeUtils.createImage(ticket_number, 240, 240, null);
        shopImage.setImageBitmap(bitmap);

        if (Ticket_status.equals("0")) {
            shopStatue.setText("团购劵未核销");
            linearlayoutImage.setBackground(TuanBulkVolumeActivity.this.getResources().getDrawable(R.drawable.shap_bg_line_blue_radius));
            shopStatue.setBackground(TuanBulkVolumeActivity.this.getResources().getDrawable(R.drawable.shap_bg_line_blue_radius));
            shopStatue.setTextColor(TuanBulkVolumeActivity.this.getResources().getColor(R.color.blue));
        } else if(Ticket_status.equals("1")){
            shopStatue.setText("团购劵已核销");
            shopImage.setAlpha(255 / 2);
            linearlayoutImage.setBackground(TuanBulkVolumeActivity.this.getResources().getDrawable(R.drawable.shap_bg_white_with_gray_boder));
            shopStatue.setBackground(TuanBulkVolumeActivity.this.getResources().getDrawable(R.drawable.shap_bg_white_with_gray_boder));
            shopStatue.setTextColor(TuanBulkVolumeActivity.this.getResources().getColor(R.color.title_color));
        }else {
            shopStatue.setText("团购劵已取消");
            shopImage.setAlpha(255 / 2);
            linearlayoutImage.setBackground(TuanBulkVolumeActivity.this.getResources().getDrawable(R.drawable.shap_bg_white_with_gray_boder));
            shopStatue.setBackground(TuanBulkVolumeActivity.this.getResources().getDrawable(R.drawable.shap_bg_white_with_gray_boder));
            shopStatue.setTextColor(TuanBulkVolumeActivity.this.getResources().getColor(R.color.title_color));
        }
    }

    private void initnTent() {
        Intent intent = getIntent();
        if (intent != null) {
            ticket_number = intent.getStringExtra("ticket_number");
            ShopCount = intent.getStringExtra("x" + "ShopCount");
            Ticket_status = intent.getStringExtra("Ticket_status");
        }

    }

    @Override
    protected void initActionBar() {
//        backIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TuanBulkVolumeActivity.this.finish();
//            }
//        });
//        titleTv.setText("团购券");
        tvTitle.setText("团购券");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        StatusBarUtil.immersive(TuanBulkVolumeActivity.this);
//        StatusBarUtil.setPaddingSmart(TuanBulkVolumeActivity.this, toolbar);

    }

    @Override
    protected void initFindViewById() {
        mHeadview = this.findViewById(R.id.title);
    }

    @Override
    protected void initEvent() {

    }

}
