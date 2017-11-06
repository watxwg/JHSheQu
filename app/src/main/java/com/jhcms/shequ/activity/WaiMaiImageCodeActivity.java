package com.jhcms.shequ.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.waimaiV3.R;

public class WaiMaiImageCodeActivity extends AppCompatActivity {
   ImageView imagecode;
    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                Glide.with(WaiMaiImageCodeActivity.this).load(Api.BASE_URL+Api.WAIMAI_VERIFY)
                        .diskCacheStrategy( DiskCacheStrategy.NONE ).skipMemoryCache(true).into(imagecode);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waimai_popu_imagewindow);
        TextView cancelBtn= (TextView) this.findViewById(R.id.cancel);
        TextView truebtn= (TextView) this.findViewById(R.id.trueButton);
        imagecode = (ImageView) this.findViewById(R.id.imagecode);
        Glide.with(WaiMaiImageCodeActivity.this).load(Api.BASE_URL+Api.WAIMAI_VERIFY).into(imagecode);
        imagecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("ok");
                mhandler.sendEmptyMessage(0);
            }
        });
        LinearLayout layout= (LinearLayout) this.findViewById(R.id.layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout.getBackground().setAlpha(255/2);
        final EditText imageEdtext= (EditText) this.findViewById(R.id.ImagecodeEd);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        truebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(imageEdtext.getText())){
                    ToastUtil.show("图片验证码不能为空!");
                    return;
                }

            }


        });
    }
}
