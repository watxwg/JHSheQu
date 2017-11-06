package com.jhcms.tuangou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.waimaiV3.R;

/**
 * 优惠订单详情
 */
public class TuanFavorableBuyOrderDetailsActivity extends BaseActivity {
    private View mHeadview;
    private ImageView mIvBack;
    private TextView  mTitlecontent;
    private  int postionflag;//0 代表去评价 1 代表查看评价
    private  TextView mLookEvaluate;
    private  TextView mComeToEvaluate;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_favorable_buy_order_details);
    }

    @Override
    protected void initData() {
        inintintent();
    }

    private void inintintent() {
        Intent intent=getIntent();
        postionflag=intent.getIntExtra("flag",-1);
        if(postionflag==0){
            mLookEvaluate.setVisibility(View.GONE);
            mComeToEvaluate.setVisibility(View.VISIBLE);

        }else if (postionflag==1){
            mLookEvaluate.setVisibility(View.VISIBLE);
            mComeToEvaluate.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initActionBar() {
    mIvBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TuanFavorableBuyOrderDetailsActivity.this.finish();
        }
    });
        mTitlecontent.setText("订单详情");
    }

    @Override
    protected void initFindViewById() {
        mHeadview=this.findViewById(R.id.title);
        mIvBack= (ImageView) mHeadview.findViewById(R.id.back_iv);
        mTitlecontent= (TextView) mHeadview.findViewById(R.id.title_tv);
        mLookEvaluate= (TextView) this.findViewById(R.id.LookEvaluate_tv);
        mComeToEvaluate= (TextView) this.findViewById(R.id.ComeToEvaluate_tv);

    }

    @Override
    protected void initEvent() {
        inintLookEvaluateEvent();
        inintComeToEvaluateEvent();
    }

    private void inintComeToEvaluateEvent() {
        mComeToEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TuanFavorableBuyOrderDetailsActivity.this,"去评价",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(TuanFavorableBuyOrderDetailsActivity.this,TuanFavorableBuyOrderDetailsActivity.class);
                intent.putExtra("flag",postionflag);
                TuanFavorableBuyOrderDetailsActivity.this.startActivity(intent);
            }
        });
    }

    private void inintLookEvaluateEvent() {
        mLookEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TuanFavorableBuyOrderDetailsActivity.this,"查看评价",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(TuanFavorableBuyOrderDetailsActivity.this,TuanFavorableBuyOrderDetailsActivity.class);
                intent.putExtra("flag",postionflag);
                TuanFavorableBuyOrderDetailsActivity.this.startActivity(intent);
            }
        });
    }
}
